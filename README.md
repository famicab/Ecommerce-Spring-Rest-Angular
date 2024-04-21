# Spring Boot eCommerce Project

This is a simple Spring Boot project for an eCommerce website, made for learning purposes. This project is based on the Spring Security course on Open Webinars platform, taught by Luis Miguel López.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)

## Features

- User authentication using JWT with role-based access control (admin and user).
- Create, read and modify Products.
- Create, read and delete Orders.
- Documented with Swagger UI.

## Technologies

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA + Hibernate
- JWT
- H2 Database
- Swagger

## Installation
1. Clone the repository.
2. Navigate to the project directory.
3. Build the project.

## Usage
1. Run the Spring Boot application.
2. Open your web browser and go to [http://localhost:8080](http://localhost:8080) to access the application.
3. Test all operations with Swagger [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#).

## API Endpoints

### Authentication

**POST** `/auth/login`: Login the user.

**GET** `/user/me`: Retrieve your personal data. Requires authentication.

**POST** `/user/`: Register a new user.

**PATCH** `/user/edit`: Change password. Requires authentication.

### Product

**GET** `/product/{id}`: Select a product based on the id. Inserted values range from 1 to 30.

**PUT** `/product/{id}`: Change a product name or price. Requires authentication.

**GET** `/product`: Search a product. Filter by name(contains) and/or price (lower than).

**POST** `/product`: Insert a new product. Requires authentication (as admin:Admin1).

### Order

**GET** `/order/`: Retrieves every order associated to the user. As admin:Admin1, retrieves all the orders. Requires authentication.

**POST** `/order/`: Creates a new order. Requires authentication.

**DELETE** `/order/{id}`: Deletes an order. Requires authentication (as admin:Admin1).

  
