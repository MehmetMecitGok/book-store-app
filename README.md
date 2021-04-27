# Book Store App

* Book Store Webservice, Spring Boot, Gradle, Spring Swcurity, Authentication, Java Restful Web API Project.
* Application will start at port 8080

## Requirements

* Java 11
* Spring Boot 2.4.5
* PostgreSql latest version
* Json Web Token 

## Run with Docker
```bash
docker-compose up --build
```

## Authentication Rest APIs
```java
POST /authenticate             # Login with email and password and get json web token
```
## Customer Rest APIs
```java
POST /customer/signup          # Sign-up with firstName, LastName, email and password
```
## Order Rest APIs
```java
POST /order/new                # Cretae new order with token and bookId
GET  /order/details            # Get order details with token and orderId
GET  /order/all                # Get all order with token and user authentication info
```
## Book Rest APIs
```java
POST /book/save                # Cretae new book with book name, stock, price
GET  /book/details             # Get book details with book id