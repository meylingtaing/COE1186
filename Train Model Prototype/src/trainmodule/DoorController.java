package trainmodule;

public class DoorController
{
	private boolean doors = false;
	
	public DoorController()
	{
		
	}
	
	public void openDoors()
	{
		doors = true;
	}
	
	public void closeDoors()
	{
		doors = false;
	}
	
	public String getDoors()
	{
		if (doors)
			return "Open";
		else
			return "Closed";
	}
}
