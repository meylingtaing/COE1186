/**
 * The controller class for the login functions in the CTC
 * 
 * @author meyling
 */
package ctc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Login 
{
	
	@FXML private TextField usernameInput;
	@FXML private PasswordField passwordInput;
	@FXML private Text errorMessage;
	@FXML private GridPane rootPane;
	
	private String usersDb = "users.csv";
	
	public void initialize() 
	{
	}
	
	/**
	 * Called when the submit button is pressed on the CTC login form
	 * It collects the user's input for username and password
	 */
	public void submit() 
	{
		//*
		String username = usernameInput.getText();
		String pw = passwordInput.getText();
		
		CTC.currUser = login(username, pw);
		
		if (CTC.currUser != null) 
		{
			// Close the login
			Stage currStage = (Stage) rootPane.getScene().getWindow();
			currStage.close();
			
			// Display the main
			Parent ctcRoot;
			try {
				ctcRoot = FXMLLoader.load(getClass().getResource("CTCView.fxml"));
				CTC.ctcStage.setScene(new Scene(ctcRoot));
				CTC.ctcStage.setTitle("North Shore Extension");
				CTC.ctcStage.show();
			} catch (IOException e) {
				System.err.println("Can't open the CTC office!");
			}
			
		}
		else 
		{
			errorMessage.setVisible(true);
		}
		//*/
	}
	
	
	/**
	 * Calls submit() when the user hits enter while typing in the 
	 * CTC login form.
	 * 
	 * @param event		Identifies the key that is pressed
	 */
	public void enter(KeyEvent event) 
	{
		//*
		if (event.getCode() == KeyCode.ENTER) 
		{
			submit();
		}
		//*/
	}
	

	/**
	 * Checks the user login credentials with the users database
	 * @param username
	 * @param pw
	 * @param user		User object to be created
	 * @return 			true if user login credentials are valid, otherwise false
	 */
	public User login(String username, String pw) 
	{
		try 
		{
			Scanner fileScan = new Scanner(new File(usersDb));
			
			while (fileScan.hasNext()) 
			{
				String line = fileScan.nextLine();
				String[] userInfo = line.split(",");
				
				if (userInfo[0].equals(username)) 
				{
					if (userInfo[1].equals(pw)) 
					{
						// Create user
						User user = new User(userInfo[0], userInfo[2], userInfo[3], userInfo[4], userInfo[5]);
						System.out.println("Successfully logged in");
						fileScan.close();
						return user;
					}
					break;
				}
				
			}
			
			fileScan.close();
		} catch (FileNotFoundException e) 
		{
			System.out.println("Error: users file("+ usersDb +") cannot be found");
			System.exit(-1);
		}
		
		return null;
		//*/
	}
	
}
