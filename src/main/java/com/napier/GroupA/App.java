package com.napier.GroupA;

import java.io.*;
import java.sql.*;
import java.util.*;

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
     * Regions in the world
     */
    String[] regions = {"Caribbean", "Southern and Central Asia", "Central Africa", "Southern Europe", "Middle East",
            "South America", "Polynesia", "Antarctica", "Australia and New Zealand", "Western Europe", "Eastern Africa",
            "Western Africa", "Eastern Europe", "Central America", "North America", "Southeast Asia", "Southern Africa",
            "Eastern Asia", "Nordic Countries", "Northern Africa", "Baltic Countries", "Melanesia", "Micronesia",
            "British Islands", "Micronesia/Caribbean"};

    /**
     * Continents of the world
     */
    String[] continents = {"North America", "South America", "Asia", "Africa", "Europe", "Oceania", "Antarctica"};

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

    /**
     * Generate report 1, countries in the world from largest to smallest
     * @param countries
     */
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

    /**
     * Generate report 2, countries in a continent organised by largest population to smallest
     * @param countries
     * @param continent
     */
    private static void report2(ArrayList<Country> countries, String continent){
        ArrayList<Country> countries1 = new ArrayList<>();

        for (Country c: countries){
            if(c.getContinent().equals(continent)){
                countries1.add(c);
            }
        }
        Collections.sort(countries1, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                if (o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }else{
                    return 1;
                }
            }
        });
        printCountryReport(countries1, "All countries in a continent (" + continent +
                ") organised by largest population to the smallest", "./reports/report2_" + continent + ".md");
    }

    private static void report3(ArrayList<Country> countries, String region){
        ArrayList<Country> countries1 = new ArrayList<>();
        for (Country c : countries){
            if(c.getRegion().equals(region)){
                countries1.add(c);
            }
        }
        Collections.sort(countries1, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                if(o1.getPopulation() > o2.getPopulation()){
                    return -1;
                }
                else{return  1;}
            }
        });
        printCountryReport(countries1, "All the countries in a region (" + region +
                ") organised from largest population to smallest", "./reports/report3_" + region + ".md");
    }

    private static void report4(ArrayList<Country> countries, int input){
        ArrayList<Country> countries1 = new ArrayList<>();

        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                if(o1.getPopulation() > o2.getPopulation()){
                    return -1;
                }else{return 1;}
            }
        });

        // Number of countries to print, placeholder fixed value for testing
        int N = 5;

        // Get top N countries
        List<Country> nValues = countries.subList(0, N);

        printCountryNReport(nValues, "The top " + input+ " countries in the world organised by" +
                "largest population to smallest", "./reports/report4.md");
    }

    /**
     * Method to print out every country
     * @param countries
     * @param heading
     * @param filename
     */
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

    /**
     * Method to print out N countries
     * @param countries
     * @param heading
     * @param filename
     */
    public static void printCountryNReport(List<Country> countries, String heading, String filename) {
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

    /**
     * Method to print outputs
     */
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

    /**
     * Create a HashMap for every City
     * @return
     */
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

    /**
     * Create a HashMap for every Language
     * @return
     */
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

        for(String continent:a.continents){
            report2(countries, continent);
        }

     //   for(String region :a.regions){
       //     report3(countries, region);
       // }

        report4(countries,7);

        outputReadme();

        // Disconnect from database
        a.disconnect();
    }
}