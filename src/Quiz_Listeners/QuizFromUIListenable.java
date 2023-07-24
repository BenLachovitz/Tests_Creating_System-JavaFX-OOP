package Quiz_Listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import Quiz_Model.QuestionExistException;
import Quiz_Model.QuestionStock;
import Quiz_Model.Quiz;

public interface QuizFromUIListenable 
{
	void askToPrint();
	void addQuestionFromUI(String question,Vector<String> allAnswers,Vector<Boolean> allRights);
	void updateQuestion(int questionNumber, String changes);
	void updateAnswer(int questionIndex,int answerIndex,String updatedAnswer,boolean status);
	void deleteAnswer(String question,int answerIndex);
	void finishManualQuizMaker();
	void UITryToAddQuestionToQuiz(int questionIndex);
	void UITryToAddAnswersToQuiz(Vector<Integer> answersIndexs,int questionIndex);
	void automaticQuizMakerFromUI(int num);
	void UIAskForNumOfQuestionToAuto();
	void copyTest();
	void UIAskToMakeNewQuiz();
	void UIAskForQuestions();
	void UIAskForQuestionsThatCanDeleteAnswer();
	void UIAskForAnswerToDelete(String question);
	void UIAskForAnswer(int index);
	void UICloseAndSave();
}
