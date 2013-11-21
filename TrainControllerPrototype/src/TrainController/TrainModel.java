package TrainController;

public class TrainModel {
	
	public Double length = 32.2; // m
	public Double maxAcceleration = 0.5; // m/s^2
	public Double maxDeceleration = 1.2; // m/s^2
	public Double maxPower = 120.0; // Watts
	public Double currentPower = 0.0;
	public Double maxSpeed = 70.0; // km/h
	public Double angle = 0.0;
	public Double maxAcc = 5.0;
	public TrainController parent;
	
	public TrainModel(TrainController parent) {
		this.parent = parent;
	}
	
	public boolean openDoors() {
		System.out.println("[Train Model]: Doors Opened");
		return true;
	}
	
	public boolean closeDoors() {
		System.out.println("[Train Model]: Doors Closed");
		return true;
	}
	
	public void pullBreak(Double percent) {
		
	}
	
	public Double setPower(Double power, Double currentSpeed, Double dt) {
		Double maxIncrease = dt * maxAcc;
		Double speed = power * (maxSpeed / maxPower); 
		if(currentSpeed + maxIncrease < speed) {
			speed = currentSpeed + maxIncrease;
		}
		//Double t = parent.delta * parent.optimalTime; // time in seconds elapsed since last cycle
		//speed = speed * Math.sin(angle);
		//angle += 0.01;
		return speed;
	}

}
