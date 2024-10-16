package data;

/**
 * The Packet class represents a packet with dimensions and weight.
 */
public class Packet {

    /** The length of the packet in millimeters */
    public int length;

    /** width of package in millimeters */
    public int width;

    /** height of package in millimeters */
    public int height;

    /** weight of package in grams */
    public int weight;

    /**
     * Creates a new packet with the given dimensions and weight.
     *
     * @param length the length of the packet in millimeters
     * @param width the width of the packet in millimeters
     * @param height the height of the packet in millimeters
     * @param weight the weight of the packet in grams
     */
    public Packet(int length, int width, int height, int weight) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

}
