package com.napier.devops;

import com.napier.devops.model.City;
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
            System.out.println("4. The top N populated countries in the world.");
            System.out.println("5. The top N populated countries in a continent.");
            System.out.println("6. The top N populated countries in a region.");
            System.out.println("7. All the cities in the world.");
            System.out.println("8. All the cities in a continent.");
            System.out.println("9. All the cities in a region.");
            System.out.println("10. All the cities in a country.");
            System.out.println("11. All the cities in a district.");
            System.out.println("12. The top N populated cities in the world.");
            System.out.println("13. The top N populated cities in a continent.");
            System.out.println("14. The top N populated cities in a region.");
            System.out.println("15. The top N populated cities in a country.");
            System.out.println("16. The top N populated cities in a district.");
            System.out.println("17. All capitals in the world");
            System.out.println("18. All capitals in a continent");
            System.out.println("19. All capitals in a region");
            System.out.println("20. The top N populated capitals in the world.");
            System.out.println("21. The top N populated capitals in a continent.");
            System.out.println("22. The top N populated capitals in a region.");
            System.out.println("23. Exit");
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
                    case 4 ->{
                        printCountries(reportService.getAllTopNCountriesByPopulation());
                    }
                    case 5 -> {
                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();
                        printCountries(reportService.getAllTopNCountriesByContinent(continent));
                    }
                    case 6 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCountries(reportService.getAllTopNCountriesByRegion(region));
                    }

                    case 7 ->{
                        printCities(reportService.getAllCitiesByPopulation());
                    }

                    case 8 -> {
                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();
                        printCities(reportService.getAllCitiesByContinent(continent));
                    }

                    case 9 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCities(reportService.getAllCitiesByRegion(region));
                    }

                    case 10 -> {
                        System.out.print("Enter country: ");
                        String country = scanner.nextLine();
                        printCities(reportService.getAllCitiesByCountry(country));
                    }

                    case 11 -> {
                        System.out.print("Enter district: ");
                        String district = scanner.nextLine();
                        printCities(reportService.getAllCitiesByDistrict(district));
                    }

                    case 12 -> {
                        printCities(reportService.getAllTopNCitiesByPopulation());
                    }
                    case 13 -> {
                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();
                        printCities(reportService.getAllTopNCitiesByContinent(continent));
                    }

                    case 14 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCities(reportService.getAllTopNCitiesByRegion(region));
                    }

                    case 15 -> {
                        System.out.print("Enter country: ");
                        String country = scanner.nextLine();
                        printCities(reportService.getAllTopNCitiesByCountry(country));
                    }

                    case 16 -> {
                        System.out.print("Enter district: ");
                        String district = scanner.nextLine();
                        printCities(reportService.getAllTopNCitiesByDistrict(district));
                    }

                    case 17 -> printCapitals(reportService.getAllCountries());
                    case 18 -> {
                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();
                        printCapitals(reportService.getCountriesByContinent(continent));
                    }
                    case 19 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCapitals(reportService.getCountriesByRegion(region));
                    }
                    case 20 ->{
                        printCapitals(reportService.getAllTopNCountriesByPopulation());
                    }
                    case 21 -> {
                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();
                        printCapitals(reportService.getAllTopNCountriesByContinent(continent));
                    }
                    case 22 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCapitals(reportService.getAllTopNCountriesByRegion(region));
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

        System.out.printf("%-5s %-50s %-15s %-30s %-15s %-20s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");

        for (Country c : list) {
            System.out.printf("%-5s %-50s %-15s %-30s %-15d %-20s%n",
                    c.getCode(),
                    c.getName(),
                    c.getContinent(),
                    c.getRegion(),
                    c.getPopulation(),
                    c.getCapital());
        }
    }
    private void printCities(List<City> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No results.");
            return;
        }

        System.out.printf("%-5s %-35s %-15s %-30s %-40s %-20s %-15s%n",
                "ID", "City", "Continent", "Region", "Country","District", "Population");

        for (City ct : list) {
            System.out.printf("%-5s %-35s %-15s %-30s %-40s %-20s %-15s%n",
                    ct.getId(),
                    ct.getName(),
                    ct.getContinent(),
                    ct.getRegion(),
                    ct.getCountry(),
                    ct.getDistrict(),
                    ct.getPopulation());

        }
    }
    private void printCapitals(List<Country> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No results.");
            return;
        }

        System.out.printf("%-35s  %-15s %-30s %-15s%n",
                "Capital", "Continent", "Region", "Population");

        for (Country ca : list) {
            System.out.printf("%-35s %-15s %-30s %-15s%n",
                    ca.getCapital(),
                    ca.getContinent(),
                    ca.getRegion(),
                    ca.getPopulation());

        }
    }
}