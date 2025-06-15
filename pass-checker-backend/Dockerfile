FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
RUN ./gradlew dependencies --refresh-dependencies
COPY src ./src
RUN ./gradlew build

FROM docker.io/library/openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]