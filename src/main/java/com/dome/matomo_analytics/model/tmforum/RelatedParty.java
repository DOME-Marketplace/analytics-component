package com.dome.matomo_analytics.model.tmforum;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RelatedParty {
    @Id
    private String id;
    private String href;
    private String name;
    private String role;
    @JsonProperty("@referredType")
    private String referredType;
}
