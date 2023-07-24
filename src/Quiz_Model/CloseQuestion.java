package Quiz_Model;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;

public class CloseQuestion extends Question implements Serializable, Cloneable
{
	private Set<String> allAnswers;
	private Vector<Boolean> rightAnswers;

	public CloseQuestion(String question)
	{
		super(question);
		allAnswers=new Set<String>();
		rightAnswers=new Vector<Boolean>();
	}
	
	public CloseQuestion (String question,Set<String> allAnswers,Vector<Boolean> allRights) throws CloneNotSupportedException
	{
		super(question);
		this.allAnswers=allAnswers.clone();
		this.rightAnswers=(Vector<Boolean>)allRights.clone();
	}

	public CloseQuestion internalClone() throws CloneNotSupportedException
	{
		CloseQuestion copyQuestion = (CloseQuestion)super.clone();
		copyQuestion.allAnswers=allAnswers.clone();
		copyQuestion.rightAnswers=(Vector<Boolean>)rightAnswers.clone();
		return copyQuestion;
	}
	
	public void clearAnswers()
	{
		allAnswers=new Set<String>();
		rightAnswers=new Vector<Boolean>();
	}

	public void save(PrintWriter writer,boolean doesSolution)
	{
		writer.println(this.question+"\n");
		for (int i=0;i<allAnswers.size();i++)
		{
			if (doesSolution)
				writer.println((char)(i+'A')+") "+allAnswers.get(i)+"==>"+rightAnswers.get(i));
			else
				writer.println((char)(i+'A')+") "+allAnswers.get(i));
		}
	}

	public boolean addAnswer(String answer,boolean status)
	{
		if (answer.isEmpty())
			return false;
		boolean succeeded=allAnswers.add(answer);
		if (!succeeded)
			return false;
		rightAnswers.add(status);
		return true;
	}

	@Override
	public int getCharCount()
	{
		return this.allAnswers.SetToString().length();
	}

	public void deleteAnswer(int index)
	{
			allAnswers.remove(index);
			rightAnswers.remove(index);
	}

	public void addExtraAnswers(int count)
	{
		allAnswers.add("More then one is true");
		allAnswers.add("None is true");
		if (count>1)
			rightAnswers.add(true);
		else
			rightAnswers.add(false);
		if (count==0)
			rightAnswers.add(true);
		else
			rightAnswers.add(false);
	}

	public Set<String> getAnswers()
	{
		return this.allAnswers;
	}

	public Vector<Boolean> getStatusAnswers()
	{
		return this.rightAnswers;
	}

	public int getHowManyFalse()
	{
		return Collections.frequency(rightAnswers, false);
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof CloseQuestion))
			return false;
		CloseQuestion temp=(CloseQuestion)other;
		if (this.question.compareToIgnoreCase(temp.question)==0)
			return true;
		return false;
	}

	@Override
	public String toString()
	{
		StringBuffer str=new StringBuffer("This question type is: "+this.getClass().getSimpleName()
				+"\n"+super.toString()+"\n\n");
		for (int i=0;i<allAnswers.size();i++)
		{
			if (allAnswers.get(i).equals("More then one is true")&&rightAnswers.get(i)==true)
				str.append("\t"+(i+1)+") "+allAnswers.get(i)+" =====> "+rightAnswers.get(i)+" (this is the most right answer)\n");
			else
				str.append("\t"+(i+1)+") "+allAnswers.get(i)+" =====> "+rightAnswers.get(i)+"\n");
		}
		return str.toString();
	}
}
