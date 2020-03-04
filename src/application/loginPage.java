package application;

import business.ControllerFactory;
import business.LoginException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class loginPage extends Stage implements LibWindow {

	public static final loginPage INSTANCE = new loginPage();

	@Override
	public void init() {

		GridPane grid = new GridPane();
		GridPane mainGrid = new GridPane();
		// grid.setGridLinesVisible(true);
		// mainGrid.setGridLinesVisible(true);
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setHgap(20);
		mainGrid.setVgap(20);
		grid.setVgap(20);
		grid.setHgap(20);
		GridPane img1 = new GridPane();
	//	img1.setStyle("-fx-background-image: url('application/user-icon.png')");
		img1.setPadding(new Insets(20));
		img1.setHgap(25);
		img1.setVgap(15);

		img1.setMinWidth(300);
		img1.setMaxWidth(300);

		mainGrid.add(img1, 0, 1);

		// mainGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Login");
		scenetitle.setFont(Font.font("optima", FontWeight.NORMAL, 30));
		mainGrid.add(scenetitle, 0, 0, 2, 1);
		Label userName = new Label(" User Name:");
		userName.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label passWord = new Label("Password:");
		passWord.setFont(Font.font("Gotham", FontWeight.NORMAL, 17));

		grid.add(userName, 0, 1);
		grid.add(passWord, 0, 2);

		TextField userNameField = new TextField();
		userNameField.setPromptText("Enter Username");
		userNameField.setFont(Font.font("optima", FontWeight.NORMAL, 17));
		userNameField.setMaxWidth(250.0);
		userNameField.setPrefHeight(40.0);

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Enter Passowrd");
		passwordField.setFont(Font.font("optima", FontWeight.NORMAL, 17));
		passwordField.setPrefWidth(200.0);
		passwordField.setPrefHeight(40.0);

		grid.add(userNameField, 1, 1);
		grid.add(passwordField, 1, 2);

		Button submitBtn = new Button("Login");
		Button home = new Button("Home");
		home.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HomePage.hideAllWindows();
				HomePage.primStage().show();
			}
		});

		submitBtn.setMaxWidth(70.0);
		submitBtn.setPrefHeight(35.0);
		home.setMaxWidth(70.0);
		home.setPrefHeight(35.0);

		grid.add(submitBtn, 1, 3);
		grid.add(home, 0, 3);
		mainGrid.add(grid, 1, 1);
		// btn.setMaxWidth(500.0);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);
		// Context Menu for error messages
		final ContextMenu usernameValidator = new ContextMenu();
		usernameValidator.setAutoHide(true);
		final ContextMenu passValidator = new ContextMenu();
		passValidator.setAutoHide(true);

		// Action on button press
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// Clearing message if any
				actiontarget.setText("");

				// Checking if the userTextField is empty
				if (userNameField.getText().equals("")) {
					usernameValidator.getItems().clear();
					usernameValidator.getItems().add(new MenuItem("Please enter username"));
					usernameValidator.show(userNameField, Side.RIGHT, 10, 0);
				}
				// Checking if the pwBox is empty
				if (passwordField.getText().equals("")) {
					passValidator.getItems().clear();
					passValidator.getItems().add(new MenuItem("Please enter Password"));
					passValidator.show(passwordField, Side.RIGHT, 10, 0);
				}
				// If both of the above textFields have values
				if (!passwordField.getText().equals("") && !userNameField.getText().equals("")) {
					actiontarget.setFill(Color.GREEN);
					actiontarget.setText("Welcome");
				}
				try {
					ControllerFactory.of().login(userNameField.getText().trim(), passwordField.getText().trim());
					messageBar.setFill(HomePage.Colors.green);
					messageBar.setText("Login successful");
					HomePage.showAdminWindow();

				} catch (LoginException ex) {
					messageBar.setFill(HomePage.Colors.red);
					messageBar.setText("Error! " + ex.getMessage());
				}
			}
		});

		Scene scene = new Scene(mainGrid, 800, 400);
		// scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);
	}

	private Text messageBar = new Text();

	public void clear() {
		messageBar.setText("");
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
