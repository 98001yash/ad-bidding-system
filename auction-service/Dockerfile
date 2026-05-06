
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app


COPY . .


WORKDIR /app/kafka-events
RUN mvn clean install -DskipTests

WORKDIR /app/auction-service
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/auction-service/target/*.jar app.jar


EXPOSE 8092

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]