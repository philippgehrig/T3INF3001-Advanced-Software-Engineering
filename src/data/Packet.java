package data;

import java.util.Arrays;

/**
 * The Packet class represents a packet with dimensions and weight.
 */
public class Packet {

    /** The length of the packet in millimeters */
    private int length;

    /** The width of the packet in millimeters */
    private int width;

    /** The height of the packet in millimeters */
    private int height;

    /** The weight of the packet in grams */
    private int weight;

    /** The girth of the packet in millimeters */
    private int girth;

    /**
     * Creates a new packet with the given dimensions and weight.
     *
     * @param length the length of the packet in millimeters
     * @param width the width of the packet in millimeters
     * @param height the height of the packet in millimeters
     * @param weight the weight of the packet in grams
     */
    public Packet(int length, int width, int height, int weight) {
        checkInputs(length, width, height, weight); // Check if the dimensions and weight are valid

        int[] orderedDimensions = orderDimensions(length, width, height);
        this.length = orderedDimensions[2];
        this.width = orderedDimensions[1];
        this.height = orderedDimensions[0];
        this.weight = weight;
        this.girth = calculateGirth(this.length, this.width, this.height);
    }

    /**
     * Checks if the dimensions and weight of a package are valid.
     *
     * @param length the length of the packet in millimeters
     * @param width the width of the packet in millimeters
     * @param height the height of the packet in millimeters
     * @param weight the weight of the packet in grams
     * @throws IllegalArgumentException for negative values
     */
    public void checkInputs(int length, int width, int height, int weight) throws IllegalArgumentException {
        if (length <= 0 || width <= 0 || height <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Dimensions and weight must be positive values.");
        }
    }

    /**
     * Orders the dimensions of a package in descending order.
     *
     * @param length the length of the packet in millimeters
     * @param width the width of the packet in millimeters
     * @param height the height of the packet in millimeters
     * @return the ordered dimensions
     */
    private int[] orderDimensions(int length, int width, int height) {
        int[] dimensions = {length, width, height};
        Arrays.sort(dimensions);
        return dimensions;
    }

    /**
     * Calculates the girth of the package.
     *
     * @param length the length of the packet in millimeters
     * @param width the width of the packet in millimeters
     * @param height the height of the packet in millimeters
     * @return the calculated girth
     */
    private int calculateGirth(int length, int width, int height) {
        return length + 2 * width + 2 * height;
    }

    // Getters and Setters

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        checkInputs(length, this.width, this.height, this.weight);
        int[] orderedDimensions = orderDimensions(length, this.width, this.height);
        this.length = orderedDimensions[2];
        this.width = orderedDimensions[1];
        this.height = orderedDimensions[0];
        this.girth = calculateGirth(this.length, this.width, this.height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        checkInputs(this.length, width, this.height, this.weight);
        int[] orderedDimensions = orderDimensions(this.length, width, this.height);
        this.length = orderedDimensions[2];
        this.width = orderedDimensions[1];
        this.height = orderedDimensions[0];
        this.girth = calculateGirth(this.length, this.width, this.height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        checkInputs(this.length, this.width, height, this.weight);
        int[] orderedDimensions = orderDimensions(this.length, this.width, height);
        this.length = orderedDimensions[2];
        this.width = orderedDimensions[1];
        this.height = orderedDimensions[0];
        this.girth = calculateGirth(this.length, this.width, this.height);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        checkInputs(this.length, this.width, this.height, weight);
        this.weight = weight;
    }

    public int getGirth() {
        return girth;
    }
}