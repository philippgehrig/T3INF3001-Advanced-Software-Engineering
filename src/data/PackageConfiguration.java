package data;

/**
 * The PackageConfiguration class contains a package with a price.
 * @see data.Packet
 */
public class PackageConfiguration extends Packet {
    private double price;

    /**
     * CONSTRUCTOR: Package Configuration
     * @param length of the package configuration
     * @param width of the package configuration
     * @param height of the package configuration
     * @param weight of the package configuration
     * @param girth of the package configuration
     * @param price of the package configuration
     */
    public PackageConfiguration(int length, int width, int height, int weight, int girth, double price) {
        super(length, width, height, weight);
        this.setGirth(girth);
        this.price = price;
    }

    /**
     * GETTER: Price of Configuration
     * @return price of the package configuration
     */
    public double getPrice() {
        return price;
    }

    /**
     * FUNCTION IS NOT USED - ONLY THERE FOR FUTURE DEVELOPMENTS
     * Set the price of the package configuration
     *
     * @param price price of the package configuration
     */
    public void setPrice(double price) {
        this.price = price;
    }


    /**
     * SETTER: Girth of Configuration
     * @param girth of the package configuration
     */
    public void setGirth(int girth) {
        this.girth = girth;
    }
}