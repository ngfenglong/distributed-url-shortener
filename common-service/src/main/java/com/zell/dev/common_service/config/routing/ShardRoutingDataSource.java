package com.zell.dev.common_service.config.routing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class ShardRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("ShardRoutingDataSource: Routing to = {}", RoutingContext.getRoutingKey());
        return RoutingContext.getRoutingKey();
    }
}