package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import sun.audio.*;


public class MyCanvas extends Canvas implements KeyListener{
	int time = 0;
    int score = 0;
	Goodguy Mario = new Goodguy(10,10,100,100,"Goodguy.png");
	LinkedList badguys = new LinkedList();
	LinkedList Medkit = new LinkedList();
	boolean start = false;
	boolean GameOver = false;
	Image heart = Toolkit.getDefaultToolkit().createImage("files/heart.png");
	int health = 2;
	
	public MyCanvas() {
		this.setSize(1200,864);
		this.addKeyListener(this);
		playIt("files/Main.wav");
				
		Random rand = new Random();
		int winwidth = this.getWidth();
		int winheight = this.getHeight();
		for(int i = 0; i<30; i ++) {
			if (time % 10 == 0) {	
			Badguy bg = new Badguy(rand.nextInt(winwidth)+2000, rand.nextInt(winheight-250),100,100,"files/Badguyleft.png");
				Rectangle r = new Rectangle (200,200,30,30);
				if (r.contains(Mario.getxCoord(),Mario.getyCoord())) {
					System.out.println("badguy on top of link");
					
					continue;
				}
				badguys.add(bg);
			}
			
			}
		TimerTask repeatedTask = new TimerTask() {
	        public void run() {
	        	for(int i = 0; i < badguys.size(); i++) {
	    			Badguy bg = (Badguy) badguys.get(i);
	    			bg.setxCoord(bg.getxCoord() - 3  );
	        	}

	            repaint();
	        }
	    };
	    Timer timer = new Timer("Timer");
	    long delay  = 0L;
	    long period = 20L;
	    timer.scheduleAtFixedRate (repeatedTask, delay, period);
	}
		
	public void paint(Graphics g ) {
		
			if (badguys.size()==0) {
				this.setBackground(Color.BLACK);
				g.setColor(Color.WHITE);
				Font myFont = new Font("Comic Sans", 1, 60);
				g.setFont(myFont);
				g.drawString("Thank you Mario, very cool!", 170, 400);
			} 
			
			else if (start == true && GameOver == false) {
				
				Badguy back = new Badguy(-200, -200, 1400, 1050, "files/Background.png");
				g.drawImage(back.getImg(), back.getxCoord(), back.getyCoord(), back.getWidth(), back.getHeight(), this);
				g.drawImage(Mario.getImg(), Mario.getxCoord(), Mario.getyCoord(), Mario.getWidth(),Mario.getHeight(),this);
				if (health == 2) {
					g.drawImage(heart, 750, 10, 100, 50, this);
					g.drawImage(heart, 825, 10, 100, 50, this);
				} else if (health == 1) {
					g.drawImage(heart, 750, 10, 100, 50, this);
				} else if (health == 0) {
					GameOver = true;
				}
				Font font = new Font("Helvetica", Font.BOLD, 90);
				g.setFont(font);
				g.setColor(Color.WHITE);
				g.drawString("Luigis Saved: " + Integer.toString(score), 72, 82);
				for(int i = 0; i < badguys.size(); i++) {
		
					Badguy bg = (Badguy) badguys.get(i);
					g.drawImage(bg.getImg(), bg.getxCoord(), bg.getyCoord(), bg.getWidth(),bg.getHeight(),this);
					Rectangle r = new Rectangle(bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight());
					
					if (bg.getxCoord() <= 2) {
						
						start = false;

						GameOver = true;
					}
					
					for(int j=0; j < Medkit.size(); j++) {
						Projectile k = (Projectile) Medkit.get(j);
						if (k.getxCoord()>this.getWidth()) {Medkit.remove(k);}
						k.setxCoord(k.getxCoord()+1);
						
						g.drawImage(k.getImg(), k.getxCoord(), k.getyCoord(), k.getWidth(),k.getHeight(),this);
						int h;
						int w;
						Rectangle kr = new Rectangle(k.getxCoord(),k.getyCoord(),k.getWidth(),k.getHeight());
						
						if(kr.intersects(r)) {
							badguys.remove(i);
							Medkit.remove(j);
							score ++;
						}
						
						
						repaint();
						}
					}
			}
			else if (start == false && GameOver == true) {

				g.setColor(Color.WHITE);
				Font myFont = new Font("Comic Sans", 1, 60);
				g.setFont(myFont);
				g.drawString("You lose, better luck next time.", 170, 400);
				this.setBackground(Color.BLACK);
				
			}
			else if (!GameOver) {
				this.setBackground(Color.BLACK);
				g.setColor(Color.WHITE);
				Font myFont = new Font("Comic Sans", 1, 60);
				g.setFont(myFont);
				g.drawString("Press Enter to Start", 350, 400);
			}
		}
	public void keyPressed (KeyEvent e) {
			if (start) {
				if(e.getKeyCode()== 32) {
					Projectile Medkits = new Projectile(Mario.getxCoord(), Mario.getyCoord(), 100, 100, "files/Medkit.png");
					Medkit.add(Medkits);
					playIt("files/Whoosh.wav");
		
				}
				Mario.moveIt(e.getKeyCode(),this.getWidth(),this.getHeight());
				
				for(int i = 0; i <badguys.size(); i++) {
					Badguy bg = (Badguy) badguys.get(i);
					Rectangle r = new Rectangle(bg.getxCoord(),bg.getyCoord(),bg.getWidth(),bg.getHeight());
					
					if (r.intersects(Mario.getxCoord(), Mario.getyCoord(), Mario.getWidth(),Mario.getHeight())) {
						System.out.println("badguy hit by link");
						health = health - 1;
						badguys.remove(i);
						
						if (health == 0) {
							GameOver = true;
							start = false;
						}
					}
				}
			}
			else {
				if (e.getKeyCode()==10) {
					start = true;
				}
			}
			repaint();
		}
	public void playIt(String filename) {
		try {
			InputStream in = new FileInputStream(filename);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
