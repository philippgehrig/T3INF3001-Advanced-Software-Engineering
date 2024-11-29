package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Map;
import java.util.Optional;

/**
 * The Messages class represents a utility for displaying messages in the application.
 */
public class Messages {

    private Messages() {
        throw new UnsupportedOperationException("Utility class");
    }

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

    public static void createDialog(String title, String content, Map<ButtonType, Runnable> commands) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(commands.keySet());

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> {
            Runnable command = commands.get(buttonType);
            if (command != null) {
                command.run();
            }
        });
    }

}