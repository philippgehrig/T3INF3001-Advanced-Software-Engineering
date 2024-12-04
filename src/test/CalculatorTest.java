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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The CalculatorTest class contains tests for the Calculator class.
 * @see control.Calculator
 */
class CalculatorTest {

    private Calculator calculator;
    private static final String CSV_FILE_PATH_WITHOUT_GIRTH = "src/test/testPackageConfigurations.csv";
    private static final String CSV_FILE_PATH_WITH_GIRTH = "src/test/testPackageConfigurationsWithGirth.csv";
    private static final String UNEQUAL_ERROR_MESSAGE = "The calculated price should match the expected price";

    /**
     * Write test CSV files for package configurations.
     * @throws IOException if an I/O error occurs while writing the csv file
     */
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

    /**
     * Set up a new Calculator object before each test.
     */
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    /**
     * Remove the test CSV files after all tests have been executed
     * @throws IOException if an I/O error occurs while deleting the csv file
     */
    @AfterAll
    static void removeCsvFile() throws IOException {
        Files.deleteIfExists(Paths.get(CSV_FILE_PATH_WITHOUT_GIRTH));
        Files.deleteIfExists(Paths.get(CSV_FILE_PATH_WITH_GIRTH));
    }

    /**
     * Create a stream of valid packages without girth.
     * @return a stream of valid packages without girth.
     */
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

    /**
     * Create a stream of valid packages with girth.
     * @return a stream of valid packages with girth.
     */
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

    /**
     * Create a stream of out of bounds packages.
     * @return a stream of out of bounds packages.
     */
    static Stream<Packet> createOutOfBoundsPackages() {
        return Stream.of(
                new Packet(601, 1, 1, 1),  // Exceeds length limit
                new Packet(500, 301, 300, 1), // Exceeds width limit
                new Packet(500, 301, 301, 1),  // Exceeds width & height limit
                new Packet(1, 1, 1, 1001)    // Exceeds weight limit
        );
    }

    /**
     * Create a stream of 1000 random packages.
     * @return a stream of 1000 random packages.
     */
    static Stream<Map.Entry<Packet, Double>> createRandomPackages() {
        Map<Packet, Double> packages = new HashMap<>();

        int counter = 0;
        while(counter < 1000) {
            int length = (int) (Math.random() * 600) + 1;
            int width = (int) (Math.random() * 300) + 1;
            int height = (int) (Math.random() * 300) + 1;
            int weight = (int) (Math.random() * 1000) + 1;
            Packet packet = new Packet(length, width, height, weight);

            try {
                double price = new CalculatorTest().calcShippingCosts(packet);
                packages.put(packet, price);
                counter += 1;
            } catch (NotValidDimensionsException | IllegalArgumentException e) {
                // Ignore invalid packages
            }
        }

        return packages.entrySet().stream();
    }

    /**
     * Function used with random test cases to validate the calculation function of actual Calculator class
     * Calculate the shipping costs for a given package.
     * @param pack the package for which to calculate the shipping costs
     * @return the shipping costs for the package
     * @throws NotValidDimensionsException if the package dimensions are not valid
     * @throws IllegalArgumentException if the package dimensions are negative
     */
    public double calcShippingCosts(Packet pack) throws NotValidDimensionsException, IllegalArgumentException {
        // Validate inputs
        checkInputs(pack.getLength(), pack.getWidth(), pack.getHeight(), pack.getWeight());

        double shippingCosts;

        // Sort dimensions in descending order
        int[] dimensions = {pack.getLength(), pack.getWidth(), pack.getHeight()};
        Arrays.sort(dimensions);
        int length = dimensions[2];
        int width = dimensions[1];
        int height = dimensions[0];

        // Calculate the girth
        int girth = length + 2 * width + 2 * height;

        // Determine shipping costs based on sorted dimensions and weight
        if (length <= 100 && width <= 50 && height <= 50 && pack.getWeight() <= 200 && girth <= 150) {
            shippingCosts = 19.99;
        } else if (length <= 200 && width <= 100 && height <= 100 && pack.getWeight() <= 400 && girth <= 300) {
            shippingCosts = 29.99;
        } else if (length <= 300 && width <= 150 && height <= 150 && pack.getWeight() <= 600 && girth <= 450) {
            shippingCosts = 39.99;
        } else if (length <= 400 && width <= 200 && height <= 200 && pack.getWeight() <= 800 && girth <= 600) {
            shippingCosts = 49.99;
        } else if (length <= 500 && width <= 300 && height <= 300 && pack.getWeight() <= 1000 && girth <= 750) {
            shippingCosts = 59.99;
        } else {
            throw new NotValidDimensionsException("Package not in valid dimensions");
        }

        return shippingCosts;
    }

    /**
     * Check the inputs of a package for valid inputs.
     * @param length the length of the package
     * @param width the width of the package
     * @param height the height of the package
     * @param weight the weight of the package
     * @throws NotValidDimensionsException if the package dimensions are not valid
     * @throws IllegalArgumentException if the package dimensions are negative
     */
    public void checkInputs(int length, int width, int height, int weight)
            throws NotValidDimensionsException, IllegalArgumentException {
        if (length <= 0 || width <= 0 || height <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Dimensions and weight must be positive values.");
        }

        int[] dimensions = {length, width, height};
        Arrays.sort(dimensions);

        length = dimensions[2];
        width = dimensions[1];
        height = dimensions[0];

        if (length > 500 || width > 300 || height > 300 || weight > 1000) {
            throw new NotValidDimensionsException("Package not in valid dimensions");
        }
    }

    /**
     * Test the calculation of shipping costs for valid packages without girth.
     * @param entry Map of Packages and Expected Prices
     */
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

    /**
     * Test the calculation of shipping costs for valid packages with girth.
     * @param entry Map of Packages and Expected Prices
     */
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

    /**
     * Test the calculation of shipping costs for out of bounds packages.
     * @param packet the out-of-bounds package
     */
    @ParameterizedTest
    @MethodSource("createOutOfBoundsPackages")
    void testCalcShippingCostWithOutOfBoundsPackages(Packet packet) {
        assertThrows(NotValidDimensionsException.class, () ->
                calculator.calcShippingCosts(packet, CSV_FILE_PATH_WITHOUT_GIRTH));
    }

    /**
     * Test the calculation of shipping costs for 1000 random packages.
     * Compare the calculated price from the Calculator class with the price calculated in the test function.
     * @param entry Map of Packages and Expected Prices
     * @see control.Calculator
     */
    @ParameterizedTest
    @MethodSource("createRandomPackages")
    void testRandomPackages(Map.Entry<Packet, Double> entry) {
        Packet packet = entry.getKey();
        double expectedPrice = entry.getValue();
        try {
            double actualPrice = calculator.calcShippingCosts(packet, CSV_FILE_PATH_WITH_GIRTH);
            assertEquals(expectedPrice, actualPrice, 0.01, UNEQUAL_ERROR_MESSAGE);
        } catch (NotValidDimensionsException e) {
            fail("Exception thrown for valid package: " + e.getMessage());
        }
    }
}