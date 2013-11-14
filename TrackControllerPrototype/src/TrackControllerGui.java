import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Label;
import java.awt.LayoutManager;

import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class TrackControllerGui {

	private JFrame frame;
	private JPanel panel;
	private JPanel con1panel, con2panel,con3panel, imgPanel,controllerContainer;
	private JTextArea train1Panel;
	private JTextArea train2Panel, train3Panel;
	private JTextArea switch1Panel, switch2Panel, switch3Panel;
	public ArrayList<TrackController> trackControllers = new ArrayList<TrackController>();
	public ArrayList<Train> trains = new ArrayList<Train>();
	public TrackController controller1;
	public TrackController controller2;
	public TrackController controller3;
	public boolean switcharoo = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackControllerGui window = new TrackControllerGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrackControllerGui() {
		initialize();
		Timer t = new Timer();
		long delay = 1000;
		long period = 10000;
		MyTask task = new MyTask();		
		t.schedule(task, delay,period);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1250, 780);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		java.awt.Rectangle r = new Rectangle(1150,680);
		
		panel = new JPanel();
		panel.setBounds(r);

		LayoutManager mgr = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(mgr);
		frame.getContentPane().add(panel);
		
		
		
		con1panel = new JPanel();
		con2panel = new JPanel();
		con3panel = new JPanel();		
		train1Panel = new JTextArea();
		train2Panel = new JTextArea();
		train3Panel = new JTextArea();
		switch1Panel = new JTextArea();
		switch2Panel = new JTextArea();
		switch3Panel = new JTextArea();
		train1Panel.setLineWrap(true);
		train2Panel.setLineWrap(true);
		train3Panel.setLineWrap(true);
		switch1Panel.setLineWrap(true);
		switch2Panel.setLineWrap(true);
		switch3Panel.setLineWrap(true);
		imgPanel = new JPanel();
		controllerContainer = new JPanel();
		JPanel c1labelPanel = new JPanel();
		JPanel c2labelPanel = new JPanel();
		JPanel c3labelPanel = new JPanel();
		LayoutManager mgr2 = new BoxLayout(controllerContainer, BoxLayout.X_AXIS);
		controllerContainer.setLayout(mgr2);
		
		
		imgPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		controllerContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		con1panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		con2panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		con3panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		LayoutManager c1= new BoxLayout(con1panel, BoxLayout.Y_AXIS);
		LayoutManager c2= new BoxLayout(con2panel, BoxLayout.Y_AXIS);
		LayoutManager c3= new BoxLayout(con3panel, BoxLayout.Y_AXIS);
		con1panel.setLayout(c1);
		con2panel.setLayout(c2);
		con3panel.setLayout(c3);
		
		JLabel c1label = new JLabel("Track Controller 1");
		JLabel c2label = new JLabel("Track Controller 2");
		JLabel c3label = new JLabel("Track Controller 3");
		JLabel trainsL1 = new JLabel("Trains:");
		JLabel trainsL2 = new JLabel("Trains:");
		JLabel trainsL3 = new JLabel("Trains:");
		JLabel SwitchL1 = new JLabel("Switches:");
		JLabel SwitchL2 = new JLabel("Switches:");
		JLabel SwitchL3 = new JLabel("Switches:");
		
		c1labelPanel.add(c1label);
		c2labelPanel.add(c2label);
		c3labelPanel.add(c3label);
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("PrototypeTrack.jpg"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		imgPanel.add(picLabel);
		c1labelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		c2labelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		c3labelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//c1labelPanel.setBounds(r1.height, 0, 383, 100);
		
		
		con1panel.add(c1labelPanel);
		con2panel.add(c2labelPanel);
		con3panel.add(c3labelPanel);
		con1panel.add(trainsL1);
		con2panel.add(trainsL2);
		con3panel.add(trainsL3);
		con1panel.add(train1Panel);
		con2panel.add(train2Panel);
		con3panel.add(train3Panel);
		con1panel.add(SwitchL1);
		con2panel.add(SwitchL2);
		con3panel.add(SwitchL3);
		con1panel.add(switch1Panel);
		con2panel.add(switch2Panel);
		con3panel.add(switch3Panel);
		
		controllerContainer.add(con1panel);
		controllerContainer.add(con2panel);
		controllerContainer.add(con3panel);
		
		
		panel.add(imgPanel);
		panel.add(controllerContainer);
		
		/*
		 * 
		 * create one track Controller row type
		 * then add multiples to list
		 */
		setUpTrainsAndBlocks();
	}
	
	public void setUpTrainsAndBlocks()
	{
		//Initialize trains, blocks, and track controllers
		
		//Setup Train Routes
		Block[] routeA = new Block[9];
		Block[] routeC = new Block[9];
		Block[] routeB = new Block[9];
		
		Block b13 = new Block("B13", 50, null, false);
		Block b4 = new Block("B4", 50, null, false);
		
		//Block b5 holds a switch so create one
		Switch s5 = new Switch(b4, b13);
		Block b5 = new Block("B5",50,s5,false);
		
		Block b1 = new Block("B1", 50, null, false);
		Block b10 = new Block("B10", 50, null, false);
		//Block b9 holds a Switch
		Switch s9 = new Switch(b1,b10); 
		Block b9 = new Block("B9", 50, s9, false);
		
		//Create the remaining Blocks
		Block b6= new Block("B6",50,null,false);
		Block b7= new Block("B7",50,null,true);
		Block b8= new Block("B8",50,null,false);
		Block b11= new Block("B11",50,null,true);		
		Block b12= new Block("B12",50,null,false);
		Block b2= new Block("B2",50,null,true);
		Block b3= new Block("B3",50,null,false);
		
		routeA[0] = b6;
		routeA[1] = b5;
		routeA[2] = b13;
		routeA[3] = b12;
		routeA[4] = b11;
		routeA[5] = b10;
		routeA[6] = b9;
		routeA[7] = b8;
		routeA[8] = b7;
		
		routeC[0] = b10;
		routeC[1] = b9;
		routeC[2] = b8;
		routeC[3] = b7;
		routeC[4] = b6;
		routeC[5] = b5;
		routeC[6] = b13;
		routeC[7] = b12;
		routeC[8] = b11;
		
		routeB[0] = b4;
		routeB[1] = b3;		
		routeB[2] = b2;
		routeB[3] = b1;
		routeB[4] = b9;
		routeB[5] = b8;
		routeB[6] = b7;
		routeB[7] = b6;
		routeB[8] = b5;
		
		Train trainA = new Train("A",routeA, b6);
		Train trainB = new Train("B",routeC, b10);
		Train trainC = new Train("C",routeB, b4);
		trains.add(trainA);
		trains.add(trainB);
		trains.add(trainC);
		
		b6.setTrainInBlock(trainA);
		b10.setTrainInBlock(trainB);
		b4.setTrainInBlock(trainC);
		
		trainA.setAuthority(2);
		trainB.setAuthority(2);
		trainC.setAuthority(4);
		
		ArrayList<Train> t1Trains = new ArrayList<Train>();
		t1Trains.add(trainC);
		
		ArrayList<Train> t2Trains = new ArrayList<Train>();
		t2Trains.add(trainB);
		
		ArrayList<Train> t3Trains = new ArrayList<Train>();
		t3Trains.add(trainA);
		
		ArrayList<TrackController> tAPlc = new ArrayList<TrackController>();
		ArrayList<TrackController> tBPlc = new ArrayList<TrackController>();
		ArrayList<TrackController> tCPlc = new ArrayList<TrackController>();
		
		
		Block [] t1Blocks = new Block[6];
		t1Blocks[0] = b5;
		t1Blocks[1] = b4;
		t1Blocks[2] = b3;
		t1Blocks[3] = b2;
		t1Blocks[4] = b1;
		t1Blocks[5] = b9;
		
		Block [] t2Blocks = new Block[6];
		t2Blocks[0] = b5;
		t2Blocks[1] = b13;
		t2Blocks[2] = b12;
		t2Blocks[3] = b11;
		t2Blocks[4] = b10;
		t2Blocks[5] = b9;
		
		Block [] t3Blocks = new Block[5];
		t3Blocks[0] = b9;
		t3Blocks[1] = b8;
		t3Blocks[2] = b7;
		t3Blocks[3] = b6;
		t3Blocks[4] = b5;
		
		String s1 = "t1";
		String s2 = "t2";
		String s3 = "t3";
		TrackController t1 = new TrackController(s1,t1Blocks,t1Trains);
		TrackController t2 = new TrackController(s2,t2Blocks,t2Trains);
		TrackController t3 = new TrackController(s3,t3Blocks,t3Trains);
		
		tAPlc.add(t2);
		tAPlc.add(t3);
		tBPlc.add(t2);
		tBPlc.add(t3);
		tCPlc.add(t1);
		tCPlc.add(t3);
		
		trainA.setTrackControllers(tAPlc);
		trainB.setTrackControllers(tBPlc);
		trainC.setTrackControllers(tCPlc);
		
		t1.startBlock = b5;
		t1.terminalBlock = b9;
		t1.priorTerminalBlock = b2;
		controller1 = t1;
		
		t2.startBlock = b5;
		t2.terminalBlock = b9;
		t2.priorTerminalBlock = b11;
		controller2 = t2;
		
		t3.startBlock = b9;
		t3.terminalBlock = b5;
		t3.priorTerminalBlock = b7;
		controller3 = t3;
		
		trackControllers.add(controller1);
		trackControllers.add(controller2);
		trackControllers.add(controller3);
		
		
		
	}
	
	class MyTask extends TimerTask{

		@Override
		public void run() 
		{
		
			updateGUI();
			for(TrackController t:trackControllers)
			{
				//t.calculateFixedBlockAuthority();
				t.checkSwitchStatus();
				t.handoff();
				t.detectTrains();				
				//t.calculateFixedBlockAuthority();
			}
			
			for(Train t:trains)
			{
				t.goToNextBlock();
			}
			
			for(TrackController t:trackControllers)
			{
				t.calculateFixedBlockAuthority();
			}
			//updateGUI();
			
		}

		private void updateGUI() {
			
			/*if(switcharoo)
			{
				imgPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
				switcharoo = false;
			}
			else
			{
				switcharoo = true;
				imgPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
			}*/
			String ts1 = "";
			for(Train t:controller1.trainsInSection)
			{
				ts1 = ts1 + t.toString();
			}
			train1Panel.setText("");
			train1Panel.setText(ts1);
			
			String ts2 = "";
			for(Train t:controller2.trainsInSection)
			{
				ts2 = ts2 + t.toString();
			}
			train2Panel.setText("");
			train2Panel.setText(ts2);
			
			String ts3 = "";
			for(Train t:controller3.trainsInSection)
			{
				ts3 = ts3 + t.toString();
			}
			train3Panel.setText("");
			train3Panel.setText(ts3);
			
			Block b1 = controller1.terminalBlock;
			Switch s1 = b1.getSwitch();
			switch1Panel.setText("");
			switch1Panel.setText(s1.toString());
			
			Block b2 = controller2.terminalBlock;
			switch2Panel.setText("");
			Switch s2 = b2.getSwitch();
			switch2Panel.setText(s2.toString());
			
			Block b3 = controller3.terminalBlock;
			Switch s3 = b3.getSwitch();
			switch3Panel.setText("");
			switch3Panel.setText(s3.toString());
			
		}
		
	}
}
