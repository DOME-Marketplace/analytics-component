package com.dome.matomo_analytics.service.tmforum;

import com.dome.matomo_analytics.config.TmForumConfigValues;
import com.dome.matomo_analytics.model.tmforum.Catalog;
import com.dome.matomo_analytics.repository.tmforum.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final TmForumConfigValues tmForumConfigValues;
    private final TmForumDataFetcherService dataFetcher;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository,
                          TmForumConfigValues tmForumConfigValues,
                          TmForumDataFetcherService dataFetcher) {
        this.catalogRepository = catalogRepository;
        this.tmForumConfigValues = tmForumConfigValues;
        this.dataFetcher = dataFetcher;
    }

    public void fetchDataAndSave() {
        int limit = 100;
        long offset = catalogRepository.count();
        String lifecycleStatus = "Launched";

        while (true) {
            String url = tmForumConfigValues.getHost() + "/product-catalog/catalog?offset=" + offset + "&limit=" + limit + "&lifecycleStatus=" + lifecycleStatus;
            List<Catalog> catalogs = dataFetcher.fetchData(url, tmForumConfigValues.getApi_key(), Catalog.class);
            if (catalogs == null || catalogs.isEmpty()) {
                break;
            }
            catalogRepository.saveAll(catalogs);
            offset += catalogs.size();
        }
    }

}
