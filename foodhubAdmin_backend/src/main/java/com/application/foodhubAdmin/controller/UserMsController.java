package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.domain.MembershipType;

import com.application.foodhubAdmin.dto.request.UserChangePasswdRequest;

import com.application.foodhubAdmin.dto.request.UserLogInRequest;
import com.application.foodhubAdmin.dto.request.UserUpdateRequest;
import com.application.foodhubAdmin.dto.response.user.*;
import com.application.foodhubAdmin.service.UserMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class UserMsController {

    private final UserMsService userMsService;

    // 로그인
    @PostMapping("/logIn")
    public ResponseEntity<?> logIn (@RequestBody UserLogInRequest requestDto) {
        String token = userMsService.logIn(requestDto);
        return ResponseEntity.ok(token);
    }

    // 마이 페이지
    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        return ResponseEntity.ok(userMsService.getProfile());
    }

    // 회원정보 수정
    @PutMapping("/editProfile")
    public ResponseEntity<UserUpdateResponse> updateUser(@RequestPart(value = "uploadProfile", required = false) MultipartFile uploadProfile,
                                                         @RequestPart("requestDto") UserUpdateRequest requestDto) throws IOException {
        return ResponseEntity.ok(userMsService.updateUser(uploadProfile, requestDto));
    }

    // 비밀번호 변경
    @PutMapping("/changePasswd")
    public ResponseEntity<?> changePasswd(@RequestBody UserChangePasswdRequest requestDto) {
        userMsService.changePasswd(requestDto);
        return ResponseEntity.ok().build();
    }

    // 년도별 신규 가입자 수
    @GetMapping("/yearlyNewUser")
    public ResponseEntity<List<YearlyNewUserCntResponse>> yearlyNewUser() {
        return ResponseEntity.ok(userMsService.getYearlyNewUserCnt());
    }

    // 월별 신규 가입자 수
    @GetMapping("/monthlyNewUser")
    public ResponseEntity<List<MonthlyNewUserCntResponse>> monthlyNewUser() {
        return ResponseEntity.ok(userMsService.getMonthlyNewUserCnt());
    }

    // 일별 신규 가입자 수
    @GetMapping("/dailyNewUser")
    public ResponseEntity<List<DailyNewUserCntResponse>> dailyNewUser() {
        return ResponseEntity.ok(userMsService.getDailyNewUserCnt());
    }


    // 유저 정보 리스트
    @GetMapping("/memberList")
    public ResponseEntity<List<UserListResponse>> memberList() {
        return ResponseEntity.ok(userMsService.getUserList());
    }

    // 유저 탈퇴
    @DeleteMapping("/memberList/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") String id) {
        userMsService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    // 유저 리스트에서 멤버십 변경
    @PutMapping("/memberList/{id}")
    public ResponseEntity<?> updateMembershipType(@PathVariable("id") String id,
                                          @RequestBody MembershipType membershipType) {
        userMsService.updateMembershipType(id, membershipType);
        return ResponseEntity.ok().build();
    }

}
