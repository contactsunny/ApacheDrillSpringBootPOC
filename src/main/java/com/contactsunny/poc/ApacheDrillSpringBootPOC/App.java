package com.contactsunny.poc.ApacheDrillSpringBootPOC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Value("${drill.connection.uri}")
    private String drillConnectionUri;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        /*
        Loading the JDBC driver
         */
        Class.forName("org.apache.drill.jdbc.Driver");

        /*
        We'll use the Drill JDBC connection string which connects to the local Apache Zookeeper instance.
         */
//        Connection connection = DriverManager.getConnection("jdbc:drill:zk=local");
        Connection connection = DriverManager.getConnection(drillConnectionUri);

        /*
        Creating the query object here.
         */
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from kafka.`drill1` limit 10");

        /*
        We'll fetch the records and print them one by one.
         */
        while(resultSet.next()){
            logger.info("ID: " + resultSet.getString(1));
            logger.info("First Name: " + resultSet.getString(2));
            logger.info("Last Name: " + resultSet.getString(3));
            logger.info("Email: " + resultSet.getString(4));
            logger.info("Gender: " + resultSet.getString(5));
            logger.info("IP Address: " + resultSet.getString(6));
            logger.info("----------------------------------------------");
        }

        /*
        Let's run another query to fetch data from a MongoDB storage plugin.
         */
        resultSet = statement.executeQuery("select * from mongo.drill.sampleData limit 10");

        logger.info("Querying MongoDB now...");

        /*
        We'll print the new data now.
         */
        while(resultSet.next()){
            logger.info("ID: " + resultSet.getString(1));
            logger.info("string1: " + resultSet.getString(2));
            logger.info("string2: " + resultSet.getString(3));
            logger.info("double1: " + resultSet.getString(4));
            logger.info("double2: " + resultSet.getString(5));
            logger.info("----------------------------------------------");
        }
    }
}
