package Quiz_View;

import java.util.Vector;

import Quiz_Listeners.QuizFromUIListenable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CloneQuizView
{
	private HBox hbClone=new HBox();
	private ScrollPane spQuizPrint=new ScrollPane();
	private Vector<QuizFromUIListenable> allListeners;
	private BorderPane bpRoot;
	private Label lbError;
	private Label lbPrint;

	public CloneQuizView(Label lbMsg,Label lbForPrint,BorderPane bpRootView,Vector<QuizFromUIListenable> allListener)
	{
		this.allListeners=allListener;
		this.bpRoot=bpRootView;
		this.lbError=lbMsg;
		this.lbPrint=lbForPrint;
		
		hbClone.setPadding(new Insets(40));
		hbClone.setSpacing(14);
		hbClone.setAlignment(Pos.CENTER_LEFT);
		spQuizPrint.setPadding(new Insets(10));
		Label lbClone=new Label("Here you can duplicate the last quiz you made.\nIf you wish so, Please press here: ");
		lbClone.setFont(new Font(18));
		lbClone.setTextFill(Color.BLACK);
		Button btClone=new Button("Duplicate");
		btClone.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		btClone.setMinSize(60, 20);
		hbClone.getChildren().addAll(lbClone,btClone);
		spQuizPrint.setStyle("-fx-background: #668cff");
		btClone.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbError.setText("");
				for (QuizFromUIListenable l : allListeners)
					l.copyTest();
				if (lbError.getText().isBlank())
				{
					spQuizPrint.setContent(lbPrint);
					bpRoot.setCenter(spQuizPrint);
				}
			}
		});
	}
	
	public HBox show()
	{
		spQuizPrint.setContent(null);
		return hbClone;
	}
}
