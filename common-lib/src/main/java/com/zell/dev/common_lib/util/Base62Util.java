package com.zell.dev.common_lib.util;

public class Base62Util {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = BASE62.length();

    public String encode(long value){
        StringBuilder sb = new StringBuilder();
        while(value > 0){
            sb.append(BASE62.charAt((int)(value % BASE)));
            value /= BASE;
        }

        return sb.toString();
    }

    public long decode(String str){
        long result = 0;
        for(char c: str.toCharArray()){
            result = (result * BASE) + BASE62.indexOf(c);
        }

        return result;
    }

}
