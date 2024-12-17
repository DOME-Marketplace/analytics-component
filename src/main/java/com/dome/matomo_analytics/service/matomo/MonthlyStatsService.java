package com.dome.matomo_analytics.service.matomo;

import com.dome.matomo_analytics.dto.MonthlyStatsDto;
import com.dome.matomo_analytics.model.matomo.MonthlyStats;
import com.dome.matomo_analytics.repository.matomo.MonthlyStatsRepository;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthlyStatsService {

    private final MonthlyStatsRepository monthlyStatsRepository;
    private final MatomoService matomoService;

    @Autowired
    public MonthlyStatsService(MonthlyStatsRepository monthlyStatsRepository,
                               MatomoService matomoService) {
        this.monthlyStatsRepository = monthlyStatsRepository;
        this.matomoService = matomoService;
    }

    public void fetchMonthlyDataForLastMonths(Integer months) {
        LocalDate startDate = LocalDate.now().minusMonths(months);
        LocalDate endDate = LocalDate.now();

        while (startDate.isBefore(endDate)) {
            fetchDataForSingleMonth(startDate);
            startDate = startDate.plusMonths(1);
        }
    }

    public void fetchDataForSingleMonth(LocalDate date) {
        String period = "month";
        String method = "Actions.getPageUrls";
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        ResponseEntity<String> response = matomoService.buildMatomoEndpoint(method, period, formattedDate);
        JsonArray data = JsonParser.parseString(response.getBody()).getAsJsonArray();
        parseAndSave(data, formattedDate, method, period);
    }


    public void parseAndSave(JsonArray data, String date, String method, String period) {
        for (JsonElement pageElement : data) {
            MonthlyStats monthlyStats = new MonthlyStats();
            monthlyStats.setDate(date);
            monthlyStats.setPeriod(period);
            monthlyStats.setApiMethod(method);
            monthlyStats.setData(pageElement.getAsJsonObject().toString());
            monthlyStatsRepository.save(monthlyStats);
        }
    }

    public List<MonthlyStatsDto> getAllMonthlyStats() {
        return monthlyStatsRepository.findAllSortedByDate().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MonthlyStatsDto convertToDto(MonthlyStats monthlyStats) {
        String jsonData = monthlyStats.getData();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        MonthlyStatsDto monthlyStatsDto = new MonthlyStatsDto();

        monthlyStatsDto.setLabel(jsonObject.get("label").getAsString());
        //String url = jsonObject.get("segment").getAsString();
        //String decodedUrl = MatomoPageUtil.decodePageUrl(url);
        monthlyStatsDto.setDate(monthlyStats.getDate());
        monthlyStatsDto.setVisits(jsonObject.get("nb_visits").getAsString());
        monthlyStatsDto.setUniqueVisits(jsonObject.get("nb_uniq_visitors").getAsString());
        monthlyStatsDto.setNbActions(jsonObject.get("nb_actions").getAsString());
        monthlyStatsDto.setNbHits(jsonObject.get("nb_hits").getAsString());
        monthlyStatsDto.setExitNbVisits(jsonObject.get("exit_nb_visits").getAsString());
        monthlyStatsDto.setExitNbUniqVisitors(jsonObject.get("exit_nb_uniq_visitors").getAsString());
        monthlyStatsDto.setAvgTimeOnPage(jsonObject.get("avg_time_on_page").getAsString());
        monthlyStatsDto.setExitRate(jsonObject.get("exit_rate").getAsString());
        monthlyStatsDto.setBounceRate(jsonObject.get("bounce_rate").getAsString());

        return monthlyStatsDto;
    }

}
