package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.config.JwtUtil;
import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.dto.request.UserChangePasswdRequest;
import com.application.foodhubAdmin.dto.request.UserLogInRequest;
import com.application.foodhubAdmin.dto.request.UserUpdateRequest;
import com.application.foodhubAdmin.dto.response.user.*;
import com.application.foodhubAdmin.repository.StatsRepository;
import com.application.foodhubAdmin.repository.UserMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserMsService {

    @Value("${file.repo.path}")
    private String fileRepositoryPath;

    private final UserMsRepository userMsRepository;
    private final StatsRepository statsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // 현재 로그인된 사용자 ID 가져오기
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("로그인되지 않은 사용자입니다.");
        }
        return authentication.getName(); // memberId 반환
    }

    // 로그인
    public String logIn(UserLogInRequest requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUserId(), requestDto.getPasswd())
        );

        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String memberShip = principal.getAuthorities().iterator().next().getAuthority();
        System.out.println(memberShip);

        if (memberShip.startsWith("ROLE_")) {
            memberShip = memberShip.substring(5);
        }
        return jwtUtil.generateToken(requestDto.getUserId(), memberShip);

    }

    // 마이페이지
    public UserProfileResponse getProfile() {
        User user = userMsRepository.findById(getCurrentUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        return UserProfileResponse.of(user);
    }

    // 회원정보 수정
    @Transactional
    public UserUpdateResponse updateUser(MultipartFile uploadProfile, UserUpdateRequest requestDto) throws IOException {
        User user = userMsRepository.findById(getCurrentUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        String originalFile = null;
        String profileUuid = null;

        if (uploadProfile != null && !uploadProfile.isEmpty()) {
            new File(fileRepositoryPath + requestDto.getProfileUuid()).delete();
            originalFile = uploadProfile.getOriginalFilename();
            String extension = originalFile.substring(originalFile.lastIndexOf("."));
            profileUuid = UUID.randomUUID() + extension;
            uploadProfile.transferTo(new File(fileRepositoryPath + profileUuid));
        }

        user.updateUser(originalFile, profileUuid, requestDto.getTel(), requestDto.getEmail());
        User updatedUser = userMsRepository.save(user);
        return UserUpdateResponse.of(updatedUser);
    }

    // 비밀번호 변경
    @Transactional
    public void changePasswd(UserChangePasswdRequest requestDto) {
        User user = userMsRepository.findById(requestDto.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.changePasswd(passwordEncoder.encode(requestDto.getPasswd()));
    }


    // 유저 정보 리스트
    public List<UserListResponse> getUserList() {
        return userMsRepository.findAll()
                .stream()
                .map(UserListResponse::of)
                .toList();
    }

    // 유저 가입수 통계 저장
    public void insertUserStatsJoin(LocalDate date) {
        // 1. 해당 날짜에 가입한 유저 수 조회
        Long joinCount = userMsRepository.countUserJoinedOn(date);

        // 2. 기존 통계가 존재하는지 확인
        Optional<Stats> optionalStats = statsRepository.findByCategoryIdAndStatDate(1, date);

        if (optionalStats.isPresent()) {
            // 3. 존재하면 업데이트
            Stats existing = optionalStats.get();
            existing.setStatCnt(joinCount);
            existing.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(existing);
        } else {
            // 4. 없으면 새로 삽입
            Stats stats = Stats.builder()
                    .categoryId(1)
                    .statDate(date)
                    .statCnt(joinCount)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            statsRepository.save(stats);
        }
    }

    // 유저 탈퇴수 통계 저장
    public void insertUserStatsDelete(LocalDate date) {

        Long deletedCount = userMsRepository.countUserDeletedOn(date);

        Optional<Stats> optionalStats = statsRepository.findByCategoryIdAndStatDate(2, date);

        if (optionalStats.isPresent()) {
            Stats existing = optionalStats.get();
            existing.setStatCnt(deletedCount);
            existing.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(existing);
        } else {
            Stats stats = Stats.builder()
                    .categoryId(2) // 탈퇴자 통계
                    .statDate(date)
                    .statCnt(deletedCount)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            statsRepository.save(stats);
        }
    }

    // 유저 총회원수 통계 저장
    public void insertUserStatsTotal(LocalDate date) {
        Long totalCount = userMsRepository.countTotalUsers();

        Optional<Stats> optionalStats = statsRepository.findByCategoryIdAndStatDate(3, date);

        if (optionalStats.isPresent()) {
            Stats existing = optionalStats.get();
            existing.setStatCnt(totalCount);
            existing.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(existing);
        } else {
            Stats stats = Stats.builder()
                    .categoryId(3) // 총회원수
                    .statDate(date)
                    .statCnt(totalCount)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            statsRepository.save(stats);
        }
    }




    // 년도별 총 회원수 조회
    public List<YearlyTotalUserCntResponse> getYearlyTotalUserCnt() {
        return statsRepository.getYearlyTotalUserCnt();
    }

    // 월별 총 회원수 조회
    public List<MonthlyTotalUserCntResponse> getMonthlyTotalUserCnt(String startDate) {
        String year = startDate.substring(0, 4);
        String month = startDate.substring(5, 7);
        if(month.startsWith("0")) {
            month = month.substring(1);
        }
        startDate = year + "-" + month;
        return statsRepository.getMonthlyTotalUserCnt(startDate);
    }
    // 유저 탈퇴
    @Transactional
    public void deleteMember(String id) {
        User user = userMsRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.deleteMember();
        userMsRepository.save(user);
    }

    // 유저 리스트에서 멤버십 변경
    @Transactional
    public void updateMembershipType(String id, String membership) {
        String mem = membership.substring(membership.lastIndexOf(":")).substring(2);
        User user = userMsRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.updateMemberShipType(mem);
        userMsRepository.save(user);

    }

    // 일별 총 회원수 조회
    public List<DailyTotalUserCntResponse> getDailyTotalUserCnt(String startDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate = dateFormat.parse(startDate); // String → Date 변환
        return statsRepository.getDailyTotalUserCnt(parsedStartDate);
    }


    // 년도별 신규 가입자 수 조회
    public List<YearlyNewUserCntResponse> getYearlyNewUserCnt() {
        return statsRepository.getYearlyNewUserCnt();
    }

    // 월별 신규 가입자 수 조회
    public List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt(String startDate) {
        String year = startDate.substring(0, 4);
        String month = startDate.substring(5, 7);
        if(month.startsWith("0")) {
            month = month.substring(1);
        }
        startDate = year + "-" + month;
        return statsRepository.getMonthlyNewUserCnt(startDate);
    }

    // 일별 신규 가입자 수 조회
    public List<DailyNewUserCntResponse> getDailyNewUserCnt(String startDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate = dateFormat.parse(startDate); // String → Date 변환
        return statsRepository.getDailyNewUserCnt(parsedStartDate);
    }

    // 년도별 탈퇴 수 조회
    public List<YearlyDeleteUserCntResponse> getYearlyDeleteUserCnt() {
        return statsRepository.getYearlyDeleteUserCnt();
    }

    // 월별 탈퇴 수 조회
    public List<MonthlyDeleteUserCntResponse> getMonthlyDeleteUserCnt(String startDate) {
        String year = startDate.substring(0, 4);
        String month = startDate.substring(5, 7);
        if(month.startsWith("0")) {
            month = month.substring(1);
        }
        startDate = year + "-" + month;
        return statsRepository.getMonthlyDeleteUserCnt(startDate);
    }

    // 일별 탈퇴 수 조회
    public List<DailyDeleteUserCntResponse> getDailyDeleteUserCnt(String startDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate = dateFormat.parse(startDate); // String → Date 변환
        return statsRepository.getDailyDeleteUserCnt(parsedStartDate);
    }

    // 대시보드 신규 가입자
    public List<MonthlyNewUserCntResponse> getNewUserCnt() {
        return statsRepository.getNewUserCnt();
    }

    // 대시보드 탈퇴 수
    public List<MonthlyDeleteUserCntResponse> getDeleteUserCnt() {
        return statsRepository.getDeleteUserCnt();
    }

    @Transactional // 유저 하드 삭제
    public void hardDeleteUsers() {
        // 기준 시간: 현재 시각에서 6개월 이전
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);

        List<User> usersToDelete = userMsRepository.findUsersToHardDelete(sixMonthsAgo);
        if (!usersToDelete.isEmpty()) {
            userMsRepository.deleteAll(usersToDelete);
        }
    }

}

