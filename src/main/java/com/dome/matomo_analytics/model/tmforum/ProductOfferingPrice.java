package com.dome.matomo_analytics.model.tmforum;

import com.dome.matomo_analytics.model.tmforum.Quantity;
import com.dome.matomo_analytics.model.tmforum.TimePeriod;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ProductOfferingPrice {
    @Id
    private String id;

    private String href;
    private String name;
    private String description;
    private String version;
    private String priceType;
    private String recurringChargePeriodType;
    private LocalDateTime lastUpdate;
    private String lifecycleStatus;
    private float percentage;

    @Embedded
    private TimePeriod validFor;

    @Embedded
    private Quantity unitOfMeasure;

    @Embedded
    private Money price;

//    @OneToMany
//    private List<BundledProductOfferingPriceRelationship> bundledPopRelationship;
//
//    @OneToMany
//    private List<ProductOfferingPriceRelationship> popRelationship;
//
//    @OneToMany
//    private List<ProductSpecificationCharacteristicValueUse> prodSpecCharValueUse;
//
//    @OneToMany
//    private List<ProductOfferingTerm> productOfferingTerm;
//
//    @OneToMany
//    private List<PlaceRef> place;
//
//    @OneToMany
//    private List<ConstraintRef> constraint;
//
//    @OneToMany
//    private List<PricingLogicAlgorithm> pricingLogicAlgorithm;
//
//    @OneToMany
//    private List<TaxItem> tax;
}
