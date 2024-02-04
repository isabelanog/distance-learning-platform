# Distance Learning Platform
## Description
This project is a microservices-based application developed using Java 11, Spring Boot, Docker Compose, and MySQL. It consists of three microservices - authuser, course, and notification, each handling specific business logic. The communication between these microservices is asynchronous.

The project also includes Eureka for service discovery, an API gateway, and a configuration server. The configuration server hosts all configuration files for business logic microservices on GitHub.

## Technologies Used
- Java 11
- Spring Boot
- Docker Compose
- MySQL
- Eureka (for service discovery)
- API Gateway
- Config Server (hosting configurations on GitHub)
## Project Structure
The project is structured as follows:

- **authuser**: Microservice handling authentication and user-related logic.
- **course**: Microservice responsible for managing course-related operations.
- **notification**: Microservice handling asynchronous notifications.
- **service-registry**: Eureka microservice for service discovery.
- **api-gateway** : API Gateway for routing requests to the appropriate microservices.
- **config-server**: Configuration Server hosting configurations on GitHub.
## Setup and Installation
### Prerequisites
- Java 11 installed
- Docker and Docker Compose installed
- GitHub account for configuration server
### Steps
Clone the Repository \
`git clone git@github.com:isabelanog/distance-learning-platform.git` 

Then, in the root repository run the follow command to build the Docker Images and starts the containers for each microservice \
`docker compose up --build` 

## Access the services
- **authuser** : http://localhost:8087/dlp-authuser/swagger-ui.html#/
- **course**: http://localhost:8082/dlp-course/swagger-ui.html
- **notification**:  http://localhost:8084/dlp-notification/swagger-ui.html#/
## Service Dependencies
- **Eureka**: Service discovery at http://localhost:8761
- **API Gateway** : Routing requests to microservices at http://localhost:8080
- **Config Server**: Configuration server at http://localhost:8888
