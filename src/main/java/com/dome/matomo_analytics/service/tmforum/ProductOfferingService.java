package com.dome.matomo_analytics.service.tmforum;

import com.dome.matomo_analytics.config.TmForumConfigValues;
import com.dome.matomo_analytics.model.tmforum.ProductOffering;
import com.dome.matomo_analytics.repository.tmforum.ProductOfferingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOfferingService {
    private final ProductOfferingRepository productOfferingRepository;
    private final TmForumConfigValues tmForumConfigValues;
    private final TmForumDataFetcherService dataFetcher;
    private final ProductOfferingHelper offeringHelper;

    public ProductOfferingService(ProductOfferingRepository productOfferingRepository,
                                  TmForumConfigValues tmForumConfigValues,
                                  TmForumDataFetcherService dataFetcher,
                                  ProductOfferingHelper offeringHelper) {
        this.productOfferingRepository = productOfferingRepository;
        this.tmForumConfigValues = tmForumConfigValues;
        this.dataFetcher = dataFetcher;
        this.offeringHelper = offeringHelper;
    }

    public void fetchDataAndSave() {
        int limit = 100;
        Long offset = productOfferingRepository.count();
        String lifecycleStatus = "Launched";
        String url = tmForumConfigValues.getHost() + "/product-catalog/productOffering?offset=" + offset + "&limit=" + limit + "&lifecycleStatus=" + lifecycleStatus;
        List<ProductOffering> productOfferings = dataFetcher.fetchData(url, tmForumConfigValues.getApi_key(), ProductOffering.class);

        if (productOfferings != null) {
            productOfferingRepository.saveAll(productOfferings);
        }
    }

    public void getCategoryPathOfProduct() {
        offeringHelper.findAndSaveAllPaths();
    }


}
