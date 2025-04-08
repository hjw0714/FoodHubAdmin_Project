package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.config.JwtUtil;
import com.application.foodhubAdmin.dto.request.UserLogInRequest;
import com.application.foodhubAdmin.dto.response.user.DailyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.MonthlyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse;
import com.application.foodhubAdmin.repository.UserMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMsService {

    @Value("${file.repo.path}")
    private String fileRepositoryPath;

    private final UserMsRepository userMsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

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


}
