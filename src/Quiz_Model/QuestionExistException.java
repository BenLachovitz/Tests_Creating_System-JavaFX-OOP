package Quiz_Model;

public class QuestionExistException extends Exception
{
	public QuestionExistException(String type)
	{
		super("This question of type '"+type+"' already exists at our stock, Please try something else");
	}
}
