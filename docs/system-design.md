# System Design: Distributed URL Shortener

## 1. Overview

A distributed, read-optimized URL shortening system using Spring Boot microservices. Built to scale horizontally, support sharding, and handle high read throughput.

## 2. Architecture Diagram

![System Architecture](./system-architecture.png)

## 3. Components

### 3.1 Gateway Server
- Routes requests to `/shorten/**` and `/r/**`
- Handles basic routing and rate limiting

### 3.2 Shorten API
- Accepts long URLs and generates short IDs
- Writes to sharded database and updates Redis

### 3.3 Redirect API
- High-read path for short URL resolution
- Redis-first, DB fallback
- Read-only DB access

### 3.4 ID Generator
- ID generation logic is located in the `common-lib` module
- Uses a **timestamp-based strategy** with sequence counter to prevent collisions
- Designed to be simple and robust for single-instance generation
- Can be extended to Redis-backed counters or Snowflake if needed
- **Scalable design**: In the future, this can be upgraded to use Snowflake or similar distributed ID generation algorithms to ensure uniqueness across services or data centers  
- Read more on this design decision here: [Why Distributed Systems Need Their Own Unique ID Generator](https://medium.com/@zell_dev/why-distributed-systems-need-their-own-unique-id-generator-38bd10bcbc97)

### 3.5 Common Service / Lib
- Shared models, utils, configs, Redis setup, and shard resolver logic
- `common-lib` holds entity definitions, Base62 and hash utilities
- `common-service` includes shared infrastructure beans like:
  - Shard resolver
  - Redis config
  - Multiple DataSource configurations

## 4. Data Flow

### Shortening Flow
1. Client sends `POST /shorten` with a long URL
2. Shorten API calls ID generator
3. Writes to DB and Redis

### Redirect Flow
1. Client hits `GET /r/{shortId}`
2. Redirect API checks Redis
3. If miss, fallback to DB

## 5. Database Sharding
- Shard selection is handled via a **HashBasedShardResolver** component.
- We apply a **Murmur3 hash** on the generated numeric ID (before Base62 encoding), and use `hash % shardCount` to resolve which shard to use.
- While this approach is static and hash-based, it is **extensible** to:
  - Geographical routing
  - User-group bucketing

> Note: For simplicity, this prototype uses **2 shards** and defines **2 separate repositories** (e.g., `ShortUrlRepositoryShard0`, `ShortUrlRepositoryShard1`) to write/read data accordingly.


## 6. Redis Caching
- Shortened URL mappings are cached for fast resolution
- TTL (Time-To-Live) and LRU (Least Recently Used) eviction strategy
- Reduces load on databases and improves redirect latency


## 7. Tech Stack

| Layer      | Technology           |
|------------|----------------------|
| API        | Spring Boot          |
| Gateway    | Spring Cloud Gateway |
| Caching    | Redis                |
| DB         | MySQL (sharded) |
| Infra      | Docker / Compose     |
| Metrics    | Spring Actuator      |


