package com.dome.matomo_analytics.controller.matomo;

import com.dome.matomo_analytics.service.matomo.DailyStatsService;
import com.dome.matomo_analytics.service.matomo.MonthlyStatsService;
import com.dome.matomo_analytics.service.matomo.PageService;
import com.dome.matomo_analytics.service.matomo.ReferrersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matomo")
public class FetchMatomoDataController {

    private final PageService pageService;

    private final DailyStatsService dailyStatsService;
    private final MonthlyStatsService monthlyStatsService;
    private final ReferrersService referrersService;

    public FetchMatomoDataController(PageService pageService,
                                     DailyStatsService dailyStatsService,
                                     MonthlyStatsService monthlyStatsService,
                                     ReferrersService referrersService) {
        this.pageService = pageService;
        this.dailyStatsService = dailyStatsService;
        this.monthlyStatsService = monthlyStatsService;
        this.referrersService = referrersService;
    }

    @GetMapping(value = "/fetch/pages")
    public void fetchPages() {
        pageService.fetchAndSavePageDataForLastMonth();
    }

    @PostMapping(value = "/fetch/daily-stats")
    public void fetchDailyStats() {
        dailyStatsService.fetchDailyDataForYear(2024);
    }

    @PostMapping(value = "/fetch/monthly-stats")
    public void fetchMonthlyStats() {
        monthlyStatsService.fetchMonthlyDataForLastMonths(6);
    }

    @PostMapping(value = "/fetch/referrers")
    public void fetchReferrers() {
        referrersService.fetchMonthlyDataForLastMonths(6);
    }

}
