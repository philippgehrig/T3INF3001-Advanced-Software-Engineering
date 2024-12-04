package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PackageModelArea extends VBox {

    private final PackageModel packageModel;

    public PackageModelArea() {
        packageModel = new PackageModel();

        // Add the scene to the VBox
        this.getChildren().add(new Label("3D Package Model"));
        this.getChildren().add(packageModel.getGroup());
        this.setPadding(new Insets(10));
    }

    public void handleUpdate(int length, int width, int height) {
        // Update the 3D model
        packageModel.updateModel(length, width, height);
    }
}