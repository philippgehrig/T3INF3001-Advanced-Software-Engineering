package gui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The main application class for the PackageCalculator.
 * This class sets up the GUI and initializes the application.
 */
public class PackageCalculator extends Application {

	public static final String APP_NAME = "Package Calculator";

	// initialise GUI areas
	public static final ToolbarArea toolbarArea = new ToolbarArea();
	public static final CalculatorArea calculatorArea = new CalculatorArea();
	public static final DHBWArea dhbwArea = new DHBWArea();
	public static final PackageModelArea packageModelArea = new PackageModelArea();

	/**
	 * Starts the application and sets up the main GUI.
	 * @param primaryStage the primary stage for this application
	 */
	@Override
	public void start(Stage primaryStage) {

		// tdSplitPane
		SplitPane tdSplitPane = new SplitPane();
		tdSplitPane.setOrientation(Orientation.HORIZONTAL);
		tdSplitPane.getItems().addAll(dhbwArea, calculatorArea, packageModelArea);
		tdSplitPane.setDividerPositions(0.3f, 0.7f);

		// center the DHBW area
		dhbwArea.setAlignment(Pos.CENTER);

		// center the calculator area
		calculatorArea.setAlignment(Pos.CENTER);
		calculatorArea.setMinWidth(500); // minimum width
		calculatorArea.setMinHeight(400); // minimum height

		// center the package model area
		packageModelArea.setAlignment(Pos.CENTER);

		// add all areas
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(toolbarArea);
		mainPane.setCenter(tdSplitPane);

		// show main pane
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(mainPane, screenBounds.getWidth(), screenBounds.getHeight(), true);
		primaryStage.setTitle(APP_NAME);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * The main method to launch the application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}