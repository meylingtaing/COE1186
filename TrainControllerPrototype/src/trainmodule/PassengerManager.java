package trainmodule;

import java.util.Random;

public class PassengerManager
{
	private int passengerCount;
	private double totalPassengerMass;
	private double passengerMass = 150.0;
	private Random generator;
	
	public PassengerManager()
	{
		passengerCount = 1;		//The conductor
		totalPassengerMass = passengerMass;
		generator = new Random();
	}
	
	public double getTotalPassengerMass()
	{
		return totalPassengerMass;
	}
	
	public int getPassengerCount()
	{
		return passengerCount;
	}
	
	public int updatePassengers()
	{
		passengerCount = (generator.nextInt() % 222) + 1;
		totalPassengerMass = passengerCount * passengerMass;
		return passengerCount;
	}
	
	public void clearPassengers()
	{
		passengerCount = 1;
	}
}
