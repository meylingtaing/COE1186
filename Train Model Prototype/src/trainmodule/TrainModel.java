package trainmodule;

import java.util.*;

import javafx.application.Application;
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
	
	public TrainModel(int speed, Route trip, double temp, String engineer)
	{
		doors = new DoorController();
		lights= new LightController();
		passengers = new PassengerManager();
		engine = new EngineModel();
		temperature = new TemperatureController(temp);
		failure = new TrainFailure();
		conductor = engineer;
		clockSpeed = speed;
		route = trip;
	}
}
