package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Data
public class ProductCategoryPath {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String productName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String path;

    private String startDateTime;

    private String lastUpdate;
    private String productOfferingId;
}
