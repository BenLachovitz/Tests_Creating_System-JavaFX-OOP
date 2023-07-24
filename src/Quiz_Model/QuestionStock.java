package Quiz_Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

import Quiz_Listeners.QuizFromModelListenable;

public class QuestionStock implements Serializable
{
	private Vector<Question> allQuestion;
	private Vector<QuizFromModelListenable> listeners=new Vector<QuizFromModelListenable>();
	private Quiz test;
	private Quiz tempForManual;

	public QuestionStock()
	{
		allQuestion=new Vector<Question>();
		test=new Quiz();
	}
	public QuestionStock(Vector<Question> allQuestion) throws CloneNotSupportedException
	{
		this.allQuestion=(Vector<Question>)allQuestion.clone();
		test=new Quiz();
	}

	public void registerListener(QuizFromModelListenable listener)
	{
		listeners.add(listener);
	}
	
	public void makeTheQuiz()
	{
		tempForManual=new Quiz();
	}
	
	public void cloneLastQuiz() throws CloneNotSupportedException
	{
		test.clone();
		firePrintQuiz(test.toString());
	}
	
	public void sendNumberOfQuestionForAuto()
	{
		fireNumOfQuestion(allQuestion.size()-problematicQuestions());
	}
	
	private void fireNumOfQuestion(int numOfQuestion) {
		for(QuizFromModelListenable l : listeners)
			l.numOfQuestionsFromModel(numOfQuestion);
	}

	public void addQuestion(String question,Vector<String> allAnswers,Vector<Boolean> allRights) throws Exception
	{
		if (question.isBlank())
			throw new Exception("You must enter a question");
		if (allAnswers.size()==1)
		{
			for (int i=0;i<allQuestion.size();i++)
			{
				if (allQuestion.get(i).question.compareToIgnoreCase(question)==0 && allQuestion.get(i) instanceof OpenQuestion)
					throw new Exception("This open question already exists");	
			}
			if (allAnswers.get(0).isBlank())
				throw new Exception("You must enter answers");
			allQuestion.add(new OpenQuestion(question,allAnswers.get(0)));
		}
		else
		{
			for (int i=0;i<allQuestion.size();i++)
			{
				if (allQuestion.get(i).question.compareToIgnoreCase(question)==0 && allQuestion.get(i) instanceof CloseQuestion)
					throw new Exception("This close question already exists");	
			}
			for (int i=0;i<allAnswers.size();i++)
			{
				if (allAnswers.get(i).isBlank())
					throw new Exception("You must enter answers");
				if (Collections.frequency(allAnswers, allAnswers.get(i))>1)
					throw new Exception("You entered duplicated answers");
				if (allRights.get(i)==null)
					throw new Exception("You must choose true/false");
			}
			Set<String> temp=new Set<String>();
			Iterator it=allAnswers.iterator();
			while(it.hasNext())
				temp.add((String)it.next());
			allQuestion.add(new CloseQuestion(question,temp,allRights));
		}
		fireSucceedToUI("Succeed to add the question");
		fireJumpToHomePage();
	}
	
	private void fireJumpToHomePage() {
		for (QuizFromModelListenable l : listeners)
			l.jumpToHomePageView();
	}
	public void getAllQuestionsStrings()
	{
		Vector<String> questions=new Vector<String>();
		for (int i=0;i<allQuestion.size();i++)
		{
			questions.add(allQuestion.get(i).getQuestion());
		}
		fireSendQuestionsToUI(questions);
	}

	private void fireSendQuestionsToUI(Vector<String> questions) {
		for (QuizFromModelListenable l:listeners)
			l.questionsToUI(questions);
	}
	
	public void updateQuestion(String question,int index) throws Exception,QuestionExistException 
	{
		if (question.isBlank())
			throw new Exception("Can not update question with empty field");
		Question tempQuestion=allQuestion.get(index);
		int tempId=allQuestion.get(allQuestion.size()-1).getId()+1;
		if (tempQuestion instanceof CloseQuestion)
			tempQuestion=new CloseQuestion(question);
		else
			tempQuestion=new OpenQuestion(question);
		tempQuestion.setId(tempId);
		if (allQuestion.contains(tempQuestion))
		{
			throw new QuestionExistException(tempQuestion.getClass().getSimpleName());
		}
		allQuestion.get(index).setQuestion(question);
		fireSucceedToUI("Succeed to update the question");
		fireJumpToHomePage();
	}

	private void fireSucceedToUI(String msg) {
		for (QuizFromModelListenable l : listeners)
			l.succeedMethodToUI(msg);
		
	}

