# REST-API-Automation
This is a REST API testing project designed to test Swagger pet store API using Rest Assured and TestNG framework. It uses Maven for build compilation & dependency management. This guide provides a comprehensive overview of the project structure, setup prerequisites, and instructions to run test cases while accessing logs and reports.

## Prerequisites
Before starting with the project, ensure the following are installed on your system:
1. Java (LTS) - Verify by running java ``` -version ``` in the terminal.
2. Eclipse IDE

## Getting Started
1. Clone the repository to your local machine.
```bash
  git clone https://github.com/Bipasha0431/REST-API-Automation
```
2. Open the project in Eclipse IDE.
   
3. Ensure all the dependencies are downloaded by Maven by updating the project.
   
    ``
    Right-click the project in Eclipse and select Maven > Update Project
  .``
## Project Structure
![image](https://github.com/user-attachments/assets/76a2d12e-2e4e-48ff-96e4-c020fc690e1b)

1. **src/test/java**
   
   - api.endpoints - Contains all the API endpoint definitions and CRUD methods for interacting with the pet, store and user API
   - api.test - Contains all test cases that execute various scenarios.
   - api.utils - Includes extent report file to generate report and fileUtils file for reading JSON payload files.
      
2. **src/test/resources** : Contains JSON files for storing payload and configuration file for logging.
3. **Maven Dependencies** : All the dependency JAR files installed via pom.xml will be stored here.
4. **logs/**- Store log files for each test case in a timestamped format.
5. **report/** - Contains Extent Report
6. **pom.xml** - Maven configuration file. Lists all required dependencies for the project.
7. **testng.xml**: Configuration file to define and organize test suites and test cases.

### Execute Tests
- Run the testng.xml file as a TestNG suite. This will execute all the tests.
- After running the tests, the log files will be generated in the logs folder.
- The Extent Report will be saved in the reports folder, where you can access the test execution details.

### Log files
Logs are generated using Log4j2, recording detailed execution and are stored in the logs folder for each test class.

### Generating Reports
- **Extent Report**: The Extent Report will be saved in the reports folder, providing detailed insights into the test execution, including passed/failed test cases .
- **TestNG Report**: The TestNG Report is generated in the test-output folder and provides a summary of the test execution, including individual test results. Emailable TestNG Report can be accessed from the test-output folder.

#### Extent Report
![image](https://github.com/user-attachments/assets/207ab744-20fa-48c7-a6c7-ec09f98e07e0)

#### TestNG Report
![image](https://github.com/user-attachments/assets/7650da16-9ae2-4377-89e6-9142a83315bb)


**Note** - Mentioning some of the test cases where the request is processed as valid despite the invalid input.

1.The API should return an error when the name or photoUrls fields are missing, or when the status contains invalid values. However, the API does not return the expected error for missing name or invalid status.

  * Eg- Send a request to create a new pet with the following invalid payload:
      - Omit the name field (e.g., name: null or name is missing).
      - Provide an invalid status value that is not part of the allowed enum values.
        
2. When updating a user by passing invalid username i.e with a non-existent username, the API should return an error or a user not found message but it creates a new pet with the provided payload.

        
