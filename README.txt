CRM REST project (updated)
--------------------------

What's included:
 - gradlew      (shell stub)
 - gradlew.bat  (cmd stub)
 - gradle/wrapper/gradle-wrapper.properties
 - full project source (src/)
 - build.gradle, settings.gradle
 - src/main/resources/data.sql
 - crm-rest.postman_collection.json (Postman collection for testing)

Important note about Gradle Wrapper:
 - For the "real" Gradle wrapper to work (download and run Gradle automatically), the file
   gradle/wrapper/gradle-wrapper.jar must be present. I could not include the binary jar here.
 - If you have internet and Gradle installed, running './gradlew bootRun' will use system Gradle.
 - To run without installing Gradle, either:
     a) install Gradle, or
     b) run the app from your IDE (Open CrmRestApplication and Run), or
     c) ask me and I will produce a build artifact (.jar) you can run with 'java -jar'.

Running via IDE (recommended):
 - Open project in VS Code or IntelliJ
 - Run class com.example.crm.CrmRestApplication (main method)
 - H2 console at http://localhost:8080/h2-console (jdbc:h2:mem:crmdb, user sa)

Postman collection import file: crm-rest.postman_collection.json