	public void updateAnswer(int questionIndex,int answerIndex,String updatedAnswer,boolean status) throws Exception
	{
		if (updatedAnswer.isBlank())
			throw new Exception("Can not update answer with empty field");
		if (answerIndex==-1)
		{
			((OpenQuestion)allQuestion.get(questionIndex)).setAnswer(updatedAnswer);
		}
		else
		{
		if (((CloseQuestion)allQuestion.get(questionIndex)).getAnswers().contains(updatedAnswer))
			throw new Exception("The answer '"+updatedAnswer+"' already exists at this question.\n");
		((CloseQuestion)allQuestion.get(questionIndex)).getAnswers().set(answerIndex, updatedAnswer);
		((CloseQuestion)allQuestion.get(questionIndex)).getStatusAnswers().set(answerIndex, status);
		}
		fireSucceedToUI("Succeed to update the answer");
		fireJumpToHomePage();
	}

	public void deleteAnswer(int questionIndex,int answerIndex)
	{
		((CloseQuestion)allQuestion.get(questionIndex)).deleteAnswer(answerIndex);
		fireSucceedToUI("Succeed to delete the answer");
		fireJumpToHomePage();
	}

	public int problematicQuestions()
	{
		int count=0;
		for (int i=0;i<allQuestion.size();i++)
		{
			if (allQuestion.get(i) instanceof CloseQuestion)
				if(((CloseQuestion)allQuestion.get(i)).getHowManyFalse()<3)
					count++;
		}
		return count;
	}
	
	public void questionsThatAnswerCanBeDelete()
	{
		Vector<String> temp=new Vector<String>();
		for (int i=0;i<allQuestion.size();i++)
		{
			if (allQuestion.get(i) instanceof CloseQuestion)
			{
				if (((CloseQuestion)allQuestion.get(i)).getAnswers().size() > 4)
					temp.add(allQuestion.get(i).question);
			}
		}
		fireQuestionsForDelete(temp);
	}
	
	private void fireQuestionsForDelete(Vector<String> temp) {
		for(QuizFromModelListenable l : listeners)
			l.questionsForDeleteToUI(temp);
	}

	public int getDeleteAnswers(String str)
	{
		for (int i=0;i<allQuestion.size();i++)
		{
			if (allQuestion.get(i) instanceof CloseQuestion)
			{
				if (allQuestion.get(i).question.equals(str))
				{
					return i;
				}
			}
		}
		return -1;
	}
	
	public void getAnswers(int index)
	{	
		Vector<String> answersTemp=new Vector<String>();
		if (allQuestion.get(index) instanceof CloseQuestion)
		{
			Set<String> temp=((CloseQuestion)allQuestion.get(index)).getAnswers();
			for(int i=0;i<temp.size();i++)
			{
				answersTemp.add(temp.get(i));
			}
		}
		else
			answersTemp.add(((OpenQuestion)allQuestion.get(index)).getAnswer());
		fireDeliverAnswers(answersTemp);
	}

	private void fireDeliverAnswers(Vector<String> answers) {
		for (QuizFromModelListenable l:listeners)
			l.answersToUI(answers);
	}
	
	public void manualQuestionAdder(int questionIndex) throws CloneNotSupportedException
	{
		if(allQuestion.get(questionIndex) instanceof CloseQuestion)
		{
			CloseQuestion temp=(CloseQuestion) allQuestion.get(questionIndex).internalClone();
			Vector<String> tempAnswers=new Vector<String>();
			for(int i=0;i<temp.getAnswers().size();i++)
				tempAnswers.add(temp.getAnswers().get(i)+" ====> "+temp.getStatusAnswers().get(i));
			temp.clearAnswers();
			tempForManual.getQuestions().add(temp);
			fireAnswersForQuizToUI(tempAnswers);
		}
		else
		{
			tempForManual.getQuestions().add(allQuestion.get(questionIndex).internalClone());
			fireManualToUI("Succeed to add the question to the quiz",false);
		}
	}
	
	public void manualAnswersAdder(Vector<Integer> answersIndexs,int questionIndex) throws Exception
	{
		if (answersIndexs.size()<4)
			throw new Exception("You must select at least 4 answers");
		CloseQuestion temp=(CloseQuestion) allQuestion.get(questionIndex);
		for (int i=0;i<answersIndexs.size();i++)
		{
			((CloseQuestion)tempForManual.getQuestions().get(tempForManual.getQuestions().size()-1)).addAnswer(temp.getAnswers().get(answersIndexs.get(i)), 
					temp.getStatusAnswers().get(answersIndexs.get(i)));
		}
		((CloseQuestion)tempForManual.getQuestions().get(tempForManual.getQuestions().size()-1)).addExtraAnswers(((CloseQuestion)tempForManual.getQuestions()
				.get(tempForManual.getQuestions().size()-1)).getHowManyFalse());
		fireManualToUI("Succeed to add the question to the quiz",true);
	}
	
