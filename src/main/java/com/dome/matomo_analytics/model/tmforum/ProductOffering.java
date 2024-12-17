package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ProductOffering {
    @Id
    private String id;

    private String href;
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean isBundle;
    private boolean isSellable;
    private String lastUpdate;
    private String lifecycleStatus;
    private String name;
    private String statusReason;
    private String version;
    @Embedded
    private TimePeriod validFor;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<CategoryRef> category;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductOfferingPriceRef> productOfferingPrice;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<ProductOfferingTerm> productOfferingTerm;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductSpecificationRef productSpecification;
}
