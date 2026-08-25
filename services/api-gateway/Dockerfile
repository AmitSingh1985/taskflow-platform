FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -pl services/api-gateway -am -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/services/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Download/copy OpenTelemetry Java Agent
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar \
    /otel/opentelemetry-javaagent.jar

EXPOSE 8080

ENTRYPOINT ["java","-javaagent:/otel/opentelemetry-javaagent.jar","-jar","app.jar"]