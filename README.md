Jangles
============

Jangles is a small Java application built in Linux. It supports caching, logging, dual MySQL / H2 connections, external properties and connection config files, Guice injection, localization and more. Jangles is designed as a shared codebase for Java Distributed Applications.

### Technologies Used

- Guice injection
- Apache Shiro for Authentication
- Apache JCS Caching
- slf4j with Logback for logging
- Tomcat JDBC Connection Pooling
- JAXB Marshalling for External Configuration files

### Installation and Setup

Jangles uses MySQL for development and H2 for testing. **schema.sql** and **data.sql** scripts for MySQL are in `/install/sql.`  Test SQL script is located in `/test/resources` which runs on test launch.

Jangles uses external Property and Connection Configuration files,`global.properties` and `connections.xml`.  A `jangles.properties` file is located in `/resources` where you will enter the path of the external files. *This location is relative to your home directory.*  

Here is a summary of the setup process:

1. Create your MySQL database and run the MySQL Setup scripts in `/install/sql.`
3. Configure and copy the external property files in`install/external` to a subfolder of your home directory
4. Enter the path to those external files (`global.properties` and `connections.xml`) in `/resources/jangles.properties`

### To Run Application and Tests

After completing the setup steps listed above, use Maven or your IDE to run the Jangles Demo which displays a simple "Hello!" and some configuration info. All Tests should run Green.

Maven is configured to create a working .JAR file on running

```bash
{PROJECT_ROOT}/$ mvn clean install
```
You can then run the JAR with

```bash
{PROJECT_ROOT}/$ java -jar target/jangles.jar
```

**Last Updated:** *8/28/17*



