package test;

import data.Packet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PacketTest {

    @Test
    void testValidPacket() {
        Packet packet = new Packet(300, 200, 100, 500);
        assertEquals(300, packet.length);
        assertEquals(200, packet.width);
        assertEquals(100, packet.height);
        assertEquals(500, packet.weight);
        assertEquals(900, packet.girth);
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
        assertEquals(900, packet.girth);
    }
}