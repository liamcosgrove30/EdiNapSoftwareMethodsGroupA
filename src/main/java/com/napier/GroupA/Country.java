package com.napier.GroupA;

import java.util.ArrayList;

/**
 *Represents a Country in the db.
 */
public class Country {

    //Declare variables for the Country Class
    private String code;
    private  String name;
    private String continent;
    private String region;
    private int population;
    private City capital;
    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<CountryLanguage> countryLanguages = new ArrayList<>();

    public Country(){}

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code=code;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getContinent(){
        return continent;
    }

    public void setContinent(String continents){
        this.continent=continent;
    }

    public String getRegion(){
        return region;
    }

    public void setRegion(String region){
        this.region=region;
    }

    public int getPopulation(){
        return population;
    }

    public void setPopulation(int population){
        this.population=population;
    }

    public City getCapital(){
        return capital;
    }

    public void setCapital(City capital){
        this.capital=capital;
    }

    public ArrayList<City>getCities(){
        return cities;
    }

    public void setCities(ArrayList<City> cities){
        this.cities=cities;
    }

    public ArrayList<CountryLanguage> getCountryLanguages(){
        return countryLanguages;
    }

    public void setCountryLanguages(ArrayList<CountryLanguage> countryLanguages){
        this.countryLanguages=countryLanguages;
    }

    @Override
    public String toString(){
        String str = "Country{" + ", code='" + code + '\'' + ", name='" + name + '\'' +
                ", continent='" + continent + '\'' + ", region='" + region + '\'' +
                ", population=" + population;
        if(capital != null){
            str += ", capital=" + capital.getName();
        }else{str += ", captial = null";}

        str += ", cities=" + cities.size() + ", languages=" + countryLanguages.size()
                + '}';

        return str;
    }

    public String toMarkdown(){
        String str = "";
        str += "|" + code + "|" + name + "|" + continent + "|" + region + "|" + population + "|";
        if(capital !=null){
            str += capital.getName();
        }else{
            str += " ";
        }
        str += "|";

        return str;
    }
}
