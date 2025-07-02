package com.zell.dev.shorten_api.controller;

import com.zell.dev.shorten_api.dto.UrlShortenRequestDto;
import com.zell.dev.shorten_api.dto.UrlShortenResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UrlShortenController {

    @PostMapping("/shorten")
    public ResponseEntity<UrlShortenResponseDto> shortenUrl(@RequestBody UrlShortenRequestDto requestDto){


        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
