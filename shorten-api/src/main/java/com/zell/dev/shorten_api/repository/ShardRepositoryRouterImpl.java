package com.zell.dev.shorten_api.repository;

import com.zell.dev.common_lib.repository.ShortUrlRepository;
import com.zell.dev.common_service.config.repository.ShardRepositoryRouter;
import com.zell.dev.shorten_api.repository.shard0.Shard0ShortUrlRepository;
import com.zell.dev.shorten_api.repository.shard0.replica.Shard0ReadOnlyShortUrlRepository;
import com.zell.dev.shorten_api.repository.shard1.Shard1ShortUrlRepository;
import com.zell.dev.shorten_api.repository.shard1.replica.Shard1ReadOnlyShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShardRepositoryRouterImpl implements ShardRepositoryRouter {
    private final Shard0ShortUrlRepository shard0Repo;
    private final Shard1ShortUrlRepository shard1Repo;
    private final Shard0ReadOnlyShortUrlRepository shard0ReadOnlyRepo;
    private final Shard1ReadOnlyShortUrlRepository shard1ReadOnlyRepo;

    @Override
    public ShortUrlRepository getReadRepositoryForShard(int shardId) {
        return switch (shardId) {
                case 0 -> shard0ReadOnlyRepo;
                case 1 -> shard1ReadOnlyRepo;
                default -> throw new IllegalArgumentException("Invalid shard ID: " + shardId);
        };
    }

    @Override
    public ShortUrlRepository getWriteRepositoryForShard(int shardId) {
        return switch (shardId) {
            case 0 -> shard0Repo;
            case 1 -> shard1Repo;
            default -> throw new IllegalArgumentException("Invalid shard ID: " + shardId);
        };
    }
}
