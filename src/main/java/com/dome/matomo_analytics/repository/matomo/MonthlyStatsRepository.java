package com.dome.matomo_analytics.repository.matomo;

import com.dome.matomo_analytics.model.matomo.MonthlyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MonthlyStatsRepository extends JpaRepository<MonthlyStats, UUID> {

    @Query("SELECT ms FROM MonthlyStats ms ORDER BY ms.date ASC")
    List<MonthlyStats> findAllSortedByDate();

}
