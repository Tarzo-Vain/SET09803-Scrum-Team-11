package com.napier.devops.report;

import com.napier.devops.dao.CountryDAO;
import com.napier.devops.model.Country;

import java.sql.SQLException;
import java.util.List;

public class ReportService {

    private final CountryDAO countryDAO;

    public ReportService(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    public List<Country> getAllCountries() throws SQLException {
        return countryDAO.getAllCountriesByPopulation();
    }

    public List<Country> getCountriesByContinent(String continent) throws SQLException {
        return countryDAO.getCountriesByContinent(continent);
    }

    public List<Country> getCountriesByRegion(String region) throws SQLException {
        return countryDAO.getCountriesByRegion(region);
    }
}
