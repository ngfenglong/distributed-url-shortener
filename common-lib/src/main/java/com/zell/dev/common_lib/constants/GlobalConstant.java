package com.zell.dev.common_lib.constants;

public class GlobalConstant {
    public static final String OPERATION_WRITE = "WRITE";
    public static final String OPERATION_READ = "READ";

    public static String generateLookUpKey(int shardId, String type){
        return shardId + "_" + type;
    }
}

