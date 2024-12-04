package gui;

import control.Calculator;
import control.NotValidDimensionsException;
import data.Packet;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Objects;

/**
 * The CalculatorArea class represents the main interface for calculating shipping costs.
 */
public class CalculatorArea extends GridPane {

	// declarations of constants
	private static final String MM = "mm";
	private static final String CM = "cm";
	private static final String M = "m ";
	private static final String G = "g ";
	private static final String KG = "kg";
	private static final String LENGTH_LABEL = "Length: ";
	private static final String WIDTH_LABEL = "Width:  ";
	private static final String HEIGHT_LABEL = "Height: ";
	private static final String WEIGHT_LABEL = "Weight: ";
	private static final String SHIPPING_COSTS_LABEL = "Shipping Costs: ";
	private static final String EMPTY_STRING = "";
	private static final String STYLESHEET_PATH = "../gui/styles.css";
	private static final String INPUT_FIELD_CLASS = "input-field";
	private static final String CALC_BUTTON_CLASS = "calc-button";
	private static final String RESULT_LABEL_CLASS = "result-label";
	private static final String SHIPPING_COSTS_FILE_PATH = "src/data/shippingCosts.csv";

	// UI Components
	private final TextField lengthTextField = new TextField();
	private final TextField widthTextField = new TextField();
	private final TextField heightTextField = new TextField();
	private final TextField weightTextField = new TextField();
	private final Label shippingCostLabel = new Label(EMPTY_STRING);
	private final Button calcButton = new Button("Calculate");
	private final Button infoButton = new Button("i");

	private final ComboBox<String> lengthUnitComboBox = new ComboBox<>();
	private final ComboBox<String> widthUnitComboBox = new ComboBox<>();
	private final ComboBox<String> heightUnitComboBox = new ComboBox<>();
	private final ComboBox<String> weightUnitComboBox = new ComboBox<>();

	/**
	 * Constructor for the CalculatorArea class.
	 */
	public CalculatorArea() {
		setupLayout();
		setupEventHandlers();
	}

	/**
	 * This function sets up the layout of the CalculatorArea.
	 */
	private void setupLayout() {
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

		// Labels with the dimensions and weight
		this.add(new Label(LENGTH_LABEL), 1, 1);
		this.add(new Label(WIDTH_LABEL), 1, 2);
		this.add(new Label(HEIGHT_LABEL), 1, 3);
		this.add(new Label(WEIGHT_LABEL), 1, 4);

		// input fields for the dimensions and weight
		this.add(lengthTextField, 2, 1);
		this.add(widthTextField, 2, 2);
		this.add(heightTextField, 2, 3);
		this.add(weightTextField, 2, 4);

		// dropdown menus for the units
		this.add(lengthUnitComboBox, 3, 1);
		this.add(widthUnitComboBox, 3, 2);
		this.add(heightUnitComboBox, 3, 3);
		this.add(weightUnitComboBox, 3, 4);

		// shipping costs label + calculate shipping cost and info buttons
		this.add(new Label(SHIPPING_COSTS_LABEL), 1, 5);
		this.add(shippingCostLabel, 2, 5);
		this.add(calcButton, 3, 5);
		this.add(infoButton, 4, 5);

		// Add CSS stylesheet
		this.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLESHEET_PATH)).toExternalForm());
	}

	/**
	 * This function sets up the event handlers for the CalculatorArea.
	 */
	private void setupEventHandlers() {
		// calculate the shipping cost once the button is clicked
		calcButton.setOnAction(event -> handleCalculatorIO());

		// show the info popup with different package configurations when the info button is clicked
		infoButton.setOnAction(event -> new PackageConfigurationPopup().showInfoPopup(SHIPPING_COSTS_FILE_PATH));
	}

	/**
	 * This function handles the input and output of the calculator.
	 * It reads the input values from the textFields
	 * converts them to the correct units and calculates the shipping costs.
	 */
	private void handleCalculatorIO() {
		Calculator calc = new Calculator();

		try {
			// read the input values from the testFields
			int length = Integer.parseInt(lengthTextField.getText());
			int width = Integer.parseInt(widthTextField.getText());
			int height = Integer.parseInt(heightTextField.getText());
			int weight = Integer.parseInt(weightTextField.getText());

			// Convert dimensions and weight
			length = convertToMillimeters(length, lengthUnitComboBox.getValue());
			width = convertToMillimeters(width, widthUnitComboBox.getValue());
			height = convertToMillimeters(height, heightUnitComboBox.getValue());
			weight = convertToGrams(weight, weightUnitComboBox.getValue());

			// create a new Packet with the converted values
			Packet packet = new Packet(length, width, height, weight);

			// calculate the shipping costs and display them
			double costs = calc.calcShippingCosts(packet, SHIPPING_COSTS_FILE_PATH);
			shippingCostLabel.setText(String.valueOf(costs));

			// update the 3D model
			PackageCalculator.packageModelArea.handleUpdate(packet.getLength(), packet.getWidth(), packet.getHeight());

		} catch (IllegalArgumentException | NotValidDimensionsException e) {
			// create an Error message for an invalid input
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
		return KG.equals(unit) ? value * 1000 : value; // "g"
	}
}
