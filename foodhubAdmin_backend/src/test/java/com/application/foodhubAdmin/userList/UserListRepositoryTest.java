package com.application.foodhubAdmin.userList;

import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.repository.UserMsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class UserListRepositoryTest {

    @Autowired
    private UserMsRepository userMsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private User test;

    @BeforeEach
    void setUp() {
        test = userMsRepository.findById("3333").orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Test
    @Order(1)
    @DisplayName("유저 리스트 조회")
    public void getUserList() {
        List<User> userList = userMsRepository.findAll();
        Assertions.assertNotNull(userList);
    }

    @Test
    @Order(2)
    @DisplayName("유저 멤버십 변경")
    public void updateMembership() {
        String membership = "일반";
        test.updateMemberShipType(membership);
        if(test.getMembershipType().equals("COMMON")) {
            Assertions.assertEquals("COMMON", test.getMembershipType());
        }
        else if(test.getMembershipType().equals("BUSSI")) {
            Assertions.assertEquals("BUSSI", test.getMembershipType());
        }
    }

    @Test
    @Order(3)
    @DisplayName("유저 탈퇴")
    public void deleteUser() {
        test.deleteMember();
        Assertions.assertNotNull(test.getDeletedAt());
    }

    @Test
    @Order(4)
    @DisplayName("유저 탈퇴 취소")
    public void notDeleteUser() {
        test.notDeleteMember();
        Assertions.assertNull(test.getDeletedAt());
    }

    @Test
    @Order(5)
    @DisplayName("유저 리스트 비밀번호 변경")
    public void changePasswd() {
        String newPasswd = "123456";
        test.changePasswd(passwordEncoder.encode(newPasswd));
        Assertions.assertEquals(true, passwordEncoder.matches(newPasswd, test.getPasswd()));
    }

}