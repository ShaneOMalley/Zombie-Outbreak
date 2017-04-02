package main;

import java.awt.Rectangle;

import art.Animation;
import art.Drawable;

public class Tile implements Drawable {
	
	// Tile Variables
	public float x, y;
	public float w, h;
	public boolean isSolid;
	
	protected int layer = 0;
	
	// Draw related Variables
	public Animation animation;
	
	public void tick(long timePassed) {
		animation.update(timePassed);
	}
	
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, (int)w, (int)h);
	}
	
	public void draw() {
		draw(x, y, w, h);
	}
	
	public void draw(float x, float y, float w, float h) {
		animation.draw(x, y, w, h);
	}
	
	public void draw(float x, float y, float w, float h, float angle) {
		animation.draw(x, y, w, h, angle);
	}	
}
