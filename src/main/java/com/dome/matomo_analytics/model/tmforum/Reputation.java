package com.dome.matomo_analytics.model.tmforum;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Reputation {
    @Id
    @JsonProperty(value = "_id")
    private String id;
    private int avg;
    private int count;
}
