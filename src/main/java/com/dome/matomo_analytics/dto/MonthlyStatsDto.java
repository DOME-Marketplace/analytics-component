package com.dome.matomo_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyStatsDto {
    private String label;
    private String date;
    private String visits;
    private String uniqueVisits;
    private String nbActions;
    private String nbHits;
    private String exitNbVisits;
    private String exitNbUniqVisitors;
    private String avgTimeOnPage;
    private String exitRate;
    private String bounceRate;
}
