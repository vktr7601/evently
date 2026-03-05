FROM maven:3.9-amazoncorretto-21 AS builder
WORKDIR /build

COPY pom.xml .

COPY common/pom.xml common/pom.xml
COPY common/src common/src

COPY notification-service/pom.xml notification-service/pom.xml
COPY notification-service/src notification-service/src

RUN mvn install -N -DskipTests && \
    mvn install -f common/pom.xml -DskipTests && \
    mvn package -f notification-service/pom.xml -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=builder /build/notification-service/target/notification-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]