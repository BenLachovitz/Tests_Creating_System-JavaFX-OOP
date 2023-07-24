package Quiz_View;

import java.util.Vector;

import Quiz_Listeners.QuizFromUIListenable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DeleteAnswerView
{
	private Label lbDeleteMsg=new Label("Maybe you see fewer questions then you saw at the question "
			+ "stock, and that because you can not delete\nopen question answer (logic sence).\n"
			+ "In addition, in order to delete close questions answer, there must be at least 5 "
			+ "possible answers.");
	private Label lbDeleteError=new Label("There are no avalible questions to delete answers from them.");
	private Button btSelectQuestion=new Button("Select");
	private Button btDeleteAnswer=new Button("Delete");
	private GridPane gpDeleteAnswer=new GridPane();
	private Vector<RadioButton> update;
	private Vector<QuizFromUIListenable> allListeners;
	private Label lbError;
	private BorderPane bpRoot;
	private ScrollPane spQuestions;
	private VBox vbQuestions;
	
	public DeleteAnswerView(Label lbMsg, Vector<QuizFromUIListenable> allListener,Vector<RadioButton> question
			,BorderPane bpRootView,ScrollPane spQuestion,VBox vbQuestion)
	{
		this.update=question;
		this.allListeners=allListener;
		this.lbError=lbMsg;
		this.bpRoot=bpRootView;
		this.spQuestions=spQuestion;
		this.vbQuestions=vbQuestion;
		
		lbDeleteError.setFont(new Font(15));
		lbDeleteError.setStyle("-fx-font-weight:bold");
		lbDeleteError.setTextFill(Color.DARKRED);
		lbDeleteError.setVisible(false);
		btDeleteAnswer.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		btSelectQuestion.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		lbDeleteMsg.setFont(new Font(15));
		Label lbAnswerSelect=new Label("Please select answer:");
		lbAnswerSelect.setFont(new Font(14));
		lbAnswerSelect.setTextFill(Color.BLACK);
		gpDeleteAnswer.setHgap(8);
		gpDeleteAnswer.setVgap(8);
		gpDeleteAnswer.setAlignment(Pos.CENTER_LEFT);
		btSelectQuestion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				String questionSelected=getRadioSelectedText();
				lbError.setText("");
				gpDeleteAnswer.getChildren().clear();
				if (questionSelected!=null)
				{
					gpDeleteAnswer.add(lbAnswerSelect, 2, 2);
					gpDeleteAnswer.setVisible(true);
					update.clear();
					for (QuizFromUIListenable l:allListeners)
						l.UIAskForAnswerToDelete(questionSelected);
					gpDeleteAnswer.add(btDeleteAnswer, 5, update.size()+4);
					for (int i=0;i<update.size();i++)
					{
						gpDeleteAnswer.add(update.get(i), 4,2+i);
					}
					bpRoot.setCenter(gpDeleteAnswer);
					btDeleteAnswer.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent action) {
							int answerIndex=getRadioSelected();
							if (answerIndex!=-1)
							{
								for (QuizFromUIListenable l : allListeners)
									l.deleteAnswer(questionSelected, answerIndex);
							}
							else
								errorMessege("You must to select an answer");
						}
					});
				}
				else
					errorMessege("You must select question");
			}
		});
	}

	public ScrollPane show()
	{
		spQuestions.setContent(vbQuestions);
		vbQuestions.getChildren().add(lbDeleteMsg);
		for(QuizFromUIListenable l:allListeners)
			l.UIAskForQuestionsThatCanDeleteAnswer();
		vbQuestions.getChildren().addAll(btSelectQuestion,lbDeleteError);
		if (update.isEmpty())
		{
			lbDeleteError.setVisible(true);
			btSelectQuestion.setDisable(true);
		}
		else
		{
			lbDeleteError.setVisible(false);
			btSelectQuestion.setDisable(false);
		}
		return spQuestions;
	}
	
	public void errorMessege(String msg)
	{
		lbError.setText(msg);
		lbError.setTextFill(Color.DARKRED);
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
	
	public String getRadioSelectedText()
	{
		for (int i=0;i<update.size();i++)
		{
			if (update.get(i).isSelected())
				return update.get(i).getText();
		}
		return null;
	}
}
