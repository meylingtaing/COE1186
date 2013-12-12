package trackModel;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Collection;


public class TestTrackWhole{
	Track tracktester;
	NewTrackController ntcTester;
	ChildQueryController cqcTester;
	TrackMainController tmcTester;
	
	@Before
	public void initialize(){
		String[] args = new String[0];
		tracktester = new Track();
		ntcTester = new NewTrackController();
		cqcTester = new ChildQueryController();
		tmcTester = new TrackMainController();
	}
	
	//testing bad input for new track file
	@Test
	public void testBadInputTrack() {
		tearDown();
		Track.trackArray.clear();
		ntcTester.InternalImport("BadFileName.csv");
		assertNull(Track.trackArray.get("Green"));		
	}
	
	//testing correct input for new track file
	@Test
	public void testInputTrack() {
		
		ntcTester.InternalImport("greenline.csv");
		assertNotNull(Track.trackArray.get("Green"));		
		//tearDown();
	}
	
	//testing querying an existing block
	@Test
	public void testQueryBlock(){
		
		assertEquals(Track.trackArray.get("Green").getBlock(15).getSpeedLimit(), 70);
	}
	
	//test breaking block by id
	@Test
	public void testBreakById(){
		testInputTrack();
		int bId=10;
		String line = "Green";
		tmcTester.internalBreakWithId(bId, line);
		assertTrue(Track.trackArray.get(line).getBlock(bId).isClosed());
		tearDown();
	}
	
	//testing deleting an existing track
	@Test
	public void testDeleteTrack(){
		int trackListSize = Track.trackListData.size();
		trackListSize--;
		String line = Track.trackListData.get(trackListSize);
		assertNotNull(Track.trackArray.get(line));
		tmcTester.internalDeleteTrack(line, trackListSize);
		assertNull(Track.trackArray.get(line));
	}
	
	@After
	public void tearDown(){
		tracktester=null;
		ntcTester=null;
		cqcTester = null;
		tmcTester = null;
		
		initialize();
	}

}
