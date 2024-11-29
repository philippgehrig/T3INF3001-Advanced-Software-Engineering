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

	/** The name of the application. */
	public final static String APPNAME = "PackageCalculator";

	/** Singleton instance of the PackageCalculator. */
	private static PackageCalculator instance;

	/**
	 * Returns the singleton instance of the PackageCalculator.
	 *
	 * @return the singleton instance
	 */
	public static PackageCalculator getInstance() {
		return instance;
	}

	// GUI areas
	public static final ToolbarArea toolbarArea = new ToolbarArea();
	public static final CalculatorArea calculatorArea = new CalculatorArea();
	public static final DHBWArea dhbwArea = new DHBWArea();

	/** The primary stage for the application. */
	private Stage primaryStage;

	/**
	 * Returns the primary stage of the application.
	 *
	 * @return the primary stage
	 */
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	/**
	 * Starts the application and sets up the main GUI.
	 *
	 * @param primaryStage the primary stage for this application
	 */
	@Override
	public void start(Stage primaryStage) {
		// remember singleton instance (instantiated by javafx)
		PackageCalculator.instance = this;

		// remember stage for subwindows
		this.primaryStage = primaryStage;

		// tdSplitPane
		SplitPane tdSplitPane = new SplitPane();
		tdSplitPane.setOrientation(Orientation.VERTICAL);
		tdSplitPane.getItems().addAll(dhbwArea, calculatorArea);
		tdSplitPane.setDividerPositions(0.4f, 0.6f); // Adjusted divider position

		// center the dhbw area
		dhbwArea.setAlignment(Pos.CENTER);

		// center the calculator area
		calculatorArea.setAlignment(Pos.TOP_CENTER);

		// add all areas
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(toolbarArea);
		mainPane.setCenter(tdSplitPane);

		// show main pane
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(mainPane, screenBounds.getWidth(), screenBounds.getHeight(), true);
		primaryStage.setTitle(APPNAME);
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