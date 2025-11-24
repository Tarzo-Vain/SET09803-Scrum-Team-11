package com.napier.devops.report;


import com.napier.devops.dao.CityDAO;
import com.napier.devops.dao.CountryDAO;
import com.napier.devops.model.City;
import com.napier.devops.model.Country;
import com.napier.devops.model.LanguageReport;
import com.napier.devops.model.PopulationReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReportService.
 * These are pure unit tests (no real DB) using mocked DAOs.
 */
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private CountryDAO countryDAO;

    @Mock
    private CityDAO cityDAO;

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        // ReportService is a record: new ReportService(countryDAO, cityDAO)
        reportService = new ReportService(countryDAO, cityDAO);
    }

    // 1) getAllCountries delegates to CountryDAO.getAllCountriesByPopulation
    @Test
    void getAllCountries_returnsListFromDao() throws SQLException {
        List<Country> expected = List.of(new Country(), new Country());

        when(countryDAO.getAllCountriesByPopulation()).thenReturn(expected);

        List<Country> result = reportService.getAllCountries();

        assertSame(expected, result, "Service should return the list from CountryDAO");
        verify(countryDAO).getAllCountriesByPopulation();
        verifyNoInteractions(cityDAO);
    }

    // 2) getCountriesByContinent passes continent to CountryDAO
    @Test
    void getCountriesByContinent_passesContinentToDao() throws SQLException {
        String continent = "Asia";
        List<Country> expected = List.of(new Country());

        when(countryDAO.getCountriesByContinent(continent)).thenReturn(expected);

        List<Country> result = reportService.getCountriesByContinent(continent);

        assertSame(expected, result);
        verify(countryDAO).getCountriesByContinent("Asia");
        verifyNoInteractions(cityDAO);
    }

    // 3) getAllCitiesByCountry delegates to CityDAO with correct country
    @Test
    void getAllCitiesByCountry_usesCityDao() throws SQLException {
        String countryName = "Belize";
        List<City> expected = List.of(new City(), new City(), new City());

        when(cityDAO.getAllCitiesByCountry(countryName)).thenReturn(expected);

        List<City> result = reportService.getAllCitiesByCountry(countryName);

        assertSame(expected, result);
        verify(cityDAO).getAllCitiesByCountry("Belize");
        verifyNoMoreInteractions(cityDAO);
        verifyNoInteractions(countryDAO);
    }

    // 4) getWorldPopulationReport returns PopulationReport from CountryDAO
    @Test
    void getWorldPopulationReport_returnsReportFromDao() throws SQLException {
        PopulationReport expected = new PopulationReport();
        expected.setName("World");
        expected.setTotalPopulation(7_800_000_000L);

        when(countryDAO.getWorldPopulationReport()).thenReturn(expected);

        PopulationReport result = reportService.getWorldPopulationReport();

        assertSame(expected, result);
        verify(countryDAO).getWorldPopulationReport();
        verifyNoInteractions(cityDAO);
    }

    // 5) getLanguageReport returns list from CountryDAO
    @Test
    void getLanguageReport_returnsListFromDao() throws SQLException {
        List<LanguageReport> expected = List.of(new LanguageReport(), new LanguageReport());

        when(countryDAO.getLanguageReport()).thenReturn(expected);

        List<LanguageReport> result = reportService.getLanguageReport();

        assertSame(expected, result);
        verify(countryDAO).getLanguageReport();
        verifyNoInteractions(cityDAO);
    }

    // OPTIONAL extra (good to have): exceptions are propagated
    @Test
    void getAllCountries_propagatesSQLException() throws SQLException {
        when(countryDAO.getAllCountriesByPopulation())
                .thenThrow(new SQLException("DB error"));

        assertThrows(SQLException.class, () -> reportService.getAllCountries());
        verify(countryDAO).getAllCountriesByPopulation();
    }
}