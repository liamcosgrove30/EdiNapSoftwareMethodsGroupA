package com.napier.GroupA;

import java.sql.*;

public class App
{

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public Country getCountry(String code){
        try {
          //create an SQL Statement
            Statement stmt = con.createStatement();
          //create string for SQL statement
            String strSelect =
                    "SELECT Code, Name, Region"
                    +"FROM country"
                    +"WHERE Code =" + code;
            //Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            //return new country if valid
            //check one is returned
            if(rset.next())
            {
                Country cntry = new Country();
                cntry.country_code=rset.getString("Code");
                cntry.country_name=rset.getString("Name");
                cntry.country_region= rset.getString("Region");
                return cntry;
            }else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public void displayCountry(Country cntry){
        if(cntry != null){
            System.out.println(cntry.country_code + " " + cntry.country_name);
        }
    }

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        Country cntry = a.getCountry("AFG");
        a.displayCountry(cntry);

        // Disconnect from database
        a.disconnect();
    }
}