FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY target/events-service-0.0.1-SNAPSHOT.jar events-service-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "events-service-0.0.1-SNAPSHOT.jar"]