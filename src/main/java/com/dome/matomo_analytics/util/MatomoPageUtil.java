package com.dome.matomo_analytics.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class MatomoPageUtil {
    public static String decodePageUrl(String segment) {
        try {
            String urlPart = segment.replaceFirst("pageUrl=+?", "");

            if (urlPart.startsWith("^")) {
                urlPart = urlPart.substring(1);
            }
            if (urlPart.startsWith("=")) {
                urlPart = urlPart.substring(1);
            }

            String decodedOnce = URLDecoder.decode(urlPart, StandardCharsets.UTF_8.name());
            return URLDecoder.decode(decodedOnce, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return null;
        }
    }
}
