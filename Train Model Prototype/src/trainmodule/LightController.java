package trainmodule;

public class LightController
{
	private boolean lights = false;
	
	public LightController()
	{
		
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
