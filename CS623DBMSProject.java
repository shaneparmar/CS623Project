/*
    Authors : Ayush Sharma
              Shane Parmar
              Rushabh Singhala
 */

package com.CS623DBMSProject; // import project package

// Import important libraries.

import java.sql.*;
import java.util.*;


// CS623DBMSProject.java class
public class CS623DBMSProject {

    // Main method
    public static void main(String[] args) throws SQLException {


        // creating a c variable and initializing it with null value
        Connection c = null;

        // creating a rs variable and initializing it with null value
        ResultSet rs= null;


        // try/catch block for catching exceptions
        try
        {
            // Loading the driver
            Class.forName("org.postgresql.Driver");

            // Connecting to the postgreSQL database
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                                            "postgres",
                                        "password"
                                            );

            // Enabling statement to write SQL queries
            Statement stmt = c.createStatement();


            /*
                Create tables
             */
            // Creating table "Product" with prodid, pname and price
            stmt.executeUpdate("CREATE TABLE Product(" + "prodid char(5), pname char(20), price double precision)");

            // Altering table Product by adding prodid as primary key
            stmt.executeUpdate("ALTER TABLE Product ADD CONSTRAINT pk_product PRIMARY KEY(prodid)");

            // Creating table "Depot" with 'dep', 'addr' and volume
            stmt.executeUpdate("CREATE TABLE Depot(" + "depid char(5), addr char(20), volume double precision)");

            // Altering table "Depot" by adding dep as the primary key
            stmt.executeUpdate("ALTER TABLE Depot ADD CONSTRAINT pk_depot PRIMARY KEY(depid)");

            // Creating table "Stock" with 'prod', 'dep' and 'quantity'
            stmt.executeUpdate("CREATE TABLE Stock(" + "prod char(5), dep char(5), quantity double precision)");

            // Altering table "Stock" by adding 'dep' as a foreign key using On Delete Cascade
            stmt.executeUpdate("ALTER TABLE Stock ADD CONSTRAINT pk_stock FOREIGN KEY(dep) REFERENCES Depot (depid) ON DELETE CASCADE");

            // Altering table "Stock" by adding 'prod' as a foreign key using On Delete Cascade
            stmt.executeUpdate("ALTER TABLE Stock ADD CONSTRAINT pk_stockprod FOREIGN KEY(prod) REFERENCES Product (prodid) ON DELETE CASCADE");


            /*
                Insertion of the data into the tables
             */

            // inserting values into "Product" prodid, pname and price
            stmt.executeUpdate(
                            "insert into Product (prodid, pname,price) " +
                                    "values      ('p1','tape',2.5), " +
                                                "('p2','tv',250), " +
                                                "('p3','vcr',80)"
                             ) ;


            // inserting values into "Depot" dep, addr and volume
            stmt.executeUpdate(
                            "insert into Depot (depid, addr, volume) " +
                                    "values     ('d1','New York',9000), " +
                                               "('d2','Syracuse',6000), " +
                                               "('d4','New York',2000)  "
                                )   ;


            // inserting values into "Stock" prod, dep and quantity
            stmt.executeUpdate(
                            "insert into Stock (prod, dep, quantity) " +
                                    "values  ('p1','d1',1000), " +
                                            "('p1','d2',-100), " +
                                            "('p1','d4',1200), " +
                                            "('p3','d1',3000), " +
                                            "('p3','d4',2000), " +
                                            "('p2','d4',1500)," +
                                            "('p2','d1',-400)," +
                                            "('p2','d2',2000)"
                                )   ;


            /*
                Task for the project
             */
            stmt.executeUpdate("DELETE FROM Depot WHERE depid = 'd1' ");

            // moving to the next row
            //rsw.next();


            /*
                Checking the data from all the tables
             */
            rs = stmt.executeQuery("SELECT * FROM Stock");
            System.out.println("Data in \'Stock\' table -----");
            while(rs.next())
            {
                String rsprod = rs.getString("prod");
                String rsdep  = rs.getString("dep");
                String rsquan  = rs.getString("quantity");
                System.out.println(rsprod + " | " + rsdep + " | " +rsquan);
            }

            // setAutoCommit set to false - automicity
            c.setAutoCommit(false);
        }
        catch (Exception e)
        {
            // printing the stack trace of the error
            e.printStackTrace();

            // specific error
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            // exiting the program
            System.exit(0);

            // rollbacking the connection
            c.rollback();

            // closing the connection
            c.close();
        }
    }
}
