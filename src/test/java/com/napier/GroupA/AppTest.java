package com.napier.GroupA;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void printPopulationTestNull()
    {
        app.printPopReport(null);
    }

    @Test
    void printPopulationTestEmpty(){
        ArrayList<Country> countries = new ArrayList<Country>();
        app.printPopReport(countries);
    }

    @Test
    void printPopultaionTestContainsNull(){
        ArrayList<Country> countries = new ArrayList<Country>();
        countries.add(null);
        app.printPopReport(countries);
    }

    @Test
    void printPopultaion(){
        ArrayList<Country> countries = new ArrayList<Country>();
        Country cntry = new Country();
        cntry.country_name = "Scotland";
        cntry.country_population = 5454000;
        app.printPopReport(countries);
    }
}
