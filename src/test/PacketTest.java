package test;

import data.Packet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PacketTest {

    @Test
    void testPacketGetters() {
        Packet packet = new Packet(300, 200, 100, 500);

        assertEquals(300, packet.getLength());
        assertEquals(200, packet.getWidth());
        assertEquals(100, packet.getHeight());
        assertEquals(500, packet.getWeight());
        assertEquals(900, packet.getGirth());
    }

    @Test
    void testPacketSetters() {
        Packet packet = new Packet(300, 200, 100, 500);

        packet.setLength(400);
        assertEquals(400, packet.getLength());
        assertEquals(200, packet.getWidth());
        assertEquals(100, packet.getHeight());
        assertEquals(500, packet.getWeight());
        assertEquals(1000, packet.getGirth()); // 1*L + 2*W

        packet.setWidth(300);
        assertEquals(400, packet.getLength());
        assertEquals(300, packet.getWidth());
        assertEquals(100, packet.getHeight());
        assertEquals(500, packet.getWeight());
        assertEquals(1200, packet.getGirth());

        packet.setHeight(200);
        assertEquals(400, packet.getLength());
        assertEquals(300, packet.getWidth());
        assertEquals(200, packet.getHeight());
        assertEquals(500, packet.getWeight());
        assertEquals(1400, packet.getGirth());

        packet.setWeight(1000);
        assertEquals(400, packet.getLength());
        assertEquals(300, packet.getWidth());
        assertEquals(200, packet.getHeight());
        assertEquals(1000, packet.getWeight());
        assertEquals(1400, packet.getGirth());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -1, -1, -1})
    void testNegativeDimensions(int value) {
        assertThrows(IllegalArgumentException.class, () ->
                new Packet(value, 200, 100, 500));
        assertThrows(IllegalArgumentException.class, () ->
                new Packet(300, value, 100, 500));
        assertThrows(IllegalArgumentException.class, () ->
                new Packet(300, 200, value, 500));
        assertThrows(IllegalArgumentException.class, () ->
                new Packet(300, 200, 100, value));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 0, 0, 0})
    void testZeroDimensions(int value) {
        assertThrows(IllegalArgumentException.class, () ->
                new Packet(value, 200, 100, 500));
        assertThrows(IllegalArgumentException.class, () ->
                new Packet(300, value, 100, 500));
        assertThrows(IllegalArgumentException.class, () ->
                new Packet(300, 200, value, 500));
        assertThrows(IllegalArgumentException.class, () ->
                new Packet(300, 200, 100, value));
    }

    @Test
    void testValidGirthCalculation() {
        // girth = 1 * L + 2 * W + 2 * H with ordered dimensions => 1 * 300 + 2 * 200 + 2 * 100 = 900

        Packet packet = new Packet(300, 200, 100, 500);
        assertEquals(900, packet.getGirth());
    }
}