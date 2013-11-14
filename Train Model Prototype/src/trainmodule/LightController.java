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
	
	public boolean getLights()
	{
		return lights;
	}
}
