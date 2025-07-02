package com.zell.dev.common_service.config.repository;

import com.zell.dev.common_lib.repository.ShortUrlRepository;

public interface ShardRepositoryRouter {
    ShortUrlRepository getRepositoryForShard(int shardId);
}
