package com.napier.devops.dao;

import com.napier.devops.model.PopulationReport;
import com.napier.devops.model.LanguageReport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryDAOIT {

    static Connection connection;

    @BeforeAll
    static void setUp() throws Exception {
        /*
         * These env vars let us reuse the same test in:
         *  - Local Docker:   host=localhost, port=33060 (or your mapped port)
         *  - GitHub Actions: host=127.0.0.1, port=3306 (MySQL service)
         */
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
        System.out.println("Connected successfully!");
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void testRealDatabaseConnection() throws Exception {
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    void testGetWorldPopulationReport_onRealDB() throws Exception {
        CountryDAO dao = new CountryDAO(connection);

        PopulationReport report = dao.getWorldPopulationReport();

        assertNotNull(report);
        assertNotNull(report.getName());
        assertTrue(report.getTotalPopulation() > 0);

        System.out.println("World population: " + report.getTotalPopulation());
    }

    @Test
    void testGetLanguageReport_onRealDB() throws Exception {
        CountryDAO dao = new CountryDAO(connection);

        List<LanguageReport> languageReports = dao.getLanguageReport();

        assertNotNull(languageReports);
        assertFalse(languageReports.isEmpty());

        languageReports.forEach(lang ->
                System.out.println(lang.getLanguage() + ": " + lang.getSpeakers())
        );
    }
}