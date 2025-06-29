package com.zell.dev.common_lib.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashUtil {
    public static int hash(String key){
        return Hashing.murmur3_32_fixed()
                .hashString(key, StandardCharsets.UTF_8)
                .asInt() & 0x7fffffff; // make it positive
    }
}
