package Quiz_View;

import java.util.Vector;

import Quiz_Listeners.QuizFromUIListenable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AddQuestionView
{
	private TextField tfQuestion=new TextField();
	private TextField tfAnswer=new TextField();
	private GridPane gpAddQuestion=new GridPane();
	private ComboBox<String> cbQuestionsType=new ComboBox<String>();
	private ComboBox<Integer> cbAnswersNumber=new ComboBox<Integer>();
	private Label lbQuestion=new Label("Enter the question here: ");
	private Label lbAnswer=new Label("Please enter you answer here: ");
	private Label lbAnswersNumber=new Label("Please choose how many answers do you want: ");
	private Vector<Label> answersLabels=new Vector<Label>();
	private Vector<TextField> closeAnswers=new Vector<TextField>();
	private Vector<Label> rightsLabels=new Vector<Label>();
	private Vector<ComboBox> allRightAnswers=new Vector<ComboBox>();
	private Button btAddOpenQuestionToStock =new Button("Add the question");
	private Button btAddCloseQuestionToStock =new Button("Add the question");
	private Label lbError;
	private Vector<QuizFromUIListenable> allListeners;
	
	public AddQuestionView(Label lbMsg,Vector<QuizFromUIListenable> allListener)
	{
		this.lbError=lbMsg;
		this.allListeners=allListener;
		
		gpAddQuestion.setHgap(8);
		gpAddQuestion.setVgap(8);
		btAddOpenQuestionToStock.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		btAddCloseQuestionToStock.setStyle("-fx-background-radius: 10; -fx-background-color:#f5f5f5; -fx-font-weight: bold");
		cbQuestionsType.setStyle("-fx-font-weight: bold");
		cbAnswersNumber.setStyle("-fx-font-weight: bold");
		Label lbChooseQuestionType=new Label("Please choose the question type:");
		lbChooseQuestionType.setTextFill(Color.BLACK);
		lbChooseQuestionType.setFont(new Font(14));
		lbAnswersNumber.setTextFill(Color.BLACK);
		lbAnswersNumber.setFont(new Font(14));
		lbQuestion.setTextFill(Color.BLACK);
		lbQuestion.setFont(new Font(14));
		lbAnswer.setTextFill(Color.BLACK);
		lbAnswer.setFont(new Font(14));
		cbQuestionsType.getItems().addAll("Open Question","Close Question");
		cbAnswersNumber.getItems().addAll(4,5,6,7,8);
		for (int i=0;i<8;i++)
		{
			answersLabels.add(new Label((i+1)+") Please enter your answer here: "));
			answersLabels.get(i).setVisible(false);
			answersLabels.get(i).setTextFill(Color.BLACK);
			answersLabels.get(i).setFont(new Font(14));
			closeAnswers.add(new TextField());
			closeAnswers.get(i).setVisible(false);
			rightsLabels.add(new Label("Select true/false: "));
			rightsLabels.get(i).setVisible(false);
			rightsLabels.get(i).setTextFill(Color.BLACK);
			rightsLabels.get(i).setFont(new Font(14));
			ComboBox<Boolean> temp=new ComboBox<Boolean>();
			temp.getItems().addAll(true,false);
			temp.setStyle("-fx-font-weight: bold");
			allRightAnswers.add(temp);
			allRightAnswers.get(i).setVisible(false);
		}
		gpAddQuestion.add(lbQuestion, 4, 8);
		gpAddQuestion.add(tfQuestion, 6, 8);
		gpAddQuestion.add(lbAnswersNumber, 4, 6);
		gpAddQuestion.add(cbAnswersNumber, 6, 6);
		gpAddQuestion.add(lbAnswer, 4, 10);
		gpAddQuestion.add(tfAnswer, 6, 10);
		for(int i=0;i<8;i++)
		{
			gpAddQuestion.add(answersLabels.get(i), 4, 10+i);
			gpAddQuestion.add(closeAnswers.get(i), 6, 10+i);
			gpAddQuestion.add(rightsLabels.get(i), 8, 10+i);
			gpAddQuestion.add(allRightAnswers.get(i), 10, 10+i);
		}
		gpAddQuestion.add(btAddOpenQuestionToStock, 4, 18);
		gpAddQuestion.add(btAddCloseQuestionToStock, 4, 18);
		gpAddQuestion.add(lbChooseQuestionType, 4, 4);
		gpAddQuestion.add(cbQuestionsType, 6, 4);
		cbQuestionsType.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbError.setText("");
				tfQuestion.clear();
				lbAnswersNumber.setVisible(false);
				cbAnswersNumber.setVisible(false);
				lbQuestion.setVisible(false);
				tfQuestion.setVisible(false);
				lbAnswer.setVisible(false);
				tfAnswer.setVisible(false);
				tfAnswer.clear();
				btAddOpenQuestionToStock.setVisible(false);
				btAddCloseQuestionToStock.setVisible(false);
				for (int i=0;i<8;i++)
				{
					answersLabels.get(i).setVisible(false);
					closeAnswers.get(i).setVisible(false);
					rightsLabels.get(i).setVisible(false);
					allRightAnswers.get(i).setVisible(false);
				}
				cbAnswersNumber.getSelectionModel().clearSelection();
				if (cbQuestionsType.getValue().equals("Close Question"))
				{
					lbAnswersNumber.setVisible(true);
					cbAnswersNumber.setVisible(true);
					cbAnswersNumber.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent action) {
							if (cbAnswersNumber.getValue()!=null)
							{
								for (int i=0;i<8;i++)
								{
									answersLabels.get(i).setVisible(false);
									closeAnswers.get(i).setVisible(false);
									rightsLabels.get(i).setVisible(false);
									allRightAnswers.get(i).setVisible(false);
								}
								lbQuestion.setVisible(true);
								tfQuestion.setVisible(true);
								btAddCloseQuestionToStock.setVisible(false);
								int chosenNumber=cbAnswersNumber.getValue();
								for (int i=0;i<chosenNumber;i++)
								{
									answersLabels.get(i).setVisible(true);
									closeAnswers.get(i).setVisible(true);
									rightsLabels.get(i).setVisible(true);
									allRightAnswers.get(i).setVisible(true);
								}
								btAddCloseQuestionToStock.setVisible(true);
								btAddCloseQuestionToStock.setOnAction(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent action) {
										String theQuestion=tfQuestion.getText();
										Vector<String> allAnswers=new Vector<String>();
										Vector<Boolean> allRights=new Vector<Boolean>();
										for (int i=0;i<chosenNumber;i++)
										{
											allAnswers.add(closeAnswers.get(i).getText());
											allRights.add((Boolean)allRightAnswers.get(i).getValue());
										}	
										for (QuizFromUIListenable l : allListeners)
											l.addQuestionFromUI(theQuestion, allAnswers,allRights);
									}	
								});	
							}
						}
					});
				}	
				else
				{
					lbQuestion.setVisible(true);
					tfQuestion.setVisible(true);
					lbAnswer.setVisible(true);
					tfAnswer.setVisible(true);
					btAddOpenQuestionToStock.setVisible(true);
					btAddOpenQuestionToStock.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent action) {
							String theQuestion=tfQuestion.getText();
							Vector<String> answer=new Vector<String>();
							answer.add(tfAnswer.getText());
							for (QuizFromUIListenable l : allListeners)
								l.addQuestionFromUI(theQuestion, answer,new Vector<Boolean>());
						}	
					});	
				}
			}
		});
	}
	
	public GridPane show()
	{
		resetAddQuestion();
		return gpAddQuestion;
	}
	
	public void resetAddQuestion()
	{
		cbQuestionsType.setValue("");
		cbAnswersNumber.getSelectionModel().clearSelection();
		lbQuestion.setVisible(false);
		lbAnswersNumber.setVisible(false);
		cbAnswersNumber.setVisible(false);
		tfQuestion.setVisible(false);
		tfQuestion.clear();
		lbAnswer.setVisible(false);
		tfAnswer.setVisible(false);
		tfAnswer.clear();
		for(int i=0;i<8;i++)
		{
			answersLabels.get(i).setVisible(false);
			closeAnswers.get(i).setVisible(false);
			closeAnswers.get(i).clear();
			rightsLabels.get(i).setVisible(false);
			allRightAnswers.get(i).setVisible(false);
			allRightAnswers.get(i).getSelectionModel().clearSelection();
		}
		btAddOpenQuestionToStock.setVisible(false);
		btAddCloseQuestionToStock.setVisible(false);
	}
}
