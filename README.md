<img width="1561" height="224" alt="image" src="https://github.com/user-attachments/assets/046f5492-39c5-4aa6-bb64-d082f84703ff" /># Campus Course & Records Manager (CCRM)

A console-based *Java SE* application to manage students, courses, enrollments, and transcripts for an institute.  
This project demonstrates *OOP concepts, Java SE APIs, exception handling, design patterns, and file operations* — all without using a database (data is stored in in-memory lists).

---

## ✨ Features

- *Student Management*
  - Add, list, update, deactivate students
  - Print student profiles & transcripts  

- *Course Management*
  - Add, list, update, deactivate courses
  - Assign instructors, filter/search (Streams API)  

- *Enrollment & Grading*
  - Enroll/unenroll students (with max-credit rules)
  - Assign grades & compute GPA
  - Transcript generation (polymorphism, toString override)  

- *File Operations*
  - Import/export data (CSV-like plain files)  
  - Backup with timestamped folders (NIO.2, recursion for size)  

- *CLI Workflow*
  - Menu-driven console
  - Demonstrates if, switch, loops, breaks, continue, etc.  

- *Technical Coverage*
  - OOP: encapsulation, inheritance, abstraction, polymorphism  
  - Builder & Singleton patterns  
  - Checked & unchecked exceptions + custom exceptions  
  - Streams & lambdas  
  - NIO.2 file I/O + Date/Time API  

---

## Screenshots
![Screenshot 1](https://github.com/Yathonymous/CCRM/blob/main/ccrm/screenshots/Screenshot%202025-09-25%20005701.png?raw=true)
![Screenshot 2]((https://github.com/Yathonymous/CCRM/blob/main/ccrm/screenshots/Screenshot%202025-09-25%20005913.png?raw=true))
![Screenshot 3]((https://github.com/Yathonymous/CCRM/blob/main/ccrm/screenshots/Screenshot%202025-09-25%20005930.png?raw=true))
![Screenshot 4](https://github.com/Yathonymous/CCRM/blob/main/ccrm/screenshots/Screenshot%202025-09-25%20010040.png?raw=true)

## Demo Video Link
[ccrm demo](https://drive.google.com/drive/folders/1eDSKRyiiZAIkL7i2_KzbacGnSxX5pYm0)

## 🚀 How to Run

### Prerequisites
- Java SE 17+ installed  
- VS Code (with Java extensions) or any IDE  

### Steps
```bash
# Compile
cd src
javac edu/ccrm/cli/MainCLI.java

# Run
java edu.ccrm.cli.MainCLI
