package com.zell.dev.shorten_api.repository.shard1.replica;

import com.zell.dev.common_lib.repository.ShortUrlRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Shard1ReadOnlyShortUrlRepository extends ShortUrlRepository { }
