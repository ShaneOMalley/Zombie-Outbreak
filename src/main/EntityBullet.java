package main;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;

public class EntityBullet extends Entity {
	
	// EntityBullet Variables
	public Entity spawner;
	public float damage;
	public float color[]; //Red Green and Blue Values
	private float dx, dy;
	private float length;
	
	public boolean shot = false;
	
	public EntityBullet(float x, float y, float dir, int color, Entity spawner, float damage) {
		this.level = spawner.level;
		this.x = x;
		this.y = y;
		this.w = 1;
		this.h = 1;
		this.r = 1;
		this.maxSpeed = 12f;
		this.maxHp = 100.0f;
		this.hp = maxHp;
		this.rotation = dir;
		
		this.hpVisible = false;
		this.isInvincible = true;
		this.isProjectile = true;
		
		this.bouncePriority = 0;
		
		this.spawner = spawner;
		this.damage = damage;
		
		this.color = new float[3];
		
		Color col = new Color(color);
		this.color[0] = col.getRed()/255;
		this.color[1] = col.getGreen()/255;
		this.color[2] = col.getBlue()/255;
		
		length = 8;
		
		dx = length * (float)Math.cos(Math.toRadians(rotation));
		dy = length * (float)Math.sin(Math.toRadians(rotation));
	}
	
	public void draw() {
		glLineWidth(2f);
		
		glBegin(GL_LINES);
			glColor4f(color[0], color[1], color[2], 1);
			glVertex2f(x+dx, y+dy);
			glColor4f(color[0], color[1], color[2], 1);
			glVertex2f(x, y);
		glEnd();
		
		glColor4f(1, 1, 1, 1);
		
	}

	// What happens when this entity 'dies'
	public void kill() {}
}
