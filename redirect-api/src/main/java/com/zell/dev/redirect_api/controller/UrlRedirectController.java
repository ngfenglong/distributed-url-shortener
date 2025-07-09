package com.zell.dev.redirect_api.controller;

import com.zell.dev.redirect_api.service.IUrlRedirectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/r")
@AllArgsConstructor
public class UrlRedirectController {
    private IUrlRedirectService iUrlRedirectService;

    @GetMapping("/{shortId}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortId) {
        String originalUrl = iUrlRedirectService.resolveOriginalUrl(shortId);

        if(originalUrl != null){
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
