package com.dome.matomo_analytics.service.tmforum;

import com.dome.matomo_analytics.model.tmforum.Catalog;
import com.dome.matomo_analytics.model.tmforum.Reputation;
import com.dome.matomo_analytics.repository.tmforum.ReputationRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReputationService {
    private final ReputationRepository reputationRepository;

    private final RestTemplate restTemplate = new RestTemplate();


    public ReputationService(ReputationRepository reputationRepository) {
        this.reputationRepository = reputationRepository;
    }

    public void fetchDataAndSave() {
        String url = "https://dome-marketplace.org/REPManagement/reputation";

        ResponseEntity<List<Reputation>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<Reputation> reputationList = responseEntity.getBody();

        if (reputationList != null) {
            for (Reputation r : reputationList) {
                reputationRepository.save(r);
            }
        }
    }



}
