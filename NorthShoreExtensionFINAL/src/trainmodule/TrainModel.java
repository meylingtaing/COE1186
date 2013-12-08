/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

//TODO Signal failure ie cannot update train position
//TODO Add information to table
//TODO create test cases
//TODO Add information to table

import java.util.*;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class simulates the train model
 */
public class TrainModel
{
	private static final double TRAIN_MASS = 37103.9;	//This variable contains the train mass			
	private static final double LENGTH = 32.2;			//This variable contains the train LENGTH
	public static ViewController vc;				//This holds the view controller
	
	private DoorController doors;					//This holds the door controller
	private LightController lights;					//This holds the light controller
	private PassengerManager passengers;			//This holds the passenger manager
	private EngineModel engine;						//This holds the train engine	
	//private ctc.Route route;						//This holds the train route information
	private TemperatureController temperature;		//This holds the temperature controller
	private String conductor;						//This holds the conductor name or ID
	//private static int clockSpeed;				//This holds the clock speed
	private TrainFailure failure;					//This holds the train failure
	private boolean eBrake = false;					//This holds the emergency brake state (engaged = true, disengaged = false)
	
	//DEBUG VARIABLES
	private static int trainIDGenerator = 0;		//Generates the train ID
	
	//TABLE COLUMN VARIABLES
	private SimpleIntegerProperty trainID;			//Holds the train ID
	
	/**
	 * This is the main to run the train model indiviually
	 */
	public static void main(String args[])
	{
		TrainView.createGUI();	
	}
	
	/**
	 * This a simple train constructor
	 */
	public TrainModel(int id)
	{
		this.trainID = new SimpleIntegerProperty(id);
		doors = new DoorController();
		lights= new LightController();
		passengers = new PassengerManager();
		engine = new EngineModel(0.1, trainID.getValue());
		temperature = new TemperatureController(72);
		failure = new TrainFailure();
		conductor = "engineer";
	}
	
	
	/**
	 * This another basic train constructor
	 */
	public TrainModel(double tick, /*ctc.Route trip,*/ double t, String engineer)
	{
		this(tick, t, engineer, 0);
	}
	
	/**
	 * This the advanced train constructor
	 */
	public TrainModel(double tick, /*ctc.Route trip,*/ double t, String engineer, int id)
	{
		doors = new DoorController();
		lights= new LightController();
		passengers = new PassengerManager();
		trainID = new SimpleIntegerProperty(trainIDGenerator);
		engine = new EngineModel(tick, trainID.getValue());
		temperature = new TemperatureController(t);
		failure = new TrainFailure();
		conductor = engineer;

		//INITIALIZE TABLE VARIABLES		
		this.trainID = new SimpleIntegerProperty(id);
	}
	
	/**
	 * This method returns the ID of the train
	 */
	public int getTrainID()
	{
        return trainID.get();
    }
    
	/**
	 * This method returns the setpoint of the train
	 */
    public String getSetpoint()
	{
        return engine.getSetpoint() + " watts";
    }
	
    /**
	 * This method sets the setpoint of the train
	 */
    public double setSetpoint(double d)
    {    	
    	double speed;
    	
    	if (!eBrake)
    		speed = engine.calculateSetpoint(d, (TRAIN_MASS + passengers.getTotalPassengerMass()));
    	else
    		speed = engine.pullEmergencyBrake(TRAIN_MASS + passengers.getTotalPassengerMass());    	
    	
    	updateG();	//Updates GUI
    	
    	return speed;
    }
    
    /**
	 * This method pulls the brake on the train
	 */
    public double pullBrake(double load)
    {    	
    	double speed = engine.pullBrake(load, (TRAIN_MASS + passengers.getTotalPassengerMass()));
    	
    	updateG();
    	
    	return speed;
    }
    
    /**
	 * This method pulls the emergency brake on the train
	 */
    public double pullEmergencyBrake()
    {    	
    	double speed = engine.pullEmergencyBrake(TRAIN_MASS + passengers.getTotalPassengerMass());
    	
    	updateG();
    	
    	return speed;
    }
    
