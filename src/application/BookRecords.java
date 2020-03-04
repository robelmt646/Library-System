package application;

import java.util.HashMap;

import business.Book;
import business.ControllerFactory;
import dataaccess.Auth;
import dataaccess.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.WindowUtils;

public class BookRecords extends Stage implements LibWindow {

	public static final BookRecords INSTANCE = new BookRecords();

	private boolean isInitialized = false;
	private HashMap<String, Book> booksMap = new HashMap<>();
	private TableView<Book> tbv;

	private BookRecords() {
	}

	@Override
	public void init() {
		try {
			User user = ControllerFactory.of().getCurrentUser();
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER_LEFT);
			grid.setHgap(20);
			grid.setVgap(20);
			grid.setPadding(new Insets(25, 25, 25, 25));
			grid.getStyleClass().add(getClass().getSimpleName());

			Text scenetitle = new Text("Book Records");
			scenetitle.setFont(Font.font("optima", FontWeight.NORMAL, 30));
			grid.add(scenetitle,0,1);

			this.tbv = WindowUtils.createBookListTableView();
			grid.add(this.tbv,0,2);
			

			Button back = new Button("Back");
			
			back.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					HomePage.hideAllWindows();
					HomePage.showAdminWindow();
				}
			});
			
			grid.add(back, 0, 3);

		

			Scene scene = new Scene(grid, 1000, 600);
			//scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
			setScene(scene);

	

			this.isInitialized = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void setInitialized(boolean val) {
		isInitialized = val;
	}

	public void refreshBookList(HashMap<String, Book> booksMap) {
		
		this.booksMap.clear();
		this.booksMap.putAll(booksMap);
		if (tbv != null) {
			tbv.getItems().clear();
			this.booksMap.forEach((r, v) -> {
				tbv.getItems().add(v);
			});
		}
	}

}
