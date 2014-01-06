java-jangles
============

Java Jangles is a project intended to feature java application functions like caching, logging, the data provider pattern, web services, user authentication and other common practices.

According to the [Urban Dictionary,](http://www.urbandictionary.com/define.php?term=jangles) Jangles is defined as

_A “flavor word” added after absolutely any other noun to make your sentence cooler than anyone else’s._

That’s actually a good description of Java Jangles. Unlike my other GitHub repositories which have clear functions, Java Jangles is, at least initially,  a “flavor application” used to demonstrate various Java practices that I find useful and interesting.

Jangles also serves as a vehicle for me to transfer many of the coding patterns from .NET to Java that I used during my 10+ years as a .NET developer.

###  Jangles as Blogging Fodder

I will be blogging on Java Jangles while it is being developed, which is another function of Jangles. If you are interested in the description that accompanies Java Jangles updates, please visit my blog at <http://nixmash.com.>

### Installation and Setup

Java Jangles is built in Eclipse. External Resources include the postgresql jdbc, mysql connector, log4j and others. See the Ant build.xml for the Referenced Libraries.

The database backend of Jangles is MySQL and PostgreSQL, switchable through a Data Provider Model and a configuration file which specifies which backend is to be used. Setup Scripts for both MySQL and PostgreSQL are available in /src/main/sql.

### Package Explorer

Here is a snapshot of the Eclipse Package Explorer.


![This is the caption](http://nixmash.com/x/pics/github/jangles0106.png)





