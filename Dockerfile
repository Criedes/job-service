# ============ Build stage ============
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Cache deps first (works with or without BuildKit)
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Build the app
COPY src ./src
RUN mvn -q -e -DskipTests clean package

# ============ Runtime stage ============
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/target/job-service-*.jar app.jar

EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker
VOLUME ["/data"]

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
