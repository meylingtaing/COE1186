/**
 * Test for routing
 */
package ctc;

import static org.junit.Assert.*;
import nse.TransitSystem;

import org.junit.BeforeClass;
import org.junit.Test;

public class RouteTest 
{
	// Load with greenline
	static TransitSystem ts = new TransitSystem("config.txt");
	static CTC ctc = new CTC(ts);
	
	/**
	 * Tests all of the stations for green line
	 */
	@BeforeClass static public void testGreenlineRoute()
	{
		assertTrue("Add train", AddTrainFormController.addTrain("Train A", ctc));
	}
	
	@Test(timeout=500) public void testGlenbury()
	{
		assertTrue("Route train to glenbury", RouteTrainFormController.routeTrain("Train A", "greenline", "GLENBURY", ctc));
	}
	
	@Test(timeout=500) public void testDormont()
	{
		assertTrue("Route train to dormont", RouteTrainFormController.routeTrain("Train A", "greenline", "DORMONT", ctc));
	}
	
	@Test(timeout=500) public void testMtlebanon()
	{
		assertTrue("Route train to mt lebanon", RouteTrainFormController.routeTrain("Train A", "greenline", "MT LEBANON", ctc));
	}
	
	@Test(timeout=500) public void testPoplar()
	{
		assertTrue("Route train to poplar", RouteTrainFormController.routeTrain("Train A", "greenline", "POPLAR", ctc));
	}
	
	@Test(timeout=500) public void testCastleshannon()
	{
		assertTrue("Route train to castle shannon", RouteTrainFormController.routeTrain("Train A", "greenline", "CASTLE SHANNON", ctc));
	}
	
	@Test(timeout=500) public void testOverbrook()
	{
		assertTrue("Route train to overbrook", RouteTrainFormController.routeTrain("Train A", "greenline", "OVERBROOK", ctc));
	}
	
	@Test(timeout=500) public void testInglewood()
	{
		assertTrue("Route train to inglewood", RouteTrainFormController.routeTrain("Train A", "greenline", "INGLEWOOD", ctc));
	}
	
	@Test(timeout=500) public void testCentral()
	{
		assertTrue("Route train to central", RouteTrainFormController.routeTrain("Train A", "greenline", "CENTRAL", ctc));
	}
	
	@Test(timeout=500) public void testSouthbank()
	{
		assertTrue("Route train to south bank", RouteTrainFormController.routeTrain("Train A", "greenline", "SOUTH BANK", ctc));
	}
	
	@Test(timeout=500) public void testWhited()
	{
		assertTrue("Route train to whited", RouteTrainFormController.routeTrain("Train A", "greenline", "WHITED", ctc));
	}
	
	@Test(timeout=500) public void testStation16()
	{
		assertTrue("Route train to station 16", RouteTrainFormController.routeTrain("Train A", "greenline", "STATION16", ctc));
	}
	
	@Test(timeout=500) public void testEdgebrook()
	{
		assertTrue("Route train to edgebrook", RouteTrainFormController.routeTrain("Train A", "greenline", "EDGEBROOK", ctc));
	}
	
	@Test(timeout=500) public void testPioneer()
	{
		assertTrue("Route train to pioneer", RouteTrainFormController.routeTrain("Train A", "greenline", "PIONEER", ctc));
	}
}
