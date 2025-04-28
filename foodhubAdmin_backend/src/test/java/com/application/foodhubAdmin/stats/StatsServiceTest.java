package com.application.foodhubAdmin.stats;

import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.dto.response.comments.MonthlyCommentReportResponse;
import com.application.foodhubAdmin.dto.response.comments.YearlyTotalCommentsCntResponse;
import com.application.foodhubAdmin.dto.response.post.MonthlyPostReportResponse;
import com.application.foodhubAdmin.dto.response.post.PostReportResponse;
import com.application.foodhubAdmin.dto.response.post.YearlyCategoryPostCntResponse;
import com.application.foodhubAdmin.dto.response.post.YearlyTotalPostCntResponse;
import com.application.foodhubAdmin.dto.response.user.YearlyDeleteUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.YearlyNewUserCntResponse;
import com.application.foodhubAdmin.dto.response.user.YearlyTotalUserCntResponse;
import com.application.foodhubAdmin.dto.response.visitorLog.YearlyVisitorLogResponse;
import com.application.foodhubAdmin.repository.StatsRepository;
import com.application.foodhubAdmin.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatsServiceTest {

    @Mock
    private StatsRepository statsRepository;

    @InjectMocks
    private UserMsService userMsService;

    @InjectMocks
    private PostMsService postMsService;

    @InjectMocks
    private CommentsService commentsService;

    @InjectMocks
    private VisitorLogMsService visitorLogMsService;

    @InjectMocks
    private PostReportService postReportService;

    @InjectMocks
    private CommentReportService commentReportService;

    private List<Stats> statsList;

    @BeforeEach
    void setUpTestData() {
        statsList = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            Stats stats = new Stats();
            stats.setCategoryId(i);
            stats.setStatDate(LocalDate.now());
            stats.setStatCnt(100L);
            stats.setCreatedAt(LocalDateTime.now());
            stats.setUpdatedAt(LocalDateTime.now());
            statsList.add(stats);
        }
        statsRepository.saveAll(statsList);
    }

    @Test @Order(1) @DisplayName("신규 유저 통계 조회")
    void testNewGetUserStats() {
        List<YearlyNewUserCntResponse> mockResponse = new ArrayList<>();
        for (Stats stats : statsList) {
            if (stats.getCategoryId() == 1) {
                mockResponse.add(new YearlyNewUserCntResponse(stats.getStatDate().getYear(), stats.getStatCnt()));
            }
        }

        when(statsRepository.getYearlyNewUserCnt()).thenReturn(mockResponse);

        List<YearlyNewUserCntResponse> result = userMsService.getYearlyNewUserCnt();

        assertThat(result).isNotEmpty();

        verify(statsRepository, times(1)).getYearlyNewUserCnt();
    }

    @Test @Order(2) @DisplayName("탈퇴 유저 통계 조회")
    void testDeletedGetUserStats() {
        List<YearlyDeleteUserCntResponse> mockResponse = new ArrayList<>();
        for (Stats stats : statsList) {
            if (stats.getCategoryId() == 2) {
                mockResponse.add(new YearlyDeleteUserCntResponse(stats.getStatDate().getYear(), stats.getStatCnt()));
            }
        }
        when(statsRepository.getYearlyDeleteUserCnt()).thenReturn(mockResponse);
        List<YearlyDeleteUserCntResponse> result = userMsService.getYearlyDeleteUserCnt();
        assertThat(result).isNotEmpty();
        verify(statsRepository, times(1)).getYearlyDeleteUserCnt();
    }

    @Test @Order(3) @DisplayName("현재 활동 유저 통계 조회")
    void testTotalGetUserStats() {
        List<YearlyTotalUserCntResponse> mockResponse = new ArrayList<>();
        for (Stats stats : statsList) {
            if (stats.getCategoryId() == 3) {
                mockResponse.add(new YearlyTotalUserCntResponse(stats.getStatDate().getYear(), stats.getStatCnt()));
            }
        }
        when(statsRepository.getYearlyTotalUserCnt()).thenReturn(mockResponse);
        List<YearlyTotalUserCntResponse> result = userMsService.getYearlyTotalUserCnt();
        assertThat(result).isNotEmpty();
        verify(statsRepository, times(1)).getYearlyTotalUserCnt();
    }

    @Test @Order(4) @DisplayName("총 게시글 통계 조회")
    void testTotalPostStats() {
        List<YearlyTotalPostCntResponse> mockResponse = new ArrayList<>();
        for (Stats stats : statsList) {
            if (stats.getCategoryId() == 4) {
                mockResponse.add(new YearlyTotalPostCntResponse(stats.getStatDate().getYear(), stats.getStatCnt()));
            }
        }
        when(statsRepository.getYearlyTotalPostCnt()).thenReturn(mockResponse);
        List<YearlyTotalPostCntResponse> result = postMsService.getYearlyTotalPostCnt();
        assertThat(result).isNotEmpty();
        verify(statsRepository, times(1)).getYearlyTotalPostCnt();
    }

    @Test @Order(5) @DisplayName("카테고리별 게시글 통계 조회")
    void testCategoryPostStats() {
        Integer categoryId = 5;

        List<YearlyCategoryPostCntResponse> mockResponse = new ArrayList<>();
        for (Stats stats : statsList) {
            if (stats.getCategoryId().equals(categoryId)) {
                mockResponse.add(new YearlyCategoryPostCntResponse(
                        stats.getStatDate().getYear(),
                        stats.getStatCnt() ,
                        stats.getCategoryId() - 5
                ));
            }
        }
        when(statsRepository.getYearlyCategoryPostCnt(categoryId)).thenReturn(mockResponse);
        List<YearlyCategoryPostCntResponse> result = postMsService.getYearlyCategoryPostCnt(categoryId);
        assertThat(result).isNotEmpty();
        verify(statsRepository, times(1)).getYearlyCategoryPostCnt(categoryId);
    }

    @Test @Order(6) @DisplayName("댓글 통계 조회")
    void testTotalCommentStats() {
        List<YearlyTotalCommentsCntResponse> mockResponse = new ArrayList<>();
        for (Stats stats : statsList) {
            if (stats.getCategoryId() == 13) {
                mockResponse.add(new YearlyTotalCommentsCntResponse(stats.getStatDate().getYear(), stats.getStatCnt()));
            }
        }
        when(statsRepository.getYearlyTotalCommentsCnt()).thenReturn(mockResponse);
        List<YearlyTotalCommentsCntResponse> result = commentsService.getYearlyTotalCommentsCnt();
        assertThat(result).isNotEmpty();
        verify(statsRepository, times(1)).getYearlyTotalCommentsCnt();
    }

    @Test @Order(7) @DisplayName("방문자 통계 조회")
    void testVisitorLogStats() {
        List<YearlyVisitorLogResponse> mockResponse = new ArrayList<>();
        for (Stats stats : statsList) {
            if (stats.getCategoryId() == 14) {
                mockResponse.add(new YearlyVisitorLogResponse(stats.getStatDate().getYear(), stats.getStatCnt()));
            }
        }
        when(statsRepository.getYearlyVisitorLog()).thenReturn(mockResponse);
        List<YearlyVisitorLogResponse> result = visitorLogMsService.getYearlyVisitorLog();
        assertThat(result).isNotEmpty();
        verify(statsRepository, times(1)).getYearlyVisitorLog();
    }

    @Test @Order(8) @DisplayName("게시글 신고수 조회")
    void testReportPostStats() {
        List<MonthlyPostReportResponse> mockResponse = new ArrayList<>();
        for (Stats stats : statsList) {
            if (stats.getCategoryId() == 15) {
                mockResponse.add(new MonthlyPostReportResponse(stats.getStatDate().getMonthValue(), stats.getStatCnt()));
            }
        }
        when(statsRepository.getPostReportCnt()).thenReturn(mockResponse);
        List<MonthlyPostReportResponse> result = postReportService.getPostReportCnt();
        assertThat(result).isNotEmpty();
        verify(statsRepository, times(1)).getPostReportCnt();
    }

    @Test @Order(9) @DisplayName("댓글 신고수 조회")
    void testReportCommentStats() {
        List<MonthlyCommentReportResponse> mockResponse = new ArrayList<>();
        for (Stats stats : statsList) {
            if (stats.getCategoryId() == 16) {
                mockResponse.add(new MonthlyCommentReportResponse(stats.getStatDate().getMonthValue(), stats.getStatCnt()));
            }
        }
        when(statsRepository.getCommentReportCnt()).thenReturn(mockResponse);
        List<MonthlyCommentReportResponse> result = commentReportService.getCommentReportCnt();
        assertThat(result).isNotEmpty();
        verify(statsRepository, times(1)).getCommentReportCnt();
    }







}
