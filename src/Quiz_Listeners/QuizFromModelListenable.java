package Quiz_Listeners;

import java.util.Vector;

public interface QuizFromModelListenable
{
	void printToUI(String print);
	void errorToUI(String error);
	void questionsToUI(Vector<String> questions);
	void questionsForDeleteToUI(Vector<String> questions);
	void succeedMethodToUI(String msg);
	void succeedAboutManualToUI(String msg,boolean status);
	void answersToUI(Vector<String> answers);
	void answersForQuizToUI(Vector<String> answers);
	void numOfQuestionsFromModel(int numbersQuestion);
	void jumpToHomePageView();
}
