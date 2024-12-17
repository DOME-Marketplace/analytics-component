package com.dome.matomo_analytics.service.tmforum;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Service
public class TmForumDataFetcherService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();


    public <T> List<T> fetchData(String url, String apiKey, Class<T> clazz) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("api-key", apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);


            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String jsonResponse = response.getBody();


            Type listType = TypeToken.getParameterized(List.class, clazz).getType();


            return gson.fromJson(jsonResponse, listType);

        } catch (Exception e) {

            System.err.println("Error fetching data from " + url + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
