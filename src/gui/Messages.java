package gui;

import javafx.scene.control.Alert;

/**
 * The Messages class represents a utility for displaying messages in the application.
 */
public class Messages {

    /**
     * Creates and shows an alert with the specified message.
     * @param message the message to be displayed in the alert
     */
    public static void createMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Creates and shows an error alert with the specified message.
     * @param message the message to be displayed in the error alert
     */
    public static void createErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}