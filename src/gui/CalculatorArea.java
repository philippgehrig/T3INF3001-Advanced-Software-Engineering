package gui;

import control.Calculator;
import data.Packet;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * The CalculatorArea class represents the area in the application where users can input package dimensions and weight to calculate shipping costs.
 */
public class CalculatorArea extends GridPane {

	// input fields
	/**
	 * TextField for inputting the length of the package.
	 */
	TextField lengthTextField = new TextField();

	/**
	 * TextField for inputting the width of the package.
	 */
	TextField widthTextField  = new TextField();

	/**
	 * TextField for inputting the height of the package.
	 */
	TextField heightTextField = new TextField();

	/**
	 * TextField for inputting the weight of the package.
	 */
	TextField weightTextField = new TextField();

	// output label
	/**
	 * Label for displaying the calculated shipping cost.
	 */
	Label shippingCostLabel = new Label("?");

	// buttons
	/**
	 * Button to trigger the calculation of shipping costs.
	 */
	Button calcButton = new Button("Calculate");

	/**
	 * Calculates the shipping costs based on the user input values.
	 *
	 * @return the calculated shipping costs
	 */
	private double calcShippingCosts() {
		Calculator calc = new Calculator();

		try {
			// get user input values
			int length = Integer.parseInt(lengthTextField.getText());
			int width = Integer.parseInt(widthTextField.getText());
			int height = Integer.parseInt(heightTextField.getText());
			int weight = Integer.parseInt(weightTextField.getText());

			// perform calculation
			Packet packet = new Packet(length, width, height, weight);
			double costs = calc.calcShippingCosts(packet);

			// show result
			shippingCostLabel.setText(String.valueOf(costs));
			return costs;
		} catch (Exception e) {
			// disable previous result display
			shippingCostLabel.setText("");

			// show error message popup with JavaFX.alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid Input");
			alert.setContentText(e.getMessage()); // returns the error message from the exception
			alert.showAndWait();
			return -1;
		}
	}

	/**
	 * Constructs a new CalculatorArea and initializes the input fields, labels, and buttons.
	 */
	public CalculatorArea() {
		// set standard distance between elements
		this.setPadding(new Insets(10, 10, 10, 10));

		// add description labels
		this.add(new Label("Length: "), 1, 1);
		this.add(new Label("Width:  "), 1, 2);
		this.add(new Label("Height: "), 1, 3);
		this.add(new Label("Weight: "), 1, 4);

		// add input fields
		this.add(lengthTextField, 2, 1);
		this.add(widthTextField,  2, 2);
		this.add(heightTextField, 2, 3);
		this.add(weightTextField, 2, 4);

		// add labels for units
		this.add(new Label("mm"), 3, 1);
		this.add(new Label("mm"), 3, 2);
		this.add(new Label("mm"), 3, 3);
		this.add(new Label("g"), 3, 4);

		// add shipping cost calculation line
		this.add(new Label("Shipping Costs: "), 1, 5);
		this.add(shippingCostLabel, 2, 5);
		this.add(calcButton, 3, 5);

		// set action listeners
		calcButton.setOnAction(ae -> calcShippingCosts());
	}
}