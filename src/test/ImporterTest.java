package test;

import data.Importer;
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

    private static final String VALID_FILE_PATH = "src/data/shippingCost.csv";
    private static final String INVALID_FORMAT_FILE_PATH = "src/data/invalidFormat.csv";
    private static final String NONEXISTENT_FILE_PATH = "src/data/nonexistent.csv";

    private static final String NOT_NULL_MESSAGE = "The shipping costs list should not be null";
    private static final String EMPTY_MESSAGE = "The shipping costs list should be empty";

    // logger of impoter for deactivation
    private static final Logger logger = Logger.getLogger(Importer.class.getName());
    private final Level originalLogLevel = logger.getLevel();

    private final Importer importer = new Importer();

    @BeforeEach
    void setUp() throws IOException {
        try (FileWriter writer = new FileWriter(VALID_FILE_PATH)) {
            writer.write("3.89;4.39;5.89;7.99;14.99");
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
    void testImportShippingCosts() {

        List<Double> shippingCosts = importer.importShippingCosts(VALID_FILE_PATH);

        assertNotNull(shippingCosts, NOT_NULL_MESSAGE);
        assertEquals(5, shippingCosts.size(), "The shipping costs list should contain 5 elements");

        assertEquals(3.89, shippingCosts.get(0), 0.01);
        assertEquals(4.39, shippingCosts.get(1), 0.01);
        assertEquals(5.89, shippingCosts.get(2), 0.01);
        assertEquals(7.99, shippingCosts.get(3), 0.01);
        assertEquals(14.99, shippingCosts.get(4), 0.01);
    }

    @Test
    void testImportShippingCostsFileNotFound() {
        logger.setLevel(Level.OFF);

        List<Double> shippingCosts = importer.importShippingCosts(NONEXISTENT_FILE_PATH);

        assertNotNull(shippingCosts, NOT_NULL_MESSAGE);
        assertTrue(shippingCosts.isEmpty(), EMPTY_MESSAGE);

        logger.setLevel(originalLogLevel);
    }

    @Test
    void testImportShippingCostsInvalidFormat() {
        logger.setLevel(Level.OFF);

        List<Double> shippingCosts = importer.importShippingCosts(INVALID_FORMAT_FILE_PATH);

        assertNotNull(shippingCosts, NOT_NULL_MESSAGE);
        assertTrue(shippingCosts.isEmpty(), EMPTY_MESSAGE);

        logger.setLevel(originalLogLevel);
    }
}