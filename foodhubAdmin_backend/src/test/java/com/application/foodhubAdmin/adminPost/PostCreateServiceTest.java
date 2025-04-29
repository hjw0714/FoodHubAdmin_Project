package com.application.foodhubAdmin.adminPost;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.dto.request.PostCreateRequest;
import com.application.foodhubAdmin.repository.FileUploadRepository;
import com.application.foodhubAdmin.repository.PostCreateRepository;
import com.application.foodhubAdmin.repository.UserMsRepository;
import com.application.foodhubAdmin.service.PostCreateService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostCreateServiceTest {

    @Mock
    private PostCreateRepository postCreateRepository;

    @Mock
    private FileUploadRepository fileUploadRepository;

    @Mock
    private UserMsRepository userMsRepository;

    @InjectMocks
    private PostCreateService postCreateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("게시글 생성")
    void createPost() throws IOException {
        System.out.println("1. 게시글 생성 테스트");

        User user = User.builder()
                .id("admin")
                .nickname("관리자")
                .build();

        PostCreateRequest request = new PostCreateRequest();
        request.setUserId("admin");
        request.setTitle("테스트 게시글");
        request.setContent("테스트 내용");
        request.setFile(new MockMultipartFile(
                "file",
                "test.png",
                "image/png",
                "test file content".getBytes()
        ));

        when(userMsRepository.findById("admin")).thenReturn(Optional.of(user));
        when(postCreateRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        postCreateService.createPost(request);

        verify(postCreateRepository, times(1)).save(any(Post.class));
        verify(fileUploadRepository, times(1)).save(any()); // 파일까지 저장됐는지 검증

        System.out.println("게시글 생성 완료");
        System.out.println();
    }
}
