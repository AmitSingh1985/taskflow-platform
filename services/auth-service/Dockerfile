# ===============================
# Build Stage
# ===============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package \
    -pl services/auth-service \
    -am \
    -DskipTests

# ===============================
# Runtime Stage
# ===============================
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder \
    /app/services/auth-service/target/auth-service-0.0.1-SNAPSHOT.jar \
    app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]