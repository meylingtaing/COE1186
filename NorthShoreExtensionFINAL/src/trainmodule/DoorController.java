/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

/**
 * This class handles the operation and state of the doors
 * on the train
 */
public class DoorController
{
	/**Holds the doors state (false = closed, true = open)*/
	private boolean doors;
	
	/**This is the constructor
	 * Initializes the doors to closed state
	 */
	public DoorController()
	{
		doors = false;
	}
	
	/**This method opens the doors
	 */
	public void openDoors()
	{
		doors = true;
	}
	
	/**This method closes the doors
	 */
	public void closeDoors()
	{
		doors = false;
	}
	
	/**This method gets doors state
	 */
	public String getDoors()
	{
		if (doors)
			return "Open";
		else
			return "Closed";
	}
}
