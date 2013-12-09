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
		assertTrue(train.getPassengerNumber() > 1);
		System.out.println("It took " + i + " transfers\nPassenger number: " + train.getPassengerNumber());
		
		train.openDoors();
		assertEquals(train.clearPassengers(), 1);
		train.closeDoors();
		
		//Tests for initial failure states
		assertFalse(train.isEngineBroken());
		assertFalse(train.isBrakeBroken());
		assertFalse(train.isSignalBroken());
		
		//Tests for failure toggling
		train.throwEngineFailure();
		assertTrue(train.isEngineBroken());
		train.fixEngineFailure();
		assertFalse(train.isEngineBroken());
		train.throwBrakeFailure();
		assertTrue(train.isBrakeBroken());
		train.fixBrakeFailure();
		assertFalse(train.isBrakeBroken());
		train.throwSignalFailure();
		assertTrue(train.isSignalBroken());
		train.fixSignalFailure();
		assertFalse(train.isSignalBroken());
		
		//Tests that the fix all failures works
		train.throwEngineFailure();
		train.throwBrakeFailure();
		train.throwSignalFailure();
		train.fixAllFailures();
		assertFalse(train.isEngineBroken());
		assertFalse(train.isBrakeBroken());
		assertFalse(train.isSignalBroken());
		
		//TODO create test methods for engine
		//Tests for correct initial train speed
		assertEquals(train.getPower(), 0, 0);
		assertEquals(train.getVelocity(), 0, 0);
		
		//Tests that the engine input changes the speed
		train.setSetpoint(100);
		double pow = train.getPower();
		double speed = train.getVelocity();
		assertTrue((pow > 0));	
		assertTrue((speed > 0));
		System.out.println("Train power: " + pow + "\nTrain speed: " + speed);
		
		train.setSetpoint(0);
		
		double nextPow = train.getPower();
		double nextSpeed = train.getVelocity();
		assertTrue((nextPow <= pow));	
		assertTrue((nextSpeed <= speed));
		System.out.println("Train power: " + nextPow + "\nTrain speed: " + nextSpeed);
	}
}
