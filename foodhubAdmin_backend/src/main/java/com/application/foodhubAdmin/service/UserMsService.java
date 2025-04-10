package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.config.JwtUtil;
import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.dto.request.UserChangePasswdRequest;
import com.application.foodhubAdmin.dto.request.UserLogInRequest;
import com.application.foodhubAdmin.dto.request.UserUpdateRequest;
import com.application.foodhubAdmin.dto.response.user.UserListResponse;
import com.application.foodhubAdmin.dto.response.user.UserProfileResponse;
import com.application.foodhubAdmin.dto.response.user.UserUpdateResponse;
import com.application.foodhubAdmin.repository.StatsRepository;
import com.application.foodhubAdmin.repository.UserMsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


//    // 월별 신규 가입자 수
//    public List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt() {
//       return userMsRepository.getMonthlyNewUserCnt();
//    }
//
//    // 년도별 신규 가입자 수
//    public List<YearlyNewUserCntResponse> getYearlyNewUserCnt() {
//        return userMsRepository.getYearlyNewUserCnt();
//    }
//
//    // 일별 신규 가입자 수
//    public List<DailyNewUserCntResponse> getDailyNewUserCnt() {
//        return userMsRepository.getDailyNewUserCnt();
//    }

    // 회원정보 수정
    @Transactional
    public UserUpdateResponse updateUser(MultipartFile uploadProfile, UserUpdateRequest requestDto) throws IOException {
        com.application.foodhubAdmin.domain.User user = userMsRepository.findById(getCurrentUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        String originalFile = null;
        String profileUuid = null;

        if (uploadProfile != null && requestDto.getProfileUuid() != null) {
            new File(fileRepositoryPath + requestDto.getProfileUuid()).delete();
            originalFile = uploadProfile.getOriginalFilename();
            String extension = originalFile.substring(originalFile.lastIndexOf("."));
            profileUuid = UUID.randomUUID() + extension;
            uploadProfile.transferTo(new File(fileRepositoryPath + profileUuid));
        }

        user.updateUser(originalFile, profileUuid, requestDto.getTel(), requestDto.getEmail());
        com.application.foodhubAdmin.domain.User updatedUser = userMsRepository.save(user);
        return UserUpdateResponse.of(updatedUser);
    }

    // 비밀번호 변경
    @Transactional
    public void changePasswd(UserChangePasswdRequest requestDto) {
        com.application.foodhubAdmin.domain.User user = userMsRepository.findById(requestDto.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.changePasswd(passwordEncoder.encode(requestDto.getPasswd()));
    }

//    // 월별 신규 가입자 수
//    public List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt() {
//       return userMsRepository.getMonthlyNewUserCnt();
//    }
//
//    // 년도별 신규 가입자 수
//    public List<YearlyNewUserCntResponse> getYearlyNewUserCnt() {
//        return userMsRepository.getYearlyNewUserCnt();
//    }
//
//    // 일별 신규 가입자 수
//    public List<DailyNewUserCntResponse> getDailyNewUserCnt() {
//        return userMsRepository.getDailyNewUserCnt();
//    }


    // 마이페이지
    public UserProfileResponse getProfile() {
        User user = userMsRepository.findById(getCurrentUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        return UserProfileResponse.of(user);
    }

    // 유저 정보 리스트
    public List<UserListResponse> getUserList() {
        return userMsRepository.findAll()
                .stream()
                .map(UserListResponse::of)
                .toList();
    }

    // 유저 가입수 통계 저장
    public void updateUserStatsJoin(LocalDate date) {
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
    public void updateUserStatsDelete(LocalDate date) {

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
    public void updateUserStatsTotal(LocalDate date) {
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

}

