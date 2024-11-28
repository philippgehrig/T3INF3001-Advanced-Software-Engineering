package test;

import control.Calculator;
import control.NotValidDimensionsException;
import data.Importer;
import data.PackageConfiguration;
import data.Packet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    static Stream<Map.Entry<Packet, Double>> createValidPackages() {
        Importer importer = new Importer();
        List<PackageConfiguration> packageConfigurations = importer.importPackageConfigurations("src/data/packageConfigurations.csv");

        Map<Packet, Double> packages = new HashMap<>();
        packages.put(new Packet(100, 50, 30, 200), packageConfigurations.get(0).getPrice());
        packages.put(new Packet(200, 100, 60, 400), packageConfigurations.get(1).getPrice());
        packages.put(new Packet(300, 150, 90, 600), packageConfigurations.get(2).getPrice());
        packages.put(new Packet(400, 200, 120, 800), packageConfigurations.get(3).getPrice());
        packages.put(new Packet(500, 250, 150, 1000), packageConfigurations.get(4).getPrice());

        return packages.entrySet().stream();
    }

    static Stream<Packet> createOutOfBoundsPackages() {
        return Stream.of(
                new Packet(600, 300, 150, 2000),  // Exceeds length and girth limit
                new Packet(1200, 600, 600, 31000), // Exceeds weight limit
                new Packet(1201, 400, 400, 1000)   // Just over length limit
        );
    }

    @ParameterizedTest
    @MethodSource("createValidPackages")
    void testCalcShippingCostWithValidPackages(Map.Entry<Packet, Double> entry) {
        Packet packet = entry.getKey();
        double expectedCost = entry.getValue();
        try {
            double calcShippingCosts = calculator.calcShippingCosts(packet);
            assertEquals(expectedCost, calcShippingCosts);
        } catch (NotValidDimensionsException e) {
            fail("Exception thrown for valid package: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("createOutOfBoundsPackages")
    void testCalcShippingCostWithOutOfBoundsPackages(Packet packet) {
        assertThrows(NotValidDimensionsException.class, () -> calculator.calcShippingCosts(packet));
    }
}