package gui;

import javafx.scene.control.ListView;

/**
 * The Message class represents
 */
public class MessagesArea extends ListView<String> {

        public MessagesArea() {
            this.setPrefWidth(200);
        }

        public void setMessage(String message) {
            this.clearMessages();
            this.getItems().add(message);
        }

        public void clearMessages() {
            this.getItems().clear();
        }

        public String getMessages() {
            return this.getItems().get(0);
        }

}
