package com.zell.dev.redirect_api.service.impl;

import com.zell.dev.common_lib.model.ShortUrl;
import com.zell.dev.common_lib.repository.ShortUrlRepository;
import com.zell.dev.common_service.config.annotation.ReadOnly;
import com.zell.dev.common_service.config.resolver.HashBasedShardResolver;
import com.zell.dev.common_service.config.routing.RoutingContext;
import com.zell.dev.redirect_api.exception.ResourceNotFoundException;
import com.zell.dev.redirect_api.service.IUrlRedirectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlRedirectService implements IUrlRedirectService {
    private final RedisTemplate<String, String> redisTemplate;
    private final HashBasedShardResolver hashBasedShardResolver;
    private final ShortUrlRepository repository;

    @Override
    @ReadOnly
    public String resolveOriginalUrl(String shortId) {
        String cacheUrl = redisTemplate.opsForValue().get(shortId);

        if(cacheUrl != null) {
            log.info("Taking from Cache" + cacheUrl);
            return cacheUrl;
        }

        int shardId = hashBasedShardResolver.resolveShard(shortId);
        RoutingContext.setShardId(shardId);

        ShortUrl shortUrl = repository.findById(shortId).orElseThrow(() -> new ResourceNotFoundException(ShortUrl.class.getName(), shortId));

        log.info("Querying from repo" + shortUrl.getOriginalUrl());
        redisTemplate.opsForValue().set(shortId, shortUrl.getOriginalUrl(), Duration.ofHours(1));

        return shortUrl.getOriginalUrl();
    }
}
