package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ProductSpecification {
    @Id
    private String id;

    @Column(columnDefinition = "text")
    private String brand;
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "text")
    private String href;

    private boolean isBundle;
    @Column(columnDefinition = "text")
    private String lastUpdate;
    @Column(columnDefinition = "text")
    private String lifecycleStatus;
    @Column(columnDefinition = "text")
    private String name;
    @Column(columnDefinition = "text")
    private String productNumber;
    @Column(columnDefinition = "text")
    private String version;

    @Embedded
    private TimePeriod validFor;

    @ManyToMany
    private List<RelatedParty> relatedParty;

    //attachment

//    @OneToMany
//    private List<ProductSpecificationCharacteristic> productSpecCharacteristic;
}
