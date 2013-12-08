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
	
	public LightController()
	{
		lights = false;
	}
	
	public void turnLightsOn()
	{
		lights = true;
	}
	
	public void turnLightsOff()
	{
		lights = false;
	}
	
	public String getLights()
	{
		if (lights)
			return "On";
		else
			return "Off";
	}
}
