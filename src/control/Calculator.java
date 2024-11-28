package control;

import data.Importer;
import data.PackageConfiguration;
import data.Packet;

import java.util.Comparator;
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
	public double calcShippingCosts(Packet pack) throws NotValidDimensionsException, IllegalArgumentException {

		// Check if the dimensions and weight are valid
		try {
			pack.checkInputs(pack.getLength(), pack.getWidth(), pack.getHeight(), pack.getWeight());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Dimensions and weight must be positive values.");
		}

		// Import and sort package configurations by price
		List<PackageConfiguration> packageConfigurations = new Importer().importPackageConfigurations("src/data/shippingCosts.csv");
		packageConfigurations.sort(Comparator.comparingDouble(PackageConfiguration::getPrice));

		// Determine shipping costs based on sorted package configurations
		for (PackageConfiguration config : packageConfigurations) {
			if (pack.getLength() <= config.getLength() && pack.getWidth() <= config.getWidth() &&
					pack.getHeight() <= config.getHeight() && pack.getWeight() <= config.getWeight() &&
					pack.getGirth() <= config.getGirth()) {
				return config.getPrice();
			}
		}

		throw new NotValidDimensionsException("Package not in valid dimensions");
	}
}