package Game_1;

import java.awt.Color;

import javax.swing.JFrame;

	public class main {

		static JFrame f = new JFrame();
		static TestJPanel t = new TestJPanel();

		public static void reset()
		{
			t.restart = false;
			t.x = 100;
			t.y = 430;
			t.xball = 200;
			t.yball = 430;
			t.gravity = 0.4;
			t.velx = 0;
			t.vely = 0;
			t.velxball = 0;
			t.velxball = 0;
			t.color = Color.GREEN;
		}
		
		public static void main(String [] args)
		{
			t.setBackground(new Color(10, 34, 100));
			f.add(t);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize(800, 600);
			f.setLocationRelativeTo(null);
		}
}
