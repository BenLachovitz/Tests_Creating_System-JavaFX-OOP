package Quiz_View;

import java.util.Vector;

import Quiz_Listeners.QuizFromUIListenable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AutoQuizView
{
	private VBox vbAutoQuiz=new VBox();
	private Label lbQuestionNumber=new Label("How many question do you want?");
	private Button btNumberEnter=new Button("Enter");
	private ScrollPane spQuizPrint=new ScrollPane();
	private ComboBox<Integer> cbNumOfQuestionsForAuto;
	private Label lbError;
	private Label lbPrint;
	private BorderPane bpRoot;
	private Vector<QuizFromUIListenable> allListeners;
	private Button btCloneQuiz;
	
	public AutoQuizView(ComboBox<Integer> cbNumOfQuestionsForAutoQuiz,Label lbMsg,Vector<QuizFromUIListenable> allListener
			, Label lbForPrint, BorderPane bpRootView, Button btClone)
	{
		this.cbNumOfQuestionsForAuto=cbNumOfQuestionsForAutoQuiz;
		this.allListeners=allListener;
		this.lbError=lbMsg;
		this.lbPrint=lbForPrint;
		this.bpRoot=bpRootView;
		this.btCloneQuiz=btClone;
		
		HBox hbAutoQuiz=new HBox();
		Label lbAutoMsg=new Label("For auto quiz maker the questions that available are open questions"
			+ "\nand close questions with at least 3 false answers.");
		lbAutoMsg.setFont(new Font(20));
		vbAutoQuiz.getChildren().addAll(lbAutoMsg,hbAutoQuiz);
		lbAutoMsg.setAlignment(Pos.TOP_LEFT);
		vbAutoQuiz.setPadding(new Insets(40));
		vbAutoQuiz.setSpacing(140);
		spQuizPrint.setPadding(new Insets(10));
		hbAutoQuiz.setSpacing(20);
		hbAutoQuiz.getChildren().addAll(lbQuestionNumber,cbNumOfQuestionsForAuto,btNumberEnter);
		lbQuestionNumber.setFont(new Font(18));
		lbQuestionNumber.setTextFill(Color.BLACK);
		btNumberEnter.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		spQuizPrint.setStyle("-fx-background: #668cff");
		btNumberEnter.setMinSize(60, 20);
		cbNumOfQuestionsForAuto.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (cbNumOfQuestionsForAuto.getValue()!=null)
				{
					btNumberEnter.setDisable(false);
					btNumberEnter.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent action) {
							lbError.setText("");
							for (QuizFromUIListenable l:allListeners)
								l.automaticQuizMakerFromUI(cbNumOfQuestionsForAuto.getValue());
							if (lbError.getText().isBlank())
							{
								btCloneQuiz.setDisable(false);
								spQuizPrint.setContent(lbPrint);
								bpRoot.setCenter(spQuizPrint);
							}
						}
					});
				}
			}
		});
	}

	public VBox show()
	{
		btNumberEnter.setDisable(true);
		spQuizPrint.setContent(null);
		for(QuizFromUIListenable l:allListeners)
			l.UIAskForNumOfQuestionToAuto();
		return vbAutoQuiz;
	}
}
