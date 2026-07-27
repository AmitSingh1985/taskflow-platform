FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -pl services/api-gateway -am -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/services/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]