package test;

import data.Importer;
import data.PackageConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ImporterTest {

    private static final String VALID_FILE_PATH = "src/test/packageConfigurations.csv";
    private static final String INVALID_FORMAT_FILE_PATH = "src/test/invalidFormat.csv";
    private static final String NONEXISTENT_FILE_PATH = "src/test/nonexistent.csv";

    private static final String NOT_NULL_MESSAGE = "The package configurations list should not be null";
    private static final String EMPTY_MESSAGE = "The package configurations list should be empty";

    // logger of importer for deactivation
    private static final Logger logger = Logger.getLogger(Importer.class.getName());
    private final Level originalLogLevel = logger.getLevel();

    private final Importer importer = new Importer();

    @BeforeEach
    void setUp() throws IOException {
        try (FileWriter writer = new FileWriter(VALID_FILE_PATH)) {
            writer.write("Length;Height;Width;Weight;Girth;Price\n");
            writer.write("100;50;30;200;150;19.99\n");
            writer.write("200;100;60;400;300;29.99\n");
            writer.write("300;150;90;600;450;39.99\n");
            writer.write("400;200;120;800;600;49.99\n");
            writer.write("500;250;150;1000;750;59.99\n");
        }
        try (FileWriter writer = new FileWriter(INVALID_FORMAT_FILE_PATH)) {
            writer.write("invalid;data;format");
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(VALID_FILE_PATH));
        Files.deleteIfExists(Paths.get(INVALID_FORMAT_FILE_PATH));
        Files.deleteIfExists(Paths.get(NONEXISTENT_FILE_PATH));
    }

    @Test
    void testImportPackageConfigurations() {
        List<PackageConfiguration> packageConfigurations = importer.importPackageConfigurations(VALID_FILE_PATH);

        assertNotNull(packageConfigurations, NOT_NULL_MESSAGE);
        assertTrue(packageConfigurations.size() == 4 || packageConfigurations.size() == 5,
                "The package configurations list should contain 4 or 5 elements");
        assertEquals(100, packageConfigurations.getFirst().getLength());
        assertEquals(30, packageConfigurations.getFirst().getHeight());
        assertEquals(50, packageConfigurations.getFirst().getWidth());
        assertEquals(200, packageConfigurations.getFirst().getWeight());
        assertEquals(150, packageConfigurations.getFirst().getGirth());
        assertEquals(19.99, packageConfigurations.getFirst().getPrice(), 0.01);

        // Add similar assertions for other package configurations if needed
    }

    @Test
    void testImportPackageConfigurationsFileNotFound() {
        logger.setLevel(Level.OFF);

        List<PackageConfiguration> packageConfigurations = importer.importPackageConfigurations(NONEXISTENT_FILE_PATH);

        assertNotNull(packageConfigurations, NOT_NULL_MESSAGE);
        assertTrue(packageConfigurations.isEmpty(), EMPTY_MESSAGE);

        logger.setLevel(originalLogLevel);
    }

    @Test
    void testImportPackageConfigurationsInvalidFormat() {
        logger.setLevel(Level.OFF);

        List<PackageConfiguration> packageConfigurations = importer.importPackageConfigurations(INVALID_FORMAT_FILE_PATH);

        assertNotNull(packageConfigurations, NOT_NULL_MESSAGE);
        assertTrue(packageConfigurations.isEmpty(), EMPTY_MESSAGE);

        logger.setLevel(originalLogLevel);
    }
}