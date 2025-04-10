package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {
    Optional<Stats> findByCategoryIdAndStatDate(Integer categoryId, LocalDate statDate);
}
