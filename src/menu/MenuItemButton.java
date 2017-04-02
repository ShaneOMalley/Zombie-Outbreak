package menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import utility.Geometry;
import art.*;

public abstract class MenuItemButton extends MenuItem {
	
	//MenuItemButton Variables
	protected Animation[] textures;
	protected int currentTextureIndex;
	
	public MenuItemButton(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		currentTextureIndex = 0;
	}
	
	//what to do when the button is pressed
	public abstract void onPressed();
	
	//Update the button and check if it is pressed
	public void tick(long timePassed) {		
		if(isEntered())
			currentTextureIndex = 1;
		else
			currentTextureIndex = 0;
		
		textures[currentTextureIndex].update(timePassed);
	}
	
	//returns 'true' if the button is clicked
	protected boolean isClicked() {
		return false;
	}
	
	public boolean isEntered() {
		if(Geometry.contains(Mouse.getX(), Mouse.getY(), x, y, w, h))
			return true;
		return false;
	}
	
	/*
	 * Drawable Methods
	 */
	public void draw() {
		draw(x, y, w, h);
	}
	
	public void draw(float x, float y, float w, float h) {
		textures[currentTextureIndex].draw(x, y, w, h);
	}
	
	public void draw(float x, float y, float w, float h, float angle) {
		textures[currentTextureIndex].draw(x, y, w, h, 0);
	}
}
