package ivyhacksspring21;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class DetectPol implements ActionListener {
	JFrame frame;
	JPanel panel;
	JComboBox<String> locCB;
	JTextArea infoTA;
	
	JButton airqBut;
	JButton covidBut;
	JButton helpBut;
	JButton hychBut;
	JButton sourceBut;
	JButton gameBut;
	JButton infoBut;
	
	JTable airqTab;
	JTable covidTab;
	//JTable infoTab;
	DefaultTableModel aqdtm;
	DefaultTableModel covdtm;
	//DefaultTableModel infdtm;
	String[] airqCols = {"Location", "Overall AQI", "Ozone (AQI)", "pm2.5 (AQI)", "pm10 (AQI)"};
	String[] covidCols = {"Location", "Total Cases", "Total Deaths", "New Cases Per Day"};
	//String[] infoCols = {"Extra Location Information"};
	Font font = new Font("serif", Font.PLAIN, 32);
	
	BufferedReader reader;
	int locNum;
	ArrayList<String> locAL;
	String[][] airq;
	String[][] covid;
	String[][] info;
	
	SortingGame sg = new SortingGame();
	
	public static void main(String[] args) throws IOException {
		DetectPol dp = new DetectPol();
		dp.setUp();
		dp.airqData();
		dp.covidData();
		dp.infoData();
	}
	
	public void setUp() throws IOException {
		frame = new JFrame();
		panel = new JPanel();
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setBackground(Color.LIGHT_GRAY);
		
		locCB = new JComboBox<String>();
		locCB.setFont(font);
		reader =  new BufferedReader(new FileReader("locations.in"));
		//"locations.in" format: first line is integer n, followed by n lines with one city on each
		locNum = Integer.parseInt(reader.readLine());
		locAL = new ArrayList<String>();
		for(int i=0;i<locNum;i++) {
			locAL.add(reader.readLine());
		}
		reader.close();
		/*for(int i=0;i<locNum;i++) {
			System.out.println(locAL.get(i));
		}*/ //for testing if input is read correctly
		for(String loc : locAL) {
			locCB.addItem(loc);
		}
		
		airqBut = new JButton("Air Quality");
		covidBut = new JButton("Covid");
		infoBut = new JButton("Extra Location Information");
		airqBut.setFont(font);
		covidBut.setFont(font);
		infoBut.setFont(font);
		airqBut.addActionListener(this);
		covidBut.addActionListener(this);
		infoBut.addActionListener(this);
		
		helpBut = new JButton("?");
		hychBut = new JButton("How You Can Help");
		helpBut.setFont(font);
		hychBut.setFont(font);
		helpBut.addActionListener(this);
		hychBut.addActionListener(this);
		sourceBut = new JButton("Sources");
		sourceBut.setFont(font);
		sourceBut.addActionListener(this);
		
		aqdtm = new DefaultTableModel(airqCols, 2);
		covdtm = new DefaultTableModel(covidCols, 2);
		//infdtm = new DefaultTableModel(infoCols, 1);
		airqTab = new JTable(aqdtm);
		covidTab = new JTable(covdtm);
		//infoTab = new JTable(infdtm);
		airqTab.setFont(font);
		covidTab.setFont(font);
		//infoTab.setFont(font);
		
		for(int i=0;i<5;i++) {
			airqTab.getColumnModel().getColumn(i).setPreferredWidth(300);
		}
		for(int i=0;i<4;i++) {
			covidTab.getColumnModel().getColumn(i).setPreferredWidth(300);
		}
		//infoTab.getColumnModel().getColumn(0).setPreferredWidth(1500);
		airqTab.setRowHeight(50);
		covidTab.setRowHeight(50);
		//infoTab.setRowHeight(50);
		
		gameBut = new JButton("Trash Sorting Game");
		gameBut.setFont(font);
		gameBut.addActionListener(this);
		
		infoTA = new JTextArea();
		infoTA.setFont(font);
		infoTA.setLineWrap(true);
		infoTA.setWrapStyleWord(true);
		infoTA.setSize(1500, 500);
		
		addStuff();
		
		frame.pack();
	}
	
	public void addStuff() {
		frame.add(panel);
		panel.add(locCB);

		panel.add(airqBut);
		panel.add(covidBut);
		panel.add(infoBut);

		panel.add(airqTab);
		panel.add(covidTab);
		//panel.add(infoTab);
		panel.add(infoTA);
		
		panel.add(helpBut);
		panel.add(hychBut);
		panel.add(sourceBut);
		
		panel.add(gameBut);
	}

	public void airqData() throws IOException {
		airq = new String[locNum][5];
		reader =  new BufferedReader(new FileReader("airq.in"));
		//"airq.in" format: location name followed by ":", 4 numbers or "little to none" separated by ";"
		for(int i=0;i<locNum;i++) {
			String[] l1 = reader.readLine().split(":");
			airq[i][0] = l1[0];
			String[] l2 = l1[1].split(";");
			for(int j=1;j<5;j++) {
				airq[i][j] = l2[j-1];
			}
		}
		reader.close();
		
		/*for(int i=0;i<locNum;i++) {
			for(int j=0;j<5;j++) {
				System.out.print(airq[i][j]+" ");
			}
			System.out.println("");
		}*/ //for testing if array format is correct
	}
	
	public void covidData() throws IOException {
		covid = new String[locNum][4];
		reader =  new BufferedReader(new FileReader("covid.in"));
		//"covid.in" format: location name followed by ":", 3 numbers separated by ";"
		for(int i=0;i<locNum;i++) {
			String[] l1 = reader.readLine().split(":");
			covid[i][0] = l1[0];
			String[] l2 = l1[1].split(";");
			for(int j=1;j<4;j++) {
				covid[i][j] = l2[j-1];
			}
		}
		reader.close();
		
		/*for(int i=0;i<locNum;i++) {
			for(int j=0;j<4;j++) {
				System.out.print(covid[i][j]+" ");
			}
			System.out.println("");
		}*/ //for testing if array format is correct
	}
	
	public void infoData() throws IOException{
		info = new String[locNum][2];
		reader =  new BufferedReader(new FileReader("info.in"));
		//"info.in" format: location name and a sentence separated by ":"
		for(int i=0;i<locNum;i++) {
			String[] line = reader.readLine().split(":");
			info[i][0] = line[0];
			info[i][1] = line[1];
		}
		reader.close();
		
		/*for(int i=0;i<locNum;i++) {
			for(int j=0;j<2;j++) {
				System.out.print(info[i][j]+" ");
			}
			System.out.println("");
		}*/ //for testing if array format is correct
	}
	
	public void displayAirq(String curLoc) {
		String[] temp = new String[5];
		for(String[] arr : airq) {
			if(arr[0].equals(curLoc)) {
				temp = arr;
				break;
			}
		}	
		
		aqdtm.removeRow(0);
		aqdtm.removeRow(0);
		aqdtm.addRow(airqCols);
		aqdtm.addRow(temp);
		airqTab.setEnabled(false);
		
		int x = Integer.parseInt(temp[1]);
		if(x<=50)
			airqTab.setBackground(Color.green);
		else if(x<=100)
			airqTab.setBackground(Color.yellow);
		else if(x<=150)
			airqTab.setBackground(Color.orange);
		else if(x<=200)
			airqTab.setBackground(Color.red);
		else
			airqTab.setBackground(Color.magenta);
		
		
		airqTab.setModel(aqdtm);
	}
	
	public void displayCovid(String curLoc) {
		String[] temp = new String[4];
		for(String[] arr : covid) {
			if(arr[0].equals(curLoc)) {
				temp = arr;
				break;
			}
		}
		
		covdtm.removeRow(0);
		covdtm.removeRow(0);
		covdtm.addRow(covidCols);
		covdtm.addRow(temp);
		covidTab.setEnabled(false);
		
		if(temp[3].contains(","))
			covidTab.setBackground(Color.magenta);
		else {
			int x = Integer.parseInt(temp[3]);
			if(x<=50)
				covidTab.setBackground(Color.green);
			else if(x<=100)
				covidTab.setBackground(Color.yellow);
			else if(x<=250)
				covidTab.setBackground(Color.orange);
			else if(x<=500)
				covidTab.setBackground(Color.red);
		}
		
		covidTab.setModel(covdtm);
	}
	
	public void displayInfo(String curLoc) {
		//String[] temp = new String[1];
		String t = "";
		for(String[] arr : info) {
			if(arr[0].equals(curLoc)) {
				//temp[0] = arr[1];
				t = arr[1];
				break;
			}
		}
		
		infoTA.setText(t);
		infoTA.setBackground(Color.pink);
		
		/*infdtm.removeRow(0);
		infdtm.addRow(temp);
		infoTab.setEnabled(false);
		infoTab.setModel(infdtm);*/
	}
	
	public void sortGame() {
		sg.run();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton buttonPressed = (JButton) e.getSource();
		if(buttonPressed.equals(airqBut)) {
			String curLoc = (String) locCB.getSelectedItem();
			displayAirq(curLoc);
			//System.out.println("airqBut pressed at "+curLoc); //testing button
		}
		if(buttonPressed.equals(covidBut)) {
			String curLoc = (String) locCB.getSelectedItem();
			displayCovid(curLoc);
			//System.out.println("covidBut pressed at "+curLoc); //testing button
		}
		if(buttonPressed.equals(infoBut)) {
			String curLoc = (String) locCB.getSelectedItem();
			displayInfo(curLoc);
		}
		if(buttonPressed.equals(helpBut)) {
			JOptionPane.showMessageDialog(null, "PM: particulate matter, 2.5 or 10 refers to the size of the particle\n"
					+ "AQI: Air Quality index. It is a scale from 0 to 500 that judges air quality\n"
					+ "For more information about AQI, visit: https://www.airnow.gov/aqi/aqi-basics/\n"
					+ "\nColor code (from least to most severe): "
					+ "green, yellow, orange, red, magenta");
		}
		if(buttonPressed.equals(hychBut)) {
			JOptionPane.showMessageDialog(null, "To improve air quality in your city, always try to use your car less.\n"
					+ "If possible, try to switch over to electric vehicles and tools.\n"
					+ "Try not to burn anything in the city and remember to take care of plants.\n"
					+ "\n"
					+ "To reduce the spread of the coronavirus, always wear masks and social distance when going out.\n"
					+ "Try to eat out less often and always have proper hygiene.");
		}
		if(buttonPressed.equals(sourceBut)) {
			JOptionPane.showMessageDialog(null, "Air Quality: https://www.airnow.gov/\n"
					+ "Source: https://www.nytimes.com/interactive/2020/us/california-coronavirus-cases.html");
		}
		if(buttonPressed.equals(gameBut)) {
			sortGame();
		}
	}
}
