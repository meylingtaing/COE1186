/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

import static org.junit.Assert.*;

import org.junit.Test;

public class TrainModelTest
{

	/**
	 * This is the main code for when the test is run
	 */	
	@Test
	public void test()
	{
		//Tests for correct initialization
		TrainModel train = new TrainModel(0.2, 65.2, "bob", 101);
		assertEquals(train.getTrainID(), 101);
		assertEquals(train.getConductor(), "bob");
		assertEquals(train.getDeltaT(), 0.2, 0);
		
		//Tests for correct initial temperature and string output
		String temp = train.getTemp();
		assertEquals(temp, "65.2 F");
		assertEquals(train.getTemperature(), 65.2, 0);
		
		//Tests that the temperature control works
		train.coolTrain();
		assertTrue(train.getTemperature() < 65.2);		
		double newTemp = train.getTemperature();		
		train.heatTrain();
		assertTrue(train.getTemperature() > newTemp);
		
		//Checks to see that the doors are initially closed
		assertFalse(train.getDoorState());
		
		//Tests if the doors open and close
		train.openDoors();
		assertTrue(train.getDoorState());
		train.closeDoors();
		assertFalse(train.getDoorState());
		
		//Checks to see that the lights are initially off
		assertFalse(train.getLightState());
		
		//Tests if the lights turn on and off
		train.turnLightsOn();
		assertTrue(train.getLightState());
		train.turnLightsOff();
		assertFalse(train.getLightState());	
		
		//Tests the passenger manager
		assertEquals(train.getPassengerNumber(), 1);
		train.openDoors();
		int i = 0;
		while (train.getPassengerNumber() == 1)
		{			
			train.updatePassengers();
			i++;
		}	
		train.closeDoors();
		assertTrue(train.getPassengerNumber() <= PassengerManager.MAX_PASSENGERS);
		assertTrue(train.getPassengerNumber() >= 1);
		System.out.println("It took " + i + " transfers\nPassenger number: " + train.getPassengerNumber());
		assertEquals(train.clearPassengers(), 1);
		
		
	}
}
