package control;

import data.Importer;
import data.PackageConfiguration;
import data.Packet;

import java.util.Comparator;
import java.util.List;

public class Calculator {

	public double calcShippingCosts(Packet pack, String filePath) throws NotValidDimensionsException, IllegalArgumentException {

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
		for (PackageConfiguration config : packageConfigurations) {
			boolean isValid = pack.getLength() <= config.getLength() &&
					pack.getWidth() <= config.getWidth() &&
					pack.getHeight() <= config.getHeight() &&
					pack.getWeight() <= config.getWeight();

			if (config.getGirth() > 0) {
				isValid = isValid && pack.getGirth() <= config.getGirth();
			}

			if (isValid) {
				return config.getPrice();
			}
		}

		throw new NotValidDimensionsException("Package not in valid dimensions");
	}
}