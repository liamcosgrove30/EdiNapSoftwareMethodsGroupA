package com.napier.GroupA;

/**
 *Enum of continents
 */
enum ContinentEnum{Asia,Europe,North_America,Africa,Oceania,,South_America}

/**
 *Represents a Country in the db.
 */
public class Country {

    /**
     *Country Code
     */
    public String country_code;

    /**
     *Country Name
     */
    public String country_name;

    /**
     *Continent of Country
     */
    public ContinentEnum country_continent;

    /**
     *Region of Country
     */
    public String country_region;

    /**
     *Country Surface Area
     */
    public float country_sa;

    /**
     *Country Independence Year
     */
    public int country_ind_year;

    /**
     *Country Population
     */
    public int country_population;

    /**
     *Country Life Expectancy
     */
    public float country_life_exp;

    /**
     *Country GNP
     */
    public float country_gnp;

    /**
     *COuntry Old GNP
     */
    public float country_old_gnp;

    /**
     *Local Name of Country
     */
    public String country_local_name;

    /**
     *Country Government Form
     */
    public String country_gov_form;

    /**
     *Country Head of State
     */
    public String country_head_of_state;

    /**
     *Capital of Country
     */
    public int country_capital;

    /**
     *Secondary Country Code
     */
    public String country_sec_code;

}
