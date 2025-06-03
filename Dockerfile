# Use a Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a minimal Java image to run the app
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/ProjectPortfolio-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
