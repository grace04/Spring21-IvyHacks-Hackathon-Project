package ivyhacksspring21;
import java.util.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SortingGame implements ActionListener {
	JFrame frame;
   	JPanel panel;
   	JButton rec;
   	JButton tra;
   	JButton com;
   	JButton replay;
	
   	JLabel scoLab;
	JLabel label;
	JLabel secLab;
	Font font = new Font("serif", Font.PLAIN, 32);
	
	String[] itemsArr = {"Newspaper", "Cardboard", "Styrofoam", "Rubberbands", "Fruit peel", "Tea bag"};
	//ArrayList<String> itemsAL = new ArrayList<String>(); //enable for random
	int score = 0;
	int counter = 0;
	
	/*public static void main(String[] args) {
		SortingGame sg = new SortingGame();
		sg.run();
	}*/
	
	public void run() {
		frame = new JFrame();
	   	panel = new JPanel();
	   	
	   	rec = new JButton("Recycle");
	   	tra = new JButton("Trash");
	   	com = new JButton("Compost");
	   	rec.setBackground(Color.blue);
	   	tra.setBackground(Color.gray);
	   	com.setBackground(Color.green);
	   	rec.setFont(font);
	   	tra.setFont(font);
	   	com.setFont(font);
	   	
	   	replay = new JButton("Play Again");
	   	replay.setBackground(Color.cyan);
	   	replay.setFont(font);
	   	replay.addActionListener(this);
	   	panel.add(replay);
	   	
	   	frame.add(panel);
	   	panel.add(rec);
	   	panel.add(tra);
	   	panel.add(com);
	   	frame.setVisible(true);
	   	rec.addActionListener(this);
	   	tra.addActionListener(this);
	   	com.addActionListener(this);
	   	
	   	frame.setSize(650, 200);
	   	
	   	scoLab = new JLabel();
	   	label = new JLabel();
	   	secLab = new JLabel();
		panel.add(scoLab);
		panel.add(label);
		panel.add(secLab);
		
	   	game();
	}
	
	public void game() {
		/*if(!itemsAL.isEmpty())
			itemsAL.clear();
		for(String s : itemsArr) {
			itemsAL.add(s);
		}*/ //enable for random
		
		score = 0;
		scoLab.setText(score + "/6");
		label.setText("");
		secLab.setText("");
		scoLab.setFont(font);
		secLab.setFont(font);
		label.setFont(font);
		
		counter = 0;
		
		//nextRand(false); //enable for random
		nextItem(false); //disable for random
	}
	
	public void nextItem(boolean prev) {
		if(prev)
			score++;
		scoLab.setText(score + "/6");
		if(counter<6) {
			label.setText(itemsArr[counter]);
			System.out.println(counter); //test
		}
		else {
			//System.out.println("done"); //test
			label.setText("Congratulations, you have finished.");
			secLab.setText("Your score is " + score + " out of 6.");
		}
	} //disable for random
	
	/*public void nextRand(boolean prev) {
		if(prev)
			score++;
		scoLab.setText(score + "/6");
		Random gen = new Random();
		if(!itemsAL.isEmpty()) {
			int x = gen.nextInt(itemsAL.size());
			String s = itemsAL.remove(x);
			//System.out.println("current: "+s); //test
			//System.out.println(itemsAL.size()+" left"); //test
			label.setText(s);
		}
		else {
			//System.out.println("done"); //test
			label.setText("Congratulations, you have finished.");
			secLab.setText("Your score is " + score + " out of 6.");
		}
	}*/ //enable for random

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bp = (JButton) e.getSource();
		if(bp.equals(replay))
			game();
		if(label.getText().equals(itemsArr[0])||label.getText().equals(itemsArr[1])) {
			if(bp.equals(rec)) {
				counter++;
				nextItem(true);
			}
			else
				nextItem(false);
		}
		if(label.getText().equals(itemsArr[2])||label.getText().equals(itemsArr[3])) {
			if(bp.equals(tra)) {
				counter++;
				nextItem(true);
			}
			else
				nextItem(false);
		}
		if(label.getText().equals(itemsArr[4])||label.getText().equals(itemsArr[5])) {
			if(bp.equals(com)) {
				counter++;
				nextItem(true);
			}
			else
				nextItem(false);
		}
	}
}
