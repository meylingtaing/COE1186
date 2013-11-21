package trainmodule;

import java.lang.*;
import nse.MainController;

public class EngineModel
{	
	private double maxGradient = 0.60;
	private double maxSpeed = 70000.0;
	private double maxAcceleration = 0.5 * 0.5 + 0.5;
	private double maxPower = 120000;
	private double maxBrakeDeceleration = 1.8;
	private double emergencyDeceleration = 4.095;
	private double gravity = 9.8067;
	private double frictionCoefficient = 0.1;
	private double staticFrictionCoefficient = 0.5;
	private double setpoint;
	private boolean eBrake = false;
	private double deltaT;
	private int trainNum;
		
	private double currentVelocity;
	private double currentGradient;
	private boolean engineFailure;
	private boolean brakeFailure;
	//private double tickTime;
	
	public EngineModel(double time, int id)
	{
		setpoint = 0;
		currentVelocity = 0;
		currentGradient = 0;
		engineFailure = false;
		brakeFailure = false;
		deltaT = time;
		trainNum = id;
		//deltaT = 0.05;
	}
	
	public double pullBrake(double load, double mass)
	{		
		if (brakeFailure)
			load = 0;
		
		double angle = Math.atan(currentGradient / 100);
		double staticFriction = mass * gravity * Math.cos(angle) * staticFrictionCoefficient;		
		
		currentVelocity = currentVelocity - (staticFriction / mass) * deltaT - load * maxBrakeDeceleration * deltaT;
		
		if (currentVelocity < 0)
			currentVelocity = 0;
		
		MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		return currentVelocity;
	}
	
	public double pullEmergencyBrake(double mass)
	{		
		double angle = Math.atan(currentGradient / 100);
		double staticFriction = mass * gravity * Math.cos(angle) * staticFrictionCoefficient;		
		
		currentVelocity = currentVelocity - (staticFriction / mass) * deltaT - emergencyDeceleration * deltaT;
		
		if (currentVelocity < 0)
			currentVelocity = 0;
		
		MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		return currentVelocity;
	}
	
	public double calculateSetpoint(double power, double mass)
	{
		power = power * 1000;	//Convert kilowatts to watts
		double angle = Math.atan(currentGradient / 100);
		double staticFriction = mass * gravity * Math.cos(angle) * staticFrictionCoefficient; //* staticFrictionCoefficient;
		
		if (engineFailure)
		{
			power = 0;
			currentVelocity = currentVelocity - (staticFriction / mass) * deltaT;
			
			if (currentVelocity < 0)
				currentVelocity = 0;
			
			return currentVelocity;
		}
		
		if (power > maxPower)
			power = maxPower;
		if (power < 0)
			power = 0;
		
		currentVelocity = (power / (staticFriction)) + currentVelocity - (staticFriction / mass) * deltaT;
		
		if (currentVelocity < 0)
			currentVelocity = 0;
		else if (currentVelocity > maxSpeed)
			return maxSpeed;
		
		MainController.transitSystem.trainPositions.get(trainNum).moveTrain(currentVelocity * deltaT);
		
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