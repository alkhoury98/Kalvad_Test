## Kalvad_Test (Delivery Application Crud)

## Descrption:
This project is a Java 11 implementation of an API for a delivery company, specifically designed to manage customer information through a delivery form. The API provides endpoints for retrieving, creating, and managing customer data, including addresses.

## Technologies used: 
Spring Boot: Used for building the API.
Maven: Dependency management and build tool.
Mysql: Database management system. 
docker: Docker is a platform and tool designed to make it easier to create, deploy, and run applications by using containers.

## How to Run

1. Ensure you have Java 11 installed on your system.
2. Ensure you have Mysql installed on your system.
3. Clone this repository.
4. Build and run the application.

## How to Run using docker   

1. Ensure you have Java 11 installed on your system.
2. Clone this repository.
3. Make sure you have Maven installed, then navigate to the project root directory and run the following command to build the application.
4. Run with Docker Compose using this command -> docker-compose up.

## NOTES:
-The first time you run the application, it is recommended to perform a Maven clean install to ensure that all dependencies are resolved and the Docker image is properly built.
-Ensure that Docker Desktop is opened on your machine before running the docker-compose up command.
-there is a postman collection attached to the project you can use. 

## Access the Application
once your application is running without using docker, you can access it at http://localhost:8080
if you are using docker, you can access it at http://localhost:9090

