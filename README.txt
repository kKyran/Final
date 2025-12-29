CRM REST API for Educational Center
Project Overview

This project is a backend CRM REST API designed for managing the operations of an educational center.
The system automates key business processes such as student and teacher management, course organization, group scheduling, payment tracking, and processing applications from new clients.

The application is implemented as a RESTful web service using a layered architecture that ensures scalability, maintainability, and security.

Key Features

Management of core entities: students, teachers, courses

Creation and management of study groups

Scheduling of lessons and timetables

Tracking student payments

Processing applications from potential students

Secure access with authentication and role-based authorization

Technology Stack

Java: 17

Framework: Spring Boot 3.1.2

Spring Web: REST API development

Spring Data JPA: Database interaction

Spring Security: Authentication and authorization

Build Tool: Gradle

Database: PostgreSQL

Database Migrations: Liquibase

Containerization: Docker (via docker-compose)

API Testing: Postman

Application Architecture

The project follows a layered (multi-tier) architecture:

Controller Layer (/controller)
Handles HTTP requests and exposes REST endpoints. Converts DTOs to entities and vice versa.

Service Layer (/service)
Contains the business logic and coordinates interactions between repositories.

Repository Layer (/repository)
Provides CRUD operations using Spring Data JPA.

Entity Layer (/entity)
Defines the domain models and database tables.

DTO Layer (/dto)
Used to transfer data between the client and server while hiding internal entity structure.

Mapper Layer (/mapper)
Responsible for mapping between Entity and DTO objects.

Database Management

The application uses PostgreSQL as the relational database.

Database schema changes are managed using Liquibase.
All migrations (table creation, column updates, etc.) are defined in YAML files located at:

src/main/resources/db/changelog/changes


This approach guarantees consistent database structure across all environments (development, testing, and production).

REST API Design

The API follows standard REST principles and uses HTTP methods:

GET

POST

PUT

DELETE

Main Endpoints

/api/requests — client applications

/api/courses — courses

/api/students — students

/api/teachers — teachers

/api/groups — study groups

/api/schedule — schedules

/api/lessons — lessons

/api/payments — payments

Data format: JSON

A Postman collection is provided for testing:

crm-rest-new.postman_collection.json

Security

Security is implemented using Spring Security with JWT (JSON Web Token) authentication.

Authentication Flow

User submits login and password

Server validates credentials

Server generates and returns a JWT token

Client sends the token in the Authorization header with each request

Authorization

Each request is validated by a custom AuthTokenFilter

Role-based access control is implemented

Example roles:

ROLE_USER

ROLE_ADMIN

Core Business Entities

Student — stores personal student data

Teacher — stores teacher information

Course — represents an educational program

Group — connects students, a teacher, and a course

ApplicationRequest — represents a request from a potential client

Schedule / Lesson — manage class schedules and lessons

Payment — tracks student payments

Key Business Processes

Application Processing:
New client applications are created and later converted into student records.

Group Formation:
Administrators create study groups, assign courses and teachers, and add students.

Scheduling:
Lessons and schedules are created and managed for each group.

Payment Tracking:
Payments are recorded and linked to students.

Running the Application
Requirements

Java 17

Docker

Steps to Run

Start PostgreSQL using Docker:

docker-compose up -d


Run the application:

./gradlew bootRun

API Testing

Import crm-rest-new.postman_collection.json into Postman

Authenticate to receive a JWT token

Use the token to access secured endpoints

Author

Darhan\
