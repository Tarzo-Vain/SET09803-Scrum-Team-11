package com.napier.devops.dao;

import com.napier.devops.model.City;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public record CityDAO(Connection con) {

    private City mapCity(ResultSet rs) throws SQLException {
        City ct = new City();
        //ct.setId(rs.getInt("ID"));
        ct.setName(rs.getString("Name"));
       // ct.setContinent(rs.getString("Continent"));
       // ct.setRegion(rs.getString("Region"));
        ct.setCountry(rs.getString("Country"));
        ct.setDistrict(rs.getString("District"));
        ct.setPopulation(rs.getLong("Population"));

        return ct;
    }

    // 7. All Cities in the world
    public List<City> getAllCitiesByPopulation() throws SQLException {
       /*
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                ORDER BY ci.Population DESC;
                """;

        */
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                ORDER BY ci.Population DESC limit 10;
                """;


        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapCity(rs));
        }
        return list;
    }

    // 8. Cities in a continent
    public List<City> getAllCitiesByContinent(String continent) throws SQLException {
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Continent = ?
                ORDER BY ci.Population DESC limit 10;
                """;

        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCity(rs));
            }
        }
        return list;
    }

    // 9. Cities in a region
    public List<City> getAllCitiesByRegion(String region) throws SQLException {
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Region = ?
                ORDER BY ci.Population DESC limit 10;
                """;

        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCity(rs));
            }
        }
        return list;
    }

    // 10. Cities in a Country
    public List<City> getAllCitiesByCountry(String country) throws SQLException {
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Name = ?
                ORDER BY ci.Population DESC limit 10;
                """;

        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, country);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCity(rs));
            }
        }
        return list;
    }

    // 11. Cities in a District
    public List<City> getAllCitiesByDistrict(String district) throws SQLException {
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                WHERE ci.District = ?
                ORDER BY ci.Population DESC limit 10;
                """;

        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, district);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCity(rs));
            }
        }
        return list;
    }

    // 12. All Cities in the world
    public List<City> getAllTopNCitiesByPopulation() throws SQLException {
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                ORDER BY ci.Population DESC limit 10;
                """;

        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapCity(rs));
        }
        return list;
    }

    // 13. Cities in a continent
    public List<City> getAllTopNCitiesByContinent(String continent) throws SQLException {
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Continent = ?
                ORDER BY ci.Population DESC;
                """;

        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCity(rs));
            }
        }
        return list;
    }

    // 14. Cities in a region
    public List<City> getAllTopNCitiesByRegion(String region) throws SQLException {
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Region = ?
                ORDER BY ci.Population DESC;
                """;

        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCity(rs));
            }
        }
        return list;
    }

    // 15. Cities in a continent
    public List<City> getAllTopNCitiesByCountry(String country) throws SQLException {
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Name = ?
                ORDER BY ci.Population DESC;
                """;

        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, country);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCity(rs));
            }
        }
        return list;
    }

    // 16. Cities in a continent
    public List<City> getAllTopNCitiesByDistrict(String district) throws SQLException {
        String sql = """
                SELECT ci.ID, ci.Name, ci.District, ci.Population, co.Code, co.Name AS Country, co.Continent, co.Region
                FROM city ci
                LEFT JOIN country co ON ci.CountryCode = co.Code
                WHERE ci.District = ?
                ORDER BY ci.Population DESC;
                """;

        List<City> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, district);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapCity(rs));
            }
        }
        return list;
    }
}