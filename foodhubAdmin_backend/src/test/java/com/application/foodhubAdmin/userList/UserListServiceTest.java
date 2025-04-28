package com.application.foodhubAdmin.userList;

import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.dto.request.UserChangePasswdRequest;
import com.application.foodhubAdmin.dto.response.user.UserListResponse;
import com.application.foodhubAdmin.repository.UserMsRepository;
import com.application.foodhubAdmin.service.UserMsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class UserListServiceTest {

    @Mock
    private UserMsRepository userMsRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMsService userMsService;

    private User test;

    @BeforeEach
    void setUp() {
        test = new User();
        test.User("test", passwordEncoder.encode("test"), "test", "test@example.com");
        userMsRepository.saveAndFlush(test);
    }

    @Test
    @Order(1)
    @DisplayName("유저 리스트 조회")
    public void getUserList() {
        List<UserListResponse> userList = userMsService.getUserList();
        Assertions.assertNotNull(userList);
    }

    @Test
    @Order(2)
    @DisplayName("유저 회원 탈퇴")
    public void deleteUser() {
        userMsService.deleteMember(test.getId());
    }

    @Test
    @Order(3)
    @DisplayName("유저 회원 탈퇴 취소")
    public void notDeleteUser() {
        userMsService.notDeleteMember(test.getId());
    }

    @Test
    @Order(4)
    @DisplayName("비밀번호 변경")
    public void changePassword() {
        UserChangePasswdRequest request = new UserChangePasswdRequest();
        request.setUserId("test");
        request.setPasswd(passwordEncoder.encode("test1"));
        userMsService.changePasswd(request);
    }

    @Test
    @Order(5)
    @DisplayName("멤버십 변경")
    public void updateMembership() {
        userMsService.updateMembershipType(test.getId(), "membershipType : 사업자");
    }

}