	public void finishManualQuiz() throws Exception
	{
		if(tempForManual.getQuestions().size()<1)
			throw new Exception ("You must add at least 1 question to make a quiz");
		tempForManual.sortByAnswersLength();
		tempForManual.save();
		test=tempForManual;
		firePrintQuiz(test.toString());
	}

	private void fireManualToUI(String string,boolean status) {
		for (QuizFromModelListenable l:listeners)
			l.succeedAboutManualToUI(string,status);
	}

	private void fireAnswersForQuizToUI(Vector<String> tempAnswers) {
		for(QuizFromModelListenable l:listeners)
			l.answersForQuizToUI(tempAnswers);
		
	}

	public Vector<Question> stockForAuto(int howManyCan)
	{
		Vector<Question> relevantQuestion=new Vector<Question>();
		for (int i=0;i<allQuestion.size();i++)
		{
			if (allQuestion.get(i) instanceof CloseQuestion)
			{
				if(((CloseQuestion)allQuestion.get(i)).getHowManyFalse()>=3)
					relevantQuestion.add(allQuestion.get(i));
			}
			else
				relevantQuestion.add(allQuestion.get(i));
		}
		return relevantQuestion;
	}
	
	public void autoQuizMaker(int num) throws CloneNotSupportedException, FileNotFoundException
	{
		test=new Quiz();
		Vector<Question> relevantQuestion=stockForAuto(num);
		int questionNumber,answer,correctCount=0;
		for (int i=0;i<num;i++)
		{
			questionNumber=(int)(Math.random()*(relevantQuestion.size()));
			while(test.getQuestions().contains(relevantQuestion.get(questionNumber)))
			{
				questionNumber=(int)(Math.random()*(relevantQuestion.size()));
			}
			if (relevantQuestion.get(questionNumber) instanceof CloseQuestion)
			{
				CloseQuestion fromTest=((CloseQuestion)relevantQuestion.get(questionNumber).internalClone());
				CloseQuestion fromStock=(CloseQuestion)relevantQuestion.get(questionNumber);
				fromTest.clearAnswers();
				for (int answersCount=0;answersCount<4;answersCount++)
				{
					answer=(int)(Math.random()*(fromStock.getAnswers().size()));
					boolean rightAnswer=fromStock.getStatusAnswers().get(answer);
					while(fromTest.getAnswers().contains(fromStock.getAnswers().get(answer))||(correctCount==1&&rightAnswer))
					{
						answer=(int)(Math.random()*(fromStock.getAnswers().size()));
						rightAnswer=fromStock.getStatusAnswers().get(answer);
					}
					fromTest.addAnswer(fromStock.getAnswers().get(answer), fromStock.getStatusAnswers().get(answer));
					if(rightAnswer)
						correctCount++;
				}
				fromTest.addExtraAnswers(correctCount);
				correctCount=0;
				test.getQuestions().add(fromTest);
			}
			else
				test.getQuestions().add((OpenQuestion)relevantQuestion.get(questionNumber).internalClone());
		}
		test.sortByAnswersLength();
		test.save();
		firePrintQuiz(test.toString());
	}

	private void fireErrorToUI(String message) {
		for (QuizFromModelListenable l:listeners)
			l.errorToUI(message);
	}

	private void firePrintQuiz(String quiz) {
		for (QuizFromModelListenable l:listeners)
		{
			l.printToUI(quiz);
		}
	}
	
	public void save() throws IOException
	{
		ObjectOutputStream outFile=new ObjectOutputStream(new FileOutputStream("Question_Stock.dat"));
		outFile.writeObject(this.allQuestion);
		outFile.close();
	}
	
	public void read() throws ClassNotFoundException, IOException
	{
		ObjectInputStream inFile=new ObjectInputStream(new FileInputStream("Question_Stock.dat"));
		this.allQuestion=(Vector<Question>)inFile.readObject();
		inFile.close();
		this.allQuestion.get(this.allQuestion.size()-1).setId(this.allQuestion.get(this.allQuestion.size()-1).getId()+1);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof QuestionStock))
			return false;
		QuestionStock temp=(QuestionStock)other;
		if (this.allQuestion.equals(temp.allQuestion))
			return true;
		return false;
	}

	@Override
	public String toString()
	{
		StringBuffer str=new StringBuffer("This is the question stock, and those are the questions:");
		for (int i=0;i<allQuestion.size();i++)
			str.append("\n"+(i+1)+") "+allQuestion.get(i)+"\n\n");
		fireStockPrint(str.toString());
		return str.toString();
	}

	private void fireStockPrint(String str)
	{
		for (QuizFromModelListenable l:listeners)
		{
			l.printToUI(str);
		}
	}
}