package com.zell.dev.common_service.config;

import com.zell.dev.common_lib.util.HashUtil;
import org.springframework.beans.factory.annotation.Value;

public class HashBasedShardResolver implements ShardResolver{
    @Value("${shortener.shard.count}")
    private int shardCount;

    @Override
    public int resolveShard(String shardKey) {
        return HashUtil.hash(shardKey) % shardCount;
    }
}
