package com.dome.matomo_analytics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "matomo")
@Configuration
@Data
public class MatomoConfigValues {
    private String matomoTokenAuth;
    private String matomoSiteId;
    private String matomoApiUrl;
}
