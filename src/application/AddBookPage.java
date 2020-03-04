package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import business.Author;
import business.Book;
import business.ControllerFactory;
import business.ControllerInterface;
import business.ValidationException;
import dataaccess.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddBookPage extends Stage implements LibWindow {

	private TextField isbnField;
	private TextField titleField;
	private TextField publisherField;
	private TextField legthofBorrow;
	private TextField numberofCopyField;
	private TextField authorField;
	private List<Author> authorList = new ArrayList<>();
	private HashMap<String, Book> booksMap = new HashMap<>();
	private TableView<Book> tbv;

	Button cancelbtn;
	Button updatebtn;
	Button submitbtn;
	TextField Isbntxt;
	Text noIsbn = new Text();;
	public static final AddBookPage INSTANCE = new AddBookPage();

	private AddBookPage() {
	}

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	private Text messageBar = new Text();

	public void clear() {
		messageBar.setText("");
	}

	@Override
	public void init() {
		String searchInfo = "Type Book ISBN No Id to search!";
		GridPane grid = new GridPane();
		// grid.setGridLinesVisible(true);
		GridPane mainGrid = new GridPane();
		mainGrid.setAlignment(Pos.TOP_LEFT);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		mainGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Add a New Book");
		scenetitle.setFont(Font.font("optima", FontWeight.NORMAL, 30));
		mainGrid.add(scenetitle, 0, 0, 2, 1);

		Label isbn = new Label("ISBN:");
		isbn.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		isbn.setMaxWidth(200.0);
		isbn.setPrefHeight(40.0);
		Label title = new Label("Title of the Book:");
		title.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		title.setMaxWidth(300.0);
		title.setPrefHeight(40.0);
		Label authors = new Label("Authors:");
		authors.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label publisher = new Label("Publisher");
		publisher.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label numberofCopies = new Label("Number of Copies");
		numberofCopies.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		Label lengthofBorrow = new Label("Length of Borrow:");
		lengthofBorrow.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));

		grid.add(isbn, 0, 1);
		grid.add(title, 0, 2);
		grid.add(authors, 0, 3);
		grid.add(publisher, 0, 4);
		grid.add(numberofCopies, 0, 5);
		grid.add(lengthofBorrow, 0, 6);

		isbnField = new TextField();
		isbnField.setPromptText("Enter ISBN");
		isbnField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		isbnField.setMaxWidth(200.0);
		isbnField.setPrefHeight(40.0);

		titleField = new TextField();
		titleField.setPromptText("Enter title of the Book");
		titleField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		titleField.setPrefWidth(300.0);
		titleField.setPrefHeight(40.0);
		Button buttonAuthor = new Button("Add Author info");
		buttonAuthor.setPrefWidth(130.0);
		buttonAuthor.setPrefHeight(40.0);
		authorField = new TextField();
		authorField.setEditable(false);
		publisherField = new TextField();

		publisherField.setPromptText("Enter Publisher");
		publisherField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		publisherField.setMaxWidth(200.0);
		publisherField.setPrefHeight(40.0);
		numberofCopyField = new TextField();
		numberofCopyField.setPromptText("Enter Number of Copies");
		numberofCopyField.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		numberofCopyField.setMaxWidth(100.0);
		numberofCopyField.setPrefHeight(40.0);
		legthofBorrow = new TextField();
		legthofBorrow.setPromptText("Enter length of Borrow");
		legthofBorrow.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		legthofBorrow.setMaxWidth(200.0);
		legthofBorrow.setPrefHeight(40.0);

		grid.add(isbnField, 1, 1);
		grid.add(titleField, 1, 2);
		grid.add(buttonAuthor, 1, 3);
		grid.add(publisherField, 1, 4);
		grid.add(numberofCopyField, 1, 5);
		grid.add(legthofBorrow, 1, 6);
		// grid.add(authorField, 3, 4);

		Button btn = new Button("Submit");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					ControllerFactory.of().addBook(isbnField.getText(), titleField.getText(), legthofBorrow.getText(),
							authorList, numberofCopyField.getText());
					messageBar.setFill(HomePage.Colors.green);
					messageBar.setText("Successed.");
				} catch (ValidationException e1) {
					messageBar.setFill(HomePage.Colors.red);
					messageBar.setText(e1.getMessage());
				}
			}
		});
		Button back = new Button("Back");

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HomePage.hideAllWindows();
				HomePage.showAdminWindow();
			}
		});

		submitbtn = new Button("Submit");
		Button logOut = new Button("< Back");
		cancelbtn = new Button("Cancel");
		updatebtn = new Button("Update");

		submitbtn.setPrefWidth(130.0);
		submitbtn.setPrefHeight(40.0);
		logOut.setPrefWidth(130.0);
		logOut.setPrefHeight(40.0);

		cancelbtn.setPrefWidth(130.0);
		cancelbtn.setPrefHeight(40.0);
		updatebtn.setPrefWidth(130.0);
		updatebtn.setPrefHeight(40.0);

		submitbtn.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		HBox exitBtn = new HBox(10);
		exitBtn.setAlignment(Pos.BOTTOM_LEFT);
		exitBtn.getChildren().add(submitbtn);
		grid.add(exitBtn, 1, 10);
		exitBtn.setPrefWidth(200.0);

		logOut.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));
		HBox logBtn = new HBox(10);
		exitBtn.setAlignment(Pos.BOTTOM_RIGHT);
		exitBtn.getChildren().add(updatebtn);

		logOut.setFont(Font.font("Gotham", FontWeight.NORMAL, 15));

		exitBtn.setAlignment(Pos.CENTER);
		exitBtn.getChildren().add(cancelbtn);

		grid.add(logOut, 0, 10);
		logBtn.setPrefWidth(200.0);

		submitbtn.setDisable(false);
		cancelbtn.setVisible(false);
		updatebtn.setDisable(true);
		logOut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HomePage.hideAllWindows();
				HomePage.showAdminWindow();
			}
		});
		submitbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					ControllerFactory.of().addBook(isbnField.getText(), titleField.getText(), legthofBorrow.getText(),
							authorList, numberofCopyField.getText());
					messageBar.setFill(HomePage.Colors.green);
					messageBar.setText("Successed.");

					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(
							"You have successfully added a book, you can check your Book Records for more info");
					alert.setContentText("Dou you want to add another Book or Go back to Home");
					refreshBookList();
					ButtonType addAgain = new ButtonType("Add Another");
					// ButtonType goBack = new ButtonType("Go Back");

					alert.getButtonTypes().setAll(addAgain);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == addAgain) {
						reset();
						init();
					} else {
						HomePage.hideAllWindows();
						HomePage.showAdminWindow();
					}
				} catch (ValidationException e1) {
					Alert alert = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.OK);
					alert.setTitle("Add a new library book");
					alert.setHeaderText("Validation error");
					alert.show();
					messageBar.setFill(HomePage.Colors.red);
					messageBar.setText(e1.getMessage());
					return;

				}
			}
		});

		updatebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					ControllerFactory.of().updateBook(isbnField.getText(), titleField.getText(),
							legthofBorrow.getText(), authorList, numberofCopyField.getText());
					messageBar.setFill(HomePage.Colors.green);
					messageBar.setText("Successed.");
					reset();
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(
							"You have successfully Updated a book, you can check your Book Records for more info");
					updatebtn.setDisable(true);
					submitbtn.setDisable(false);
					cancelbtn.setVisible(false);
					Isbntxt.setText("");
					noIsbn.setText(searchInfo);
					alert.show();
					refreshBookList();
					ButtonType goBack = new ButtonType("Go Back");

				} catch (ValidationException e1) {
					Alert alert = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.OK);
					alert.setTitle("Add a new library book");
					alert.setHeaderText("Validation error");
					alert.show();
					messageBar.setFill(HomePage.Colors.red);
					messageBar.setText(e1.getMessage());
					return;

				}
			}
		});

		buttonAuthor.setOnAction(r -> {
			HomePage.showAddAuthors(this.authorList);
		});

		mainGrid.add(grid, 0, 1);

		tbv = new TableView();
		tbv.setMaxWidth(400);
		tbv.setEditable(true);

		TableColumn<Book, String> colIsbn = new TableColumn<Book, String>("ISBN");
		TableColumn<Book, String> colTitle = new TableColumn<Book, String>("Title");
		TableColumn<Book, String> colAuthor = new TableColumn<Book, String>("Authors");
		TableColumn<Book, String> colCopy = new TableColumn<Book, String>("copies");
		TableColumn<Book, String> collenBorrow = new TableColumn<Book, String>("Max.Checkout Length");
		TableColumn<Book, String> colPublisher = new TableColumn<Book, String>("Publisher");

		tbv.getColumns().addAll(colIsbn, colTitle, colAuthor, colCopy, collenBorrow);
		tbv.setRowFactory(tv -> {

//			private TextField isbnField;
//			private TextField titleField;
//			private TextField publisherField;
//			private TextField legthofBorrow;
//			private TextField numberofCopyField;
//			private TextField authorField;

			TableRow<Book> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				submitbtn.setDisable(true);
				updatebtn.setDisable(false);
				cancelbtn.setVisible(true);
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					// cancelEdit();
					// editForm();
					Book rowData = row.getItem();
					isbnField.setText(rowData.getIsbn());
					titleField.setText(rowData.getTitle());
					legthofBorrow.setText(String.valueOf(rowData.getMaxCheckoutLength()));

					numberofCopyField.setText(String.valueOf(rowData.getNumCopies()));
					authorField.setText(rowData.getAuthorList());
				}
			});
			return row;
		});
		colIsbn.setCellValueFactory(data -> {
			Book rowValue = data.getValue();
			String cellValue = rowValue.getIsbn();
			return new ReadOnlyStringWrapper(cellValue);
		});
		colTitle.setCellValueFactory(data -> {
			Book rowValue = data.getValue();
			String cellValue = rowValue.getTitle();
			return new ReadOnlyStringWrapper(cellValue);
		});
		colAuthor.setCellValueFactory(data -> {
			Book rowValue = data.getValue();
			String cellValue = rowValue.getAuthorList();
			return new ReadOnlyStringWrapper(cellValue);
		});

		colCopy.setCellValueFactory(data -> {
			Book rowValue = data.getValue();
			String cellValue = String.valueOf(rowValue.getNumCopies());
			return new ReadOnlyStringWrapper(cellValue);
		});

		collenBorrow.setCellValueFactory(data -> {
			Book rowValue = data.getValue();
			String cellValue = String.valueOf(rowValue.getMaxCheckoutLength());
			return new ReadOnlyStringWrapper(cellValue);
		});

		this.noIsbn.setText(searchInfo);
		Isbntxt = new TextField();
		HBox hs = new HBox(10);
		hs.getChildren().add(Isbntxt);
		hs.getChildren().add(noIsbn);
