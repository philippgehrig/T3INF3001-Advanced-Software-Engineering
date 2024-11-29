package gui;

import data.Importer;
import data.PackageConfiguration;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class PackageConfigurationPopup {

    private static final String LENGTH_LABEL = "Length [mm]";
    private static final String WIDTH_LABEL = "Width [mm]";
    private static final String HEIGHT_LABEL = "Height [mm]";
    private static final String WEIGHT_LABEL = "Weight [g]";
    private static final String GIRTH_LABEL = "Girth [mm]";
    private static final String PRICE_LABEL = "Price [€]";

    private final Importer importer = new Importer();

    public void showInfoPopup(String filePath) {
        List<PackageConfiguration> data = importer.importPackageConfigurations(filePath);

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Package Size Combinations");

        TableView<PackageConfiguration> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<PackageConfiguration, Integer> lengthColumn = new TableColumn<>(LENGTH_LABEL);
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));

        TableColumn<PackageConfiguration, Integer> widthColumn = new TableColumn<>(WIDTH_LABEL);
        widthColumn.setCellValueFactory(new PropertyValueFactory<>("width"));

        TableColumn<PackageConfiguration, Integer> heightColumn = new TableColumn<>(HEIGHT_LABEL);
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

        TableColumn<PackageConfiguration, Integer> weightColumn = new TableColumn<>(WEIGHT_LABEL);
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        TableColumn<PackageConfiguration, Integer> girthColumn = new TableColumn<>(GIRTH_LABEL);
        girthColumn.setCellValueFactory(new PropertyValueFactory<>("girth"));

        TableColumn<PackageConfiguration, Double> priceColumn = new TableColumn<>(PRICE_LABEL);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll
                (List.of(lengthColumn, widthColumn, heightColumn, weightColumn, girthColumn, priceColumn));

        // Add data to the table
        tableView.getItems().addAll(data);

        // Calculate preferred height based on the number of rows
        int rowHeight = 25; // Approximate row height
        int maxVisibleRows = 10; // Maximum number of rows to display without scrolling
        int numRows = Math.min(data.size(), maxVisibleRows);
        tableView.setPrefHeight(numRows * rowHeight + 30.0); // Add some padding

        VBox vbox = new VBox(tableView);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}