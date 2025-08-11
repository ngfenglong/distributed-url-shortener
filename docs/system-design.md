# Distributed URL Shortener — System Design

## Overview
This project is a distributed URL shortener, built as a hands-on exploration of sharding, caching, and microservice patterns using Java, Spring Boot, and Docker. It is not production-grade, but demonstrates key distributed system concepts in a runnable, modular form.

## Architecture Diagram
![System Architecture](./system-architecture.png)

This diagram illustrates how all major components interact:
- **Gateway:** All client requests enter through the Gateway (Spring Cloud Gateway), which is the only externally exposed service. The Gateway routes traffic to backend services.
- **Service Discovery:** The Gateway, Shorten API, and Redirect API are all registered with Eureka for dynamic service discovery.
- **Microservices:** The system is split into a URL shortening API and a redirect API, each responsible for their respective business logic.
- **Sharded Databases:** Both APIs interact with sharded MySQL databases (two shards, each with a master and a replica). Shard selection is handled by a hash-based resolver.
- **Caching:** Redis is used to cache URL mappings, reducing database load and improving redirect speed.
- **Internal-Only:** All services and databases except the Gateway are internal and not exposed to the outside world.

## Key Implementation Details
- **Hash-Based Sharding:** A Murmur3 hash of the short URL ID determines the database shard, ensuring even data distribution.
- **AOP-Based Data Source Routing:** Custom annotations and aspect-oriented programming route reads to replicas and writes to masters.
- **Shorten API:** Accepts long URLs, generates unique short IDs, stores mappings in the correct shard, and updates Redis.
- **Redirect API:** Resolves short URLs, first checking Redis, then falling back to the database if needed. Results are cached for future requests.
- **Health Checks:** All services expose health endpoints; Docker Compose waits for dependencies to be healthy before starting each service.
- **Configuration:** Connection details, shard counts, and cache settings are externalized for easy adjustment.

## Design Choices & Limitations
- **Not Production-Grade:** The system omits authentication, advanced monitoring, and rate limiting to focus on core distributed data patterns.
- **Simplicity:** The architecture is intentionally simple and easy to run locally, prioritizing clarity over completeness.

## What This Project Demonstrates
- Practical use of sharding and caching in a microservice context
- Clean separation of concerns between routing, business logic, and data access
- Use of service discovery and containerization for local distributed systems
- Engineering tradeoffs in building a demo-scale distributed backend

---



