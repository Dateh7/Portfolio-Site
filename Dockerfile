# Use Maven with Java 21 to build the application
FROM maven:3.9.5-eclipse-temurin-21 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the full source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use a lightweight Java 21 JRE to run the application
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the builder image
COPY --from=builder /app/target/ProjeectPortfolio-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
