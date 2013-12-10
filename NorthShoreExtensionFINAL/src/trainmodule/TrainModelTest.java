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
		System.out.println("The train model initializes properly");
		
		//Tests for correct initial temperature and string output
		String temp = train.getTemp();
		assertEquals(temp, "65.2 F");
		assertEquals(train.getTemperature(), 65.2, 0);
		System.out.println("The train temperature initializes properly");
		
		//Tests that the temperature control works
		train.coolTrain();
		assertTrue(train.getTemperature() < 65.2);		
		double newTemp = train.getTemperature();		
		train.heatTrain();
		assertTrue(train.getTemperature() > newTemp);
		System.out.println("Temperature control is functioning");
		
		//Checks to see that the doors are initially closed
		assertFalse(train.getDoorState());
		System.out.println("Train doors are initialized properly");
		
		//Tests if the doors open and close
		train.openDoors();
		assertTrue(train.getDoorState());
		train.closeDoors();
		assertFalse(train.getDoorState());
		System.out.println("Doors open and close as expected");
		
		//Checks to see that the lights are initially off
		assertFalse(train.getLightState());
		System.out.println("The lights are initialized correctly");
		
		//Tests if the lights turn on and off
		train.turnLightsOn();
		assertTrue(train.getLightState());
		train.turnLightsOff();
		assertFalse(train.getLightState());	
		System.out.println("The train lights operate properly");
		
		//Tests the passenger manager
		assertEquals(train.getPassengerNumber(), 1);
		train.openDoors();
		int num = 0;
		while (train.getPassengerNumber() == 1)
		{			
			train.updatePassengers();
			num++;
		}
		//Train does not move until the doors are closed
		train.setSetpoint(1000);
		assertTrue(train.getVelocity() == 0);
		//System.out.println("The train does not move unless the doors are closed first");
		train.closeDoors();
		assertTrue(train.getPassengerNumber() <= PassengerManager.MAX_PASSENGERS);
		assertTrue(train.getPassengerNumber() > 1);
		//System.out.println("It took " + num + " transfers\nPassenger number: " + train.getPassengerNumber());
		System.out.println("The train updated passengers properly");
		
		train.openDoors();
		assertEquals(train.clearPassengers(), 1);
		train.closeDoors();
		System.out.println("Clearing the passengers off the train works");
		
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
		System.out.println("The train detects failures as designed");
		
		//TODO create test methods for engine
		//Tests for correct initial train speed
		assertEquals(train.getPower(), 0, 0);
		assertEquals(train.getVelocity(), 0, 0);
		System.out.println("The train engine initializes properly");
		
		//Tests that the engine input changes the speed
		train.setSetpoint(500);
		train.setSetpoint(500);
		train.setSetpoint(500);
		double pow = train.getPower();
		double speed = train.getVelocity();
		assertTrue((pow > 0));	
		assertTrue((speed > 0));
		System.out.println("The train moves forward when power is provided");
		//System.out.println("Train speed: " + speed);
		
		train.setSetpoint(0);
		
		//Tests that friction works
		double nextPow = train.getPower();
		double nextSpeed = train.getVelocity();
		assertTrue((nextPow <= pow));	
		assertTrue((nextSpeed < speed));
		System.out.println("Train friction works");
		
		//Tests that braking works
		TrainModel train2 = new TrainModel(0.2, 65.2, "susan", 105);
		train2.setSetpoint(500);
		train2.setSetpoint(500);
		train2.setSetpoint(500);
		train2.pullBrake(0.5);
		
		double newPow = train2.getPower();
		double newSpeed = train2.getVelocity();
		assertTrue(newPow <= nextPow);	
		assertTrue(newSpeed < nextSpeed);
		System.out.println("Train braking functions correctly");
		
		//Tests that emergency braking works
		TrainModel train3 = new TrainModel(0.2, 62.2, "Pete", 203);
		train3.setSetpoint(500);
		train3.setSetpoint(500);
		train3.setSetpoint(500);
		train3.pullEmergencyBrake();
		
		nextPow = train3.getPower();
		nextSpeed = train3.getVelocity();
		assertTrue(nextPow <= newPow);	
		assertTrue(nextSpeed < newSpeed);
		System.out.println("Train emergency brakes work");
		
		//Tests that brake failure does not allow braking
		TrainModel train4 = new TrainModel(0.2, 68.1, "John", 133);
		train4.setSetpoint(500);
		train4.setSetpoint(500);
		train4.setSetpoint(500);
		train4.throwBrakeFailure();
		train4.pullBrake(0.5);
		
		nextPow = train4.getPower();
		nextSpeed = train4.getVelocity();
		assertEquals(nextPow, train.getPower(), 0);	
		assertEquals(nextSpeed, train.getVelocity(), 0);
		System.out.println("When the brakes fail train brakes do not function");
		
		//Train engine testing below
		train3 = new TrainModel(0.2, 64.1, "Steve", 190);
		train4 = new TrainModel(0.2, 60.8, "Alex", 424);
		train4.openDoors();
		while (train4.getPassengerMass() <= train3.getPassengerMass())
		{
			train4.updatePassengers();			
		}		
		train4.closeDoors();		
		assertTrue(train3.getPassengerMass() != train4.getPassengerMass());
		
		//Tests for heavier trains accelerating at different rates than light trains
		for (int i = 1; i < 5000; i++)
		{
			train3.setSetpoint(i);
			train4.setSetpoint(i);
		}
		assertTrue(train3.getVelocity() > train4.getVelocity());
		System.out.println("The lighter train moves faster than the heavier train");
		
		//Tests that the train does not accept a negative power
		train4 = new TrainModel(0.2, 62.2, "Ashley", 300);
		train4.setSetpoint(-100);
		assertTrue(train4.getVelocity() == 0);
		System.out.println("Train does not accept negative power inputs");
		
		//Tests that the train handles power increases too high
		train4.setSetpoint(50000000);
		assertTrue(train4.getPower() <= 120000);
		System.out.println("Train handles too large of power inputs");
	}
}
