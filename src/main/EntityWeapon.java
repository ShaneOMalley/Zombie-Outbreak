package main;

public class EntityWeapon extends Entity {
	
	public EnumWeapon weaponType;
	
	public EntityWeapon(float x, float y, EnumWeapon type, Level level) {
		this.weaponType = type;
		
		this.animation = weaponType.animation;
		
		this.level = level;
		this.x = x;
		this.y = y;
		this.w = 56;
		this.h = 20;
		this.r = 15;
		this.maxSpeed = 0;
		this.maxHp = 100.0f;
		this.hp = maxHp;
		this.rotation = 180.0f;
		
		this.hpVisible = false;
		this.isInvincible = true;
		this.isProjectile = false;
		
		this.bouncePriority = 0;
	}
	
	// What happens when this entity 'dies'
	public void kill() {}
}
