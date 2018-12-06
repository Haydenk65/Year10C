package game;

import java.awt.Image;
import java.awt.Toolkit;

public class Goodguy {

	private int xCoord = 0;
	private int yCoord = 0;
	private int width = 10;
	private int height = 10;
	private Image img;
	
	public Goodguy() {
		setxCoord(300);
		setyCoord(10);
		setWidth(3);
		setHeight(3);
		
	}
	
	public Goodguy(int x, int y, int w, int h, String imgpath) {
		setxCoord(x);
		setyCoord(y);
		setWidth(w);
		setHeight(h);
		setImg(imgpath);
		setImg("files/Goodguy.png");
	}
	
	public void moveIt(int direction, int w, int h) {
		int speed = 25;
		int x = getxCoord();
		int y = getyCoord();
	
		if (direction == 39) {
			x = x + speed;
			if (x > w) { x = x - speed * 3; }
			setxCoord(x);
			setImg("files/Goodguy.png");
			
		} else if (direction == 37) {
			x = x - speed;
			if (x < 0) { x = x + speed * 3; }
			setxCoord(x);
			setImg("files/GoodguyLeft.png");
			
		} else if (direction == 38) {
			y = y - speed;
			if (y < 0) { y = y + speed * 3; }
			setyCoord(y);
			setImg("files/Goodguy.png");
			
		} else if (direction == 40) {
			y = y + speed;
			if (y > h - 100) { y = y - speed * 3; }
			setyCoord(y);
			setImg("files/Goodguy.png");
			
		}
	}
	
	
	
	public void setImg(String imgpath) {
		this.img = Toolkit.getDefaultToolkit().getImage(imgpath);
		
	}

	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
}
