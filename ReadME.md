# Evently

A full-stack event ticketing and booking platform built with a microservices architecture. Users can discover events, book tickets, manage orders, and make payments for concerts, festivals, sports, theatre shows, and more.

## Architecture

Evently uses a microservices architecture with a React frontend communicating through an API Gateway to seven Spring Boot services.

```
Frontend (React) → API Gateway (9000) → Microservices
                                          ├── User Service (8085)
                                          ├── Events Service (8082)
                                          ├── Booking Service (8081)
                                          ├── Payment Service (8084)
                                          ├── Notification Service (8083)
                                          └── Eureka Server (8761)
```

**Messaging:** Services communicate asynchronously via Apache Kafka.

## Tech Stack

**Frontend**
- React 19, React Router DOM 7
- Bootstrap 5, Axios
- Stripe React integration

**Backend**
- Java 21, Spring Boot 3.5, Spring Cloud 2025
- Spring Security + JWT
- Apache Kafka (async messaging)
- PostgreSQL (5 isolated databases)
- Redis (caching)
- MapStruct, Lombok

**Infrastructure**
- Docker & Docker Compose
- AWS S3 (media storage)
- Stripe (payments)

## Services

| Service | Port | Responsibility |
|---------|------|----------------|
| API Gateway | 9000 | Request routing, load balancing |
| Eureka Server | 8761 | Service discovery |
| User Service | 8085 | Auth, profiles, follows |
| Events Service | 8082 | Events, locations, artists, categories |
| Booking Service | 8081 | Tickets, orders, promo codes |
| Payment Service | 8084 | Stripe integration, refunds |
| Notification Service | 8083 | Email notifications via Kafka |

## Features

**User**
- Browse and filter events by category
- View event details, artists, and venues
- Follow artists and locations
- Book tickets and apply promo codes
- Checkout with Stripe payment
- View order history and tickets
- Receive email notifications

**Admin**
- Create and manage events, artists, venues
- Manage promo codes and orders
- Review transaction history

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 21 + Maven 3.9+ (for local backend development)
- Node.js 20+ (for local frontend development)

### Run with Docker Compose (Recommended)

```bash
git clone https://github.com/your-org/evently.git
cd evently
docker-compose up -d
```

This starts the full stack:

| Service | URL |
|---------|-----|
| Frontend | http://localhost:3000 |
| API Gateway | http://localhost:9000 |
| Eureka Dashboard | http://localhost:8761 |
| PgAdmin | http://localhost:7654 |
| RedisInsight | http://localhost:5540 |

### Local Frontend Development

```bash
cd app
npm install
npm start
```

Runs on http://localhost:3000. The app connects to the API Gateway at `http://localhost:9000`.

### Local Backend Development

```bash
cd server
./mvnw clean install
# Run a specific service, e.g.:
cd events-service
../mvnw spring-boot:run
```

## Environment Variables

The following secrets must be set (via `.env` or Docker environment):

| Variable | Description |
|----------|-------------|
| `POSTGRES_USER` | PostgreSQL username (default: `admin`) |
| `POSTGRES_PASSWORD` | PostgreSQL password |
| `STRIPE_SECRET_KEY` | Stripe secret key for payment processing |
| `AWS_ACCESS_KEY` | AWS access key for S3 storage |
| `AWS_SECRET_KEY` | AWS secret key for S3 storage |
| `REDIS_HOST` | Redis host (default: `localhost`) |

## Databases

Each service owns its own PostgreSQL database:

| Database | Service |
|----------|---------|
| `events_db` | Events Service |
| `users_db` | User Service |
| `booking_db` | Booking Service |
| `payments_db` | Payment Service |
| `notifications_db` | Notification Service |

The database is seeded with sample data including artists, venues, event categories, and events.

## Kafka Event Topics

Services communicate asynchronously via these Kafka events:

- User registration
- Order payment success
- Event creation
- Promo code creation
- Order completion

## Project Structure

```
evently/
├── app/                      # React frontend
│   ├── src/
│   │   ├── api/              # Axios client and API calls
│   │   ├── components/       # Reusable UI components
│   │   ├── pages/            # Route-level page components
│   │   └── constants/        # API routes and constants
│   └── Dockerfile
├── server/                   # Spring Boot microservices
│   ├── evently-gateway/      # API Gateway
│   ├── eureka-server/        # Service discovery
│   ├── user-service/
│   ├── events-service/
│   ├── booking-service/
│   ├── payment-service/
│   ├── notification-service/
│   └── pom.xml               # Parent POM
├── docker-compose.yaml
└── init.sql                  # Database initialization
```

## CI/CD

GitHub Actions workflows are configured for:
- **Build:** Java microservices build and test (`.github/workflows/build.yml`)
- **Lint:** Code linting (`.github/workflows/linter.yml`)
