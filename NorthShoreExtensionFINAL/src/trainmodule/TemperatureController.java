package trainmodule;

public class TemperatureController
{

	private double outsideTemperature;
	private double trainTemperature;
	private double temperatureDelta = 1.0;
	
	public TemperatureController(double temp)
	{
		outsideTemperature = temp;
		trainTemperature = temp;
	}
	
	public double getTrainTemperature()
	{		
		return trainTemperature;
	}
	
	public void setoutsideTemperature(double temp)
	{		
		outsideTemperature = temp;
	}
	
	public void heatTrain()
	{		
		trainTemperature = trainTemperature + temperatureDelta + 0.01 * (outsideTemperature - trainTemperature);
	}	
	
	public void coolTrain()
	{		
		trainTemperature = trainTemperature - temperatureDelta + 0.01 * (outsideTemperature - trainTemperature);
	}	
}
