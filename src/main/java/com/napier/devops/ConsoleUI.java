package com.napier.devops;

import com.napier.devops.model.City;
import com.napier.devops.model.Country;
import com.napier.devops.model.PopulationReport; // *** NEW ***
import com.napier.devops.report.ReportService;
import com.napier.devops.model.LanguageReport;


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
            System.out.println("23. Population by continent (total / in cities / not in cities).");
            System.out.println("24. Population by region (total / in cities / not in cities).");
            System.out.println("25. Population by country (total / in cities / not in cities).");
            System.out.println("26. The population of a world.");
            System.out.println("27. The population of a continent.");
            System.out.println("28. The population of a region.");
            System.out.println("29. The population of a country.");
            System.out.println("30. The population of a district.");
            System.out.println("31. The population of a city.");
            System.out.println("32. Language speakers report.");
            System.out.println("0. Exit");
            System.out.print("Enter option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1 -> printCountries(reportService.getAllCountries()," All Countries in the World");
                    case 2 -> {
                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();
                        printCountries(reportService.getCountriesByContinent(continent),"Countries in Continent: " + continent);
                    }
                    case 3 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCountries(reportService.getCountriesByRegion(region),"Countries in Region: " + region );
                    }
                    case 4 -> {
                        int n = readPositiveInt("Enter N (top N countries in the world): ");
                        printCountries(reportService.getTopCountriesInWorld(n), "Top " + n + " Countries in the World");
                    }

                    case 5 -> {
                        String continent = readString("Enter continent: ");
                        int n = readPositiveInt("Enter N (top N in this continent): ");
                        printCountries(reportService.getTopCountriesInContinent(continent, n),"Top " + n + " Countries in Continent: " + continent);
                    }

                    case 6 -> {
                        String region = readString("Enter region: ");
                        int n = readPositiveInt("Enter N (top N in this region): ");
                        printCountries(reportService.getTopCountriesInRegion(region, n), "Top " + n + " Countries in Region: " + region);
                    }

                    case 7 -> printCities(reportService.getAllCitiesByPopulation(),
                            "All Cities in the World");


                    case 8 -> {
                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();
                        printCities(reportService.getAllCitiesByContinent(continent),
                                "Cities in Continent - " + continent);
                    }

                    case 9 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCities(reportService.getAllCitiesByRegion(region),
                                "Cities in Region - " + region);
                    }

                    case 10 -> {
                        System.out.print("Enter country: ");
                        String country = scanner.nextLine();
                        printCities(reportService.getAllCitiesByCountry(country),
                                "Cities in Country - " + country);
                    }

                    case 11 -> {
                        System.out.print("Enter district: ");
                        String district = scanner.nextLine();
                        printCities(reportService.getAllCitiesByDistrict(district),
                                "Cities in District - " + district);
                    }

                    case 12 ->{
                        //printCities(reportService.getAllTopNCitiesByPopulation());
                        int n = readPositiveInt("Enter N (top N Cities in the world): ");
                         printCities(reportService.getAllTopNCitiesByPopulation(n),
                                 "Top " + n + " Populated Cities in the World");
                    }


                    case 13 -> {
                        String continent = readString("Enter continent: ");
                        int n = readPositiveInt("Enter N (top N Cities in this continent): ");
                        printCities(reportService.getAllTopNCitiesByContinent(continent,n),
                                "Top " + n + " Populated Cities in Continent - " + continent);
                    }

                    case 14 -> {
                        String region = readString("Enter region: ");
                        int n = readPositiveInt("Enter N (top N Cities in this region): ");
                        printCities(reportService.getAllTopNCitiesByRegion(region,n),
                                "Top " + n + " Populated Cities in Region - " + region);
                    }

                    case 15 -> {
                       String country = readString("Enter Country: ");
                        int n = readPositiveInt("Enter N (top N Cities in this Country): ");
                        printCities(reportService.getAllTopNCitiesByCountry(country, n),
                                "Top " + n + " Populated Cities in Country - " + country);
                    }

                    case 16 -> {
                        String district = readString("Enter district: ");
                        int n = readPositiveInt("Enter N (top N Cities in this district): ");
                        printCities(reportService.getAllTopNCitiesByDistrict(district,n),
                                "Top " + n + " Populated Cities in District - " + district);
                    }

                    case 17 -> printCapitals(reportService.getAllCapitalByPopulation(),
                            "All Capital Cities in the World");

                    case 18 -> {
                        System.out.print("Enter continent: ");
                        String continent = scanner.nextLine();
                        printCapitals(reportService.getAllCapitalByContinent(continent),
                                "All Capital Cities in Continent - " + continent);
                    }
                    case 19 -> {
                        System.out.print("Enter region: ");
                        String region = scanner.nextLine();
                        printCapitals(reportService.getAllCapitalByRegion(region),
                                "All Capital Cities in Region - " + region);
                    }

                    case 20 ->{
                        int n = readPositiveInt("Enter N (top N Capital Cities in the world): ");
                        printCapitals(reportService.getTopNCapitalByPopulation(n),
                                "The Top " + n + "Populated Capitals Cities in the World");
                    }

                    case 21 -> {
                        String continent = readString("Enter continent: ");
                        int n = readPositiveInt("Enter N (top N Capital Cities in this continent): ");
                        printCapitals(reportService.getTopNCapitalByContinent(continent, n),
                                "The Top " + n + " Populated Capital Cities in Continent - " + continent);
                    }

                    case 22 -> {
                        String region = readString("Enter region: ");
                        int n = readPositiveInt("Enter N (top N Capital Cities in this region): ");
                        printCapitals(reportService.getTopNCapitalByRegion(region,n),
                                "The Top " + n +" Capital Cities in this Region - " + region);
                    }
                    case 23 -> {
                        List<PopulationReport> list = reportService.getPopulationByContinent();
                        printPopulationReportTable("The Population of People, People Living " +
                                "in cities, and people not living in cities in each Continent", list);
                    }

                    case 24 -> {
                        List<PopulationReport> list = reportService.getPopulationByRegion();
                        printPopulationReportTable("The Population of People, People Living " +
                                "in cities, and people not living in cities in each Region", list);
                    }

                    case 25 -> {
                        List<PopulationReport> list = reportService.getPopulationByCountry();
                        printPopulationReportTable("The Population of People, People Living " +
                                "in cities, and people not living in cities in each Country", list);
                    }

                    case 26 -> {
                        // World population
                        PopulationReport report = reportService.getWorldPopulationReport();
                        printPopulationReport("The Population of the World",report);
                    }

                    case 27 -> {
                        String continent = readString("Enter continent: ");
                        PopulationReport report = reportService.getContinentPopulationReport(continent);
                        printPopulationReport("The Population of Continent - " + continent,report);
                    }


                    case 28 -> {
                        String region = readString("Enter region: ");
                        PopulationReport report = reportService.getRegionPopulationReport(region);
                        printPopulationReport("The Population of Region - " + region,report);
                    }

                    case 29 -> {
                        String country = readString("Enter country: ");
                        PopulationReport report = reportService.getCountryPopulationReport(country);
                        printPopulationReport("The Population of the Country of - " + country,report);
                    }

                    case 30 -> {
                        // District population
                        String district = readString("Enter district: ");
                        PopulationReport report = reportService.getDistrictPopulationReport(district);
                        printPopulationReport("The Population of the district of - " + district,report);
                    }
                    case 31 -> {
                        // City population
                        String city = readString("Enter city: ");
                        PopulationReport report = reportService.getCityPopulationReport(city);
                        printPopulationReport("The Population of a City - " + city,report);
                    }
                    case 32 -> {
                        List<LanguageReport> list = reportService.getLanguageReport();
                        printLanguageReport(list);
                    }


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

    //new print countries with title and ASCII table format.
    private void printCountries(List<Country> list, String title) {
        if (list == null || list.isEmpty()) {
            System.out.println("No results.");
            return;
        }

        int wCode = 5;
        int wName = 40;
        int wCont = 15;
        int wReg  = 25;
        int wPop  = 15;
        int wCap  = 20;

        System.out.println();
        System.out.println("=== " + title + " ===");

        // Top border
        System.out.print("┌");
        System.out.print("─".repeat(wCode + 2)); System.out.print("┬");
        System.out.print("─".repeat(wName + 2)); System.out.print("┬");
        System.out.print("─".repeat(wCont + 2)); System.out.print("┬");
        System.out.print("─".repeat(wReg  + 2)); System.out.print("┬");
        System.out.print("─".repeat(wPop  + 2)); System.out.print("┬");
        System.out.print("─".repeat(wCap  + 2)); System.out.println("┐");

        // Header
        System.out.printf(
                "│ %-"+wCode+"s │ %-"+wName+"s │ %-"+wCont+"s │ %-"+wReg+"s │ %-"+wPop+"s │ %-"+wCap+"s │%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital"
        );

        // Separator
        System.out.print("├");
        System.out.print("─".repeat(wCode + 2)); System.out.print("┼");
        System.out.print("─".repeat(wName + 2)); System.out.print("┼");
        System.out.print("─".repeat(wCont + 2)); System.out.print("┼");
        System.out.print("─".repeat(wReg  + 2)); System.out.print("┼");
        System.out.print("─".repeat(wPop  + 2)); System.out.print("┼");
        System.out.print("─".repeat(wCap  + 2)); System.out.println("┤");

        // Rows
        for (Country c : list) {
            System.out.printf(
                    "│ %-"+wCode+"s │ %-"+wName+"s │ %-"+wCont+"s │ %-"+wReg+"s │ %,"+wPop+"d │ %-"+wCap+"s │%n",
                    c.getCode(),
                    c.getName(),
                    c.getContinent(),
                    c.getRegion(),
                    c.getPopulation(),
                    c.getCapital()
            );
        }

        // Bottom border
        System.out.print("└");
        System.out.print("─".repeat(wCode + 2)); System.out.print("┴");
        System.out.print("─".repeat(wName + 2)); System.out.print("┴");
        System.out.print("─".repeat(wCont + 2)); System.out.print("┴");
        System.out.print("─".repeat(wReg  + 2)); System.out.print("┴");
        System.out.print("─".repeat(wPop  + 2)); System.out.print("┴");
        System.out.print("─".repeat(wCap  + 2)); System.out.println("┘");
    }

    //New PrintCities with title and ASCII Table format
    private void printCities(List<City> list, String title) {
        if (list == null || list.isEmpty()) {
            System.out.println("No results.");
            return;
        }

        int wCity  = 30;
        int wCtry  = 30;
        int wDist  = 20;
        int wPop   = 15;

        System.out.println();
        System.out.println("=== " + title + " ===");

        // Top border
        System.out.print("┌");
        System.out.print("─".repeat(wCity + 2)); System.out.print("┬");
        System.out.print("─".repeat(wCtry + 2)); System.out.print("┬");
        System.out.print("─".repeat(wDist + 2)); System.out.print("┬");
        System.out.print("─".repeat(wPop  + 2)); System.out.println("┐");

        // Header
        System.out.printf(
                "│ %-"+wCity+"s │ %-"+wCtry+"s │ %-"+wDist+"s │ %-"+wPop+"s │%n",
                "City", "Country", "District", "Population"
        );

        // Separator
        System.out.print("├");
        System.out.print("─".repeat(wCity + 2)); System.out.print("┼");
        System.out.print("─".repeat(wCtry + 2)); System.out.print("┼");
        System.out.print("─".repeat(wDist + 2)); System.out.print("┼");
        System.out.print("─".repeat(wPop  + 2)); System.out.println("┤");

        // Rows
        for (City ct : list) {
            System.out.printf(
                    "│ %-"+wCity+"s │ %-"+wCtry+"s │ %-"+wDist+"s │ %,"+wPop+"d │%n",
                    ct.getName(),
                    ct.getCountry(),
                    ct.getDistrict(),
                    ct.getPopulation()
            );
        }

        // Bottom border
        System.out.print("└");
        System.out.print("─".repeat(wCity + 2)); System.out.print("┴");
        System.out.print("─".repeat(wCtry + 2)); System.out.print("┴");
        System.out.print("─".repeat(wDist + 2)); System.out.print("┴");
        System.out.print("─".repeat(wPop  + 2)); System.out.println("┘");
    }

