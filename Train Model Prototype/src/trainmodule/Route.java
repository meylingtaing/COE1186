package trainmodule;

import java.util.*;

public class Route
{
	Stack<String> route;
	String currentStop;
	String nextStop;
	
	public Route(Stack<String> list)
	{
		route = list;
		currentStop = "Yard";
		nextStop = list.pop();
	}
	
	public String getNextStop()
	{		
		return nextStop;
	}
	
	public void atStop()
	{		
		currentStop = nextStop;
		
		if (!route.isEmpty())
			nextStop = route.pop();
	}
}
