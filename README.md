# recipe-app

## Description
It is an ingredient and recipe management application.

### Features
 - create users with the following roles: `ROLE_ADMIN`, `ROLE_COOK` and `ROLE_BASIC_USER`
 - CRUD operations on ingredients from pantry - allowed only for users with `ROLE_COOK`
 - CRUD operations on recipes with type:
    - `PUBLIC` 
        - received filtered list (by name, difficulty and cooking time) for any authenticated user
        - add new recipe - allowed for users with `ROLE_ADMIN`, `ROLE_COOK`
        - update recipe - allowed for users with `ROLE_ADMIN`, `ROLE_COOK`
        - delete recipe  - allowed for users with `ROLE_ADMIN`, `ROLE_COOK`
    - `PRIVATE`
        - received filtered list (by name, difficulty and cooking time) for any user with `ROLE_COOK`. This list contains all the public recipes and the ones created by th logged-in user
        - add new recipe - allowed for users with `ROLE_COOK`
        - update recipe - allowed for users with `ROLE_COOK`
        - delete recipe  - allowed for users with `ROLE_COOK`

## Tech stack
 - Java 16
 - Maven
 - Spring Boot 2.5 with Spring Web (REST Services), Spring Data, Spring Security
 - Microsoft MySQL 8
 - Junit 5
 - Mockito
 - Swagger
 - Postman

## Run locally
Run the application from your IDE. By default it will run on port `8080`.

#### Database cofiguration
The url, username and password are set in `application.properties`. Update them with your credentials.

## API Documentation
The documentation for the API was created using Swagger. To access it, run the application and open in any browser this url: `http://localhost:8080/swagger-ui.html`

## Postman collection
The collection to be imported in Postman can be found in `doc` directory.
