FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app
RUN apk add --no-cache bash


COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

COPY settings.gradle build.gradle ./
COPY src src

RUN ./gradlew clean bootJar -x test

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
