Datapine Java Developer Test (Solution)
=======================================
Test for [Datapine](https://www.datapine.com/)


This solution uses SpringBoot 1.4.3 and Java 8. It's very simple to package or run

It uses maven 3 so you can:
- run using command-line ```mvn spring-boot:run```
- create a fat jar using ```mvn clean package```
- to run the package you will use ```java -jar datapine_test.jar```


It uses an embedded jetty and an embedded H2 DB instance.

- the app will be available on port 8080 (http://localhost:8080)
- the H2 console: http://localhost:8080/h2-console
- the JDBC URL: jdbc:h2:mem:testdb

There are two users created at startup

- login: admin
- password: admin
- access level: ROLE_ADMIN

- login: user
- password: user
- access level: ROLE_USER

Using the endpoint http://localhost:8080/register is possible to create new users with ROLE_USER access level.


=============================================


**INSTRUCTIONS**:

You will now receive an application skeleton with a number of tasks to complete. The application is a web application, which uses Maven, JPA and Spring. It comes with an embedded H2 database and the Maven Jetty plugin configured, so running it should be easy.

You should clone this git repository https://github.com/datamint/datapine-test-app and work on the following tasks.


Your tasks:

- Implement the UserDAO using JPA.
- Implement the UserService using the UserDAO and transactions.
- Implement a RESTful UserController to manage users.
- Implement a simple user login dialog to login a user. Use HttpSession to store the user credentials to save time, Spring Security can also be used.
- Use Spring AOP to log all login attempts via console output or log4j.


Big Bonus:

- In addition to securing the URLs, secure access to ItemDAO (has to be created along side with Item domain) via spring security.
- Restrict ItemDAO access if user is not authenticated by session. Filter any findBy~ and findAll method within ItemDAO. ACL has to be used in order to secure domain.
- You can either create an API or prepare jUnit tests for adding&viewing Item object (only authenticated users can insert and view objects).


Flow to follow:

- when a user is registered, create domain object as it is.
- when an item is added, create domain object as well as an Acl entry for item domain
- when item object is requested via find* methods filter out non authorized items. //item object must linked to user domain (acl_sid table has User.id as sid).

 
ACL domain security is actively used in datapine backend, so completing big bonus is most likely to be decisive for our application process. ItemDAO can include any attribute (it is just used to demonstrate ACL usage). Overall the test is fairly basic and straight forward and should give you enough room to show off some great things around it.
