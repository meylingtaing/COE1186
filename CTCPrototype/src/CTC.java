/**
 *	Meyling Taing
 *	Some skeleton code for a JavaFX main application
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CTC extends Application {
	protected static Track[] tracks;
	protected static int numTracks = 0;
	protected static Stage ctcStage;
	protected static User currUser;
	
	private final String usersDb = "users.csv";
	
	public static void main(String[] args) throws IOException{
		Application.launch(CTC.class, args);
	}
	
	private void refreshStage() {
		Parent mainController = new CTCMainController();
		ctcStage.setScene(new Scene(mainController));
		ctcStage.setTitle("Trains and stuff");
		ctcStage.show();
	}
	
	@Override
	public void start(Stage stage) throws IOException {

		tracks = new Track[4];
		currUser = null;
		
		ctcStage = new Stage();
		refreshStage();
		
		displayLogin();
		
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
						currUser = new User(userInfo[0], userInfo[2], userInfo[3], userInfo[4], userInfo[5]);
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
	
	private void displayLogin() {
		
		final Popup loginPopup = new Popup();
		final TextField inputUsername = new TextField();
		final PasswordField inputPw = new PasswordField();
		Text usernamePrompt = new Text("Username: ");
		Text pwPrompt = new Text("Password: ");
		final Text errorMessage = new Text("Error: username/password combination invalid");
		errorMessage.setVisible(false);
		Button submitButton = new Button("Submit");
		
		HBox userBox = new HBox();
		userBox.getChildren().addAll(usernamePrompt, inputUsername);
		HBox pwBox = new HBox();
		pwBox.getChildren().addAll(pwPrompt, inputPw);
		
		VBox popupBox = new VBox();
		popupBox.getChildren().addAll(userBox, pwBox, submitButton, errorMessage);
		loginPopup.getContent().add(popupBox);
		popupBox.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
		loginPopup.show(CTC.ctcStage, 500, 300);
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String username = inputUsername.getText();
				String pw = inputPw.getText();
				
				boolean loginSucess = login(username, pw);
				
				if (loginSucess) {
					loginPopup.hide();
					refreshStage();
				}
				else {
					errorMessage.setVisible(true);
				}
			}
		});
		
		inputPw.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					String username = inputUsername.getText();
					String pw = inputPw.getText();
					
					boolean loginSucess = login(username, pw);
					
					if (loginSucess) {
						loginPopup.hide();
						refreshStage();
					}
					else {
						errorMessage.setVisible(true);
					}
				}
			}
		});
	}
}