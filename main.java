package Game_1;

import java.awt.Color;

import javax.swing.JFrame;

	public class main {

		static JFrame f = new JFrame();
		static TestJPanel t = new TestJPanel();


		public static void setup(){

			t.setBackground(new Color(10, 34, 100));
			f.add(t);

			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize(800, 600);
		}
		public static void reset(){
			t.x = 100;
			t.y = 430;
			t.xball = 200;
			t.yball = 430;
			f.remove(t);
			t = new TestJPanel();
			f.add(t);
			t.setBackground(new Color(10, 34, 100));
			f.revalidate();
			
			
		}
		public static void main(String [] args)
		{
			setup();
		}
}
