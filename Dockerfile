# ---- Build stage ----
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Copy project files
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

# Build the Spring Boot app
RUN mvn clean package -DskipTests

# ---- Run stage ----
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/ProjeectPortfolio-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
