package Quiz_View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Vector;

import Quiz_Listeners.QuizFromUIListenable;

public class QuestionsPrintView
{
	private ScrollPane spRoot=new ScrollPane();
	private Label lbPrint;
	private Vector<QuizFromUIListenable> allListeners;
	
	public QuestionsPrintView(Label lbPrint, Vector<QuizFromUIListenable> allListeners)
	{
		this.lbPrint=lbPrint;
		this.allListeners=allListeners;
		spRoot.setStyle("-fx-background: #668cff; -fx-border-color: #b3c6ff;");
		spRoot.setPadding(new Insets(10));
	}
	
	public ScrollPane show()
	{
		spRoot.setContent(null);
		for (QuizFromUIListenable l : allListeners)
		{
			l.askToPrint();
		}
		spRoot.setContent(lbPrint);
		return spRoot;
	}
}
