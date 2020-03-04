package application;

import java.util.HashMap;

import business.ControllerFactory;
import business.ControllerInterface;
import business.LibraryMember;
import business.ValidationException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddMemberWindow extends Stage implements LibWindow {
	public static final AddMemberWindow INSTANCE = new AddMemberWindow();
	private HashMap<String, LibraryMember> membersMap;
	private TableView<LibraryMember> tbv;
	private String txtMemberId;
	private TextField txtFirstname;
	private TextField txtLastname;
	private TextField txtTelephone;
	private TextField txtStreet;
	private TextField txtCity;
	private ComboBox txtState;
	private TextField txtZip;
	private boolean isUpdate = false;
	private boolean isInitialized = false;
	private Button editbtn;
	private Button subimtbtn;
	private Button cancelBtn;
	private Button searchBtn;
	private TextField memeberId;
	private Text noMemberText = new Text();

	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void setInitialized(boolean val) {

	}

	@Override
	public void init() {
		// getAll();
		System.out.println("am here");
		GridPane mainGrid = new GridPane();
		GridPane grid = new GridPane();

		grid.setAlignment(Pos.TOP_LEFT);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);

		grid.setHgap(10);
		grid.setVgap(10);
		memeberId = new TextField();

		String searchInfo = "Type Member's Id to search!";
		this.noMemberText.setText(searchInfo);
		memeberId.textProperty().addListener((observable, oldValue, newValue) -> {
			if (tbv != null) {
				tbv.getItems().clear();
				memeberId.setText(newValue);
				this.membersMap.forEach((r, v) -> {
					if (v.getMemberId().equals(memeberId.getText().trim().toUpperCase())) {

						this.memeberId.setText(newValue.trim().toUpperCase());
						tbv.getItems().clear();
						String s = "Member exists with " + newValue.trim().toUpperCase();
						this.noMemberText.setText(s);
						System.out.println("found");
						if (!v.equals(null)) {
							tbv.getItems().add(v);
						}

					}
//					else {
//
//						String n = "Member does  exist not with " + memeberId.getText().trim().toUpperCase()
//								+ " member Id!";
//						this.noMemberText.setText(n);
//						System.out.println(n);
//						// System.out.println("textfield changed from " + oldValue + " to " + newValue);
//
//					}

				});
			}
			if (newValue.isEmpty()) {
				this.noMemberText.setText(searchInfo);
				refreshMemberList();
			}
		});

//		searchBtn.setOnAction((e) -> {
//			if (tbv != null) {
//				tbv.getItems().clear();
//				this.membersMap.forEach((r, v) -> {
//					if (v.getMemberId().equals(searchMemeber.getText().trim())) {
//						tbv.getItems().clear();
//						tbv.getItems().add(v);
//					}
//
//				});
//			}
//		});
		HBox hboxTop = new HBox(10);
		hboxTop.getChildren().add(memeberId);
		hboxTop.getChildren().add(noMemberText);

		mainGrid.add(hboxTop, 1, 0);
		mainGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Add Library Member");
		scenetitle.setFont(Font.font("optima", FontWeight.NORMAL, 30));
		mainGrid.add(scenetitle, 0, 0);
		
		Button back = new Button("Back");
//		back.setDisable(true);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HomePage.hideAllWindows();
				HomePage.showAdminWindow();
			}
		});
		grid.add(back, 0, 12);

		Label firstName = new Label("First Name:");
		firstName.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label lastName = new Label("Last Name:");
		lastName.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label phoneNumber = new Label("Phone Number:");
		phoneNumber.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label address = new Label("Address");
		address.setFont(Font.font("Gotham", FontWeight.NORMAL, 17));
		Label street = new Label("Street:");
		street.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label city = new Label("City:");
		city.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label state = new Label("State:");
		state.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label zip = new Label("Zip:");
		zip.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		grid.add(firstName, 0, 1);
		grid.add(lastName, 0, 2);
		grid.add(phoneNumber, 0, 3);
		grid.add(address, 0, 4);
		grid.add(street, 0, 5);
		grid.add(city, 0, 6);
		grid.add(state, 0, 7);
		grid.add(zip, 0, 8);

		txtFirstname = new TextField();
		txtFirstname.setPromptText("Enter First Name");
		txtFirstname.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		txtFirstname.setPrefWidth(300.0);
		txtFirstname.setPrefHeight(40.0);

		txtLastname = new TextField();
		txtLastname.setPromptText("Enter Last Name");
		txtLastname.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		txtLastname.setPrefWidth(300.0);
		txtLastname.setPrefHeight(40.0);

		txtTelephone = new TextField();
		txtTelephone.setPromptText("Enter Phone Name");
		txtTelephone.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		txtTelephone.setPrefWidth(300.0);
		txtTelephone.setPrefHeight(40.0);

		txtStreet = new TextField();
		txtStreet.setPromptText("Enter Street Name");
		txtStreet.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		txtStreet.setPrefWidth(300.0);
		txtStreet.setPrefHeight(40.0);

		txtCity = new TextField();
		txtCity.setPromptText("Enter City");
		txtCity.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		txtCity.setPrefWidth(300.0);
		txtCity.setPrefHeight(40.0);
		txtState = new ComboBox();
		txtState.getItems().addAll("Iowa", "Illinois", "NewYork", "Washington", "Colorado", "California", "Illinois",
				"Texas", "Florida", "Ohaio");
		txtState.setPromptText("Select State");
		txtState.setPrefWidth(300.0);
		txtState.setPrefHeight(40.0);
		txtZip = new TextField();
		txtZip.setPromptText("Enter Zip Code");
		txtZip.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		txtZip.setPrefWidth(300.0);
		txtZip.setPrefHeight(40.0);

		grid.add(txtFirstname, 1, 1);

		grid.add(txtLastname, 1, 2);
		grid.add(txtTelephone, 1, 3);
		grid.add(txtStreet, 1, 5);
		grid.add(txtCity, 1, 6);
		grid.add(txtState, 1, 7);
		grid.add(txtZip, 1, 8);

		subimtbtn = new Button("Submit");
		editbtn = new Button("Update");
		cancelBtn = new Button("Cancle");

		cancelBtn.setDisable(true);
		editbtn.setDisable(true);
		subimtbtn.setDisable(false);

		HBox hbxBtn = new HBox(10);

		hbxBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbxBtn.getChildren().add(subimtbtn);
		hbxBtn.getChildren().add(editbtn);
		hbxBtn.getChildren().add(cancelBtn);
		grid.add(hbxBtn, 1, 10);
		hbxBtn.setPrefWidth(200.0);

		// tableview
		tbv = new TableView();
		tbv.setMaxWidth(600);
		tbv.setEditable(true);

		TableColumn<LibraryMember, String> colMemberId = new TableColumn<LibraryMember, String>("Member Id");
		TableColumn<LibraryMember, String> colfirstName = new TableColumn<LibraryMember, String>("First Name");
		// afirstName.setMaxWidth(0.4);
		// TableColumn<LibraryMember, String> alastName = new TableColumn<LibraryMember,
		// String>("Last Name");
		TableColumn<LibraryMember, String> telephone = new TableColumn<LibraryMember, String>("Telephone");
		TableColumn<LibraryMember, String> first = new TableColumn<LibraryMember, String>("First Name");
		TableColumn<LibraryMember, String> colLastName = new TableColumn<LibraryMember, String>("Last Name");
		TableColumn<LibraryMember, String> colAddress = new TableColumn<LibraryMember, String>("Address");
		colfirstName.setCellValueFactory(new PropertyValueFactory<>("Memar"));
		tbv.getColumns().addAll(colMemberId, colfirstName, colLastName, telephone, colAddress);

		// colfirstName.prefWidthProperty().bind(tbv.widthProperty().multiply(0.9));
		tbv.prefWidthProperty().bind(tbv.widthProperty().multiply(4));
		colLastName.prefWidthProperty().bind(tbv.widthProperty().multiply(0.16));
		telephone.prefWidthProperty().bind(tbv.widthProperty().multiply(0.23));
		first.prefWidthProperty().bind(tbv.widthProperty().multiply(0.22));
		// nameCol.prefWidthProperty().bind(tbv.widthProperty().multiply(0.19));

		tbv.setRowFactory(tv -> {

			TableRow<LibraryMember> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					cancelEdit();
					editForm();
					LibraryMember rowData = row.getItem();
					txtMemberId = rowData.getMemberId();
					txtFirstname.setText(rowData.getFirstName());
					txtLastname.setText(rowData.getLastName());
					txtTelephone.setText(rowData.getTelephone());
					txtCity.setText(rowData.getAddress().getCity());
					txtState.setValue(rowData.getAddress().getState());
					txtStreet.setText(rowData.getAddress().getStreet());
					txtZip.setText(rowData.getAddress().getZip());
				}
			});
			return row;
		});

		colMemberId.setCellValueFactory(data -> {
			LibraryMember rowValue = data.getValue();
			String cellValue = rowValue.getMemberId();
			return new ReadOnlyStringWrapper(cellValue);
		});
		colfirstName.setCellValueFactory(data -> {
			LibraryMember rowValue = data.getValue();
			String cellValue = rowValue.getFirstName();
			return new ReadOnlyStringWrapper(cellValue);
		});
		colLastName.setCellValueFactory(data -> {
			LibraryMember rowValue = data.getValue();
			String cellValue = rowValue.getLastName();
			return new ReadOnlyStringWrapper(cellValue);
		});
		telephone.setCellValueFactory(data -> {
			LibraryMember rowValue = data.getValue();
			String cellValue = rowValue.getTelephone();
			return new ReadOnlyStringWrapper(cellValue);
		});
		colAddress.setCellValueFactory(data -> {
			LibraryMember rowValue = data.getValue();

			String cellValue = rowValue.getAddress().getCity() + "," + rowValue.getAddress().getState() + ","
					+ rowValue.getAddress().getStreet() + "," + rowValue.getAddress().getStreet();
			return new ReadOnlyStringWrapper(cellValue);
		});
		mainGrid.add(grid, 0, 1);
		mainGrid.add(tbv, 1, 1);
		updateMember();
		// Button handler

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);
		// Context Menu for error messages

		Scene scene = new Scene(mainGrid, 1050, 600);
		// scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);

	}

	void updateMember() {
		editbtn.setOnAction((e) -> {
			ControllerInterface ci = ControllerFactory.of();
			System.out.println("------- " + txtMemberId);
			try {
				ci.validateUpdateMemberForm(txtMemberId, txtFirstname.getText().trim(), txtLastname.getText().trim(),
						txtTelephone.getText().trim(), txtStreet.getText().trim(), txtCity.getText().trim(),
						txtState.getValue().toString().trim(), txtZip.getText().trim());
			} catch (ValidationException ex) {
				Alert alert = new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK);
				alert.setTitle("Update library member");
				alert.setHeaderText("Validation error");
				alert.show();
				return;
			}
			String memberId = txtMemberId;
			ci.addMember(txtMemberId, txtFirstname.getText().trim(), txtLastname.getText().trim(),
					txtTelephone.getText().trim(), txtStreet.getText().trim(), txtCity.getText().trim(),
					txtState.getValue().toString().trim(), txtZip.getText().trim());

			updateMembersMap(ci.getMembersMap());
			refreshMemberList();

			resetFields();
			Alert alert = new Alert(AlertType.INFORMATION,
					"The library member with member ID '" + memberId + "' has been updated successfully!",
					ButtonType.OK);
			alert.setTitle("Add a new library member");
			alert.setHeaderText(" library member was updated");
			alert.show();
			// hideUpdateBtn();
		});
	}

	void validateFieldsMember() {

		final ContextMenu fnameValidator = new ContextMenu();
		fnameValidator.setAutoHide(true);
		final ContextMenu lnameValidator = new ContextMenu();
		lnameValidator.setAutoHide(true);
		final ContextMenu passValidator = new ContextMenu();
		passValidator.setAutoHide(true);
		final ContextMenu phoneValidator = new ContextMenu();
		phoneValidator.setAutoHide(true);
		final ContextMenu streetValidator = new ContextMenu();
		streetValidator.setAutoHide(true);
		final ContextMenu cityValidator = new ContextMenu();
		cityValidator.setAutoHide(true);
		final ContextMenu zipValidator = new ContextMenu();
		zipValidator.setAutoHide(true);
		subimtbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				System.out.println("this is first name " + txtFirstname.getText());
				if (!txtFirstname.getText().matches("[A-Za-z\\s]+")) {
					fnameValidator.getItems().clear();
					fnameValidator.getItems().add(new MenuItem("Please Write your Name correctly"));
					fnameValidator.show(txtFirstname, Side.RIGHT, 10, 0);
				}

				else if (!txtLastname.getText().matches("[A-Za-z\\s]+")) {
					lnameValidator.getItems().clear();
					lnameValidator.getItems().add(new MenuItem("Write your Last Name correctly"));
					lnameValidator.show(txtLastname, Side.RIGHT, 10, 0);
				} else if (!txtTelephone.getText().matches("[0-9]+")) {
					phoneValidator.getItems().clear();
					phoneValidator.getItems().add(new MenuItem("Your phone number is not valid"));
					phoneValidator.show(txtTelephone, Side.RIGHT, 10, 0);
				} else if (!txtStreet.getText().matches("[A-Za-z\\s]+")) {
					streetValidator.getItems().clear();
					streetValidator.getItems().add(new MenuItem("Write your Last Name"));
					streetValidator.show(txtStreet, Side.RIGHT, 10, 0);
				} else if (!txtCity.getText().matches("[A-Za-z\\s]+")) {
					cityValidator.getItems().clear();
					cityValidator.getItems().add(new MenuItem("Write your City"));
					cityValidator.show(txtCity, Side.RIGHT, 10, 0);
				} else if (!txtZip.getText().matches("[0-9]+")) {
					zipValidator.getItems().clear();
					zipValidator.getItems().add(new MenuItem("Write your zip code"));
					zipValidator.show(txtZip, Side.RIGHT, 10, 0);
				} else {

					ControllerInterface ci = ControllerFactory.of();
					txtMemberId = ci.generateMemberId();
					System.out.println("txtMemberId-" + txtMemberId);
					System.out.println("txtFirstname-" + txtFirstname.getText().trim());
					System.out.println("txtLastname-" + txtLastname.getText().trim());
					System.out.println("txtTelephone-" + txtTelephone.getText().trim());
					System.out.println("txtStreet-" + txtStreet.getText().trim());
					System.out.println("txtCity-" + txtCity.getText().trim());
					System.out.println("txtState-" + txtState.getValue().toString().trim());
					System.out.println("txtZip-" + txtZip.getText().trim());
					try {
						ci.validateAddMemberForm(txtMemberId, txtFirstname.getText().trim(),
								txtLastname.getText().trim(), txtTelephone.getText().trim(), txtStreet.getText().trim(),
								txtCity.getText().trim(), txtState.getValue().toString().trim(),
								txtZip.getText().trim());
					} catch (ValidationException ex) {
						Alert alert = new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK);
						alert.setTitle("Add a new library book");
						alert.setHeaderText("Validation error");
						alert.show();
						return;
					}
					String memberId = txtMemberId;
					ci.addMember(txtMemberId, txtFirstname.getText().trim(), txtLastname.getText().trim(),
							txtTelephone.getText().trim(), txtStreet.getText().trim(), txtCity.getText().trim(),
							txtState.getValue().toString().trim(), txtZip.getText().trim());

					updateMembersMap(ci.getMembersMap());
					refreshMemberList();
					
					

					resetFields();
					Alert alert = new Alert(AlertType.INFORMATION,
							"The new library member with member ID '" + memberId + "' was added successfully.",
							ButtonType.OK);
					alert.setTitle("Add a new library member");
					alert.setHeaderText("New library member was added");
					alert.show();
				}

			}
		});
	}

	String genrateRandom() {
		int rand = this.membersMap.size() + 1;
		return "LIB-" + rand;
	}

	public void getAll() {
		ControllerInterface ci = ControllerFactory.of();
		this.membersMap = ci.getMembersMap();

		refreshMemberList();
	}

	public void updateMembersMap(HashMap<String, LibraryMember> membersMap) {
		this.membersMap = membersMap;
	}

	public void refreshMemberList() {
		if (tbv != null) {
			tbv.getItems().clear();
			this.membersMap.forEach((r, v) -> {
				tbv.getItems().add(v);
			});
		}
	}

	public void resetFields() {
		txtMemberId = "";
		txtFirstname.setText("");
		txtLastname.setText("");
		txtTelephone.setText("");
		txtStreet.setText("");
		txtCity.setText("");
		txtState.setValue(null);
		txtZip.setText("");
	}

	void cancelEdit() {

		cancelBtn.setOnAction((e) -> {
			resetFields();
			cancelBtn.setDisable(true);
			editbtn.setDisable(true);
			subimtbtn.setDisable(false);
			memeberId.setText("");
		});
	}

	void editForm() {
		cancelBtn.setDisable(false);
		editbtn.setDisable(false);
		subimtbtn.setDisable(true);
	}
}
