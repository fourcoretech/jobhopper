![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.4.3-brightgreen.svg)
![Build](https://img.shields.io/badge/build-passing-brightgreen)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Java](https://img.shields.io/badge/java-21-blue)
![Tests](https://img.shields.io/badge/tests-passing-brightgreen.svg)

# AI-Powered Resume Analyzer

The **AI-Powered Resume Analyzer** is a collection of microservices designed to analyze resumes, provide interview
preparation assistance, and manage authentication. Built with **Java 21** and **Spring Boot**, the backend is modular,
scalable, and integrates seamlessly with OpenAI's GPT models for AI-driven insights.

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

### 1. Common Library

* Shared utilities, DTOs, and configurations used across all services.
* Includes the OpenAIClient for interacting with OpenAI APIs.

### 2. Resume Analyzer Service

* Parses resumes and extracts key information such as skills, experience, and education.
* Provides AI-powered feedback and recommendations for improving resumes.

### 3. Interview Preparation Service

* Generates interview questions and answers based on job descriptions and resumes.
* Offers tips and strategies for interview preparation.

### 4. Authentication Service

* Manages user authentication and authorization.
* Implements OAuth2 and JWT-based security.

---

## Installation

1. Clone the repository:

bash
git clone https://github.com/erri0n/ai-powered-resume-analyzer.git
cd ai-powered-resume-analyzer


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

## License

This project is licensed under the MIT License. See [LICENSE](./LICENSE) for more information.
