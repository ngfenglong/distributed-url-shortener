package com.zell.dev.shorten_api.mapper;

import com.zell.dev.common_lib.model.ShortUrl;
import com.zell.dev.shorten_api.dto.UrlShortenRequestDto;
import com.zell.dev.shorten_api.dto.UrlShortenResponseDto;

public class ShortUrlMapper {
//    public static ShortUrl mapToShortUrlMapper(){
//
//    }

    public static UrlShortenResponseDto mapToUrlShortenRequestDto(ShortUrl shortUrl){
        return new UrlShortenResponseDto(
                shortUrl.getId(),
                "/" + shortUrl.getId(),
                shortUrl.getOriginalUrl(),
                shortUrl.getCreatedAt(),
                shortUrl.getExpiryAt()
        );
    }

}
