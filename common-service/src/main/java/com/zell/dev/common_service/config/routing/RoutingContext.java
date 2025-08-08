package com.zell.dev.common_service.config.routing;

import static com.zell.dev.common_lib.constants.GlobalConstant.*;

public class RoutingContext {
    private static final ThreadLocal<String> OPERATION_TYPE = new ThreadLocal<>();
    private static final ThreadLocal<Integer> SHARD_ID = new ThreadLocal<>();

    public static void setShardId(int shardId) {
        SHARD_ID.set(shardId);
    }

    public static int getShardId() {
        return SHARD_ID.get();
    }

    public static void setReadOnly() {
        setOperationType(OPERATION_READ);
    }

    public static void setWriteOnly() {
        setOperationType(OPERATION_WRITE);
    }

    public static String getRoutingKey() {
        if(SHARD_ID.get() == null || OPERATION_TYPE.get() == null) {
            System.out.println("RoutingContext: NULL values - using default datasource");
            return null;
        }

        String routingKey = generateLookUpKey(SHARD_ID.get(), OPERATION_TYPE.get());
        System.out.println("RoutingContext: Using routing key = " + routingKey);

        return generateLookUpKey(SHARD_ID.get(), OPERATION_TYPE.get());
    }

    public static void clear() {
        OPERATION_TYPE.remove();
        SHARD_ID.remove();
    }

    public static void setOperationType(String type) {
        OPERATION_TYPE.set(type);
    }

    public static boolean isReadOnly() {
        return OPERATION_READ.equals(OPERATION_TYPE.get());
    }


}
