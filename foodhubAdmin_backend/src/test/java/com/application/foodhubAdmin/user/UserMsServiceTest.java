package com.application.foodhubAdmin.user;

import com.application.foodhubAdmin.domain.MembershipType;
import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.dto.request.UserChangePasswdRequest;
import com.application.foodhubAdmin.dto.request.UserUpdateRequest;
import com.application.foodhubAdmin.dto.response.user.UserProfileResponse;
import com.application.foodhubAdmin.dto.response.user.UserUpdateResponse;
import com.application.foodhubAdmin.repository.UserMsRepository;
import com.application.foodhubAdmin.service.UserMsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserMsServiceTest {

    @Mock
    private UserMsRepository userMsRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserMsService userMsService;

    private User user;
    private MockMultipartFile mockFile;

    @BeforeEach
    void beforeSetUpDate() {
        user = User.builder()
                .id("testUser")
                .passwd("encodedPassword")
                .nickname("testNickname")
                .email("testEmail@test.com")
                .tel("010-1234-5678")
                .gender("M")
                .birthday(LocalDate.of(2000, 1, 5))
                .profileUuid("profile-uuid")
                .profileOriginal("profile.jpg")
                .membershipType(MembershipType.ADMIN)
                .smsYn("Y")
                .emailYn("Y")
                .joinAt(LocalDateTime.now())
                .modifyAt(LocalDateTime.now())
                .status("ACTIVE")
                .build();

        // 모킹된 파일 설정
        mockFile = new MockMultipartFile("uploadProfile", "profile.jpg", "image/jpeg", "test image".getBytes());
    }

    @Test @Order(1) @DisplayName("마이페이지 프로필 조회")
    void testGetProfile() {
        // Given
        when(authentication.getName()).thenReturn("testUser");
        when(authentication.isAuthenticated()).thenReturn(true);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userMsRepository.findById("testUser")).thenReturn(Optional.of(user));

        // When
        UserProfileResponse response = userMsService.getProfile();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo("testUser");
        assertThat(response.getNickname()).isEqualTo("testNickname");
        assertThat(response.getEmail()).isEqualTo("testEmail@test.com");
        verify(userMsRepository, times(1)).findById("testUser");
    }

    @Test @Order(2) @DisplayName("회원정보 수정")
    void testUpdateUser() throws IOException {
        // Given
        when(authentication.getName()).thenReturn("testUser");
        when(authentication.isAuthenticated()).thenReturn(true);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        UserUpdateRequest requestDto = new UserUpdateRequest();
        requestDto.setEmail("updatedEmail@test.com");
        requestDto.setTel("010-1111-2222");
        requestDto.setProfileUuid("old-uuid");

        when(userMsRepository.findById("testUser")).thenReturn(Optional.of(user));
        when(userMsRepository.save(any(User.class))).thenReturn(user);

        // When
        UserUpdateResponse response = userMsService.updateUser(mockFile, requestDto);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("updatedEmail@test.com");
        assertThat(response.getProfileOriginal()).isEqualTo("profile.jpg");
        verify(userMsRepository, times(1)).findById("testUser");
        verify(userMsRepository, times(1)).save(any(User.class));
    }

    @Test @Order(3) @DisplayName("비밀번호 변경")
    void testChangePasswd() {
        // Given
        UserChangePasswdRequest requestDto = new UserChangePasswdRequest();
        requestDto.setUserId("testUser");
        requestDto.setPasswd("newPassword");

        when(userMsRepository.findById("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        // When
        userMsService.changePasswd(requestDto);

        // Then
        verify(userMsRepository, times(1)).findById("testUser");
        verify(passwordEncoder, times(1)).encode("newPassword");
    }

}
