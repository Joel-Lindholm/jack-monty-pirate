# Getting Started

This is a small work sample project

It is build in Spring Boot, and only has one endpoint **jack-monty**

It accepts a number of islands and a number that excludes a number of islands
localhost:8082/jack-monty?islands=X&excluded=Y

The result will be a holder with two booleans

## Start the server
Simply run the JackMontyApplication

    ./mvnw spring-boot:run

Will start the application on port 8082