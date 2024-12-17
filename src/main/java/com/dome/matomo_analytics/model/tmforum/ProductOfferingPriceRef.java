package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class ProductOfferingPriceRef {
    @Id
    private String id;
    private String href;
    private String name;
    private String referredType;
}