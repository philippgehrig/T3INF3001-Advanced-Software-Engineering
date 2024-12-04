package test;

import data.PackageConfiguration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The PackageConfigurationTest class contains the unit tests for the PackageConfiguration class.
 */
class PackageConfigurationTest {

    /**
     * Test the constructor and getters of the PackageConfiguration class.
     */
    @Test
    void testConstructorAndGetters() {
        int length = 100;
        int width = 50;
        int height = 30;
        int weight = 200;
        int girth = 150;
        double price = 19.99;

        PackageConfiguration config = new PackageConfiguration(length, width, height, weight, girth, price);

        assertEquals(length, config.getLength());
        assertEquals(width, config.getWidth());
        assertEquals(height, config.getHeight());
        assertEquals(weight, config.getWeight());
        assertEquals(girth, config.getGirth());
        assertEquals(price, config.getPrice(), 0.01);
    }

    /**
     * Test the setters of the PackageConfiguration class.
     */
    @Test
    void testSetters() {
        PackageConfiguration config =
                new PackageConfiguration(100, 50, 30, 200, 150, 19.99);

        config.setLength(200);
        assertEquals(200, config.getLength());
        assertEquals(50, config.getWidth());
        assertEquals(30, config.getHeight());
        assertEquals(200, config.getWeight());
        assertEquals(360, config.getGirth());
        assertEquals(19.99, config.getPrice(), 0.01);

        config.setWidth(100);
        assertEquals(200, config.getLength());
        assertEquals(100, config.getWidth());
        assertEquals(30, config.getHeight());
        assertEquals(200, config.getWeight());
        assertEquals(460, config.getGirth());
        assertEquals(19.99, config.getPrice(), 0.01);

        config.setHeight(60);
        assertEquals(200, config.getLength());
        assertEquals(100, config.getWidth());
        assertEquals(60, config.getHeight());
        assertEquals(200, config.getWeight());
        assertEquals(520, config.getGirth());
        assertEquals(19.99, config.getPrice(), 0.01);

        config.setWeight(300);
        assertEquals(200, config.getLength());
        assertEquals(100, config.getWidth());
        assertEquals(60, config.getHeight());
        assertEquals(300, config.getWeight());
        assertEquals(520, config.getGirth());
        assertEquals(19.99, config.getPrice(), 0.01);

        config.setPrice(1000);
        assertEquals(200, config.getLength());
        assertEquals(100, config.getWidth());
        assertEquals(60, config.getHeight());
        assertEquals(300, config.getWeight());
        assertEquals(520, config.getGirth());
        assertEquals(1000.0, config.getPrice(), 0.01);
    }
}