package com.napier.GroupA;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Class for Integration Testing
 */
public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060");
    }


    //Integration Test to test normal conditions in the printPopReport
    @Test
    void testGetCountry()
    {

    }
}
