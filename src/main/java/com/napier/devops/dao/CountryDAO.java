package com.napier.devops.dao;

import com.napier.devops.model.Country;
import com.napier.devops.model.PopulationReport; // *** NEW ***

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public record CountryDAO(Connection con) {

    private Country mapCountry(ResultSet rs) throws SQLException {
        Country c = new Country();
        c.setCode(rs.getString("Code"));
        c.setName(rs.getString("Name"));
        c.setContinent(rs.getString("Continent"));
        c.setRegion(rs.getString("Region"));
        c.setPopulation(rs.getLong("Population"));
        c.setCapital(rs.getString("Capital"));
        return c;
    }

    // Helper: map a row to PopulationReport and compute percentages
    private PopulationReport mapPopulationReport(ResultSet rs) throws SQLException {
        PopulationReport report = new PopulationReport();

        String name = rs.getString("Name");
        long totalPop = rs.getLong("TotalPop");
        long cityPop = rs.getLong("CityPop");

        // If SUM(ci.Population) is NULL (no cities), treat as 0
        if (rs.wasNull()) {
            cityPop = 0;
        }

        long nonCityPop = totalPop - cityPop;
        if (nonCityPop < 0) {
            nonCityPop = 0;   // safety
        }

        double cityPercent = 0.0;
        double nonCityPercent = 0.0;

        if (totalPop > 0) {
            cityPercent = (cityPop * 100.0) / totalPop;
            nonCityPercent = (nonCityPop * 100.0) / totalPop;
        }

        report.setName(name);
        report.setTotalPopulation(totalPop);
        report.setCityPopulation(cityPop);
        report.setNonCityPopulation(nonCityPop);
        report.setCityPopulationPercent(cityPercent);
        report.setNonCityPopulationPercent(nonCityPercent);

        return report;
    }


    // 1. All countries in the world and #17
    public List<Country> getAllCountriesByPopulation() throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                ORDER BY co.Population DESC limit 10;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapCountry(rs));
        }
        return list;
    }

    // 2. Countries in a continent and #18
    public List<Country> getCountriesByContinent(String continent) throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                WHERE co.Continent = ?
                ORDER BY co.Population DESC limit 10;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCountry(rs));
            }
        }
        return list;
    }

    // 3. Countries in a region
    public List<Country> getCountriesByRegion(String region) throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                WHERE co.Region = ?
                ORDER BY co.Population DESC;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCountry(rs));
            }
        }
        return list;
    }

/* Old Code
    // 4. All Countries by Population--menu 20
    public List<Country> getAllTopNCountriesByPopulation(int n) throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                ORDER BY co.Population DESC 
                LIMIT ?;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, n);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCountry(rs));
            }
        }
        return list;
    }

    // 5. Countries in a continent
    public List<Country> getAllTopNCountriesByContinent(String continent) throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                WHERE co.Continent = ?
                ORDER BY co.Population DESC;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCountry(rs));
            }
        }
        return list;
    }

    // 6. Countries in a region
    public List<Country> getAllTopNCountriesByRegion(String region) throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                WHERE co.Region = ?
                ORDER BY co.Population DESC;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCountry(rs));
            }
        }
        return list;
    }

*/
    // 4. Top N countries in the world by population
    public List<Country> getTopCountriesInWorld(int n) throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                ORDER BY co.Population DESC
                LIMIT ?;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, n);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCountry(rs));
            }
        }
        return list;
    }

    // 5. Top N countries in a continent by population
    public List<Country> getTopCountriesInContinent(String continent, int n) throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                WHERE co.Continent = ?
                ORDER BY co.Population DESC
                LIMIT ?;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            stmt.setInt(2, n);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCountry(rs));
            }
        }
        return list;
    }

    // 6. Top N countries in a region by population
    public List<Country> getTopCountriesInRegion(String region, int n) throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                WHERE co.Region = ?
                ORDER BY co.Population DESC
                LIMIT ?;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            stmt.setInt(2, n);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCountry(rs));
            }
        }
        return list;
    }

    // === NEW: POPULATION REPORT METHODS ===

    // Population report for a continent
    public PopulationReport getContinentPopulationReport(String continent) throws SQLException {
        String sql = """
                SELECT co.Continent AS Name,
                       SUM(co.Population) AS TotalPop,
                       SUM(ci.Population) AS CityPop
                FROM country co
                LEFT JOIN city ci ON co.Code = ci.CountryCode
                WHERE co.Continent = ?
                GROUP BY co.Continent;
                """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPopulationReport(rs);
                }
            }
        }
        return null;
    }

    // Population report for a region
    public PopulationReport getRegionPopulationReport(String region) throws SQLException {
        String sql = """
                SELECT co.Region AS Name,
                       SUM(co.Population) AS TotalPop,
                       SUM(ci.Population) AS CityPop
                FROM country co
                LEFT JOIN city ci ON co.Code = ci.CountryCode
                WHERE co.Region = ?
                GROUP BY co.Region;
                """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPopulationReport(rs);
                }
            }
        }
        return null;
    }

    // Population report for a single country (by country name, e.g. 'Belize')
    public PopulationReport getCountryPopulationReport(String countryName) throws SQLException {
        String sql = """
                SELECT co.Name AS Name,
                       co.Population AS TotalPop,
                       SUM(ci.Population) AS CityPop
                FROM country co
                LEFT JOIN city ci ON co.Code = ci.CountryCode
                WHERE co.Name = ?
                GROUP BY co.Name, co.Population;
                """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, countryName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPopulationReport(rs);
                }
            }
        }
        return null;
    }

}