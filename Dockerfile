# Use an official Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-17 as builder

# Set the working directory
WORKDIR /app

# Copy all project files
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Use a minimal Java image to run the app
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder image
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on (usually 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
