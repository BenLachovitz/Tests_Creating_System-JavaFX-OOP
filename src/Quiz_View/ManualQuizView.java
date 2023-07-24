package Quiz_View;

import java.util.Vector;

import Quiz_Listeners.QuizFromUIListenable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ManualQuizView
{
	private Button btAddTheQuestion = new Button ("Add To Quiz");
	private Button btFinishQuizMaker=new Button ("Finish");
	private Button btAddTheAnswers=new Button("Add Answers");
	private HBox hbForButtons=new HBox();
	private Label lbMakeQuiz=new Label("Those are the questions from the stock.\nPlease select wich ones you "
			+ "want.");
	private ScrollPane spQuizPrint=new ScrollPane();
	private Vector<RadioButton> update;
	private Vector<QuizFromUIListenable> allListeners;
	private Label lbError;
	private BorderPane bpRoot;
	private ScrollPane spQuestions;
	private VBox vbQuestions;
	private VBox vbForAnswers;
	private Vector<CheckBox> selectAnswers;
	private Label lbForPrint;
	private Button btCloneQuiz;
	
	public ManualQuizView(Label lbMsg, Vector<QuizFromUIListenable> allListener,Vector<RadioButton> questions
			,BorderPane bpRootView,ScrollPane spQuestion,VBox vbForQuestions,VBox vbAnswers,Vector<CheckBox> selectAnswer
			,Label lbPrint,Button btClone)
	{
		this.update=questions;
		this.allListeners=allListener;
		this.lbError=lbMsg;
		this.bpRoot=bpRootView;
		this.spQuestions=spQuestion;
		this.vbQuestions=vbForQuestions;
		this.vbForAnswers=vbAnswers;
		this.selectAnswers=selectAnswer;
		this.lbForPrint=lbPrint;
		this.btCloneQuiz=btClone;
		
		spQuizPrint.setPadding(new Insets(10));
		spQuizPrint.setStyle("-fx-background: #668cff");
		lbMakeQuiz.setFont(new Font(17));
		lbMakeQuiz.setStyle("-fx-font-color: #f5f5f5f5");
		btAddTheQuestion.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		btFinishQuizMaker.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		btAddTheAnswers.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		hbForButtons.getChildren().addAll(lbMakeQuiz,btAddTheQuestion,btFinishQuizMaker);
		hbForButtons.setSpacing(10);
		btAddTheQuestion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbError.setText("");
				vbForAnswers.setVisible(false);
				vbForAnswers.getChildren().clear();
				selectAnswers.clear();
				int questionIndex=getRadioSelected();
				if (questionIndex!=-1)
				{
					update.get(questionIndex).setDisable(true);
					update.get(questionIndex).setSelected(false);
					for (QuizFromUIListenable l:allListeners)
						l.UITryToAddQuestionToQuiz(questionIndex);
					if(vbForAnswers.isVisible())
					{
						vbForAnswers.getChildren().add(btAddTheAnswers);
						vbQuestions.setDisable(true);
						btAddTheAnswers.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent action) {
								lbError.setText("");
								Vector<Integer> selectedAnswers=new Vector<Integer>();
								for(int i=0;i<selectAnswers.size();i++)
									if(selectAnswers.get(i).isSelected())
										selectedAnswers.add(i);
								for (QuizFromUIListenable l:allListeners)
									l.UITryToAddAnswersToQuiz(selectedAnswers, questionIndex);
							}
						});
					}
				}
				else
				{
					lbError.setText("You must select a question");
					lbError.setTextFill(Color.DARKRED);
				}
			}
		});
		btFinishQuizMaker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbError.setText("");
				for (QuizFromUIListenable l:allListeners)
					l.finishManualQuizMaker();
				if (lbError.getText().isBlank())
				{
					btCloneQuiz.setDisable(false);
					spQuizPrint.setContent(lbPrint);
					bpRoot.setCenter(spQuizPrint);
				}
			}
		});
	}

	public ScrollPane show()
	{
		spQuestions.setContent(vbQuestions);
		spQuizPrint.setContent(null);
		vbQuestions.getChildren().add(lbMakeQuiz);
		for(QuizFromUIListenable l:allListeners)
		{
			l.UIAskForQuestions();
			l.UIAskToMakeNewQuiz();
		}
		vbQuestions.getChildren().add(hbForButtons);
		return spQuestions;
	}
	
	public int getRadioSelected()
	{
		for (int i=0;i<update.size();i++)
		{
			if (update.get(i).isSelected())
				return i;
		}
		return -1;
	}
}
