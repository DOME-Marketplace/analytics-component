package com.dome.matomo_analytics.service.tmforum;

import com.dome.matomo_analytics.config.TmForumConfigValues;
import com.dome.matomo_analytics.model.tmforum.ProductSpecification;
import com.dome.matomo_analytics.repository.tmforum.ProductSpecificationRepository;
import com.dome.matomo_analytics.repository.tmforum.RelatedPartyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSpecificationService {


    private final ProductSpecificationRepository productSpecificationRepository;
    private final TmForumConfigValues tmForumConfigValues;
    private final TmForumDataFetcherService dataFetcher;

    private final RelatedPartyRepository relatedPartyRepository;

    public ProductSpecificationService(ProductSpecificationRepository productSpecificationRepository,
                                       TmForumConfigValues tmForumConfigValues,
                                       TmForumDataFetcherService dataFetcher,
                                       RelatedPartyRepository relatedPartyRepository) {

        this.productSpecificationRepository = productSpecificationRepository;
        this.tmForumConfigValues = tmForumConfigValues;
        this.dataFetcher = dataFetcher;
        this.relatedPartyRepository = relatedPartyRepository;
    }


    public void fetchDataAndSave() {
        int limit = 100;
        long offset = productSpecificationRepository.count();

        while (true) {
            String url = tmForumConfigValues.getHost() + "/product-catalog/productSpecification?offset=" + offset + "&limit=" + limit + "&lifecycleStatus=Launched";
            List<ProductSpecification> productSpecifications = dataFetcher.fetchData(url, tmForumConfigValues.getApi_key(), ProductSpecification.class);

            if (productSpecifications == null || productSpecifications.isEmpty()) {
                break;
            }

            productSpecifications.forEach(spec -> {
                if (spec.getRelatedParty() == null) {
                    spec.setRelatedParty(new ArrayList<>());
                }
            });

            productSpecificationRepository.saveAll(productSpecifications);

            offset += productSpecifications.size();
        }
    }


}
