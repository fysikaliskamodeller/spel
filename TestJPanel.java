package Game_1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class TestJPanel extends JPanel implements KeyListener, ActionListener{
	
	Timer t = new Timer(15, this);
	double x = 100, y = 435, velx = 0.0, vely = 0.0, xball = 200, yball = 430, velyball = 0.0, velxball = 0.0, xbound1 = 0.0, xbound2 = 780;
	Rectangle2D [] rectangles = new Rectangle2D [13];
	Rectangle2D [] recthori = new Rectangle2D [6];
	private Rectangle2D player = new Rectangle((int)x, (int)y, 40, 40);
	private Rectangle2D rect2 = new Rectangle(600, 150, 300, 30); //floor middle
	private Rectangle2D rect3 = new Rectangle(50, 520, 410, 60); //floor bottom
	private Rectangle2D rect4 = new Rectangle(140, 0, 570, 20); // roof
	private Rectangle2D rect7 = new Rectangle(30, 0, 20, 700); 
	private Rectangle2D rect6 = new Rectangle(600, 65, 20, 60); //1
	private Rectangle2D rect8 = new Rectangle(450, 65, 150, 20); //2
	private Rectangle2D rect9 = new Rectangle(450, 65, 20, 300); //3
	private Rectangle2D rect10 = new Rectangle(450, 365, 350, 20); //4
	private Rectangle2D rect11 = new Rectangle(770, 180, 30, 185); //5
	private Rectangle2D rect12 = new Rectangle(50, 150, 300, 20); //6
	private Rectangle2D rect13 = new Rectangle(50, 410, 530, 20); //7
	private Rectangle2D rect14 = new Rectangle(520, 520, 130, 60); //8
	private Rectangle2D rect15 = new Rectangle(650, 560, 60, 20);
	private Rectangle2D rect16 = new Rectangle(710, 370, 150, 250);

	private Ellipse2D ball = new Ellipse2D.Double(yball, xball, 20, 20);

	private double maxvely = 10;
	public double gravity = 0.4;
	private double massball = 50;
	private double friction = 0.4;
	private double fake = 0;
	private double dt = 0.5;
	//F = (1/2)*C*p*A*v
	private double velocity = 0;
	private double angle = 0;
	private double forceair = 0;
	//luftmotståndskoefficient
	private double C = 0.02;
	//luftdensitet
	private double p = 0.1;
	
	
	
	private boolean test = false;
	public Color color = Color.GREEN;
	private BufferedImage pinguin;
	private BufferedImage pinguinright;
	private BufferedImage pinguinleft;
	private BufferedImage pinguinflippedright;
	private BufferedImage pinguinflippedleft;
	private BufferedImage flippedgoalflag;
	private BufferedImage ballimage1;
	private BufferedImage ballimage2;
	private BufferedImage ballimage3;
	private BufferedImage ballimage4;
	private BufferedImage ballimage;
	private double counter = 0;
	private boolean facing = true;
	public boolean restart = false;
	public TestJPanel()
	{
		
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		
		
		rectangles[0] = rect2;
		rectangles[1] = rect3;
		rectangles[2] = rect4;
		rectangles[4] = rect6;
		rectangles[5] = rect7;
		rectangles[6] = rect8;
		rectangles[7] = rect9;
		rectangles[8] = rect10;
		rectangles[9] = rect12;
		rectangles[10] = rect13;
		rectangles[11] = rect14;
		rectangles[12] = rect15;
		rectangles[3] = rect16;
		
		//horizontal 
		recthori[0] = rect3;
		recthori[2] = rect6;
		recthori[3] = rect9;
		recthori[4] = rect7;
		recthori[5] = rect14;
		recthori[1] = rect16;
		
		//tar in bilderna
		 try {
	         flippedgoalflag = ImageIO.read(new File(FileSystems.getDefault().getPath("flippedgoalflag.png").toUri()));
	         pinguinright = ImageIO.read(new File(FileSystems.getDefault().getPath("PinguinRight.png").toUri()));
	         pinguinleft =  ImageIO.read(new File(FileSystems.getDefault().getPath("PinguinLeft.png").toUri()));
	         pinguinflippedright =  ImageIO.read(new File(FileSystems.getDefault().getPath("PinguinFlippedRight.png").toUri()));
	         pinguinflippedleft =  ImageIO.read(new File(FileSystems.getDefault().getPath("PinguinFlippedLeft.png").toUri()));
	         ballimage1 = ImageIO.read(new File(FileSystems.getDefault().getPath("ball1.png").toUri()));
	         ballimage2 = ImageIO.read(new File(FileSystems.getDefault().getPath("ball2.png").toUri()));
	         ballimage3 = ImageIO.read(new File(FileSystems.getDefault().getPath("ball3.png").toUri()));
	         ballimage4 = ImageIO.read(new File(FileSystems.getDefault().getPath("ball4.png").toUri()));
	     } catch (IOException e) {
	         System.out.println("Image not found");
	     }
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		//boll
		g2.setColor(Color.BLUE);
		g2.fill(ball = new Ellipse2D.Double(xball, yball, 20, 20));
		g2.drawImage(ballimage, (int)xball, (int)yball, 21, 21, null);
		
		//spelpjäs
		g2.setColor(new Color(10, 34, 100));
		g2.fill(player = new Rectangle((int)x, (int)y, 40, 40));
		
		//ritar ut pingvinen på spelpjäsen och ritar ut målflaggan
		g2.drawImage(pinguin, (int)x, (int)y, 40, 40, null);
		g2.drawImage(flippedgoalflag, 70, 170, 70, 70, null);
		g2.setColor(Color.MAGENTA);
		
		//plan
		g2.fill(rect2);
		g2.fill(rect3);
		g2.fill(rect4);
		g2.fill(rect6);
		g2.fill(rect7);
		g2.fill(rect8);
		g2.fill(rect9);
		g2.fill(rect10);
		g2.fill(rect12);
		g2.fill(rect13);
		g2.fill(rect14);
		g2.fill(rect16);
		g2.setColor(Color.WHITE);
		
		g2.setColor(color);
		g2.fill(rect15);
		g2.fill(rect11);	
	}
	//detta är "game-loopen", denna metod uppdateras hela tiden och kör spelet
	public void actionPerformed(ActionEvent e)
	{	
		//här ser kollar vi om vi vunnit spelet
		if (x >= 70 && x<=140 && y >= 170 && y<=210 && xball >= 650 && xball <= 750 && yball >= 530 ) {
			if (JOptionPane.showConfirmDialog(null, "Grattis du vann!  Vill du starta om?", "Victory", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ) {
				
				main.reset();
            }else {
                System.exit(0);
            }
		}
		//om vi trycker på r och vill restarta spelet
		if(restart == true)
			main.reset();

		
		if(ballPlayerCollision() == true)
		{
			velx = 0;
			vely = 0;
		}
	//if satserna nedan kontrollerar att om vi kolliderar med bollen, väggar, om vi är i luften
	if(playerInAir() == false && test == true)
	{
		velx = 0;
		test = false;
	}
	if(playerInAir() == true && nextMove() == true)
	{
		velx = 0;
		y = y + vely;
	}
	
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
	
	
	if(x > 760) //dessa håller spelaren och bollen på banan
	{
		x = 760;
	}
	if(xball > 780)
	{
		xball = 780;
	}

	//detta byteder bilden på spelaren så att rätt bild ritas upp när vi rör oss åt ett visst håll
	if(velx >= 0 && facing == true)
		pinguin = pinguinright;
	if(velx <= 0 && facing == false)
		pinguin = pinguinleft;
	if(gravity < 0 && velx >= 0 && facing == true)
		pinguin = pinguinflippedright;
	if(gravity < 0 && velx <= 0 && facing == false)
		pinguin = pinguinflippedleft;
	
	//här byter vi bild på bollen 
	if(velxball < 0.5 && velxball > -0.5)
		ballimage = ballimage1;
	if(velxball > 0)
	{
		if(counter >= 0 && counter < 1)
			ballimage = ballimage1;
		if(counter >= 1 && counter < 2)
			ballimage = ballimage2;
		if(counter >= 2 && counter < 3)
			ballimage = ballimage3;
		if(counter >= 3 && counter < 4)
			ballimage = ballimage4;
		
		counter = counter + 0.5;
	}
	if(velxball < 0)
	{
		if(counter >= 0 && counter < 1)
			ballimage = ballimage1;
		if(counter >= 1 && counter < 3)
			ballimage = ballimage4;
		if(counter >= 2 && counter < 3)
			ballimage = ballimage3;
		if(counter >= 3 && counter < 4)
			ballimage = ballimage2;
		
		counter = counter + 0.5;
	}
	if(counter == 4)
		counter = 0;
	
	//ritar om banan 
		repaint();
	}
	
	//metoden som gör att vi kan hoppa om vi trycker på space
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
	//metoden som gör att vi kan röra oss åt vänster
	public void left()
	{
		facing = false;
		velx = -5.0;
	}
	//metoden som gör att vi kan röra oss åt höger
	public void right()
	{
		facing = true;
		velx = 5.0;
	}
	//vänder på gravitationen 
	public void gravitySwitch()
	{
		if(gravity > 0)
		{
		gravity = -Math.abs(gravity);
		color = Color.RED;
		}
		else if(gravity < 0)
		{
			gravity = Math.abs(gravity);
		color = Color.GREEN;
		}	
	}

	public void keyTyped(KeyEvent e) {}

	//tittar om vi tryckt ner någon knapp
	public void keyPressed(KeyEvent e) {
		
		if(playerInAir() == false)
		{
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			jump();
	 	if(e.getKeyCode() == KeyEvent.VK_LEFT)
			left();
	 	if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			right();
		if(e.getKeyCode() == KeyEvent.VK_R)
			main.reset();
		}
	}
	//tittar om vi släpper någon knapp
	public void keyReleased(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_UP)
			vely = 0;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			vely = 0;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
		if(playerInAir() == false)
		velx = 0;
			else
				test = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(playerInAir() == false)
			velx = 0;
			else
				test = true;
		}
	}
	//tittar om spelpjäsen är påväg att kollidera med någonting
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
	//kontrollerar om bollenkolliderar med en vägg samtdigt som spelpjäsen kolliderar med bollen
	//detta för att förhindra att det går att trycka bollen genom en vägg
	public boolean ballPlayerCollision()
	{
		double balldecider = 0;
		double decider = 0;
		if(velx > 0)
		{
		balldecider = xball + 4;
		decider = x + 2;
		}
		if(velx < 0)
		{
		balldecider = xball - 4;
		decider = x - 2;
		}
		Rectangle2D fakerect = new Rectangle((int) decider, (int) y , 40, 40);
		Ellipse2D fakeball = new Ellipse2D.Double(balldecider, yball, 20, 20);
		for(Rectangle2D item : recthori)
		{
		if(fakerect.getBounds2D().intersects(fakeball.getBounds2D()) && fakeball.getBounds2D().intersects(item.getBounds2D()))
		{
			return true;
		}
		}
			return false;	
	}

	//kontrollerar om spelare är i luften
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
	//tittar om bollen är påväg att kollidera med något
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
	//tittar om bollen är på golvet
	public boolean ballOnFloor()
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
				friction = 5.5;
				return true;
			}
		}
		return false;
	}
	//hanterar spelpjäsen och bollen när de åker igenom de hål som finns i banan
	public void hole()
	{
		if(x >= 460 && x <= 580 && y >=580 )
		{
			velx = 0;
			x = 740;
			y = -30;
			vely = 0;
		}
		if(xball >= 400 && xball <= 700 && yball >= 580)
		{
			velxball = Math.abs(velxball)-2;
			xball = 750;
			yball = -30;
			velyball = 0;
		}
		if(xball >= 740 && yball >= 180 && yball <= 365)
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
			xball = 110;
			gravitySwitch();
		}
		if(xball >= 650 && xball <= 710 && yball >=539 && yball < 540 && velyball == 0 && velxball == 0)
		{
			yball = 541;
			velyball = 0;
			gravitySwitch();
		}
	}
	
	//kontrollerar om bollen studsar i någon vägg
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
	//kontrollerar om bollen kolliderar med spelare och får då fart, här hanteras friktion och luftmotstånd på bollen
	public void ballCollision()
	{
		//spelaren puttar till bollen 
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
		
		//stannar bollen om den har låg hastighet
		if(0.1 > velxball && velxball > -0.1)
		{
			velxball = 0;
		}
		
		if(ballOnFloor() == false && velxball != 0)
		{
			//luftmotstånd
			velocity = Math.sqrt(Math.pow(velxball, 2) + Math.pow(velyball, 2));
			angle = Math.acos(velxball/velocity);
			//F = (1/2)*C*p*A*v^2
			//a = F/m
			forceair = ((0.5*C*p*(Math.pow((ball.getHeight()/2), 2) * Math.PI)*velocity)/massball);
			
			velxball = velxball - Math.cos(angle)*forceair*dt;
			velyball = velyball - Math.sin(angle)*forceair*dt;
		}
		
		 fake = velx;
		}
	
	//kontrollerar gravitationen, att spelaren och bollen faller
	public void fall()
	{
			vely += gravity;
			velyball += gravity;
			double fakeball = velyball;
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
		//detta gör att bollen studsar tillbaka om den kommer med fart ner i golvet
		if(nextBallMove() == true && velyball != 0 )
		{
			//tar bort liten del av hastigheten när den studsar i marken
			velyball = -fakeball+3;
			
			if(velyball > 0.1 && velyball > -0.1)
			{
				velyball = 0;
			}
		}
	}
}