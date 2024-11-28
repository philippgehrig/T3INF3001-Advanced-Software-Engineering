package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Importer {
    private static final Logger logger = Logger.getLogger(Importer.class.getName());

    public List<PackageConfiguration> importPackageConfigurations(String filePath) {
        List<PackageConfiguration> packageConfigurations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line of the CSV file
                }
                String[] values = line.split(";");
                if (values.length >= 5) {
                    try {
                        int length = Integer.parseInt(values[0]);
                        int height = Integer.parseInt(values[1]);
                        int width = Integer.parseInt(values[2]);
                        int weight = Integer.parseInt(values[3]);
                        int girth = values[4].isEmpty() ? 0 : Integer.parseInt(values[4]); // Handle empty girth
                        double price = Double.parseDouble(values[5]);

                        PackageConfiguration config = new PackageConfiguration(length, height, width, weight, girth, price);
                        packageConfigurations.add(config);
                    } catch (NumberFormatException e) {
                        String finalLine = line;
                        logger.log(Level.WARNING, e, () -> "Invalid format in line: " + finalLine);
                    }
                } else {
                    logger.log(Level.WARNING, "Invalid number of values in line: " + line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e, () -> "Error reading file: " + filePath);
        }

        return packageConfigurations;
    }
}