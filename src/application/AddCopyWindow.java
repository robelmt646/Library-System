package application;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import business.Author;
import business.Book;
import business.BookCopy;
import business.ControllerFactory;
import business.ControllerInterface;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.WindowUtils;

public class AddCopyWindow extends Stage implements LibWindow {

	public static final AddCopyWindow INSTANCE = new AddCopyWindow();

	private boolean isInitialized = false;
	private HashMap<String, Book> booksMap;
	private TableView<Book> tbv;
	private TextField txtIsbn;

	public AddCopyWindow() {
	}

	public void setData(HashMap<String, Book> booksMap) {
		this.booksMap = booksMap;
	}

	@Override
	public void init() {
		try {
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.TOP_CENTER);
			grid.setHgap(20);
			grid.setVgap(20);
			grid.setPadding(new Insets(25, 25, 25, 25));
			grid.getStyleClass().add(getClass().getSimpleName());

			VBox hbLeft = new VBox(10);
			VBox hbRight = new VBox();
			VBox hbBottom = new VBox(10);
			hbLeft.setAlignment(Pos.TOP_LEFT);
			hbBottom.setAlignment(Pos.BOTTOM_CENTER);

			tbv = new TableView<>();
			hbRight.getChildren().add(tbv);
			initBookListView(tbv);

			Text scenetitle = WindowUtils.createSceneText("Add a Book Copy");
			Text selecttitle = WindowUtils.createSceneText("Select the book", "selectTitle");
			Text lefttitle = WindowUtils.createSceneText("Search a book", "leftTitle");

			grid.add(scenetitle, 0, 0, 2, 1);
			grid.add(lefttitle, 0, 1);
			grid.add(selecttitle, 1, 1);
			grid.add(hbLeft, 0, 2);
			grid.add(hbRight, 1, 2);
			grid.add(hbBottom, 0, 3, 2, 1);

			Scene scene = new Scene(grid, 725, 595);

			setScene(scene);
			setResizable(false);
			sizeToScene();

			GridPane gridIsbn = new GridPane();
			gridIsbn.setHgap(10);
			Label lblIsbn = new Label("ISBN: ");
			gridIsbn.add(lblIsbn, 0, 0);
			txtIsbn = new TextField();
			txtIsbn.setMaxWidth(100);
			gridIsbn.add(txtIsbn, 1, 0);
			hbLeft.getChildren().add(gridIsbn);

			GridPane gridSearchBtn = new GridPane();
			gridSearchBtn.setHgap(10);
			Button searchBtn = new Button("Search");
			searchBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					searchForBook(txtIsbn.getText());
				}
			});
			gridSearchBtn.add(searchBtn, 0, 0);

			Button clearBtn = new Button("Clear");
			clearBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					txtIsbn.setText("");
					refreshBookList();
				}
			});
			gridSearchBtn.add(clearBtn, 1, 0);
			hbLeft.getChildren().add(gridSearchBtn);

			GridPane gridBtn = new GridPane();
			gridBtn.setHgap(20);
			gridBtn.setVgap(20);

			Button back = new Button("Back");

			back.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					HomePage.hideAllWindows();
					HomePage.showAdminWindow();
				}
			});
			gridBtn.add(back, 0, 0);
			gridBtn.add(initBtnAdd(), 1, 0);

			hbBottom.getChildren().add(gridBtn);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void searchForBook(String isbn) {
		ControllerInterface controller = ControllerFactory.of();
		HashMap<String, Book> filteredBooks = controller.searchBooks(isbn);
		updateBookListView(filteredBooks);
	}

	private void updateBookListView(HashMap<String, Book> filteredBooks) {
		if (tbv != null) {
			tbv.getItems().clear();
			filteredBooks.forEach((r, v) -> {
				tbv.getItems().add(v);
			});
		}
	}

	private Button initBtnAdd() {
		Button btnAddACopy = new Button("Add a copy of the selected book");
		btnAddACopy.setMinSize(150, 20);
		btnAddACopy.setAlignment(Pos.BOTTOM_RIGHT);
		btnAddACopy.setOnAction((e) -> {
			Book book = tbv.getSelectionModel().getSelectedItem();
			if (book == null) {
				Alert alert = new Alert(AlertType.WARNING, "To add a new copy, select the book first!", ButtonType.OK);
				alert.setTitle("Add a Book Copy");
				alert.setHeaderText("Select the book");
				alert.show();
			} else {
				ControllerInterface ci = ControllerFactory.of();
				ci.addACopy(book);

				updateBooksMap(ci.getBooksMap());
				refreshBookList();

				txtIsbn.setText("");
				Alert alert = new Alert(AlertType.INFORMATION,
						"The copy of the book '" + book.getIsbn() + "' was added successfully.", ButtonType.OK);
				alert.setTitle("Add a Book Copy");
				alert.setHeaderText("The copy was added");
				alert.show();
			}
		});
		return btnAddACopy;
	}

	private void initBookListView(TableView<Book> table) {
		// Add extra columns if necessary:
		TableColumn<Book, String> colIsbn = new TableColumn<>("ISBN");
		colIsbn.setMinWidth(80);
		colIsbn.setCellValueFactory(data -> {
			Book rowValue = data.getValue();
			String cellValue = rowValue.getIsbn();
			return new ReadOnlyStringWrapper(cellValue);
		});
		table.getColumns().add(colIsbn);

		TableColumn<Book, String> colTitle = new TableColumn<>("Title");
		colTitle.setMinWidth(80);
		colTitle.setCellValueFactory(data -> {
			Book rowValue = data.getValue();
			String cellValue = rowValue.getTitle();
			return new ReadOnlyStringWrapper(cellValue);
		});
		table.getColumns().add(colTitle);

		TableColumn<Book, String> colAuthors = new TableColumn<>("Authors");
		colAuthors.setMinWidth(80);
		colAuthors.setCellValueFactory(data -> {
			Book rowValue = data.getValue();
			List<Author> authors = rowValue.getAuthors();
			String authorsName = authors.stream().map(AddCopyWindow::getAuthorName).collect(Collectors.joining(", "));
			return new ReadOnlyStringWrapper(authorsName);
		});
		table.getColumns().add(colAuthors);

		TableColumn<Book, String> colCopies = new TableColumn<>("Copies");
		colCopies.setMinWidth(80);
		colCopies.setCellValueFactory(data -> {
			Book rowValue = data.getValue();
			BookCopy[] copyies = rowValue.getCopies();
			int availableNum = 0;
			for (BookCopy copy : copyies) {
				if (copy.isAvailable()) {
					availableNum++;
				}
			}
			return new ReadOnlyStringWrapper(availableNum + "/" + copyies.length);
		});
		table.getColumns().add(colCopies);
		table.setPrefSize(500, 500);
		table.setMaxHeight(400);
		table.setColumnResizePolicy((param) -> true);
	}

	public static String getAuthorName(Author author) {
		return author.getFirstName() + " " + author.getLastName();
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void setInitialized(boolean val) {
		isInitialized = val;
	}

	public void updateBooksMap(HashMap<String, Book> booksMap) {
		this.booksMap = booksMap;
	}

	public void refreshBookList() {
		if (tbv != null) {
			tbv.getItems().clear();
			this.booksMap.forEach((r, v) -> {
				tbv.getItems().add(v);
			});
		}
	}

}
