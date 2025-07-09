package com.zell.dev.shorten_api.service.impl;


import com.zell.dev.common_lib.model.ShortUrl;
import com.zell.dev.common_lib.repository.ShortUrlRepository;
import com.zell.dev.common_lib.util.Base62Util;
import com.zell.dev.common_lib.id_generator.IDGenerator;
import com.zell.dev.common_service.config.resolver.HashBasedShardResolver;
import com.zell.dev.shorten_api.dto.UrlShortenRequestDto;
import com.zell.dev.shorten_api.dto.UrlShortenResponseDto;
import com.zell.dev.shorten_api.mapper.ShortUrlMapper;
import com.zell.dev.shorten_api.repository.ShardRepositoryRouterImpl;
import com.zell.dev.shorten_api.service.IUrlShortenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlShortenService implements IUrlShortenService {
    private final IDGenerator idGenerator;
    private final HashBasedShardResolver hashBasedShardResolver;
    private final ShardRepositoryRouterImpl shardRepositoryRouter;

    @Override
    public UrlShortenResponseDto shortenUrl(UrlShortenRequestDto requestDto) {
        // base64- > Generate aI
        long newId = idGenerator.nextId();
        String encodedId = Base62Util.encode(newId);

        LocalDateTime currentDateTime = LocalDateTime.now();

        ShortUrl newShortUrl = new ShortUrl(encodedId, requestDto.getUrl(), currentDateTime, null, currentDateTime.plusMonths(1), true);

        ShortUrlRepository selectedRepo = shardRepositoryRouter.getRepositoryForShard(hashBasedShardResolver.resolveShard(encodedId));
        selectedRepo.save(newShortUrl);

        return ShortUrlMapper.mapToUrlShortenRequestDto(newShortUrl);
    }
}
