package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductAnalyticsInfo {
    @Id
    private String id;
    private String catalogName;
    private String catalogId;
    private String productOfferingName;
    private String productOfferingId;
    private String categoryName;
    private String startDateTime;
    private String lifecycleStatus;

}
