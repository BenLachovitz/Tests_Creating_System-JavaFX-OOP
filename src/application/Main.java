package application;
	
import java.io.FileNotFoundException;
import java.io.IOException;

import Quiz_Controller.Controller;
import Quiz_View.MenuButtonsView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException, ClassNotFoundException, IOException, CloneNotSupportedException {
		MenuButtonsView theView=new MenuButtonsView(primaryStage);
		Controller controller=new Controller(theView);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
