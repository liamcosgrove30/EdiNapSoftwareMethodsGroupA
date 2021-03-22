package com.napier.GroupA;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for Unit Testing
 */
public class AppTest {
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    //UnitTest to check if the printPopReport is null
    @Test
    void printPopulationTestNull()
    {
        app.printPopReport(null);
    }

    //UnitTest to test when the countries array is empty in rhw printPopReport function
    @Test
    void printPopulationTestEmpty(){
        ArrayList<Country> countries = new ArrayList<Country>();
        app.printPopReport(countries);
    }

    //UnitTest to test when the countries array contains a null value in the printPopReport function
    @Test
    void printPopultaionTestContainsNull(){
        ArrayList<Country> countries = new ArrayList<Country>();
        countries.add(null);
        app.printPopReport(countries);
    }

    //UnitTest to test normal conditions in the printPopReport
    @Test
    void printPopultaion(){
        ArrayList<Country> countries = new ArrayList<Country>();
        Country cntry = new Country();
        cntry.country_name = "United Kingdom";
        cntry.country_population = 55000000;
        app.printPopReport(countries);
    }

    //UnitTest to test when null is passed to displayCountryy
    @Test
    void displayCountryTestNull(){
        app.displayCountry(null);
    }

    //UnitTest to test when country is empty in displayCountry
    @Test
    void displayCountryTestEmpty(){
        Country cntry = new Country();
        app.displayCountry(cntry);
    }

    //UnitTest to test normal conditions in the displayCountry function
    @Test
    void displayCountry(){
        Country cntry = new Country();
        cntry.country_code = "GBR";
        cntry.country_name = "United Kingdom";
    }
}
