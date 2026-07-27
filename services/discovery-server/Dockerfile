# ============================
# Stage 1 - Build
# ============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy complete project
COPY . .

# Build only discovery-server and its dependencies
RUN mvn clean package -pl services/discovery-server -am -DskipTests

# ============================
# Stage 2 - Runtime
# ============================
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/services/discovery-server/target/discovery-server-1.0.0.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","app.jar"]