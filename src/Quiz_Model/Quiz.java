package Quiz_Model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Vector;

public class Quiz implements Cloneable
{
	private Vector<Question> allQuestion;

	public Quiz()
	{
		allQuestion=new Vector<Question>();
	}

	public Vector<Question> getQuestions()
	{
		return allQuestion;
	}
	
	public Quiz clone() throws CloneNotSupportedException
	{
		Quiz copyQuiz=(Quiz)super.clone();
		copyQuiz.allQuestion=new Vector<Question>();
		Iterator it=allQuestion.iterator();
		while(it.hasNext())
		{
			copyQuiz.allQuestion.add(((Question)it.next()).internalClone());
		}
		return copyQuiz;
	}
	
	public void sortByAnswersLength()
	{
		allQuestion.sort(new CompareByAnswerLength());
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Quiz))
			return false;
		Quiz temp=(Quiz)other;
		if (this.allQuestion.equals(temp.allQuestion))
			return true;
		return false;
	}

	public void save() throws FileNotFoundException
	{
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy_MM_dd");
		LocalDate nowDate=LocalDate.now();
		File fileExam = new File("Quizes/exam_"+formatter.format(nowDate)+".txt");
		PrintWriter pwExam=new PrintWriter(fileExam);
		File fileSolution = new File("Quizes/solution_"+formatter.format(nowDate)+".txt");
		PrintWriter pwSolution=new PrintWriter(fileSolution);
		Iterator<Question> it=allQuestion.iterator();
		int i=1;
		while (it.hasNext())
		{
			Question temp=it.next();
			pwExam.print((i)+") ");
			pwSolution.print((i++)+") ");
			temp.save(pwExam,false);
			temp.save(pwSolution, true);
			pwExam.println();
			pwSolution.println();
		}
		pwExam.close();
		pwSolution.close();
	}
	
	@Override
	public String toString()
	{
		StringBuffer str=new StringBuffer("Welcome to the quiz.\nThere are ("
				+ allQuestion.size()+") questions.\n");
		for (int i=0;i<allQuestion.size();i++)
		{
			str.append("Question number "+(i+1)+". "+allQuestion.get(i).toString()+"\n\n");
		}
		return str.toString();
	}

}