//new print format for PrintCapitals
private void printCapitals(List<Country> list, String title) {
    if (list == null || list.isEmpty()) {
        System.out.println("No results.");
        return;
    }

    int wCap  = 30;
    int wCtry = 30;
    int wPop  = 15;

    System.out.println();
    System.out.println("=== " + title + " ===");

    // Top border
    System.out.print("┌");
    System.out.print("─".repeat(wCap  + 2)); System.out.print("┬");
    System.out.print("─".repeat(wCtry + 2)); System.out.print("┬");
    System.out.print("─".repeat(wPop  + 2)); System.out.println("┐");

    // Header
    System.out.printf(
            "│ %-"+wCap+"s │ %-"+wCtry+"s │ %-"+wPop+"s │%n",
            "Capital", "Country", "Population"
    );

    // Separator
    System.out.print("├");
    System.out.print("─".repeat(wCap  + 2)); System.out.print("┼");
    System.out.print("─".repeat(wCtry + 2)); System.out.print("┼");
    System.out.print("─".repeat(wPop  + 2)); System.out.println("┤");

    // Rows
    for (Country ca : list) {
        System.out.printf(
                "│ %-"+wCap+"s │ %-"+wCtry+"s │ %,"+wPop+"d │%n",
                ca.getCapital(),
                ca.getName(),
                ca.getPopulation()
        );
    }

    // Bottom border
    System.out.print("└");
    System.out.print("─".repeat(wCap  + 2)); System.out.print("┴");
    System.out.print("─".repeat(wCtry + 2)); System.out.print("┴");
    System.out.print("─".repeat(wPop  + 2)); System.out.println("┘");
}

    // *** NEW *** with title
    private void printPopulationReport(String title, PopulationReport report) {
        if (report == null) {
            System.out.println("No results for this population report.");
            return;
        }

        String name = report.getName();
        long total = report.getTotalPopulation();
        long city = report.getCityPopulation();
        long nonCity = report.getNonCityPopulation();
        double pctCity = report.getCityPopulationPercent();
        double pctNonCity = report.getNonCityPopulationPercent();

        System.out.println();
        System.out.println("=== " + title + " ===");

        System.out.println("┌──────────────────────────────┬──────────────────────────┬───────────────────────────────┬────────────────────────────────┐");
        System.out.printf ("│ %-28s │ %-24s │ %-29s │ %-30s │%n",
                "Name",
                "Total Population",
                "Living in Cities",
                "Not Living in Cities"
        );
        System.out.println("├──────────────────────────────┼──────────────────────────┼───────────────────────────────┼────────────────────────────────┤");

        System.out.printf("│ %-28s │ %,24d │ %,15d (%.2f%%) │ %,15d (%.2f%%) │%n",
                name,
                total,
                city, pctCity,
                nonCity, pctNonCity
        );

        System.out.println("└──────────────────────────────┴──────────────────────────┴───────────────────────────────┴────────────────────────────────┘");
    }

    //new print languagre report format
