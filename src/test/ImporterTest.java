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

import static org.junit.jupiter.api.Assertions.*;

class ImporterTest {

    private static final String FILE_PATH = "src/test/shippingCosts.csv";

    @BeforeEach
    void setUp() throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write("3.89;4.39;5.89;7.99;14.99");
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }

    @Test
    void testImportShippingCosts() {
        Importer importer = new Importer();
        List<Double> shippingCosts = importer.importShippingCosts(FILE_PATH);

        assertNotNull(shippingCosts, "The shipping costs list should not be null");
        assertEquals(5, shippingCosts.size(), "The shipping costs list should contain 5 elements");

        assertEquals(3.89, shippingCosts.get(0), 0.01);
        assertEquals(4.39, shippingCosts.get(1), 0.01);
        assertEquals(5.89, shippingCosts.get(2), 0.01);
        assertEquals(7.99, shippingCosts.get(3), 0.01);
        assertEquals(14.99, shippingCosts.get(4), 0.01);
    }

    @Test
    void testImportShippingCostsFileNotFound() {
        Importer importer = new Importer();
        List<Double> shippingCosts = importer.importShippingCosts("src/data/nonexistent.csv");

        assertNotNull(shippingCosts, "The shipping costs list should not be null even if the file is not found");
        assertTrue(shippingCosts.isEmpty(), "The shipping costs list should be empty if the file is not found");
    }

    @Test
    void testImportShippingCostsInvalidFormat() {
        Importer importer = new Importer();
        List<Double> shippingCosts = importer.importShippingCosts("src/data/invalidFormat.csv");

        assertNotNull(shippingCosts, "The shipping costs list should not be null even if the file format is invalid");
        assertTrue(shippingCosts.isEmpty(), "The shipping costs list should be empty if the file format is invalid");
    }
}