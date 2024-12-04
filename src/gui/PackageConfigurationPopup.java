package gui;

import data.Importer;
import data.PackageConfiguration;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * The PackageConfigurationPopup class represents a popup window for displaying package configurations.
 */
public class PackageConfigurationPopup {
    // initialize the labels
    private static final String LENGTH_LABEL = "Length [mm]";
    private static final String WIDTH_LABEL = "Width [mm]";
    private static final String HEIGHT_LABEL = "Height [mm]";
    private static final String WEIGHT_LABEL = "Weight [g]";
    private static final String GIRTH_LABEL = "Girth [mm]";
    private static final String PRICE_LABEL = "Price [€]";
    private static final String POPUP_STAGE_TITLE = "Package Size Combinations";
    // create an instance of the Importer class
    private final Importer importer = new Importer();

    /**
     * This function create a popup window with the package configurations
     * @param filePath to the configuration file
     */
    public void showInfoPopup(String filePath) {
        // import the package configurations from the filepath
        List<PackageConfiguration> data = importer.importPackageConfigurations(filePath);

        // initialise a stage with a title
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(POPUP_STAGE_TITLE);

        TableView<PackageConfiguration> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // create the columns for the table

        TableColumn<PackageConfiguration, Integer> lengthColumn = new TableColumn<>(LENGTH_LABEL);
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));

        TableColumn<PackageConfiguration, Integer> widthColumn = new TableColumn<>(WIDTH_LABEL);
        widthColumn.setCellValueFactory(new PropertyValueFactory<>("width"));

        TableColumn<PackageConfiguration, Integer> heightColumn = new TableColumn<>(HEIGHT_LABEL);
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

        TableColumn<PackageConfiguration, Integer> weightColumn = new TableColumn<>(WEIGHT_LABEL);
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        TableColumn<PackageConfiguration, Integer> girthColumn = getPackageConfigurationGirthTableColumn();

        TableColumn<PackageConfiguration, Double> priceColumn = new TableColumn<>(PRICE_LABEL);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // add the columns to the table
        tableView.getColumns().addAll(lengthColumn, widthColumn, heightColumn, weightColumn, girthColumn, priceColumn);

        // Add data to the table
        tableView.getItems().addAll(data);

        // Calculate preferred height based on the number of rows
        int rowHeight = 25; // Approximate row height
        int maxVisibleRows = 10; // Maximum number of rows to display without scrolling
        int numRows = Math.min(data.size(), maxVisibleRows);
        tableView.setPrefHeight(numRows * rowHeight + 30.0); // Add some padding

        // Create a VBox to hold the table
        VBox vbox = new VBox(tableView);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    /**
     * additional function to create the girth column since girth can be null / zero
     * @return a {@link TableColumn} for displaying the "girth" property of a {@link PackageConfiguration}.
     */
    private static TableColumn<PackageConfiguration, Integer> getPackageConfigurationGirthTableColumn() {

        // initialise the girth column
        TableColumn<PackageConfiguration, Integer> girthColumn = new TableColumn<>(GIRTH_LABEL);
        girthColumn.setCellValueFactory(new PropertyValueFactory<>("girth"));
        girthColumn.setCellFactory(_ -> new TableCell<>() {

            /**
             * This function is called by JavaFX to display the cell content.
             * Creates a TableColumn for displaying the "girth" property of a
             * {@link PackageConfiguration} in a {@link TableView}.
             *
             * <p>The column is configured to:
             * <ul>
             *   <li>Retrieve the "girth" property using a {@link PropertyValueFactory}.</li>
             *   <li>Use a custom {@link TableCell} to control how the "girth" values are displayed.</li>
             *   <li>Display an empty cell for null, empty, or zero values.</li>
             * </ul>
             */
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                // girth is set to 0 if it is not used in the configuration
                if (empty || item == null || item == 0) {
                    setText(""); // set text to 0
                } else {
                    setText(item.toString());
                }
            }
        });
        return girthColumn; // the girth column
    }
}