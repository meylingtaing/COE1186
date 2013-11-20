package TrainController;

public class TrainTest {

	public static void main(String[] args) {
		TrainController train = new TrainController();
		
		long loopsPS = 5;
		long optimalTime = 1000000000 / loopsPS; // One second divided by loops per second.
		double delta;
		long lastLoopTime = System.nanoTime();
		long lastFpsTime = 0;
		int fps = 0;
		int fpsOut = 0;
		int iteration = 0;
		
		train.setAuthorityMoving(5000.0);
		train.setSpeedSetpoint(25.0);
		
		while(true) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			delta = updateLength / ((double)optimalTime);
			
			// update the frame counter
			lastFpsTime += updateLength;
			fps++;
			
			train.cruiseControl();
			train.maintainAuthority(delta);
			
			//System.out.println(iteration++ + ":\t\t" + fpsOut + "\t" + delta + "\t" + lastLoopTime + "\t" + optimalTime);
			//System.out.println(iteration++ + ":\tSpeed Setp: " + train.speedSetpoint + "\tSpeed: " + train.speed + "\tAuthority: " + train.authoritySetpointMoving);
			System.out.format("%d \t SpeedSet: %.3f \t Speed: %.3f \t Authority: %.3f \t FPS: %d\n", iteration++, train.speedSetpoint, train.speed, train.authoritySetpointMoving, fpsOut);
			
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
