Java Jangles
============

Jangles is a very lean Java application with basic features like caching, logging, dual MySQL / H2 JDBC configuration, global properties and more. It is intended to be used as a codebase for Java Applications.

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

Jangles is good to go with MySQL and H2 for testing. The setup Script for MySQL is in `/install/sql.` H2 scripts are in `/test/resources.`  A `jangles.properties` file is located in `/resources`  Global Properties and Connections are defined externally to the application in `global.properties` and `connections.xml` respectfully. Examples are located in `/install/samples.`

After you listed the path to `connections.xml` in `/resources/jangles.properties`, configured the MySQL Connection in `connections.xml` and run the MySQL Setup script in `/install/sql` you can use Maven or your IDE to run the Jangles Demo which does nothing more than display a list of users from the MySQL database.

**Last Updated:** *6/11/17*



