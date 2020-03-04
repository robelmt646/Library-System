package application;

import business.Book;


import business.CheckOutRecordEntry;
import business.ControllerFactory;
import business.LibraryMember;
import business.ValidationException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import utils.WindowUtils;


public class CheckOutEntries extends Stage implements LibWindow {
	public static final CheckOutEntries INSTANCE = new CheckOutEntries();
	private boolean isInitialized = false;
	private TextField txtmemeberId;
	private TextField txtIsbnId;
	private Label errorMessage;
	private TableView<CheckOutRecordEntry> tbv;

	

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	@Override
	public void init() {
		errorMessage = new Label();
		GridPane grid = new GridPane();
		GridPane mainGrid = new GridPane();
		HBox hbox = new HBox(50);

		// mainGrid.setGridLinesVisible(true);
		// mainGrid.setAlignment(Pos.TOP_LEFT);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		grid.setHgap(10);
		grid.setVgap(10);

		mainGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Checkout Records");
		hbox.getChildren().add(scenetitle);
		hbox.getChildren().add(errorMessage);
		errorMessage.setFont(Font.font("optima", FontWeight.NORMAL, 20));
		scenetitle.setFont(Font.font("optima", FontWeight.NORMAL, 20));
		mainGrid.add(hbox, 0, 0);
		// mainGrid.add(errorMessage, 1, 0);

		Label memeberIdLable = new Label("Library Member ID:");
		memeberIdLable.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label isbnoLable = new Label("ISBN Number:");
		isbnoLable.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));

		Button recBtn = new Button("Query Records");
		recBtn.setOnAction(e -> {
			this.errorMessage.setText("");

			LibraryMember member = ControllerFactory.of().getLibraryMember(txtmemeberId.getText());
			if (member != null) {
				System.out.println("member id --" + member.getMemberId());
				loadCheckOutRecordTableView(member);
				// loadCheckOutRecordTableView(member);
				outputSuccessMessage("Successed");
			} else {
				reset();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText(null);
				alert.setContentText("Member ID does not Exist!");

				alert.showAndWait();
			}
		});
		recBtn.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		recBtn.setPrefHeight(40.0);

		
		grid.add(memeberIdLable, 0, 1);
		grid.add(isbnoLable, 2, 1);

		txtmemeberId = new TextField();
		txtmemeberId.setPromptText("Enter Member ID");
		txtmemeberId.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		txtmemeberId.setPrefWidth(200.0);
		txtmemeberId.setPrefHeight(40.0);

		txtIsbnId = new TextField();
		txtIsbnId.setPromptText("Enter ISBN Number");
		txtIsbnId.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		txtIsbnId.setPrefWidth(150.0);
		txtIsbnId.setPrefHeight(40.0);

		grid.add(txtmemeberId, 1, 1);
		grid.add(txtIsbnId, 3, 1);
		grid.add(recBtn, 4, 1);
		

		this.tbv = WindowUtils.createCheckOutRecordEntryListTableView();
		tbv.setPrefHeight(400);


		mainGrid.add(grid, 0, 1);
		mainGrid.add(tbv, 0, 2);
		
		Button btnBack = new Button("Back");
		
		btnBack.setAlignment(Pos.BOTTOM_LEFT);
		btnBack.setOnAction((e) -> {
			HomePage.showAdminWindow();
		});
		
		Button checkOut = new Button("CheckOut Book");
		checkOut.setPrefHeight(40.0);
		checkOut.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		checkOut.setOnAction(e -> {
			try {
				this.errorMessage.setText("");
				LibraryMember member = ControllerFactory.of().getLibraryMember(txtmemeberId.getText());
				if (member != null) {
					loadCheckOutRecordTableView(member);
				} else {
					reset();
				}
				ControllerFactory.of().checkOut(txtmemeberId.getText(), txtIsbnId.getText());
				loadCheckOutRecordTableView(member);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("You have successfully checked out a record!");

				alert.showAndWait();
				loadCheckOutRecordTableView(member);
			} catch (ValidationException e1) {
				outputErrorMessage(e1.getMessage());
			}
		});
		grid.add(checkOut, 5, 1);
		
		Button checkIn = new Button("CheckIN Book");
		checkIn.setPrefHeight(40.0);
		checkIn.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		checkIn.setOnAction(e -> {
			try {
				this.errorMessage.setText("");
				LibraryMember member = ControllerFactory.of().getLibraryMember(txtmemeberId.getText());
				if (member != null) {
					loadCheckOutRecordTableView(member);
				} else {
					reset();
				}
				ControllerFactory.of().checkIN(txtmemeberId.getText(), txtIsbnId.getText());
				//loadCheckOutRecordTableView(member);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("You have successfully Returned a Book");

				alert.showAndWait();
				loadCheckOutRecordTableView(member);
				
				
			} catch (ValidationException e1) {
				//outputErrorMessage(e1.getMessage());
			}
		});
		grid.add(checkIn, 5, 0);
		
		
		mainGrid.add(btnBack, 0, 3);

		Scene scene = new Scene(mainGrid, 900, 500);
		setScene(scene);
	}

	public void reset() {
		this.errorMessage.setText("");
		this.tbv.getItems().clear();
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void setInitialized(boolean val) {
		isInitialized = val;
	}

	private void loadCheckOutRecordTableView(LibraryMember member) {
		this.tbv.getItems().clear();
		member.getCheckOutRecord().getEntryList().forEach(r -> this.tbv.getItems().add(r));
	}
	

	private void outputErrorMessage(String text) {
		errorMessage.setTextFill(HomePage.Colors.red);
		errorMessage.setText(text);
	}

	private void outputSuccessMessage(String text) {
		errorMessage.setTextFill(HomePage.Colors.green);
		errorMessage.setText(text);
	}
}
