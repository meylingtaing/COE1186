package trackController;

import TrainController.TrainController;

public class TrainWithAuthority {

	public TrainController train;
	public int authority;
	
	public TrainWithAuthority(TrainController t, int a)
	{
		train = t;
		authority = a;
	}
	
	
	
}
