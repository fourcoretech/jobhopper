![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.4.3-brightgreen.svg)
![Build](https://img.shields.io/badge/build-passing-brightgreen)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Java](https://img.shields.io/badge/java-21-blue)
![Tests](https://img.shields.io/badge/tests-passing-brightgreen.svg)
[![API Docs](https://img.shields.io/badge/API-Documentation-blue)](./docs/api.md)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](./docker-compose.yml)
[![OpenAI](https://img.shields.io/badge/AI-OpenAI%20GPT-orange)](https://openai.com)

# AI-Powered Resume Analyzer

> Transform your hiring process with AI-driven resume analysis and interview preparation

The **AI-Powered Resume Analyzer** is a sophisticated suite of microservices that revolutionizes the hiring process through:
- 🎯 Intelligent resume parsing and analysis
- 💡 AI-powered feedback and scoring
- 🎓 Automated interview question generation
- 🔒 Secure user authentication and authorization

Built with **Java 21** and **Spring Boot 3.4.3**, this system leverages OpenAI's GPT models to provide actionable insights for both recruiters and job seekers. The microservices architecture ensures scalability, maintainability, and rapid deployment.

---

## Table of Contents

* [Overview](#overview)
* [Features](#features)
* [Tech Stack](#tech-stack)
* [Services](#services)
* [Installation](#installation)
* [How to Run Each Service](#how-to-run-each-service)
* [How to Test Endpoints](#how-to-test-endpoints)
* [Testing](#testing)
* [API Documentation](#api-documentation)
* [Project Structure](#project-structure)
* [Logging](#logging)
* [Contributing](#contributing)
* [License](#license)

---

## Overview

The backend services power the AI-Powered Resume Analyzer application, enabling features such as:

* **Authentication**: Manages secure user authentication and authorization.
* **Resume Analysis**: Extracts key details and provides AI-driven feedback.
* **Interview Preparation**: Generates tailored interview questions and answers.

Each service is designed as an independent Spring Boot application, ensuring modularity and scalability.

---

## Features

* 🔐 JWT-based stateless authentication with H2-backed user validation
* 📄 Resume parsing using Apache Tika
* 🤖 AI-powered resume feedback and interview prep using OpenAI
* ⚡ Modular microservice architecture (Spring Boot)
* 🧪 Unit-tested services with easy local setup
* 📂 Common library for shared code and config

---

## Tech Stack

* **Language**: Java 21
* **Framework**: Spring Boot 3.4.3
* **Build Tool**: Maven
* **Database**: H2 (local development)
* **HTTP Client**: WebClient
* **AI Integration**: OpenAI API
* **Resume Parsing**: Apache Tika
* **Security**: Spring Security with OAuth2 and JWT
* **Retry Mechanism**: Spring Retry
* **Testing**: JUnit, Spring Boot Test

---

## Services

### 1. Common Library (`common-library`)
* 📚 Shared utilities, DTOs, and configurations
* 🔌 OpenAI client integration
* 🛠️ Common security configurations
* 📋 Shared data models and interfaces

### 2. Resume Analyzer Service (`resume-analyzer-service`)
* 📄 Advanced resume parsing with Apache Tika
* 🤖 AI-powered skill extraction and matching
* 📊 Detailed resume scoring and feedback
* 🔍 Experience and education validation

### 3. Interview Preparation Service (`interview-prep-service`)
* 💭 Dynamic interview question generation
* 📝 Personalized answer suggestions
* 🎯 Role-specific question adaption
* 💡 Interview strategy recommendations

### 4. Authentication Service (`auth-service`)
* 🔐 JWT-based authentication
* 👥 User management and authorization
* 🔒 OAuth2 integration
* 🛡️ Role-based access control

### 5. Event Listener Service (`listener-notification-events`)
* 📨 Real-time event processing
* 🔔 Notification management
* 📱 Multi-channel delivery
* 📊 Event tracking and monitoring

---

## Installation

### Prerequisites
* Java Development Kit (JDK) 21
* Maven 3.8+
* Docker and Docker Compose
* OpenAI API key
* Git

### Quick Start
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ai-powered-resume-analyzer.git
   cd ai-powered-resume-analyzer
   ```

2. Configure environment variables:
   ```bash
   # Create environment files
   cp .env.example .env
   
   # Edit .env file with your configurations
   OPENAI_API_KEY=your_api_key
   JWT_SECRET=your_jwt_secret
   ```

3. Build all services:
   ```bash
   mvn clean install
   ```

4. Start with Docker Compose:
   ```bash
   docker-compose up -d
   ```

### Manual Setup
For development, you can run services individually:

1. Build the common library first:
   ```bash
   cd common-library
   mvn clean install
   ```

2. Configure each service's `application.yaml`
3. Start services in order:
   - Authentication Service
   - Resume Analyzer Service
   - Interview Preparation Service
   - Event Listener Service

---

## How to Run Each Service

### Prerequisites

* Java 21 installed
* Maven installed
* OpenAI API key configured in application.yaml of the resume analyzer and interview preparation services.
* Use the JWT secret generator in the common-library to create a secret key for authentication.
* Paste the generated secret key in the application.yaml of the authentication service.

### Services and Ports

1. **Authentication Service**

    * **Port**: 8080
    * **Dependencies**: Common Library
    * **Run Command**:

bash
mvn spring-boot:run -pl auth-service

2. **Resume Analyzer Service**

    * **Port**: 8082
    * **Dependencies**: OpenAI API, Common Library
    * **Run Command**:

bash
mvn spring-boot:run -pl resume-analyzer-service

3. **Interview Preparation Service**

    * **Port**: 8081
    * **Dependencies**: OpenAI API, Common Library
    * **Run Command**:

bash
mvn spring-boot:run -pl interview-prep-service


---

## How to Test Endpoints

You can find the complete Postman collection here: [`docs/postman_collection.json`](./docs/postman_collection.json)

Run requests for:

- `POST /auth/user/register`
- `POST /auth/authenticate`
- `POST /analysis/analyze`
- `POST /job-match/match`
- `POST /interview/prepare`

---

## Testing

Each service includes complete unit test coverage with JUnit and Spring Boot Test.

To run all tests:

bash
mvn test

For integration testing:

* Each core flow (e.g., /acceptOffer, /analyze, /prepare) includes integration tests in src/test/java

---

## API Documentation

### Sample Request (Resume Analyzer)

**POST** /analysis/analyze

**Form-Data:**

* file: resume.docx

**Response:**

json
{
"extractedInfo": {
"name": "Test User",
"email": "testing123@gmail.com"
},
"scoringResult": "Overall Score: 8/10. Strengths: Experience, Skills, Projects. Areas to improve: formatting, education
section."
}


---

## Project Structure

ai-powered-resume-analyzer/
├── auth-service/
├── common-library/
├── docs/
├── interview-prep-service/
├── resume-analyzer-service/
├── README.md
├── CONTRIBUTING.md
├── LICENSE
└── pom.xml


---

## Logging

Each service uses Slf4j for logging request status, exceptions, and service-level insights.

---

## Contributing

Pull requests and feature suggestions are welcome. For major changes, please open an issue first to discuss your idea.

---

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
## License

This project is licensed under the MIT License. See [LICENSE](./LICENSE) for more information.
