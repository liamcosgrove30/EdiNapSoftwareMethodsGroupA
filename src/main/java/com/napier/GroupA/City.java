package com.napier.GroupA;

/**
 * Represents a City in the db.
 */
public class City {

    private int id;
    private String name;
    private Country country;
    private String district;
    private int population;

    public City() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "City{" + ", name='" + name + '\'' +
                "country=" + country.getCode() + '\'' + ", district='" + district + '\'' +
                ", population=" + population + '}';
    }

    public String toMarkdown() {
        String str = "";
        str += "|" + name + "|" + country.getCode() + "|" + district + "|" + population + "|";
        return str;
    }

}