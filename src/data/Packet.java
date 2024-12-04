package data;

public class Packet {
    private int length;
    private int width;
    private int height;
    private int weight;
    int girth;

    /**
     * CONSTRUCTOR: Packet
     * @param length length of the packet
     * @param width width of the packet
     * @param height height of the packet
     * @param weight weight of the packet
     */
    public Packet(int length, int width, int height, int weight) {
        checkInputs(length, width, height, weight);

        int[] dimensions = {length, width, height};
        java.util.Arrays.sort(dimensions);
        this.length = dimensions[2];
        this.width = dimensions[1];
        this.height = dimensions[0];
        this.weight = weight;
        this.girth = calculateGirth(length, width, height); // girth is calculated
    }

    /**
     * FUNCTION: Check Inputs of Packet for valid inputs
     * Negative values are not allowed
     * @param length length of the packet
     * @param width width of the packet
     * @param height height of the packet
     * @param weight weight of the packet
     * @throws IllegalArgumentException if any of the inputs are negative
     */
    public void checkInputs(int length, int width, int height, int weight) throws IllegalArgumentException {
        if (length <= 0 || width <= 0 || height <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Dimensions and weight must be positive values.");
        }
    }

    /**
     * FUNCTION: Calculate Girth of Packet
     * Since the package can be turned the parameters need to be orderd to achieve minimal girth
     * Girth is calculated by the formula: length + 2 * width + 2 * height
     * @param length length of the packet
     * @param width width of the packet
     * @param height height of the packet
     * @return girth of the packet
     */
    private int calculateGirth(int length, int width, int height) {
        int[] dimensions = {length, width, height};
        java.util.Arrays.sort(dimensions);
        length = dimensions[2];
        width = dimensions[1];
        height = dimensions[0];
        return length + 2 * width + 2 * height;
    }

    /**
     * GETTER: Length of Packet
     * @return length of the packet
     */
    public int getLength() {
        return length;
    }

    /**
     * SETTER: Length of Packet
     * @param length length of the packet
     */
    public void setLength(int length) {
        checkInputs(length, this.width, this.height, this.weight);
        this.length = length;
        this.girth = calculateGirth(length, this.width, this.height); // girth needs to be recalculated
    }

    /**
     * GETTER: Width of Packet
     * @return width of the packet
     */
    public int getWidth() {
        return width;
    }

    /**
     * SETTER: Width of Packet
     * @param width width of the packet
     */
    public void setWidth(int width) {
        checkInputs(this.length, width, this.height, this.weight);
        this.width = width;
        this.girth = calculateGirth(this.length, width, this.height); // girth needs to be recalculated
    }

    /**
     * GETTER: Height of Packet
     * @return height of the packet
     */
    public int getHeight() {
        return height;
    }

    /**
     * SETTER: Height of Packet
     * @param height height of the packet
     */
    public void setHeight(int height) {
        checkInputs(this.length, this.width, height, this.weight);
        this.height = height;
        this.girth = calculateGirth(this.length, this.width, height); // girth needs to be recalculated
    }

    /**
     * GETTER: Weight of Packet
     * @return weight of the packet
     */
    public int getWeight() {
        return weight;
    }

    /**
     * SETTER: Weight of Packet
     * @param weight weight of the packet
     */
    public void setWeight(int weight) {
        checkInputs(this.length, this.width, this.height, weight);
        this.weight = weight;
    }

    /**
     * GETTER: Girth of Packet
     * @return girth of the packet
     */
    public int getGirth() {
        return girth;
    }

    // girth does not need a setter because it is a calculated value
}