    /**
	 * This method updates the train GUI
	 */
    private void updateG()
    {
    	if (vc != null)
    		vc.updateGUI();
    	else
    		System.out.println("No view controller");
    }
    
    /**
	 * This method returns the current velocity of the train
	 */
    public String getSpeed()
	{
        return engine.getSpeed() + " m/s";
    }
    
    /**
	 * This method returns the temperature of the train
	 */
    public String getTemp()
	{
        return temperature.getTrainTemperature() + " F";
    }
	
    /**
	 * This method sets the temperature on the train
	 */
    public void setTemp(double t)
    {
    	if (t <= temperature.getTrainTemperature())
    		temperature.heatTrain();
    	else
    	{
    		temperature.coolTrain();
    	}
    }
    
    /**
	 * This method returns the failures as a string
	 */
    public String getFail()
	{
    	String fail = "";
    	
    	for (String s : failure.getFailures())
    		fail = fail + s + " ";
        return fail;
    }
	
    /**
	 * This method throws an engine failure
	 */
    public void throwEngineFailure()
    {
        failure.throwEngineFailure();
        engine.setEngineFailure(true);
    }
    
    /**
	 * This method throws a signal failure
	 */
    public void throwSignalFailure()
    {
        failure.throwSignalFailure();
    }
    
    /**
	 * This method throws a brake failure
	 */
    public void throwBrakeFailure()
    {
        failure.throwBrakeFailure();
        engine.setBrakeFailure(true);
    }
    
    /**
	 * This method fixes an engine failure
	 */
    public void fixEngineFailure()
    {
        failure.fixEngineFailure();
        engine.setEngineFailure(false);
    }
    
    /**
	 * This method fixes a signal failure
	 */
    public void fixSignalFailure()
    {
        failure.fixSignalFailure();
    }
    
    /**
	 * This method fixes a brake failure
	 */
    public void fixBrakeFailure()
    {
        failure.fixBrakeFailure();
        engine.setBrakeFailure(false);
    }
    
    /**
	 * This method fixes all train failures
	 */
    public void fixAllFailures()
    {
        failure.fixAllFailures();
        engine.setEngineFailure(false);
        engine.setBrakeFailure(false);
    }
    
    /**
	 * This method returns the state of the brakes (true = broken, false = functional)
	 */
    public boolean isBrakeBroken()
	{
		return failure.isBrakeBroken();
	}
	
    /**
	 * This method returns the state of the signal (true = broken, false = functional)
	 */
	public boolean isSignalBroken()
	{
		return failure.isSignalBroken();
	}
	
	/**
	 * This method returns the state of the engine (true = broken, false = functional)
	 */
	public boolean isEngineBroken()
	{
		return failure.isEngineBroken();
	}
    
	/**
	 * This method returns the amount of passengers on the train
	 */
    public int getPassengerNumber()
	{
        return passengers.getPassengerCount();
    }
	
    /**
	 * This method sets the passenger number
	 */
    public void setPassengerNumber()
    {
        passengers.updatePassengers();
    }
   
    /**
	 * This method returns the state of the lights (true = on, false = off)
	 */
    public String getLightValue()
	{
        return lights.getLights();
    }
	
    /**
	 * This method sets the state of the lights (true = broken, false = functional)
	 */
    public void setLightValue(boolean val)
    {
        if (val)
        {
        	lights.turnLightsOn();
        }
        else
        {
        	lights.turnLightsOff();
        }
    }
    
    /**
	 * This method returns the state of the doors (true = open, false = closed)
	 */
    public String getDoorValue()
	{
        return doors.getDoors();
    }
	
    /**
	 * This method sets the state of the doors (true = open, false = closed)
	 */
    public void setDoorValue(boolean val)
    {
        if (val)
        {
        	doors.openDoors();
        }
        else
        {
        	doors.closeDoors();
        }
    }
    
    /**
   	 * This method pulls the emergency brake
   	 */
    public void pullEBrake()
    {
    	eBrake = true;
    }
    
    /**
   	 * This method releases the emergency brake
   	 */
    public void releaseEBrake()
    {
    	eBrake = false;
    }
}