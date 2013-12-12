/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

import nse.MainController;

/**
 * This class simulates the physics of the trains engine.
 * It is capable of taking inputs such as power and converting
 * that to a speed.
 */
public class EngineModel
{	
	public static final double MAX_GRADIENT = 0.60;						//The max gradient (percentage) the train can handle (in the spec)
	public static final double MAX_SPEED = 19.4444;						//The max speed (m/s) the train can go (in the spec)
	public static final double MEDIUM_ACCELERATION = 0.5;				//A comfortable acceleration (m/s^2) speed (in the spec)
	public static final double TOTAL_MAX_POWER = 120000;					//The max power (watt) input the train can handle (in the spec)
	public static final double MEDIUM_DECELERATION = 1.2;				//A comfortable braking deceleration (m/s^2) (in the spec)
	public static final double EMERGENCY_DECELERATION = 4.095;			//The emergency braking deceleration (m/s^2) of the train (in the spec)
	public static final double GRAVITY = 9.8067;						//The gravitational acceleration constant (m/s^2) (in the spec)
	public static final double FRICTION_COEFFICIENT = 0.1;				//The kinetic frictional (unitless) constant (in the spec)
	public static final double STATIC_FRICTION_COEFFICIENT = 0.5;		//The static frictional (unitless) constant (in the spec)
	public static final double FRICTION = 0.07433;						//The actual frictional acceleration (m/s^2) for the train
	
	//private double maxPowerChange;
	private double maxPower;			//The max power (watt) increase in a time step		
	//private double lastPower;			//The power (watt) input in a previous time step
	private double currentVelocity;		//The current velocity (m/s) the train is traveling
	private double currentGradient;		//The current slope (percentage)
	private boolean engineFailure;		//Holds the engine status (functional = false, broken = true)
	private boolean brakeFailure;		//Holds the brake status (functional = false, broken = true)
	//private boolean eBrake;				//Holds the emergency brake status (not pulled = false, pulled = true)
	private double setpoint;			//The current setpoint power
	private double deltaT;				//The time step (seconds)
	private int trainNum;				//Holds the current train number
	
	/**
	 * This constructor creates an instance of the train engine
	 */
	public EngineModel(double time, int id)
	{
		//eBrake = false;
		setpoint = 0;
		currentVelocity = 0;
		currentGradient = 0;
		engineFailure = false;
		brakeFailure = false;
		deltaT = time;
		trainNum = id;
		//lastPower = 0;
		deltaT = 0.2;//###################################TAKE THIS OUT#################
	}
	
	/**
	 * This method calculates the speed after the brake is pulled
	 */	
	public double pullBrake(double load, double mass)
	{	
		//Gets the current block slope
		if (!MainController.transitSystem.trainPositions.isEmpty())
		{
			currentGradient = MainController.transitSystem.trainPositions.get(trainNum).getCurrBlock().getGrade();
		}
		
		double angle = Math.atan(currentGradient / 100);
		double slopeAccel;
		
		if (currentGradient > 0)
		{
			angle = angle * -1;
		}
		
		slopeAccel = GRAVITY * Math.sin(angle);
		
		setpoint = 0;
		
		if (brakeFailure)
		{
			load = 0;
			//System.out.println("Load = 0");
		}
		
		if (load > 1)
		{
			load = 1;
		}
		else if (load < 0)
		{
			load = 0;
		}			
		
		currentVelocity = currentVelocity - FRICTION * deltaT - load * (MEDIUM_DECELERATION - slopeAccel) * deltaT;
		
		if (currentVelocity < 0)
		{
			currentVelocity = 0;
		}
		
		//Signals the distance traveled by the train
		if (TrainModel.demo == false && !MainController.transitSystem.trainPositions.isEmpty())
		{
			MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		}
		
		return currentVelocity;
	}
	
	/**
	 * This method calculates the speed after the emergency brake is pulled
	 */	
	public double pullEmergencyBrake(double mass)
	{		
		//Gets the current block slope
		if (TrainModel.demo == false && !MainController.transitSystem.trainPositions.isEmpty())
		{
			currentGradient = MainController.transitSystem.trainPositions.get(trainNum).getCurrBlock().getGrade();
		}
		
		double angle = Math.atan(currentGradient / 100);
		double slopeAccel;
		
		if (currentGradient > 0)
		{
			angle = angle * -1;
		}
		
		slopeAccel = GRAVITY * Math.sin(angle);
		
		setpoint = 0;	
		
		currentVelocity = currentVelocity - FRICTION * deltaT - (EMERGENCY_DECELERATION - slopeAccel) * deltaT;
		
		if (currentVelocity < 0)
		{
			currentVelocity = 0;
		}
		
		//Signals the distance traveled by the train
		if (TrainModel.demo == false && !MainController.transitSystem.trainPositions.isEmpty())
		{
			MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		}
		
		return currentVelocity;
	}
	
	/**
	 * This method calculates the speed after the setpoint power is given
	 */	
	public double calculateSetpoint(double power, double mass)
	{	
		double speedLimit = MAX_SPEED;
		
		//Gets the current block slope and speed limit
		if (TrainModel.demo == false && !MainController.transitSystem.trainPositions.isEmpty())
		{
			currentGradient = MainController.transitSystem.trainPositions.get(trainNum).getCurrBlock().getGrade();//43.496
			speedLimit = MainController.transitSystem.trainPositions.get(trainNum).getCurrBlock().getSpeedLimit() * 0.277778;
		}
		
		double angle = Math.atan(currentGradient / 100);
		double slopeAccel;
		
		if (currentGradient > 0)
		{
			angle = angle * -1;
		}
		
		slopeAccel = GRAVITY * Math.sin(angle);
		
		if (currentGradient < 0)
		{
			angle = angle * -1;
		}
		
		//If an engine failure occurs velocity is only effected by friction and slope
		if (engineFailure)
		{
			power = 0;
			currentVelocity = currentVelocity - FRICTION * deltaT;
			
			if (currentVelocity < 0)
			{
				currentVelocity = 0;
			}
			
			setpoint = 0;
			//lastPower = 0;
			return currentVelocity;
		}

		if (power < 0)
		{
			power = 0;
		}
		
		//This calculates the comfortable power change
		maxPower = mass * (MEDIUM_ACCELERATION - slopeAccel) * currentVelocity;
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
		currentVelocity = currentVelocity + veloc - FRICTION * deltaT;
		setpoint = power;
		
		if (currentVelocity < 0)
		{
			currentVelocity = 0;
		}
		else if (currentVelocity > speedLimit)
		{
			/*currentVelocity = speedLimit;
			System.out.println("Speed Limit: " + speedLimit);
			System.out.println("Current Speed: " + currentVelocity);*/
		}
		else if (currentVelocity > MAX_SPEED)
		{
			currentVelocity = MAX_SPEED;
		}		
		
		//Signals the distance traveled by the train
		if (TrainModel.demo == false && !MainController.transitSystem.trainPositions.isEmpty())
		{
			MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		}		
		
		//System.out.println("Current velocity: " + currentVelocity);
		//System.out.println("Current deltaT: " + deltaT);
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