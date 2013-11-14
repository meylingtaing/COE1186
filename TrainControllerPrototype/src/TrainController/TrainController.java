package TrainController;

public class TrainController implements Runnable {
	
	private TrainModel model;
	private GPS gps;
	private Double powerSetpoint;
	private Double speedSetpoint;
	private Double speed;
	private Double authoritySetpointMoving;
	private Block authoritySetpointFixed;
	private Boolean doorsOpen;
	private Boolean lightsOn;
	private int passengerCount;
	private Vector position;
	private int temperatureSetpoint;
	public long loopsPS = 5;
	public long optimalTime = 1000000000 / loopsPS; // One second divided my loops per second.
	public double delta;
	
	private GUI UI;
	public GUIController guic = null;
	public Thread UIThread;
	
	public void run() {
		long lastLoopTime = System.nanoTime();
		long lastFpsTime = 0;
		int fps = 0;
		int fpsOut = 0;
		int iteration = 0;
		while(true) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			delta = updateLength / ((double)optimalTime);
			
			// update the frame counter
			lastFpsTime += updateLength;
			fps++;
			
			cruiseControl();
			
			//System.out.println(iteration++ + ":\t\t" + fpsOut + "\t" + delta + "\t" + lastLoopTime + "\t" + optimalTime);
			System.out.println(iteration++ + ":\t\t" + speedSetpoint + "\t" + speed);
			
			// update our FPS counter if a second has passed since
			// we last recorded
			if (lastFpsTime >= 1000000000)
			{
				fpsOut = fps;
				lastFpsTime = 0;
				fps = 0;
			}
			
			long delay = lastLoopTime - System.nanoTime() + optimalTime;
			if( delay > 0 ) {
				try {
					Thread.sleep( delay / 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public TrainController() {
		model = new TrainModel(this);
		gps = new GPS();
		powerSetpoint = 0.0;
		speedSetpoint = 0.0;
		speed = 0.0;
		authoritySetpointMoving = 0.0;
		authoritySetpointFixed = new Block();
		doorsOpen = false;
		lightsOn = false;
		passengerCount = 0;
		position = new Vector();
		temperatureSetpoint = 68;		
		
	}
	
	public Boolean closeDoors() {
		return model.closeDoors();
	}
	
	public Boolean openDoors() {
		return model.openDoors();
	}
	
	public Boolean turnOnLights() {
		return false;
	}
	
	public Boolean turnOffLights() {
		return false;
	}
	
	public Vector getPosition() {
		return null;
	}
	
	public Boolean announceStation() {
		return false;
	}
	
	public Boolean ensureSpeedLimit() {
		return false;
	}
	
	public Boolean setPower(Double level) {
		return false;
	}
	
	public Boolean brakes(Double level) {
		return false;
	}
	
	public Boolean emergencyBrakes(Boolean engadged) {
		return false;
	}
	
	public Double getSpeedSetpoint() {
		return speedSetpoint;
	}
	
	public Double setSpeedSetpoint(Double newSpeed) {
		if(newSpeed >= 0) {
			speedSetpoint = newSpeed;
		}
		System.out.println("[Train Controller]: Speed Setpoint now: " + speedSetpoint);
		return speedSetpoint;
	}
	
	private void cruiseControl() {
		if(speed < speedSetpoint) {
			powerSetpoint += .5;
			speed = model.setPower(powerSetpoint);
		} else if (speed > speedSetpoint) {
			powerSetpoint -= .5;
			speed = model.setPower(powerSetpoint);
		} else {
			speed = model.setPower(powerSetpoint);
		}
		if(guic != null) {
			//if(guic.text_currentSpeed != null)
				guic.text_currentSpeed.setText(Double.toString(speed));
		}
	}
	
	public Boolean isTrainStopped() {
		return false;
	}
	
	public Double getTrainLength() {
		return 0.0;
	}
	
	public Double getAuthorityMoving() {
		return 0.0;
	}
	
	public Block getAuthorityFixed() {
		return null;
	}
	
	public Boolean setAuthorityMoving(Double distance) {
		return false;
	}
	
	public Boolean setAuthorityFixed(Block distance) {
		return false;
	}
	
	public Double getMaxAcceleration() {
		return 0.0;
	}
	
	public Double getMaxDeceleration() {
		return 0.0;
	}
	
	public int getPassengerCount() {
		return 0;
	}
	
	public Double getTotalTrainMass() {
		return 0.0;
	}
	
	public Double getPower() {
		return 0.0;
	}

	public static void main(String[] args) {
		System.out.println("Test1");
		TrainController controller = new TrainController();
		System.out.println("Test2");
	}

}
