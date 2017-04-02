package main;

import art.Animation;

public class EntityStatic extends Entity {
	
	public EntityStatic(Level level, float x, float y, float rot, boolean solid, 
			float width, float height, Animation animation) {
		
		// Setup Variables
		this.level = level;
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		this.r = width * 0.8f;
		this.maxSpeed = 0;
		this.speed = maxSpeed;
		this.maxHp = 1.0f;
		this.hp = maxHp;
		this.rotation = rot;
		
		this.hpVisible = false;
		this.isInvincible = true;
		this.isProjectile = false;
		
		this.animation = animation;
		
		this.bouncePriority = bouncePriorityRecord++;
	}
	
	public void kill() {
		level.entityRemoveBuffer.add(this);
	}
}
