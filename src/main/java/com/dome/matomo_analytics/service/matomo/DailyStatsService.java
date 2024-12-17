package com.dome.matomo_analytics.service.matomo;

import com.dome.matomo_analytics.dto.DailyStatsDto;
import com.dome.matomo_analytics.model.matomo.DailyStats;
import com.dome.matomo_analytics.repository.matomo.DailyStatsRepository;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyStatsService {

    private final MatomoService matomoService;

    private final DailyStatsRepository dailyStatsRepository;

    @Autowired
    public DailyStatsService(MatomoService matomoService, DailyStatsRepository dailyStatsRepository) {
        this.matomoService = matomoService;
        this.dailyStatsRepository = dailyStatsRepository;
    }

    public void fetchDataForSingleDay(LocalDate date) {
        String period = "day";
        String method = "Actions.getPageUrls";
        String formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        ResponseEntity<String> response = matomoService.buildMatomoEndpoint(method, period, formattedDate);
        JsonArray data = JsonParser.parseString(response.getBody()).getAsJsonArray();
        parseAndSave(data, formattedDate, method, period);

    }

    public void parseAndSave(JsonArray data, String date, String method, String period) {
        for (JsonElement pageElement : data) {
            DailyStats dailyStats = new DailyStats();
            dailyStats.setDate(date);
            dailyStats.setPeriod(period);
            dailyStats.setApiMethod(method);
            dailyStats.setData(pageElement.getAsJsonObject().toString());
            dailyStatsRepository.save(dailyStats);
        }
    }
    public void fetchDailyDataForYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        while (!startDate.isAfter(endDate)) {
            fetchDataForSingleDay(startDate);
            startDate = startDate.plusDays(1);
        }
    }

    public void fetchDailyDataForLast3Months() {
        LocalDate startDate = LocalDate.now().minusDays(180);
        LocalDate endDate = LocalDate.now();

        while (startDate.isBefore(endDate)) {
            fetchDataForSingleDay(startDate);
            startDate = startDate.plusDays(1);
        }
    }

    public List<DailyStatsDto> getAllDailyStats() {
        return dailyStatsRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DailyStatsDto convertToDto(DailyStats dailyStats) {
        String jsonData = dailyStats.getData();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);

        String label = jsonObject.get("label").getAsString();
        //String url = jsonObject.get("segment").getAsString();
        //String decodedUrl = MatomoPageUtil.decodePageUrl(url);
        String date = dailyStats.getDate();
        String visits = jsonObject.get("visits").getAsString();
        String uniqueVisits = jsonObject.get("UniqueVisits").getAsString();

        return new DailyStatsDto(label, date, visits, uniqueVisits);
    }


}
