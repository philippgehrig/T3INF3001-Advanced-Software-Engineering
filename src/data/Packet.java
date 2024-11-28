package data;

public class Packet {
    private int length;
    private int width;
    private int height;
    private int weight;
    int girth;

    public Packet(int length, int width, int height, int weight) {
        checkInputs(length, width, height, weight);
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.girth = calculateGirth(length, width, height);
    }

    public void checkInputs(int length, int width, int height, int weight) throws IllegalArgumentException {
        if (length <= 0 || width <= 0 || height <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Dimensions and weight must be positive values.");
        }
    }

    private int calculateGirth(int length, int width, int height) {
        return length + 2 * width + 2 * height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        checkInputs(length, this.width, this.height, this.weight);
        this.length = length;
        this.girth = calculateGirth(length, this.width, this.height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        checkInputs(this.length, width, this.height, this.weight);
        this.width = width;
        this.girth = calculateGirth(this.length, width, this.height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        checkInputs(this.length, this.width, height, this.weight);
        this.height = height;
        this.girth = calculateGirth(this.length, this.width, height);
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