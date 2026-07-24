# Institute Management System

A role-based desktop application for managing an educational institute, built as a JavaFX semester project at PIEAS.

## Overview

The system supports multiple user roles and manages several divisions of an institute from a single desktop app:

- **Roles:** Administrator, Student, Teacher, Applicant
- **Divisions:** Education, Hostel, Transport

## Features

- Login and role-based dashboards (Administrator, Student, Teacher)
- Applicant registration and application review (`Applicant`, `ApplicantPage`, `CreateApplicationPage`, `ViewApplicants`)
- Course viewing and enrollment (`Course`, `ViewCourses`, `EnrollCoursePage`)
- Fee tracking (`FeeEntity`, `ViewFee`)
- Hostel and room management (`Hostel`, `Room`, `HostelDivision`, `ViewHostels`)
- Transport route management (`Route`, `TransportDivision`, `ViewTransportRoutes`)
- Institute/teacher/student record views (`Institute`, `Teacher`, `TeacherPage`, `Student`, `StudentPage`, `ViewStudents`, `ViewTeachers`)
- Scene navigation between pages (`sceneManager`, `rootNode`)

## Tech Stack

- Java + JavaFX 13 (controls & FXML)
- SQLite (via `sqlite-jdbc`) for data persistence — `ims.db`
- Maven for build/dependency management

## Project Structure

```
institute_management_system/
├── pom.xml                          # Maven build configuration
├── ims.db                           # SQLite database file
└── src/main/
    ├── java/files/                  # Application source (pages, entities, dashboards)
    │   ├── App.java                 # Application entry point
    │   ├── LoginPage.java
    │   ├── AdministratorDashboard.java / AdministratorPage.java
    │   ├── StudentDashboard.java / StudentPage.java
    │   ├── TeacherPage.java
    │   ├── ApplicantPage.java / CreateApplicationPage.java
    │   ├── DatabaseManager.java     # SQLite data access
    │   └── ...                      # Divisions, entities, view pages
    └── resources/files/
        ├── primary.fxml
        ├── secondary.fxml
        └── style.css
```

## Building & Running

Requires Java and Maven, with JavaFX set up via the `javafx-maven-plugin`.

```bash
mvn clean javafx:run
```

## Notes

Developed as a course project to explore JavaFX (Scene Builder-style layouts, `TableView`, `GridPane`, `ObservableList`) alongside SQLite-based data persistence.
