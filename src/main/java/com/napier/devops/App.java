package com.napier.devops;

import com.napier.devops.report.ReportService;
import com.napier.devops.dao.CountryDAO;
import com.napier.devops.dao.CityDAO;
import java.sql.*;

public class App {

    Connection con;

    public static void main(String[] args) {
        App app = new App();
        app.connect();

        try {
            // DAO
            CountryDAO countryDAO = new CountryDAO(app.con);

            // DAO
            CityDAO cityDAO = new CityDAO(app.con);

            // Service
            ReportService reportService = new ReportService(countryDAO, cityDAO);

            // UI
            ConsoleUI ui = new ConsoleUI(reportService);
            ui.start();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            app.disconnect();
        }
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:33060/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "example"
            );
            System.out.println("Connected.");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (con != null) con.close();
        } catch (Exception ignored) {}
    }
}
