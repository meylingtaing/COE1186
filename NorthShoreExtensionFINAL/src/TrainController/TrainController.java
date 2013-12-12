package TrainController;

import java.util.Stack;

import ctc.Route;

public class TrainController implements Runnable {
	
	public int id;
	public trainmodule.TrainModel model;
	public PIDController pidc;
	public ctc.Route route;
	public GPS gps;
	public Double powerSetpoint;
	public Double speedSetpoint;
	public Double speedSetpointSelf; // TODO:	implement logic to manage
	public Double speedSetpointMBO;  //			various speed setpoints
	public Double speedSetpointCTC;
	public Double speed;
	public Double authoritySetpointMoving;
	public int authoritySetpointFixed;
	public trackModel.Block currentBlock;
	public trackModel.Block nextBlock;
	public Boolean doorsOpen;
	public Boolean lightsOn;
	public int passengerCount;
	public Vector position;
	public int temperatureSetpoint;
	public long loopsPS = 5;
	public long optimalTime = 1000000000 / loopsPS; // One second divided by loops per second.
	public double delta;
	
	//private GUI UI;
	//public GUIController guic = null;
	//public Thread UIThread;
	
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
			
			//cruiseControl();
			//maintainAuthority();
			
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
					Thread.sleep( delay / 1000000 );
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Added this just so I can put an ID in the train
	 */
	public TrainController()
	{
		this(1);
	}

	public TrainController(int id) {
		this.id = id;
		model = new trainmodule.TrainModel(1 / loopsPS, /*null,*/ 70.2, "???", id);
		pidc = new PIDController(37103.9);
		//route = new ctc.Route();
		gps = new GPS();
		powerSetpoint = 0.0;
		speedSetpoint = 0.0;
		speedSetpointSelf = 0.0;
		speedSetpointMBO = 0.0;
		speedSetpointCTC = 0.0;
		speed = 0.0;
		authoritySetpointMoving = 0.0;
		authoritySetpointFixed = 0;
		doorsOpen = false;
		lightsOn = false;
		passengerCount = 0;
		position = new Vector();
		temperatureSetpoint = 68;
	}
	
	public void openDoors() {
		model.openDoors();
	}
	
	public void closeDoors() {
		model.closeDoors();
	}
	
	public void turnOnLights() {
		model.turnLightsOn();
	}
	
	public void turnOffLights() {
		model.turnLightsOff();
	}
	
	public Vector getPosition() {
		return null;
	}
	
	public Boolean announceStation() {
		return false;
	}
	
	public Boolean ensureSpeedLimit() {
		return true;
	}
	
	public Boolean setPower(Double level) {
		return false;
	}
	
	public Double brakes(Double level) {
		return model.pullBrake(level);
	}
	
	public void emergencyBrakes() {
		model.pullEmergencyBrake();
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
	
	public void cruiseControl(Double delta) {
		
		// Updates train model with delta T
		model.SetDeltaT(delta);
		
		// Sets power and updates it based on the current speedd
		speed = model.setSetpoint(powerSetpoint);
		powerSetpoint = pidc.getPower(speedSetpoint, speed, delta);
		System.out.println("Power Setpoint: " + powerSetpoint + "\tSpeed: " + speed);
		
		currentBlock = nse.MainController.transitSystem.trainPositions.get(this.id).getCurrBlock();
		//nextBlock = nse.MainController.transitSystem.trainPositions.get(this.id).getCurrBlock().getNextBlockId();

	}
	
	public void maintainAuthority(double delta) {
		Double speed2 = speed * 5.0 / 18.0; // conversion rate from km/h to m/s
		Double distanceTraveled = speed2 * delta * optimalTime * .000000001; // convert to seconds
		authoritySetpointMoving = authoritySetpointMoving - distanceTraveled;
		//int output = authoritySetpointMoving.intValue();
//		System.out.println("Authority:" + output + "\t" + distanceTraveled);
//		if(guic != null)
//			guic.setAuthorityText(output);
	}
	
	public Boolean isTrainStopped() {
		if(speed == 0.0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Double getTrainLength() {
		// TODO: get from train model, need to implement getter.
		return 0.0;
	}
	
	public Double getAuthorityMoving() {
		return authoritySetpointMoving;
	}
	
	public int getAuthorityFixed() {
		return authoritySetpointFixed;
	}
	
	public Boolean setAuthorityMoving(Double distance) {
		authoritySetpointMoving = distance;
//		guic.setAuthorityText(authoritySetpointMoving.intValue());
		return true;
	}
	
	public Boolean setAuthorityFixed(int distance) {
		authoritySetpointFixed = distance; 
		return true;
	}
	
	public Double getMaxAcceleration() {
		// TODO: get from train model, need to implement getter.
		return 0.0;
	}
	
	public Double getMaxDeceleration() {
		// TODO: get from train model, need to implement getter.
		return 0.0;
	}
	
	public int getPassengerCount() {
		return model.getPassengerNumber();
	}
	
	public Double getTotalTrainMass() {
		// TODO: get from train model, need to implement getter.
		return 0.0;
	}
	
	public Double getPower() {
		return powerSetpoint;
	}

	public trainmodule.TrainModel getModel() {
		return model;
	}
	
	public static void main(String[] args) {
		System.out.println("Test1");
		TrainController controller = new TrainController();
		System.out.println("Test2");
	}

}
