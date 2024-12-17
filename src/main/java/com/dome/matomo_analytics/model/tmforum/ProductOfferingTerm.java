package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductOfferingTerm {

    @Id
    private String id;

    private String description;
    private String name;

    @Embedded
    private Quantity duration;

    @Embedded
    private TimePeriod validFor;

}
