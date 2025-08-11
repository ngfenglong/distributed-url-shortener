package com.zell.dev.common_lib.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlValidatorUtil {
    public static boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) return false;

        if (!url.matches("^(http|https)://.*")) {
            url = "https://" + url;
        }

        try {
            URL u = new URL(url);
            return u.getHost() != null && u.getProtocol().matches("https?");
        } catch (MalformedURLException e) {
            return false;
        }
    }
}