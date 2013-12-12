package ctc;

import static org.junit.Assert.*;
import nse.TransitSystem;

import org.junit.Test;

public class CTCTest 
{
	
	/**
	 * Test login stuff
	 */
	@Test public void testLogin() 
	{
		LoginController loginTester = new LoginController();
		User user = loginTester.login("root", "password");
		assertNotNull("Username: root, Password: password is a valid user", user);
		assertEquals("User has correct name: root", "root", user.name);
		assertTrue("User has dispatcher permissions", user.isDispatcher());
		assertTrue("User has maintenance permissions", user.isMaintenance());
		assertTrue("User has track creator permissions", user.isTrackCreator());
		assertFalse("User does not have admin permissions", user.isAdmin());
	}
	
	@Test public void testLoginNoUser() 
	{
		LoginController loginTester = new LoginController();
		User user = loginTester.login("poo", "poo2");
		assertNull("Invalid credentials does not create user object", user);
	}
	
	@Test public void testLoginWrongPw() 
	{
		LoginController loginTester = new LoginController();
		User user = loginTester.login("root", "wrong");
		assertNull("Wrong password does not create user object", user);
	}
	
	@Test public void testLoginBlank()
	{
		LoginController loginTester = new LoginController();
		User user = loginTester.login("", "");
		assertNotNull("Username and password blank is secret user", user);
	}

	// **********************************************************************//
	
	/**
	 * Test for track files
	 */
	
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
	
	// **********************************************************************//
	
	/**
	 * Test for trains
	 */
	
	@Test public void testAddRemoveTrain()
	{
		TransitSystem ts = new TransitSystem("confignotrains.txt");
		CTC ctc = new CTC(ts);
		assertTrue("Add train", AddTrainFormController.addTrain("Test Train", ctc));
		assertTrue("Add train with no name", AddTrainFormController.addTrain("", ctc));
		
		// Make sure two trains exit
		assertEquals("There are two trains in ctc's database", 2, ctc.trains.size());
		assertEquals("There are two trains in system", 2, ts.trains.size());
		
		// Remove trains
		//ts.ctcRemoveTrain(trainId);
	}
}
