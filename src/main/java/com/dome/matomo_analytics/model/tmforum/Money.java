package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Money {
    private String unit;
    private float value;
}
