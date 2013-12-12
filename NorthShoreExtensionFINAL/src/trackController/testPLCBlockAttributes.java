package trackController;

import static org.junit.Assert.*;

import java.util.ArrayList;

import nse.MainController;
import nse.TransitSystem;

import org.junit.Test;

import trackModel.Block;
import trackModel.TrackObject;

public class testPLCBlockAttributes {

	/*
	 * Test 6.2.2
	 * 		Test that a PLC can access track info
	 * 
	 * */
	
	public TransitSystem trainSys; 
	ArrayList<TrackController> plcs;
	
	@Test public void testPLCBlockAttributes() 
	{
		//Create a track model for the green line
		MainController main = new MainController();
		trainSys = main.transitSystem;
		TrackControllerInitalizer ini = new TrackControllerInitalizer(main.transitSystem.ctcGetTrack("greenline"),trainSys);
		plcs = ini.initialize();
		TrackObject trackModel = main.transitSystem.ctcGetTrack("greenline");
		
		int i = 1;
		while(trackModel.getBlock(i)!=null)
		{
			Block b = trackModel.getBlock(i);
			assertNotNull("Block Non null",b);
			i++;
		}
		
		
		
		if(i == 1)
		{
			assertNotNull("No Block information Recieved From Track Model",i);
		}
		
		//All blocks (150) Visited
		// i is 151 since the last comparison returns null
		assertEquals("All Green line Blocks recieved",i,151);
	}
	
}
