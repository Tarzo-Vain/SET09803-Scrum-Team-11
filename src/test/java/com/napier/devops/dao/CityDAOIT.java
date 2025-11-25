package com.napier.devops.dao;

import com.napier.devops.model.City;
import com.napier.devops.model.PopulationReport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for CityDAO using the real MySQL world database.
 * Uses the same env-var pattern as CountryDAOIT:
 *   DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASS
 */
class CityDAOIT {

    private static Connection connection;
    private static CityDAO cityDAO;

    @BeforeAll
    static void setUp() throws Exception {
        String host = System.getenv().getOrDefault("DB_HOST", "localhost");
        String port = System.getenv().getOrDefault("DB_PORT", "33060"); // local default
        String db   = System.getenv().getOrDefault("DB_NAME", "world");
        String user = System.getenv().getOrDefault("DB_USER", "root");
        String pass = System.getenv().getOrDefault("DB_PASS", "example");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + db
                + "?useSSL=false&allowPublicKeyRetrieval=true";

        System.out.println("Connecting to database at " + url + " ...");
        connection = DriverManager.getConnection(url, user, pass);
        assertNotNull(connection, "Connection should not be null");
        assertFalse(connection.isClosed(), "Connection should be open");

        cityDAO = new CityDAO(connection);
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * 1) Basic sanity: getAllCitiesByPopulation should return a non-empty list.
     */
    @Test
    void getAllCitiesByPopulation_returnsNonEmptyList() throws Exception {
        List<City> cities = cityDAO.getAllCitiesByPopulation();

        assertNotNull(cities, "Cities list should not be null");
        assertFalse(cities.isEmpty(), "Cities list should not be empty");
    }

    /**
     * 2) Check that cities are ordered by population descending.
     */
    @Test
    void getAllCitiesByPopulation_isOrderedDescending() throws Exception {
        List<City> cities = cityDAO.getAllCitiesByPopulation();

        assertNotNull(cities);
        assertTrue(cities.size() > 1, "Need at least 2 cities to check ordering");

        long previous = Long.MAX_VALUE;
        for (City city : cities) {
            long current = city.getPopulation();
            assertTrue(previous >= current,
                    "Cities should be in descending order of population");
            previous = current;
        }
    }

    /**
     * 3) getAllCitiesByCountry should return some cities for a known country.
     *    Adjust the country name if your data set is different.
     */
    @Test
    void getAllCitiesByCountry_returnsCitiesForKnownCountry() throws Exception {
        String countryName = "China";   // or "United States", "India", etc. depending on your DB

        List<City> cities = cityDAO.getAllCitiesByCountry(countryName);

        assertNotNull(cities);
        assertFalse(cities.isEmpty(), "Expected some cities for country: " + countryName);
    }

    /**
     * 4) Top N cities by population: returns at most N cities and they are ordered.
     */
    @Test
    void getAllTopNCitiesByPopulation_respectsLimitAndOrdering() throws Exception {
        int n = 10;

        List<City> cities = cityDAO.getAllTopNCitiesByPopulation(n);

        assertNotNull(cities);
        assertFalse(cities.isEmpty(), "Top N cities list should not be empty");
        assertTrue(cities.size() <= n, "List size should be <= N");

        long previous = Long.MAX_VALUE;
        for (City city : cities) {
            long current = city.getPopulation();
            assertTrue(previous >= current,
                    "Top N cities should be in descending order of population");
            previous = current;
        }
    }

    /**
     * 5) Population report for a known city should be returned.
     *    Update "Kabul" to a city that you know exists in your data.
     */
    @Test
    void getCityPopulationReport_returnsReportForExistingCity() throws Exception {
        String cityName = "Kabul"; // adjust if needed for your DB

        PopulationReport report = cityDAO.getCityPopulationReport(cityName);

        assertNotNull(report, "Population report should not be null");
        assertEquals(cityName, report.getName(), "Report name should match the city name");
        assertTrue(report.getTotalPopulation() > 0, "Population should be > 0");
    }
}