package com.zell.dev.common_lib.repository;

import com.zell.dev.common_lib.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {
}
