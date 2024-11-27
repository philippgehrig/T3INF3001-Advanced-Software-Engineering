package control;

import data.Packet;

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
	public double calcShippingCosts(Packet pack) throws NotValidDimensionsException, IllegalArgumentException{

		// Check if the dimensions and weight are valid
		try {
			pack.checkInputs(pack.getLength(), pack.getWidth(), pack.getHeight(), pack.getWeight());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Dimensions and weight must be positive values.");
		}

		double shippingCosts;

		// Determine shipping costs based on sorted dimensions and weight
		if (pack.getLength() <= 300 && pack.getWidth() <= 300 && pack.getHeight() <= 150 && pack.getWeight() <= 1000) {
			shippingCosts = 3.89;
		} else if (pack.getLength() <= 600 && pack.getWidth() <= 300 && pack.getHeight() <= 150 && pack.getWeight() <= 2000) {
			shippingCosts = 4.39;
		} else if (pack.getLength() <= 1200 && pack.getWidth() <= 600 && pack.getHeight() <= 600
				&& pack.getGirth() <= 3000 && pack.getWeight() <= 5000) {
			shippingCosts = 5.89;
		} else if (pack.getLength() <= 1200 && pack.getWidth() <= 600 && pack.getHeight() <= 600
				&& pack.getGirth() <= 3000 && pack.getWeight() <= 10000) {
			shippingCosts = 7.99;
		} else if (pack.getLength() <= 1200 && pack.getWidth() <= 600 && pack.getHeight() <= 600 && pack.getWeight() <= 31000) {
			shippingCosts = 14.99;
		} else {
			throw new NotValidDimensionsException("Package not in valid dimensions");
		}

		return shippingCosts;
	}



}