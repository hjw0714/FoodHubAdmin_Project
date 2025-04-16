package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.request.UserChangePasswdRequest;
import com.application.foodhubAdmin.dto.request.UserUpdateRequest;
import com.application.foodhubAdmin.dto.response.user.*;
import com.application.foodhubAdmin.service.UserMsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class UserMsController {

    private final UserMsService userMsService;

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
        System.out.println("id: " + requestDto.getUserId());
        System.out.println("passwd: " + requestDto.getPasswd());
        userMsService.changePasswd(requestDto);
        return ResponseEntity.ok().build();
    }


    // 유저 정보 리스트
    @GetMapping("/memberList")
    public ResponseEntity<List<UserListResponse>> memberList() {
        return ResponseEntity.ok(userMsService.getUserList());
    }

    // 유저 탈퇴
    @DeleteMapping("/memberList/delete/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") String id) {
        userMsService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    // 유저 리스트에서 멤버십 변경
    @PutMapping("/memberList/update/{id}")
    public ResponseEntity<?> updateMembershipType(@PathVariable("id") String id,
                                                  @RequestBody String membership) {
        userMsService.updateMembershipType(id, membership);
        return ResponseEntity.ok().build();
    }

    // 년도별 신규 가입자 수
    @GetMapping("/yearlyNewUser")
    public ResponseEntity<List<YearlyNewUserCntResponse>> yearlyNewUser() {
        return ResponseEntity.ok(userMsService.getYearlyNewUserCnt());
    }

    // 월별 신규 가입자 수
    @GetMapping("/monthlyNewUser")
    public ResponseEntity<List<MonthlyNewUserCntResponse>> monthlyNewUser(@RequestParam("startDate") String startDate) {
        return ResponseEntity.ok(userMsService.getMonthlyNewUserCnt(startDate));
    }


    // 일별 신규 가입자 수
    @GetMapping("/dailyNewUser")
    public ResponseEntity<List<DailyNewUserCntResponse>> dailyNewUser(@RequestParam("startDate") String startDate) throws ParseException {
        return ResponseEntity.ok(userMsService.getDailyNewUserCnt(startDate));
    }

    // 년도별 총 회원 수
    @GetMapping("/yearlyTotalUser")
    public ResponseEntity<List<YearlyTotalUserCntResponse>> yearlyTotalUser() {
        return ResponseEntity.ok(userMsService.getYearlyTotalUserCnt());
    }

    // 월별 총 회원 수
    @GetMapping("/monthlyTotalUser")
    public ResponseEntity<List<MonthlyTotalUserCntResponse>> monthlyTotalUser(@RequestParam("startDate") String startDate) {
        return ResponseEntity.ok(userMsService.getMonthlyTotalUserCnt(startDate));
    }

    // 일별 총 회원 수
    @GetMapping("/dailyTotalUser")
    public ResponseEntity<List<DailyTotalUserCntResponse>> dailyTotalUser(@RequestParam("startDate") String startDate) throws ParseException {
        return ResponseEntity.ok(userMsService.getDailyTotalUserCnt(startDate));
    }

    // 년도별 탈퇴 수
    @GetMapping("/yearlyDeleteUser")
    public ResponseEntity<List<YearlyDeleteUserCntResponse>> yearlyDeleteUser() {
        return ResponseEntity.ok(userMsService.getYearlyDeleteUserCnt());
    }


    // 일별 탈퇴 수
    @GetMapping("/dailyDeleteUser")
    public ResponseEntity<List<DailyDeleteUserCntResponse>> dailyDeleteUser(@RequestParam("startDate") String startDate) throws ParseException {
        return ResponseEntity.ok(userMsService.getDailyDeleteUserCnt(startDate));
    }

    // 월별 탈퇴 수
    @GetMapping("/monthlyDeleteUser")
    public ResponseEntity<List<MonthlyDeleteUserCntResponse>> monthlyDeleteUser(@RequestParam("startDate") String startDate) {
        return ResponseEntity.ok(userMsService.getMonthlyDeleteUserCnt(startDate));
    }

    // 대시보드 월별 신규 가입자 수
    @GetMapping("/newUser")
    public ResponseEntity<List<MonthlyNewUserCntResponse>> newUser() {
        return ResponseEntity.ok(userMsService.getNewUserCnt());
    }

    // 대시보드 탈퇴 수
    @GetMapping("/deleteUser")
    public ResponseEntity<List<MonthlyDeleteUserCntResponse>> deleteUser() {
        return ResponseEntity.ok(userMsService.getDeleteUserCnt());
    }

}
