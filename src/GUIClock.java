import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUIClock {
	Integer FinalHours= -1;
	Integer FinalMinutes = -1;
	Integer FinalSeconds = -1;
	JPanel mainscreen, alarmscreen;
	JLabel Welcome, GeneratedDate, GeneratedTime;
	JFrame mainGUI, alarmGUI;
	JButton SetAlarms, ConfirmAlarms;
	JTextField hours, minutes, seconds;
	ActionListener lforText;
	DefaultListModel<String> listmodel;
	Date cal;
	Integer Hours = -1;
	Integer Minutes =-1;
	Integer Seconds = -1;
	
	public static void main(String[] args) {
		new GUIClock();
		
	}
		
	public GUIClock(){
		System.out.println("Initializing...");
		createGUI();
		mainscreen = new JPanel();
		mainscreen.setLayout(null);
		mainscreen.setBackground(Color.GRAY);
		mainscreen.setOpaque(true);
		System.out.println("Basis for GUI Window initialized...");
		
		alarmscreen = new JPanel();
		alarmscreen.setLayout(null);
		alarmscreen.setBackground(Color.GRAY);
		alarmscreen.setOpaque(true);
		System.out.println("Basis for Alarm Window initialized...");
		alarmscreen.setVisible(false);

		ListenForReturnHome lForHome = new ListenForReturnHome();
		ListenForCustomAlarm lForCustomAlarm = new ListenForCustomAlarm();
		ListenForCurrentAlarms lForCurrent = new ListenForCurrentAlarms();
		ListenForConfirmation lforconfirmation = new ListenForConfirmation();

		
		//ListenForText lForText = new ListenForText();
		/*ListenForLogout lForOpenItem = new ListenForLogout();
		ListenForPrint lForPrint = new ListenForPrint();
		ListenForWithdraw lforWithdraw = new ListenForWithdraw();
		ListenForDeposit lforDeposit = new ListenForDeposit();
		ListenForText3 lForText3 = new ListenForText3();
		ListenForText2 lForText2 = new ListenForText2();
		ListenForAbout lForAbout = new ListenForAbout();
		ListenForHome lForHome = new ListenForHome();*/
		
	

		//LOGO///LOGO///LOGO//////LOGO///LOGO///LOGO//////LOGO///LOGO///LOGO///		
		BufferedImage clocklogo = null;
		try {
			clocklogo = ImageIO.read(this.getClass().getResource("clocklogo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel clogo = new JLabel(new ImageIcon(clocklogo));
		clogo.setBounds(95, 0, 95,45);
		clogo.setHorizontalAlignment(JLabel.CENTER);
		mainscreen.add(clogo);
		/////////////////////////////////////////////////////////////////////////////
		BufferedImage fancyline = null;
		try {
			fancyline = ImageIO.read(this.getClass().getResource("hline.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel hline = new JLabel(new ImageIcon(fancyline));
		hline.setBounds(0, 30, 300,30);
		mainscreen.add(hline);
		///LOGO///LOGO///LOGO//////LOGO///LOGO///LOGO//////LOGO///LOGO///LOGO/	
		
		
		Calendar cal = GregorianCalendar.getInstance();
	    System.out.println("Minutes : "+ cal.get(Calendar.MINUTE));
	    System.out.println("Seconds :" + cal.get(Calendar.SECOND));
	    System.out.println("Hours : "+ cal.get(Calendar.HOUR));

		
		///TIME AND DATE
		currentTime();
		GeneratedTime.setOpaque(true);
		GeneratedTime.setBounds(0, 45, 300, 75);
		GeneratedTime.setFont(new Font(GeneratedTime.getName(), Font.BOLD,18));
		GeneratedTime.setForeground(Color.pink);
		GeneratedTime.setBackground(Color.GRAY);
		GeneratedTime.setHorizontalAlignment(JLabel.CENTER);
		mainscreen.add(GeneratedTime);
		
		
		GeneratedDate.setOpaque(true);
		GeneratedDate.setBounds(42, 60, 200, 140);
		GeneratedDate.setFont(new Font(GeneratedDate.getName(), Font.BOLD, 22));
		GeneratedDate.setForeground(Color.pink);
		GeneratedDate.setBackground(Color.GRAY);
		GeneratedDate.setHorizontalAlignment(JLabel.CENTER);
		mainscreen.add(GeneratedDate);
		

		SetAlarms = new JButton();
		SetAlarms.setText("Set A Custom Alarm");
		SetAlarms.setBounds(50, 160, 200, 100);
		SetAlarms.setHorizontalAlignment(JButton.CENTER);
		SetAlarms.addActionListener(lForCustomAlarm);
		mainscreen.add(SetAlarms);
		
	    
		
		////////////////////////////////MENU//////////////////////////////

		JMenuBar menuBar = new JMenuBar();
		
		JMenu alarmMenu = new JMenu("Pre-Set Alarms");
		JMenuItem alarm1 = new JMenuItem("5:30AM", KeyEvent.VK_5);
		//alarm1MenuItem.addActionListener(lForAlarmOne);
		alarmMenu.add(alarm1);
		alarmMenu.addSeparator();
		JMenuItem alarm2 = new JMenuItem("7:00AM", KeyEvent.VK_7);
		//alarm2MenuItem.addActionListener(lForAlarmTwo);
		alarmMenu.add(alarm2);
		alarmMenu.addSeparator();
		JMenuItem alarm3 = new JMenuItem("8:00PM", KeyEvent.VK_8);
		//alarm3MenuItem.addActionListener(lForAlarmThree);
		alarmMenu.add(alarm3);
		
		JMenu extraMenu = new JMenu("Extras");
		JMenuItem currentalarms = new JMenuItem("Current Alarms", KeyEvent.VK_C);
		currentalarms.addActionListener(lForCurrent);
		extraMenu.add(currentalarms);
		extraMenu.addSeparator();
		JMenuItem homelink = new JMenuItem("Return to Home", KeyEvent.VK_H);
		homelink.addActionListener(lForHome);
		extraMenu.add(homelink);
		
		
		menuBar.add(alarmMenu);
		menuBar.add(extraMenu);
		mainGUI.add(menuBar);
		mainGUI.setJMenuBar(menuBar);
		

		////////////////////////////////MENU//////////////////////////////
		mainGUI.add(mainscreen);
		mainGUI.setVisible(true);
		

		////////////////////////////////////////////////ALARM SCREEN//////////////////////////////////////////////
		
		//LOGOS	
		BufferedImage alarmlogo = null;
		try {
			alarmlogo = ImageIO.read(this.getClass().getResource("customlogo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel alogo = new JLabel(new ImageIcon(alarmlogo));
		alogo.setBounds(25, 10, 250,30);
		alogo.setHorizontalAlignment(JLabel.CENTER);
		alarmscreen.add(alogo);
		/////////////////////////////////////////////////////////////////////////////////////////////////
		BufferedImage fancyline2 = null;
		try {
			fancyline2 = ImageIO.read(this.getClass().getResource("hline.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel hline2 = new JLabel(new ImageIcon(fancyline2));
		hline2.setBounds(0, 30, 300,30);
		alarmscreen.add(hline2);
		
		
		//END LOGOS

		
		addalarmscreenlabels();
		hours = new JTextField(20);
		hours.setBounds(40, 100, 60, 30);
		hours.setToolTipText("Hour");
		alarmscreen.add(hours);
		
		minutes = new JTextField(20);
		minutes.setBounds(115, 100, 60, 30);
		minutes.setToolTipText("Minutes");

		alarmscreen.add(minutes);
		
		seconds = new JTextField(20);
		seconds.setBounds(190, 100, 60, 30);
		seconds.setToolTipText("Seconds");
		alarmscreen.add(seconds);

		
		ConfirmAlarms = new JButton();
		ConfirmAlarms.setText("Confirm");
		ConfirmAlarms.setBounds(25, 160, 100, 50);
		ConfirmAlarms.setHorizontalAlignment(JButton.CENTER);
		ConfirmAlarms.addActionListener(lforconfirmation);
		alarmscreen.add(ConfirmAlarms);
		
	

		
		//thetime = new Date();
		
		while(true) {
			cal = Calendar.getInstance();
			Date date = cal.getTime();
			GeneratedTime.setText(date.toString());
			Integer Hours = cal.get(Calendar.HOUR);
			Integer Minutes = cal.get(Calendar.MINUTE);
			Integer Seconds = cal.get(Calendar.SECOND);


			if(Hours.equals(FinalHours) && Minutes.equals(FinalMinutes) && Seconds.equals(FinalSeconds)) {
					
					JOptionPane.showMessageDialog(mainGUI, "Beep Beep Beep");
					}
					
	}	
	}
	
	private class ListenForConfirmation implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String userhours = hours.getText();
			String usermins = minutes.getText();
			String usersecs = seconds.getText();
			FinalHours = Integer.valueOf(userhours);
			FinalMinutes = Integer.valueOf(usermins);
			FinalSeconds = Integer.valueOf(usersecs);
			System.out.println(FinalHours);
			System.out.println(FinalMinutes);
			System.out.println(FinalSeconds);




		}
	}
	//Alarm calls for setting up//
	private class ListenForCurrentAlarms implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//JOptionPane.showMessageDialog(mainGUI, AlarmList);
		}
		
	}
	
	
	private class ListenForReturnHome implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			alarmscreen.setVisible(false);
			mainGUI.add(mainscreen);
			mainscreen.setVisible(true);
		}
		
	}


	private void addalarmscreenlabels() {
		// TODO Auto-generated method stub
		JLabel hours = new JLabel();
		hours.setText("Hour");
		hours.setFont(new Font(hours.getName(), Font.BOLD, 14));
		hours.setBounds(40, 70, 60, 30);
		alarmscreen.add(hours);
		
		JLabel minutes = new JLabel();
		minutes.setText("Minutes");
		minutes.setFont(new Font(minutes.getName(), Font.BOLD, 14));
		minutes.setBounds(115, 70, 60, 30);
		alarmscreen.add(minutes);
		
		JLabel seconds = new JLabel();
		seconds.setText("Seconds");
		seconds.setFont(new Font(seconds.getName(), Font.BOLD, 14));
		seconds.setBounds(190, 70, 80, 30);
		alarmscreen.add(seconds);
		
		JLabel alarms = new JLabel();
		alarms.setText("Alarms");
		alarms.setFont(new Font(alarms.getName(), Font.BOLD, 14));
		alarms.setBounds(175, 204, 60, 30);
		alarmscreen.add(alarms);
		
	}

	public void createGUI() {
	mainGUI = new JFrame();
	mainGUI.setTitle("Clock");
	mainGUI.setLocationRelativeTo(null);
	mainGUI.setSize(300, 300);
	mainGUI.setResizable(false);
	mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String currentTime() {
		 GeneratedDate = new JLabel();
		 GeneratedTime = new JLabel();
		 Calendar today = Calendar.getInstance();
		 //date
		 SimpleDateFormat calendardateF = new SimpleDateFormat("MM-dd-yyyy");
		 String calendardate = calendardateF.format(today.getTime());
		 GeneratedDate.setText(calendardate);
		 //time
		 SimpleDateFormat timeF = new SimpleDateFormat("hh:mm:ss a");
		 String time = timeF.format(today.getTime());
		 GeneratedTime.setText(time);

		 return time;
		 
	}

	private class ListenForCustomAlarm implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			mainscreen.setVisible(false);
			mainGUI.add(alarmscreen);
			alarmscreen.setVisible(true);
		}
	}
	
	
	
	
}
	
/*int hour = today.get(Calendar.HOUR);
int min = today.get(Calendar.MINUTE);
int sec = today.get(Calendar.SECOND);
int day = today.get(Calendar.DATE);
int month = today.get(Calendar.MONTH);
int year = today.get(Calendar.YEAR);*/
//GeneratedDate.setText(hour+":"+min+":"+sec);