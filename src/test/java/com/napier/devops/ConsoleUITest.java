package com.napier.devops;

// Add package here if ConsoleUI has one, e.g.:
// package com.napier.devops;

import com.napier.devops.model.City;
import com.napier.devops.model.Country;
import com.napier.devops.model.LanguageReport;
import com.napier.devops.model.PopulationReport;
import com.napier.devops.report.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ConsoleUI using mocked ReportService and captured console I/O.
 */
@ExtendWith(MockitoExtension.class)
class ConsoleUITest {

    @Mock
    private ReportService reportService;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void redirectIO() {
        originalOut = System.out;
        originalIn = System.in;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreIO() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    /**
     * Helper to run ConsoleUI with a given input script.
     */
    private void runConsoleWithInput(String inputScript) {
        System.setIn(new ByteArrayInputStream(inputScript.getBytes()));
        ConsoleUI ui = new ConsoleUI(reportService);
        ui.start();
    }

    /**
     * 1) User chooses 0 immediately → exit, no service calls.
     */
    @Test
    void start_whenUserChoosesExitImmediately_doesNotCallReportService() {
        // menu choice: 0 (exit)
        runConsoleWithInput("0\n");

        String output = outContent.toString();
        assertTrue(output.contains("Country Reports Menu"), "Menu should be printed");
        verifyNoInteractions(reportService);
    }

    /**
     * 2) Choice 1 → calls getAllCountries() and prints the table.
     */
    @Test
    void start_option1_callsGetAllCountriesAndPrintsTitle() throws SQLException {
        // Arrange countries returned by service
        Country c1 = mock(Country.class);
        when(c1.getCode()).thenReturn("BLZ");
        when(c1.getName()).thenReturn("Belize");
        when(c1.getContinent()).thenReturn("North America");
        when(c1.getRegion()).thenReturn("Central America");
        when(c1.getPopulation()).thenReturn(400_000L);
        when(c1.getCapital()).thenReturn("Belmopan");

        Country c2 = mock(Country.class);
        when(c2.getCode()).thenReturn("USA");
        when(c2.getName()).thenReturn("United States");
        when(c2.getContinent()).thenReturn("North America");
        when(c2.getRegion()).thenReturn("Northern America");
        when(c2.getPopulation()).thenReturn(330_000_000L);
        when(c2.getCapital()).thenReturn("Washington, D.C.");

        when(reportService.getAllCountries()).thenReturn(List.of(c1, c2));

        // Input: 1 (all countries), then 0 (exit)
        runConsoleWithInput("1\n0\n");

        verify(reportService, times(1)).getAllCountries();
        String output = outContent.toString();

        // Title from printCountries (title string has a leading space; we just check core text)
        assertTrue(output.contains("All Countries in the World"),
                "Output should contain the countries table title");
        assertTrue(output.contains("Belize"), "Output should include a country name from the list");
    }

    /**
     * 3) Choice 4 → top N countries in world, including readPositiveInt validation.
     * We feed -1 and 0 before 5 to exercise the validation loop.
     */
    @Test
    void start_option4_usesReadPositiveIntAndCallsTopCountriesInWorld() throws SQLException {
        // Arrange
        when(reportService.getTopCountriesInWorld(5)).thenReturn(Collections.emptyList());

        /*
         * Input script:
         * 4  -> menu choice: "Top N countries in world"
         * -1 -> first N (invalid, N must be > 0)
         * 0  -> second N (still invalid)
         * 5  -> valid N
         * 0  -> menu choice: exit
         */
        String script = "4\n-1\n0\n5\n0\n";
        runConsoleWithInput(script);

        verify(reportService, times(1)).getTopCountriesInWorld(5);
        String output = outContent.toString();
        assertTrue(output.contains("N must be > 0."),
                "Validation message should be shown for invalid N values");
    }

    /**
     * 4) Choice 23 → population by continent; when service returns empty list,
     * UI should print 'No results for this population report.'
     */
    @Test
    void start_option23_handlesEmptyPopulationListGracefully() throws SQLException {
        when(reportService.getPopulationByContinent()).thenReturn(Collections.emptyList());

        // 23 (population by continent), then 0 (exit)
        runConsoleWithInput("23\n0\n");

        verify(reportService, times(1)).getPopulationByContinent();
        String output = outContent.toString();
        assertTrue(output.contains("No results for this population report."),
                "Should inform user when there is no population data");
    }

    /**
     * 5) If ReportService throws SQLException, ConsoleUI should print an error line
     * and continue running (so user can still exit).
     */
    @Test
    void start_whenServiceThrowsSQLException_printsErrorMessage() throws SQLException {
        when(reportService.getAllCountries())
                .thenThrow(new SQLException("DB is down"));

        // 1 (all countries -> triggers exception), then 0 (exit)
        runConsoleWithInput("1\n0\n");

        verify(reportService, times(1)).getAllCountries();
        String output = outContent.toString();
        assertTrue(output.contains("Error: DB is down"),
                "Console should print the SQLException message");
    }
}
