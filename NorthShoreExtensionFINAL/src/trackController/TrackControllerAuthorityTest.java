package trackController;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.Test;

import trackModel.Block;
import trackModel.TrackObject;
import TrainController.TrainController;
import ctc.Route;

import nse.MainController;
import nse.TrainPosition;
import nse.TransitSystem;

public class TrackControllerAuthorityTest {

	public TransitSystem trainSys; 
	ArrayList<TrackController> plcs;
	
	@Test
	public void TrackControllerAuthorityTest()
	{
		//Create a track model for the green line
		MainController main = new MainController();
		trainSys = main.transitSystem;
		TrackControllerInitalizer ini = new TrackControllerInitalizer(main.transitSystem.ctcGetTrack("greenline"),trainSys);
		plcs = ini.initialize();
		TrackObject trackModel = main.transitSystem.ctcGetTrack("greenline");
		
		//assertNotNull("TrackControllers Intialized", plcs);
		
		//Create A train controller and add it to the system
		TrainController trainController = new TrainController();
		trainSys.ctcAddTrain(trainController);
		
		Route trainRoute = new Route(trainController.getModel().getTrainID(),trackModel);
		trainRoute.addStation("MT LEBANON");
		
		trainSys.ctcSetInitialPosition(trainController.getModel().getTrainID(), "greenline");
		trainSys.ctcSendRoute(trainController.getModel().getTrainID(), trainRoute);
	    TrainPosition tp = trainSys.trainPositions.get(trainController.getModel().getTrainID());
		tp.setRoute(trainRoute);
		startTrain();
		plcUpdate();
		
		//MT Leb station
		while(tp.getCurrBlock() != trackModel.getBlock(77))
		{
			
			
			moveTrain();
			plcUpdate();
		}

	}
	
	public void startTrain()
	{
		Hashtable<Integer, TrainController> trains = trainSys.trains;
		
		for(TrainController train : trains.values())
		{
			if(train.getModel().getTrainID() > 0)
			{
				train.setSpeedSetpoint(25.0);
				train.cruiseControl(.2);
				TrainPosition tp = trainSys.trainPositions.get(train.getModel().getTrainID());
				Block curr = tp.getCurrBlock();
				System.out.println("Train in block: "+curr.getBlockId());
			}
			else
			{
				trains.remove(train);
			}
		}
	}
	
	public void plcUpdate()
	{
		for(TrackController plc: plcs)
		{
			plc.handoff();
			ArrayList<TrainWithAuthority> t = plc.calculateFixedBlockAuthority1();
			for(TrainWithAuthority train:t)
			{
				System.out.println("");
				System.out.println("Authority: "+train.authority+" Train: "+train.train.getModel().getTrainID());
				System.out.println("");
			}
		}
	}
	
	public void moveTrain()
	{
		Hashtable<Integer, TrainController> trains = trainSys.trains;
		
		for(TrainController train : trains.values())
		{
			//Why is there a train with an ID of zero in the system??
			if(train.getModel().getTrainID() > 0)
			{
				train.setSpeedSetpoint(25.0);
				train.cruiseControl(.2);
				TrainPosition tp = trainSys.trainPositions.get(train.getModel().getTrainID());
				Block curr = tp.getCurrBlock();
				System.out.println("Train in block: "+curr.getBlockId());
			}
		}
	}

		
	
}
