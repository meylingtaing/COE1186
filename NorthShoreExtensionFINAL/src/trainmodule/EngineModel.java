package trainmodule;

import java.lang.*;

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
		
	private double currentVelocity;
	private double currentGradient;
	private boolean engineFailure;
	private boolean brakeFailure;
	//private double tickTime;
	
	public EngineModel(double time)
	{
		setpoint = 0;
		currentVelocity = 0;
		currentGradient = 0;
		engineFailure = false;
		brakeFailure = false;
		deltaT = time;
	}
	
	public double calculateLoad(double power)
	{		
		if (power == 0)
			return 0;
		else
			return power / maxPower;
	}
	
	public double pullBrake(double load, double mass)
	{		
		if (brakeFailure)
			load = 0;
		else
		{
			setpoint = 0;
		}
		
		if (mass * gravity * Math.sin(Math.atan(currentGradient / 100)) <= mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * staticFrictionCoefficient + maxBrakeDeceleration * load * mass)
			currentVelocity = 0;
		else
			currentVelocity = mass * gravity * Math.sin(Math.atan(currentGradient / 100)) - (mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * frictionCoefficient + maxBrakeDeceleration * load * mass);
		
		return currentVelocity;
	}
	
	public double pullEmergencyBrake(double mass)
	{		
		setpoint = 0;
		
		if (mass * gravity * Math.sin(Math.atan(currentGradient / 100)) <= mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * staticFrictionCoefficient + emergencyDeceleration * mass)
			currentVelocity = 0;
		else
			currentVelocity = mass * gravity * Math.sin(Math.atan(currentGradient / 100)) - (mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * frictionCoefficient + emergencyDeceleration * mass);
		
		return currentVelocity;
	}
	
	public double calculateSetpoint(double power, double mass)
	{
		power = power * 1000;	//Convert kilowatts to watts
		double angle = Math.atan(currentGradient / 100);
		double staticFriction = mass * gravity * Math.cos(angle) * staticFrictionCoefficient; //* staticFrictionCoefficient;
		double trainForce = mass * gravity * Math.sin(angle);
		//mass = mass * 1000;
		
		if (engineFailure)
		{
			power = 0;
			setpoint = 0;
		}
		
		if (power > maxPower)
			power = maxPower;
		if (power < 0)
			power = 0;
		
		
		//currentVelocity = (power / (staticFriction - trainForce)) + currentVelocity;
		currentVelocity = (power / (staticFriction)) - (gravity * Math.sin(angle) * deltaT) + currentVelocity;
		
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