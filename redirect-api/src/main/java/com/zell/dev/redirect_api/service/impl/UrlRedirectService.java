package com.zell.dev.redirect_api.service.impl;

import com.zell.dev.common_lib.model.ShortUrl;
import com.zell.dev.common_lib.repository.ShortUrlRepository;
import com.zell.dev.common_lib.util.Base62Util;
import com.zell.dev.common_service.config.repository.ShardRepositoryRouter;
import com.zell.dev.common_service.config.resolver.HashBasedShardResolver;
import com.zell.dev.redirect_api.exception.ResourceNotFoundException;
import com.zell.dev.redirect_api.service.IUrlRedirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlRedirectService implements IUrlRedirectService {
    private final RedisTemplate<String, String> redisTemplate;
    private final HashBasedShardResolver hashBasedShardResolver;
    private final ShardRepositoryRouter shardRepositoryRouter;


    @Override
    public String resolveOriginalUrl(String shortId) {
        String cacheUrl = redisTemplate.opsForValue().get(shortId);

        if(cacheUrl != null) {
            return cacheUrl;
        }

        int shardId = hashBasedShardResolver.resolveShard(shortId);
        ShortUrlRepository repo = shardRepositoryRouter.getRepositoryForShard(shardId);

        ShortUrl shortUrl = repo.findById(shortId).orElseThrow(() -> new ResourceNotFoundException(ShortUrl.class.getName(), shortId));

        return shortUrl.getOriginalUrl();
    }
}
