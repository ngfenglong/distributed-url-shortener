package com.zell.dev.shorten_api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UrlShortenRequestDto {
    private String url;
}
