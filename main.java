package Testingshiiiieeeeet;

import javax.swing.JFrame;

public class main {
	
	public static void main(String [] args)
	{
		JFrame f = new JFrame();
		TestJPanel t = new TestJPanel();
		f.add(t);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		
		
	}

}
