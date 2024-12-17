package com.dome.matomo_analytics.service.matomo;

import com.dome.matomo_analytics.config.MatomoConfigValues;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatomoService {


    private final MatomoConfigValues matomoConfigValues;

    @Autowired
    public MatomoService(MatomoConfigValues matomoConfigValues) {
        this.matomoConfigValues = matomoConfigValues;
    }

    public ResponseEntity<String> buildMatomoEndpoint(@RequestParam String method,
                                                      @RequestParam String period,
                                                      @RequestParam String date) {

        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("module", "API");
        bodyParams.put("idSite", matomoConfigValues.getMatomoSiteId());
        bodyParams.put("method", method);
        bodyParams.put("format", "json");
        bodyParams.put("token_auth", matomoConfigValues.getMatomoTokenAuth());
        bodyParams.put("period", period);
        bodyParams.put("date", date);
        bodyParams.put("flat","1");
        bodyParams.put("expanded","1");


        ResponseEntity<String> response = getMatomoResponse(bodyParams);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response;
        } else {
            return ResponseEntity.badRequest().body("Invalid request or server error.");
        }
    }

    private ResponseEntity<String> getMatomoResponse(Map<String, String> bodyParams) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.set("User-Agent", "Spring RestTemplate");

        String requestBody = bodyParams.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate.exchange(
                    matomoConfigValues.getMatomoApiUrl(),
                    HttpMethod.POST,
                    entity,
                    String.class
            );
        } catch (RestClientException e) {
            System.err.println("Error while making request to Matomo API: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getLastMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(calendar.getTime());
    }


}
