package TrainController;

public class PIDController {
	
	private Double previousError;
	private Double integral;
	private Double mass;
	private Double setpoint;
	private Double speed;
	private Double power1;
	private Double power2;
	private Double dt;
	
	private Double Kp;
	private Double Ki;
	private Double Kd;
	
	public PIDController(Double mass) {
		this.previousError = 0.0;
		this.integral = 0.0;
		this.mass = mass;
		this.power1 = 0.0;
		this.power2 = 0.0;
		
		this.Kp = mass/1000;
		this.Ki = 0.0;
		this.Kd = 0.0;
	}
	
	private Double getError() {
		return setpoint - speed;
	}
	
	private Double getIntegral() {
		return integral + getError()*dt;
	}
	
	private Double getDerivative() {
		if(dt != 0) {
			return (getError() - previousError) / dt;
		} else {
			return 0.0;
		}
	}
	
	public Double getOutput() {
		Ki = power1 - power2;
		Double output = Kp*getError() + Ki*getIntegral() + Kd*getDerivative();
		previousError = getError();
		power2 = power1;
		power1 = output;
		return output;
	}
	
	public Double getPower(Double setpoint, Double speed, Double dt) {
		this.setpoint = setpoint;
		this.speed = speed;
		this.dt = dt;
		
		return getOutput();
	}

	public static void main(String[] args) {
		
		TrainModel model = new TrainModel(null);
		PIDController controller = new PIDController(37103.9);
		Double speed = 0.0;
		Double speedSetpoint = 50.0;
		Double powerSetpoint = 0.0;
		
		long loopsPS = 100;
		long optimalTime = 1000000000 / loopsPS; // One second divided by loops per second.
		double delta;
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

			
			speed = model.setPower(powerSetpoint, speed, delta*optimalTime/1000000000);
			powerSetpoint = controller.getPower(speedSetpoint, speed, delta*optimalTime/1000000000);
			
			System.out.format("%d \t SpeedSet: %.3f \t Speed: %.3f \t Power %.3f \t dt: %.3f \t FPS: %d\n", iteration++, speedSetpoint, speed, powerSetpoint, delta*optimalTime/1000000000, fpsOut);
			
			
			
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

}
