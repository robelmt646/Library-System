package application;

import java.util.List;

import business.Author;
import business.ControllerFactory;
import business.ControllerInterface;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HomePage extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private static Stage primStage = null;

	public static Stage primStage() {
		return primStage;
	}

	public static class Colors {
		static Color green = Color.web("#F18851");
		static Color red = Color.FIREBRICK;
	}

	private static Stage[] allWindows = { AddMemberWindow.INSTANCE, CheckOutEntries.INSTANCE, loginPage.INSTANCE,
			AdminWindow.INSTANCE, AddBookPage.INSTANCE, AddAuthors.INSTANCE, BookRecords.INSTANCE, PrintPage.INSTANCE,
			AddCopyWindow.INSTANCE };

	public static void hideAllWindows() {
		primStage.hide();
		for (Stage st : allWindows) {
			st.hide();
		}
	}

	public static void destroyAllWindows() {
		for (Stage st : allWindows) {
			((LibWindow) st).setInitialized(false);
			;
		}
	}

	public static void showAdminWindow() {
		hideAllWindows();
		ControllerInterface controller = ControllerFactory.of();
		if (!AdminWindow.INSTANCE.isInitialized()) {
			AdminWindow.INSTANCE.init();
		}
		;
		AdminWindow.INSTANCE.show();
	}

	public static void showRecord() {
		hideAllWindows();
		ControllerInterface controller = ControllerFactory.of();
		if (!BookRecords.INSTANCE.isInitialized()) {
			BookRecords.INSTANCE.init();
		}
		BookRecords.INSTANCE.refreshBookList(controller.getBooksMap());
		;
		BookRecords.INSTANCE.show();
	}

	public static void showAddACopyWindow() {
		hideAllWindows();
		ControllerInterface controller = ControllerFactory.of();
		if (!AddCopyWindow.INSTANCE.isInitialized()) {
			AddCopyWindow.INSTANCE.setData(controller.getBooksMap());
			AddCopyWindow.INSTANCE.init();
		}
		AddCopyWindow.INSTANCE.refreshBookList();
		;
		AddCopyWindow.INSTANCE.show();
	}

	public static void showPrintPage() {
		hideAllWindows();
		if (!PrintPage.INSTANCE.isInitialized()) {
			PrintPage.INSTANCE.init();
		}
		PrintPage.INSTANCE.reset();
		PrintPage.INSTANCE.show();
	}

	public static void showAddBookPage(Boolean reset) {
		hideAllWindows();
		if (!AddBookPage.INSTANCE.isInitialized()) {
			AddBookPage.INSTANCE.init();
		}
		if (reset) {
			AddBookPage.INSTANCE.reset();
		}
		AddBookPage.INSTANCE.show();
	}

	public static void showMemeberPage(Boolean reset) {
		hideAllWindows();
		if (!AddBookPage.INSTANCE.isInitialized()) {
			AddMemberWindow.INSTANCE.init();
		}
		if (reset) {
			AddMemberWindow.INSTANCE.resetFields();
			AddMemberWindow.INSTANCE.getAll();

		}

		AddMemberWindow.INSTANCE.show();
	}

	public static void showCheckOutEntries() {
		hideAllWindows();
		if (!CheckOutEntries.INSTANCE.isInitialized()) {
			CheckOutEntries.INSTANCE.init();
		}
		CheckOutEntries.INSTANCE.reset();
		CheckOutEntries.INSTANCE.show();
	}

	public static void showAddBookPage(List<Author> list) {
		hideAllWindows();
		if (!AddBookPage.INSTANCE.isInitialized()) {
			AddBookPage.INSTANCE.init();
		}
		AddBookPage.INSTANCE.setAuthorList(list);
		;
		AddBookPage.INSTANCE.show();
	}

	public static void showAddAuthors(List<Author> authorList) {
		hideAllWindows();
		if (!AddAuthors.INSTANCE.isInitialized()) {
			AddAuthors.INSTANCE.init();
		}
		AddAuthors.INSTANCE.reset();
		AddAuthors.INSTANCE.setData(authorList);
		AddAuthors.INSTANCE.show();
	}

	@Override
	public void start(Stage primaryStage) {
		primStage = primaryStage;
		primaryStage.setTitle("Main Page");

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		MenuBar mainMenu = new MenuBar();
		VBox imageHolder = new VBox();
		Image image = new Image("application/libraryImage.jpg", 600, 300, false, false);

		ImageView iv = new ImageView();
		iv.setImage(image);
		imageHolder.getChildren().add(iv);
		imageHolder.setAlignment(Pos.CENTER);
		HBox splashBox = new HBox();
		Label splashLabel = new Label("The Library System");
		splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 30));
		splashBox.getChildren().add(splashLabel);
		splashBox.setAlignment(Pos.CENTER);

		topContainer.getChildren().add(mainMenu);
		topContainer.getChildren().add(splashBox);
		topContainer.getChildren().add(imageHolder);

		Menu optionsMenu = new Menu("Options");
		MenuItem login = new MenuItem("Login");

		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				hideAllWindows();
				if (!loginPage.INSTANCE.isInitialized()) {
					loginPage.INSTANCE.init();
				}
				loginPage.INSTANCE.clear();
				loginPage.INSTANCE.show();
			}
		});

		optionsMenu.getItems().addAll(login);

		mainMenu.getMenus().addAll(optionsMenu);
		Scene scene = new Scene(topContainer, 600, 370);
		primaryStage.setScene(scene);
		// scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		primaryStage.show();
	}

}
