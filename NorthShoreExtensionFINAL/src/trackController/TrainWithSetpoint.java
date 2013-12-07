package trackController;

import TrainController.TrainController;

public class TrainWithSetpoint {
	
	public TrainController train;
	public double suggestedSetPoint;
	
	public TrainWithSetpoint(TrainController t,double setPt)
	{
		train = t;
		suggestedSetPoint = setPt;
	}

}
