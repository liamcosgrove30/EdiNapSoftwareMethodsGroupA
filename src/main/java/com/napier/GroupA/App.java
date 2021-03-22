package com.napier.GroupA;

import java.sql.*;
import java.util.ArrayList;

public class App
{

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
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
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
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

    //Get a Country by its Country Code
    public Country getCountry(int code){
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

    //Display the Country
    public void displayCountry(Country cntry){
        if (cntry == null){
            System.out.println("No Country");
            return;
        }
        else if(cntry != null){
            System.out.println(cntry.country_code + " " + cntry.country_name);
        }
    }

    //Sort each country by population
    public ArrayList<Country> countriesInWorldByPop(){
        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT country.Name, country.Population"
                    + "FROM country"
                    + "ORDER BY country.Population";

            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()){
                Country cntry = new Country();
                cntry.country_name = rset.getString("country.Name");
                cntry.country_population = rset.getInt("country.Population");
                countries.add(cntry);
            }
            return countries;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries by Population");
            return null;
        }
    }

    //Print each country in order of population
    public void printPopReport(ArrayList<Country>country){
        if (country == null){
            System.out.println("No Countries");
            return;
        }
        System.out.println(String.format("%-10s, %-15s", "Country Name", "Population"));
        for(Country cntry:country){
            if(cntry == null)
                continue;;
            String cntry_string =
                    String.format("%-10s,%-15s", cntry.country_name, cntry.country_population);
            System.out.println(cntry_string);
        }
    }


    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect("localhost:33060");

        //Get Afghanistan
        //Country cntry = a.getCountry("AFG");
        //a.displayCountry(cntry);

        //Get Countries by Population
        ArrayList<Country> countries = a.countriesInWorldByPop();
        a.printPopReport(countries);

        // Disconnect from database
        a.disconnect();
    }
}