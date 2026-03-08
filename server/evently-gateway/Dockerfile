FROM maven:3.9-amazoncorretto-21 AS builder
WORKDIR /build

COPY pom.xml .

COPY common/pom.xml common/pom.xml
COPY common/src common/src

COPY evently-gateway/pom.xml evently-gateway/pom.xml
COPY evently-gateway/src evently-gateway/src

RUN mvn install -N -DskipTests && \
    mvn install -f common/pom.xml -DskipTests && \
    mvn package -f evently-gateway/pom.xml -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=builder /build/evently-gateway/target/evently-gateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "app.jar"]