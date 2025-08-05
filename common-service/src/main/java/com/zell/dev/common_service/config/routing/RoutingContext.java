package com.zell.dev.common_service.config.routing;

public class RoutingContext {
    private static final String READ = "READ";
    private static final String WRITE = "WRITE";

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setReadOnly() {
        CONTEXT.set(READ);
    }

    public static void setWriteOnly() {
        CONTEXT.set(WRITE);
    }

    public static String getRoutingKey() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static boolean isReadOnly() {
        return READ.equals(CONTEXT.get());
    }


}
