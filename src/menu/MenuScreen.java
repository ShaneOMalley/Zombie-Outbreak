package menu;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.input.Mouse;

import art.Drawable;

public class MenuScreen implements Drawable {
	
	//MenuScreen Variables
	public ArrayList<MenuItem> menuItems;
	public MenuBackGround backGround;
	private MenuItemButton clickedButton = null;
	
	public MenuScreen(MenuBackGround backGround, MenuItem...MenuItems) {
		this.backGround = backGround;
		this.menuItems = new ArrayList<MenuItem>(Arrays.asList(MenuItems));
	}
	
	public void tick(long timePassed) {
		//Check which button, if any, is clicked
		clickedButton = null;
		while(Mouse.next()) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					
					for(MenuItem item : menuItems) {
						if(item instanceof MenuItemButton) {
							
							if(((MenuItemButton) item).isEntered())
								clickedButton = (MenuItemButton)item;
						}
					}
				}
			}
		}
		if(clickedButton != null) {
			clickedButton.currentTextureIndex = 2;
			clickedButton.onPressed();
		}
		
		// Tick the background and menuItems
		backGround.tick(timePassed);
		for(MenuItem item : menuItems) {
			item.tick(timePassed);
		}
		
		// Draw the MenuScreen
		draw();
	}
	
	/*
	 * Drawable Methods
	 */
	public void draw() {
		backGround.draw();
		for(MenuItem item : menuItems) {
			item.draw();
		}
	}
	
	public void draw(float x, float y, float w, float h) {		
	}
	public void draw(float x, float y, float w, float h, float angle) {
	}
	
}
