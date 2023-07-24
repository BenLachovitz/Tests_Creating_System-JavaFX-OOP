package Quiz_Model;

import java.util.Comparator;

public class CompareByAnswerLength implements Comparator<Question>
{
	public int compare(Question question1,Question question2)
	{
		int firstNum=question1.getCharCount();
		int secondNum=question2.getCharCount();
		if (firstNum<secondNum)
			return -1;
		if (firstNum>secondNum)
			return 1;
		return 0;
	}

}
