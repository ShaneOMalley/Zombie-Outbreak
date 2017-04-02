package main;

import java.util.Random;

import art.Animation;
import art.SpriteSheetData;

public class EntityZombie extends Entity {
	
	// EntityZombie Variables
	public boolean alert;
	public float damage;
	public long maxDamageCoolDown;
	public long damageCoolDown;
	
	private Random rnd;
	
	public EntityZombie(float x, float y, Level level) {
		// Setup Variables
		this.level = level;
		this.x = x;
		this.y = y;
		this.w = level.tileSize;
		this.h = level.tileSize;
		this.rotation = (new Random()).nextFloat()*360.0f;
		this.r = 9;
		this.maxSpeed = 2.0f;
		this.speed = maxSpeed;
		this.maxHp = 40.0f;
		this.hp = maxHp;
		
		this.hpVisible = false;
		this.isInvincible = false;
		this.isProjectile = false;
		
		this.alert = false;
		
		this.damage = 0.56f;
		this.maxDamageCoolDown = 16;
		this.damageCoolDown = 0;
		
		this.bouncePriority = bouncePriorityRecord++;
		
		// sets up the animation
		rnd = new Random();
		int i = rnd.nextInt(4);
		
		animation = new Animation(125, 
				SpriteSheetData.zombieSpriteSheet.sprites[i][0],
				SpriteSheetData.zombieSpriteSheet.sprites[i][1],
				SpriteSheetData.zombieSpriteSheet.sprites[i][2],
				SpriteSheetData.zombieSpriteSheet.sprites[i][3]
				);
		
	}
	
	public void tick(long timePassed) {
		// Standard Entity.tick() functions
		Controller.tick(this);
		if(animation != null && updateAnim)
			animation.update(timePassed);
		
		if(hp <= 0 && !isInvincible)
			kill();
		
		// Zombie Attack cool down
		damageCoolDown -= timePassed;
		if(damageCoolDown < 0) damageCoolDown = 0;
	}
	// What happens when this entity 'dies'
	public void kill() {
		level.entityCreateBuffer.add(new EntityStatic(level, x, y, rnd.nextFloat() * 360, false, w, h, 
				new Animation(1, SpriteSheetData.tileSpriteSheet.sprites[3][2])));
		destroy();
	}
}
