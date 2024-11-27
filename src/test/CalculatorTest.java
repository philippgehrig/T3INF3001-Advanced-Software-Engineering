package test;

import control.Calculator;
import control.NotValidDimensionsException;
import data.Importer;
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
        List<Double> shippingCosts = importer.importShippingCosts("src/data/shippingCost.csv");

        Map<Packet, Double> packages = new HashMap<>();
        packages.put(new Packet(300, 300, 150, 1000), shippingCosts.get(0));
        packages.put(new Packet(600, 300, 150, 2000), shippingCosts.get(1));
        packages.put(new Packet(1200, 400, 400, 5000), shippingCosts.get(2));
        packages.put(new Packet(1200, 400, 400, 10000), shippingCosts.get(3));
        packages.put(new Packet(1200, 600, 600, 31000), shippingCosts.get(4));

        return packages.entrySet().stream();
    }

    static Stream<Packet> createOutOfBoundsPackages() {
        return Stream.of(
                new Packet(1300, 600, 600, 1000),  // Exceeds length and girth limit
                new Packet(1200, 600, 600, 32000), // Exceeds weight limit
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