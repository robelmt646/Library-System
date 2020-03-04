package application;

import business.ControllerFactory;
import dataaccess.Auth;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminWindow extends Stage implements LibWindow {

	public static final AdminWindow INSTANCE = new AdminWindow();

	@Override
	public void init() {

		GridPane grid = new GridPane();
		GridPane mainGrid = new GridPane();
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setHgap(20);
		mainGrid.setVgap(20);
		mainGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

		GridPane img1 = new GridPane();
		GridPane img2 = new GridPane();
		GridPane img3 = new GridPane();
		img1.setStyle("-fx-background-image: url('application/admin1.jpg')");
		img2.setStyle("-fx-background-image: url('application/Liberarian.jpg')");
		img3.setStyle("-fx-background-image: url('application/both.jpg')");
		img1.setPadding(new Insets(20));
		img1.setHgap(25);
		img1.setVgap(25);
		img1.setVisible(true);

		img1.setMinWidth(400);
		img1.setMaxWidth(243);

		mainGrid.add(img1, 0, 1);
		mainGrid.add(img2, 0, 1);
		Label adminPage = new Label("Admin Dashboard");
		adminPage.setFont(Font.font("optima", FontWeight.NORMAL, 30));
		Label librarianPage = new Label("Liberarian Dashboard");
		librarianPage.setFont(Font.font("optima", FontWeight.NORMAL, 30));
		Label bothPage = new Label("The Liberarian and Admin Dashboard");
		bothPage.setFont(Font.font("optima", FontWeight.NORMAL, 30));
		mainGrid.add(adminPage, 0, 0, 2, 1);
		mainGrid.add(librarianPage, 0, 0, 2, 1);
		mainGrid.add(bothPage, 0, 0, 2, 1);
		Button addLibraryMember = new Button("Add/Edit Member");
		addLibraryMember.setOnAction((e) -> {
			HomePage.hideAllWindows();
			HomePage.showMemeberPage(true);
		});
		grid.add(addLibraryMember, 0, 0);
		addLibraryMember.setStyle("-fx-background-color: #cc3300");
		addLibraryMember.setTextFill(Color.web("#ffffff"));
		addLibraryMember.setPrefHeight(130);
		addLibraryMember.setMinWidth(130);
		Button addNewBook = new Button("Add/Edit New Book");
		addNewBook.setOnAction((e) -> {
			HomePage.hideAllWindows();
			HomePage.showAddBookPage(true);
		});
		grid.add(addNewBook, 0, 1);
		addNewBook.setPrefHeight(130);
		addNewBook.setMinWidth(130);
		addNewBook.setStyle("-fx-background-color: #00ff00");
		addNewBook.setTextFill(Color.web("#ffffff"));

		Button bookRecord = new Button("Book Record");
		bookRecord.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HomePage.hideAllWindows();
				HomePage.showRecord();
			}
		});
		bookRecord.setPrefHeight(130);
		bookRecord.setMinWidth(130);
		bookRecord.setStyle("-fx-background-color: #0066cc");
		bookRecord.setTextFill(Color.web("#ffffff"));

		grid.add(bookRecord, 1, 0);
		addNewBook.setPrefHeight(130);
		addNewBook.setMinWidth(130);
		addNewBook.setStyle("-fx-background-color: #00ff00");
		addNewBook.setTextFill(Color.web("#ffffff"));

		Button checkRecords = new Button("Book Checkout/in");
		checkRecords.setOnAction((e) -> {
			HomePage.hideAllWindows();
			HomePage.destroyAllWindows();
			HomePage.showCheckOutEntries();
		});

		grid.add(checkRecords, 1, 1);
		checkRecords.setPrefHeight(130);
		checkRecords.setMinWidth(130);
		checkRecords.setStyle("-fx-background-color: #cccc00");
		checkRecords.setTextFill(Color.web("#ffffff"));

		Button exit = new Button("Exit");
		exit.setOnAction((e) -> {
			HomePage.hideAllWindows();
			HomePage.destroyAllWindows();

		});
		Button logOut = new Button("Log Out");
		logOut.setOnAction((e) -> {
			HomePage.hideAllWindows();
			HomePage.destroyAllWindows();
			HomePage.primStage().show();
		});
		mainGrid.add(exit, 0, 2);
		mainGrid.add(logOut, 1, 2);

		exit.setPrefHeight(40);
		exit.setMinWidth(100);
		logOut.setPrefHeight(40);
		logOut.setMinWidth(100);

		Button bookcopy = new Button("Add Book Copy");
		bookcopy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HomePage.hideAllWindows();
				HomePage.showAddACopyWindow();
			}
		});

		grid.add(bookcopy, 0, 2);
		bookcopy.setPrefHeight(130);
		bookcopy.setMinWidth(130);
		bookcopy.setStyle("-fx-background-color: #ffcc00");
		bookcopy.setTextFill(Color.web("#ffffff"));

		Button printbt = new Button("Print");
		printbt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HomePage.hideAllWindows();
				HomePage.showPrintPage();
			}
		});
		printbt.setPrefHeight(130);
		printbt.setMinWidth(130);
		printbt.setStyle("-fx-background-color: #6600cc");
		printbt.setTextFill(Color.web("#ffffff"));

		if (ControllerFactory.of().getCurrentUser().getAuthorization().equals(Auth.LIBRARIAN)) {
			addLibraryMember.setDisable(true);
			addLibraryMember.setVisible(false);
			addNewBook.setDisable(true);
			addNewBook.setVisible(false);
			printbt.setDisable(false);
			bookRecord.setDisable(false);
			checkRecords.setDisable(false);
			bookcopy.setDisable(true);
			bookcopy.setVisible(false);
			librarianPage.setVisible(true);
			adminPage.setVisible(false);
			bothPage.setVisible(false);
			img1.setVisible(false);
			img2.setVisible(true);
			img3.setVisible(false);
		} else if (ControllerFactory.of().getCurrentUser().getAuthorization().equals(Auth.ADMIN)) {
			addLibraryMember.setDisable(false);
			addNewBook.setDisable(false);
			printbt.setDisable(false);
			bookRecord.setDisable(false);
			checkRecords.setDisable(true);
			checkRecords.setVisible(false);
			bookcopy.setDisable(false);
			librarianPage.setVisible(false);
			adminPage.setVisible(true);
			bothPage.setVisible(false);
			img1.setVisible(true);
			img2.setVisible(false);
			img3.setVisible(false);
		} else if (ControllerFactory.of().getCurrentUser().getAuthorization().equals(Auth.BOTH)) {
			addLibraryMember.setDisable(false);
			addNewBook.setDisable(false);
			printbt.setDisable(false);
			bookRecord.setDisable(false);
			checkRecords.setDisable(false);
			bookcopy.setDisable(false);
			librarianPage.setVisible(false);
			adminPage.setVisible(false);
			bothPage.setVisible(true);
			img3.setVisible(true);
			img1.setVisible(false);
			img2.setVisible(true);

		}

		grid.add(printbt, 1, 2);
		printbt.setPrefHeight(130);
		printbt.setMinWidth(130);

		mainGrid.add(grid, 1, 1);
		Scene scene = new Scene(mainGrid, 800, 450);
		setScene(scene);
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInitialized(boolean val) {
		// TODO Auto-generated method stub

	}
}
