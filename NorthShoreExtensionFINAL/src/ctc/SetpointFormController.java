package ctc;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetpointFormController extends FormController {

	@FXML TextField setpointInput;
	
	@Override
	@FXML protected void submit() {
		Double setpoint = Double.parseDouble(setpointInput.getText());
		// TODO: GIVE SETPOINT TO TRACK CONTROLLER!!!
		
		CTC.ctcController.getSelectedTrain().setSpeedSetpoint(setpoint);
		
		Stage currStage = (Stage) setpointInput.getScene().getWindow();
		currStage.close();
	}

}
