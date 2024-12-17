package com.dome.matomo_analytics.model.tmforum;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Embeddable
@Data
public class TimePeriod {
    private String startDateTime;
}
