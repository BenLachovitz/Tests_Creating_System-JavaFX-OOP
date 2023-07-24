package Quiz_Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import Quiz_Listeners.QuizFromModelListenable;
import Quiz_Listeners.QuizFromUIListenable;
import Quiz_View.MenuButtonsView;
import Quiz_View.View;
import Quiz_Model.CloseQuestion;
import Quiz_Model.OpenQuestion;
import Quiz_Model.Question;
import Quiz_Model.QuestionExistException;
import Quiz_Model.QuestionStock;
import Quiz_Model.Quiz;

public class Controller implements QuizFromUIListenable,QuizFromModelListenable
{
	private QuestionStock theModel;
	private MenuButtonsView theView;
	public Controller(MenuButtonsView theView) throws FileNotFoundException, IOException, ClassNotFoundException, CloneNotSupportedException
	{
		File binaryFile=new File("Question_Stock.dat");
		if (binaryFile.exists())
		{
			theModel=new QuestionStock();
			theModel.read();
		}
		else
			theModel=hardCoded();
		this.theView=theView;

		this.theView.registerListener(this);
		theModel.registerListener(this);
	}

	public static QuestionStock hardCoded() throws CloneNotSupportedException
	{
		CloseQuestion question1=new CloseQuestion("How many bones there are in human body?");
		question1.addAnswer("500", false);
		question1.addAnswer("123", false);
		question1.addAnswer("206", true);
		question1.addAnswer("409", false);
		question1.addAnswer("256", false);
		CloseQuestion question2=new CloseQuestion("What Leonardo da Vinci was known for?");
		question2.addAnswer("Mathematician", true);
		question2.addAnswer("Bucher", false);
		question2.addAnswer("Artist", true);
		question2.addAnswer("Mobster", false);
		question2.addAnswer("Scientist", true);
		question2.addAnswer("Banker", false);
		OpenQuestion question3=new OpenQuestion("What is the meaning of 'Hakuna Matata'?","No worries");
		OpenQuestion question4=new OpenQuestion("When Yitzhak Rabin was killed?","In November 4th 1995");
		CloseQuestion question5=new CloseQuestion("Who invented the dynamite?");
		question5.addAnswer("fibonacci", false);
		question5.addAnswer("Albert Einstein", false);
		question5.addAnswer("Thomas Edison", false);
		question5.addAnswer("Alfred Nobel", true);
		question5.addAnswer("Moses", false);
		question5.addAnswer("Jesus", false);
		Vector<Question> hardCodedQuestions= new Vector<Question>(List.of(question1,question2,question3,question4,question5));
		QuestionStock qs=new QuestionStock(hardCodedQuestions);
		return qs;
	}


	@Override
	public void askToPrint() 
	{
		theModel.toString();
	}

	@Override
	public void printToUI(String print) {
		theView.stockPrint(print);
	}

	@Override
	public void automaticQuizMakerFromUI(int num){
		try
		{
			theModel.autoQuizMaker(num);
		}
		catch (NumberFormatException n)
		{
			errorToUI(n.getMessage());
		}
		catch(Exception global)
		{
			errorToUI(global.getMessage());
		}
	}

	@Override
	public void errorToUI(String error) {
		theView.errorMsg(error);
	}

	@Override
	public void UIAskForQuestions() {
		theModel.getAllQuestionsStrings();
	}

	@Override
	public void questionsToUI(Vector<String> questions) {
		theView.questionsString(questions);
	}

	@Override
	public void updateQuestion(int questionNumber, String changes) {
		try {
			theModel.updateQuestion(changes, questionNumber);
		}
		catch (QuestionExistException q)
		{
			errorToUI(q.getMessage());
		}
		catch (Exception e)
		{
			errorToUI(e.getMessage());
		}
	}

	@Override
	public void succeedMethodToUI(String msg) {
		theView.succeedToUI(msg);
	}

	@Override
	public void UIAskForAnswer(int index) {
		theModel.getAnswers(index);
	}

	@Override
	public void answersToUI(Vector<String> answers) {
		theView.answersString(answers);
	}

	@Override
	public void updateAnswer(int questionIndex, int answerIndex, String updatedAnswer,boolean status) 
	{
		try
		{
			theModel.updateAnswer(questionIndex, answerIndex, updatedAnswer,status);
		}
		catch (Exception e)
		{
			errorToUI(e.getMessage());
		}
	}

	@Override
	public void UIAskForAnswerToDelete(String question)
	{
		theModel.getAnswers(theModel.getDeleteAnswers(question));
	}

	@Override
	public void UIAskForQuestionsThatCanDeleteAnswer() {
		theModel.questionsThatAnswerCanBeDelete();
	}

	@Override
	public void questionsForDeleteToUI(Vector<String> questions) {
		theView.questionsString(questions);
	}

	@Override
	public void deleteAnswer(String question, int answerIndex) {
		theModel.deleteAnswer(theModel.getDeleteAnswers(question), answerIndex);
	}

	@Override
	public void addQuestionFromUI(String question, Vector<String> allAnswers,Vector<Boolean> allRights) {
		try
		{
			theModel.addQuestion(question, allAnswers,allRights);
		}
		catch (Exception e)
		{
			errorToUI(e.getMessage());
		}	
	}

	@Override
	public void UITryToAddQuestionToQuiz(int questionIndex) {
		try
		{
			theModel.manualQuestionAdder(questionIndex);
		}
		catch(Exception e)
		{
			theView.errorMsg(e.getMessage());
		}
	}

	@Override
	public void answersForQuizToUI(Vector<String> answers) {
		theView.answersForTheQuiz(answers);
	}

	@Override
	public void UIAskToMakeNewQuiz() {
		theModel.makeTheQuiz();	
	}

	@Override
	public void succeedAboutManualToUI(String msg,boolean status) {
		theView.succeedFromModelAboutManualToUI(msg,status);	
	}

	@Override
	public void UITryToAddAnswersToQuiz(Vector<Integer> answersIndexs,int questionIndex) {
		try
		{
			theModel.manualAnswersAdder(answersIndexs, questionIndex);
		}
		catch(Exception e)
		{
			errorToUI(e.getMessage());
		}
	}

	@Override
	public void finishManualQuizMaker() {
		try
		{
			theModel.finishManualQuiz();
		}
		catch (Exception e)
		{
			errorToUI(e.getMessage());
		}
	}

	@Override
	public void UIAskForNumOfQuestionToAuto() {
		theModel.sendNumberOfQuestionForAuto();
	}

	@Override
	public void numOfQuestionsFromModel(int numbersQuestion) {
		theView.getNumberOfQuestionsFromModel(numbersQuestion);
	}

	@Override
	public void copyTest() {
		try {
		theModel.cloneLastQuiz();
		}
		catch(Exception e)
		{
			errorToUI(e.getMessage());
		}
	}

	@Override
	public void UICloseAndSave() {
		try
		{
		theModel.save();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void jumpToHomePageView()
	{
		theView.jumpToHomePage();
	}
}
