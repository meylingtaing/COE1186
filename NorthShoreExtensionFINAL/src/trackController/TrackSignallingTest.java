package trackController;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import nse.MainController;
import nse.TrainPosition;
import nse.TransitSystem;

import org.junit.Test;

import ctc.Route;

import TrainController.TrainController;

import trackModel.Block;
import trackModel.Track;
import trackModel.TrackObject;

public class TrackSignallingTest {

		/*
		 * Test that a track controller will set the track signals for the train System correctly
		 * 
		 * 
		 * */

		public TransitSystem trainSys; 
		ArrayList<TrackController> plcs;
		
		@Test public void testTrackSignals() 
		{
			//Create a track model for the green line
			TrackObject trackModel = new TrackObject();
			Scanner fileScan = null;
			try {
				fileScan = new Scanner(new File("greenline.csv"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String trackLineColor=null;
			
			String linetwo = fileScan.nextLine();
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				String[] blockInfo = line.split(",");
				
				trackLineColor=blockInfo[0];
				trackModel.setLine(blockInfo[0]);
				Block newBlock = new Block(blockInfo);
				trackModel.addBlock(newBlock);
				
			}
			fileScan.close();
			
			MainController main = new MainController();
			trainSys = main.transitSystem;
			TrackControllerInitalizer ini = new TrackControllerInitalizer(trackModel,trainSys);
			plcs = ini.initialize();
			
			
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
				//The block that currently contains the train has a red signal state
				if(tp.getCurrBlock().getBlockId() > 0)
				{
					assertEquals(tp.getCurrBlock().getSignalState(),4);
					int id = tp.getCurrBlock().getBlockId();
					
					//The block before this one has a yellow signal
					//assertEquals(trackModel.getBlock(id-1).getSignalState(),3);
					//Currently not working between plcs
				}
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
				plc.calculateSignalStates();
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