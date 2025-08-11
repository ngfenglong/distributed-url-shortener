# Distributed URL Shortener

A scalable, modular, and distributed-style URL shortening system built using Spring Boot. Designed for high-read traffic, sharded database storage, and extensible microservice components.

**⚠️ Important Notice**: This project is created for both learning and coding reference purposes. It is **NOT** production-grade software. For production solutions, please consider using established, robust, and secure alternatives such as: **Bitly**, **TinyURL**, **Rebrandly**, **Short.io**


If you decide to use this code in production, please ensure you:
- Conduct thorough security testing
- Implement proper authentication and authorization
- Add comprehensive monitoring and alerting
- Perform load testing and performance optimization
- Follow security best practices for production deployments

---

## 📦 Project Structure

```
distributed-url-shortener/
├── common-lib/          # Shared entity models, utilities (Base62, ID generator)
├── common-service/      # Shared infrastructure beans (shard resolver, Redis, DataSource configs)
├── shorten-api/         # REST API to shorten URLs
├── redirect-api/        # Optimized redirect service (GET /r/{shortId})
├── gateway-server/      # Spring Cloud Gateway to route requests
├── eureka-server/       # Service discovery for microservices
├── docker-compose/      # Docker Compose environment (MySQL shards + replicas)
└── docs/               # System design and architecture documentation
```

---

## 🔧 Tech Stack

- **Java 21**
- **Spring Boot 3.2**
- **Spring Data JPA**
- **Redis** (read-heavy caching)
- **MySQL** (sharded with master-replica setup)
- **Spring Cloud Gateway**
- **Eureka Server** (service discovery)
- **Docker + Docker Compose**
- **Swagger / OpenAPI**

---

## 🚀 Features

- Shorten long URLs via `POST /shorten`
- Fast redirection via `GET /r/{shortId}` with Redis optimization
- Modular microservices with independent scaling
- Centralized ID generation logic (timestamp-based, extensible to Snowflake)
- Database sharding with hash-based shard resolver
- Redis caching with TTL + LRU
- Health checks and observability with Spring Actuator
- Master-replica database setup for high availability

---

## 🛠️ Getting Started

### Prerequisites

- **Java 21** - You can download it from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use [OpenJDK](https://adoptium.net/)
- **Docker & Docker Compose** - Install from [Docker's official website](https://docs.docker.com/get-docker/)
- **Make** (optional but recommended) - Install via package manager or download from [GNU Make](https://www.gnu.org/software/make/)

### Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ngfenglong/distributed-url-shortener.git
   cd distributed-url-shortener
   ```

2. **Start the entire system using Make (recommended):**
   ```bash
   make docker-up
   ```
   
   **Alternative using Docker Compose directly:**
   ```bash
   docker-compose -f docker-compose/dev/docker-compose.yml up -d
   ```

3. **Stop the system:**
   ```bash
   make docker-down
   ```
   
   **Alternative using Docker Compose directly:**
   ```bash
   docker-compose -f docker-compose/dev/docker-compose.yml down
   ```

4. **Test the API:**
   ```bash
   # Shorten a URL
   curl -X POST http://localhost:8080/shorten \
     -H "Content-Type: application/json" \
     -d '{"longUrl": "https://www.example.com/very-long-url-path"}'
   
   # Use the shortened URL (replace {shortId} with actual ID)
   curl -I http://localhost:8080/r/{shortId}
   ```

### Manual Service Startup (Alternative)

If you prefer to run services individually for development:

```bash
# Start Eureka Server first
cd eureka-server && ./mvnw spring-boot:run

# Start Gateway Server
cd gateway-server && ./mvnw spring-boot:run

# Start Shorten API
cd shorten-api && ./mvnw spring-boot:run

# Start Redirect API
cd redirect-api && ./mvnw spring-boot:run
```

---

## 📚 Documentation

- **[System Design Overview](docs/system-design.md)** 

---

## 🔍 API Documentation

To access the Swagger UI for API documentation, you must first uncomment the relevant `ports` lines in the `docker-compose` configuration to expose the service ports. Once exposed, you can access:

- **Shorten API Swagger UI**: http://localhost:8081/swagger-ui.html
- **Redirect API Swagger UI**: http://localhost:8082/swagger-ui.html

---

## 🧪 Testing & Future Enhancements

- Add unit tests for core logic
- Add performance and benchmarking tests

---

## 📌 Author

**Ng Feng Long (Zell)**  
🔗 [https://withzell.com](https://withzell.com)  
🐙 [https://github.com/ngfenglong](https://github.com/ngfenglong)

---

## 📝 License

This project is licensed under the MIT License. 

---

## 🤝 Contributing

This is a personal learning project, but if you find bugs or have suggestions for improvements, feel free to open an issue or submit a pull request.
