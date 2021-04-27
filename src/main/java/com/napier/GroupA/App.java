package com.napier.GroupA;

import java.io.*;
import java.sql.*;
import java.util.*;

import static java.util.Collections.*;

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
        sort(countries, new Comparator<Country>() {
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

        printCountryReport(countries, "All the countries in the world organised by " +
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
        sort(countries1, new Comparator<Country>() {
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
        sort(countries1, new Comparator<Country>() {
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

    /**
     * Generate report 7, cities in the world from largest to smallest population
     * @param cities
     */
    private static void report7(HashMap< Integer, City> cities){
        // Finding the values
        Collection<City> valueSet = cities.values();

        // Creating an ArrayList of values
        ArrayList<City> listOfValues
                = new ArrayList<City>(valueSet);

        //sort list of cities by population
        sort(listOfValues, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if(o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }
                else{
                    return 1;
                }
            }
        });

        printCityReport(listOfValues, "All the cities in the world organised by " +
                                      "largest population to smallest", "./reports/report7.md");
    }

    /**
     * Generate report 8, cities in a continent organised from largest to smallest population
     * @param cities
     * @param continent
     */
    private static void report8(HashMap< Integer, City> cities, ArrayList<Country> countries, String continent){
        // Finding the values for cities
        Collection<City> valueSet = cities.values();

        // Creating an ArrayList of values of cities
        ArrayList<City> listOfValues1
                = new ArrayList<City>(valueSet);

        // Creating an ArrayList for the resulting values
        ArrayList<City> result = new ArrayList<City>();

        for (Country c1: countries)
        {
            if (c1.getContinent().equals(continent))
            {
                for (City c: valueSet)
                {
                    if (c.getCountry().getCode().equals(c1.getCode()))
                    {
                        result.add(c);
                    }
                }
            }
        }

        //sort list of cities by population
        sort(result, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if (o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }else{
                    return 1;
                }
            }
        });
        printCityReport(result, "All cities in a continent (" + continent +
                                       ") organised by largest population to the smallest", "./reports/report8_" + continent + ".md");
    }


    /**
     * Generate report 9, cities in a region organised from largest to smallest population
     * @param cities
     * @param region
     */
    private static void report9(HashMap< Integer, City> cities, ArrayList<Country> countries, String region){
        // Finding the values for cities
        Collection<City> valueSet = cities.values();

        // Creating an ArrayList of values of cities
        ArrayList<City> listOfValues1
                = new ArrayList<City>(valueSet);

        // Creating an ArrayList for the resulting values
        ArrayList<City> result = new ArrayList<City>();

        for (Country c1: countries)
        {
            if (c1.getRegion().equals(region))
            {
                for (City c: valueSet)
                {
                    if (c.getCountry().getCode().equals(c1.getCode()))
                    {
                        result.add(c);
                    }
                }
            }
        }

        //sort list of cities by population
        sort(result, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if (o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }else{
                    return 1;
                }
            }
        });
        printCityReport(result, "All cities in a region (" + region +
                                ") organised by largest population to the smallest", "./reports/report9_" + region + ".md");
    }

    /**
     * Generate report 10, cities in a country organised from largest to smallest population
     * @param cities
     * @param countries
     * @param country
     */
    private static void report10(HashMap< Integer, City> cities, ArrayList<Country> countries, Country country){
        // Finding the values for cities
        Collection<City> valueSet = cities.values();

        // Creating an ArrayList of values of cities
        ArrayList<City> listOfValues1
                = new ArrayList<City>(valueSet);

        // Creating an ArrayList for the resulting values
        ArrayList<City> result = new ArrayList<City>();

        for (Country c1: countries)
        {
            if (c1.getName().equals(country))
            {
                for (City c: valueSet)
                {
                    if (c.getCountry().getCode().equals(c1.getCode()))
                    {
                        result.add(c);
                    }
                }
            }
        }

        //sort list of cities by population
        sort(result, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if (o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }else{
                    return 1;
                }
            }
        });
        printCityReport(result, "All cities in a country (" + country +
                                ") organised by largest population to the smallest", "./reports/report10_" + country + ".md");
    }

    /**
     * Generate report 12, top N populated cities in the world
     * @param cities
     */
    private static void report12(HashMap< Integer, City> cities){
        // Finding the values
        Collection<City> valueSet = cities.values();

        // Creating an ArrayList of values
        ArrayList<City> listOfValues
                = new ArrayList<City>(valueSet);

        // Number of cities to print, placeholder fixed value for testing
        int N = 5;

        // Sort list of cities by population
        sort(listOfValues, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if(o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }
                else{
                    return 1;
                }
            }
        });

        // Get top N cities
        ArrayList<City> nValues = (ArrayList<City>) listOfValues.subList(0, N);

        printCityReport(nValues, "All the cities in the world organised by " +
                "largest population to smallest", "./reports/report12.md");
    }

    /**
     * Generate report 13, top N populated cities in a continent
     * @param cities
     * @param continent
     */
    private static void report13(HashMap< Integer, City> cities, ArrayList<Country> countries, String continent){
        // Finding the values for cities
        Collection<City> valueSet = cities.values();

        // Creating an ArrayList of values of cities
        ArrayList<City> listOfValues1
                = new ArrayList<City>(valueSet);

        // Creating an ArrayList for the resulting values
        ArrayList<City> result = new ArrayList<City>();

        // Number of cities to print, placeholder fixed value for testing
        int N = 5;

        for (Country c1: countries)
        {
            if (c1.getContinent().equals(continent))
            {
                for (City c: valueSet)
                {
                    if (c.getCountry().getCode().equals(c1.getCode()))
                    {
                        result.add(c);
                    }
                }
            }
        }

        //sort list of cities by population
        sort(result, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if (o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }else{
                    return 1;
                }
            }
        });

        // Get top N cities
        ArrayList<City> nValues = (ArrayList<City>) result.subList(0, N);

        printCityReport(nValues, "All cities in a continent (" + continent +
                ") organised by largest population to the smallest", "./reports/report13_" + continent + ".md");
    }

    /**
     * Generate report 14, top N populated cities in a region
     * @param cities
     * @param region
     */
    private static void report14(HashMap< Integer, City> cities, ArrayList<Country> countries, String region){
        // Finding the values for cities
        Collection<City> valueSet = cities.values();

        // Creating an ArrayList of values of cities
        ArrayList<City> listOfValues1
                = new ArrayList<City>(valueSet);

        // Creating an ArrayList for the resulting values
        ArrayList<City> result = new ArrayList<City>();

        // Number of cities to print, placeholder fixed value for testing
        int N = 5;

        for (Country c1: countries)
        {
            if (c1.getRegion().equals(region))
            {
                for (City c: valueSet)
                {
                    if (c.getCountry().getCode().equals(c1.getCode()))
                    {
                        result.add(c);
                    }
                }
            }
        }

        //sort list of cities by population
        sort(result, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if (o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }else{
                    return 1;
                }
            }
        });

        // Get top N cities
        ArrayList<City> nValues = (ArrayList<City>) result.subList(0, N);

        printCityReport(nValues, "All cities in a region (" + region +
                ") organised by largest population to the smallest", "./reports/report14_" + region + ".md");
    }

    /**
     * Generate report 15, top N populated cities in a country
     * @param cities
     * @param countries
     * @param country
     */
    private static void report15(HashMap< Integer, City> cities, ArrayList<Country> countries, Country country){
        // Finding the values for cities
        Collection<City> valueSet = cities.values();

        // Creating an ArrayList of values of cities
        ArrayList<City> listOfValues1
                = new ArrayList<City>(valueSet);

        // Creating an ArrayList for the resulting values
        ArrayList<City> result = new ArrayList<City>();

        // Number of cities to print, placeholder fixed value for testing
        int N = 5;

        for (Country c1: countries)
        {
            if (c1.getName().equals(country))
            {
                for (City c: valueSet)
                {
                    if (c.getCountry().getCode().equals(c1.getCode()))
                    {
                        result.add(c);
                    }
                }
            }
        }

        //sort list of cities by population
        sort(result, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if (o1.getPopulation() > o2.getPopulation()){
                    return  -1;
                }else{
                    return 1;
                }
            }
        });

        // Get top N cities
        ArrayList<City> nValues = (ArrayList<City>) result.subList(0, N);

        printCityReport(nValues, "All cities in a country (" + country +
                ") organised by largest population to the smallest", "./reports/report15_" + country + ".md");
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
     * Method to print out every city
     * @param cities
     * @param heading
     * @param filename
     */
    public static void printCityReport(ArrayList<City> cities, String heading, String filename) {
        StringBuilder sb = new StringBuilder();
        sb.append("# " + heading + "\r\n\r\n");
        sb.append("| Name | CountryCode | District | Population |\r\n");
        sb.append("| :--- | :--- | :--- | :---: |\r\n");
        for (City city : cities) {
            sb.append(city.toMarkdown() + "\r\n");
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(filename)));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Successfully output " + cities.size() + " results to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * Method to print out population reports
//     * @param populations
//     */
//    public static void printPopulationReport(ArrayList<Population> populations) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("| Area Name | Total Population | Population In Cities | Population Not In Cities |\r\n");
//        sb.append("| :--- | :--- | :--- | :--- | \r\n");
//        for (Population population : populations) {
//            sb.append(population.toMarkdown() + "\r\n");
//        }
//        BufferedWriter writer = null;
//        try {
//            writer = new BufferedWriter(new FileWriter(new File("population.md")));
//            writer.write(sb.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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

            String strSelect = "SELECT * from country";

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

        for(String region :a.regions){
            report3(countries, region);
        }

        //report7
        report7(a.getCities());

        //report8
        for(String continent:a.continents) {
            report8(a.getCities(), a.getCountries(), continent);
        }

        //report9
        for(String region :a.regions){
            report9(a.getCities(), a.getCountries(), region);
        }

        //report10
        for(Country country : a.getCountries())
        report10(a.getCities(), a.getCountries(), country);

        outputReadme();

        // Disconnect from database
        a.disconnect();
    }
}