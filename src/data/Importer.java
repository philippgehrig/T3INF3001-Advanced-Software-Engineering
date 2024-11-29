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

    /**
     * This function imports the Package CConfigurations from a csv file
     * @param filePath to the package configurations
     * @return List of Package Configurations
     */

    public List<PackageConfiguration> importPackageConfigurations(String filePath) {
        // initialise the list of package configurations
        List<PackageConfiguration> packageConfigurations = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                // first line is the header line in the csv file which should be skipped
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // SJump to next line
                }
                String[] values = line.split(";"); // split the Values by the ';' delimiter

                // there need to be at least 5 values in each line to create a package configuration
                if (values.length >= 5) {
                    PackageConfiguration config = getPackageConfiguration(values);
                    packageConfigurations.add(config);
                } else {
                    logger.log(Level.WARNING, "Invalid number of values in line: {0}", line);
                }
            }
        } catch (IOException e) {
            // catch problems with reading the file
            logger.log(Level.SEVERE, e, () -> "Error reading file: " + filePath);
        } catch (NumberFormatException e) {
            // catch invalid number format when parsing the int Values
            logger.log(Level.WARNING, "Invalid format in line: {0}", e.getMessage());
        }

        return packageConfigurations;
    }

    /**
     * This private function creates a Package Configuration from a String array
     * @param values Array of Strings which are read from the csv file
     * @return PackageConfiguration object with the values from the csv file
     */

    private static PackageConfiguration getPackageConfiguration(String[] values) {
        int length = Integer.parseInt(values[0]);
        int height = Integer.parseInt(values[1]);
        int width = Integer.parseInt(values[2]);
        int weight = Integer.parseInt(values[3]);
        int girth = values[4].isEmpty() ? 0 : Integer.parseInt(values[4]); // Handle empty girth
        double price = Double.parseDouble(values[5]);

        return new PackageConfiguration(length, width, height, weight, girth, price);
    }
}