package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * The DHBWArea class represents a simple area above the calculator area.
 */
public class DHBWArea extends VBox {

    private static final String DHBW_TEXT =
            "\n \n student number: 5622763 " +
                    "\n \n name: Philipp Gehrig" +
                    "\n \n This project is for the Module Advanced Software Engineering ";

    private static final String IMAGE_PATH = "/gui/resources/dhbw.png";

    /**
     * Constructs a new DHBWArea and initializes its content.
     */
    public DHBWArea() {
        Label label = new Label(DHBW_TEXT);
        ImageView imageView = new ImageView(new Image(IMAGE_PATH));

        // Set the desired width and height for the image
        imageView.setFitWidth(400); // Set the desired width
        imageView.setFitHeight(400); // Set the desired height
        imageView.setPreserveRatio(true); // Preserve the aspect ratio

        // Set padding for the VBox
        this.setPadding(new Insets(10)); // Reduced padding

        // Add image and label to the VBox
        this.getChildren().addAll(imageView, label);
    }
}