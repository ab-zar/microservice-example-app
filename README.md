# microservice-example-app

1) The project has USERS with different roles: MANAGER, DEVELOPER, TESTER
2) The project includes several FEATURES and each one is split into TASKS.
3) Each entity has a status: OPEN, IN PROGRESS, RESOLVED, COMPLETED.
4) The TASK can have BUGS.

5)The project workflow consists of the following:

* The MANAGER creates FEATURES and TASKS and assigns the TASK to the DEVELOPER
* The DEVELOPER carries out the TASK and teansfers it to the TESTER under 'RESOLVED' status
* The TESTER tests the TASK and, if it's completed, closes it under 'COMPLETED' status. If it's not completed, they create a BUG and change the TASK's status back to 'IN PROGRESS'. A BUG is required to revert a TASK to 'IN PROGRESS'
* If all the FEATURE's TASKS are completed, the MANAGER can close the FEATURE. The TASK should be automatically transferred to the DEVELOPER when the TESTER returns the TASK to 'IN PROGRESS' status

This repository shows the development of a webApp (description see above) using microservices architecture. To implement this architecture, I used:
* Java 17
* The latest version of Spring Boot, Spring Boot 2.7.1
* Docker to package the application

Description of individual services:

Service-Gateway:
1) Serves as an access point to my application
2) Filters the request. If the endpoint is secured, requires a Token
3) Redirects to Service-User to verify the Token

Service-Discovery(Eureka)
1) Detecting services on a network
2) Enables cross-service communication

Service-User
1) Provides user registration and authentication
2) Provides JWT token
3) Stores USERS in the Database
4) Performs USER operations

Service-Feature
1) Performs FEATURE operations
2) Stores FEATURES in the Database

Service-Task
1) Performs TASK operations
2) Stores TASKS in the Database

Service-Bug
1) Performs BUG operations
2) Stores BUGS in the Database

I use Spring Feign Client for cross-service requests. All Feign clients are contained in a 'Clients' module. Each Service that requires a Feign Client has a dependency for client module in Pom.xml.

For data Storage I use PostgreSQL deployed in Docker (see docker-compose.yml). I also use Zipkin for request tracing.

In the future commits I'm planning to:
1) Find better security design and implement Authorization
2) Replace Feign Client with Spring WebClient
3) Find a better design for Data consistency
4) Add a Notification Service and RabbitMQ or Kafka message brokers
5) Cover my project with Unit tests
6) Pack each service into Docker containers and orchestrate them using MiniKube.
