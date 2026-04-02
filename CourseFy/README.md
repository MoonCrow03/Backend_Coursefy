# CourseFy API

**Colaboradores**: Aroa Ochoa (GitHub: kiraStark) y Guillem Sabaté (GitHub: GuillemSabGou)

CourseFy es un proyecto realizado en grupo para la universidad utilizando la metodología SCRUM.

Consiste en una API REST desarrollada con Spring Boot para gestionar una plataforma de cursos online.
El proyecto permite administrar cursos, usuarios, categorías, idiomas, lecciones, matrículas y reseñas, además de realizar búsquedas y consultas sobre el progreso de los estudiantes.

### Funcionalidades principales

* Crear, consultar, actualizar y eliminar cursos
* Registrar y listar usuarios
* Gestionar categorías e idiomas
* Crear y actualizar lecciones
* Inscribir usuarios en cursos
* Marcar lecciones como completadas
* Crear y actualizar reseñas
* Consultar cursos por categoría, idioma, fecha de actualización y otros criterios
* Obtener rankings de mejores profesores y estudiantes

### Tecnologías utilizadas

* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* Hibernate Validation
* Swagger / OpenAPI
* H2 Database y MySQL
* Lombok

### Estructura general

El proyecto sigue una arquitectura por capas:
* **api/**: controladores REST.
* **application/**: servicios, DTOs y excepciones.
* **domain/**: entidades del modelo de negocio.
* **persistence/**: repositorios de acceso a datos.
* **security/**: configuración y autenticación.
* **utils/**: utilidades e inicialización de datos.