FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY target/evently-gateway-0.0.1-SNAPSHOT.jar evently-gateway-0.0.1-SNAPSHOT.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "evently-gateway-0.0.1-SNAPSHOT.jar"]