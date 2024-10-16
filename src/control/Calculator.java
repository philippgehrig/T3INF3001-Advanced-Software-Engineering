package control;

import data.Packet;
import java.util.Arrays;

/**
 * The Calculator class provides a method to calculate shipping costs based on the dimensions and weight of a packet.
 */
public class Calculator {

	/**
	 * Calculates the shipping costs for a given packet
	 *
	 * @param pack the packet for which to calculate the shipping costs
	 * @return the calculated shipping costs
	 * @throws IllegalArgumentException if the shipping cost is invalid
	 * @throws NotValidDimensionsException if the package is not in valid dimensions
	 */
	public double calcShippingCosts(Packet pack) throws NotValidDimensionsException, IllegalArgumentException {
		// Validate inputs
		if (pack.length <= 0 || pack.width <= 0 || pack.height <= 0 || pack.weight <= 0) {
			throw new IllegalArgumentException("Dimensions and weight must be positive values.");
		}

		double shippingCosts;

		// Sort dimensions in descending order
		int[] dimensions = {pack.length, pack.width, pack.height};
		Arrays.sort(dimensions);
		int length = dimensions[2];
		int width = dimensions[1];
		int height = dimensions[0];

		// Calculate the girth
		int girth = length + 2 * width + 2 * height;

		// Determine shipping costs based on sorted dimensions and weight
		if (length <= 300 && width <= 300 && height <= 150 && pack.weight <= 1000) {
			shippingCosts = 3.89;
		} else if (length <= 600 && width <= 300 && height <= 150 && pack.weight <= 2000) {
			shippingCosts = 4.39;
		} else if (length <= 1200 && width <= 600 && height <= 600 && girth <= 3000 && pack.weight <= 5000) {
			shippingCosts = 5.89;
		} else if (length <= 1200 && width <= 600 && height <= 600 && girth <= 3000 && pack.weight <= 10000) {
			shippingCosts = 7.99;
		} else if (length <= 1200 && width <= 600 && height <= 600 && pack.weight <= 31000) {
			shippingCosts = 14.99;
		} else {
			throw new NotValidDimensionsException("Package not in valid dimensions");
		}

		return shippingCosts;
	}
}