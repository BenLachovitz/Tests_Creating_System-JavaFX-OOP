package Quiz_View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import Quiz_Listeners.QuizFromUIListenable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import javafx.stage.*;

public class View
{
	protected BorderPane bpRoot=new BorderPane();
	protected Vector<QuizFromUIListenable> allListeners=new Vector<QuizFromUIListenable>();
	protected Label lbPrint=new Label();
	protected Label lbError=new Label();
	protected Label lbAnswer=new Label();
	protected Label lbHeadLine=new Label();
	protected Vector<RadioButton> update=new Vector<RadioButton>();
	protected Vector<CheckBox> selectAnswers =new Vector<CheckBox>();
	protected VBox vbQuestions=new VBox();
	protected VBox vbForAnswers=new VBox();
	protected ComboBox<Integer> cbNumOfQuestionsForAuto=new ComboBox<Integer>();
	protected ScrollPane spQuestions=new ScrollPane();
	
	public View (Stage theStage)
	{	//Global layer
		vbQuestions.setSpacing(15);
		
		spQuestions.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		spQuestions.setStyle("-fx-background: #668cff; -fx-border-color: #b3c6ff;");
		spQuestions.setPadding(new Insets(10));
		
		cbNumOfQuestionsForAuto.setStyle("-fx-font-weight: bold");
		cbNumOfQuestionsForAuto.setMinSize(60, 20);
		
		lbHeadLine.setPadding(new Insets(5));
		lbHeadLine.setFont(new Font(25));
		lbHeadLine.setStyle("-fx-font-weight: bold");
		lbHeadLine.setTextFill(Color.WHITESMOKE);

		lbPrint.setFont(new Font(15));
		
		lbAnswer.setFont(new Font(14));
		lbAnswer.setTextFill(Color.BLACK);
		
		lbError.setPadding(new Insets(5));
		lbError.setFont(new Font(15));
		lbError.setStyle("-fx-font-weight: bold");
		
		vbForAnswers.setSpacing(10);
		vbForAnswers.setPadding(new Insets(10));
		
		//Global layer settings
		bpRoot.setBottom(lbError);
		bpRoot.setTop(lbHeadLine);
		bpRoot.setAlignment(lbHeadLine, Pos.CENTER);
		bpRoot.setBackground(new Background(new BackgroundFill(Color.web("#668cff"), CornerRadii.EMPTY, Insets.EMPTY)));
		theStage.setScene(new Scene(bpRoot,910,580));
		theStage.show();
		theStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent close) {
				for(QuizFromUIListenable l:allListeners)	
						l.UICloseAndSave();		
			}	
		});
	}

	public void registerListener(QuizFromUIListenable listener)
	{
		allListeners.add(listener);
	}

	public void stockPrint(String print)
	{
		lbPrint.setText(print);
	}

	public void errorMsg(String error)
	{
		lbError.setText(error);
		lbError.setTextFill(Color.DARKRED);
	}

	public void questionsString(Vector<String> questions)
	{
		for (int i=0;i<questions.size();i++)
		{
			update.add(new RadioButton(questions.get(i)));
			update.get(i).setFont(new Font(14));;
		}
		this.vbQuestions.getChildren().addAll(update);
		ToggleGroup tgQuestions=new ToggleGroup();
		for (int i=0;i<questions.size();i++)
		{
			update.get(i).setToggleGroup(tgQuestions);
		}
	}

	public void succeedFromModelAboutManualToUI(String msg,boolean status)
	{
		lbError.setText(msg);
		lbError.setTextFill(Color.GREEN);
		bpRoot.setRight(null);
		if (status)
		{
			vbForAnswers.setVisible(false);
			vbQuestions.setDisable(false);
		}
	}

	public void succeedToUI(String msg)
	{
		lbError.setText(msg);
		lbError.setTextFill(Color.GREEN);
	}

	public void answersString(Vector<String> answers) 
	{
		if (answers.size()==1)
		{
			lbAnswer.setText(answers.get(0));
		}
		else
		{
			ToggleGroup tgAnswers=new ToggleGroup();
			for (int i=0;i<answers.size();i++)
			{
				update.add(new RadioButton(answers.get(i)));
				update.get(i).setToggleGroup(tgAnswers);
				update.get(i).setFont(new Font(14));
				update.get(i).setTextFill(Color.BLACK);
			}
		}
	}

	public void answersForTheQuiz(Vector<String> answers)
	{
		for (int i=0;i<answers.size();i++)
		{
			selectAnswers.add(new CheckBox(answers.get(i)));
			selectAnswers.get(i).setFont(new Font(14));
			selectAnswers.get(i).setTextFill(Color.BLACK);
		}
		vbForAnswers.getChildren().addAll(selectAnswers);
		vbForAnswers.setVisible(true);
		bpRoot.setRight(vbForAnswers);
	}

	public void getNumberOfQuestionsFromModel(int numbersQuestion)
	{
		for (int i=1;i<=numbersQuestion;i++)
			cbNumOfQuestionsForAuto.getItems().add(i);
	}
}
