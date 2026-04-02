# CourseFy API

**Contributors**: Aroa Ochoa (GitHub: kiraStark)

CourseFy is a group project developed for university using the SCRUM methodology.

It consists of a REST API built with Spring Boot to manage an online course platform.
The project allows managing courses, users, categories, languages, lessons, enrollments, and reviews, as well as performing searches and queries on student progress.

### Main features

* Create, view, update, and delete courses
* Register and list users
* Manage categories and languages
* Create and update lessons
* Enroll users in courses
* Mark lessons as completed
* Create and update reviews
* Query courses by category, language, last update date, and other criteria
* Get rankings of best teachers and students

### Technologies used

* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* Hibernate Validation
* Swagger / OpenAPI
* H2 Database and MySQL
* Lombok

### General structure

The project follows a layered architecture:
* **api/**: REST controllers.
* **application/**: services, DTOs, and exceptions.
* **domain/**: business model entities.
* **persistence/**: data access repositories.
* **security/**: configuration and authentication.
* **utils/**: utilities and data initialization.
