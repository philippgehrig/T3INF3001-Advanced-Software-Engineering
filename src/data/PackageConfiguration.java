package data;

public class PackageConfiguration {
    private int length;
    private int height;
    private int width;
    private int weight;
    private int girth;
    private double price;

    public PackageConfiguration(int length, int height, int width, int weight, int girth, double price) {
        this.length = length;
        this.height = height;
        this.width = width;
        this.weight = weight;
        this.girth = girth;
        this.price = price;
    }

    // Getters and setters
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGirth() {
        return girth;
    }

    public void setGirth(int girth) {
        this.girth = girth;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}