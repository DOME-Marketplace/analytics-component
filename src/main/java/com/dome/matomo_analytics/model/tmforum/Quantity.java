package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Quantity {
    private float amount;
    private String units;
}
