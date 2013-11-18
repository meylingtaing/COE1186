package trainmodule;

import java.util.*;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TrainModel
{
	private DoorController doors;
	private LightController lights;
	private PassengerManager passengers;
	private EngineModel engine;
	private Route route;
	private TemperatureController temperature;
	private double trainMass = 37103.9;
	private double length = 32.2;
	private String conductor;
	private static int clockSpeed;
	private TrainFailure failure;
	
	//DEBUG VARIABLES
	private static int trainIDGenerator = 2476;
	private static double powerGenerator = 100.0;
	private static double speedGenerator = 75;
	private static double accelerationGenerator = 23.21;
	private static int passengerGenerator = 34;
	private static double temperatureGenerator = 72.4;
	
	//TABLE COLUMN VARIABLES
	private SimpleIntegerProperty trainID;
	private SimpleStringProperty setpoint;
	private SimpleStringProperty speed;
	private SimpleStringProperty acceleration;
	private SimpleIntegerProperty passengerNumber;
	private SimpleStringProperty temp;
	private SimpleStringProperty fail;
	
	public TrainModel(int clock, Route trip, double t, String engineer)
	{
		doors = new DoorController();
		lights= new LightController();
		passengers = new PassengerManager();
		engine = new EngineModel();
		temperature = new TemperatureController(t);
		failure = new TrainFailure();
		conductor = engineer;
		clockSpeed = clock;
		route = trip;
		
		//INITIALIZE TABLE VARIABLES
		trainID = new SimpleIntegerProperty(trainIDGenerator);
		trainIDGenerator += 121;
		setpoint = new SimpleStringProperty(powerGenerator + " KW");
		powerGenerator -= 3.12;
		speed = new SimpleStringProperty(speedGenerator + " MPH");
		speedGenerator += 1.91;
		acceleration = new SimpleStringProperty(accelerationGenerator + " m/s^2");
		accelerationGenerator += 0.151;		
		passengerNumber = new SimpleIntegerProperty(passengerGenerator);
		passengerGenerator += 3;
		temp = new SimpleStringProperty(temperatureGenerator + " F");
		temperatureGenerator += 1.91;
		fail = new SimpleStringProperty();
	}
	
	public int getTrainID()
	{
        return trainID.get();
    }
	
    public void setTrainID(int i)
    {
        trainID.set(i);
    }
    
    public String getSetpoint()
	{
        return setpoint.get();
    }
	
    public void setSetpoint(String s)
    {
        setpoint.set(s);
    }
    
    public String getSpeed()
	{
        return speed.get();
    }
	
    public void setSpeed(String s)
    {
        speed.set(s);
    }
    
    public String getAcceleration()
	{
        return acceleration.get();
    }
	
    public void setAcceleration(String s)
    {
        acceleration.set(s);
    }
    
    public String getTemp()
	{
        return temp.get();
    }
	
    public void setTemp(String s)
    {
        temp.set(s);
    }
    
    public String getFail()
	{
        return fail.get();
    }
	
    public void setFail(String s)
    {
        fail.set(s);
    }
    
    public int getPassengerNumber()
	{
        return passengerNumber.get();
    }
	
    public void setPassengerNumber(int i)
    {
        passengerNumber.set(i);
    }
}
