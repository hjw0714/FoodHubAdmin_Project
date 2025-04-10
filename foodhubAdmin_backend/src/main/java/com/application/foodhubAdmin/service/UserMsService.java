package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.config.JwtUtil;


import com.application.foodhubAdmin.dto.request.UserChangePasswdRequest;
import com.application.foodhubAdmin.dto.request.UserUpdateRequest;
import com.application.foodhubAdmin.dto.response.user.*;

import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.dto.request.UserLogInRequest;
import com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.UserProfileResponse;
import com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse;
import com.application.foodhubAdmin.repository.StatsRepository;

import com.application.foodhubAdmin.repository.UserMsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
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
                new UsernamePasswordAuthenticationToken(requestDto.getUserId() ,requestDto.getPasswd())
        );

        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String memberShip = principal.getAuthorities().iterator().next().getAuthority();
        System.out.println(memberShip);

        if (memberShip.startsWith("ROLE_")) {
            memberShip = memberShip.substring(5);
        }
        return jwtUtil.generateToken(requestDto.getUserId() , memberShip);

    }

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

    // 월별 신규 가입자 수
    public List<MonthlyNewUserCntResponse> getMonthlyNewUserCnt() {
       return userMsRepository.getMonthlyNewUserCnt();
    }

    // 년도별 신규 가입자 수
    public List<YearlyNewUserCntResponse> getYearlyNewUserCnt() {
        return userMsRepository.getYearlyNewUserCnt();
    }

    // 일별 신규 가입자 수
    public List<DailyNewUserCntResponse> getDailyNewUserCnt() {
        return userMsRepository.getDailyNewUserCnt();
    }

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

    public void updateUserStats(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        Long joinCnt = userMsRepository.countByJoinAtBetween(start, end);
        Long deletedCnt = userMsRepository.countByDeletedAtBetween(start, end);

        Optional<Stats> existing = statsRepository.findByCateAndTerm("USER", date);

        if (existing.isPresent()) {
            Stats stats = existing.get();
            stats.setJoinCnt(joinCnt);
            stats.setDeletedCnt(deletedCnt);
            stats.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(stats);
        } else {
            Stats newStats = Stats.builder()
                    .cate("USER")
                    .term(date)
                    .joinCnt(joinCnt)
                    .deletedCnt(deletedCnt)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            statsRepository.save(newStats);
        }
    }
}
