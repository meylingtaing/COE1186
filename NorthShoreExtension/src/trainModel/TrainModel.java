/**
 * Placeholder train model class
 */
package trainModel;

public class TrainModel 
{
	private String name;
	private int id;
	private double velocity = 10.0;
	
	public TrainModel(String name, int id)
	{
		this.name = name;
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public double getVelocity()
	{
		return velocity;
	}
	
	public void setVelocity(double velocity)
	{
		this.velocity = velocity;
	}
}
