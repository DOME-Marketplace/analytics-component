package com.dome.matomo_analytics.service.matomo;

import com.dome.matomo_analytics.model.matomo.Referrers;
import com.dome.matomo_analytics.repository.matomo.ReferrersRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ReferrersService {
    private final ReferrersRepository referrersRepository;

    private final MatomoService matomoService;


    public ReferrersService(ReferrersRepository referrersRepository,
                            MatomoService matomoService) {
        this.referrersRepository = referrersRepository;
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
        String method = "Referrers.get";
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        ResponseEntity<String> response = matomoService.buildMatomoEndpoint(method, period, formattedDate);
        JsonElement data = JsonParser.parseString(response.getBody());
        parseAndSave(data, formattedDate, method, period);
    }


    public void parseAndSave(JsonElement data, String date, String method, String period) {
        Referrers referrers = new Referrers();
        referrers.setDate(date);
        referrers.setPeriod(period);
        referrers.setApiMethod(method);
        referrers.setData(data.getAsJsonObject().toString());
        referrersRepository.save(referrers);

    }
}
