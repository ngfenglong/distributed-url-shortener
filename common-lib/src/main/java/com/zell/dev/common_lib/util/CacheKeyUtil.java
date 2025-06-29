package com.zell.dev.common_lib.util;

public class CacheKeyUtil {
    private static final String PREFIX = "shorturl:";

    public String keyFor(String shortId){
        return PREFIX + shortId;
    }
}
