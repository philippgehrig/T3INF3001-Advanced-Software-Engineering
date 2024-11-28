package gui;

import control.Calculator;
import control.NotValidDimensionsException;
import data.Packet;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The CalculatorArea class represents the area in the application where users can input package dimensions and weight
 * to calculate shipping costs.
 * @see Calculator
 */
public class CalculatorArea extends GridPane {

	private static final String MM = "mm";
	private static final String CM = "cm";
	private static final String M = "m";
	private static final String G = "g";
	private static final String KG = "kg";
	private static final String LENGTH_LABEL = "Length: ";
	private static final String WIDTH_LABEL = "Width:  ";
	private static final String HEIGHT_LABEL = "Height: ";
	private static final String WEIGHT_LABEL = "Weight: ";
	private static final String SHIPPING_COSTS_LABEL = "Shipping Costs: ";
	private static final String EMPTY_STRING = "";
	private static final String STYLESHEET_PATH = "/gui/styles.css";
	private static final String INPUT_FIELD_CLASS = "input-field";
	private static final String CALC_BUTTON_CLASS = "calc-button";
	private static final String RESULT_LABEL_CLASS = "result-label";

	// initialise the text fields, labels, buttons
	TextField lengthTextField = new TextField();
	TextField widthTextField  = new TextField();
	TextField heightTextField = new TextField();
	TextField weightTextField = new TextField();
	Label shippingCostLabel = new Label(EMPTY_STRING);
	Button calcButton = new Button("Calculate");
	Button infoButton = new Button("i");

	// initialise the combo boxes / drop down menus
	ComboBox<String> lengthUnitComboBox = new ComboBox<>();
	ComboBox<String> widthUnitComboBox = new ComboBox<>();
	ComboBox<String> heightUnitComboBox = new ComboBox<>();
	ComboBox<String> weightUnitComboBox = new ComboBox<>();

	private void handleCalculatorIO() {
		Calculator calc = new Calculator();

		try {
			int length = Integer.parseInt(lengthTextField.getText());
			int width = Integer.parseInt(widthTextField.getText());
			int height = Integer.parseInt(heightTextField.getText());
			int weight = Integer.parseInt(weightTextField.getText());

			// Convert length, width, and height to millimeters
			length = convertToMillimeters(length, lengthUnitComboBox.getValue());
			width = convertToMillimeters(width, widthUnitComboBox.getValue());
			height = convertToMillimeters(height, heightUnitComboBox.getValue());

			// Convert weight to grams
			weight = convertToGrams(weight, weightUnitComboBox.getValue());

			Packet packet = new Packet(length, width, height, weight);

			double costs = calc.calcShippingCosts(packet);
			shippingCostLabel.setText(String.valueOf(costs));

		} catch (IllegalArgumentException | NotValidDimensionsException e) {
			Messages.createErrorMessage(e.getMessage());
		}
	}

	private int convertToMillimeters(int value, String unit) {
		return switch (unit) {
			case CM -> value * 10;
			case M -> value * 1000;
			default -> value; // "mm"
		};
	}

	private int convertToGrams(int value, String unit) {
		if (KG.equals(unit)) {
			return value * 1000;
		}
		return value; // "g"
	}

