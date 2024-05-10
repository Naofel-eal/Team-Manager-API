FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder

WORKDIR /app
COPY . .
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk

COPY --from=builder /app/target/team-manager.jar /app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]