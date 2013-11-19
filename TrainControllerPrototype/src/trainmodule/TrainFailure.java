package trainmodule;

import java.util.ArrayList;

public class TrainFailure
{
	ArrayList<Integer> failures;
	Integer signal;
	Integer brake;
	Integer engine;	
	
	public TrainFailure()
	{
		failures = new ArrayList<Integer>();
		signal = new Integer(0);
		brake = new Integer(1);
		engine = new Integer(2);
	}
	
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
	
	public void fixAllFailures()
	{
		failures.clear();
	}
	
	public void throwSignalFailure()
	{
		failures.add(signal);
	}
	
	public void fixSignalFailure()
	{
		failures.remove(signal);
	}
	
	public void throwEngineFailure()
	{
		failures.add(engine);
	}
	
	public void fixEngineFailure()
	{
		failures.remove(engine);
	}
	
	public void throwBrakeFailure()
	{
		failures.add(brake);
	}
	
	public void fixBrakeFailure()
	{
		failures.remove(brake);
	}
	
	public boolean isBrakeBroken()
	{
		return failures.contains(brake);
	}
	
	public boolean isSignalBroken()
	{
		return failures.contains(signal);
	}
	
	public boolean isEngineBroken()
	{
		return failures.contains(engine);
	}
}
