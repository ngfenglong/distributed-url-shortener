package com.zell.dev.common_service.config;

public interface ShardResolver {
    int resolveShard(String shardKey);
}
