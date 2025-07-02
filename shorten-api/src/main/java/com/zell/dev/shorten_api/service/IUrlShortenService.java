package com.zell.dev.shorten_api.service;

import com.zell.dev.shorten_api.dto.UrlShortenRequestDto;
import com.zell.dev.shorten_api.dto.UrlShortenResponseDto;

public interface IUrlShortenService {
    UrlShortenResponseDto shortenUrl(UrlShortenRequestDto requestDto);
}
