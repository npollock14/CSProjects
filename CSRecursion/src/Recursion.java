import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

public class Recursion extends JPanel
		implements ActionListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	private static final long serialVersionUID = 1L;

	int screenWidth = 1000;
	int screenHeight = 1000;
	boolean[] keys = new boolean[300];
	boolean[] keysToggled = new boolean[300];
	boolean[] mouse = new boolean[200];
	int centerX = screenWidth / 2;
	int centerY = screenHeight - 100;

	double initialBranchHeight = 500; //300
	double lineLengthLimit = 10; //10
	float initialThickness = 2.0f;
	
	float angleIncrease = 0.4f; //.4
	float branchesDeviation = 2f;//1.8
	float lenMean = .70f; //.7
	float lenDev = .05f; //.05
	float treeLean = 0.0f; //0.0
	float thickMean = .75f; //.75
	float thickDev = .07f; //.07

	int frame = 0;
	int seed = 3513;
	Random r = new Random(seed);
	JSlider s1 = new JSlider();
	//3513
	//4274

	// ============== end of settings ==================

	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g.drawString("Seed: " + seed, 0, 100);
		r = new Random(seed);
		
		circles(g2, 100,300,300,0);
		
		//DONE
		//SNOWFLAKE
		//branch2(500, g2, centerX, centerY,0);
		//TREE
		//branch(300, g2, centerX, centerY, 0, 1);
		//CIRCLE THING
		//clover(g2, 300,0,0);	
		//SQUARE THING
		//squares(g2, 300, screenWidth/2 - 150, screenHeight/2 - 150);
	}

	public void update() {
		if (keys[32]) {
			seed = rBtw(0, 10000);
		}
	}

	private void init() {
		s1.setVisible(true);

	}
	
	public void circles(Graphics2D g, double w, int centerX, int centerY, double a) {
		if(a != 0) g.drawOval((int) (centerX - w), (int) (centerY-w), (int)w*2, (int)w*2);
		g.drawOval((int) (300 - w * a/Math.PI), (int) (300 - w * a/Math.PI), (int)w*2, (int)w*2);
		a += Math.PI/24;
		if(a < Math.PI*2){
			circles(g,w, 300 + (int)(100*Math.sin(a)), 300 + (int) (100 * Math.cos(a)), a);
		}
		
		
		
	}
	
	

	public void branch(double len, Graphics2D g2, int centerX, int centerY, double a, float thickness) {
		drawLine(centerX, centerY, len, a, g2, thickness);
		//g2.drawOval((int) (centerX - len), (int) (centerY-len), (int)len*2, (int)len*2);
		if (len >= lineLengthLimit) {
			for (int i = 0; i < 2; i++) {
				branch(len * .6, g2, (int) (centerX + len * Math.sin(a)),
						(int) (centerY - (len * Math.cos(a))), a + Math.PI/4,
						thickness * 1f);
				branch(len * .6, g2, (int) (centerX + len * Math.sin(a)),
						(int) (centerY - (len * Math.cos(a))), a - Math.PI/4,
						thickness * 1f);
			}
		}
	}
	
	public void clover(Graphics2D g2, int radius, int x, int y) {
		g2.drawOval(x, y, radius, radius);
		g2.drawOval(x+radius, y, radius, radius);
		g2.drawOval(x, y+radius, radius, radius);
		g2.drawOval(x+radius, y+radius, radius, radius);
		
		if(radius > 10) {
			clover(g2, radius - 10,x+10, y+10);
		}
		
	}
	
	public void branch2(double len, Graphics2D g2, int centerX, int centerY, double a) {
		if(a != 0) drawLine(centerX, centerY, len, a, g2, 1);
		if (len >= lineLengthLimit) {
			for(int i = 0; i < 5; i++){
				branch2(len * .4, g2, (int) (centerX + len * Math.sin(a)),
						(int) (centerY - (len * Math.cos(a))), a + Math.toRadians(72*(i+1)));
			}
			
			
		}
	}
	
	public void squares(Graphics g2, int w, int x, int y) {
		g2.drawRect(x,y,w,w);
			
		if(w > 10) {
			squares(g2, w/4,x-w*2/4,y-2*w/4);
			squares(g2, w/4, x-w*2/4, y+w*3/8);
			squares(g2, w/4, x-w*2/4, y+w*5/4);
			squares(g2, w/4,x+w*3/8,y-2*w/4);
			
			squares(g2, w/4,x+w*3/8,y+5*w/4);
			squares(g2, w/4,x+w*5/4,y-2*w/4);
			squares(g2, w/4, x+w*5/4, y+w*3/8);
			squares(g2, w/4, x+w*5/4, y+w*5/4);
			
			//squares(g2, (int)(w - w*dec), (int)(x + w*dec*2 + w/2), (int)(y + w*dec*2 +  w/2));
			//squares(g2, (int)(w - w*dec), (int)(x - w*dec*2 - w/2), (int)(y + w*dec*2 +  w/2));
			
			//squares(g2, (int)(w - w*dec), (int)(x + w*dec*2 + w/2), (int)(y));
			//squares(g2, (int)(w - w*dec), (int)(x - w*dec*2 - w/2), (int)(y));

		}
		
	}

	public void drawLine(int x, int y, double len, double a, Graphics2D g2, float thickness) {
		g2.setStroke(new BasicStroke(thickness));
		g2.drawLine(x, y, (int) (x + len * Math.sin(a)), (int) (y - len * Math.cos(a)));
	}

	public int rBtw(int min, int max) {
		return (int) ((Math.random() * (max - min + 1.0) + min));
	}

	public int randSign() {
		return Math.random() > .5 ? 1 : -1;
	}

	public float gau(float mean, float sd) {
		return (float) (r.nextGaussian() * sd + mean);

	}

	public Color rColor() {
		return new Color((int) rBtw(0, 255), (int) rBtw(0, 255), (int) rBtw(0, 255));
	}
	// ==================code above ===========================

	@Override
	public void actionPerformed(ActionEvent arg0) {

		update();
		repaint();
		// t.stop();
	}

	public static void main(String[] arg) {
		@SuppressWarnings("unused")
		Recursion d = new Recursion();
	}

	public Recursion() {
		JFrame f = new JFrame();
		f.setTitle("Fractal Tree");
		f.setSize(screenWidth, screenHeight);
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.addKeyListener(this);
		f.addMouseMotionListener(this);
		f.addMouseWheelListener(this);
		f.addMouseListener(this);

		f.add(this);

		t = new Timer(15, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		init();

	}

	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;

	}

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		keys[e.getKeyCode()] = false;

		if (keysToggled[e.getKeyCode()]) {
			keysToggled[e.getKeyCode()] = false;
		} else {
			keysToggled[e.getKeyCode()] = true;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouse[e.getButton()] = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouse[e.getButton()] = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouse[e.getButton()] = true;

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}