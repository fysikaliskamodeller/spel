package Testingshiiiieeeeet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class TestJPanel extends JPanel implements KeyListener, ActionListener{
	
	Timer t = new Timer(5, this);
	double x = 0.0, y = 0.0, velx = 0.0, vely = 0.0;
	private Ellipse2D circle = new Ellipse2D.Double(x, y, 40, 40);
	private Rectangle2D circle2 = new Rectangle(100, 100, 100, 100);
	double oldx = 0;
	double oldy = 0;
	
	public TestJPanel()
	{
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.fill(circle = new Ellipse2D.Double(x, y, 40, 40));
		g2.fill(circle2 = new Rectangle(100, 100, 700, 100));
		
		
		
	}
	public void actionPerformed(ActionEvent e)
	{
		
	if(nextMove() == true)
	{
		x = x;
		y = y;
	}
	else
	{
		x += velx;
		y += vely;
	}
		
		if(x < 0.0)
		{
			x = 0.0;
		}
		if(x > 760)
		{
			x = 760;
		}
		if(y < 0.0)
		{
			y = 0;
		}
		if(y > 540)
		{
			y = 540;
		}
		
		
		repaint();
	}
	
	public void up()
	{
		vely = -1.5;
		velx = 0;
	}
	public void down()
	{
		vely = 1.5;
		velx = 0;
	}
	public void left()
	{
		vely = 0;
		velx = -1.5;
	}
	public void right()
	{
		vely = 0;
		velx = 1.5;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			up();
		}
	 	if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			down();
		}
	 	if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			left();
		}
	 	if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			right();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			vely = 0;
			velx = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			vely = 0;
			velx = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			velx = 0;
			vely = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			velx = 0;
			vely = 0;
		}
		
	}
	
	public boolean nextMove()
	{
		double newx = x + velx;
		double newy = y + vely;
		Ellipse2D fakecircle = new Ellipse2D.Double(newx,newy,40,40);
		if(fakecircle.getBounds2D().intersects(circle2.getBounds2D()))
		{
			return true;
			
		}
		else
		{
			return false;
		}
	}

}
