package Quiz_Model;

import java.io.PrintWriter;
import java.io.Serializable;

public abstract class Question implements Cloneable, Serializable
{
	private static int autoId=1000;
	protected int id;
	protected String question;
	
	public Question (String question)
	{
		this.question=question;
		id=autoId++;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.autoId=id;
	}
	
	@Override
	public abstract boolean equals(Object other);
	
	public abstract int getCharCount();
	
	public abstract void save(PrintWriter writer,boolean doesSolution);
	
	public abstract Question internalClone() throws CloneNotSupportedException;
	
	public boolean setQuestion(String question)
	{
		if (question.isEmpty())
			return false;
		this.question=question;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "The question id is: "+id+"\nThe Question is:\n'"+question+"'";
	}

}
