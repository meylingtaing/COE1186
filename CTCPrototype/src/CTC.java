/**
 *	Meyling Taing
 *	Some skeleton code for a JavaFX main application
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CTC extends Application {
	protected static ArrayList<Double[]> blockCoordinates;
	protected static int blockCount;
	protected static Track[] tracks;
	protected static Stage ctcStage;
	protected static User currUser;
	
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
		// Load track layout from CSV file
		blockCoordinates = new ArrayList<Double[]>();
		blockCount = 0;
		currUser = null;
		
		ctcStage = new Stage();
		refreshStage();
		
		login();
		
	}
	
	private void login() {
		//currUser = new User ("default", false, false, false, false);
		
		// Prompt user for username and password
		final Popup loginPopup = new Popup();
		final TextField inputUsername = new TextField();
		final PasswordField inputPw = new PasswordField();
		Text usernamePrompt = new Text("Username: ");
		Text pwPrompt = new Text("Password: ");
		Button submitButton = new Button("Submit");
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String username = inputUsername.getText();
				String pw = inputPw.getText();
				
				try {
					Scanner fileScan = new Scanner(new File("users.csv"));
					while (fileScan.hasNext()) {
						String line = fileScan.nextLine();
						String[] userInfo = line.split(",");
						if (userInfo[0].equals(username)) {
							if (userInfo[1].equals(pw)) {
								// Create user
								currUser = new User(userInfo[0], userInfo[2], userInfo[3], userInfo[4], userInfo[5]);
								System.out.println("Successfully logged in");
;							}
							else
								break;
						}
					}
					fileScan.close();
				} catch (FileNotFoundException e) {
					System.out.println("Error: users file cannot be found");
					System.exit(-1);
				}
				
				loginPopup.hide();
				refreshStage();
			}
		});
		
		HBox userBox = new HBox();
		userBox.getChildren().addAll(usernamePrompt, inputUsername);
		HBox pwBox = new HBox();
		pwBox.getChildren().addAll(pwPrompt, inputPw);
		
		VBox popupBox = new VBox();
		popupBox.getChildren().addAll(userBox, pwBox, submitButton);
		loginPopup.getContent().add(popupBox);
		popupBox.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
		loginPopup.show(CTC.ctcStage, 500, 300);
	}
}