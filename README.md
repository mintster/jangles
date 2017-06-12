Java Jangles
============

Jangles is a very lean Java application with basic features like caching, logging, dual Development/Testing MySQL JDBC configuration, global properties and more. It is intended to be used as a codebase for Java Applications.

###  Jangles Posts on NixMash

Several posts at <http://nixmash.com> covered code in Java Jangles. Here is a partial list:

* [Creating a Janglesweb WAR using Ant build files referencing Jangles Core and its reference library JARs](http://nixmash.com/java/referencing-project-libraries-in-wars-with-ant/)
* [Janglesweb JAX-WS Web Client Configuration](http://nixmash.com/java/jangles-jax-ws-web-client-project-online/)
* [Janglesweb JAX-WS Web Service Configuration](http://nixmash.com/java/jangles-jax-ws-web-service-project-now-on-github/)
* [Jangles Data Provider Model in Action](http://nixmash.com/mysql/the-java-jangles-data-provider-model-in-action/)
* [Using Log4j Logging Levels in Jangles](http://nixmash.com/java/changing-log4j-logging-levels-by-output-type/)
* [Jangles Caching Wrapper](http://nixmash.com/java/the-java-jangles-caching-wrapper/)
* [Configuring Apache2 Log4j and Commons Logging as used in Jangles](http://nixmash.com/java/configuring-apache-log4j-and-commons-logging/)
* [Using Property Resource Files as used in Jangles](http://nixmash.com/java/loading-java-property-resource-files/)

### Installation and Setup

Jangles is configured to use a Primary MySQL database and a Second MySQL database for testing (this is to support Stored Procedures and Functions). **schema.sql** and **data.sql** scripts are in `/install/sql.` Run **schema.sql** in *both* Primary and Test MySQL databases. MySQL Test Scripts are located in `/test/resources` and run automatically in tests.  Please note to include the string *"test"* in your Test MySQL database name so all Data Connection tests pass. Database name examples: *Development: **janglesdb***, *Testing: **janglestestdb***.

A `jangles.properties` file is located in `/resources`  where you define the location of the external Global Properties and Connection files`global.properties` and `connections.xml` respectfully. Examples of these files are located in `/install/samples.`

### To Run Application and Tests

After you listed the path to `connections.xml` in `/resources/jangles.properties`, configured Development and Test MySQL Connections in `connections.xml` and run the MySQL Setup scripts in `/install/sql`  (*schema.sql* and *data.sql* in Development, then *schema.sql* in the Test DB) you can use Maven or your IDE to run the Jangles Demo which displays a list of users from the MySQL database. All Tests should run Green.

**Last Updated:** *6/11/17*



