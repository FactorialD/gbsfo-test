# Test project for GBSFO

# Requirements for this task
* Create Spring/Spring Boot application with REST-like architecture to handle HTTP-requests.
* Request and response format - JSON.
* Pay attention to project structure (model, service, api, etc).
* Data storage - choose any SQL/NoSQL solution, i.e. PostgreSQL or MongoDB.
* The output of this task should be a Git repository with source code, which contains README file with all the instructions to start your application locally.
* There are 2 entities: Order, Item and Payment. Each order can have multiple Payments and multiple Items.
* Items has next attributes: name, price.
* Payment has next attributes: number, sum, payment date and time.
* Order has next attributes: number, status, total of items, total of payments. Order has next lifecycle: CREATED, PROCESSING, SHIPPING, DELIVERED. Order can’t be moved to SHIPPING and DELIVERING statuses, until all items are paid. You can’t add items to Order with SHIPPING and DELIVERING status.
* CRUD operations for each entity.
* Handle all the possible scenarios (OK, BAD_REQUEST, NOT_FOUND, etc).

# How to start this application on local machine
### This project using java 17. For deploying it JDK 17 needed. Therefore, you must using Tomcat 11.0.XX or newer.
### Variant 1: Using IntelliJIdea 2022.xx or newer
* Using IntellijI, pull git repository with this project and run it using IDE-embedded server

### Variant 2: Using local Tomcat 11.xx.xx or newer
* Download and install Apache Tomcat. Add new user with required roles (if needed)
* Build this project using Maven (maven package)
* Install created war application in Tomcat
* Run it.

# Database
* Apllication is using MySql database. You need to have mysqld working with schema with name "sales_spring" and password "1111". You can change it in 'application.properties' file.
* This is bad password, you can change it in application.properties.
* Application configured to create all tables needed for work. But you can add some data, like statuses in "order_status" table.

# What I definitely want to improve
* Application controller methods doesn't have checks for statuses.
* Status table doesn't have fields for expiring and deleting management. Fields like 'attr_date_begin', 'attr_date_end' is needed. This is will help if need to stop using some fields, while not deleting it and leaving old records in that states.
* Application order flow need for new statuses, like CANCELLED, RETURNED.
* Some fields in Order table can be changed to Many-to-Many if need.
* In 'application.properties' file changing 'spring.jpa.hibernate.ddl-auto' to 'none' is needed.
* Also very important to encrypt database password using for ex. Jasypt.

# Contacts
* If you have any questions, send me letter on factoriald@gamil.com