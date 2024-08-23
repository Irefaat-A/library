# APOCALYPSE

## Summary
This fascinating project. Is about a robot APOCALYPSE.

..you can

Store information of the survivors and their resources. Trace there location and update it to keep up with their last known location. A full list of the roaming list is available. See full list below.


1. Create survivors.
2. Update there location.
3. Submit contamination request.
4. View all robots.
5. View a report of the survivors.

## Prerequisite

- IDE (eclipse/intelij)
- Java 21   [Official Java Oracle site](https://www.oracle.com/za/java/technologies/)
- Maven 3.9.2   [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

Postman (Or any REST client)

## Frameworks and Technologies

- Spring Boot 3.3.2
- H2 database
- Spring Devtools  [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#using.devtools)
- Lombok
- Swagger/OpenAPI
- junit
- hamcrest

## Application overview

This is a rest application that uses H2 as a database to store data. The exception handling has been centralised using springs @ControllerAdvice and deliver a standard error response. Logging is also been centralised making use of springs AOP @Pointcuts to make logging flexible. There is custom field annotation to validate data received is valid as early as possible. This is implement with 
@interface. The RestControllers is decorated with swagger annotations to provide a rest api documentation via swagger ui.    

```
RUN application 
Todo the below steps it's assumed you have Java and Maven installed on your computer.   
    
In the resources directory there is application.properties.

Here you are able to control:
    1. Log level (is currently on warn)
    2. active profiles
    3. set the h2 username and password
    
(Step by Step) to get Application running   
    1. Clone project.
    2. Open project in faourite IDE.
    3. After all the dependcies is done downloaded. Do a "maven clean" 
    4. Set the spring.datasource username and password in the application.properties to your prefence.
    5. Locate the Application class and run. 

The application will start on port 8080.

To view the H2 UI nevigate to:
http://localhost:8080/h2-console

Login with the credential you specified in the application.properties

Once you logged in you will be able to manage the data in the H2 db.

```

## Resources

```
In the project there is a docs folder that contains:

1. Postman collection (You can import the postman collection into your postman workspce)
2. Swaager UI nagivate to: http://localhost:8080/swagger-ui/index.html#/

```
