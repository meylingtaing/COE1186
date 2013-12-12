package ctc;

import static org.junit.Assert.*;
import nse.TransitSystem;

import org.junit.Test;

public class AddTrackTest 
{

	@Test public void testRealLines() 
	{
		CTC ctc = new CTC(new TransitSystem());
		assertTrue("Add greenline successful", AddTrackFormController.addTrack("greenline",  "greenline.csv", "lime", ctc));
		assertTrue("Add redline successful", AddTrackFormController.addTrack("redline", "redline.csv", "red", ctc));
	}

	@Test public void testBadTrackFiles()
	{
		CTC ctc = new CTC(new TransitSystem());
		assertFalse("File not found", AddTrackFormController.addTrack("blueline", "blueline.csv", "blue", ctc));
		assertFalse("Bad file format", AddTrackFormController.addTrack("greenline", "greenlinebad.csv", "lime", ctc));
	}
}
