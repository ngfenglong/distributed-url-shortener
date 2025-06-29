package com.zell.dev.common_lib.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Entity
@AllArgsConstructor
public class ShortUrl {
    @Id
    String id;

    @Column(nullable=false, length = 2048)
    private String originalUrl;

    @Column(nullable=false)
    private LocalDateTime createdAt;

    private LocalDateTime lastAccessedAt;

    private LocalDateTime expiryAt;

//    private String createdBy;

    private boolean isActive = true;

}