	private void showInfoPopup() {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Package Size Combinations");

		TableView<String[]> tableView = new TableView<>();

		String[][] data = {
				{"300", "300", "150", "1000", "1200", "10.00"},
				{"600", "300", "150", "2000", "1800", "20.00"},
				{"1200", "600", "600", "5000", "3600", "30.00"},
				{"1200", "600", "600", "10000", "3600", "40.00"},
				{"1200", "600", "600", "31000", "3600", "50.00"}
		};

		// Create columns dynamically based on data
		for (int i = 0; i < data[0].length; i++) {
			TableColumn<String[], String> column = new TableColumn<>(getColumnName(i));
			final int colIndex = i;
			column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()[colIndex]));
			column.setCellFactory(col -> new TableCell<>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
					} else {
						setText(item);
						setStyle("-fx-alignment: CENTER;");
					}
				}
			});
			tableView.getColumns().add(column);
		}

		// Add girth info button next to the "Girth" label
		TableColumn<String[], String> girthColumn = (TableColumn<String[], String>) tableView.getColumns().get(4);
		Label girthLabel = new Label("Girth");
		Button girthInfoButton = new Button("?");
		Tooltip girthTooltip = new Tooltip("Girth is calculated as 2 * (Width + Height)");
		girthInfoButton.setTooltip(girthTooltip);

		// Create HBox and set padding and margin
		HBox girthHeader = new HBox(5); // 5px padding between label and button
		HBox.setMargin(girthLabel, new Insets(3, 0, 0, 0)); // Move text down by 3px
		girthHeader.getChildren().addAll(girthLabel, girthInfoButton);
		girthColumn.setGraphic(girthHeader);

		// Add data to the table
		tableView.getItems().addAll(data);

		// Set fixed size for the table
		tableView.setPrefSize(400, 150);

		// Add table to a ScrollPane
		ScrollPane scrollPane = new ScrollPane(tableView);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);

		VBox vbox = new VBox(scrollPane);
		Scene scene = new Scene(vbox);
		popupStage.setScene(scene);
		popupStage.showAndWait();
	}

	private String getColumnName(int index) {
		return switch (index) {
			case 0 -> "Length";
			case 1 -> "Width";
			case 2 -> "Height";
			case 3 -> "Weight";
			case 4 -> ""; // name is already set at the tooltip
			case 5 -> "Price";
			default -> "Column " + (index + 1);
		};
	}

	public CalculatorArea() {
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setHgap(10);
		this.setVgap(10);

		// Add CSS classes
		TextField[] textFields = {lengthTextField, widthTextField, heightTextField, weightTextField};
		for (TextField textField : textFields) {
			textField.getStyleClass().add(INPUT_FIELD_CLASS);
		}
		calcButton.getStyleClass().add(CALC_BUTTON_CLASS);
		shippingCostLabel.getStyleClass().add(RESULT_LABEL_CLASS);

		// Add unit options
		lengthUnitComboBox.getItems().addAll(MM, CM, M);
		widthUnitComboBox.getItems().addAll(MM, CM, M);
		heightUnitComboBox.getItems().addAll(MM, CM, M);
		weightUnitComboBox.getItems().addAll(G, KG);

		// Set default values
		lengthUnitComboBox.setValue(MM);
		widthUnitComboBox.setValue(MM);
		heightUnitComboBox.setValue(MM);
		weightUnitComboBox.setValue(G);

		this.add(new Label(LENGTH_LABEL), 1, 1);
		this.add(new Label(WIDTH_LABEL), 1, 2);
		this.add(new Label(HEIGHT_LABEL), 1, 3);
		this.add(new Label(WEIGHT_LABEL), 1, 4);

		this.add(lengthTextField, 2, 1);
		this.add(widthTextField, 2, 2);
		this.add(heightTextField, 2, 3);
		this.add(weightTextField, 2, 4);

		this.add(lengthUnitComboBox, 3, 1);
		this.add(widthUnitComboBox, 3, 2);
		this.add(heightUnitComboBox, 3, 3);
		this.add(weightUnitComboBox, 3, 4);

		this.add(new Label(SHIPPING_COSTS_LABEL), 1, 5);
		this.add(shippingCostLabel, 2, 5);
		this.add(calcButton, 3, 5);
		this.add(infoButton, 4, 5);

		calcButton.setOnAction(event -> handleCalculatorIO());
		infoButton.setOnAction(event -> showInfoPopup());

		// Add CSS stylesheet
		this.getStylesheets().add(getClass().getResource(STYLESHEET_PATH).toExternalForm());
	}
}