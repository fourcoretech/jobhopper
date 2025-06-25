# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

Standard Workflow
1. First think through the problem, read the codebase for relevant files, and write a plan to todo.md. 
2. The plan should have a list of todo items that you can check off as you complete them 
3. Before you begin working, check in with me and I will verify the plan.
4. Then, begin working on the todo items, marking them as complete as you go. 
5. Please every step of the way just give me a high level explanation of what changes you made 
6. Make every task and code change you do as simple as possible. We want to avoid making any massive or complex changes. Every change should impact as little code as possible. Everything is about simplicity. 
7. Finally, add a review section to the todo.md file with a summary of the changes you made and any other relevant information.

## Development Commands

### Build and Test
- Build all services: `mvn clean install`
- Run all tests: `mvn test`
- Build specific service: `mvn clean install -pl <service-name>`
- Test specific service: `mvn test -pl <service-name>`

### Running Services
Services must be started in dependency order:
1. Build common library first: `cd backend/common-library && mvn clean install`
2. Start services individually:
   - Auth service (port 8080): `mvn spring-boot:run -pl backend/auth-service`
   - Resume analyzer (port 8082): `mvn spring-boot:run -pl backend/resume-analyzer-service` 
   - Interview prep (port 8081): `mvn spring-boot:run -pl backend/interview-prep-service`
   - Event listener (port 8083): `mvn spring-boot:run -pl backend/listener-notification-events`

### Docker Development
- Start all services: `docker-compose up -d`
- Stop all services: `docker-compose down`
- Rebuild and start: `docker-compose up -d --build`

## Architecture Overview

### Microservices Structure
This is a Spring Boot microservices architecture with 5 main components:

1. **common-library**: Shared utilities, DTOs, OpenAI client, JWT utilities, and security configurations used by all services
2. **auth-service**: JWT-based authentication with H2 database, user management
3. **resume-analyzer-service**: Resume parsing with Apache Tika, AI-powered analysis via OpenAI API
4. **interview-prep-service**: AI-generated interview questions using OpenAI API
5. **listener-notification-events**: Kafka event consumer for notifications

### Key Dependencies
- Java 21 + Spring Boot 3.4.3
- Apache Tika for resume parsing
- OpenAI API integration via WebClient
- JWT for stateless authentication
- H2 database for local development
- Kafka for event messaging
- Spring Security with OAuth2

### Service Communication
- Services communicate via HTTP REST APIs
- Authentication required via JWT tokens from auth-service
- Event-driven communication through Kafka topics
- Common library provides shared OpenAI client and security configurations

### Configuration Requirements
- OpenAI API key must be configured in application.yaml for resume-analyzer and interview-prep services
- JWT secret generated using JwtSecretGenerator in common-library
- Kafka bootstrap servers configured (default: kafka:9092)
- H2 database used for local development

### Testing
- Each service has unit tests and integration tests in src/test/java
- Integration tests use Spring Boot Test framework
- Postman collection available at postman_collection.json for API testing

## Development Workflow
When working on features:
1. Always build common-library first if making shared changes
2. Services have dependencies: auth-service is required by others for JWT validation
3. Use existing patterns for new endpoints (follow controller → service → repository pattern)
4. OpenAI integration should use the shared OpenAIService from common-library