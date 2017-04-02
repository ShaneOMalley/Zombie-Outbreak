package menu;

import art.Drawable;

public abstract class MenuItem implements Drawable {
	
	//MenuItem Variables
	public float x, y;
	public float w, h;
	
	public abstract void tick(long timePassed);
}
