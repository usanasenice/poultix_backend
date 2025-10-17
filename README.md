# 🐔 Poultix Server

> A comprehensive Spring Boot backend for poultry farm management system connecting farmers, veterinarians, and IoT devices.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.6-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📋 Table of Contents

- [Overview](overview)
- [Features](features)
- [Technology Stack](technology-stack)
- [Project Structure](project-structure)
- [Getting Started](getting-started)
- [Configuration](configuration)
- [API Documentation](#api-documentation)
- [Database Schema](database-schema)
- [Security](security)
- [Development](development)
- [Testing](testing)
- [Deployment](deployment)
- [Contributing](contributing)

---

## 🎯 Overview

Poultix Server is a RESTful API backend designed for managing poultry farms with features including:

- **User Management**: Farmers and veterinarians with role-based access control
- **Farm Management**: Track farm operations, livestock health, and facility management
- **IoT Integration**: Real-time sensor data collection and device management
- **Veterinary Services**: Schedule appointments, manage veterinary profiles
- **Messaging System**: Real-time communication between farmers and veterinarians
- **News & Support**: Announcements and help desk functionality
- **Pharmacy Management**: Medication catalog and prescription tracking

---

## ✨ Features

### Core Functionality

- ✅ **User Authentication & Authorization** - JWT-based secure authentication
- ✅ **Role-Based Access Control** - Separate permissions for farmers and veterinarians
- ✅ **Farm Operations Management** - Complete CRUD operations for farm entities
- ✅ **IoT Device Integration** - Real-time sensor data collection (temperature, humidity, etc.)
- ✅ **Appointment Scheduling** - Schedule and manage veterinary visits
- ✅ **Real-Time Messaging** - Direct communication between users
- ✅ **News & Announcements** - System-wide notifications
- ✅ **Help Desk Support** - Ticket-based support system
- ✅ **Pharmacy Management** - Medication inventory and tracking

### Technical Features

- 🔐 **Security**: Spring Security with BCrypt password encoding
- 📊 **Data Validation**: Jakarta Validation for request DTOs
- 🗄️ **Database**: PostgreSQL with JPA/Hibernate ORM
- 🔄 **DTO Pattern**: Clean separation with MapStruct auto-mapping
- 📝 **API Documentation**: Swagger/OpenAPI 3.0 integration
- 🏥 **Health Monitoring**: Spring Boot Actuator endpoints
- 🔧 **Hot Reload**: Spring DevTools for rapid development

---

## 🛠 Technology Stack

### Backend Framework

- **Spring Boot** 3.5.6
- **Spring Data JPA** - Data persistence
- **Spring Security** - Authentication & authorization
- **Spring Validation** - Input validation
- **Spring Mail** - Email notifications

### Database

- **PostgreSQL** 17.6 - Primary database
- **H2** - In-memory database for testing
- **Hibernate** 6.6 - ORM framework

### Security & Authentication

- **JWT** (JSON Web Tokens) - Token-based auth
- **BCrypt** - Password hashing

### Development Tools

- **Lombok** - Reduce boilerplate code
- **MapStruct** 1.5.5 - DTO mapping
- **Spring DevTools** - Hot reload
- **Maven** - Build & dependency management

### API Documentation

- **SpringDoc OpenAPI** 2.8.9 - Swagger UI

### Monitoring

- **Spring Boot Actuator** - Health checks & metrics

---

## 📁 Project Structure

```bash
poultix_server/
├── src/main/java/com/poultix/server/
│   ├── config/                    # Configuration classes
│   │   ├── JwtConfig.java        # JWT configuration
│   │   └── SecurityConfig.java   # Security configuration
│   │
│   ├── controller/               # REST API endpoints
│   │   ├── UserController.java
│   │   ├── FarmController.java
│   │   ├── DeviceController.java
│   │   ├── VeterinaryController.java
│   │   ├── ScheduleController.java
│   │   ├── MessageController.java
│   │   ├── NewsController.java
│   │   ├── PharmacyController.java
│   │   ├── HelpSupportController.java
│   │   └── SensorReadingController.java
│   │
│   ├── dto/                      # Data Transfer Objects
│   │   ├── request/             # Request DTOs
│   │   └── response/            # Response DTOs
│   │
│   ├── entities/                # JPA Entities
│   │   ├── User.java
│   │   ├── Farm.java
│   │   ├── Device.java
│   │   ├── Veterinary.java
│   │   ├── Schedule.java
│   │   ├── Message.java
│   │   ├── News.java
│   │   ├── Pharmacy.java
│   │   ├── HelpSupport.java
│   │   ├── SensorReading.java
│   │   ├── embeddables/         # Embedded entities
│   │   └── enums/               # Enumerations
│   │
│   ├── mappers/                 # MapStruct mappers
│   │   ├── UserMapper.java
│   │   ├── FarmMapper.java
│   │   └── ...
│   │
│   ├── repository/              # Spring Data repositories
│   │   ├── UserRepository.java
│   │   ├── FarmRepository.java
│   │   └── ...
│   │
│   ├── service/                 # Business logic
│   │   ├── UserService.java
│   │   ├── FarmService.java
│   │   └── ...
│   │
│   └── ServerApplication.java   # Main application class
│
├── src/main/resources/
│   ├── application.properties   # Configuration
│   └── static/                  # Static resources
│
├── src/test/                    # Test classes
├── pom.xml                      # Maven dependencies
└── README.md                    # This file
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.8+**
- **PostgreSQL 14+**
- **Git**

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/poultix_server.git
   cd poultix_server
   ```

2. **Setup PostgreSQL Database**

   ```sql
   CREATE DATABASE poultix;
   CREATE USER postgres WITH PASSWORD 'postgres';
   GRANT ALL PRIVILEGES ON DATABASE poultix TO postgres;
   ```

3. **Configure application properties**

   Edit `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/poultix
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```

4. **Build the project**

   ```bash
   ./mvnw clean install
   ```

5. **Run the application**

   ```bash
   ./mvnw spring-boot:run
   ```

   Or using Maven wrapper:

   ```bash
   java -jar target/server-0.0.1-SNAPSHOT.jar
   ```

6. **Verify installation**

   Open browser and navigate to:
   - API: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - Health Check: `http://localhost:8080/actuator/health`

---

## ⚙️ Configuration

### Application Properties

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/poultix
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.open-in-view=false

# JWT Configuration
jwt.secret=your-secret-key-here
jwt.expiration=3600000  # 1 hour in milliseconds

# Actuator Configuration
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
management.endpoints.web.base-path=/actuator

# Swagger Configuration
springdoc.swagger-ui.enabled=true
```

### Environment Variables

For production, use environment variables:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/poultix
export DB_USERNAME=postgres
export DB_PASSWORD=your_secure_password
export JWT_SECRET=your-256-bit-secret-key
```

---

## 📚 API Documentation

### Base URL

```bash
http://localhost:8080/api
```

### Main Endpoints

#### 🔐 User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/register` | Register new user |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users` | Get all users |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

#### 🏡 Farm Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/farms` | Create farm |
| GET | `/api/farms/{id}` | Get farm by ID |
| GET | `/api/farms/owner/{ownerId}` | Get farms by owner |
| PUT | `/api/farms/{id}` | Update farm |
| PATCH | `/api/farms/{id}/assign-veterinary` | Assign veterinary |

#### 📟 Device Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/devices` | Register device |
| GET | `/api/devices/{id}` | Get device by ID |
| GET | `/api/devices/farm/{farmId}` | Get devices by farm |
| PUT | `/api/devices/{id}` | Update device |

#### 👨‍⚕️ Veterinary Services

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/veterinaries` | Create veterinary profile |
| GET | `/api/veterinaries/{id}` | Get veterinary by ID |
| GET | `/api/veterinaries/top-rated` | Get top-rated veterinaries |
| PUT | `/api/veterinaries/{id}` | Update profile |

#### 📅 Schedule Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/schedules` | Create schedule |
| GET | `/api/schedules/{id}` | Get schedule by ID |
| GET | `/api/schedules/farmer/{farmerId}` | Get schedules by farmer |
| PATCH | `/api/schedules/{id}/complete` | Complete schedule |

#### 💬 Messaging

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/messages` | Send message |
| GET | `/api/messages/conversation` | Get conversation |
| DELETE | `/api/messages/{id}` | Delete message |

#### 📰 News Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/news` | Create news |
| GET | `/api/news` | Get all news |
| PUT | `/api/news/{id}` | Update news |

#### 💊 Pharmacy

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/pharmacies` | Create pharmacy entry |
| GET | `/api/pharmacies` | Get all pharmacies |
| PUT | `/api/pharmacies/{id}` | Update pharmacy |

#### 🆘 Help & Support

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/support` | Create support ticket |
| GET | `/api/support` | Get all tickets |
| GET | `/api/support/user/{userId}` | Get user tickets |

#### 📊 Sensor Readings

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/sensor-readings` | Create sensor reading |
| GET | `/api/sensor-readings/device/{deviceId}` | Get readings by device |
| GET | `/api/sensor-readings/farm/{farmId}` | Get readings by farm |

### Interactive API Documentation

Access the full interactive API documentation at:

```bash
http://localhost:8080/swagger-ui.html
```

---

## 🗄️ Database Schema

### Core Entities

#### Users

- `id` (UUID, PK)
- `first_name`, `last_name`
- `email` (unique)
- `password` (hashed)
- `phone_number`
- `role` (FARMER, VETERINARY)
- `is_active`
- `created_at`, `updated_at`

#### Farms

- `id` (UUID, PK)
- `name`, `description`
- `owner_id` (FK -> Users)
- `veterinary_id` (FK -> Veterinary)
- `location` (embedded)
- `livestock` (embedded)
- `facilities` (embedded)
- `health_status`

#### Devices

- `id` (UUID, PK)
- `device_id` (unique)
- `name`, `type`
- `farm_id` (FK -> Farms)
- `is_active`
- `last_reading_at`

#### Veterinary

- `id` (UUID, PK)
- `user_id` (FK -> Users)
- `license_number`
- `specialization`
- `experience_years`
- `rates` (embedded)
- `rating`, `total_visits`

#### Schedules

- `id` (UUID, PK)
- `farmer_id` (FK -> Users)
- `veterinary_id` (FK -> Veterinary)
- `farm_id` (FK -> Farms)
- `schedule_date`, `schedule_time`
- `status`, `priority`, `type`
- `results` (embedded)

### Relationships

- User 1:N Farms (owner)
- Veterinary 1:N Farms (assigned)
- Farm 1:N Devices
- Farm 1:N Schedules
- Veterinary 1:N Schedules
- Device 1:N SensorReadings

---

## 🔒 Security

### Authentication Flow

1. User registers via `/api/users/register`
2. Password is hashed using BCrypt
3. Login returns JWT token (valid for 1 hour)
4. Token must be included in Authorization header: `Bearer <token>`

### Password Security

- BCrypt hashing with salt rounds
- Minimum password requirements enforced
- Password reset with secure tokens

### API Security

- CORS configured for cross-origin requests
- CSRF protection (disabled for REST APIs)
- Rate limiting (configurable)
- SQL injection prevention via JPA
- XSS protection via input validation

### Role-Based Access Control

```java
@PreAuthorize("hasRole('FARMER')")
public void farmerOnlyMethod() { ... }

@PreAuthorize("hasRole('VETERINARY')")
public void veterinaryOnlyMethod() { ... }
```

---

## 💻 Development

### Building the Project

```bash
# Clean and compile
./mvnw clean compile

# Run tests
./mvnw test

# Package as JAR
./mvnw package

# Skip tests during build
./mvnw package -DskipTests
```

### Code Quality

The project uses:

- **Lombok** - Reduce boilerplate
- **MapStruct** - Type-safe bean mapping
- **Jakarta Validation** - Input validation

### Hot Reload

Spring DevTools enables automatic restart:

1. Make code changes
2. Save files
3. Application restarts automatically

### Database Migration

Hibernate DDL auto-update is enabled:

```properties
spring.jpa.hibernate.ddl-auto=update
```

For production, use proper migration tools like Flyway or Liquibase.

---

## 🧪 Testing

### Run Tests

```bash
# Run all tests
./mvnw test

# Run specific test
./mvnw test -Dtest=UserServiceTest

# Run with coverage
./mvnw test jacoco:report
```

### Test Structure

```bash
src/test/java/com/poultix/server/
├── controller/          # Controller tests
├── service/             # Service tests
└── repository/          # Repository tests
```

### Integration Tests

```bash
# Run integration tests
./mvnw verify
```

---

## 🚢 Deployment

### Production Build

```bash
./mvnw clean package -DskipTests
```

### Docker Deployment

Create `Dockerfile`:

```dockerfile
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:

```bash
docker build -t poultix-server .
docker run -p 8080:8080 poultix-server
```

### Environment Setup

Production checklist:

- [ ] Set strong JWT secret
- [ ] Configure secure database credentials
- [ ] Enable HTTPS/SSL
- [ ] Set up proper CORS policies
- [ ] Configure logging levels
- [ ] Enable production profiles
- [ ] Set up monitoring and alerting

---

## 📊 Monitoring & Health

### Health Endpoints

```bash
# Overall health
curl http://localhost:8080/actuator/health

# Database health
curl http://localhost:8080/actuator/health/db

# Disk space
curl http://localhost:8080/actuator/health/diskSpace
```

### Application Info

```bash
curl http://localhost:8080/actuator/info
```

---

## 🤝 Contributing

### Getting Started

1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

### Code Style

- Follow Java naming conventions
- Use Lombok annotations appropriately
- Write meaningful commit messages
- Add JavaDoc for public APIs
- Maintain test coverage

### Pull Request Process

1. Update README.md with API changes
2. Update Swagger documentation
3. Ensure all tests pass
4. Request review from maintainers

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👥 Authors

- **Usanase Nice Josiane** 

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- PostgreSQL community
- All contributors and testers

---

## 📞 Support

For support and questions:

- 📧 Email: [support@poultix.com](support@poultix.com)
- 🐛 Issues: [GitHub Issues](https://github.com/yourusername/poultix_server/issues)
- 📖 Docs: [Wiki](https://github.com/yourusername/poultix_server/wiki)

---

## 🗺️ Roadmap

- [ ] WebSocket support for real-time notifications
- [ ] GraphQL API
- [ ] Advanced analytics and reporting
- [ ] Mobile push notifications
- [ ] Multi-language support
- [ ] Export/Import functionality
- [ ] Advanced search and filtering
- [ ] Caching with Redis
- [ ] Microservices architecture

---

Made with ❤️ for the poultry farming community
