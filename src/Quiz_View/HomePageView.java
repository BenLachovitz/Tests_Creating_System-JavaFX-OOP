package Quiz_View;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HomePageView 
{
	VBox vbHomePage=new VBox();
	
	public HomePageView()
	{
		vbHomePage.setAlignment(Pos.CENTER);
		Label lbHomePageHeadLine=new Label("Welcome to quiz system");
		lbHomePageHeadLine.setFont(new Font(40));
		lbHomePageHeadLine.setStyle("-fx-font-weight: bold");
		lbHomePageHeadLine.setTextFill(Color.web("#ffffff"));
		Label lbHomePageSubHeadLine=new Label("Here you can create quizes in a simpler way\n         "
				+ "Here are the tools for your use:");
		lbHomePageSubHeadLine.setFont(new Font(25));
		lbHomePageSubHeadLine.setStyle("-fx-font-weight: bold");
		lbHomePageSubHeadLine.setTextFill(Color.web("#ffffff"));
		Text txHomePage=new Text();
		txHomePage.setText("\n1) 'Questions Print' - this button will print the questions from stock.\n"
				+ "2) 'Add Questions' - At this option you can add more qustions to the stock.\n"
				+ "3) 'Update Questions' - At this option you can update questions from the stock.\n"
				+ "4) 'Update Answer' - At this option you can update answers of questions from the stock.\n"
				+ "5) 'Delete Answer' - At this option you can delete answers from the stock.\n"
				+ "6) 'Make Quiz Manualy' - At this option you can make new quiz manually.\n"
				+ "7) 'Make Quiz Automaticaly - At this option you can make new quiz automatically.\n"
				+ "8) 'Copy Last Quiz' - At this option you can make copy of the last quiz you made.");
		txHomePage.setFont(new Font(17));
		txHomePage.setFill(Color.web("#ffffff"));
		vbHomePage.getChildren().addAll(lbHomePageHeadLine,lbHomePageSubHeadLine,txHomePage);
	}
	
	public VBox show()
	{
		return vbHomePage;
	}
}
