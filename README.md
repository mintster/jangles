Java Jangles
============

Java Jangles is a project featuring java application functions like caching, logging, the data provider pattern, web services, user authentication and other common practices. It also served as a vehicle for me to transition from many years of .NET development to Java as the patterns in Jangles are very similar to those used in .NET circa 2012.

###  Jangles Features Documentation

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

Jangles is good to go with MySQL and supports PostgreSQL. The setup Script for MySQL is in /docs. While PostgreSQL IS supported in the app, to use it you must first create a table based on the MySQL `jangles_users` script in your PostgreSQL database.

Configuration files are located in `/resources` as web as external to the app in a location you specify in `/resources/jangles.properties,` Examples of the `global.properties` and `connections.xml` configuration files are located in `/docs.`

### Package Explorer

Here is a snapshot of the Eclipse Package Explorer.

![This is the caption](http://nixmash.com/x/pics/github/jangles0209.png)


**Last Updated:** *2/9/17*



