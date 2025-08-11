package com.zell.dev.shorten_api.service.impl;


import com.zell.dev.common_lib.model.ShortUrl;
import com.zell.dev.common_lib.repository.ShortUrlRepository;
import com.zell.dev.common_lib.util.Base62Util;
import com.zell.dev.common_lib.id_generator.IDGenerator;
import com.zell.dev.common_service.config.annotation.WriteOnly;
import com.zell.dev.common_service.config.resolver.HashBasedShardResolver;
import com.zell.dev.common_service.config.routing.RoutingContext;
import com.zell.dev.shorten_api.dto.UrlShortenRequestDto;
import com.zell.dev.shorten_api.dto.UrlShortenResponseDto;
import com.zell.dev.shorten_api.mapper.ShortUrlMapper;
import com.zell.dev.shorten_api.service.IUrlShortenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.InvalidUrlException;

import java.time.LocalDateTime;

import static com.zell.dev.common_lib.util.UrlValidatorUtil.isValidUrl;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlShortenService implements IUrlShortenService {
    private final IDGenerator idGenerator;
    private final HashBasedShardResolver hashBasedShardResolver;
    private final ShortUrlRepository repository;

    @Override
    @WriteOnly
    public UrlShortenResponseDto shortenUrl(UrlShortenRequestDto requestDto) {
        if (!isValidUrl(requestDto.getUrl())) {
            throw new InvalidUrlException("Invalid URL format");
        }
        long newId = idGenerator.nextId();
        String encodedId = Base62Util.encode(newId);

        int shardId = hashBasedShardResolver.resolveShard(encodedId);
        log.info("shardId - {}", shardId);
        RoutingContext.setShardId(shardId);

        LocalDateTime currentDateTime = LocalDateTime.now();
        ShortUrl newShortUrl = new ShortUrl(encodedId, requestDto.getUrl(), currentDateTime, null, currentDateTime.plusMonths(1), true);

        repository.save(newShortUrl);

        return ShortUrlMapper.mapToUrlShortenRequestDto(newShortUrl);
    }
}
