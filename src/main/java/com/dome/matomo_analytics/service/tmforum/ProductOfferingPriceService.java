package com.dome.matomo_analytics.service.tmforum;

import com.dome.matomo_analytics.model.tmforum.ProductOfferingPrice;
import com.dome.matomo_analytics.model.tmforum.ProductOfferingPriceRef;
import com.dome.matomo_analytics.repository.tmforum.ProductOfferingPriceRefRepository;
import com.dome.matomo_analytics.repository.tmforum.ProductOfferingPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductOfferingPriceService {

    private final ProductOfferingPriceRepository productOfferingPriceRepository;
    private final ProductOfferingPriceRefRepository productOfferingPriceRefRepository;
    private final RestTemplate restTemplate = new RestTemplate();


    @Autowired
    public ProductOfferingPriceService(ProductOfferingPriceRepository productOfferingPriceRepository,
                                       ProductOfferingPriceRefRepository productOfferingPriceRefRepository) {
        this.productOfferingPriceRepository = productOfferingPriceRepository;
        this.productOfferingPriceRefRepository = productOfferingPriceRefRepository;
    }

    public void fetchDataAndSave() {
        List<ProductOfferingPriceRef> priceRefList = productOfferingPriceRefRepository.findAll();
        for (ProductOfferingPriceRef priceRef : priceRefList) {

            String url = "https://dome-marketplace.org/catalog/productOfferingPrice/" + priceRef.getId();
            ResponseEntity<ProductOfferingPrice> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            ProductOfferingPrice productPrice = responseEntity.getBody();
            if (productPrice != null) {
                productOfferingPriceRepository.save(productPrice);
            }



        }
    }


}
