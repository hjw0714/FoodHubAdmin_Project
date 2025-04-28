package com.application.foodhubAdmin.postReport;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.domain.PostReport;
import com.application.foodhubAdmin.domain.PostReportStatus;
import com.application.foodhubAdmin.domain.PostStatus;
import com.application.foodhubAdmin.dto.response.post.PostReportResponse;
import com.application.foodhubAdmin.repository.PostMsRepository;
import com.application.foodhubAdmin.repository.PostReportRepository;
import com.application.foodhubAdmin.service.PostReportService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostReportServiceTest {

    @Mock
    private PostReportRepository postReportRepository;

    @Mock
    private PostMsRepository postMsRepository;

    @InjectMocks
    private PostReportService postReportService;

    @BeforeEach
    void setUp() { // 테스트 시작 전에 자동 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("신고 게시글 조회")
    void getAllPostReports() {
        System.out.println("1. 게시글 신고 테스트");
        // Post 객체 먼저 생성
        Post post = Post.builder()
                .id(100L)
                .title("테스트 게시글 제목")
                .status(PostStatus.ACTIVE)
                .build();

        PostReport postReport = PostReport.builder()
                .id(1L)
                .userId("admin")
                .content("테스트 신고 내용")
                .status(PostReportStatus.PENDING)
                .post(post)
                .createdAt(LocalDateTime.now())
                .build();

        PostReport postReport2 = PostReport.builder()
                .id(2L)
                .userId("admin")
                .content("테스트 신고 내용2")
                .status(PostReportStatus.REVIEWED)
                .post(post)
                .createdAt(LocalDateTime.now())
                .build();

        PostReport postReport3 = PostReport.builder()
                .id(3L)
                .userId("admin")
                .content("테스트 신고 내용3")
                .status(PostReportStatus.RESOLVED)
                .post(post)
                .createdAt(LocalDateTime.now())
                .build();

        List<PostReport> reportList = List.of(postReport, postReport2, postReport3);

        when(postReportRepository.findAll()).thenReturn(reportList);

        List<PostReportResponse> result = postReportService.getAllPostReports();

        assertEquals(3, result.size());
        assertEquals("admin", result.get(0).getUserId());
        assertEquals("admin", result.get(1).getUserId());
        assertEquals("admin", result.get(2).getUserId());

        result.forEach(r -> {
            System.out.println("게시글 ID: " + r.getPostId());
            System.out.println("게시글 제목: " + r.getPostTitle());
            System.out.println("유저 ID: " + r.getUserId());
            System.out.println("신고 사유: " + r.getContent());
            System.out.println("신고 처리 상태: " + r.getPostReportStatus());
            System.out.println("게시글 처리 상태: " + r.getPostStatus());
            System.out.println("신고 일자: " + r.getCreatedAt());
            System.out.println("==============================");
        });
        System.out.println();
    }

    @Test
    @Order(2)
    @DisplayName("게시글 신고 상태 변경")
    void changeReportStatus() {
        System.out.println("2. 게시글 신고 상태 변경 테스트");
        Post post = Post.builder()
                .id(100L)
                .title("테스트 게시글 제목")
                .status(PostStatus.ACTIVE)
                .build();

        PostReport postReport = PostReport.builder()
                .id(1L)
                .userId("admin")
                .content("테스트 신고 내용")
                .status(PostReportStatus.REVIEWED)
                .post(post)
                .createdAt(LocalDateTime.now())
                .build();

        when(postReportRepository.findById(1L)).thenReturn(java.util.Optional.of(postReport));
        System.out.println("상태 변경 전 : " + postReport.getStatus());

        postReportService.changeReportStatus(1L , "RESOLVED");

        assertEquals(PostReportStatus.RESOLVED , postReport.getStatus());
        System.out.println("상태 변경 후: " + postReport.getStatus());
        System.out.println();
    }

    @Test
    @Order(3)
    @DisplayName("게시글 삭제")
    void deletePost() {
        System.out.println("3. 게시글 삭제 테스트");
        Post post = Post.builder()
                .id(100L)
                .title("테스트 게시글 제목")
                .status(PostStatus.ACTIVE)
                .build();

        when(postMsRepository.findById(100L)).thenReturn(java.util.Optional.of(post));

        System.out.println("게시글 상태 변경 전 : " + post.getStatus());

        postReportService.deletePost(100L);

        assertEquals(PostStatus.DELETED , post.getStatus());
        System.out.println("게시글 상태 변경 후 : " + post.getStatus());
        System.out.println();
    }

    @Test
    @Order(4)
    @DisplayName("게시글 복구")
    void restorePost() {
        System.out.println("4. 게시글 복구 테스트");
        Post post = Post.builder()
                .id(100L)
                .title("테스트 게시글 제목")
                .status(PostStatus.DELETED)
                .build();

        when(postMsRepository.findById(100L)).thenReturn(java.util.Optional.of(post));

        System.out.println("게시글 상태 변경 전 : " + post.getStatus());

        postReportService.restorePost(100L);

        assertEquals(PostStatus.ACTIVE , post.getStatus());
        System.out.println("게시글 상태 변경 후 : " + post.getStatus());
    }

}
