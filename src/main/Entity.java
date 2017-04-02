package main;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

import utility.Geometry;

import art.Animation;
import art.Drawable;
import art.SpriteSheetData;

public abstract class Entity implements Drawable {

	// Entity Variables
	public Level level;
	public float x, y;
	public float w, h;
	public float r;
	public float rotation;
	public float xSpeed, ySpeed;
	public float speed;
	public float maxSpeed;
	public float hp, maxHp;
	public boolean hpVisible;
	public boolean isInvincible;
	public boolean isProjectile;
	
	// Health bar variables
	private static final float healthBarW = 32;
	private static final float healthBarH = 8;	
	public static Animation[] healthBarFrames = new Animation[] {
		new Animation(1, SpriteSheetData.healthBarSheet.sprites[0][0]),
		new Animation(1, SpriteSheetData.healthBarSheet.sprites[0][1]),
		new Animation(1, SpriteSheetData.healthBarSheet.sprites[0][2]),
		new Animation(1, SpriteSheetData.healthBarSheet.sprites[0][3]),
		new Animation(1, SpriteSheetData.healthBarSheet.sprites[0][4]),
		new Animation(1, SpriteSheetData.healthBarSheet.sprites[0][5]),
		new Animation(1, SpriteSheetData.healthBarSheet.sprites[0][6]),
		new Animation(1, SpriteSheetData.healthBarSheet.sprites[0][7])
	};
	
	// Entity 'bouncing' variables
	public int bouncePriority;
	public static int bouncePriorityRecord = 1;
	
	// Draw related Variables
	public Animation animation;
	protected boolean updateAnim = true;
	
	/*
	 * Entity Methods
	 */
	public void tick(long timePassed) {
		Controller.tick(this);
		if(animation != null && updateAnim)
			animation.update(timePassed);
		
		if(hp <= 0 && !isInvincible)
			kill();
	}
	
	public void move(float dx, float dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void jumpTo(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void destroy() {
		this.level.entityRemoveBuffer.add(this);
	}
	
	// returns true if entity is cooliding with 'other'
	public boolean collides(float x, float y, float w, float h) {
		Rectangle rect1 = new Rectangle((int)this.x, (int)this.y, (int)this.w, (int)this.h);
		Rectangle rect2 = new Rectangle((int)x, (int)y, (int)w, (int)h);
		
		return rect1.intersects(rect2);
	}
	
	public boolean collides(Tile tile) {
		Rectangle rect1 = new Rectangle((int)x, (int)y, (int)w, (int)h);
		Rectangle rect2 = new Rectangle((int)tile.x, (int)tile.y, (int)tile.w, (int)tile.h);
		
		return rect1.intersects(rect2);
	}
	
	public boolean collides(Entity other) {
		float dist = Geometry.getDistance(this, other);
		return (dist <= r + other.r);
	}
	
	public boolean collides(Entity other, float add) {		
		float dist = Geometry.getDistance(this, other);
		return (dist <= r + other.r + add);
	}
	
	//Returns true if entity is in line of sight of 'other'
	public boolean isInLineOfSight(Entity other) {
		Line2D los = new Line2D.Float(x, y, other.x, other.y);
		
		float ts = level.tileSize;
		for(int i = 0; i < level.tiles.length; i++) {
			for(int j = 0; j < level.tiles[i].length; j++) {
				
				if(level.tiles[i][j] == null)
					continue;
				
				float x = i;
				float y = level.tiles[0].length - j - 1;
				if(!level.tiles[i][j].isSolid)
					continue;
				
				if(Geometry.intersects(los, x*ts, y*ts, x*ts +ts, y*ts)) return false;
				if(Geometry.intersects(los, x*ts +ts, y*ts, x*ts +ts, y*ts +ts)) return false;
				if(Geometry.intersects(los, x*ts +ts, y*ts +ts, x*ts, y*ts +ts)) return false;
				if(Geometry.intersects(los, x*ts, y*ts +ts, x*ts, y*ts)) return false;
			}
		}
		return true;
	}
	
	// What happens when this entity 'dies'
	public abstract void kill();
	
	// Private method to draw the health bar
	private void drawHP() {
//		int hpTextureIndex = (int)(hp/maxHp * healthBarFrames.length);
//		
//		if(hpTextureIndex < 0) hpTextureIndex = 0;
//		if(hpTextureIndex > healthBarFrames.length -1) hpTextureIndex = healthBarFrames.length -1;		
//		
//		healthBarFrames[hpTextureIndex].draw((x)-healthBarW/2, (float)(y + h*0.3), healthBarW, healthBarH);
		
		glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
		glBegin(GL_QUADS);
			glVertex2f(x - healthBarW/2, y + h*0.3f);
			glVertex2f(x - healthBarW/2 + healthBarW, y + h*0.3f);
			glVertex2f(x - healthBarW/2 + healthBarW, y + h*0.3f + healthBarH);
			glVertex2f(x - healthBarW/2, y + h*0.3f + healthBarH);
		glEnd();
		
		glColor4f(2-((hp/maxHp)*(2)),(hp/maxHp)*(2), 0, 1.0f);
			glBegin(GL_QUADS);
			glVertex2f(x - healthBarW/2 +1, y + h*0.3f +1);
			glVertex2f(x - healthBarW/2 + (healthBarW -1)*(hp/maxHp), y + h*0.3f +1);
			glVertex2f(x - healthBarW/2 + (healthBarW -1)*(hp/maxHp), y + h*0.3f + healthBarH -1);
			glVertex2f(x - healthBarW/2 +1, y + h*0.3f + healthBarH -1);
		glEnd();
		
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	/*
	 * Drawable Methods
	 */
	public void draw() {
		draw(x, y, w, h, rotation);
		
		if(hpVisible)
			drawHP();
	}
	
	public void draw(float x, float y, float w, float h) {
		animation.draw(x, y, w, h);
		
		if(hpVisible)
			drawHP();
	}
	
	public void draw(float x, float y, float w, float h, float angle) {
		animation.draw(x, y, w, h, angle - 180);
		
		if(hpVisible)
			drawHP();
	}
}
