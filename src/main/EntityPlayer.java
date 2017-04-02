package main;

import program.Program;
import art.Animation;
import art.SpriteSheetData;

public class EntityPlayer extends Entity {
	
	// EntityPlayer Variables
	public static final float X_POS_ON_SCREEN = Program.WINDOW_WIDTH/2;
	public static final float Y_POS_ON_SCREEN = Program.WINDOW_HEIGHT/2;
	
	public EnumWeapon currentWeapon;
	public int shootCoolDown;
	
	public float damageBuffer;
	public float maxDamagePerTick;
	
	public EntityPlayer(float x, float y, Level level) {
		// Setup Variables
		this.level = level;
		this.x = x;
		this.y = y;
		this.w = level.tileSize * 1;
		this.h = level.tileSize * 1;
		this.r = level.tileSize * 0.4f;
		this.maxSpeed = 2.1f;
		this.maxHp = 100.0f;
		this.hp = maxHp;
		this.rotation = 0.0f;
		
		this.hpVisible = false;
		this.isInvincible = false;
		this.isProjectile = false;
		
		this.bouncePriority = 0;
		
		this.currentWeapon = null;
		this.shootCoolDown = 0;
		
		this.damageBuffer = 0.0f;
		this.maxDamagePerTick = 0.86f;
		
		// sets up the animation
		animation = new Animation(125, 
				SpriteSheetData.playerSpriteSheet.sprites[0][0],
				SpriteSheetData.playerSpriteSheet.sprites[0][1],
				SpriteSheetData.playerSpriteSheet.sprites[0][2],
				SpriteSheetData.playerSpriteSheet.sprites[0][3]
				);
	}
	
	public void tick(long timePassed) {
		// Standard Entity.tick() functions
		Controller.tick(this);
		if(animation != null && updateAnim)
			animation.update(timePassed);
		
		shootCoolDown -= timePassed;
		if(shootCoolDown <= 0) shootCoolDown = 0;
		
		if(hp <= 0 && !isInvincible)
			kill();
		
		// Health loss
		if(damageBuffer > maxDamagePerTick) damageBuffer = maxDamagePerTick;
		hp -= damageBuffer;
		damageBuffer = 0;
	}
	
	// What happens when this entity 'dies'
	public void kill() {
		EventManager.playerDead();
	}
}
