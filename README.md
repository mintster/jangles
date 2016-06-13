Java Jangles
============

Java Jangles is a project featuring java application functions like caching, logging, the data provider pattern, web services, user authentication and other common practices. It has client projects like [Jangles-Web](https://github.com/mintster/jangles-java-web) which references Java Jangles Core. Jangles also serves as a vehicle for me to transfer many of the coding patterns from my 10+ years of .NET to Java.

###  Jangles Features Documentation

Java Jangles is documented extensively at <http://nixmash.com.> Here is a list of Jangles posts to date:

* [Creating a Janglesweb WAR using Ant build files referencing Jangles Core and its reference library JARs](http://nixmash.com/java/referencing-project-libraries-in-wars-with-ant/)
* [Janglesweb JAX-WS Web Client Configuration](http://nixmash.com/java/jangles-jax-ws-web-client-project-online/)
* [Janglesweb JAX-WS Web Service Configuration](http://nixmash.com/java/jangles-jax-ws-web-service-project-now-on-github/)
* [Jangles Data Provider Model in Action](http://nixmash.com/mysql/the-java-jangles-data-provider-model-in-action/)
* [Using Log4j Logging Levels in Jangles](http://nixmash.com/java/changing-log4j-logging-levels-by-output-type/)
* [Jangles Caching Wrapper](http://nixmash.com/java/the-java-jangles-caching-wrapper/)
* [Configuring Apache2 Log4j and Commons Logging as used in Jangles](http://nixmash.com/java/configuring-apache-log4j-and-commons-logging/)
* [Using Property Resource Files as used in Jangles](http://nixmash.com/java/loading-java-property-resource-files/)

### Installation and Setup

Java Jangles is built in Eclipse. External Resources include the postgresql jdbc, mysql connector, log4j and others JARs found in the /lib directory. You can build the project with the Ant build.xml, though because of dependent projects like Janglesweb the build.xml may require slight modification.

The database backend of Jangles is MySQL and PostgreSQL, switchable through a Data Provider Model and a configuration file which specifies which backend is to be used. Setup Scripts for both MySQL and PostgreSQL are available in /src/main/sql.

### Package Explorer

Here is a snapshot of the Eclipse Package Explorer.

![This is the caption](http://nixmash.com/x/pics/github/jangles0106.png)





