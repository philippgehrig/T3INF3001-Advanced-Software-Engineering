package control;

import data.Importer;
import data.PackageConfiguration;
import data.Packet;

import java.util.Comparator;
import java.util.List;

public class Calculator {

	/**
	 * Calculates the shipping Costs of a package
	 * @param pack to be shipped
	 * @param filePath to the package configurations
	 * @return price of the package
	 * @throws NotValidDimensionsException for invalid dimensions
	 * @throws IllegalArgumentException for illegal arguments
	 */
	public double calcShippingCosts(Packet pack, String filePath)
			throws NotValidDimensionsException, IllegalArgumentException {

		// Check if the dimensions and weight are valid
		try {
			pack.checkInputs(pack.getLength(), pack.getWidth(), pack.getHeight(), pack.getWeight());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Dimensions and weight must be positive values.");
		}

		// Import and sort package configurations by price
		List<PackageConfiguration> packageConfigurations = new Importer().importPackageConfigurations(filePath);
		packageConfigurations.sort(Comparator.comparingDouble(PackageConfiguration::getPrice));

		// Determine shipping costs based on sorted package configurations

		// iterate through all package configurations and check if the package fits
		for (PackageConfiguration config : packageConfigurations) {
			//checks if all parameters (except girth) are below the configuration
			boolean isValid = pack.getLength() <= config.getLength() &&
					pack.getWidth() <= config.getWidth() &&
					pack.getHeight() <= config.getHeight() &&
					pack.getWeight() <= config.getWeight();

			// if the girth is set to 0, the girth is not considered
			// otherwise, the girth must be below the configuration
			if (config.getGirth() > 0) {
				isValid = isValid && pack.getGirth() <= config.getGirth();
			}

			// if the package fits, return the price
			if (isValid) {
				return config.getPrice();
			}
		}

		// if the package does not fit, throw an NotValidDimensionsException
		throw new NotValidDimensionsException("Package not in valid dimensions");
	}
}