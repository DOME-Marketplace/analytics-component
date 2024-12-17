package com.dome.matomo_analytics.repository.matomo;

import com.dome.matomo_analytics.model.matomo.DailyStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DailyStatsRepository extends JpaRepository<DailyStats, UUID> {
    List<DailyStats> findAll();
}
