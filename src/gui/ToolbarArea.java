package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

/**
 * The ToolbarArea class represents the toolbar in the application, providing buttons for various actions such as
 * opening a project, creating a new file, and showing help information.
 */
public class ToolbarArea extends VBox {

	private static final String INFO_MESSAGE = "Package Calculator v0.3 \n (c) 2020 I. Bogicevic and P. Gehrig";
	private static final String EXIT_MESSAGE = "Do you really want to exit?";
	private static final String EXIT_TITLE = "Exit";

	/**
	 * Constructs a new ToolbarArea and initializes the toolbar buttons and their action listeners.
	 * Event listeners for each button calls dialog/info from Messages.java
	 * @see Messages
	 */
	public ToolbarArea() {
		// initialize other buttons

		Button aboutButton = new Button("About");
		Button exitButton = new Button("Exit");

		// action listeners
		aboutButton.setOnAction(e -> Messages.createMessage(INFO_MESSAGE));
		exitButton.setOnAction(e -> Messages.createDialog(EXIT_TITLE, EXIT_MESSAGE,
				Map.of(ButtonType.YES, () -> System.exit(0), ButtonType.NO, () -> {})));

		// create toolbar and add menu bar items
		ToolBar toolBar = new ToolBar();
		toolBar.getItems().addAll(exitButton, aboutButton);

		this.getChildren().add(toolBar);
	}
}