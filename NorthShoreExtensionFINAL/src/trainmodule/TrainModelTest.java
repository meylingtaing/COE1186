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
	 * This is the main code that runs the individual tests
	 */	
	@Test
	public void test()
	{
		TestTrainInitializer();			
		TestTrainTemperatureController();
		TestTrainLightController();
		TestTrainDoorController();
		TestPassengerManager();	
		TestTrainFailure();
		TestTrainMovesSafely();
		TestTrainPhysics();
		TestTrainBrakeFailure();
		TestMassEffect();
		TestTrainNoPower();
		TestTrainLowPower();
		TestTrainHighPower();
	}
	
	/**
	 * Tests for correct initialization of the train model
	 */	
	@Test
	public void TestTrainInitializer()
	{		
		TrainModel train = new TrainModel(0.2, 65.2, "bob", 101);
		assertEquals(train.getTrainID(), 101);
		assertEquals(train.getConductor(), "bob");
		assertEquals(train.getDeltaT(), 0.2, 0);
		//System.out.println("The train model initializes properly");
	}
	
	/**
	 * Tests for correct initial temperature, String output and control
	 */	
	@Test
	public void TestTrainTemperatureController()
	{
		TrainModel train = new TrainModel(0.2, 65.2, "bob", 101);		
		String temp = train.getTemp();
		assertEquals(temp, "65.2 F");
		assertEquals(train.getTemperature(), 65.2, 0);
		//System.out.println("The train temperature initializes properly");
		
		train.coolTrain();
		assertTrue(train.getTemperature() < 65.2);		
		double newTemp = train.getTemperature();		
		train.heatTrain();
		assertTrue(train.getTemperature() > newTemp);
		//System.out.println("Temperature control is functioning");
	}
	
	/**
	 * Checks to see that the doors are initially closed and tests if the doors open and close
	 */
	@Test
	public void TestTrainDoorController()
	{
		TrainModel train = new TrainModel(0.2, 65.2, "bob", 101);		
		assertFalse(train.getDoorState());
		//System.out.println("Train doors are initialized properly");
		
		train.openDoors();
		assertTrue(train.getDoorState());
		train.closeDoors();
		assertFalse(train.getDoorState());
		//System.out.println("Doors open and close as expected");		
	}
	
	/**
	 * Checks to see that the lights are initially off and tests if the lights turn on and off
	 */
	@Test
	public void TestTrainLightController()
	{
		TrainModel train = new TrainModel(0.2, 65.2, "bob", 101);
		assertFalse(train.getLightState());
		//System.out.println("The lights are initialized correctly");
		
		train.turnLightsOn();
		assertTrue(train.getLightState());
		train.turnLightsOff();
		assertFalse(train.getLightState());	
		//System.out.println("The train lights operate properly");
	}
	
	/**
	 * Tests the passenger manager class to make sure that it functions properly
	 */
	@Test
	public void TestPassengerManager()
	{
		TrainModel train = new TrainModel(0.2, 65.2, "bob", 101);

		assertEquals(train.getPassengerNumber(), 1);
		train.openDoors();
		while (train.getPassengerNumber() == 1)
		{			
			train.updatePassengers();
		}
		train.closeDoors();
		assertTrue(train.getPassengerNumber() <= PassengerManager.MAX_PASSENGERS);
		assertTrue(train.getPassengerNumber() > 1);
		
		//System.out.println("The train updated passengers properly");
		
		train.openDoors();
		assertEquals(train.clearPassengers(), 1);
		train.closeDoors();
		//System.out.println("Clearing the passengers off the train works");
	}
	
	/**
	 * Checks that the train does not move until the doors are closed
	 */
	@Test
	public void TestTrainMovesSafely()
	{
		TrainModel train = new TrainModel(0.2, 65.2, "bob", 101);
		train.openDoors();		
		train.setSetpoint(1000);
		assertTrue(train.getVelocity() == 0);
	}
	
	/**
	 * Tests that train failures get and set properly
	 */
	@Test
	public void TestTrainFailure()
	{
		TrainModel train = new TrainModel(0.2, 65.2, "bob", 101);
		
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
		//System.out.println("The train detects failures as designed");
	}
	
	/**
	 * This method tests that the physics of the train work properly
	 */
	@Test
	public void TestTrainPhysics()
	{
		TrainModel train = new TrainModel(0.2, 65.2, "bob", 101);
		
		//Tests for correct initial train speed
		assertEquals(train.getPower(), 0, 0);
		assertEquals(train.getVelocity(), 0, 0);
		//System.out.println("The train engine initializes properly");
		
		//Tests that the engine input changes the speed
		train.setSetpoint(500);
		train.setSetpoint(500);
		train.setSetpoint(500);
		double pow = train.getPower();
		double speed = train.getVelocity();
		assertTrue((pow > 0));	
		assertTrue((speed > 0));
		//System.out.println("The train moves forward when power is provided");
		//System.out.println("Train speed: " + speed);
		
		train.setSetpoint(0);
		
		//Tests that friction works
		double nextPow = train.getPower();
		double nextSpeed = train.getVelocity();
		assertTrue((nextPow <= pow));	
		assertTrue((nextSpeed < speed));
		//System.out.println("Train friction works");
		
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
		//System.out.println("Train braking functions correctly");
		
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
		//System.out.println("Train emergency brakes work");
	}
	
	/**
	 * This tests that the brakes no longer work when a brake failure occurs
	 */
	@Test
	public void TestTrainBrakeFailure()
	{
		
		//Tests that brake failure does not allow braking
		TrainModel train1 = new TrainModel(0.2, 65.2, "susan", 105);
		train1.setSetpoint(500);
		train1.setSetpoint(500);
		train1.setSetpoint(500);
		train1.pullBrake(0);
		
		TrainModel train2 = new TrainModel(0.2, 68.1, "John", 133);
		train2.setSetpoint(500);
		train2.setSetpoint(500);
		train2.setSetpoint(500);
		train2.throwBrakeFailure();
		train2.pullBrake(0.5);
		
		double nextPow = train2.getPower();
		double nextSpeed = train2.getVelocity();
		assertEquals(nextPow, train1.getPower(), 0);	
		assertEquals(nextSpeed, train1.getVelocity(), 0);
		//System.out.println("When the brakes fail train brakes do not function");
	}
	
	/**
	 * This tests for mass affecting the train movement
	 */
	@Test
	public void TestMassEffect()
	{
		//Train engine testing below
		TrainModel train1 = new TrainModel(0.2, 64.1, "Steve", 190);
		TrainModel train2 = new TrainModel(0.2, 60.8, "Alex", 424);
		
		train2.openDoors();
		
		while (train2.getPassengerMass() <= train1.getPassengerMass())
		{
			train2.updatePassengers();			
		}		
		train2.closeDoors();		
		assertTrue(train1.getPassengerMass() != train2.getPassengerMass());
		
		//Tests for heavier trains accelerating at different rates than light trains
		for (int i = 1; i < 100; i++)
		{
			train1.setSetpoint(i);
			train2.setSetpoint(i);
		}
		assertTrue(train1.getVelocity() > train2.getVelocity());
		//System.out.println("The lighter train moves faster than the heavier train");
	}
	
	/**
	 * Tests that the train handles large power outputs
	 */
	@Test
	public void TestTrainHighPower()
	{
		TrainModel train = new TrainModel(0.2, 65.5, "Sandy", 241);
		
		//Tests that the train handles power increases too high
		train.setSetpoint(50000000);
		assertTrue(train.getPower() <= 120000);
		//System.out.println("Train handles too large of power inputs");
	}
	
	/**
	 * Tests that the train handles negative power inputs
	 */
	@Test
	public void TestTrainLowPower()
	{
		TrainModel train = new TrainModel(0.2, 65.5, "Brandon", 241);
		
		//Tests that the train does not accept a negative power
		train = new TrainModel(0.2, 62.2, "Ashley", 300);
		train.setSetpoint(-100);
		assertTrue(train.getVelocity() == 0);
		//System.out.println("Train does not accept negative power inputs");
	}
	
	/**
	 * Tests that the train handles a power input of zero
	 */
	@Test
	public void TestTrainNoPower()
	{
		TrainModel train = new TrainModel(0.2, 65.5, "Brandon", 241);

		train.setSetpoint(0);
		assertTrue(train.getVelocity() == 0);
		//System.out.println("Train does not accept 0 power input");
	}	
}
