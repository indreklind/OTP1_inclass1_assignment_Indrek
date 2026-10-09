# Temperature Converter Application

---

## 1. Assignment Description
- **Problem Statement:** Building an accurate full stack temperature conversion application with containerized desktop application.
  
- **Key Requirements:**
  - **Task 1:** Create TemperatureConverter class with unit tests.
  - **Task 2:** Generate and analyze code coverage report of the TemperatureConverter class.
  - **Task 3:** Extend the project with Kelvin-to-Celsius functionality and set Up Jenkins and JaCoCo Report.
  - **Task 5:** Add Jenkinsfile(pipeline) and build local Docker Image.
  - **Task 6:** Convert project in to JavaFX app and run the app both locally and as Docker Image. 
 
---

## 2. Technologies & Tools Used
- **Languages:** Java (JDK 21)
- **Libraries:** JavaFX 21, MariaDB
- **Tools:** IntelliJ IDEA, Apache Maven, Jenkins, Docker, Xming
- **Testing software:** JUnit 5, JaCoCo

---

## 3. Design Approach & Implementation Method
The development followed an evolutionary refactoring approach, evolving from a simple initial prototype to a modular application

---

## 4. Testing & Quality Assurance Steps
- **Automated Testing:** JUnit 5 test classes (`TemperatureConverterTest`).
- **Code Coverage Verification:** JaCoCo is executed during the Maven test to make sure that branches in `isExtremeTemperature` are fully tested.

---

## 5. How to Run

**Step 1:** Clone repository

**Step 2:**. Run unit tests and generate JaCoCo coverage report
mvn clean test

**Step 3:**. View the generated coverage report
Open target/site/jacoco/index.html in your browser

**Step 4:**. Launch the JavaFX application
mvn javafx:run
