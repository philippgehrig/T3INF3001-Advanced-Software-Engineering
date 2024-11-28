package data;

public class PackageConfiguration extends Packet {
    private double price;

    public PackageConfiguration(int length, int width, int height, int weight, int girth, double price) {
        super(length, width, height, weight);
        this.setGirth(girth);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setGirth(int girth) {
        this.girth = girth;
    }
}