package com.dome.matomo_analytics.controller.matomo;

import com.dome.matomo_analytics.dto.DailyStatsDto;
import com.dome.matomo_analytics.service.matomo.DailyStatsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(value = "/matomo")
public class DailyStatsController {
    private final DailyStatsService dailyStatsService;

    public DailyStatsController(DailyStatsService dailyStatsService) {
        this.dailyStatsService = dailyStatsService;
    }

    @GetMapping("/get-daily-visits")
    public List<DailyStatsDto> getDailyVisits(){
        return dailyStatsService.getAllDailyStats();
    }



}
