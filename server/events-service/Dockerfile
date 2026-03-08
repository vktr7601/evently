FROM maven:3.9-amazoncorretto-21 AS builder
WORKDIR /build

COPY pom.xml .

COPY common/pom.xml common/pom.xml
COPY common/src common/src

COPY events-service/pom.xml events-service/pom.xml
COPY events-service/src events-service/src

RUN mvn install -N -DskipTests && \
    mvn install -f common/pom.xml -DskipTests && \
    mvn package -f events-service/pom.xml -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=builder /build/events-service/target/events-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]