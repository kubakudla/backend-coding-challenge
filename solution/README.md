README
====
How to run:

1) create mysql database 'expenses':

create database expenses;

2) set up db user/pass in application.properties

spring.datasource.username=
spring.datasource.password=

3) run backed from solution folder:

mvn package
java -jar target/expense.jar

4) run front end from the main folder:
npm install
gulp

5) available urls:

http://localhost:8080/ -> front end

http://localhost:8081/challenge/swagger-ui.html -> swagger
