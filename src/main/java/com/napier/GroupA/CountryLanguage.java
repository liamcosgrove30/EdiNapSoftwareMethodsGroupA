package com.napier.GroupA;

/**
 *Represents a Language in the db.
 */
public class CountryLanguage {

    private Country country;
    private String language;
    private String isOfficial;
    private double percentage;

    public CountryLanguage(){}

    public Country getCountry(){
        return  country;
    }

    public void setCountry(Country country){
        this.country=country;
    }

    public String getLanguage(){
        return this.language;
    }

    public void setLanguage(String language){
        this.language=language;
    }

    public String getIsOfficial(){
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial){
        this.isOfficial=isOfficial;
    }

    public double getPercentage(){
        return percentage;
    }

    public void setPercentage(double percentage){
        this.percentage=percentage;
    }

    @Override
    public String toString(){
        return "CountryLanguage{" + "country=" + country.getName() + ", language='"
                + language + '\'' + ", isOffical='" + isOfficial + '\'' +
                ", percentage=" + percentage + '}';
    }
}
