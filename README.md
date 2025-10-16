# 2025-CCE-E-DEV-010-DevelopmentBooks

This project implements the Development Books pricing exercise using Java and Spring Boot.  

## Requirements
- Java 21 or higher
- Maven 3.9+

## Build and Run

### 1. Compile and test
````
mvn clean test
````

### 2. Run the application
````
mvn spring-boot:run
````

The application will start on http://localhost:8080

## Endpoints

### GET /api/books/catalog
Returns all available book titles.

````
http://localhost:8080/api/books/catalog

Response:

["CLEAN_CODE",
"THE_CLEAN_CODER",
"CLEAN_ARCHITECTURE",
"TDD_BY_EXAMPLE",
"WORKING_EFFECTIVELY_WITH_LEGACY_CODE"]
````

### POST /api/books/pricing
Calculates the total price of a basket of books.

````
http://localhost:8080/api/books/pricing 

{
  "items": [
    {"title": "CLEAN_CODE", "quantity": 2},
    {"title": "THE_CLEAN_CODER", "quantity": 2},
    {"title": "CLEAN_ARCHITECTURE", "quantity": 2},
    {"title": "TDD_BY_EXAMPLE", "quantity": 1},
    {"title": "WORKING_EFFECTIVELY_WITH_LEGACY_CODE", "quantity": 1}
  ]
}

Expected response:

{"total": 320.00}
````

