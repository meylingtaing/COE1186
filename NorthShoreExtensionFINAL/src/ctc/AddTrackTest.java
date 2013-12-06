package ctc;

import static org.junit.Assert.*;
import nse.TransitSystem;

import org.junit.Test;

public class AddTrackTest 
{

	@Test public void test() 
	{
		CTC ctc = new CTC(new TransitSystem());
		assertTrue("Add greenline successful", AddTrackFormController.addTrack("greenline",  "greenline.csv", "lime", ctc));
		
		fail("Not yet implemented");
	}

}
