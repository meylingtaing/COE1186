/**
 * Controller for suggesting setpoint and authority
 */
package ctc;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetpointFormController extends FormController 
{
	@FXML private ComboBox<String> trainListBox;
	@FXML private TextField setpointInput, authorityInput;
	
	/**
	 * Fill dropdown with list of trains
	 */
	public void initialize()
	{
		if (ctcOffice != null)
		{
			for (String trainName : ctcOffice.trains.keySet())
			{
				String trainString = "#" + ctcOffice.trains.get(trainName) + " " + trainName;
				trainListBox.getItems().add(trainString);
			}
		}
	}
	
	/**
	 * Grab information from form and call other methods as necessary
	 */
	@Override
	@FXML protected void submit() 
	{
		try
		{
			String selectedTrain = trainListBox.getValue();
			String setpointString = setpointInput.getText();
			String authorityString = authorityInput.getText();
			
			if (selectedTrain != null)
			{
				// de-manipulate string
				String[] stringParts = selectedTrain.split(" ");
				int trainId = Integer.parseInt(stringParts[0].substring(1));
				
				// Setpoint
				if (!setpointString.isEmpty())
				{
					double setpoint = Double.parseDouble(setpointString);
					ctcOffice.transitSystem.ctcSuggestSetpoint(trainId, setpoint);
				}
				
				// Authority
				if (!authorityString.isEmpty())
				{
					int authority = Integer.parseInt(authorityString);
					ctcOffice.transitSystem.ctcSuggestAuthority(trainId, authority);
				}
			}
			
			// Close window
			Stage currStage = (Stage) trainListBox.getScene().getWindow();
			currStage.close();
		}
		catch (Exception e)
		{
			errorMessage.setVisible(true);
			e.printStackTrace();
		}

	}

}
