package com.napier.devops.dao;

import com.napier.devops.model.Country;

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

    // 1. All countries in the world
    public List<Country> getAllCountriesByPopulation() throws SQLException {
        String sql = """
                SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS Capital
                FROM country co
                LEFT JOIN city ci ON co.Capital = ci.ID
                ORDER BY co.Population DESC;
                """;

        List<Country> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapCountry(rs));
        }
        return list;
    }

    // 2. Countries in a continent
    public List<Country> getCountriesByContinent(String continent) throws SQLException {
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
}