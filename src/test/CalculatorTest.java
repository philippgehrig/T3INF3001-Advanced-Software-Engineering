package test;

import control.Calculator;
import control.NotValidDimensionsException;
import data.Packet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
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
        Map<Packet, Double> packages = new HashMap<>();
        packages.put(new Packet(300, 300, 150, 1000), 3.89);
        packages.put(new Packet(600, 300, 150, 2000), 4.39);
        packages.put(new Packet(1200, 400, 400, 5000), 5.89);
        packages.put(new Packet(1200, 400, 400, 10000), 7.99);
        packages.put(new Packet(1200, 600, 600, 31000), 14.99);

        return packages.entrySet().stream();
    }

    static Stream<Packet> createInvalidPackages() {
        return Stream.of(
                new Packet(-300, 300, 150, 1000),
                new Packet(300, -300, 150, 1000),
                new Packet(300, 300, -150, 1000),
                new Packet(300, 300, 150, -1000),
                new Packet(-300, -300, -150, -1000),
                new Packet(0, 300, 150, 1000),
                new Packet(300, 0, 150, 1000),
                new Packet(300, 300, 0, 1000),
                new Packet(300, 300, 150, 0),
                new Packet(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE)
        );
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
    @MethodSource("createInvalidPackages")
    void testCalcShippingCostWithInvalidDimensions(Packet packet) {
        assertThrows(IllegalArgumentException.class, () -> calculator.calcShippingCosts(packet));
    }

    @ParameterizedTest
    @MethodSource("createOutOfBoundsPackages")
    void testCalcShippingCostWithOutOfBoundsPackages(Packet packet) {
        assertThrows(NotValidDimensionsException.class, () -> calculator.calcShippingCosts(packet));
    }
}