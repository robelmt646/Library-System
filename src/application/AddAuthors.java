package application;

import java.util.ArrayList;
import java.util.List;

import business.Author;
import business.ControllerFactory;
import business.ValidationException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.WindowUtils;

public class AddAuthors extends Stage implements LibWindow {

	public static final AddAuthors INSTANCE = new AddAuthors();
	private TableView<Author> tbv;

	private TextField firstNameTextField;
	private TextField lastNameTextField;
	private TextField phoneNumberTextField;
	private TextField bioTextField;
	private TextField streetTextField;
	private TextField cityTextField;
	private TextField stateTextField;
	private TextField zipTextField;

	private boolean isInitialized = false;

	private List<Author> authors = new ArrayList<>();
	private Text messageBar = new Text();

	public void clear() {
		messageBar.setText("");
	}

	private AddAuthors() {

	}

	@Override
	public void init() {

		GridPane grid = new GridPane();
		GridPane mainGrid = new GridPane();

		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		grid.setHgap(10);
		grid.setVgap(10);

		mainGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Add Authors");
		scenetitle.setFont(Font.font("optima", FontWeight.NORMAL, 30));
		mainGrid.add(scenetitle, 0, 0, 2, 1);
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
		Label bio = new Label("Short Bio");
		bio.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		grid.add(firstName, 0, 1);
		grid.add(lastName, 0, 2);
		grid.add(phoneNumber, 0, 3);
		grid.add(address, 0, 4);
		grid.add(street, 0, 5);
		grid.add(city, 0, 6);
		grid.add(state, 0, 7);
		grid.add(zip, 0, 8);
		grid.add(bio, 0, 9);
		firstNameTextField = new TextField();
		firstNameTextField.setPromptText("Enter First Name");
		firstNameTextField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		firstNameTextField.setPrefWidth(300.0);
		firstNameTextField.setPrefHeight(40.0);

		lastNameTextField = new TextField();
		lastNameTextField.setPromptText("Enter Last Name");
		lastNameTextField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		lastNameTextField.setPrefWidth(300.0);
		lastNameTextField.setPrefHeight(40.0);
		phoneNumberTextField = new TextField();
		phoneNumberTextField.setPromptText("Enter Phone Name");
		phoneNumberTextField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		phoneNumberTextField.setPrefWidth(300.0);
		phoneNumberTextField.setPrefHeight(40.0);
		streetTextField = new TextField();
		streetTextField.setPromptText("Enter Street Name");
		streetTextField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		streetTextField.setPrefWidth(300.0);
		streetTextField.setPrefHeight(40.0);
		cityTextField = new TextField();
		cityTextField.setPromptText("Enter City");
		cityTextField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		cityTextField.setPrefWidth(300.0);
		cityTextField.setPrefHeight(40.0);
		stateTextField = new TextField();

		stateTextField.setPromptText("Select State");
		stateTextField.setPrefWidth(300.0);
		stateTextField.setPrefHeight(40.0);
		zipTextField = new TextField();
		zipTextField.setPromptText("Enter Zip Code");
		zipTextField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		zipTextField.setMaxWidth(130.0);
		zipTextField.setPrefHeight(40.0);

		bioTextField = new TextField();
		bioTextField.setPromptText("Write Short Biography of the Author");
		bioTextField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		bioTextField.setMaxWidth(420.0);
		bioTextField.setPrefHeight(600.0);

		grid.add(firstNameTextField, 1, 1);
		grid.add(lastNameTextField, 1, 2);
		grid.add(phoneNumberTextField, 1, 3);
		grid.add(streetTextField, 1, 5);
		grid.add(cityTextField, 1, 6);
		grid.add(stateTextField, 1, 7);
		grid.add(zipTextField, 1, 8);
		grid.add(bioTextField, 0, 10, 2, 1);

		TableView table = new TableView();
		table.setMaxWidth(400);
		table.setEditable(true);

		// table columns

		TableColumn afirstName = new TableColumn("First Name");
		TableColumn alastName = new TableColumn("Last Name");
		TableColumn telephone = new TableColumn("Email");
		TableColumn firstNameCol = new TableColumn("First Name");
		TableColumn lastNameCol = new TableColumn("Last Name");
		TableColumn emailCol = new TableColumn("Email");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("Memar"));
		table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

		Button btn = new Button("Add Author");
		btn.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		HBox hbxBtn = new HBox(10);
		HBox hbxBtn2 = new HBox(10);
		hbxBtn.setAlignment(Pos.BOTTOM_LEFT);

		hbxBtn.setPrefWidth(200.0);

		Button back = new Button("back");

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HomePage.showAddBookPage(authors);
			}
		});
		hbxBtn2.getChildren().add(back);
		mainGrid.add(hbxBtn2, 0, 3);

		Button addBtn = new Button("Add/Update Author");
		addBtn.setOnAction(r -> {
			try {
				Author author = ControllerFactory.of().createAuthor(firstNameTextField.getText(),
						lastNameTextField.getText(), phoneNumberTextField.getText(), bioTextField.getText(),
						streetTextField.getText(), cityTextField.getText(), stateTextField.getText(),
						zipTextField.getText());

				List<Author> temp = new ArrayList<>();
				temp.addAll(this.authors);

				for (Author t : temp) {
					if (t.getFirstName().equals(author.getFirstName())
							&& t.getLastName().equals(author.getLastName())) {
						// replace
						this.authors.remove(t);
					}
				}
				this.authors.add(author);
				messageBar.setFill(HomePage.Colors.red);
				messageBar.setText("Successed.");
				refreshTableView();
			} catch (ValidationException e1) {
				messageBar.setFill(HomePage.Colors.red);
				messageBar.setText(e1.getMessage());
			}
		});
		hbxBtn.getChildren().add(addBtn);
		mainGrid.add(hbxBtn, 2, 3);

		mainGrid.add(grid, 0, 1);
		tbv = WindowUtils.createAuthorListTableView();
		mainGrid.add(tbv, 1, 1);

		Scene scene = new Scene(mainGrid, 1000, 600);
		// scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);
		isInitialized = true;
	}

	private void refreshTableView() {
		this.tbv.getItems().clear();
		this.authors.forEach(r -> this.tbv.getItems().add(r));
	}

	public void setData(List<Author> authors) {
		this.authors.clear();
		this.authors.addAll(authors);
		this.tbv.getItems().clear();
		this.authors.forEach(r -> this.tbv.getItems().add(r));
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void setInitialized(boolean val) {
		// TODO Auto-generated method stub

	}

	public void reset() {

		firstNameTextField.setText("");
		lastNameTextField.setText("");
		phoneNumberTextField.setText("");
		bioTextField.setText("");
		streetTextField.setText("");
		cityTextField.setText("");
		stateTextField.setText("");
		zipTextField.setText("");
		authors.clear();
		messageBar.setText("");
	}

}
