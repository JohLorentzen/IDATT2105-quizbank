
# Quiz Bank Web Application

This project is a full-stack web application designed for creating, managing, and taking quizzes. It utilizes Vue.js for the frontend and Spring Boot for the backend, with H2 as the database.

## Getting Started

These instructions will get your copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 21
- Node.js and npm
- H2 Database (Included for development)
- Maven (for backend)

### Setting Up the Backend

1. **Navigate to the Backend Directory:**
```
   $ cd project-root
```
2. **Build the Project with Maven:**

   This command compiles the project and downloads the necessary dependencies.

```
   $ mvn clean install
```

3. **Run the Spring Boot Application:**

```
   $ mvn spring-boot:run
```

   The backend server will start on `http://localhost:8080`.

### Setting Up the Frontend

1. **Navigate to the Frontend Directory:**
```
   $ cd project-root/frontend
```
2. **Install Dependencies:**

```
   $ npm install
```

3. **Build the Application:**

   For a production build, run:

```
   $ npm run build
```

4. **Preview the Production Build:**

   To preview the production build on your local machine, use:

```
   $ npm run preview
```

   The application will be available at `http://localhost:4173`.

### Running Tests

- **Backend Test:**

  Navigate to backend:
```
   $ cd project-root
```

  Run backend tests using Maven:


```
   $ mvn test
```

- **Frontend Tests:**

  Navigate to the frontend root folder:
```
   $ cd project-root/frontend
```


   and run the commands:

```
   $ npm run test:unit
```

  For end-to-end tests:

```
   $ npm run test:e2e
```

### Manual Testing

  - #### Test Users
For testing purposes, the following user accounts are available:

- **Main Test User:**
  - Username: `testuser`
  - Password: `password`

- **Other Users:**
  - Username: `testuser1`
  - Password: `password`
  - Username: `testuser2`
  - Password: `password`

### Documentation

- Backend API documentation is available at `http://localhost:8080/swagger-ui.html` after starting the backend server.




## Authors

Johannes, Sondre and Jens
