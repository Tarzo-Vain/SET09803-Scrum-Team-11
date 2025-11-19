package com.napier.devops;

import com.napier.devops.model.City;
import com.napier.devops.model.Country;
import com.napier.devops.model.PopulationReport; // *** NEW ***
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
                    case 4 -> {
                        int n = readPositiveInt("Enter N (top N countries in the world): ");
                        printCountries(reportService.getTopCountriesInWorld(n));
                    }

                    case 5 -> {
                        String continent = readString("Enter continent: ");
                        int n = readPositiveInt("Enter N (top N in this continent): ");
                        printCountries(reportService.getTopCountriesInContinent(continent, n));
                    }

                    case 6 -> {
                        String region = readString("Enter region: ");
                        int n = readPositiveInt("Enter N (top N in this region): ");
                        printCountries(reportService.getTopCountriesInRegion(region, n));
                    }

                    case 7 -> printCities(reportService.getAllCitiesByPopulation());


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

                    case 12 ->{
                        //printCities(reportService.getAllTopNCitiesByPopulation());
                        int n = readPositiveInt("Enter N (top N Cities in the world): ");
                         printCities(reportService.getAllTopNCitiesByPopulation(n));
                    }


                    case 13 -> {
                        //System.out.print("Enter continent: ");
                        //String continent = scanner.nextLine();
                        String continent = readString("Enter continent: ");
                        int n = readPositiveInt("Enter N (top N Cities in this continent): ");
                        printCities(reportService.getAllTopNCitiesByContinent(continent,n));
                    }

                    case 14 -> {
                       // System.out.print("Enter region: ");
                        //String region = scanner.nextLine();
                        String region = readString("Enter region: ");
                        int n = readPositiveInt("Enter N (top N Cities in this region): ");
                        printCities(reportService.getAllTopNCitiesByRegion(region,n));
                    }

                    case 15 -> {
                        //System.out.print("Enter country: ");
                       // String country = scanner.nextLine();
                        String country = readString("Enter Country: ");
                        int n = readPositiveInt("Enter N (top N Cities in this Country): ");
                        printCities(reportService.getAllTopNCitiesByCountry(country, n));
                    }

                    case 16 -> {
                       // System.out.print("Enter district: ");
                      //  String district = scanner.nextLine();
                        String district = readString("Enter district: ");
                        int n = readPositiveInt("Enter N (top N Cities in this district): ");
                        printCities(reportService.getAllTopNCitiesByDistrict(district,n));
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
                        int n = readPositiveInt("Enter N (top N Capital Cities in the world): ");
                        //printCapitals(reportService.getAllTopNCountriesByPopulation(n));
                        printCapitals(reportService.getTopCountriesInWorld(n));
                    }

                    case 21 -> {
                       // System.out.print("Enter continent: ");
                       // String continent = scanner.nextLine();
                        //printCapitals(reportService.getAllTopNCountriesByContinent(continent));
                        String continent = readString("Enter continent: ");
                        int n = readPositiveInt("Enter N (top N Capital Cities in this continent): ");
                        printCapitals(reportService.getTopCountriesInContinent(continent, n));
                    }
/* old code
                    case 22 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCapitals(reportService.getAllTopNCountriesByRegion(region));
                    }

 */
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

// ---------- Input helpers ----------

    private int readInt() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            int n = readInt();
            if (n > 0) return n;
            System.out.println("N must be > 0.");
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    //--------Output Helpers-----------
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
/*
        System.out.printf("%-5s %-35s %-15s %-30s %-40s %-20s %-15s%n",
                "ID", "City", "Continent", "Region", "Country","District", "Population");

 */
        System.out.printf("%-30s %-40s %-20s %-15s%n",
                "City", "Country", "District", "Population");


/*
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
 */
        for (City ct : list) {
            System.out.printf("%-30s %-40s %-20s %-15s%n",

                    ct.getName(),


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

        System.out.printf("%-35s %-35s %-15s%n",
                "Capital", "Country", "Population");

        for (Country ca : list) {
            System.out.printf("%-35s %-35s %-15s%n",
                    ca.getCapital(),
                    ca.getName(),
                    //  ca.getContinent(),
                    // ca.getRegion(),
                    ca.getPopulation());

        }
    }


    private void printCityTable(List<City> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No cities found.");
            return;
        }

        // If you REALLY don't want ID printed, remove the first column here.
        System.out.printf("%-5s %-35s %-20s %-25s %-25s %-20s %-15s%n",
                "ID", "Name", "Country", "Continent", "Region", "District", "Population");

        for (City ct : list) {
            System.out.printf("%-5s %-35s %-20s %-25s %-25s %-20s %,15d%n",
                    ct.getId(),
                    ct.getName(),
                    ct.getCountry(),
                    ct.getContinent(),
                    ct.getRegion(),
                    ct.getDistrict(),
                    ct.getPopulation());
        }
    }

    // *** NEW ***
    // Population Report output
    private void printPopulationReport(PopulationReport report) {
        if (report == null) {
            System.out.println("No results for this population report.");
            return;
        }

        System.out.println("\n=== Population Report ===");
        System.out.println("Name: " + report.getName());
        System.out.printf("%-35s : %,15d%n",
                "Total population",
                report.getTotalPopulation());
        System.out.printf("%-35s : %,15d (%.2f%%)%n",
                "Living in cities",
                report.getCityPopulation(),
                report.getCityPopulationPercent());
        System.out.printf("%-35s : %,15d (%.2f%%)%n",
                "Not living in cities",
                report.getNonCityPopulation(),
                report.getNonCityPopulationPercent());
    }

}