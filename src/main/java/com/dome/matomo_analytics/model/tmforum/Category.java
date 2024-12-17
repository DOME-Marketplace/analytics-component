package com.dome.matomo_analytics.model.tmforum;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    private String href;
    private String description;
    @JsonProperty("isRoot")
    private boolean isRoot;
    private String lastUpdate;
    private String lifecycleStatus;
    private String name;

    private String parentId;
//    @ManyToMany
//    private List<ProductOfferingRef> productOffering;

    @ManyToMany
    private List<CategoryRef> subCategory;

    //private String parentId;
    private String version;

    @Embedded
    private TimePeriod validFor;
}
