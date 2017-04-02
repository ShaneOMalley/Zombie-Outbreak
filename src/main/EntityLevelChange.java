package main;

import art.Animation;
import art.SpriteSheetData;

public class EntityLevelChange extends Entity {
	
	public EntityLevelChange(float x, float y, float size) {
		this.x = x;
		this.y = y;
		this.w = size;
		this.h = size;
		this.r = size*0.4f;
		
		this.animation = new Animation(1,
				SpriteSheetData.tileSpriteSheet.sprites[0][0]);
	}
	
	public void tick(long timePassed) {
		if(ZombieOutbreak.activePlayer.collides(this))
			EventManager.changeLevel(ZombieOutbreak.currentLevelIndex + 1);
		
		draw();
	}
	
	/*
	 * Drawable Methods
	 */
	public void draw() {
		draw(x, y, w, h, rotation);
	}
	
	public void draw(float x, float y, float w, float h) {
		animation.draw(x, y, w, h);
	}
	
	public void draw(float x, float y, float w, float h, float angle) {
		animation.draw(x, y, w, h, angle - 180);
	}
	
	public void kill() {
	}
}
