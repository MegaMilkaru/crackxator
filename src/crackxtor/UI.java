package crackxtor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class UI {
	public static Crackxtor crackxtor;
	
	//Frame specifics
	int width = 420;
	int height = 600;
	JFrame frame = new JFrame("CRACKXATOR");
	
	//Other components
	JTextField url = new JTextField(100);
	JButton crackxtionStart = new JButton("Begin Operation");
	JTextArea statusReport = new JTextArea();
	JScrollPane scrollV;
	
	//MAGIC:
	UI() {
		//==============COMPONENTS==============//
		//url TEXT FIELD
		url.setBounds(0, 0, width, 20);
		
		//crackxationStart BUTTON
		crackxtionStart.setBounds(width/2 - 100, 20, 200, 40);
		crackxtionStart.setFocusable(false);
		crackxtionStart.addActionListener(e -> {
//			statusReport = new JTextArea();
//			statusReport.setBounds(0, 60, width, height - 60);
//			statusReport.setEditable(false);
			try {
				crackxtor.crackxate(url.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		//statusReport TEXT AREA
		statusReport.setBounds(0, 60, width, height - 60);
//		statusReport.
		statusReport.setEditable(false);
		
		//turn statusReport into SCROLL PANE
		scrollV = new JScrollPane(statusReport);
		scrollV.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		//==============MAIN FRAME==============//
		//Add all components
		frame.add(url);
		frame.add(crackxtionStart);
		frame.add(statusReport);
//		frame.add(scrollV);
		
		//Finalize main frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width + 15, height);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public void reportUpdate(String text) {
		statusReport.append(text + "\n");
	}
	
	//Keeping this block of text incase if you wanna remember this.
//	private class crackxationActionListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("you can rest now");
//		}
//	}
}
