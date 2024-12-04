package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Map;
import java.util.Optional;

/**
 * The Messages class represents a utility for displaying messages in the application.
 */
public class Messages {

    /**
     * Private constructor to prevent instantiation.
     */
    private Messages() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Creates and shows an alert with the specified message.
     * @param message the message to be displayed in the alert
     */
    public static void createMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // set Alert type to be information message
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
        Alert alert = new Alert(Alert.AlertType.ERROR); // set Alert type to be error message
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Creates and shows a confirmation dialog with the specified title and content.
     * @param title the title of the confirmation dialog
     * @param content the content of the confirmation dialog
     * @param commands the commands to be executed based on the user's choice
     */
    public static void createDialog(String title, String content, Map<ButtonType, Runnable> commands) {

        Alert alert = new Alert(Alert.AlertType.NONE); // set Alert type to be any message ==> dialog
        alert.setTitle(title); // set title of dialog window
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(commands.keySet());

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> {
            Runnable command = commands.get(buttonType);
            if (command != null) {
                command.run(); // runs the command associated with button when button is clicked
            }
        });
    }

}