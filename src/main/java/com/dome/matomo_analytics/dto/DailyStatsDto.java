package com.dome.matomo_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyStatsDto {

    private String label;
    private String date;
    private String visits;
    private String uniqueVisits;
}
