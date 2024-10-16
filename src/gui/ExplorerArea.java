package gui;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * The ExplorerArea class represents the file explorer area in the application, providing a tree view of the file system.
 */
public class ExplorerArea extends TabPane {

	private Tab explorerTab;
	private TreeView<FileItem> treeView;

	/**
	 * Wrapper class for a tree cell representing a file item.
	 */
	class FileItem {
		public File file;

		/**
		 * Constructs a new FileItem with the specified file.
		 *
		 * @param file the file to be represented by this FileItem
		 */
		public FileItem(File file) {
			this.file = file;
		}

		/**
		 * Constructs a new FileItem with the specified file path.
		 *
		 * @param str the file path to be represented by this FileItem
		 */
		public FileItem(String str) {
			this.file = new File(str);
		}

		/**
		 * Returns the string representation of the file item.
		 *
		 * @return the string representation of the file item
		 */
		@Override
		public String toString() {
			if (file.getAbsolutePath().equals(PackageCalculator.getInstance().rootPath)) {
				return "..." + File.separator + file.getName();
			}
			return file.getName();
		}

		/**
		 * Returns the context menu for the file item.
		 *
		 * @return the context menu for the file item
		 */
		public ContextMenu getMenu() {
			return new ContextMenu(new MenuItem("test"));
		}
	}

	private TreeView<FileItem> buildFileSystemBrowser(String rootPath) {
		TreeItem<FileItem> root = createNode(new FileItem(new File(rootPath)));
		return new TreeView<FileItem>(root);
	}

	private TreeItem<FileItem> createNode(final FileItem fileItem) {
		return new TreeItem<FileItem>(fileItem) {
			private boolean isLeaf;
			private boolean isFirstTimeChildren = true;
			private boolean isFirstTimeLeaf = true;

			@Override
			public ObservableList<TreeItem<FileItem>> getChildren() {
				if (isFirstTimeChildren) {
					isFirstTimeChildren = false;
					super.getChildren().setAll(buildChildren(this));
				}
				return super.getChildren();
			}

			@Override
			public boolean isLeaf() {
				if (isFirstTimeLeaf) {
					isFirstTimeLeaf = false;
					FileItem f = (FileItem) getValue();
					isLeaf = f.file.isFile();
				}
				return isLeaf;
			}

			private ObservableList<TreeItem<FileItem>> buildChildren(TreeItem<FileItem> TreeItem) {
				FileItem f = TreeItem.getValue();
				if (f != null && f.file.isDirectory()) {
					File[] files = f.file.listFiles();
					if (files != null) {
						ObservableList<TreeItem<FileItem>> children = FXCollections.observableArrayList();
						for (File childFile : files) {
							if (childFile.isDirectory() || childFile.getName().toLowerCase().endsWith(".txt")) {
								children.add(createNode(new FileItem(childFile)));
							}
						}
						return children;
					}
				}
				return FXCollections.emptyObservableList();
			}
		};
	}

	private final class TreeCellImpl extends TreeCell<FileItem> {

		@Override
		public void updateItem(FileItem item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
				return;
			}
			setText(getItem() == null ? "" : getItem().toString());
			setGraphic(getTreeItem().getGraphic());
			ContextMenu contextMenu = new ContextMenu();
			if (getItem().file.isDirectory()) {
				MenuItem newFileMenu = new MenuItem("New File");
				MenuItem newSubfolderMenu = new MenuItem("New Subfolder");
				contextMenu.getItems().addAll(newFileMenu, newSubfolderMenu);
			} else {
				MenuItem openMenu = new MenuItem("Open");
				MenuItem closeMenu = new MenuItem("Close");
				contextMenu.getItems().addAll(openMenu, closeMenu);
			}
			MenuItem copyMenu = new MenuItem("Copy");
			MenuItem pasteMenu = new MenuItem("Paste");
			MenuItem deleteMenu = new MenuItem("Delete");
			contextMenu.getItems().addAll(new SeparatorMenuItem(), copyMenu, pasteMenu, deleteMenu);
			setContextMenu(contextMenu);
		}
	}

	/**
	 * Loads a new tree view for the specified project path.
	 *
	 * @param projectPath the path of the project to load
	 */
	@SuppressWarnings("unchecked")
	public void loadNewTree(String projectPath) {
		TreeItem<FileItem> root = new TreeItem<FileItem>();
		root.setExpanded(true);
		root.getChildren().addAll(
				new TreeItem<FileItem>(),
				new TreeItem<FileItem>(),
				new TreeItem<FileItem>()
		);
		treeView = new TreeView<FileItem>(root);

		this.treeView = buildFileSystemBrowser(projectPath);

		treeView.setCellFactory(new Callback<TreeView<FileItem>, TreeCell<FileItem>>() {
			@Override
			public TreeCell<FileItem> call(TreeView<FileItem> p) {
				return new TreeCellImpl();
			}
		});

		treeView.getRoot().setExpanded(true);

		explorerTab.setContent(treeView);
	}

	/**
	 * Constructs a new ExplorerArea and initializes the explorer tab.
	 */
	public ExplorerArea() {
		explorerTab = new Tab("Explorer");
		explorerTab.setClosable(false);
		this.getTabs().add(explorerTab);
	}
}