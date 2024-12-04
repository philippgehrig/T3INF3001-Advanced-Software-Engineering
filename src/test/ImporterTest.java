package test;

import data.Importer;
import data.PackageConfiguration;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ImporterTest class contains the unit tests for the Importer class.
 */
class ImporterTest {

    private static final String VALID_FILE_PATH_WITH_GIRTH = "src/test/packageConfigurations.csv";
    private static final String VALID_FILE_PATH_WITHOUT_GIRTH = "src/test/packageConfigurationsWithoutGirth.csv";
    private static final String INVALID_FORMAT_FILE_PATH = "src/test/invalidFormat.csv";
    private static final String NONEXISTENT_FILE_PATH = "src/test/nonexistent.csv";
    private static final String INVALID_NUMBER_FORMAT_FILE_PATH = "src/test/invalidNumberFormat.csv";
    private static final String TOO_SHORT_FILE_PATH = "src/test/tooShort.csv";

    private static final String NOT_NULL_MESSAGE = "The package configurations list should not be null";
    private static final String EMPTY_MESSAGE = "The package configurations list should be empty";

    // logger of Importer Class for deactivation
    private static final Logger logger = Logger.getLogger(Importer.class.getName());
    private final Level originalLogLevel = logger.getLevel();

    private final Importer importer = new Importer();

    /**
     * Write all the test files before running the tests.
     * @throws IOException if an I/O error occurs
     */
    @BeforeAll
    static void setUp() throws IOException {
        try (FileWriter writer = new FileWriter(VALID_FILE_PATH_WITH_GIRTH)) {
            writer.write("length;width;height;weight;girth;price\n");
            writer.write("100;50;30;200;150;19.99\n");
            writer.write("200;100;60;400;300;29.99\n");
            writer.write("300;150;90;600;450;39.99\n");
            writer.write("400;200;120;800;600;49.99\n");
            writer.write("500;250;150;1000;750;59.99\n");
        }
        try (FileWriter writer = new FileWriter(INVALID_FORMAT_FILE_PATH)) {
            writer.write("invalid;data;format");
        }
        try (FileWriter writer = new FileWriter(VALID_FILE_PATH_WITHOUT_GIRTH)){
            writer.write("length;width;height;weight;;price\n");
            writer.write("100;50;30;200;;19.99\n");
            writer.write("200;100;60;400;;29.99\n");
            writer.write("300;150;90;600;;39.99\n");
            writer.write("400;200;120;800;;49.99\n");
            writer.write("500;250;150;1000;;59.99\n");
        }
        try (FileWriter writer = new FileWriter(INVALID_NUMBER_FORMAT_FILE_PATH)){
            writer.write("length;width;height;weight;girth;price\n");
            writer.write("100;50;30;200;150;19.99\n");
            writer.write("invalid;100;60;400;300;29.99\n"); // Invalid length
            writer.write("300;invalid;90;600;450;39.99\n"); // Invalid height
            writer.write("400;200;invalid;800;600;49.99\n"); // Invalid width
            writer.write("500;250;150;invalid;750;59.99\n"); // Invalid weight
            writer.write("600;300;180;1200;invalid;69.99\n"); // Invalid girth
            writer.write("700;350;210;1400;900;invalid\n"); // Invalid price
        }
        try (FileWriter writer = new FileWriter(TOO_SHORT_FILE_PATH)) {
            writer.write("Length;Height;Width;Weight\n");
            writer.write("100;50;30;200;\n");
        }
    }

    /**
     * Delete all the test files after running the tests.
     * @throws IOException if an I/O error occurs
     */
    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(VALID_FILE_PATH_WITH_GIRTH));
        Files.deleteIfExists(Paths.get(INVALID_FORMAT_FILE_PATH));
        Files.deleteIfExists(Paths.get(NONEXISTENT_FILE_PATH));
        Files.deleteIfExists(Paths.get(VALID_FILE_PATH_WITHOUT_GIRTH));
        Files.deleteIfExists(Paths.get(INVALID_NUMBER_FORMAT_FILE_PATH));
        Files.deleteIfExists(Paths.get(TOO_SHORT_FILE_PATH));
    }

    /**
     * Test the importPackageConfigurations method with a valid file containing girth.
     */
    @Test
    void testImportPackageConfigurations() {
        List<PackageConfiguration> packageConfigurations =
                importer.importPackageConfigurations(VALID_FILE_PATH_WITH_GIRTH);

        assertNotNull(packageConfigurations, NOT_NULL_MESSAGE);
        assertEquals(5, packageConfigurations.size(),
                "The package configurations list should contain 5 elements");
        assertEquals(100, packageConfigurations.getFirst().getLength());
        assertEquals(50, packageConfigurations.getFirst().getWidth());
        assertEquals(30, packageConfigurations.getFirst().getHeight());
        assertEquals(200, packageConfigurations.getFirst().getWeight());
        assertEquals(150, packageConfigurations.getFirst().getGirth());
        assertEquals(19.99, packageConfigurations.getFirst().getPrice(), 0.01);

    }

    /**
     * Test the importPackageConfigurations method with a valid file not containing girth.
     */
    @Test
    void testImportPackageConfigurationsFileNotFound() {
        logger.setLevel(Level.OFF);

        List<PackageConfiguration> packageConfigurations =
                importer.importPackageConfigurations(NONEXISTENT_FILE_PATH);

        assertNotNull(packageConfigurations, NOT_NULL_MESSAGE);
        assertTrue(packageConfigurations.isEmpty(), EMPTY_MESSAGE);

        logger.setLevel(originalLogLevel);
    }

    /**
     * Test the importPackageConfigurations method with a valid file not containing girth.
     */
    @Test
    void testImportPackageConfigurationsInvalidFormat() {
        logger.setLevel(Level.OFF);

        List<PackageConfiguration> packageConfigurations =
                importer.importPackageConfigurations(INVALID_FORMAT_FILE_PATH);

        assertNotNull(packageConfigurations, NOT_NULL_MESSAGE);
        assertTrue(packageConfigurations.isEmpty(), EMPTY_MESSAGE);

        logger.setLevel(originalLogLevel);
    }

    /**
     * Test the importPackageConfigurations method with a valid file not containing girth.
     */
    @Test
    void testImportPackageConfigurationsInvalidNumberFormat() {
        logger.setLevel(Level.OFF);

        List<PackageConfiguration> packageConfigurations =
                importer.importPackageConfigurations(INVALID_NUMBER_FORMAT_FILE_PATH);

        assertNotNull(packageConfigurations, NOT_NULL_MESSAGE);
        assertEquals(1, packageConfigurations.size(),
                "The package configurations list should contain 1 valid element");

        logger.setLevel(originalLogLevel);
    }
    /**
     * Test the importPackageConfigurations method with a valid file not containing girth.
     */
    @Test
    void testImportPackageConfigurationsTooShort() {
        logger.setLevel(Level.OFF);

        List<PackageConfiguration> packageConfigurations =
                importer.importPackageConfigurations(TOO_SHORT_FILE_PATH);

        assertNotNull(packageConfigurations, NOT_NULL_MESSAGE);
        assertTrue(packageConfigurations.isEmpty(), EMPTY_MESSAGE);

        logger.setLevel(originalLogLevel);
    }
}