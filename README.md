Jangles
============

Jangles is a very lean Java application supporting caching, logging, dual Development/Testing MySQL connections, external global properties and connections configuration, localization and more. Jangles is intended to be used as a shared codebase for Java Microservice-structured Applications.

 ## NixMash Posts by Branch
 
  ### v2.0.0-Release-Two (in progress)
  
  * *Coming...*
  
 ### v1.0.0-Release-One
 
* [Creating a Janglesweb WAR using Ant build files referencing Jangles Core and its reference library JARs](http://nixmash.com/java/referencing-project-libraries-in-wars-with-ant/)
* [Janglesweb JAX-WS Web Client Configuration](http://nixmash.com/java/jangles-jax-ws-web-client-project-online/)
* [Janglesweb JAX-WS Web Service Configuration](http://nixmash.com/java/jangles-jax-ws-web-service-project-now-on-github/)
* [Jangles Data Provider Model in Action](http://nixmash.com/mysql/the-java-jangles-data-provider-model-in-action/)
* [Using Log4j Logging Levels in Jangles](http://nixmash.com/java/changing-log4j-logging-levels-by-output-type/)
* [Jangles Caching Wrapper](http://nixmash.com/java/the-java-jangles-caching-wrapper/)
* [Configuring Apache2 Log4j and Commons Logging as used in Jangles](http://nixmash.com/java/configuring-apache-log4j-and-commons-logging/)
* [Using Property Resource Files as used in Jangles](http://nixmash.com/java/loading-java-property-resource-files/)

### Installation and Setup

Jangles uses a Primary MySQL database and a Second Test MySQL database to support Stored Procedures and Functions (since H2 does not). **schema.sql** and **data.sql** scripts are in `/install/sql.` 

Run **schema.sql** in *both* Primary and Test MySQL databases, run **data.sql** *in Primary database only*. MySQL Scripts for tests are located in `/test/resources` and run automatically.

Jangles uses an external Global Properties file and Connections.xml file,`global.properties` and `connections.xml`.  The files are initially located in `/install/external.` Enter the appropriate MySQL Connection information and copy them *to a subdirectory of your HOME FOLDER.* A `jangles.properties` file is located in `/resources` where your final step is entering the location of the external Global Properties and Connection files. The location will be relative to your *HOME FOLDER.*  

Here is a summary of the setup process:

1. Setup your Primary and Test MySQL databases. 
2. Run the MySQL Setup scripts in `/install/sql`  (*schema.sql* and *data.sql* in Primary, then *schema.sql* in the Test DB.)
3. Copy the files in`install/external` to a location outside your application 
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

**Last Updated:** *7/07/17*



