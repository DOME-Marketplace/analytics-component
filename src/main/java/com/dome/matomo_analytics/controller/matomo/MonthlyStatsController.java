package com.dome.matomo_analytics.controller.matomo;

import com.dome.matomo_analytics.dto.MonthlyStatsDto;
import com.dome.matomo_analytics.service.matomo.MonthlyStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(value = "/matomo")
public class MonthlyStatsController {
    private final MonthlyStatsService monthlyStatsService;

    @Autowired
    public MonthlyStatsController(MonthlyStatsService monthlyStatsService) {
        this.monthlyStatsService = monthlyStatsService;
    }

    @GetMapping(value = "/get-monthly-data")
    public List<MonthlyStatsDto> getMonthlyData() {
        return monthlyStatsService.getAllMonthlyStats();

    }
}
