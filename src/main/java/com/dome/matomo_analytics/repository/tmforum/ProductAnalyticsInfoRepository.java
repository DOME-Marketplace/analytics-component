package com.dome.matomo_analytics.repository.tmforum;

import com.dome.matomo_analytics.model.tmforum.ProductAnalyticsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductAnalyticsInfoRepository extends JpaRepository<ProductAnalyticsInfo, UUID> {
}
