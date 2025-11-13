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
            printMainMenu();
            int choice = readInt("Select report number (0 to exit): ");

            try {
                switch (choice) {
                    // ----- Country reports -----
                    case 1 -> printCountries(reportService.getAllCountriesByPopulation());
                    case 2 -> {
                        String continent = readString("Enter continent: ");
                        printCountries(reportService.getCountriesByContinent(continent));
                    }
                    case 3 -> {
                        String region = readString("Enter region: ");
                        printCountries(reportService.getCountriesByRegion(region));
                    }

                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (SQLException e) {
                System.out.println("Error running report: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Exiting.");
    }

    // ---------------- Menus & input helpers ----------------

    private void printMainMenu() {
        System.out.println("\n=== Population Reports Menu ===");
        System.out.println(" 1. Countries in the world (by population)");
        System.out.println(" 2. Countries in a continent (by population)");
        System.out.println(" 3. Countries in a region (by population)");
        System.out.println(" 4. Top N countries in the world");
        System.out.println(" 5. Top N countries in a continent");
        System.out.println(" 6. Top N countries in a region");

        System.out.println(" 7. Cities in the world (by population)");
        System.out.println(" 8. Cities in a continent (by population)");
        System.out.println(" 9. Cities in a region (by population)");
        System.out.println("10. Cities in a country (by population)");
        System.out.println("11. Cities in a district (by population)");
        System.out.println("12. Top N cities in the world");
        System.out.println("13. Top N cities in a continent");
        System.out.println("14. Top N cities in a region");
        System.out.println("15. Top N cities in a country");
        System.out.println("16. Top N cities in a district");

        System.out.println("17. Capital cities in the world (by population)");
        System.out.println("18. Capital cities in a continent (by population)");
        System.out.println("19. Capital cities in a region (by population)");
        System.out.println("20. Top N capital cities in the world");
        System.out.println("21. Top N capital cities in a continent");
        System.out.println("22. Top N capital cities in a region");

        System.out.println("23. Population report by continent");
        System.out.println("24. Population report by region");
        System.out.println("25. Population report by country");

        System.out.println("26. Population of the world");
        System.out.println("27. Population of a continent");
        System.out.println("28. Population of a region");
        System.out.println("29. Population of a country");
        System.out.println("30. Population of a district");
        System.out.println("31. Population of a city");

        System.out.println("32. Language speakers (Chinese, English, Hindi, Spanish, Arabic)");
        System.out.println(" 0. Exit");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) return value;
            System.out.println("Value must be > 0.");
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // ---------------- Printing helpers ----------------

    private void printCountries(List<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No results.");
            return;
        }
        System.out.printf("%-5s %-40s %-15s %-25s %-15s %-25s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        for (Country c : countries) {
            System.out.printf("%-5s %-40s %-15s %-25s %-15d %-25s%n",
                    c.getCode(), c.getName(), c.getContinent(),
                    c.getRegion(), c.getPopulation(), c.getCapital());
        }
    }

