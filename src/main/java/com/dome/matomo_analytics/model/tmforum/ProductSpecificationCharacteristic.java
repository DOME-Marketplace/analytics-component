package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductSpecificationCharacteristic {
    @Id
    private String id;
}
