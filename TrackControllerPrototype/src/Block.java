
public class Block {

	public String id;
	public int speedLimit;
	public Switch theSwitch;
	public boolean station;
	public Train train;
	
	public Block(String ID, int SpeedLimit, Switch s, boolean Station)
	{
		id = ID;
		speedLimit = SpeedLimit;
		theSwitch = s;
		train = null;
	}
	
	public Train getTrainInBlock()
	{
		return train;
	}
	
	//This would typically be done by the track model
	public void setTrainInBlock(Train t)
	{
		train = t;
	}
	
	public Switch getSwitch()
	{
		return theSwitch;
	}
	
	public int getSpeedLimit()
	{
		return speedLimit;
	}
	
	
	
}
