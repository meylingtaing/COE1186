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
	
	public ArrayList<Integer> getFailures()
	{
		return failures;
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
}
