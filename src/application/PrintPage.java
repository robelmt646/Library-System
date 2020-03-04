package application;

import business.CheckOutRecordEntry;

import business.ControllerFactory;
import business.LibraryMember;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.WindowUtils;


public class PrintPage extends Stage implements LibWindow {

	public static final  PrintPage INSTANCE = new  PrintPage();

	private boolean isInitialized = false;
	private TableView<CheckOutRecordEntry> tbv;
	private TextField txtMemberId;
	private Label errorMessage;
	private LibraryMember member;

	private PrintPage() {
	}

	@Override
	public void init() {
		try {

			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER_RIGHT);
			grid.setHgap(20);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));

			ColumnConstraints col1 = new ColumnConstraints();
			col1.setPercentWidth(15);
			ColumnConstraints col2 = new ColumnConstraints();
			col2.setPercentWidth(35);
			ColumnConstraints col3 = new ColumnConstraints();
			col3.setPercentWidth(15);
			ColumnConstraints col4 = new ColumnConstraints();
			col4.setPercentWidth(35);
			grid.getColumnConstraints().addAll(col1, col2, col3, col4);
			grid.setPrefSize(700, 500);

			this.tbv = WindowUtils.createCheckOutRecordEntryListTableView();
			Label lbMemberId = new Label("Member ID:");
			txtMemberId = new TextField();

			grid.add(WindowUtils.createSceneText("Print check out record"), 0, 0, 4, 1);
			grid.add(lbMemberId, 0, 1);
			grid.add(txtMemberId, 1, 1);
			grid.add(this.tbv, 0, 3, 4, 4);

			HBox bBox = new HBox();
			grid.add(bBox, 2, 1, 2, 1);

			Button checkBtn = new Button("Search Checkout Record");
			checkBtn.setOnAction(e -> {
				this.errorMessage.setText("");

				LibraryMember member = ControllerFactory.of().getLibraryMember(this.txtMemberId.getText());
				if (member != null) {
					loadCheckOutRecordTableView(member);
					this.member = member;
				} else {
					reset();
					outputErrorMessage("Member id is not exist.");
				}
			});

			Button printBtn = new Button("Print checkout record");
			printBtn.setOnAction(e -> {
				CheckOutRecordEntry entry = this.tbv.getSelectionModel().getSelectedItem();
				if (entry == null) {
					outputErrorMessage("please select a check out record.");
				} else {
					ControllerFactory.of().printCheckOutRecord(member, entry);
					outputSuccessMessage("The record has been outputted to console.");
				}
			});
			bBox.setAlignment(Pos.CENTER_RIGHT);
			bBox.getChildren().add(checkBtn);
			bBox.setSpacing(20);

			HBox pBox = new HBox();
			pBox.setAlignment(Pos.CENTER_RIGHT);
			pBox.getChildren().add(printBtn);
			grid.add(pBox, 2, 7, 2, 1);

			Button backBtn = new Button("Back");
			backBtn.setMinSize(150, 20);
			backBtn.setOnAction(e -> {
				HomePage.showAdminWindow();
			});
			grid.add(backBtn, 0, 7, 2, 1);

			errorMessage = new Label();
			grid.add(errorMessage, 0, 2, 2, 1);

			Scene scene = new Scene(grid);
			//scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
			setScene(scene);

			this.isInitialized = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
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
