package com.zell.dev.common_lib.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "short_url")
public class ShortUrl {
    @Id
    String id;

    @Column(name = "original_url", nullable=false, length = 2048)
    private String originalUrl;

    @Column(nullable=false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @Column(name = "expiry_at")
    private LocalDateTime expiryAt;

//    private String createdBy;

    @Column(name = "is_active")
    private boolean isActive = true;

}
