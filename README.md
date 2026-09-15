# taskflow-platform

[![TaskFlow Platform](https://img.shields.io/badge/TaskFlow-Microservices-blue)](https://github.com/AmitSingh1985/taskflow-platform)

A production-ready project management platform built with **Java 21, Spring Boot 3, microservices, event-driven architecture, Docker, Kafka, PostgreSQL, Redis, Elasticsearch, Spring AI, Ollama, DL4J/ND4J, Resilience4j, Eureka, OpenFeign, Actuator, Micrometer, and GitHub Actions**.

TaskFlow demonstrates how modern enterprise applications can be designed using **microservices, asynchronous event-driven communication, AI-assisted workflows, machine learning, distributed caching, search, resilience patterns, and containerized infrastructure**.

---

## Overview

TaskFlow is a distributed project and task management platform designed to demonstrate production-oriented backend architecture using Spring Boot microservices.

The platform supports:

- Project management
- Task management
- User assignment
- Authentication and authorization
- AI-assisted task creation
- Machine-learning-based task priority prediction
- Event-driven communication using Kafka
- Full-text project search using Elasticsearch
- Redis caching
- Service-to-service communication using OpenFeign
- Resilience using Resilience4j
- Database migrations using Flyway
- Service discovery using Eureka
- Observability using Spring Boot Actuator and Micrometer
- Docker-based local infrastructure
- Future Kubernetes deployment

---

## Features

### Project Management

- Create projects
- Update projects
- Delete projects
- Manage project status
- Assign project owners
- Project lifecycle management

### Task Management

- Create tasks
- Update tasks
- Delete tasks
- Assign tasks to users
- Task priority management
- Task status management
- Due dates
- Complexity estimation
- Urgency estimation
- Dependency tracking
- Estimated implementation hours

### AI-Assisted Task Creation

TaskFlow integrates **Spring AI + Ollama** to convert natural-language requests into structured tasks.

Example:

```text
Create a HIGH priority task to fix the production payment timeout.
Assign it to John.


----------------------------------------------------------------------------------------------------------------------

The AI service converts the request into structured information such as:

{
  "title": "Fix Production Payment Timeout",
  "description": "Investigate the production payment timeout issue and implement a permanent fix.",
  "priority": "HIGH",
  "assignee": "John",
  "dueDate": null,
  "complexity": 8,
  "urgency": 10,
  "dependencyCount": 2,
  "estimatedHours": 8
}

The AI does not directly modify the database.

Instead:

Client
   |
   v
Task Service
   |
   | OpenFeign
   v
AI Service
   |
   | Spring AI
   v
Ollama
   |
   v
AiTaskDraft
   |
   v
Task Service
   |
   v
Normal Task Creation Logic
   |
   v
PostgreSQL

This keeps authentication, authorization, validation, and business rules inside Task Service.


----------------------------------------------------------------------------------------------------------

Machine Learning Priority Prediction

TaskFlow also contains a separate ML workflow using DL4J and ND4J.

The model uses:

Complexity
Urgency
Dependency count
Estimated hours
Description length

The model predicts:

LOW
MEDIUM
HIGH

Example prediction event:

{
  "taskId": "20edf9f6-866f-4924-b914-9f124558e28b",
  "predictedPriority": "HIGH",
  "confidence": 0.98,
  "modelVersion": "task-priority-v1"
}

The prediction is published asynchronously through Kafka.

Task Service
    |
    | task-created
    v
Kafka
    |
    v
AI Service
    |
    v
DL4J / ND4J
    |
    | task-intelligence-generated
    v
Kafka
    |
    v
Task Service
    |
    v
PostgreSQL

The trained model is persisted locally and loaded on application startup.

AI Service starts
       |
       v
Model exists?
   /       \
 Yes       No
  |         |
Load      Train
Model     Model
  |         |
  |       Save
  |         |
  +----> Model Ready
  
------------------------------------------------------------------------------------------------------

Event-Driven Architecture

Kafka is used for asynchronous communication between services.

Example events:

task-created
project-created
project-updated
project-deleted
task-intelligence-generated

This allows multiple services to react independently to domain events.

For example:

Task Service
     |
     | task-created
     v
   Kafka
   / | \
  /  |  \
 v   v   v
Notification
Search
AI Service
Search

Search Service uses:

Elasticsearch
Spring Data Elasticsearch
Kafka events

Projects created or updated in Project Service can be propagated asynchronously to Elasticsearch.

Redis Caching

Redis is used to reduce repeated database access and improve response time for frequently accessed data.

Resilience

Inter-service communication uses Resilience4j for:

Retry
Circuit Breaker
Fallback handling

Example:

Task Service
     |
     v
Project Service
     |
     X
 Service unavailable
     |
     v
Retry
     |
     v
Circuit Breaker
     |
     v
Fallback

AI Service communication also uses resilience patterns because LLM inference can be slower or temporarily unavailable.

Service Discovery

Eureka provides service discovery for the Spring Boot microservices.

                 Eureka
               /   |   \
              /    |    \
             v     v     v
       Task Service
       Project Service
       AI Service
       Search Service
       Notification Service
Architecture
                         API Gateway
                              |
          +-------------------+-------------------+
          |                   |                   |
          v                   v                   v
   Project Service       Task Service        Search Service
          |                   |                   |
      PostgreSQL          PostgreSQL        Elasticsearch
                              |
                              | Kafka
                              v
                         AI Service
                         /        \
                    DL4J/ND4J    Spring AI
                       |             |
                  ML prediction    Ollama
                                      |
                                      v
                                Qwen 2.5 1.5B
AI Event Flow
Task Service
     |
     | task-created
     v
   Kafka
     |
     v
 AI Service
     |
     +------------------+
     |                  |
     v                  v
 DL4J / ND4J        Spring AI
     |                  |
     v                  v
Priority Model       Ollama
     |                  |
     +--------+---------+
              |
              v
task-intelligence-generated
              |
              v
            Kafka
              |
              v
        Task Service
              |
              v
          PostgreSQ

-------------------------------------------------------------------------------------------------------------------------

Technology Stack
Backend
Java 21
Spring Boot 3
Spring MVC
Spring Data JPA
Spring Data Elasticsearch
Spring Kafka
Spring AI
Spring Boot Actuator
Microservices
Eureka Service Discovery
API Gateway
Project Service
Task Service
Notification Service
Search Service
AI Service
AI / Machine Learning
Spring AI
Ollama
Qwen 2.5 1.5B
Deeplearning4j
ND4J
Databases and Infrastructure
PostgreSQL
Redis
Elasticsearch
Apache Kafka
Kafka UI
Resilience
Resilience4j
Retry
Circuit Breaker
Communication
REST
OpenFeign
Kafka
Database Migration
Flyway
Observability
Spring Boot Actuator
Micrometer
OpenTelemetry
DevOps
Docker
Docker Compose
Kubernetes
GitHub Actions

------------------------------------------------------------------------------------------------------------

Getting Started
Prerequisites

Install the following:

Java 21+
Maven 3.9+
Docker Engine
Docker Compose
Git

For the complete TaskFlow stack, 12–16 GB+ RAM is recommended.

The project has also been developed and tested on a machine with 8 GB RAM.

On an 8 GB machine, running all infrastructure and microservices simultaneously can result in high memory consumption and slower performance.

The most memory-intensive services are typically:

Ollama
Elasticsearch
Kafka
PostgreSQL
Spring Boot services

For this reason, it is recommended to run only the services required for the current development workflow.

Running on an 8 GB RAM Machine
AI / DL4J Workflow

When working on AI task creation and ML prediction, run only the required services:

Eureka
Kafka
PostgreSQL
Task Service
AI Service
Ollama

You can leave the following services stopped:

Redis
Elasticsearch
Search Service
Notification Service

This leaves more memory available for Ollama and the Spring Boot applications.

Search Workflow

When testing Elasticsearch:

Eureka
Kafka
PostgreSQL
Project Service
Search Service
Elasticsearch
Notification Workflow

When testing Kafka notifications:

Eureka
Kafka
Task Service
Notification Service

Selective service startup is recommended for development on machines with limited RAM.

Docker

Start the complete stack:

docker compose up -d

Check running containers:

docker ps

Stop the complete stack:

docker compose down
Start Only Required Services

For example, for the AI workflow:

docker compose up -d eureka-server kafka postgres task-service ai-service ollama

Service names depend on the names defined in docker-compose.yml.

Stop selected services:

docker compose stop elasticsearch redis search-service notification-service

Start them again:

docker compose start elasticsearch redis search-service notification-service
Docker Resource Monitoring

Check live Docker resource usage:

docker stats

For a one-time snapshot:

docker stats --no-stream

Example:

CONTAINER          CPU %     MEM USAGE / LIMIT
taskflow-ollama    ...       2.5GiB / 7.5GiB
taskflow-kafka     ...       700MiB / 7.5GiB
taskflow-postgres  ...       300MiB / 7.5GiB

The most important values to monitor are:

MEM USAGE / LIMIT
CPU %

If Docker memory usage becomes too high, stop services that are not required for the current workflow.

Ollama

TaskFlow uses Ollama for natural-language task understanding.

The development configuration uses:

qwen2.5:1.5b

The smaller model is intentional because the application should remain usable on development machines with limited RAM.

Pull the model
docker exec -it taskflow-ollama ollama pull qwen2.5:1.5b
Check installed models
docker exec taskflow-ollama ollama list
Check currently running model
docker exec taskflow-ollama ollama ps
Test Ollama directly
docker exec -it taskflow-ollama ollama run qwen2.5:1.5b
AI Configuration

When running AI Service locally outside Docker:

spring:
  ai:
    ollama:
      base-url: http://localhost:11434

When AI Service runs inside Docker:

spring:
  ai:
    ollama:
      base-url: http://ollama:11434

Docker service names should be used for inter-container communication.

Examples:

Kafka          kafka:9092
PostgreSQL     postgres:5432
Redis          redis:6379
Elasticsearch  elasticsearch:9200
Ollama         ollama:11434
Eureka         eureka-server:8761

Do not use localhost for communication between containers.

Kafka

Check Kafka containers:

docker ps

Check Kafka topics using Kafka UI or Kafka CLI.

Important TaskFlow topics include:

task-created
project-created
project-updated
project-deleted
task-intelligence-generated

Example event flow:

Task Service
     |
     | task-created
     v
Kafka
     |
     +----------------+
     |                |
     v                v
Notification       AI Service
                      |
                      v
                  DL4J/ND4J
                      |
                      v
          task-intelligence-generated
                      |
                      v
                    Kafka
                      |
                      v
                Task Service
Database

TaskFlow uses PostgreSQL with Flyway migrations.

AI-related task fields include:

predicted_priority
ai_confidence
ai_model_version

To access PostgreSQL inside the Docker container:

docker exec -it taskflow-postgres psql -U postgres

Connect to the TaskFlow database:

\c taskflow

Check the task table:

\d tasks

Check the latest tasks:

SELECT *
FROM tasks
ORDER BY created_at DESC
LIMIT 10;

Check the latest AI predictions:

SELECT
    id,
    title,
    priority,
    predicted_priority,
    ai_confidence,
    ai_model_version,
    created_at
FROM tasks
WHERE predicted_priority IS NOT NULL
ORDER BY created_at DESC
LIMIT 10;
AI Model Persistence

The DL4J model is persisted to:

./models/task-priority-v1.zip

Configuration:

taskflow:
  ai:
    model-path: ./models/task-priority-v1.zip

When the AI Service starts:

Model file exists?
       |
   +---+---+
   |       |
  Yes      No
   |       |
 Load     Train
   |       |
   |      Save
   |       |
   +---+---+
       |
       v
  Model Ready

The model directory should normally be excluded from Git:

models/

For Docker deployment, the model directory can be mounted as a volume:

volumes:
  - ./ai-models:/app/models
Testing the AI Workflow

Example request:

POST /api/tasks/ai-create
Content-Type: text/plain

Example request body:

Create a HIGH priority task to fix a production payment timeout issue.

The payment service is intermittently timing out during checkout and some customers are unable to complete payments.

Investigate the root cause and implement a permanent fix.

Assign it to John.

The AI extracts task information such as:

priority
complexity
urgency
dependencyCount
estimatedHours
assignee
description

The Task Service then creates the task using the normal business logic.

After creation, the task-created event triggers DL4J priority prediction.

The prediction is eventually stored in PostgreSQL.

Contributing

Contributions, suggestions, and improvements are welcome.

Fork the repository
Create a feature branch
Make your changes
Add tests where applicable
Commit your changes
Push the branch
Open a Pull Request

Example:

git checkout -b feature/my-feature

git add .

git commit -m "Add my feature"

git push origin feature/my-feature

License

This project is currently intended as a portfolio and educational project.

Add the appropriate license here if the repository is released under a specific open-source license.



