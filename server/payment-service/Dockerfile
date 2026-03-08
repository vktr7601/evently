FROM maven:3.9-amazoncorretto-21 AS builder
WORKDIR /build

COPY pom.xml .

COPY common/pom.xml common/pom.xml
COPY common/src common/src

COPY payment-service/pom.xml payment-service/pom.xml
COPY payment-service/src payment-service/src

RUN mvn install -N -DskipTests && \
    mvn install -f common/pom.xml -DskipTests && \
    mvn package -f payment-service/pom.xml -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=builder /build/payment-service/target/payment-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]