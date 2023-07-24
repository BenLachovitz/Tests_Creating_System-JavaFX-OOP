package Quiz_Model;

import java.io.PrintWriter;
import java.io.Serializable;

public class OpenQuestion extends Question implements Serializable,Cloneable
{
	private String answer;
	
	public OpenQuestion (String question,String answer)
	{
		super(question);
		this.answer=answer;
	}
	
	public OpenQuestion (String question)
	{
		super(question);
	}
	
	public OpenQuestion internalClone() throws CloneNotSupportedException
	{
		return (OpenQuestion)super.clone();
	}
	
	public void save(PrintWriter writer,boolean doesSoultion)
	{
		writer.println(this.question+"\n");
		if (doesSoultion)
			writer.println("  The answer is: "+this.answer);
		else
			writer.println("  The answer: ");
	}
	
	public boolean setAnswer(String answer)
	{
		if (answer.isEmpty())
			return false;
		this.answer=answer;
		return true;
	}
	
	public int getCharCount()
	{
		return answer.length();
	}
	
	public String getAnswer()
	{
		return this.answer;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof OpenQuestion))
			return false;
		OpenQuestion temp=(OpenQuestion)other;
		if (this.question.compareToIgnoreCase(temp.question)==0)
			return true;
		return false;
	}
	
	@Override
	public String toString()
	{
		return "This question type is: "+this.getClass().getSimpleName()+"\n"+super.toString()+
				"\n\nThe answer for the question is: "+this.answer;
	}

}
