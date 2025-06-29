# Distributed URL Shortener

A scalable, modular, and distributed-style URL shortening system built using Spring Boot. Designed for high-read traffic, sharded database storage, and extensible microservice components.

---

## 📦 Project Structure
```
distributed-url-shortener/
├── common-lib/ # Shared entity models, utils (Base62, ID generator)
├── common-service/ # Shared infrastructure beans (shard resolver, Redis, DataSource configs)
├── shorten-api/ # REST API to shorten URLs
├── redirect-api/ # Optimized redirect service (GET /r/{shortId})
├── gateway-server/ # Spring Cloud Gateway to route requests
├── docker/ # Docker Compose environment (Redis + MySQL shards)
└── README.md
```


---

## 🔧 Tech Stack

- **Java 21**
- **Spring Boot 3.2**
- **Spring Data JPA**
- **Redis** (read-heavy caching)
- **MySQL** (sharded)
- **Spring Cloud Gateway**
- **Docker + Docker Compose**
- **Swagger / OpenAPI**

---

## 🚀 Features
- Shorten long URLs via `POST /shorten`
- Fast redirection via `GET /r/{shortId}` with Redis optimization
- Modular microservices with independent scaling
- Centralized ID generation logic (timestamp-based, extensible to Snowflake)
- Database sharding with shard resolver abstraction
- Redis caching with TTL + LRU
- Health checks and observability with Spring Actuator

---

## 🛠️ Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/ngfenglong/distributed-url-shortener.git
   cd distributed-url-shortener
   ```


2. Start Redis + Postgres using Docker:
  ```bash
   docker-compose up -d
   ```

3. Run services individually:

```bash
cd shorten-api && ./mvnw spring-boot:run
cd redirect-api && ./mvnw spring-boot:run

```

4. Visit Swagger UI:
```
http://localhost:8081/swagger-ui.html (shorten)
http://localhost:8082/swagger-ui.html (redirect)
```

## 📚 Documentation
- [System Design Overview](docs/system-design.md)
---

## 📌 Author

**Ng Feng Long (Zell)**  
🔗 [https://withzell.com](https://withzell.com)  
🐙 [https://github.com/ngfenglong](https://github.com/ngfenglong)


## 📝 License
This project is licensed under the MIT License.