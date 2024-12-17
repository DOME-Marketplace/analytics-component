package com.dome.matomo_analytics.service.matomo;

import com.dome.matomo_analytics.model.matomo.Page;
import com.dome.matomo_analytics.repository.matomo.PageRepository;
import com.dome.matomo_analytics.util.MatomoPageUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PageService {

    private final PageRepository pageRepository;
    private final MatomoService matomoService;

    @Autowired
    public PageService(PageRepository pageRepository, MatomoService matomoService) {
        this.pageRepository = pageRepository;
        this.matomoService = matomoService;
    }

    //@Scheduled(cron = "0 0 0 1 * ?") // At 00:00 on the first day of every month
    public void fetchAndSavePageDataForLastMonth() {
        String period = "month";
        String method = "Actions.getPageUrls";
        ResponseEntity<String> response = matomoService.buildMatomoEndpoint(method, period, matomoService.getLastMonthDate());
        JsonArray pageData = JsonParser.parseString(response.getBody()).getAsJsonArray();
        for (JsonElement pageElement : pageData) {
            JsonObject page = pageElement.getAsJsonObject();
            this.parseAndSavePageData(page);
        }
        if (!pageRepository.findByLabel("domain").isPresent()) {
            Page page = new Page();
            page.setUrl("domain");
            page.setLabel("domain");
            pageRepository.save(page);
        }
    }

    public void parseAndSavePageData(JsonObject pageData) {
        String label = pageData.get("label").getAsString();
        Page page = pageRepository.findByLabel(label)
                .orElse(new Page());
        page.setLabel(label);

        String url = null;
        if (pageData.has("url")) {
            url = pageData.get("url").getAsString();
        } else if (pageData.has("segment")) {
            String segment = pageData.get("segment").getAsString();
            url = MatomoPageUtil.decodePageUrl(segment);
        }
        page.setUrl(url);
        pageRepository.save(page);
    }


}
