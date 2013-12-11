/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

import java.util.Random;

/**
 * This class simulates the passengers movement at stations but 
 * also includes the conductor on all moving trains
 */
public class PassengerManager
{
	public static final double PASSENGER_MASS = (150.0 * 0.453592);		//Is the individual passenger weight (Kg) on the train
	public static final int MAX_PASSENGERS = 222;		//Is the maximum amount of passengers that can be on the train a time
	
	private int passengerCount;				//Holds the passenger count on the train
	private double totalPassengerMass;		//Holds the total passenger mass on the train
	private Random generator;				//Used to generate random passenger counts
	
	/**
	 * This constructor creates the passenger manager object and initializes the passenger
	 * count to one because the conductor is on the train at all times
	 */
	public PassengerManager()
	{
		passengerCount = 1;						
		totalPassengerMass = PASSENGER_MASS;
		generator = new Random();
	}
	
	/**
	 * This method returns the total passenger mass (Kg)
	 */	
	public double getTotalPassengerMass()
	{
		return totalPassengerMass;
	}
	
	/**
	 * This method returns the passenger count
	 */	
	public int getPassengerCount()
	{
		return passengerCount;
	}
	
	/**
	 * This method updates the passenger count to a new
	 * value simulating passengers leaving and getting on
	 * the train. The range of passengers could be any value
	 * from 1 - 222
	 */	
	public int updatePassengers()
	{
		passengerCount = (Math.abs(generator.nextInt()) % MAX_PASSENGERS) + 1;
		totalPassengerMass = passengerCount * PASSENGER_MASS;
		return passengerCount;
	}
	
	/**
	 * This method gets everyone off of the train on the second to last stop
	 */	
	public void clearPassengers()
	{
		passengerCount = 1;
		totalPassengerMass = passengerCount * PASSENGER_MASS;
	}
}
