package com.application.foodhubAdmin.commentReport;

import com.application.foodhubAdmin.domain.CommentReport;
import com.application.foodhubAdmin.domain.CommentReportStatus;
import com.application.foodhubAdmin.domain.CommentStatus;
import com.application.foodhubAdmin.domain.Comments;
import com.application.foodhubAdmin.dto.response.comments.CommentReportResponse;
import com.application.foodhubAdmin.repository.CommentReportRepository;
import com.application.foodhubAdmin.repository.CommentsRepository;
import com.application.foodhubAdmin.service.CommentReportService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentReportServiceTest {

    @Mock
    private CommentReportRepository commentReportRepository;

    @Mock
    private CommentsRepository commentsRepository;

    @InjectMocks
    private CommentReportService commentReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("댓글 신고 목록 조회")
    void getAllCommentReports() {
        System.out.println("1. 댓글 신고 목록 조회 테스트");

        Comments comments = Comments.builder()
                .id(100L)
                .content("테스트 댓글")
                .status(CommentStatus.VISIBLE)
                .build();

        CommentReport report1 = CommentReport.builder()
                .userId("user1")
                .content("댓글 신고 내용1")
                .status(CommentReportStatus.PENDING)
                .comments(comments)
                .createdAt(LocalDateTime.now())
                .build();

        CommentReport report2 = CommentReport.builder()
                .userId("user2")
                .content("댓글 신고 내용2")
                .status(CommentReportStatus.REVIEWED)
                .comments(comments)
                .createdAt(LocalDateTime.now())
                .build();

        CommentReport report3 = CommentReport.builder()
                .userId("user3")
                .content("댓글 신고 내용3")
                .status(CommentReportStatus.RESOLVED)
                .comments(comments)
                .createdAt(LocalDateTime.now())
                .build();

        List<CommentReport> reportList = List.of(report1, report2, report3);

        when(commentReportRepository.findAll()).thenReturn(reportList);

        List<CommentReportResponse> result = commentReportService.getAllCommentReports();

        assertEquals(3, result.size());
        assertEquals("user1", result.get(0).getUserId());
        assertEquals("user2", result.get(1).getUserId());
        assertEquals("user3", result.get(2).getUserId());

        result.forEach(r -> {
            System.out.println("댓글 ID: " + r.getCommentId());
            System.out.println("댓글 내용: " + r.getCommentContent());
            System.out.println("신고자 ID: " + r.getUserId());
            System.out.println("신고 내용: " + r.getContent());
            System.out.println("신고 처리 상태: " + r.getCommentReportStatus());
            System.out.println("댓글 상태: " + r.getCommentStatus());
            System.out.println("신고 일자: " + r.getCreatedAt());
            System.out.println("==============================");
        });

        System.out.println();
    }
    @Test
    @Order(2)
    @DisplayName("댓글 신고 상태 변경")
    void changeReportStatus() {
        System.out.println("2. 댓글 신고 상태 변경 테스트");

        Comments comment = Comments.builder()
                .id(200L)
                .content("테스트 댓글")
                .status(CommentStatus.VISIBLE)
                .build();

        CommentReport report = CommentReport.builder()
                .id(1L)
                .userId("user1")
                .content("댓글 신고 내용")
                .status(CommentReportStatus.PENDING)
                .comments(comment)
                .createdAt(LocalDateTime.now())
                .build();

        when(commentReportRepository.findById(1L)).thenReturn(java.util.Optional.of(report));

        System.out.println("상태 변경 전: " + report.getStatus());

        commentReportService.changeReportStatus(1L, "RESOLVED");

        assertEquals(CommentReportStatus.RESOLVED, report.getStatus());

        System.out.println("상태 변경 후: " + report.getStatus());
        System.out.println();
    }

    @Test
    @Order(3)
    @DisplayName("댓글 삭제")
    void deleteComment() {
        System.out.println("3. 댓글 삭제 테스트");

        Comments comment = Comments.builder()
                .id(200L)
                .content("테스트 댓글")
                .status(CommentStatus.VISIBLE)
                .build();

        when(commentsRepository.findById(200L)).thenReturn(java.util.Optional.of(comment));

        System.out.println("댓글 상태 변경 전: " + comment.getStatus());

        commentReportService.deleteComment(200L);

        assertEquals(CommentStatus.DELETED, comment.getStatus());

        System.out.println("댓글 상태 변경 후: " + comment.getStatus());
        System.out.println();
    }

    @Test
    @Order(4)
    @DisplayName("댓글 복구")
    void restoreComment() {
        System.out.println("4. 댓글 복구 테스트");

        Comments comment = Comments.builder()
                .id(200L)
                .content("테스트 댓글")
                .status(CommentStatus.DELETED)
                .build();

        when(commentsRepository.findById(200L)).thenReturn(java.util.Optional.of(comment));

        System.out.println("댓글 상태 변경 전: " + comment.getStatus());

        commentReportService.restoreComment(200L);

        assertEquals(CommentStatus.VISIBLE, comment.getStatus());

        System.out.println("댓글 상태 변경 후: " + comment.getStatus());
    }


}
