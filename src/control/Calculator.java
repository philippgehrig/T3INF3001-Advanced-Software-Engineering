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
	public double calcShippingCosts(Packet pack) throws NotValidDimensionsException {
		// Validate inputs
		pack.checkInputs(pack.length, pack.width, pack.height, pack.weight);

		double shippingCosts;

		// Determine shipping costs based on sorted dimensions and weight
		if (pack.length <= 300 && pack.width <= 300 && pack.height <= 150 && pack.weight <= 1000) {
			shippingCosts = 3.89;
		} else if (pack.length <= 600 && pack.width <= 300 && pack.height <= 150 && pack.weight <= 2000) {
			shippingCosts = 4.39;
		} else if (pack.length <= 1200 && pack.width <= 600 && pack.height <= 600
				&& pack.girth <= 3000 && pack.weight <= 5000) {
			shippingCosts = 5.89;
		} else if (pack.length <= 1200 && pack.width <= 600 && pack.height <= 600
				&& pack.girth <= 3000 && pack.weight <= 10000) {
			shippingCosts = 7.99;
		} else if (pack.length <= 1200 && pack.width <= 600 && pack.height <= 600 && pack.weight <= 31000) {
			shippingCosts = 14.99;
		} else {
			throw new NotValidDimensionsException("Package not in valid dimensions");
		}

		return shippingCosts;
	}



}