package Quiz_View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

import Quiz_Listeners.QuizFromUIListenable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuButtonsView extends View
{
	protected Button btCloneQuiz=new Button("Copy Last Quiz");
	private HomePageView homePage=new HomePageView();
	
	public MenuButtonsView(Stage theStage)
	{
		super(theStage);
		
		Button btQuestionPrint=new Button("Questions Print");
		Button btAddQuestion=new Button("Add Questions");
		Button btUpdateQuestion=new Button("Update Questions");
		Button btUpdateAnswer=new Button("Update Answer");
		Button btDeleteAnswer=new Button("Delete Answer");
		Button btManualQuizMaker=new Button("Make Quiz Manualy");
		Button btAutoQuizMaker=new Button("Make Quiz Automaticaly");
		Button btHomePage;
		
		QuestionsPrintView printAllQuestions=new QuestionsPrintView(lbPrint,allListeners);
		AddQuestionView addQuestions=new AddQuestionView(lbError,allListeners);
		UpdateQuestionsView updateQuestions=new UpdateQuestionsView(lbError, allListeners, spQuestions,
				vbQuestions, update);
		AnswersUpdateView updateAnswers=new AnswersUpdateView(lbError, allListeners, update, lbAnswer,
				bpRoot, spQuestions, vbQuestions);
		DeleteAnswerView deleteAnswer=new DeleteAnswerView(lbError, allListeners, update
				, bpRoot, spQuestions, vbQuestions);
		ManualQuizView manualQuizMaker=new ManualQuizView(lbError, allListeners, update
				, bpRoot, spQuestions, vbQuestions, vbForAnswers, selectAnswers
				, lbPrint,btCloneQuiz);
		AutoQuizView autoQuizMaker=new AutoQuizView(cbNumOfQuestionsForAuto, lbError,allListeners
				, lbPrint, bpRoot,btCloneQuiz);
		CloneQuizView cloneLastQuiz=new CloneQuizView(lbError, lbPrint, bpRoot, allListeners);
		
		btQuestionPrint.setMinSize(154, 30);
		btQuestionPrint.setStyle("-fx-background-radius: 6; -fx-font-weight: bold");
		btAddQuestion.setMinSize(154, 30);
		btAddQuestion.setStyle("-fx-background-radius: 6; -fx-font-weight: bold");
		btUpdateQuestion.setMinSize(154, 30);
		btUpdateQuestion.setStyle("-fx-background-radius: 6; -fx-font-weight: bold");
		btUpdateAnswer.setMinSize(154, 30);
		btUpdateAnswer.setStyle("-fx-background-radius: 6; -fx-font-weight: bold");
		btDeleteAnswer.setMinSize(154, 30);
		btDeleteAnswer.setStyle("-fx-background-radius: 6; -fx-font-weight: bold");
		btManualQuizMaker.setMinSize(154, 30);
		btManualQuizMaker.setStyle("-fx-background-radius: 6; -fx-font-weight: bold");
		btAutoQuizMaker.setMinSize(154, 30);
		btAutoQuizMaker.setStyle("-fx-background-radius: 6; -fx-font-weight: bold");
		btCloneQuiz.setMinSize(154, 30);
		btCloneQuiz.setStyle("-fx-background-radius: 6; -fx-font-weight: bold");
		

		ImageView imageView=new ImageView();
		try {
			FileInputStream input = new FileInputStream("Pictures/icons8-home-page-24.png");
			Image image = new Image(input);
			imageView = new ImageView(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		btHomePage=new Button("Home Page",imageView);
		btHomePage.setMinSize(154, 30);
		btHomePage.setStyle("-fx-background-radius: 6; -fx-font-weight: bold");
		Vector<Button> allMenuButtons =new Vector<Button>(List.of(btQuestionPrint,btAddQuestion,
				btUpdateQuestion,btUpdateAnswer,btDeleteAnswer,btManualQuizMaker,btAutoQuizMaker,
				btCloneQuiz,btHomePage));
		btCloneQuiz.setDisable(true);
		VBox menuButtons=new VBox();
		menuButtons.setStyle("-fx-border-style: solid inside;-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;-fx-border-color: #b3c6ff;-fx-background-color:#809fff;"
				+ "-fx-background-insets: 5;-fx-background-radius:5");
		menuButtons.getChildren().addAll(allMenuButtons);
		menuButtons.setSpacing(15);
		menuButtons.setPadding(new Insets(5));
		menuButtons.setAlignment(Pos.TOP_LEFT);
		menuButtons.setMargin(btHomePage, new Insets(70,0,0,0));
		bpRoot.setLeft(menuButtons);

		//Set the home page at the first run
		bpRoot.setCenter(homePage.show());

		//Button action for print "btQuestionPrint" (option 1)
		btQuestionPrint.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbHeadLine.setText("Question Stock");
				reset();
				bpRoot.setCenter(printAllQuestions.show());
			}
		});

		//Button action for add question "btAddQuestion" (option 2)
		btAddQuestion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbHeadLine.setText("Add Question");
				reset();
				bpRoot.setCenter(addQuestions.show());
			}
		});

		//Button action for update question "btUpdateQuestion" (option 3)
		btUpdateQuestion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbHeadLine.setText("Update Question");
				reset();
				bpRoot.setCenter(updateQuestions.show());
			}
		});

		//Update Answer "btUpdateAnswer" (option 4)
		btUpdateAnswer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbHeadLine.setText("Update Answer");
				reset();
				bpRoot.setCenter(updateAnswers.show());
			}
		});

		//For delete answer "btDeleteAnswer" (option 5)
		btDeleteAnswer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbHeadLine.setText("Delete Answer");
				reset();
				bpRoot.setCenter(deleteAnswer.show());
			}
		});

		//Button action for manual "btManualQuizMaker" (option 6)
		btManualQuizMaker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbHeadLine.setText("Manual Quiz Maker");
				reset();
				bpRoot.setCenter(manualQuizMaker.show());
			}
		});

		//Button action for auto quiz maker "btAutoQuizMaker" (option 7)
		btAutoQuizMaker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbHeadLine.setText("Auto Quiz Maker");
				reset();
				bpRoot.setCenter(autoQuizMaker.show());
			}
		});

		//Button action for clone "btCloneQuiz" (option 8)
		btCloneQuiz.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbHeadLine.setText("Last Quiz Copy");
				reset();
				bpRoot.setCenter(cloneLastQuiz.show());
			}
		});

		//Button action for clone "btCloneQuiz" (option 8)
		btHomePage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbHeadLine.setText("");
				reset();
				bpRoot.setCenter(homePage.show());
			}
		});
	}

	public void jumpToHomePage()
	{
		lbHeadLine.setText("");
		bpRoot.setCenter(homePage.show());
	}
	
	public void reset()
	{
		bpRoot.setRight(null);
		vbQuestions.setDisable(false);
		lbError.setText("");
		lbPrint.setText("");
		lbAnswer.setText("");
		update.clear();
		vbQuestions.getChildren().clear();
		cbNumOfQuestionsForAuto.getItems().clear();
	}
}

