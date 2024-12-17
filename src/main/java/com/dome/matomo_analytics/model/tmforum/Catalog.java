package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Catalog {
    @Id
    private String id;

    private String href;
    @Column(columnDefinition = "text")
    private String description;
    private String lifecycleStatus;
    @Column(columnDefinition = "text")
    private String name;
    @Embedded
    private TimePeriod validFor;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<RelatedParty> relatedParty;
    @OneToMany
    private List<CategoryRef> category;
}
