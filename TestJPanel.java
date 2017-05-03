package Game_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class TestJPanel extends JPanel implements KeyListener, ActionListener{
	
	//github.com/narbuvold/physics-lecture
	Timer t = new Timer(17, this);
	double x = 70, y = 480, velx = 0.0, vely = 0.0, xball = 100, yball = 495, velyball = 0.0, velxball = 0.0, xbound1 = 0.0, xbound2 = 780;
	Rectangle2D [] rectangles = new Rectangle2D [10];
	Rectangle2D [] recthori = new Rectangle2D [5];
	private Rectangle2D player = new Rectangle((int)x, (int)y, 40, 40);
	private Rectangle2D rect2 = new Rectangle(600, 150, 300, 30); //floor middle
	private Rectangle2D rect3 = new Rectangle(60, 520, 600, 60); //floor bottom
	private Rectangle2D rect4 = new Rectangle(120, 0, 620, 20); // roof
	private Rectangle2D rect5 = new Rectangle(720, 520, 100, 60);
	private Rectangle2D rect7 = new Rectangle(50, 0, 20, 700); 
	private Rectangle2D rect6 = new Rectangle(600, 65, 20, 60); //1
	private Rectangle2D rect8 = new Rectangle(450, 65, 150, 20); //2
	private Rectangle2D rect9 = new Rectangle(450, 65, 20, 300); //3
	private Rectangle2D rect10 = new Rectangle(450, 365, 300, 20); //4
	private Rectangle2D rect11 = new Rectangle(750, 365, 100, 20); //5
	private Rectangle2D rect12 = new Rectangle(50, 150, 350, 20);


	private Ellipse2D ball = new Ellipse2D.Double(yball, xball, 20, 20);

	private double maxvely = 10;
	private double gravity = 0.5;
	private double massplayer = 50;
	private double massball = 20;
	private double friction = 0.4;
	private double speed = 0;
	private double fake = 0;
	private double dt = 0.5;
	private boolean test = false;
	public int counter = 0;
	
	public TestJPanel()
	{
		
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		rectangles[0] = rect2;
		rectangles[1] = rect3;
		rectangles[2] = rect4;
		rectangles[3] = rect5;
		rectangles[4] = rect6;
		rectangles[5] = rect7;
		rectangles[6] = rect8;
		rectangles[7] = rect9;
		rectangles[8] = rect10;
		rectangles[9] = rect12;
		
		//horizontal 
		recthori[0] = rect3;
		recthori[1] = rect5;
		recthori[2] = rect6;
		recthori[3] = rect9;
		recthori[4] = rect7;
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLUE);
		//player
		g2.fill(player = new Rectangle((int)x, (int)y, 40, 40));
		g2.setColor(new Color(11, 139, 34));
		//field
		g2.fill(rect2);
		g2.fill(rect3);
		g2.fill(rect4);
		g2.fill(rect5);
		g2.fill(rect6);
		g2.fill(rect7);
		g2.fill(rect8);
		g2.fill(rect9);
		g2.fill(rect10);
		g2.fill(rect12);
		g2.setColor(Color.RED);
		g2.fill(rect11);
		//moving ball
		g2.fill(ball = new Ellipse2D.Double(xball, yball, 20, 20));
		
		
		
	}
	public void actionPerformed(ActionEvent e)
	{
	
		
	
	hole();
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
	fall();
	if(x < 0.0) //dessa håller spelaren och bollen på banan
	{
		x = 0.0;
	}
	if(x > 760)
	{
		x = 760;
	}
	if(xball < 0.0)
	{
		xball = 0.0;
	}
	if(xball > 780)
	{
		xball = 780;
	}
	counter++;
		repaint();
	}
	
	public void jump()
	{
		if(gravity > 0)
		{
		vely = -5.0;
		}
		else
		{
			vely = 5.0;
		}
	}
	
	public void left()
	{
		
		velx = -5.0;
	}
	public void right()
	{
		
		velx = 5.0;
	}
	public void gravitySwitch()
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
		test = true;
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			jump();
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
	 		gravitySwitch();
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
	
	public boolean playerInAir()
	{
		double decider = 0;
		if(gravity > 0)
		{
			decider = 2;
		}
		if(gravity < 0)
		{
			decider = -2;
		}
		double newy = y + decider;
		Rectangle2D fakerect = new Rectangle((int) x, (int) newy, 40, 40);
		for(Rectangle2D item : rectangles)
		{
		if(fakerect.getBounds2D().intersects(item.getBounds2D()))
		{
			return false;	
		}
		
		}
		return true;
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
		Ellipse2D fakecircle = new Ellipse2D.Double(xball, newy, 20, 20);
		for(Rectangle2D item : rectangles)
		{
			if(fakecircle.getBounds2D().intersects(item.getBounds2D()))
			{
				friction = 2.5;
			}
		}
		
	}
	public void hole()
	{
		if(x >= 670 && x <= 720 && y >=580 )
		{
			x = 760;
			y = -50;
			vely = 0;
		}
		if(xball >= 660 && xball <= 740 && yball >= 580)
		{
			xball = 770;
			yball = 0;
			velyball = 0;
		}
		//le(750, 365, 100, 20);
		if(xball >= 750 && yball >= 365 && yball <= 370)
		{
			gravitySwitch();
			velxball = 0;
			xball = 5;
			yball = 700;
		}
		if(xball == 5 && yball <= -5)
		{
			velyball = 0;
			yball = -10;
			xball = 80;
			gravitySwitch();
		}
	}
	
	public void horizontalBounce()
	{
		if(xball == xbound1 || xball == xbound2)
		{
			velxball = -velxball;
		}	
		for(Rectangle2D item : recthori)
		{
			if(ball.getBounds2D().intersects(item.getBounds2D()))
			{
				velxball = -velxball;
			}
			
		}
	}
	
	

	
	public void ballCollision()
	{
		
		if(player.getBounds2D().intersects(ball.getBounds2D()))
		{
			velxball = fake;
			xball = xball + velx;
		}
		
		
		horizontalBounce();
		if(velxball > 0)
		{
			ballOnFloor();
			velxball = velxball - friction/(massball)*dt;
			xball += velxball*dt;
		}
		if(velxball < 0)
		{
			ballOnFloor();
			velxball = velxball + friction/(massball)*dt;
			xball += velxball*dt;
		}
		if(0.1 > velxball && velxball > -0.1)
		{
			velxball = 0;
		}
		 fake = velx;
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