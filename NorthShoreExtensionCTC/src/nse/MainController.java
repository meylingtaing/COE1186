package nse;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainController {
	protected static TransitSystem transitSystem = new TransitSystem();
	
	@FXML private void navigateCTC() throws Exception 
	{
		Stage newStage = new Stage();
		transitSystem.ctc.start(newStage);
	}
}
