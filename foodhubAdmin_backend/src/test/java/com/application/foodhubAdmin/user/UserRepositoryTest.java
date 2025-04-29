package com.application.foodhubAdmin.user;

import com.application.foodhubAdmin.domain.MembershipType;
import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.repository.UserMsRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Mock
    private UserMsRepository userMsRepository;

    private User user;

    @BeforeEach
    void beforeSetupData() {
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
    }

    @Test @Order(1) @DisplayName("사용자 프로필 조회")
    void testFindUserById() {
        // Given
        when(userMsRepository.findById("testUser")).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userMsRepository.findById("testUser");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo("testUser");
        assertThat(result.get().getNickname()).isEqualTo("testNickname");
        verify(userMsRepository, times(1)).findById("testUser");
    }

    @Test @Order(2) @DisplayName("사용자 정보 수정 저장")
    void testUpdateUser() {
        // Given
        user.updateUser("new-profile.jpg", "new-profile-uuid", "010-1111-2222", "updatedEmail@test.com");
        when(userMsRepository.save(user)).thenReturn(user);

        // When
        User updatedUser = userMsRepository.save(user);

        // Then
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo("testUser");
        assertThat(updatedUser.getEmail()).isEqualTo("updatedEmail@test.com");
        assertThat(updatedUser.getTel()).isEqualTo("010-1111-2222");
        assertThat(updatedUser.getProfileUuid()).isEqualTo("new-profile-uuid");
        assertThat(updatedUser.getProfileOriginal()).isEqualTo("new-profile.jpg");
        verify(userMsRepository, times(1)).save(user);
    }

    @Test @Order(3) @DisplayName("닉네임 조회")
    void testGetNicknameByUserId() {
        // Given
        when(userMsRepository.getNicknameByUserId("testUser")).thenReturn("testNickname");

        // When
        String nickname = userMsRepository.getNicknameByUserId("testUser");

        // Then
        assertThat(nickname).isEqualTo("testNickname");
        verify(userMsRepository, times(1)).getNicknameByUserId("testUser");
    }

}
