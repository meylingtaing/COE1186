package trainmodule;

import java.lang.*;
import nse.MainController;

public class EngineModel
{	
	private double maxGradient = 0.60;
	private double maxSpeed = 70000.0;
	private double mediumAcceleration = 0.5;
	private double maxPower = 120000;
	private double mediumDeceleration = 1.2;
	private double emergencyDeceleration = 4.095;
	private double gravity = 9.8067;
	private double frictionCoefficient = 0.1;
	private double staticFrictionCoefficient = 0.5;
	private double setpoint;
	private boolean eBrake = false;
	private double deltaT;
	private int trainNum;
	private double friction = 2.6667 * Math.pow(10, -2);
	private double maxPowerChange = 5;
	
	private double lastPower;	
	private double currentVelocity;
	private double currentGradient;
	private boolean engineFailure;
	private boolean brakeFailure;
	
	public EngineModel(double time, int id)
	{
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
	
	public double pullBrake(double load, double mass)
	{		
		if (brakeFailure)
			load = 0;
		
		double angle = Math.atan(currentGradient / 100);
		//double staticFriction = mass * gravity * Math.cos(angle) * staticFrictionCoefficient;		
		
		currentVelocity = currentVelocity - friction * deltaT - load * mediumDeceleration * deltaT;
		
		if (currentVelocity < 0)
			currentVelocity = 0;
		
		MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		
		return currentVelocity;
	}
	
	public double pullEmergencyBrake(double mass)
	{		
		double angle = Math.atan(currentGradient / 100);
		//double staticFriction = mass * gravity * Math.cos(angle) * staticFrictionCoefficient;		
		
		currentVelocity = currentVelocity - friction * deltaT - emergencyDeceleration * deltaT;
		
		if (currentVelocity < 0)
			currentVelocity = 0;
		
		MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		
		return currentVelocity;
	}
	
	public double calculateSetpoint(double power, double mass)
	{
		double angle = Math.atan(currentGradient / 100);
		//double staticFriction = mass * gravity * Math.cos(angle) * staticFrictionCoefficient; 
		
		if (engineFailure)
		{
			power = 0;
			currentVelocity = currentVelocity - friction * deltaT;
			
			if (currentVelocity < 0)
				currentVelocity = 0;
			
			lastPower = 0;
			return currentVelocity;
		}

		if (power < 0)
			power = 0;
		
		maxPower = mass * mediumAcceleration * currentVelocity;
		if (power > maxPower && currentVelocity != 0)
			power = maxPower;
			
		double veloc = Math.sqrt((2 * power * deltaT) / (mass));
		currentVelocity = currentVelocity + veloc - 0.01433 * deltaT;
		
		if (currentVelocity < 0)
			currentVelocity = 0;
		else if (currentVelocity > maxSpeed)
			return maxSpeed;
		
		// TODO fix integrating this deltaT stuff
		MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		System.out.println("Current velocity: " + currentVelocity);
		System.out.println("Current deltaT: " + deltaT);
		return currentVelocity;
	}

	public boolean getBrakeFailure()
	{
		return brakeFailure;
	}
	
	public void setBrakeFailure(boolean fail)
	{
		brakeFailure = fail;
	}
	
	public boolean getEngineFailure()
	{
		return engineFailure;
	}
	
	public void setEngineFailure(boolean fail)
	{
		engineFailure = fail;
	}
	
	public double getSetpoint()
	{
		return setpoint;
	}
	
	public double getSpeed()
	{
		return currentVelocity;
	}
}