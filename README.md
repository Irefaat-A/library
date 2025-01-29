# ONLINE LIBRARY

## Summary
Providing a online platform to manage book inventory

...you can

Add, Update, Remove books. You can search easily for your favourite book and rent it.

1. Add a book.
2. Update a book.
3. Remove a book.
4. View all books.
5. Search for a book.
6. Rent a book.

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

This is a restFul application that uses H2 as a database to store data. The exception handling has been centralised using springs @ControllerAdvice and deliver a standard error response. Logging is also been centralised making use of springs AOP @Pointcuts to make logging flexible. There is custom field annotation to validate data as early as possible. This is implement with 
@interface. The RestControllers is decorated with swagger annotations to provide a rest api documentation via swagger ui.    

```
RUN application 
Todo the below steps it's assumed you have Java and Maven installed on your computer.   
    
In the resources directory there is application.properties.

Here you are able to control:
    1. Log level (is currently on debug)
    2. active profiles
    3. set the h2 username and password
    
(Step by Step) to get Application running   
    1. Clone project.
    2. Open project in favourite IDE.
    3. After all the dependcies is done downloaded. Do a "maven clean" 
    4. Set the spring.datasource username and password in the application.properties to your prefence.
    5. Locate the Application class and run. 

The application will start on port 8080.

To view the H2 UI nevigate to:
http://localhost:8080/h2-console

Login with the credential you specified in the application.properties (spring.datasource username and password)

Once you logged in you will be able to manage the data in the H2 db.

```

## Resources

```
In the project there is a docs folder that contains:

1. Swaager UI nagivate to: http://localhost:8080/swagger-ui/index.html#/

```
