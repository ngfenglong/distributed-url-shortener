package com.zell.dev.common_service.config.resolver;

public interface ShardResolver {
    int resolveShard(String shardKey);
}
