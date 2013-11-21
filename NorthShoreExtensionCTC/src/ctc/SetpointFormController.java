package ctc;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetpointFormController extends FormController {

	@FXML TextField setpointInput;
	
	@Override
	protected void submit() {
		Double setpoint = Double.parseDouble(setpointInput.getText());
		// TODO: GIVE SETPOINT TO TRACK CONTROLLER!!!
		Stage currStage = (Stage) setpointInput.getScene().getWindow();
		currStage.close();
	}

}
