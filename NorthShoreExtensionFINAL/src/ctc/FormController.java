/**
 * Controller for CTC popup forms
 * @author meyling
 */
package ctc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class FormController 
{
	protected CTC ctcOffice;
	@FXML protected Text errorMessage;
	
	/**
	 * Sets the CTC office model that we are controlling
	 * @param ctcOffice
	 */
	public void setContext(CTC ctcOffice)
	{
		this.ctcOffice = ctcOffice;
		initialize();
	}
	
	public void initialize() {
		// I would make this abstract but maybe some of the controllers don't
		// need any initialization
		
	}

	/**
	 * Closes the popup window when cancel button is pressed
	 * 
	 * @param event		identifies the cancel node which identifies the window
	 */
	@FXML protected void cancel(ActionEvent event) 
	{
		Node currNode = (Node) event.getSource();
		Stage currStage = (Stage) currNode.getScene().getWindow();
		currStage.close();
	}
	
	/**
	 * Submits when enter is pressed
	 * 
	 * @param event
	 */
	@FXML protected void enter(KeyEvent event)
	{
		if (event.getCode() == KeyCode.ENTER) 
		{
			submit();
		}
	}
	
	/**
	 * Needs to be overwritten in subclass
	 */
	@FXML abstract protected void submit();
	
}
