package com.zell.dev.common_service.config.routing;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ShardRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("ShardRoutingDataSource: Routing to = " + RoutingContext.getRoutingKey());
        return RoutingContext.getRoutingKey();
    }
}