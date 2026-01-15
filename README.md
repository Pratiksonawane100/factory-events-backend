# Backend Intern Assignment – Solvei8

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

## How to Run
1. mvn clean install
2. mvn spring-boot:run

## APIs
POST /events/batch  
GET /stats  
GET /stats/top-defect-lines  

## Testing
All APIs tested using Postman (screenshots attached).

## Notes
- Event deduplication handled using eventId
- Validation rules implemented as per assignment
