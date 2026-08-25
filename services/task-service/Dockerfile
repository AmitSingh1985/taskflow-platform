# ===============================
# Build Stage
# ===============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package \
    -pl services/task-service \
    -am \
    -DskipTests

# ===============================
# Runtime Stage
# ===============================
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder \
    /app/services/task-service/target/task-service-0.0.1-SNAPSHOT.jar \
    app.jar
    
# Download/copy OpenTelemetry Java Agent
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar \
    /otel/opentelemetry-javaagent.jar

EXPOSE 8081

ENTRYPOINT ["java","-javaagent:/otel/opentelemetry-javaagent.jar","-jar","app.jar"]