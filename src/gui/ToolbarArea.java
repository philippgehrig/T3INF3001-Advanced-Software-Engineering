package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The ToolbarArea class represents the toolbar in the application, providing buttons for various actions such as
 * opening a project, creating a new file, and showing help information.
 */
public class ToolbarArea extends VBox {

	/**
	 * Shows a help dialog with information about the application.
	 */
	private void showInfoDialog() {
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		VBox vbox = new VBox(20);
		Text infoText = new Text("Package Calculator v0.3 \n (c) 2020 I. Bogicevic and P. Gehrig");
		vbox.getChildren().add(infoText);
		Scene dialogScene = new Scene(vbox, 400, 250);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	/**
	 * Constructs a new ToolbarArea and initializes the toolbar buttons and their action listeners.
	 */
	public ToolbarArea() {


		// initialize other buttons

		Button aboutButton = new Button("About");
		Button settingsButton = new Button("Settings");

		// action listeners
		aboutButton.setOnAction(e -> showInfoDialog());

		// create toolbar and add menu bar items
		ToolBar toolBar = new ToolBar();
		toolBar.getItems().addAll(settingsButton, aboutButton);

		// add toolbar to VBox
		this.getChildren().add(toolBar);
	}
}