/**
 * On Track Trainwreck
 * NewTrackController.java
 * Purpose: Controls the GUI for inputting a new CVS file and reading in all
 * the blocks
 * 
 * @author Sarah Bunke
 * @version 2.0 12/12/13
 * 
 */
package trackModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class NewTrackController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button CreateNewTrackButton;

    @FXML
    private Button cancelbutton;
    
    @FXML
    private Label FileNotFoundLabel;

    @FXML
    private TextField textField;

    
    /**cancel button is clicked, window closes
     * 
     * @param MouseEvent event
     */
    @FXML
    void CancelNewTrackAlert(MouseEvent event) {
    	Stage stage = (Stage)CreateNewTrackButton.getScene().getWindow();
    	stage.close();
    }

    /**when import button is clicked, text field is read in and from ImportFile
    *  will be passed to internalImport
    *  
    *  @param MouseEvent event
    **/
    @FXML
    void ImportFile(MouseEvent event) {
    	
    	String file = textField.getText();
    	int result=InternalImport(file);
    	if(result == 1){
    		TrackMainController.trackLineList.setItems(Track.trackListData);
    		Stage stage = (Stage)CreateNewTrackButton.getScene().getWindow();
        	stage.close();
    	}
    	else{
    		FileNotFoundLabel.setVisible(true);	
    	}
    	
    	
    }
    
    /**internal method to read in file, used for JUnit tests also
    * creates a track object
    * 
    * @param String test -->file name
    * @return an int value to indicate success or failure
    */
    public int InternalImport(String text){
    	TrackObject trckOb = new TrackObject();
    	String trackLineColor=null;
    	try {
			Scanner fileScan = new Scanner(new File(text));
			
			
			String linetwo = fileScan.nextLine();
			
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				String[] blockInfo = line.split(",");
				
				trackLineColor=blockInfo[0];
				trckOb.setLine(blockInfo[0]);
				Block newBlock = new Block(blockInfo);
				trckOb.addBlock(newBlock);
				/*
				System.out.print(blockInfo[0] + " ");
				System.out.print(blockInfo[1] + " ");
				System.out.print(blockInfo[2] + " ");
				System.out.print(blockInfo[3] + " ");
				System.out.print(blockInfo[4] + " ");
				System.out.print(blockInfo[5] + " ");
				System.out.print(blockInfo[6] + " ");
				System.out.print(blockInfo[7] + " ");
				System.out.print(blockInfo[8] + " ");
				System.out.print(blockInfo[9] + " ");
				
				System.out.println();
				*/
			}
			fileScan.close();
			Track.trackArray.put(trckOb.getLine(), trckOb);
			Track.trackListData.add(trackLineColor);
			return 1;
		} 
		
		catch (FileNotFoundException e) {
			return 0;
			
		}	
    }
    
    /**
     * method for CTC to use for debugging
     * 
     * @param filename ->name of file to read in
     */
    public void ImportFile(String filename)
    {
    	TrackObject trckOb = new TrackObject();
    	String trackLineColor=null;
    	try {
			Scanner fileScan = new Scanner(new File(filename));
			
			
			String linetwo = fileScan.nextLine();
			
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				String[] blockInfo = line.split(",");
				
				trackLineColor=blockInfo[0];
				trckOb.setLine(blockInfo[0]);
				Block newBlock = new Block(blockInfo);
				trckOb.addBlock(newBlock);
				/*
				System.out.print(blockInfo[0] + " ");
				System.out.print(blockInfo[1] + " ");
				System.out.print(blockInfo[2] + " ");
				System.out.print(blockInfo[3] + " ");
				System.out.print(blockInfo[4] + " ");
				System.out.print(blockInfo[5] + " ");
				System.out.print(blockInfo[6] + " ");
				System.out.print(blockInfo[7] + " ");
				System.out.print(blockInfo[8] + " ");
				System.out.print(blockInfo[9] + " ");
				
				System.out.println();
				*/
			}
			fileScan.close();
			Track.trackArray.put(trckOb.getLine(), trckOb);
			Track.trackListData.add(trackLineColor);
			TrackMainController.trackLineList.setItems(Track.trackListData);
			Stage stage = (Stage)CreateNewTrackButton.getScene().getWindow();
	    	stage.close();
		} 
		
		catch (FileNotFoundException e) {
			FileNotFoundLabel.setVisible(true);
			
		}
    }

    @FXML
    void initialize() {
        assert CreateNewTrackButton != null : "fx:id=\"CreateNewTrackButton\" was not injected: check your FXML file 'newTrackAlertBox.fxml'.";
        assert FileNotFoundLabel != null : "fx:id=\"FileNotFoundLabel\" was not injected: check your FXML file 'newTrackAlertBox.fxml'.";
        assert cancelbutton != null : "fx:id=\"cancelbutton\" was not injected: check your FXML file 'newTrackAlertBox.fxml'.";
        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'newTrackAlertBox.fxml'.";

         
    }

}
