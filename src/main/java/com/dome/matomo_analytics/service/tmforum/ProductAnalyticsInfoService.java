package com.dome.matomo_analytics.service.tmforum;

import com.dome.matomo_analytics.config.TmForumConfigValues;
import com.dome.matomo_analytics.model.tmforum.Catalog;
import com.dome.matomo_analytics.model.tmforum.ProductAnalyticsInfo;
import com.dome.matomo_analytics.model.tmforum.ProductOffering;
import com.dome.matomo_analytics.repository.tmforum.CatalogRepository;
import com.dome.matomo_analytics.repository.tmforum.ProductAnalyticsInfoRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAnalyticsInfoService {
    private final CatalogRepository catalogRepository;
    private final ProductAnalyticsInfoRepository productAnalyticsInfoRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final TmForumConfigValues tmForumConfigValues;

    public ProductAnalyticsInfoService(CatalogRepository catalogRepository,
                                       ProductAnalyticsInfoRepository productAnalyticsInfoRepository, TmForumConfigValues tmForumConfigValues) {
        this.catalogRepository = catalogRepository;
        this.productAnalyticsInfoRepository = productAnalyticsInfoRepository;
        this.tmForumConfigValues = tmForumConfigValues;
    }

    public void saveProductAnalyticsInfo() {
        List<Catalog> catalogs = catalogRepository.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", tmForumConfigValues.getApi_key());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        for (Catalog catalog : catalogs) {
            String url = tmForumConfigValues.getHost() + "/product-catalog/catalog/"
                    + catalog.getId() + "/productOffering";

            ResponseEntity<List<ProductOffering>> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            if (response.getBody() != null) {
                List<ProductAnalyticsInfo> entries = response.getBody().stream()
                        .map(po -> mapToCatalogProductOffering(catalog, po))
                        .collect(Collectors.toList());

                productAnalyticsInfoRepository.saveAll(entries);
            }
        }

    }

    private ProductAnalyticsInfo mapToCatalogProductOffering(Catalog catalog, ProductOffering productOffering) {
        ProductAnalyticsInfo entry = new ProductAnalyticsInfo();
        entry.setCatalogName(catalog.getName());
        entry.setCatalogId(catalog.getId());
        entry.setProductOfferingName(productOffering.getName());
        entry.setProductOfferingId(productOffering.getId());
        entry.setCategoryName(productOffering.getCategory() != null && !productOffering.getCategory().isEmpty()
                ? productOffering.getCategory().get(0).getName() : null);
        entry.setStartDateTime(productOffering.getValidFor() != null
                ? productOffering.getValidFor().getStartDateTime().toString() : null);
        entry.setLifecycleStatus(productOffering.getLifecycleStatus());
        return entry;
    }


}
