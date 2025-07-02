package com.zell.dev.shorten_api.repository;

import com.zell.dev.common_lib.repository.ShortUrlRepository;
import com.zell.dev.common_service.config.repository.ShardRepositoryRouter;
import com.zell.dev.shorten_api.repository.shard0.Shard0ShortUrlRepository;
import com.zell.dev.shorten_api.repository.shard1.Shard1ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShardRepositoryRouterImpl implements ShardRepositoryRouter {
    private final Shard0ShortUrlRepository shard0Repo;
    private final Shard1ShortUrlRepository shard1Repo;

    @Override
    public ShortUrlRepository getRepositoryForShard(int shardId) {
        return switch (shardId) {
                case 0 -> shard0Repo;
                case 1 -> shard1Repo;
                default -> throw new IllegalArgumentException("Invalid shard ID: " + shardId);
        };
    }
}
