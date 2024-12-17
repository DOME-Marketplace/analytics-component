package com.dome.matomo_analytics.repository.tmforum;

import com.dome.matomo_analytics.model.tmforum.ProductCategoryPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductCategoryPathRepository extends JpaRepository<ProductCategoryPath, UUID> {
    Optional<ProductCategoryPath> findByProductName(String productName);

    boolean existsProductCategoryPathByProductNameAndPath(String productName, String path);

}
