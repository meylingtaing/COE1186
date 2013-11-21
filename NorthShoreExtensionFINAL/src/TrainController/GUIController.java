package TrainController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GUIController implements Initializable {
	
	public GUI parentGUI = null;
	public TrainController TC = null;

	@FXML private Button button_doorsOpen;
	@FXML private Button button_doorsClose;
	@FXML private Button button_speedUp;
	@FXML private Button button_speedDown;
	@FXML private Button button_newAuthority;
	@FXML public TextField text_speed;
	@FXML public TextField text_currentSpeed;
	@FXML public TextField text_currentPower;
	@FXML public TextField text_newAuthority;
	@FXML public TextField text_currentAuthority;
	
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	assert button_doorsOpen != null : "fx:id=\"button_doorsOpen\" was not injected: check your FXML file 'TrainControllerUI.fxml'.";
    	assert button_doorsClose != null : "fx:id=\"button_doorsClose\" was not injected: check your FXML file 'TrainControllerUI.fxml'.";
    	assert button_speedUp != null : "fx:id=\"button_speedUp\" was not injected: check your FXML file 'TrainControllerUI.fxml'.";
    	assert button_speedDown != null : "fx:id=\"button_speedDown\" was not injected: check your FXML file 'TrainControllerUI.fxml'.";
    	assert button_newAuthority != null : "fx:id=\"button_newAuthority\" was not injected: check your FXML file 'TrainControllerUI.fxml'.";
    	assert text_speed != null : "null";
    	assert text_currentSpeed != null : "null";
    	assert text_currentPower != null : "null";
    	assert text_newAuthority != null : "null";
    	assert text_currentAuthority != null : "null";
    	
    	// initialize your logic here: all @FXML variables will have been injected
    	
    	button_doorsOpen.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			System.out.println("Open Doors Command Sent");
    			if (TC != null) {
    				TC.openDoors();
    			}
    		}
    	});
    	
    	button_doorsClose.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			System.out.println("Close Doors Command Sent");
    			if (TC != null) {
    				TC.closeDoors();
    			}
    		}
    	});
    	
    	button_speedUp.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			System.out.println("Speed Up Command Sent");
    			if (TC != null) {
    				Double speedSetpoint = TC.getSpeedSetpoint();
    				text_speed.setText(Double.toString(TC.setSpeedSetpoint(speedSetpoint + 1.0)));
    				
    			}
    		}
    	});
    	
    	button_speedDown.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			System.out.println("Slow Down Command Sent");
    			if (TC != null) {
    				Double speedSetpoint = TC.getSpeedSetpoint();
    				text_speed.setText(Double.toString(TC.setSpeedSetpoint(speedSetpoint - 1.0)));
    			}
    		}
    	});
    	
    	button_newAuthority.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			System.out.println("New Authority Command Set");
    			if (TC != null) {
    				TC.setAuthorityMoving(Double.parseDouble(text_newAuthority.getText()));
    			}
    		}
    	});
    	
    	
    }
    
    public void setAuthorityText(int authority) {
    	text_currentAuthority.setText(authority + "");
	}
    
    public void setPowerText(int power) {
    	text_currentPower.setText(power + "");
    }
    
    
    

}
