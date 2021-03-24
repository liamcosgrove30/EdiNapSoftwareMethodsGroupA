package com.napier.GroupA;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Main App where we connect to the database and extract report information.
 */
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
                Thread.sleep(3000);
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

    private static void report1(ArrayList<Country> countries){
        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                if(o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }
                else{
                    return 1;
                }
            }
        });

        printCountryReport(countries, "All the countries in the world organised by" +
                "largest population to smallest", "./reports/report1.md");
    }

    public static void printCountryReport(ArrayList<Country> countries, String heading, String filename) {
        StringBuilder sb = new StringBuilder();
        sb.append("# " + heading + "\r\n\r\n");
        sb.append("| Code | Name | Continent | Region | Population | Capital |\r\n");
        sb.append("| :--- | :--- | :--- | :--- | :--- | :--- |\r\n");
        for (Country country : countries) {
            sb.append(country.toMarkdown() + "\r\n");
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(filename)));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Successfully output " + countries.size() + " results to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void outputReadme() {
        File dir = new File("./reports/");
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".md");
            }
        });
        String str = "# Reports\r\n";
        for(File file : files){
            str += "[" + file.getName() + "](<./reports/" + file.getName() + ">)  " + "\r\n";
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File("./README.md")));
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<Integer, City> getCities() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement

            String strSelect = "SELECT * from city ";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            HashMap<Integer, City> cities = new HashMap<>();
            while (rset.next()) {
                City city = new City();
                city.setId(rset.getInt("ID"));
                city.setName(rset.getString("Name"));
                Country country = new Country();
                country.setCode(rset.getString("CountryCode"));
                city.setCountry(country);
                city.setDistrict(rset.getString("District"));
                city.setPopulation(rset.getInt("Population"));
                cities.put(city.getId(), city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    private HashMap<Country, CountryLanguage> getCountryLanguages() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement

            String strSelect = "SELECT * from countrylanguage ";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            HashMap<Country, CountryLanguage> countryLanguages = new HashMap<>();
            while (rset.next()) {
                CountryLanguage countryLanguage = new CountryLanguage();
                Country country = new Country();
                country.setCode(rset.getString("CountryCode"));
                countryLanguage.setCountry(country);
                countryLanguage.setLanguage(rset.getString("Language"));
                countryLanguage.setIsOfficial(rset.getString("IsOfficial"));
                countryLanguage.setPercentage(rset.getDouble("Percentage"));
                countryLanguages.put(countryLanguage.getCountry(), countryLanguage);
            }
            return countryLanguages;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<Country> getCountries() {
        //country set to null
        HashMap<Integer, City> cities = getCities();
        //country set to null
        HashMap<Country, CountryLanguage> countryLanguages = getCountryLanguages();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement

            String strSelect = "SELECT * from country ";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next()) {
                Country country = new Country();
                country.setCode(rset.getString("Code"));
                country.setName(rset.getString("Name"));
                country.setContinent(rset.getString("Continent"));
                country.setRegion(rset.getString("Region"));
                country.setPopulation(rset.getInt("Population"));
                country.setCapital(cities.get(rset.getInt("Capital")));
                for (City city : cities.values()) {
                    if (city.getCountry().getCode().equals(country.getCode())) {
                        country.getCities().add(city);
                        city.setCountry(country);
                    }
                }
                countries.add(country);

                for (CountryLanguage cl : countryLanguages.values()) {
                    if (cl.getCountry().getCode().equals(country.getCode())) {
                        country.getCountryLanguages().add(cl);
                        cl.setCountry(country);
                    }
                }
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect("localhost:33060");

        ArrayList<Country> countries = a.getCountries();

        report1(countries);

        outputReadme();

        // Disconnect from database
        a.disconnect();
    }
}