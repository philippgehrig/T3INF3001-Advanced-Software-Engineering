package gui;

import javafx.scene.control.ListView;

/**
 * The Message class represents
 */
public class MessagesArea extends ListView<String> {

        /**
         * Creates a new MessagesArea.
         */
        public MessagesArea() {
            this.setPrefWidth(200);
        }

        /**
         * Sets the message to be displayed in the messages area.
         * @param message the message to be displayed
         */
        public void setMessage(String message) {
            this.clearMessages();
            this.getItems().add(message);
        }

        /**
         * Clears the message that is currently displayed
         */
        public void clearMessages() {
            this.getItems().clear();
        }

        /**
         * @return the message currently displayed
         */
        public String getMessages() {
            return this.getItems().get(0);
        }

}
