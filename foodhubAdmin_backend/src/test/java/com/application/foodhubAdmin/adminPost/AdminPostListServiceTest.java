package com.application.foodhubAdmin.adminPost;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.domain.PostStatus;
import com.application.foodhubAdmin.dto.response.post.AdminPostListResponse;
import com.application.foodhubAdmin.repository.AdminPostListRepository;
import com.application.foodhubAdmin.service.AdminPostListService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminPostListServiceTest {

    @Mock
    private AdminPostListRepository adminPostListRepository;

    @InjectMocks
    private AdminPostListService adminPostListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("공지사항 게시글 조회")
    void findNoticePosts() {
        System.out.println("1. 공지사항 게시글 조회 테스트");

        Post post1 = Post.builder()
                .id(1L)
                .title("공지사항1")
                .categoryNm("공지사항")
                .nickname("관리자1")
                .createdAt(LocalDateTime.now())
                .status(PostStatus.ACTIVE)
                .build();

        Post post2 = Post.builder()
                .id(2L)
                .title("공지사항2")
                .categoryNm("공지사항")
                .nickname("관리자2")
                .createdAt(LocalDateTime.now())
                .status(PostStatus.ACTIVE)
                .build();
        
        when(adminPostListRepository.findByCategoryNm("공지사항")).thenReturn(List.of(post1 , post2));

        List<Post> result = adminPostListService.findNoticePosts();

        assertEquals(2, result.size());
        assertEquals("공지사항1", result.get(0).getTitle());
        assertEquals("공지사항2", result.get(1).getTitle());
        assertEquals("관리자1", result.get(0).getNickname());
        assertEquals("관리자2", result.get(1).getNickname());

        System.out.println("조회된 게시글 수: " + result.size());
        for (Post post : result) {
            System.out.println("ID: " + post.getId());
            System.out.println("제목: " + post.getTitle());
            System.out.println("작성자: " + post.getNickname());
            System.out.println("등록일: " + post.getCreatedAt());
            System.out.println("상태: " + post.getStatus());
            System.out.println("==============================");
        }
        System.out.println();
    }

    @Test
    @Order(2)
    @DisplayName("공지사항 게시글 삭제")
    void deletePost() throws IllegalAccessException {
        System.out.println("2. 공지사항 게시글 삭제 테스트");

        Post post = Post.builder()
                .id(1L)
                .title("삭제할 게시글")
                .nickname("관리자")
                .status(PostStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        when(adminPostListRepository.findById(1L)).thenReturn(Optional.of(post));

        System.out.println("삭제 전 상태: " + post.getStatus());

        adminPostListService.deletePost(1L);

        assertEquals(PostStatus.DELETED, post.getStatus());

        System.out.println("삭제 후 상태: " + post.getStatus());
        System.out.println();
    }

    @Test
    @Order(3)
    @DisplayName("공지사항 게시글 복구")
    void restorePost() throws IllegalAccessException {
        System.out.println("3. 공지사항 게시글 복구 테스트");

        Post post = Post.builder()
                .id(1L)
                .title("복구할 게시글")
                .nickname("운영팀")
                .status(PostStatus.DELETED)
                .createdAt(LocalDateTime.now())
                .build();

        when(adminPostListRepository.findById(1L)).thenReturn(Optional.of(post));

        System.out.println("복구 전 상태: " + post.getStatus());

        adminPostListService.restorePost(1L);

        assertEquals(PostStatus.ACTIVE, post.getStatus());

        System.out.println("복구 후 상태: " + post.getStatus());
        System.out.println();
    }

}
