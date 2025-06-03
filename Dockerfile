# Use a Maven image with JDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Set work directory
WORKDIR /app

# Copy source
COPY . .

# Package the app
RUN mvn clean package -DskipTests

# Use a minimal JDK image to run the app
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
