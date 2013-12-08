/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

import java.util.ArrayList;

/**
 * This class simulates failures occurring
 */
public class TrainFailure
{
	private static final Integer SIGNAL = new Integer(0);		//Stands for signal failure
	private static final Integer BRAKE = new Integer(1);		//Stands for brake failure
	private static final Integer ENGINE = new Integer(2);		//Stands for engine failure
	
	ArrayList<Integer> failures;			//Holds all the current train failures	
	
	/**
	 * This constructor initializes the train to a no failure state
	 */
	public TrainFailure()
	{
		failures = new ArrayList<Integer>();		
	}
	
	/**
	 * This method returns the failures as an ArrayList on the train
	 */	
	public ArrayList<String> getFailures()
	{
		ArrayList<String> fail = new ArrayList<String>();
		
		for (Integer i : failures)
		{
			switch (i)
			{
				case 0:
					fail.add("Signal");
					break;
				case 1:
					fail.add("Brake");
					break;
				case 2:
					fail.add("Engine");
					break;
			}
		}
		
		return fail;
	}
	
	/**
	 * This method fixes all train failures
	 */	
	public void fixAllFailures()
	{
		failures.clear();
	}
	
	/**
	 * This method throws a signal failure
	 */	
	public void throwSignalFailure()
	{
		failures.add(SIGNAL);
	}
	
	/**
	 * This method fixes a signal failure
	 */	
	public void fixSignalFailure()
	{
		failures.remove(SIGNAL);
	}
	
	/**
	 * This method throws an engine failure
	 */	
	public void throwEngineFailure()
	{
		failures.add(ENGINE);
	}
	
	/**
	 * This method fixes an engine failure
	 */	
	public void fixEngineFailure()
	{
		failures.remove(ENGINE);
	}
	
	/**
	 * This method throws a brake failure
	 */	
	public void throwBrakeFailure()
	{
		failures.add(BRAKE);
	}
	
	/**
	 * This method fixes a brake failure
	 */	
	public void fixBrakeFailure()
	{
		failures.remove(BRAKE);
	}
	
	/**
	 * This method returns the brake state (true = broken, false = functional)
	 */	
	public boolean isBrakeBroken()
	{
		return failures.contains(BRAKE);
	}
	
	/**
	 * This method returns the signal state (true = broken, false = functional)
	 */	
	public boolean isSignalBroken()
	{
		return failures.contains(SIGNAL);
	}
	
	/**
	 * This method returns the engine state (true = broken, false = functional)
	 */	
	public boolean isEngineBroken()
	{
		return failures.contains(ENGINE);
	}
}
