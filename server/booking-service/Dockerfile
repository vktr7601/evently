FROM maven:3.9-amazoncorretto-21 AS builder
WORKDIR /build

# Copy parent pom
COPY pom.xml .

# Copy common module
COPY common/pom.xml common/pom.xml
COPY common/src common/src

# Copy booking-service module
COPY booking-service/pom.xml booking-service/pom.xml
COPY booking-service/src booking-service/src

# Build
RUN mvn install -N -DskipTests && \
    mvn install -f common/pom.xml -DskipTests && \
    mvn package -f booking-service/pom.xml -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=builder /build/booking-service/target/booking-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]