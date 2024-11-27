package control;

import data.Importer;
import data.Packet;

import java.util.List;

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

		List<Double> shippingCosts = new Importer().importShippingCosts("src/data/shippingCosts.csv");

		// Determine shipping costs based on sorted dimensions and weight
		if (pack.getLength() <= 300 && pack.getWidth() <= 300 && pack.getHeight() <= 150 && pack.getWeight() <= 1000) {
			return shippingCosts.get(0);
		} else if (pack.getLength() <= 600 && pack.getWidth() <= 300 && pack.getHeight() <= 150
				&& pack.getWeight() <= 2000) {
			return shippingCosts.get(1);
		} else if (pack.getLength() <= 1200 && pack.getWidth() <= 600 && pack.getHeight() <= 600
				&& pack.getGirth() <= 3000 && pack.getWeight() <= 5000) {
			return shippingCosts.get(2);
		} else if (pack.getLength() <= 1200 && pack.getWidth() <= 600 && pack.getHeight() <= 600
				&& pack.getGirth() <= 3000 && pack.getWeight() <= 10000) {
			return shippingCosts.get(3);
		} else if (pack.getLength() <= 1200 && pack.getWidth() <= 600 && pack.getHeight() <= 600
				&& pack.getWeight() <= 31000) {
			return shippingCosts.get(4);
		} else {
			throw new NotValidDimensionsException("Package not in valid dimensions");
		}
	}



}