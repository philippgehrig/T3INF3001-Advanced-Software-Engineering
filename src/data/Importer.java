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

    public List<Double> importShippingCosts(String filePath) {
        List<Double> shippingCosts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] costs = line.split(";");
                for (String cost : costs) {
                    addShippingCost(shippingCosts, cost);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e, () -> "Error reading file: " + filePath);
        }

        return shippingCosts;
    }

    private void addShippingCost(List<Double> shippingCosts, String cost) {
        try {
            shippingCosts.add(Double.parseDouble(cost));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, e, () -> "Invalid format: " + cost);
        }
    }
}