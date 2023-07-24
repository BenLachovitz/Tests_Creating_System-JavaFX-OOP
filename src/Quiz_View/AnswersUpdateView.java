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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AnswersUpdateView
{
	private GridPane gpUpdateAnswers=new GridPane();
	private RadioButton trueAnswer=new RadioButton("true");
	private RadioButton falseAnswer=new RadioButton("false");
	private TextField tfUpdateAnswer=new TextField();
	private Button btUpdateAnswerOK=new Button("Update");
	private Button btUpdateOk=new Button("OK");
	private Vector<RadioButton> update;
	private Vector<QuizFromUIListenable> allListeners;
	private Label lbError;
	private Label lbAnswer;
	private BorderPane bpRoot;
	private ScrollPane spQuestions;
	private VBox vbQuestions;

	public AnswersUpdateView(Label lbMsg,Vector<QuizFromUIListenable> allListener,Vector<RadioButton> question,Label lbForAnswer,
			BorderPane bpRootView,ScrollPane spQuestion,VBox vbQuestion)
	{
		this.update=question;
		this.allListeners=allListener;
		this.lbError=lbMsg;
		this.lbAnswer=lbForAnswer;
		this.bpRoot=bpRootView;
		this.spQuestions=spQuestion;
		this.vbQuestions=vbQuestion;
		
		btUpdateOk.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		btUpdateAnswerOK.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		trueAnswer.setFont(new Font(14));
		falseAnswer.setFont(new Font(14));
		trueAnswer.setTextFill(Color.BLACK);
		falseAnswer.setTextFill(Color.BLACK);
		ToggleGroup tgAnswerStatus=new ToggleGroup();
		trueAnswer.setToggleGroup(tgAnswerStatus);
		falseAnswer.setToggleGroup(tgAnswerStatus);
		gpUpdateAnswers.setVisible(false);
		Label lbTheAnswerIs=new Label("The Answer/s: ");
		lbTheAnswerIs.setFont(new Font(14));
		lbTheAnswerIs.setTextFill(Color.BLACK);
		Label lbEnterAnswer=new Label("Enter the answer here: ");
		lbEnterAnswer.setFont(new Font(14));
		lbEnterAnswer.setTextFill(Color.BLACK);
		gpUpdateAnswers.setHgap(8);
		gpUpdateAnswers.setVgap(8);
		gpUpdateAnswers.setAlignment(Pos.CENTER_LEFT);
		btUpdateOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				int index=getRadioSelected();
				lbError.setText("");
				gpUpdateAnswers.getChildren().clear();
				if (index!=-1)
				{
					gpUpdateAnswers.add(lbTheAnswerIs, 2, 2);
					gpUpdateAnswers.setVisible(true);
					boolean status;
					update.clear();
					for (QuizFromUIListenable l:allListeners)
						l.UIAskForAnswer(index);
					if (!lbAnswer.getText().isBlank())
					{
						gpUpdateAnswers.add(lbEnterAnswer, 2, 4);
						gpUpdateAnswers.add(tfUpdateAnswer, 4, 4,2,1);
						gpUpdateAnswers.add(btUpdateAnswerOK, 2, 6);
						gpUpdateAnswers.add(lbAnswer, 4, 2);
						status=false;
					}
					else
					{
						for (int i=0;i<update.size();i++)
						{
							gpUpdateAnswers.add(update.get(i), 4,2+i);
						}
						gpUpdateAnswers.add(lbEnterAnswer, 2, update.size()+3);
						gpUpdateAnswers.add(tfUpdateAnswer, 4, update.size()+3,2,1);
						gpUpdateAnswers.add(btUpdateAnswerOK, 2, update.size()+5);
						status=true;
						gpUpdateAnswers.add(trueAnswer, 6, update.size()+3);
						gpUpdateAnswers.add(falseAnswer, 6, update.size()+4);
					}

					bpRoot.setCenter(gpUpdateAnswers);
					btUpdateAnswerOK.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent action) {
							if (!status)
							{
								for (QuizFromUIListenable l:allListeners)
									l.updateAnswer(index, -1, tfUpdateAnswer.getText(),false);
							}
							else
							{
								int answerIndex=getRadioSelected();
								if (answerIndex!=-1)
								{
									boolean answerStatus=false;
									if (trueAnswer.isSelected())
										answerStatus=true;
									if (trueAnswer.isSelected()||falseAnswer.isSelected())
									{
										for (QuizFromUIListenable l:allListeners)
											l.updateAnswer(index,answerIndex , tfUpdateAnswer.getText(),answerStatus);
									}
									else
										errorMessege("You must to select true/false");
								}
								else
									errorMessege("You must to select an answer");
							}
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
		trueAnswer.setSelected(false);
		falseAnswer.setSelected(false);
		tfUpdateAnswer.clear();
		spQuestions.setContent(vbQuestions);
		for(QuizFromUIListenable l:allListeners)
			l.UIAskForQuestions();
		vbQuestions.getChildren().add(btUpdateOk);
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
}
