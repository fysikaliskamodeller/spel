package Game_1;

import java.awt.Color;

import javax.swing.JFrame;

public class main {
	
	
	public static void main(String [] args)
	{
		JFrame f = new JFrame();
		TestJPanel t = new TestJPanel();
		t.setBackground(new Color(10, 34, 100));
		f.add(t);
		
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		
		
		
	}

}
