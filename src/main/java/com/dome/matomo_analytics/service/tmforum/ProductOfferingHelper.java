package com.dome.matomo_analytics.service.tmforum;

import com.dome.matomo_analytics.model.tmforum.Category;
import com.dome.matomo_analytics.model.tmforum.CategoryRef;
import com.dome.matomo_analytics.model.tmforum.ProductCategoryPath;
import com.dome.matomo_analytics.model.tmforum.ProductOffering;
import com.dome.matomo_analytics.repository.tmforum.CategoryRepository;
import com.dome.matomo_analytics.repository.tmforum.ProductCategoryPathRepository;
import com.dome.matomo_analytics.repository.tmforum.ProductOfferingRepository;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferingHelper {
    private final CategoryRepository categoryRepository;
    private final ProductCategoryPathRepository productCategoryPathRepository;

    private final ProductOfferingRepository productOfferingRepository;

    public ProductOfferingHelper(CategoryRepository categoryRepository,
                                 ProductCategoryPathRepository productCategoryPathRepository,
                                 ProductOfferingRepository productOfferingRepository) {
        this.categoryRepository = categoryRepository;
        this.productCategoryPathRepository = productCategoryPathRepository;
        this.productOfferingRepository = productOfferingRepository;
    }

    public void findAndSaveAllPaths() {
        List<ProductOffering> poList = productOfferingRepository.findAll();
        for (ProductOffering p : poList) {
            List<String> paths = buildCategoryPaths(p.getCategory());
            if (paths != null) {
                for (String path : paths) {
                    savePath(p.getName(), p.getLastUpdate(), p.getValidFor().getStartDateTime(), path, p.getId());
                }
            }
        }
    }

    private void savePath(String productName, String lastUpdate, String startDateTime, String path, String productId) {
        String[] categories = path.split(" > ");
        JsonObject jsonPath = new JsonObject();
        for (int i = 0; i < categories.length; i++) {
            jsonPath.addProperty("c" + (i + 1), categories[i].trim());
        }
        String jsonPathString = jsonPath.toString();
        boolean pathExists = productCategoryPathRepository.existsProductCategoryPathByProductNameAndPath(productName, jsonPathString);

        if (!pathExists) {
            ProductCategoryPath newProductCategoryPath = new ProductCategoryPath();
            newProductCategoryPath.setLastUpdate(lastUpdate);
            newProductCategoryPath.setStartDateTime(startDateTime);
            newProductCategoryPath.setProductName(productName);
            newProductCategoryPath.setPath(jsonPathString);
            newProductCategoryPath.setProductOfferingId(productId);
            productCategoryPathRepository.save(newProductCategoryPath);
        }
    }

    private List<String> buildCategoryPaths(List<CategoryRef> categoryRefs) {
        if (categoryRefs == null || categoryRefs.isEmpty()) {
            return null;
        }

        List<String> allPaths = new ArrayList<>();
        for (CategoryRef categoryRef : categoryRefs) {
            categoryRepository.findById(categoryRef.getId()).ifPresent(category -> {
                List<String> paths = getCategoryPaths(category, "");
                allPaths.addAll(paths);
            });
        }
        return allPaths;
    }

    private List<String> getCategoryPaths(Category category, String currentPath) {
        String path = category.getName() + (currentPath.isEmpty() ? "" : " > " + currentPath);
        List<String> paths = new ArrayList<>();

        if (category.getParentId() != null) {
            Optional<Category> parentCategory = categoryRepository.findById(category.getParentId());
            if (parentCategory.isPresent()) {
                paths.addAll(getCategoryPaths(parentCategory.get(), path));
            } else {
                paths.add(path);
            }
        } else {
            paths.add(path);
        }
        return paths;
    }

}
