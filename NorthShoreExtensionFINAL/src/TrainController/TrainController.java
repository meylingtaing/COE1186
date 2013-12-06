package TrainController;

import java.util.Stack;

import ctc.Route;

public class TrainController implements Runnable {
	
	public trainmodule.TrainModel model;
	public PIDController pidc;
	public ctc.Route route;
	public GPS gps;
	public Double powerSetpoint;
	public Double speedSetpoint;
	public Double speed;
	public Double authoritySetpointMoving;
	public int authoritySetpointFixed;
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
		model = new trainmodule.TrainModel(1 / loopsPS, /*null,*/ 70.2, "???", id);
		pidc = new PIDController(37103.9);
		//route = new ctc.Route();
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
		
		// TODO: TrainController: Add ID
	}
	
	public Boolean closeDoors() {
		return false;
	}
	
	public Boolean openDoors() {
		return false;
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
	
	public void cruiseControl(Double delta) {
		
		speed = model.setSetpoint(powerSetpoint);
		powerSetpoint = pidc.getPower(speedSetpoint, speed, delta);
		System.out.println("Power Setpoint: " + powerSetpoint);

//		if(speed < speedSetpoint) {
//			powerSetpoint += .5;
//			speed = model.setSetpoint(powerSetpoint);
//		} else if (speed > speedSetpoint) {
//			powerSetpoint -= .5;
//			speed = model.setSetpoint(powerSetpoint);
//		} else {
//			speed = model.setSetpoint(powerSetpoint);
//		}
//		if(guic != null) {
//			if(guic.text_currentSpeed != null)
//				guic.text_currentSpeed.setText(Double.toString(speed));
//				guic.setPowerText(powerSetpoint.intValue());
//		}
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
		authoritySetpointMoving = distance;
//		guic.setAuthorityText(authoritySetpointMoving.intValue());
		return true;
	}
	
	public Boolean setAuthorityFixed(int distance) {
		authoritySetpointFixed = distance;
		return true;
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

	public trainmodule.TrainModel getModel() {
		return model;
	}
	
	public static void main(String[] args) {
		System.out.println("Test1");
		TrainController controller = new TrainController();
		System.out.println("Test2");
	}

}
