# Apache Drill Java and Spring Boot POC

This is a simple POC which demonstrates the use of Apache Drill to query data from various sources.

## Connection to Apache Drill

We can connect to Apache Drill using a JDBC connection string. In this project, the connection string
is in the ```application.properties``` file. You can change it there depending on where
your Apache Zookeeper or local instance of Drill is available.