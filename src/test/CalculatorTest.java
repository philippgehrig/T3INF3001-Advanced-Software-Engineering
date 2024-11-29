package test;

import control.Calculator;
import control.NotValidDimensionsException;
import data.Importer;
import data.PackageConfiguration;
import data.Packet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;
    private static final String CSV_FILE_PATH_WITHOUT_GIRTH = "src/test/testPackageConfigurations.csv";
    private static final String CSV_FILE_PATH_WITH_GIRTH = "src/test/testPackageConfigurationsWithGirth.csv";
    private static final String UNEQUAL_ERROR_MESSAGE = "The calculated price should match the expected price";

    @BeforeAll
    static void writeCsvFile() throws IOException {
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH_WITHOUT_GIRTH)) {
            writer.write("Length;Height;Width;Weight;Girth;Price\n");
            writer.write("100;50;50;200;;19.99\n");
            writer.write("200;100;100;400;;29.99\n");
            writer.write("300;150;150;600;;39.99\n");
            writer.write("400;200;200;800;;49.99\n");
            writer.write("500;300;300;1000;;59.99\n");
        }
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH_WITH_GIRTH)) {
            writer.write("Length;Height;Width;Weight;Girth;Price\n");
            writer.write("100;50;50;200;150;19.99\n");
            writer.write("200;100;100;400;300;29.99\n");
            writer.write("300;150;150;600;450;39.99\n");
            writer.write("400;200;200;800;600;49.99\n");
            writer.write("500;300;300;1000;750;59.99\n");
        }
    }

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @AfterAll
    static void removeCsvFile() throws IOException {
        Files.deleteIfExists(Paths.get(CSV_FILE_PATH_WITHOUT_GIRTH));
        Files.deleteIfExists(Paths.get(CSV_FILE_PATH_WITH_GIRTH));
    }

    static Stream<Map.Entry<Packet, Double>> createValidPackagesWithoutGirth() {
        Importer importer = new Importer();
        List<PackageConfiguration> packageConfigurations =
                importer.importPackageConfigurations(CSV_FILE_PATH_WITHOUT_GIRTH);

        Map<Packet, Double> packages = new HashMap<>();

        if (packageConfigurations.size() >= 5) {

            // length test cases

            packages.put(new Packet(100, 1, 1, 200),
                    packageConfigurations.get(0).getPrice());
            packages.put(new Packet(200, 1, 1, 400),
                    packageConfigurations.get(1).getPrice());
            packages.put(new Packet(300, 1, 1, 600),
                    packageConfigurations.get(2).getPrice());
            packages.put(new Packet(400, 1, 1, 800),
                    packageConfigurations.get(3).getPrice());
            packages.put(new Packet(500, 1, 1, 1000),
                    packageConfigurations.get(4).getPrice());

            // width test cases

            packages.put(new Packet(51, 50, 1, 200),
                    packageConfigurations.get(0).getPrice());
            packages.put(new Packet(101, 100, 1, 400),
                    packageConfigurations.get(1).getPrice());
            packages.put(new Packet(151, 150, 1, 600),
                    packageConfigurations.get(2).getPrice());
            packages.put(new Packet(201, 200, 1, 800),
                    packageConfigurations.get(3).getPrice());
            packages.put(new Packet(301, 300, 1, 1000),
                    packageConfigurations.get(4).getPrice());

            // height % width test cases

            packages.put(new Packet(51, 50, 50, 200),
                    packageConfigurations.get(0).getPrice());
            packages.put(new Packet(101, 100, 100, 400),
                    packageConfigurations.get(1).getPrice());
            packages.put(new Packet(151, 150, 150, 600),
                    packageConfigurations.get(2).getPrice());
            packages.put(new Packet(201, 200, 200, 800),
                    packageConfigurations.get(3).getPrice());
            packages.put(new Packet(301, 300, 300, 1000),
                    packageConfigurations.get(4).getPrice());

            // weight test cases

            packages.put(new Packet(1, 1, 1, 200),
                    packageConfigurations.get(0).getPrice());
            packages.put(new Packet(1, 1, 1, 400),
                    packageConfigurations.get(1).getPrice());
            packages.put(new Packet(1, 1, 1, 600),
                    packageConfigurations.get(2).getPrice());
            packages.put(new Packet(1, 1, 1, 800),
                    packageConfigurations.get(3).getPrice());
            packages.put(new Packet(1, 1, 1, 1000),
                    packageConfigurations.get(4).getPrice());

        }

        return packages.entrySet().stream();
    }

    static Stream<Map.Entry<Packet, Double>> createValidPackagesWithGirth(){

        Importer importer = new Importer();
        List<PackageConfiguration> packageConfigurations =
                importer.importPackageConfigurations(CSV_FILE_PATH_WITH_GIRTH);

        Map<Packet, Double> packages = new HashMap<>();

        if (packageConfigurations.size() >= 5) {
            packages.put(new Packet(100, 1, 1, 200),
                    packageConfigurations.get(0).getPrice()); // girth = 104
            packages.put(new Packet(100, 13, 12, 200),
                    packageConfigurations.get(0).getPrice()); // girth = 150

            packages.put(new Packet(200, 1, 1, 400),
                    packageConfigurations.get(1).getPrice()); // girth = 204
            packages.put(new Packet(200, 25, 25, 400),
                    packageConfigurations.get(1).getPrice()); // girth = 300

            packages.put(new Packet(300, 1, 1, 600),
                    packageConfigurations.get(2).getPrice()); // girth = 304
            packages.put(new Packet(300, 37, 38, 600),
                    packageConfigurations.get(2).getPrice()); // girth = 450

            packages.put(new Packet(400, 1, 1, 800),
                    packageConfigurations.get(3).getPrice()); // girth = 404
            packages.put(new Packet(400, 50, 50, 800),
                    packageConfigurations.get(3).getPrice()); // girth = 600

            packages.put(new Packet(500, 1, 1, 1000),
                    packageConfigurations.get(4).getPrice()); // girth = 504
            packages.put(new Packet(500, 62, 63, 1000),
                    packageConfigurations.get(4).getPrice()); // girth = 750
        }
        return packages.entrySet().stream();
    }

    static Stream<Packet> createOutOfBoundsPackages() {
        return Stream.of(
                new Packet(601, 1, 1, 1),  // Exceeds length limit
                new Packet(500, 301, 300, 1), // Exceeds width limit
                new Packet(500, 301, 301, 1),  // Exceeds width & height limit
                new Packet(1, 1, 1, 1001)    // Exceeds weight limit
        );
    }

    @ParameterizedTest
    @MethodSource("createValidPackagesWithoutGirth")
    void testValidPackagesForPriceWithoutGirth(Map.Entry<Packet, Double> entry) {
        Packet packet = entry.getKey();
        double expectedPrice = entry.getValue();
        try {
            double actualPrice = calculator.calcShippingCosts(packet, CSV_FILE_PATH_WITHOUT_GIRTH);
            assertEquals(expectedPrice, actualPrice, 0.01, UNEQUAL_ERROR_MESSAGE);
        } catch (NotValidDimensionsException e) {
            fail("Exception thrown for valid package: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("createValidPackagesWithGirth")
    void testValidPackagesForPriceWithGirth(Map.Entry<Packet, Double> entry) {
        Packet packet = entry.getKey();
        double expectedPrice = entry.getValue();
        try {
            double actualPrice = calculator.calcShippingCosts(packet, CSV_FILE_PATH_WITH_GIRTH);
            assertEquals(expectedPrice, actualPrice, 0.01, UNEQUAL_ERROR_MESSAGE);
        } catch (NotValidDimensionsException e) {
            fail("Exception thrown for valid package: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("createOutOfBoundsPackages")
    void testCalcShippingCostWithOutOfBoundsPackages(Packet packet) {
        assertThrows(NotValidDimensionsException.class, () ->
                calculator.calcShippingCosts(packet, CSV_FILE_PATH_WITHOUT_GIRTH));
    }

}