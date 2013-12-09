/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

import java.lang.*;

import nse.MainController;

/**
 * This class simulates the physics of the trains engine.
 * It is capable of taking inputs such as power and converting
 * that to a speed.
 */
public class EngineModel
{	
	public static final double MAX_GRADIENT = 0.60;						//The max gradient (percentage) the train can handle (in the spec)
	public static final double MAX_SPEED = 70000.0;						//The max speed (m/s) the train can go (in the spec)
	public static final double MEDIUM_ACCELERATION = 0.5;				//A comfortable acceleration (m/s^2) speed (in the spec)
	public static final double TOTAL_MAX_POWER = 120000;					//The max power (watt) input the train can handle (in the spec)
	public static final double MEDIUM_DECELERATION = 1.2;				//A comfortable braking deceleration (m/s^2) (in the spec)
	public static final double EMERGENCY_DECELERATION = 4.095;			//The emergency braking deceleration (m/s^2) of the train (in the spec)
	public static final double GRAVITY = 9.8067;						//The gravitational acceleration constant (m/s^2) (in the spec)
	public static final double FRICTION_COEFFICIENT = 0.1;				//The kinetic frictional (unitless) constant (in the spec)
	public static final double STATIC_FRICTION_COEFFICIENT = 0.5;		//The static frictional (unitless) constant (in the spec)
	public static final double FRICTION = 2.6667 * Math.pow(10, -2);	//The actual frictional acceleration (m/s^2) for the train
	
	private double maxPowerChange;
	private double maxPower;			//The max power (watt) increase in a time step		
	private double lastPower;			//The power (watt) input in a previous time step
	private double currentVelocity;		//The current velocity (m/s) the train is traveling
	private double currentGradient;		//The current slope (percentage)
	private boolean engineFailure;		//Holds the engine status (functional = false, broken = true)
	private boolean brakeFailure;		//Holds the brake status (functional = false, broken = true)
	private boolean eBrake;				//Holds the emergency brake status (not pulled = false, pulled = true)
	private double setpoint;			//The current setpoint power
	private double deltaT;				//The time step (seconds)
	private int trainNum;				//Holds the current train number
	
	/**
	 * This constructor creates an instance of the train engine
	 */
	public EngineModel(double time, int id)
	{
		eBrake = false;
		setpoint = 0;
		currentVelocity = 0;
		currentGradient = 0;
		engineFailure = false;
		brakeFailure = false;
		deltaT = time;
		trainNum = id;
		lastPower = 0;
		deltaT = 0.2;
	}
	
	/**
	 * This method calculates the speed after the brake is pulled
	 */	
	public double pullBrake(double load, double mass)
	{		
		if (brakeFailure)
		{
			load = 0;
		}
		
		double angle = Math.atan(currentGradient / 100);	
		
		currentVelocity = currentVelocity - FRICTION * deltaT - load * MEDIUM_DECELERATION * deltaT;
		
		if (currentVelocity < 0)
		{
			currentVelocity = 0;
		}
		
		MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		
		return currentVelocity;
	}
	
	/**
	 * This method calculates the speed after the emergency brake is pulled
	 */	
	public double pullEmergencyBrake(double mass)
	{		
		double angle = Math.atan(currentGradient / 100);	
		
		currentVelocity = currentVelocity - FRICTION * deltaT - EMERGENCY_DECELERATION * deltaT;
		
		if (currentVelocity < 0)
		{
			currentVelocity = 0;
		}
		
		MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		
		return currentVelocity;
	}
	
	/**
	 * This method calculates the speed after the setpoint power is given
	 */	
	public double calculateSetpoint(double power, double mass)
	{
		// TODO add in the effects of the slope also make sure it handles negative and positive values
		
		double angle = Math.atan(currentGradient / 100);		//Calculates the angle from the slope
		
		//If an engine failure occurs velocity is only effected by friction and slope
		if (engineFailure)
		{
			power = 0;
			currentVelocity = currentVelocity - FRICTION * deltaT;
			
			if (currentVelocity < 0)
			{
				currentVelocity = 0;
			}
			
			lastPower = 0;
			return currentVelocity;
		}

		if (power < 0)
		{
			power = 0;
		}
		
		//This calculates the comfortable power change
		maxPower = mass * MEDIUM_ACCELERATION * currentVelocity;
		if (power > maxPower && currentVelocity != 0)
		{
			power = maxPower;
		}
		
		if (power > TOTAL_MAX_POWER)
		{
			power = TOTAL_MAX_POWER;
		}
		
		//This calculates the velocity
		double veloc = Math.sqrt((2 * power * deltaT) / (mass));
		currentVelocity = currentVelocity + veloc - 0.01433 * deltaT;
		setpoint = power;
		
		if (currentVelocity < 0)
		{
			currentVelocity = 0;
		}
		else if (currentVelocity > MAX_SPEED)
		{
			return MAX_SPEED;
		}
		
		// TODO fix integrating this deltaT stuff
		//Signals the distance traveled by the train
		if (!MainController.transitSystem.trainPositions.isEmpty())
		{
			MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		}		
		
		System.out.println("Current velocity: " + currentVelocity);
		System.out.println("Current deltaT: " + deltaT);
		return currentVelocity;
	}

	/**
	 * This method returns the status of the brakes
	 */	
	public boolean getBrakeFailure()
	{
		return brakeFailure;
	}
	
	/**
	 * This method sets the status of the brakes
	 */	
	public void setBrakeFailure(boolean fail)
	{
		brakeFailure = fail;
	}
	
	/**
	 * This method returns the status of the engine
	 */	
	public boolean getEngineFailure()
	{
		return engineFailure;
	}
	
	/**
	 * This method sets the status of the engine
	 */	
	public void setEngineFailure(boolean fail)
	{
		engineFailure = fail;
	}
	
	/**
	 * This method returns the current setpoint speed
	 */	
	public double getSetpoint()
	{
		return setpoint;
	}
	
	/**
	 * This method returns the current velocity of the train
	 */	
	public double getSpeed()
	{
		return currentVelocity;
	}
	
	/**
   	 * This method returns delta t
   	 */
    public double getDeltaT()
    {
    	return deltaT;
    }
}