package Quiz_View;

import java.util.Vector;

import Quiz_Listeners.QuizFromUIListenable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UpdateQuestionsView
{
	private Label lbEnterQuestion=new Label("Please enter your new question:");
	private TextField tfUpdateQuestion=new TextField();
	private Button btUpdateOk=new Button("OK");
	private Label lbError;
	private Vector<QuizFromUIListenable> allListeners;
	private ScrollPane spQuestions;
	private VBox vbQuestions;
	private Vector<RadioButton> update;
	
	public UpdateQuestionsView(Label lbMsg,Vector<QuizFromUIListenable> allListener,ScrollPane spQuestion,
			VBox vbQuestion,Vector<RadioButton> question)
	{
		this.lbError=lbMsg;
		this.allListeners=allListener;
		this.spQuestions=spQuestion;
		this.vbQuestions=vbQuestion;
		this.update=question;
		
		tfUpdateQuestion.setMaxSize(250, 15);
		lbEnterQuestion.setFont(new Font(14));
		btUpdateOk.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		btUpdateOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbError.setText("");
				int index=getRadioSelected();
				if (index!=-1)
				{
					for(QuizFromUIListenable l:allListeners)
						l.updateQuestion(index, tfUpdateQuestion.getText());
				}
				else
				{
					lbError.setText("You must to select question");
					lbError.setTextFill(Color.DARKRED);
				}
				tfUpdateQuestion.clear();
			}
		});
	}

	public ScrollPane show()
	{
		spQuestions.setContent(vbQuestions);
		vbQuestions.getChildren().clear();
		for(QuizFromUIListenable l:allListeners)
			l.UIAskForQuestions();
		vbQuestions.getChildren().addAll(lbEnterQuestion,tfUpdateQuestion,btUpdateOk);
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