//		tbv.getColumns().addAll(colIsbn, colTitle, colAuthor, colCopy, collenBorrow);
		Isbntxt.setMaxWidth(150);

		Isbntxt.textProperty().addListener((observable, oldValue, newValue) -> {
			if (tbv != null) {
				tbv.getItems().clear();
				Isbntxt.setText(newValue);
				this.booksMap.forEach((r, v) -> {
					if (v.getIsbn().equals(Isbntxt.getText().trim())) {

						this.Isbntxt.setText(newValue.trim());
						tbv.getItems().clear();
						String s = "Book exists with " + newValue.trim().toUpperCase() + " ISBN Number";
						this.noIsbn.setText(s);
						System.out.println("found");
						if (!v.equals(null)) {
							tbv.getItems().add(v);
						}

					}

				});
			}
			if (newValue.isEmpty()) {
				this.noIsbn.setText(searchInfo);
				refreshBookList();
			}
		});

		mainGrid.add(hs, 1, 0);
		mainGrid.add(tbv, 1, 1);
		User user = ControllerFactory.of().getCurrentUser();

		this.isInitialized = true;

		Scene scene = new Scene(mainGrid, 1000, 600);
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		setScene(scene);
		isInitialized = true;
		getAll();
		cancelEdit();
	}

	@Override
	public void setInitialized(boolean val) {
		isInitialized = val;

	}

	public void reset() {
		isbnField.setText("");
		titleField.setText("");
		publisherField.setText("");
		legthofBorrow.setText("");
		numberofCopyField.setText("");
		authorField.clear();
		messageBar.setText("");
	}

	public void setAuthorList(List<Author> authors) {
		this.authorList.clear();
		this.authorList.addAll(authors);
		String d = "";
		for (Author a : authors) {
			String t = a.getFirstName() + " " + a.getLastName();
			if ("".equals(d)) {
				d = d + t;
			} else {
				d = d + "," + t;
			}
		}
		this.authorField.setText(d);
	}

	public void getAll() {
		ControllerInterface ci = ControllerFactory.of();
		this.booksMap = ci.getBooksMap();
		refreshBookList();
	}

	public void refreshBookList() {
		if (tbv != null) {
			tbv.getItems().clear();
			this.booksMap.forEach((r, v) -> {
				tbv.getItems().add(v);
			});
		}
	}

	void cancelEdit() {

		cancelbtn.setOnAction((e) -> {
			reset();
			cancelbtn.setVisible(false);
			updatebtn.setDisable(true);
			submitbtn.setDisable(false);
			Isbntxt.setText("");
//			memeberId.setText("");
		});
	}

}
