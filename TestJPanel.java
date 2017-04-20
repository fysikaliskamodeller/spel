package Game_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class TestJPanel extends JPanel implements KeyListener, ActionListener{
	
	Timer t = new Timer(5, this);
	double x = 650, y = 20, velx = 0.0, vely = 0.0, xball = 600, yball = 200, velyball = 0.0, velxball = 0.0;
	Rectangle2D [] rectangles = new Rectangle2D [3];
	private Rectangle2D player = new Rectangle((int)x, (int)y, 40, 40);
	private Rectangle2D rect2 = new Rectangle(600, 300, 200, 30);
	private Rectangle2D rect3 = new Rectangle(0, 560, 800, 20);
	private Rectangle2D rect4 = new Rectangle(0, 0, 800, 20);
	private Ellipse2D ball = new Ellipse2D.Double(yball, xball, 20, 20);

	private double maxvely = 10;
	private double gravity = 0.05;
	private double massplayer = 10;
	private double massball = 10;
	private double friction = 0.01;
	private double speed = 0;
	private double fake = 0;
	private boolean push = true;
	
	
	public TestJPanel()
	{
		
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		rectangles[0] = rect2;
		rectangles[1] = rect3;
		rectangles[2] = rect4;
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLUE);
		//player
		g2.fill(player = new Rectangle((int)x, (int)y, 40, 40));
		g2.setColor(Color.BLACK);
		//field
		g2.fill(rect2);
		g2.fill(rect3);
		g2.fill(rect4);
		//moving ball
		g2.fill(ball = new Ellipse2D.Double(xball, yball, 20, 20));
		
		
		
	}
	public void actionPerformed(ActionEvent e)
	{
	
	ballCollision();
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
	
	if(nextBallMove() == true)
	{
		xball = xball;
		yball = yball;
	}
	else	
	{
	xball += velxball;
	yball += velyball;
	}
	
	fall(); //Tittar vi faller
		
	if(x < 0.0) //dessa håller spelaren och bollen på banan
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
	
	if(xball < 0.0)
	{
		xball = 0.0;
	}
	if(xball > 760)
	{
		xball = 760;
	}
	if(yball < 0.0)
	{
		yball = 0;
	}
	
		
		
		repaint();
	}
	
	public void up()
	{
		if(gravity > 0)
		{
		vely = -2.5;
		}
		else
		{
			vely = 2.5;
		}
	}
	public void down()
	{
		vely = 1.5;
		
	}
	public void left()
	{
		
		velx = -1.5;
	}
	public void right()
	{
		
		velx = 1.5;
	}
	public void gravityswitch()
	{
		if(gravity > 0)
		{
		gravity = -Math.abs(gravity);
		
		}
		else if(gravity < 0)
		{
			gravity = Math.abs(gravity);
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
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
	 	if(e.getKeyCode() == KeyEvent.VK_G)
	 	{
	 		gravityswitch();
	 	}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			vely = 0;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			vely = 0;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			velx = 0;
			
			
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			velx = 0;
			
			
		}
		
		
		
	}
	
	public boolean nextMove()
	{
		double newx = x + velx;
		double newy = y + vely;
		Rectangle2D fakerect = new Rectangle((int) newx, (int) newy,40,40);
		
		for(Rectangle2D item : rectangles)
		{
		if(fakerect.getBounds2D().intersects(item.getBounds2D()))
		{
			return true;	
		}
		
		}
		return false;
		
	}
	public boolean nextBallMove()
	{
		double newx = xball + velxball;
		double newy = yball + velyball;
		Ellipse2D fakecircle = new Ellipse2D.Double( newx, newy, 20, 20);
		
		for(Rectangle2D item : rectangles)
		{
		if(fakecircle.getBounds2D().intersects(item.getBounds2D()))
		{
			return true;	
		}
		
		}
		return false;
		
	}
	public void ballOnFloor()
	{
		friction = 0;
		double fdecider = 0;
		if(gravity > 0)
			fdecider = 2;
		if(gravity < 0)
			fdecider = -2;
		double newy = yball + fdecider;
		Ellipse2D fakecircle = new Ellipse2D.Double(x, newy, 20, 20);
		for(Rectangle2D item : rectangles)
		{
			if(fakecircle.getBounds2D().intersects(item.getBounds2D()))
			{
				friction = 0.1;
			}
		}
		
	}
	
	
	public void ballCollision()
	{
		//v = v0 -a*t
		//a = Fnet/m = b*v/m // m=1 b = 0.2
		//d=1/2at^2+vit
		
		
		
		if(player.getBounds2D().intersects(ball.getBounds2D()))
		{
			velxball = fake;
			xball = xball + velx;
		}
		
		{
		if(velxball > 0)
		{
			ballOnFloor();
			velxball = (velxball - (friction/(2*massball)));
			xball += velxball;
		}
		if(velxball < 0)
		{
			ballOnFloor();
			velxball = velxball + friction/(2*massball);
			xball += velxball;
		}
		if( 0.01 > velxball && velxball > -0.01)
		{
			velxball = 0;
		}
		 fake = velx;
		}
		
	}
	
	public void fall()
	{
		
	
			vely += gravity;
			velyball += gravity;
			if(vely >= maxvely)
			{
				vely = maxvely;
			}
			if(velyball >= maxvely)
			{
				velyball = maxvely;
			}
		
		if(nextMove() == true)
		{
			vely = 0;
			
		}
		if(nextBallMove() == true)
		{
			velyball = 0;
		}
	}

	
}