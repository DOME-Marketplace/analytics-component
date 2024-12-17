package com.dome.matomo_analytics.service.tmforum;

import com.dome.matomo_analytics.config.TmForumConfigValues;
import com.dome.matomo_analytics.model.tmforum.Category;
import com.dome.matomo_analytics.repository.tmforum.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TmForumConfigValues tmForumConfigValues;
    private final TmForumDataFetcherService dataFetcher;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           TmForumConfigValues tmForumConfigValues,
                           TmForumDataFetcherService dataFetcher) {
        this.categoryRepository = categoryRepository;
        this.tmForumConfigValues = tmForumConfigValues;
        this.dataFetcher = dataFetcher;
    }

    public void fetchDataAndSave() {
        int limit = 100;
        long offset = categoryRepository.count();
        String lifecycleStatus = "Launched";

        while (true) {
            String url = tmForumConfigValues.getHost() + "/product-catalog/category?offset=" + offset + "&limit=" + limit + "&lifecycleStatus=" + lifecycleStatus;
            List<Category> categories = dataFetcher.fetchData(url, tmForumConfigValues.getApi_key(), Category.class);

            if (categories == null || categories.isEmpty()) {
                break;
            }
            categoryRepository.saveAll(categories);
            offset += categories.size();
        }
    }

}
