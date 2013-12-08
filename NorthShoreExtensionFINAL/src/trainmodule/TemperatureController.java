/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

/**
 * This class simulates the passengers movement at stations but 
 * also includes the conductor on all moving trains
 */
public class TemperatureController
{
	private double TEMPERATURE_DELTA = 1.0;		//This is the change in temperature ratio

	private double outsideTemperature;			//The outside temperature
	private double trainTemperature;			//The current train temperature
	
	/**
	 * This constructor creates the internal temperature of the train
	 * It is set to the outside temperatue at first
	 */
	public TemperatureController(double temp)
	{
		outsideTemperature = temp;
		trainTemperature = temp;
	}
	
	/**
	 * This method returns the train temperature (degrees F)
	 */	
	public double getTrainTemperature()
	{		
		return trainTemperature;
	}
	
	/**
	 * This method sets the outside temperature (degrees F)
	 */	
	public void setoutsideTemperature(double temp)
	{		
		outsideTemperature = temp;
	}
	
	/**
	 * This method adds heat to the train so that the temperature increases
	 */	
	public void heatTrain()
	{		
		trainTemperature = trainTemperature + TEMPERATURE_DELTA + 0.01 * (outsideTemperature - trainTemperature);
	}	
	
	/**
	 * This method removes heat from the train so that the temperature decreases
	 */
	public void coolTrain()
	{		
		trainTemperature = trainTemperature - TEMPERATURE_DELTA + 0.01 * (outsideTemperature - trainTemperature);
	}	
}
