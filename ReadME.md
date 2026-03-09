<a id="readme-top"></a>
## 🌟 About The Project

[![Evently][product-screenshot]](https://evently-spring.s3.eu-north-1.amazonaws.com/internal/evently_logo.jpg)

**Evently** is a full-stack event management platform built with a modern, cloud-native microservices architecture. It enables users to discover, create, and manage events with a seamless ticketing experience and secure global payments powered by **Stripe**.

By moving away from a traditional monolithic approach, Evently provides a highly scalable, event-driven solution designed to handle the complexities of real-time event management.

### 🚀 Key Technical Features

* **📦 Microservices Architecture** – Deconstructed into 5+ specialized services (User, Event, Booking, Payment, Notification). Each service maintains strict Domain Isolation with its own dedicated logical **PostgreSQL** database, ensuring data autonomy and a clean separation of concerns.
* **⚡ Asynchronous Communication** – Orchestrated via **Apache Kafka**. High-latency operations like email notifications are offloaded to an event bus, ensuring the user experience remains fast and uninterrupted.
* **🛣️ API Gateway & Routing** – A centralized entry point built with **Spring Cloud Gateway** (Port 9000). It provides a clean abstraction layer for the frontend, dynamically routing requests while masking the complexity of the internal microservice network.
* **🔍 Service Discovery** – All services dynamically register with a **Netflix Eureka** server. This enables a self-healing environment where services can scale horizontally or restart without the need for hardcoded IP addresses.
* **🛡️ Stateless JWT Security** – Implements robust authentication using **JSON Web Tokens (JWT)**. The Gateway validates the identity once, then securely propagates the user's security context across the entire ecosystem.
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- Badge reference definition -->
[product-screenshot]: https://evently-spring.s3.eu-north-1.amazonaws.com/internal/evently_logo.jpg

### Built With

#### Frontend
* [![React][React.js]][React-url]
* [![React Router][ReactRouter.js]][ReactRouter-url]
* [![Bootstrap][Bootstrap.com]][Bootstrap-url]
* [![Axios][Axios.js]][Axios-url]
* [![Stripe][Stripe.js]][Stripe-url]

#### Backend
* [![Java][Java.dev]][Java-url]
* [![Spring Boot][SpringBoot.io]][SpringBoot-url]
* [![Spring Security][SpringSecurity.io]][SpringSecurity-url]
* [![Apache Kafka][Kafka.apache]][Kafka-url]
* [![PostgreSQL][PostgreSQL.org]][PostgreSQL-url]
* [![Redis][Redis.io]][Redis-url]

#### Infrastructure
* [![Docker][Docker.com]][Docker-url]
* [![AWS S3][AWS.amazon]][AWS-url]
* [![Stripe][Stripe.js]][Stripe-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>


[React.js]: https://img.shields.io/badge/React_19-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/

[ReactRouter.js]: https://img.shields.io/badge/React_Router_7-CA4245?style=for-the-badge&logo=react-router&logoColor=white
[ReactRouter-url]: https://reactrouter.com/

[Bootstrap.com]: https://img.shields.io/badge/Bootstrap_5-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com/

[Axios.js]: https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white
[Axios-url]: https://axios-http.com/

[Stripe.js]: https://img.shields.io/badge/Stripe-635BFF?style=for-the-badge&logo=stripe&logoColor=white
[Stripe-url]: https://stripe.com/

[Java.dev]: https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/

[SpringBoot.io]: https://img.shields.io/badge/Spring_Boot_3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white
[SpringBoot-url]: https://spring.io/projects/spring-boot

[SpringSecurity.io]: https://img.shields.io/badge/Spring_Security_+_JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white
[SpringSecurity-url]: https://spring.io/projects/spring-security

[Kafka.apache]: https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white
[Kafka-url]: https://kafka.apache.org/

[PostgreSQL.org]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org/

[Redis.io]: https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white
[Redis-url]: https://redis.io/

[Docker.com]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/

[AWS.amazon]: https://img.shields.io/badge/AWS_S3-FF9900?style=for-the-badge&logo=amazons3&logoColor=white
[AWS-url]: https://aws.amazon.com/s3/


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


## High Level Diagram

[![Evently](https://evently-spring.s3.eu-north-1.amazonaws.com/internal/Screenshot%202026-03-09%20at%209.34.38.png)](https://evently-spring.s3.eu-north-1.amazonaws.com/internal/Screenshot%202026-03-09%20at%209.34.38.png)
<!-- GETTING STARTED -->

### Techical Aspects
```
Technical Architecture & Port Map
──────────────────────────────────────────────────────────────────────────
Frontend (React) ──▶ API Gateway (9000) ──▶ [Service Discovery (Eureka: 8761)]
                                          │
                                          ├── User Service         (8085)
                                          ├── Events Service       (8082)
                                          ├── Booking Service      (8081)
                                          ├── Payment Service      (8084)
                                          └── Notification Service (8083)
                                          │
        [ Shared Event Bus ] ◀────────────┴──────────▶ Apache Kafka (9092)
```

## 🛠️ Features

### 🔐 Authentication & Profile
* **User Access:** Secure registration and login using **JWT-based stateless authentication**.
* **Role-Based Control:** Granular access levels for **Users** vs. **Admins**.
* **Profile Management:** Users can manage personal info, view owned tickets, and track followed artists/venues.

### 📅 Event Management
* **Discovery:** Browse events
* **Deep Dives:** Detailed event views including artist lineups, location info, and real-time availability.
* **Admin Tools:** Full CRUD capabilities for creating, editing, and managing event listings.

### 🎫 Booking & Payments
* **Stripe Integration:** Secure, industry-standard checkout powered by **Stripe**.
* **Promo Engine:** Admin-managed promo codes that users can apply at checkout for dynamic pricing.

### 🔔 Notifications (Kafka Driven)
* **Email Service:** Automated emails for registration, order confirmations, and updates—processed asynchronously via **Apache Kafka**.