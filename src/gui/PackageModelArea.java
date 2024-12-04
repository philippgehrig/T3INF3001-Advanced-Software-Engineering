package gui;

import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The PackageModelArea class contains the 3D model of the package.
 * It displays the 3D model of the package in a SubScene which can be rotated by the user.
 */
public class PackageModelArea extends VBox {

    private final PackageModel packageModel;
    private double mouseOldX;
    private double mouseOldY;

    /**
     * Constructor for the PackageModelArea class.
     * Contains the PackageModel Sub scene @link{PackageModel}
     */
    public PackageModelArea() {
        packageModel = new PackageModel();

        Text text = new Text("3D-Model");
        text.setFont(new Font(20));

        // Create a SubScene for the 3D model
        SubScene subScene = new SubScene(packageModel.getGroup(), 550, 800, true, null);
        subScene.setCamera(packageModel.createCamera());

        // Add the SubScene to the VBox
        this.getChildren().addAll(text, subScene);

        // Set up mouse events for rotating the 3D model
        subScene.setOnMousePressed(this::handleMousePressed);
        subScene.setOnMouseDragged(this::handleMouseDragged);
    }

    /**
     * This function updates the 3D model with the new dimensions
     * @param length of the 3-dimensional model
     * @param width of the 3-dimensional model
     * @param height of the 3-dimensional model
     */
    public void handleUpdate(int length, int width, int height) {
        packageModel.updateModel(length, width, height);
    }

    /**
     * This function handles the mouse pressed event for rotating the 3D model
     * @param event the mouse event
     */
    private void handleMousePressed(MouseEvent event) {
        mouseOldX = event.getSceneX();
        mouseOldY = event.getSceneY();
    }

    /**
     *  This function handles the mouse dragged event for rotating the 3D model
     * @param event the mouse event
     */
    private void handleMouseDragged(MouseEvent event) {
        double mousePosX = event.getSceneX();
        double mousePosY = event.getSceneY();

        // Update rotations based on mouse movement
        packageModel.getRotateX().setAngle(packageModel.getRotateX().getAngle() - (mousePosY - mouseOldY));
        packageModel.getRotateY().setAngle(packageModel.getRotateY().getAngle() + (mousePosX - mouseOldX));
        mouseOldX = mousePosX;
        mouseOldY = mousePosY;

    }
}