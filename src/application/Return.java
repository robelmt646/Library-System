package application;
	
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Return extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			GridPane root = new GridPane();
			 root.setPadding(new Insets(20));
		       root.setHgap(25);
		       root.setVgap(15);
			
			 //First horizontal box 
			Label title = new Label("Return Page");
		
			 title.setFont(new Font(28));
			 Button home = new Button("Home");
			 
			
			 root.add(title, 0,0,3,1);
			 root.add(home, 13, 0);
			 
			Label name = new Label("Enter Member's Name");
			TextField tname = new TextField();
			Button checkin = new Button("Check in");
			Button search = new Button("Search");
			
			root.add(tname, 2, 1);
			root.add(checkin, 13, 1);
			root.add(search, 11, 1);
			
			root.add(name, 0,1);
			root.setGridLinesVisible(true);
			
			TableView tableView = new TableView();

			TableColumn firstNameColumn = new TableColumn("ISBN");
			TableColumn lastNameColumn = new TableColumn("Member's Name");
			TableColumn f1 = new TableColumn("Book Name");
			TableColumn f2 = new TableColumn("BorrowedDate");
			TableColumn f3 = new TableColumn("Due Date");
			TableColumn f4 = new TableColumn("Return Check");
			
			tableView.getColumns().addAll(firstNameColumn,lastNameColumn,f1,f2,f3,f4);
			
			tableView.setPrefWidth(500);
			
			root.add(tableView, 0, 2 , 14 , 2);
			
			
			
			Scene scene = new Scene(root,750,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
