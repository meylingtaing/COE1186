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
	private double frictionCoefficient = 0.0015;
	private double staticFrictionCoefficient = 0.01;
		
	private double currentVelocity;
	private double currentGradient;
	private boolean engineFailure;
	private boolean brakeFailure;
	//private double tickTime;
	
	public EngineModel(/*double time*/)
	{
		currentVelocity = 0;
		currentGradient = 0;
		engineFailure = false;
		brakeFailure = false;
		//tickTime = time;
	}
	
	public double calculateLoad(double power)
	{		
		if (power == 0)
			return 0;
		else
			return maxPower / power;
	}
	
	public double pullBrake(double load, double mass)
	{
		if (brakeFailure)
			load = 0;
		
		if (mass * gravity * Math.sin(Math.atan(currentGradient / 100)) <= mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * staticFrictionCoefficient + maxBrakeDeceleration * load * mass)
			currentVelocity = 0;
		else
			currentVelocity = mass * gravity * Math.sin(Math.atan(currentGradient / 100)) - (mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * frictionCoefficient + maxBrakeDeceleration * load * mass);
		
		return currentVelocity;
	}
	
	public double pullEmergencyBrake(double mass)
	{		
		if (mass * gravity * Math.sin(Math.atan(currentGradient / 100)) <= mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * staticFrictionCoefficient + emergencyDeceleration * mass)
			currentVelocity = 0;
		else
			currentVelocity = mass * gravity * Math.sin(Math.atan(currentGradient / 100)) - (mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * frictionCoefficient + emergencyDeceleration * mass);
		
		return currentVelocity;
	}
	
	public double calculateSetpoint(double power, double mass)
	{
		if (engineFailure)
			power = 0;
		
		if (power > 120)
			power = 120;
		else
		{
			if (power < 0)
				power = 0;
			
			double load = calculateLoad(power);
			double partialForce = (load * maxAcceleration) * mass;
			
			if (partialForce + mass * gravity * Math.sin(Math.atan(currentGradient / 100)) <= mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * staticFrictionCoefficient)
				currentVelocity = 0;
			else
				currentVelocity = (power / partialForce) + (mass * gravity * Math.sin(Math.atan(currentGradient / 100)) - mass * gravity * Math.cos(Math.atan(currentGradient / 100)) * frictionCoefficient);
		}
		
		/*double force;
		double partialForce = 0;
		
		if (currentVelocity != 0)
			force = mass * gravity * Math.sin(Math.atan(currentGradient / 100)) - (mass * gravity * frictionCoefficient);
		else
		{
			if (mass * gravity * staticFrictionCoefficient >= mass * gravity * Math.sin(Math.atan(currentGradient / 100)))
			{
				partialForce = mass * gravity * Math.sin(Math.atan(currentGradient / 100)) - mass * gravity * staticFrictionCoefficient;
				force = partialForce;
			}
			else
			{
				partialForce = 0;
				force = 0;
			}
		}
		
		double acceleration = force / mass;
		instantaneousVelocity = currentVelocity + (acceleration * tickTime);
		
		if (power == 0 && currentGradient >= 0)
			return instantaneousVelocity;
		if (power == 0 && currentGradient < 0)
			return -1 * instantaneousVelocity;
		
		if (power > 120)
			power = 120;
		
		if (mass * acceleration > mass * gravity * staticFrictionCoefficient)
			currentVelocity = power / (mass * acceleration);
		
		
		currentVelocity += instantaneousVelocity;
		
		if (currentVelocity > maxSpeed)
			return maxSpeed;*/
		
		return currentVelocity;
	}
	
	private boolean getBrakeFailure()
	{
		return brakeFailure;
	}
	
	private void setBrakeFailure(boolean fail)
	{
		brakeFailure = fail;
	}
	
	private boolean getEngineFailure()
	{
		return engineFailure;
	}
	
	private void setEngineFailure(boolean fail)
	{
		engineFailure = fail;
	}
	
	
}
