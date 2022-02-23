# Camunda Forms Extension

***This project is developed under the MSc thesis called "Extending support for data types in Camunda user forms" at Faculty of Technical Sciences in Novi Sad.***

Project consists of two parts:
* *Camunda Forms Extension* - Java Archive (backend plugin)
* *Form Generator* - JavaScript file (frontend plugin)

The main goal of this project was to extend the applicability of Camunda's *Generated Task Forms*. Many missing field types and validations are added to support a wider range of real-world business processes. Another improvement is that now *enum* field options can be set dynamically, and more than one option can be chosen by the user.

## Getting Started

At the root of this repository, there are .jar and .js files ready to be used in another application. For easier understanding of how those files (plugins) should be used with your application, an example application that demonstrates the usage of those files in practice is also included. Additionally, the source code of the *Camunda Forms Extension* is available as well.

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

To get started, clone the project to your local directory:
```
$ git clone https://github.com/NikolaBrodic/camunda-forms-extension.git
```

### Prerequisites

You need to install following software to be able to view and run the project:
* Camunda Modeler 4.9.0 [*(download link)*](https://downloads.camunda.cloud/release/camunda-modeler/4.9.0/)
* Java JDK v11 [*(download link)*](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
* Apache Maven [*(download link)*](https://maven.apache.org/download.cgi) (or use IDE of your choice, such as IntelliJ Idea, Eclipse etc.)
* MySQL v8.0.x [*(download link)*](https://dev.mysql.com/downloads/mysql/)

### Using

#### Testing *Camunda Forms Extension/Vehicle Inspection App*

If you are using *Apache Maven* to run the Spring Boot applications, go inside the application directory, open a console and type:
```
mvn spring-boot:run
```
This command will start the desired application.

If you are using some *IDE*, just open/import the application directory in it and run the project.

#### Adding *Camunda Forms Extension* Plugin

1. Copy `camunda-forms-extension-1.0.0.jar` to the `src\main\resources\lib\` in your application
2. Add dependency to `pom.xml`

   ```
   <dependency>
      <groupId>rs.ac.uns.ftn.camundaformsextension</groupId>
      <artifactId>camunda-forms-extension</artifactId>
      <version>1.0.0</version>
      <scope>system</scope>
      <systemPath>${basedir}/src/main/resources/lib/camunda-forms-extension-1.0.0.jar</systemPath>
   </dependency>
   ```
   *NOTE: It is required to add dependencies for Spring Data JPA and MySQL inside `pom.xml` as well.*
   
3. In `spring-boot-maven-plugin`, set system scope to true

   ```
   <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <version>${project.parent.version}</version>
      <configuration>
         <includeSystemScope>true</includeSystemScope>
      </configuration>
   </plugin>
   ```
4. Create configuration bean to enable dependency injection

   ```
   @Configuration
   @ComponentScan("rs.ac.uns.ftn.camundaformsextension")
   public class CamundaPluginConfig {
      // leave empty
   }
   ```
#### Adding *Form Generator* Plugin

1. Copy `form-generator.js` file to the desired location in your application (e.g. `src\main\resources\js\`)
2. Call `generateFormFields()` function after getting form's configuration. For example:

   ```
   fetch(camundaUrl + `/form-data?piId=${procInstanceId}&taskId=${taskId}`)
      .then(response => response.json())
      .then(json => generateFormFields(formId, json, setCssClasses(), true));
   ```

## Built With

* [Spring Boot](https://spring.io/)
* [JavaScript](https://www.javascript.com/)
* [Maven](https://maven.apache.org/)
* [MySQL](https://www.mysql.com/)
* [Camunda Modeler](https://camunda.com/download/modeler/)

## Author

* [Nikola Brodić](https://github.com/NikolaBrodic), MSc student at Faculty of Technical Sciences in Novi Sad
