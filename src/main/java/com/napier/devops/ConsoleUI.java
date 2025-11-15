package com.napier.devops;

import com.napier.devops.model.Country;
import com.napier.devops.report.ReportService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final ReportService reportService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(ReportService reportService) {
        this.reportService = reportService;
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Country Reports Menu ===");
            System.out.println("1. All countries in the world");
            System.out.println("2. All countries in a continent");
            System.out.println("3. All countries in a region");
            System.out.println("0. Exit");
            System.out.print("Enter option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1 -> printCountries(reportService.getAllCountries());
                    case 2 -> {
                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();
                        printCountries(reportService.getCountriesByContinent(continent));
                    }
                    case 3 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCountries(reportService.getCountriesByRegion(region));
                    }
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printCountries(List<Country> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No results.");
            return;
        }

        System.out.printf("%-5s %-40s %-15s %-20s %-15s %-20s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");

        for (Country c : list) {
            System.out.printf("%-5s %-40s %-15s %-20s %-15d %-20s%n",
                    c.getCode(),
                    c.getName(),
                    c.getContinent(),
                    c.getRegion(),
                    c.getPopulation(),
                    c.getCapital());
        }
    }
}