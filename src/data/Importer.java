package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importer {
    public List<Double> importShippingCosts(String filePath) {
        List<Double> shippingCosts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] costs = line.split(";");
                for (String cost : costs) {
                    shippingCosts.add(Double.parseDouble(cost));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shippingCosts;
    }
}