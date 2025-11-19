package com.napier.devops.report;

import com.napier.devops.dao.CountryDAO;
import com.napier.devops.dao.CityDAO;
import com.napier.devops.model.City;
import com.napier.devops.model.Country;
import com.napier.devops.model.PopulationReport; //**New***

import java.sql.SQLException;
import java.util.List;

public class ReportService {

    private final CountryDAO countryDAO;
    private final CityDAO cityDAO;


    public ReportService(CountryDAO countryDAO, CityDAO cityDAO) {

        this.countryDAO = countryDAO;
        this.cityDAO = cityDAO;
    }

    //---------------COUNTRY REPORTS--------------------
    public List<Country> getAllCountries() throws SQLException {
        return countryDAO.getAllCountriesByPopulation();
    }

    public List<Country> getCountriesByContinent(String continent) throws SQLException {
        return countryDAO.getCountriesByContinent(continent);
    }

    public List<Country> getCountriesByRegion(String region) throws SQLException {
        return countryDAO.getCountriesByRegion(region);
    }

    // New: Top N in world
    public List<Country> getTopCountriesInWorld(int n) throws SQLException {
        return countryDAO.getTopCountriesInWorld(n);
    }

    // New: Top N in continent
    public List<Country> getTopCountriesInContinent(String continent, int n) throws SQLException {
        return countryDAO.getTopCountriesInContinent(continent, n);
    }

    // New: Top N in region
    public List<Country> getTopCountriesInRegion(String region, int n) throws SQLException {
        return countryDAO.getTopCountriesInRegion(region, n);
    }

/* old code
//used by menu 20
    public List<Country> getAllTopNCountriesByPopulation(int n) throws SQLException {
        return countryDAO.getAllTopNCountriesByPopulation(n);
    }

    public List<Country> getAllTopNCountriesByContinent(String continent) throws SQLException {
        return countryDAO.getAllTopNCountriesByContinent(continent);
    }

    public List<Country> getAllTopNCountriesByRegion(String region) throws SQLException {
        return countryDAO.getAllTopNCountriesByRegion(region);
    }
*/
    //--------------CITY REPORTS-------------
    public List<City> getAllCitiesByPopulation() throws SQLException {
        return cityDAO.getAllCitiesByPopulation();
    }


    public List<City> getAllCitiesByContinent(String continent) throws SQLException {
        return cityDAO.getAllCitiesByContinent(continent);
    }

    public List<City> getAllCitiesByRegion(String region) throws SQLException {
        return cityDAO.getAllCitiesByRegion(region);
    }

    public List<City> getAllCitiesByCountry(String country) throws SQLException {
        return cityDAO.getAllCitiesByCountry(country);
    }

    public List<City> getAllCitiesByDistrict(String district) throws SQLException {
        return cityDAO.getAllCitiesByDistrict(district);
    }

    public List<City> getAllTopNCitiesByPopulation(int n) throws SQLException {
        return cityDAO.getAllTopNCitiesByPopulation(n);
    }

    public List<City> getAllTopNCitiesByContinent(String continent, int n) throws SQLException {
        return cityDAO.getAllTopNCitiesByContinent(continent, n);
    }

    public List<City> getAllTopNCitiesByRegion(String region, int n) throws SQLException {
        return cityDAO.getAllTopNCitiesByRegion(region, n);
    }

    public List<City> getAllTopNCitiesByCountry(String country, int n) throws SQLException {
        return cityDAO.getAllTopNCitiesByCountry(country, n );
    }

    public List<City> getAllTopNCitiesByDistrict(String district, int n) throws SQLException {
        return cityDAO.getAllTopNCitiesByDistrict(district, n);
    }

    // === NEW: POPULATION REPORT ACCESSORS ===

    public PopulationReport getContinentPopulationReport(String continent) throws SQLException {
        return countryDAO.getContinentPopulationReport(continent);
    }

    public PopulationReport getRegionPopulationReport(String region) throws SQLException {
        return countryDAO.getRegionPopulationReport(region);
    }

    public PopulationReport getCountryPopulationReport(String countryName) throws SQLException {
        return countryDAO.getCountryPopulationReport(countryName);
    }
}