private void printLanguageReport(List<LanguageReport> list) {
    if (list == null || list.isEmpty()) {
        System.out.println("No language data found.");
        return;
    }

    System.out.println();
    System.out.println("=== " + "The number of people who speak the following languages: Chinese, English, Hindi, Spanish, Arabic." + " ===");

    System.out.println("┌────────────────────────┬───────────────────────────┬──────────────────────────────┐");
    System.out.printf ("│ %-22s │ %-25s │ %-28s │%n",
            "Language",
            "Speakers",
            "% of World Population"
    );
    System.out.println("├────────────────────────┼───────────────────────────┼──────────────────────────────┤");

    for (LanguageReport lr : list) {
        System.out.printf("│ %-22s │ %,25d │ %26.2f%% │%n",
                lr.getLanguage(),
                lr.getSpeakers(),
                lr.getPercentOfWorld()
        );
    }

    System.out.println("└────────────────────────┴───────────────────────────┴──────────────────────────────┘");
}


    private void printPopulationReportTable(String title, List<PopulationReport> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No results for this population report.");
            return;
        }

        System.out.println();
        System.out.println("=== " + title + " ===");

        // Column widths — *must match formatting*
        int w1 = 25;  // Name
        int w2 = 24;  // Total Population
        int w3 = 31;  // Living in Cities
        int w4 = 30;  // Not Living in Cities

        // Top border
        System.out.print("┌");
        System.out.print("─".repeat(w1 + 2)); System.out.print("┬");
        System.out.print("─".repeat(w2 + 2)); System.out.print("┬");
        System.out.print("─".repeat(w3 + 2)); System.out.print("┬");
        System.out.print("─".repeat(w4 + 2)); System.out.println("┐");

        // Header row
        System.out.printf(
                "│ %-"+w1+"s │ %-"+w2+"s │ %-"+w3+"s │ %-"+w4+"s │%n",
                "Name",
                "Total Population",
                "Living in Cities",
                "Not Living in Cities"
        );

        // Separator
        System.out.print("├");
        System.out.print("─".repeat(w1 + 2)); System.out.print("┼");
        System.out.print("─".repeat(w2 + 2)); System.out.print("┼");
        System.out.print("─".repeat(w3 + 2)); System.out.print("┼");
        System.out.print("─".repeat(w4 + 2)); System.out.println("┤");

        // Data rows
        for (PopulationReport r : list) {
            System.out.printf(
                    "│ %-"+w1+"s │ %,"+w2+"d │ %,"+(w3-16)+"d (%.2f%%) │ %,"+(w4-16)+"d (%.2f%%) │%n",
                    r.getName(),
                    r.getTotalPopulation(),
                    r.getCityPopulation(),
                    r.getCityPopulationPercent(),
                    r.getNonCityPopulation(),
                    r.getNonCityPopulationPercent()
            );
        }

        // Bottom border
        System.out.print("└");
        System.out.print("─".repeat(w1 + 2)); System.out.print("┴");
        System.out.print("─".repeat(w2 + 2)); System.out.print("┴");
        System.out.print("─".repeat(w3 + 2)); System.out.print("┴");
        System.out.print("─".repeat(w4 + 2)); System.out.println("┘");
    }

}