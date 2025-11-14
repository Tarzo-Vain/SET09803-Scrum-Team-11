package com.napier.devops.model;
public class City {

    private int id;                // city.ID (optional but useful)
    private String name;           // city name
    private String country;        // country name
    private String countryCode;    // internal code if needed
    private String district;
    private long population;

    public City() {}

    public City(int id, String name, String country,
                String countryCode, String district, long population) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public long getPopulation() { return population; }
    public void setPopulation(long population) { this.population = population; }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                '}';
    }
}
