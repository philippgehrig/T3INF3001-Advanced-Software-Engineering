package gui;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * The PackageModel class contains the 3D model of the package.
 * It displays the 3D model of the package in a Group which can be rotated by the user.
 * It is used in a SubScene by PackageModelArea.
 * @see gui.PackageModelArea
 */
public class PackageModel {

    private final Group group;
    private final Box cube;
    private final Rotate rotateX = new Rotate(30, Rotate.X_AXIS); // initial rotation on x axis by 30 deg
    private final Rotate rotateY = new Rotate(45, Rotate.Y_AXIS); // initial rotation on y axis by 45 deg

    /**
     * Constructor for the PackageModel class.
     * creates a 3-dimensional-model of the package that is calculated
     */
    public PackageModel() {
        group = new Group();

        // Create the cube
        cube = new Box(50, 50, 50); // Initial dimensions
        group.getChildren().add(cube);

        // Apply rotations to the group
        group.getTransforms().addAll(rotateX, rotateY);

        // Set up the material for the cube
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.DARKGRAY);
        cube.setMaterial(material);
    }

    /**
     * Getter for the group containing the 3D model
     * @return the group containing the 3D model
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Updates the dimensions of the cube in the 3D model
     * @param length the length of the cube
     * @param width the width of the cube
     * @param height the height of the cube
     */
    public void updateModel(int length, int width, int height) {
        // Update cube dimensions
        cube.setWidth(width);
        cube.setHeight(height);
        cube.setDepth(length);
    }

    /**
     * Creates a camera for the 3D model
     * @return the camera for the 3D model
     */
    public PerspectiveCamera createCamera() {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().add(new Translate(0, 0, -200)); // Position the camera
        camera.setNearClip(0.1);
        camera.setFarClip(1000);
        camera.setFieldOfView(60);
        return camera;
    }

    /**
     * Getter for the X rotation of the 3D model
     * @return the X rotation of the 3D model
     */
    public Rotate getRotateX() {
        return rotateX;
    }

    /**
     * Getter for the Y rotation of the 3D model
     * @return the Y rotation of the 3D model
     */
    public Rotate getRotateY() {
        return rotateY;
    }
}
