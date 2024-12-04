package gui;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class PackageModel {

    private Group group;
    private Box cube;
    private Scene scene;
    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    public PackageModel() {
        group = new Group();
        cube = new Box(0, 0, 0);
        group.getChildren().add(cube);

        // Set up the camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                new Rotate(-20, Rotate.Y_AXIS),
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -500)
        );

        // Set up the scene
        scene = new Scene(group, 400, 400, true);
        scene.setFill(Color.LIGHTGRAY);
        scene.setCamera(camera);

        // Add rotation transforms to the group
        group.getTransforms().addAll(rotateX, rotateY);

        // Set up mouse event handlers for rotation
        scene.setOnMousePressed(this::handleMousePressed);
        scene.setOnMouseDragged(this::handleMouseDragged);

        // Set up the material for the box
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.DARKGRAY);
        cube.setMaterial(material);
    }

    public Group getGroup() {
        return group;
    }

    public Scene getScene() {
        return scene;
    }

    public void updateModel(int length, int width, int height) {
        cube.setWidth(width);
        cube.setHeight(height);
        cube.setDepth(length);
    }

    private void handleMousePressed(MouseEvent event) {
        mouseOldX = event.getSceneX();
        mouseOldY = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        mousePosX = event.getSceneX();
        mousePosY = event.getSceneY();
        rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
        rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
        mouseOldX = mousePosX;
        mouseOldY = mousePosY;
    }
}