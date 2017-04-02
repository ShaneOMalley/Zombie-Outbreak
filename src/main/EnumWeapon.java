package main;

import art.Animation;
import art.SpriteSheetData;

public enum EnumWeapon {
	
	SMG(new Animation(1, SpriteSheetData.weaponSpriteSheet.sprites[0][2]), 1.6f, 45, 9, 1),
	SHOTGUN(new Animation(1, SpriteSheetData.weaponSpriteSheet.sprites[0][0]), 6.7f, 700, 15, 9),
	ASSAULT(new Animation(1, SpriteSheetData.weaponSpriteSheet.sprites[0][1]), 8.0f, 120, 3, 1);	
	
	public Animation animation;
	public float damage;
	public float spread;
	public int rof;
	public int shots;
	
	EnumWeapon(Animation anim, float damage, int rof, float spread, int shots) {		
		this.animation = anim;
		this.damage = damage;
		this.rof = rof;
		this.spread = spread;
		this.shots = shots;
	}
}
