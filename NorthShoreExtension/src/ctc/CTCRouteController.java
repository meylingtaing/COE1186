package ctc;

public class CTCRouteController extends CTCController {
	
	public void initialize()
	{	
		CTC.ctcController = this;
		displayTrack();
		displayLegend();
		
		if (!CTC.currUser.isDispatcher())
			addTrainButton.setDisable(true);
	}
}
