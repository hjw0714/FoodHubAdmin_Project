package com.application.foodhubAdmin.stats;

import static org.assertj.core.api.Assertions.assertThat;

import com.application.foodhubAdmin.domain.Stats;
import com.application.foodhubAdmin.repository.StatsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class StatsRepositoryTest {

    @Autowired
    private StatsRepository statsRepository;

    @BeforeEach
    void setUpTestData() {
        for (int i = 1; i <= 16; i++) {
            Stats stats = new Stats();
            stats.setCategoryId(i);
            stats.setStatDate(LocalDate.now());
            stats.setStatCnt(100L);
            stats.setCreatedAt(LocalDateTime.now());
            stats.setUpdatedAt(LocalDateTime.now());
            statsRepository.save(stats);
        }
    }

    @Test
    @Order(1)
    @DisplayName("유저 통계 불러오기 테스트 - 신규 유저 통계")
    void getNewUsersStatsTest() {
        var result = statsRepository.getYearlyNewUserCnt();
        assertThat(result).isNotEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("유저 통계 불러오기 테스트 - 탈퇴한 유저 통계")
    void getDeletedUsersStatsTest() {
        var result = statsRepository.getYearlyDeleteUserCnt();
        assertThat(result).isNotEmpty();
    }

    @Test
    @Order(3)
    @DisplayName("유저 통계 불러오기 테스트 - 현재 유저 통계")
    void getTotalUsersStatsTest() {
        var result = statsRepository.getYearlyTotalUserCnt();
        assertThat(result).isNotEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("게시글 통계 불러오기 테스트 - 전체 게시글 통계")
    void getTotalPostsStatsTest() {
        var result = statsRepository.getYearlyTotalPostCnt();
        assertThat(result).isNotEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("게시글 통계 불러오기 테스트 - 카테고리별 게시글 통계")
    void getCategoryPostStatsTest() {
        Integer categoryId = 7;
        var result = statsRepository.getYearlyCategoryPostCnt(categoryId);
        assertThat(result).isNotEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("댓글 통계 불러오기 테스트 - 전체 댓글 통계")
    void getTotalCommentsStatsTest() {
        var result = statsRepository.getYearlyTotalCommentsCnt();
        assertThat(result).isNotEmpty();
    }

    @Test
    @Order(7)
    @DisplayName("방문자 수 통계 불러오기 테스트")
    void getVisitorLogStatsTest() {
        var result = statsRepository.getYearlyVisitorLog();
        assertThat(result).isNotEmpty();
    }

    @Test
    @Order(8)
    @DisplayName("게시글 신고수 통계 불러오기 테스트")
    void getReportPostStatsTest() {
        var result = statsRepository.getPostReportCnt();
        assertThat(result).isNotEmpty();
    }

    @Test
    @Order(9)
    @DisplayName("게시글 신고수 통계 불러오기 테스트")
    void getReportCommentStatsTest() {
        var result = statsRepository.getCommentReportCnt();
        assertThat(result).isNotEmpty();
    }




}
