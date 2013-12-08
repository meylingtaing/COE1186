/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

/**
 * This class handles the operation of the train lights
 */
public class LightController
{
	private boolean lights;		//This variable holds the lights state
	
	/**This is the constructor
	 * Initializes the lights to the off state
	 */
	public LightController()
	{
		lights = false;
	}
	
	/**This method turns the lights on
	 */
	public void turnLightsOn()
	{
		lights = true;
	}
	
	/**This method turns the lights off
	 */
	public void turnLightsOff()
	{
		lights = false;
	}
	
	/**This method returns the lights state
	 */
	public String getLights()
	{
		if (lights)
			return "On";
		else
			return "Off";
	}
}
