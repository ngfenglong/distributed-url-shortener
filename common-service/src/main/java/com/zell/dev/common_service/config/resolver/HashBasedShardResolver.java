package com.zell.dev.common_service.config.resolver;

import com.zell.dev.common_lib.util.HashUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HashBasedShardResolver implements ShardResolver{
    @Value("${sharding.count}")
    private int shardCount;

    @Override
    public int resolveShard(String shardKey) {
        return HashUtil.hash(shardKey) % shardCount;
    }
}
