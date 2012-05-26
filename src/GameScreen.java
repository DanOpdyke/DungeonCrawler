import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class GameScreen extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3653013745102588148L;

	static final int CANVAS_WIDTH = 800;
	static final int CANVAS_HEIGHT = 600;
	static final int PLAYER_SPEED = 4;
	
	 static final int UPDATE_RATE = 24;    // number of game update per second
	 static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;  // nanoseconds
	
	int playerX;
	int playerY;
	boolean mouseDown = false;
	
	private GameCanvas canvas;
	
	public GameScreen(){
		canvas = new GameCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		this.setContentPane(canvas);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("Dungeon Crawler");
		this.setVisible(true);
		
		playerX = CANVAS_WIDTH / 2;
		playerY = CANVAS_HEIGHT / 2;
	}
	
	public void gameDraw(Graphics2D g){
		g.setColor(Color.red);
		g.drawOval(playerX, playerY, 20, 20);
	}
	
	public void gameStart(){
		Thread gameThread = new Thread(){
			@Override
			public void run(){
				gameLoop();
			}
		};
		gameThread.start();
	}
	
	public void updatePlayerPosition(){
		Point dest = getMousePosition();
		if(dest.getX() != (double)playerX)
			playerX = playerX>dest.getX()?playerX-PLAYER_SPEED:playerX+PLAYER_SPEED;
		if(dest.getY() != (double)playerY)
			playerY = playerY>dest.getY()?playerY-PLAYER_SPEED:playerY+PLAYER_SPEED;
	}
	
	private void gameLoop(){
		long beginTime, elapsedTime, timeRemaining;
		while(true){
			beginTime = System.nanoTime();
			if(mouseDown)
				updatePlayerPosition();
			repaint();
			elapsedTime = System.nanoTime() - beginTime;
			timeRemaining = (UPDATE_PERIOD - elapsedTime) / 1000000;
			if(timeRemaining < 10) timeRemaining = 10;
			try{
				Thread.sleep(timeRemaining);
			} 
			catch(InterruptedException ex){}
		}
	}
	
	class GameCanvas extends JPanel implements MouseListener{
		
		public GameCanvas(){
			setFocusable(true);
			requestFocus();
			addMouseListener(this);
			gameStart();
			//TODO Add keylistener?
		}
		
		@Override
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			super.paintComponent(g2d);
			setBackground(Color.black);
			gameDraw(g2d);
		}

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseDown = true;
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseDown = false;
		}
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new GameScreen();
			}
		});
	}
	
}


