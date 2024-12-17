package com.dome.matomo_analytics.controller.tmforum;

import com.dome.matomo_analytics.service.tmforum.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tmForum")
public class FetchTMForumDataController {

    private final CategoryService categoryService;
    private final CatalogService catalogService;
    private final ProductOfferingService productOfferingService;
    private final ProductOfferingPriceService productOfferingPriceService;

    private final ProductSpecificationService productSpecificationService;

    private final ReputationService reputationService;

    private final ProductAnalyticsInfoService productAnalyticsInfoService;

    @Autowired
    public FetchTMForumDataController(CategoryService categoryService,
                                      CatalogService catalogService,
                                      ProductOfferingService productOfferingService,
                                      ProductOfferingPriceService productOfferingPriceService,
                                      ProductSpecificationService productSpecificationService,
                                      ReputationService reputationService,
                                      ProductAnalyticsInfoService productAnalyticsInfoService) {
        this.categoryService = categoryService;
        this.catalogService = catalogService;
        this.productOfferingService = productOfferingService;
        this.productOfferingPriceService = productOfferingPriceService;
        this.productSpecificationService = productSpecificationService;
        this.reputationService = reputationService;
        this.productAnalyticsInfoService = productAnalyticsInfoService;
    }


    @PostMapping(value = "/fetchCategoryData")
    public void fetchCategoryData() {
        categoryService.fetchDataAndSave();
    }

    @PostMapping(value = "/fetchCatalogData")
    public void fetchCatalogData() {
        catalogService.fetchDataAndSave();
    }

    @PostMapping(value = "/fetchProductOfferingData")
    public void fetchProductOfferingData() {
        productOfferingService.fetchDataAndSave();
    }

    @PostMapping(value = "/fetchProductOfferingPriceData")
    public void fetchProductOfferingPriceData() {
        productOfferingPriceService.fetchDataAndSave();
    }

    @PostMapping(value = "/fetchProductSpecificationData")
    public void fetchProductSpecificationData() {
        productSpecificationService.fetchDataAndSave();
    }

    @PostMapping(value = "/setCategoryPath")
    public void getPath() {
        productOfferingService.getCategoryPathOfProduct();
    }

    @PostMapping(value = "/fetchReputationData")
    public void fetchReputationData() {
        reputationService.fetchDataAndSave();
    }

    @PostMapping(value = "/saveAnalyticsInfo")
    public void saveAnalyticsInfo() {
        productAnalyticsInfoService.saveProductAnalyticsInfo();
    }

}
