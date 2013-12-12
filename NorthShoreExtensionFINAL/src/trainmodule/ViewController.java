/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This class handles the actions registered in the GUI
 */
public class ViewController
{
    @FXML
    private ResourceBundle resources;		//Holds the resources of trainmodule

    @FXML
    private URL location;					//This contains the URL location
    
    @FXML 
    private TextField nextStop;				//This holds the next stop information
    
    @FXML 
    private TextField currentStop;			//This holds the currentStop

    @SuppressWarnings("rawtypes")
	@FXML 
    private TableView trainTable;			//This is the table that contains the trains and associated information
       
    @SuppressWarnings("rawtypes")
	@FXML 
    private TableColumn idColumn;			//This is the id column of the table
    
    @SuppressWarnings("rawtypes")
	@FXML 
    private TableColumn setColumn;			//This is the setpoint column of the table
    
    @SuppressWarnings("rawtypes")
	@FXML 
    private TableColumn speedColumn;		//This is the speed column  of the table
    
    @SuppressWarnings("rawtypes")
	@FXML 
    private TableColumn accColumn;			//This is the acceleration column  of the table
    
    @SuppressWarnings("rawtypes")
	@FXML 
    private TableColumn passColumn;			//This is the passenger column  of the table
    
    @SuppressWarnings("rawtypes")
	@FXML 
    private TableColumn tempColumn;			//This is the temperature column of the table
    
    @SuppressWarnings("rawtypes")
	@FXML 
    private TableColumn doorColumn;			//This is the doors column  of the table
    
    @SuppressWarnings("rawtypes")
	@FXML 
	    private TableColumn lightColumn;	//This is the light column  of the table
	    
    @SuppressWarnings("rawtypes")
	@FXML 
    private TableColumn failColumn;			//This is the failure column  of the table
    
    @SuppressWarnings("rawtypes")
	@FXML 
    private ComboBox trainChoice;			//This is the train choice selector

    private ObservableList<TrainModel> data;
    
    //This holds the active trains 
    private ObservableList<Integer> trains;				//This holds the selector options
    private TrainModel selectedTrain;					//This holds the selected train
    
    /**
	 * This method initializes the window
	 */	
    @SuppressWarnings("unchecked")
	@FXML
    void initialize()
    {   
    	//Sets the view controller for the train model
    	TrainModel.vc = this;
    	
    	//Sets up the columns of the table
    	idColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, Integer>("trainID"));
    	setColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("setpoint"));
    	speedColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("speed"));
    	doorColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("doorValue"));
    	lightColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("lightValue"));
    	passColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, Integer>("passengerNumber"));
    	tempColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("temp"));
    	failColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("fail"));
    	
    	if (TrainModel.demo)
    	{
    		data = FXCollections.observableArrayList(
    			new TrainModel(0.2, 65.2, "Matt", 101),
    			new TrainModel(0.2, 68.9, "Jackie", 105)
    		);
    	}
    	else
    	{
    		data = FXCollections.observableArrayList();
    	}
    	
    	updateGUI();	//Needed to refresh the window with updated values
    }
    
    /**
	 * This method returns the active trains
	 */
    public ObservableList<TrainModel> getActiveTrains()
    {
    	return data;
    }
    
    /**
	 * This method removes a train from the active trains
	 */
    public void removeTrain(int i)
    {
    	for (TrainModel tm : data)
    	{
    		if (trainChoice.getValue().equals(tm.getTrainID()))
    		{
    			data.remove(tm);
    		}
    	} 
    }
    
    /**
	 * This method adds a train to the active trains
	 */
    public void addTrain(TrainModel t)
    {
    	data.add(t);
    }
    
    /**
	 * This method adds a train to the active trains
	 */
    public void addTrain(/*ctc.Route r,*/ double t, String c)
    {
    	//data.add(new TrainModel(0.2, /*r,*/ t, c));
    }
    
    /**
	 * This method updates the GUI by refreshing the table, selector, etc. values
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateGUI()
    {     
    	if (TrainModel.demo)
    	{   		
    		for (TrainModel tm : data)
    		{    		
        		System.out.println("Train added: " + tm.getTrainID());
    		}
    	}
    	else
    	{
	    	//Initializes the table data to empty list    		
        	data.clear();
        	
        	for (TrainController.TrainController tc : nse.MainController.transitSystem.trains.values())
    		{    		
        		data.add(tc.getModel());
        		System.out.println("Train added: " + tc.getModel().getTrainID());
    		}
    	}
    	
    	trains = FXCollections.observableArrayList();   	
    	
    	for (TrainModel tm : data)
    	{
    		if (selectedTrain == null)
    		{
    			selectedTrain = tm;
    		}
    		trains.add(tm.getTrainID());
    	}
    	
    	trainChoice.setItems(trains);
    	
    	if (selectedTrain != null)
    	{
    		trainChoice.setValue(selectedTrain.getTrainID());    	
    	}
    	
    	trainTable.setItems(data);
    	
    	((TableColumn)(trainTable.getColumns()).get(0)).setVisible(false);
    	((TableColumn)(trainTable.getColumns()).get(0)).setVisible(true);
    	
    	System.out.println("GUI REFRESH");
    }
    
    /**
	 * This method returns the selected train
	 */
    public void currentTrain()
    {   	
    	for (TrainModel tm : data)
    	{
    		if (trainChoice.getValue().equals(tm.getTrainID()))
    		{
    			selectedTrain = tm;
    		}
    	}    	
    }
    
    /**
	 * This method throws the engine failure
	 */
    public void throwEngineFailure()
    {
    	if (selectedTrain != null && !selectedTrain.isEngineBroken())
    	{
    		System.out.println("TrainID: " + selectedTrain.getTrainID());
    		selectedTrain.throwEngineFailure();
    		updateGUI();
    	}
    	else
    		System.out.println("No selection");
    }
    
    /**
   	 * This method throws the signal failure
   	 */
    public void throwSignalFailure()
    {
    	if (selectedTrain != null && !selectedTrain.isSignalBroken())
    	{
	    	selectedTrain.throwSignalFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    /**
   	 * This method throws the brake failure
   	 */
    public void throwBrakeFailure()
    {
    	if (selectedTrain != null && !selectedTrain.isBrakeBroken())
    	{
	    	selectedTrain.throwBrakeFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    /**
   	 * This method fixes the engine failure
   	 */
    public void fixEngineFailure()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.fixEngineFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    /**
   	 * This method fixes the signal failure
   	 */
    public void fixSignalFailure()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.fixSignalFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    /**
   	 * This method fixes the brake failure
   	 */
    public void fixBrakeFailure()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.fixBrakeFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    /**
   	 * This method fixes all failures
   	 */
    public void fixAllFailures()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.fixAllFailures();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    /**
   	 * This method engages the emergency brake
   	 */
    public void engageEBrake()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.pullEBrake();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    /**
   	 * This method disengages the emergency brake
   	 */
    public void disengageEBrake()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.releaseEBrake();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
}