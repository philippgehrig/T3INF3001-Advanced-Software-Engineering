package control;

import java.io.File;
import gui.PackageCalculator;
import javafx.stage.DirectoryChooser;

/**
 * The ProjectHandling class provides methods to handle project operations such as opening a project and creating a new file.
 */
public class ProjectHandling {

	/**
	 * Opens a project located at the specified root path.
	 *
	 * @param rootPath the root path of the project to open
	 */
	public static void openProject(String rootPath) {
		// update window title
		PackageCalculator.getInstance().getPrimaryStage().setTitle(PackageCalculator.APPNAME + " – " + rootPath);
		// remember open project
		PackageCalculator.getInstance().rootPath = rootPath;
	}

	/**
	 * Opens a project by showing a directory chooser dialog to the user.
	 */
	public static void openProject() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		File projectDirectory;
		projectDirectory = directoryChooser.showDialog(PackageCalculator.getInstance().getPrimaryStage());
		if (projectDirectory != null) {
			openProject(projectDirectory.getAbsolutePath());
		}
	}

	/**
	 * Creates a new file in the current project.
	 */
	public static void newFile() {
		// Implementation for creating a new file
	}
}