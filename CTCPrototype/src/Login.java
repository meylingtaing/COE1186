import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Login {
	
	@FXML private TextField usernameInput;
	@FXML private PasswordField passwordInput;
	@FXML private Text errorMessage;
	@FXML private GridPane rootPane;
	
	private String usersDb = "users.csv";
	
	public void initialize() {
		errorMessage.setVisible(false);
	}
	
	public void submit() {
		String username = usernameInput.getText();
		String pw = passwordInput.getText();
		
		boolean loginSucess = login(username, pw);
		
		if (loginSucess) {
			// Call the main!
			Stage currStage = (Stage) rootPane.getScene().getWindow();
			currStage.close();
			CTC.refreshStage();
		}
		else {
			errorMessage.setVisible(true);
		}
	}
	
	public void enter(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			submit();
		}
	}
	
	private boolean login(String username, String pw) {
		boolean userValid = false;
		try {
			Scanner fileScan = new Scanner(new File(usersDb));
			
			while (fileScan.hasNext()) {
				String line = fileScan.nextLine();
				String[] userInfo = line.split(",");
				
				if (userInfo[0].equals(username)) {
					if (userInfo[1].equals(pw)) {
						// Create user
						CTC.currUser = new User(userInfo[0], userInfo[2], userInfo[3], userInfo[4], userInfo[5]);
						System.out.println("Successfully logged in");
						userValid = true;
					}
					break;
				}
				
			}
			
			fileScan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: users file("+ usersDb +") cannot be found");
			System.exit(-1);
		}
		
		return userValid;
	}
}
