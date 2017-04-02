package main;
import java.util.Random;

import org.lwjgl.input.Mouse;

import program.Program;
import utility.Geometry;

public class ControllerBullet {
	
	public static void tick(EntityBullet bullet) {
		move(bullet);
		collideWithScenery(bullet);
		damageEntities(bullet);
	}
	
	private static void move(EntityBullet bullet) {
		bullet.x += bullet.maxSpeed * Math.cos(Math.toRadians(bullet.rotation-180));
		bullet.y += bullet.maxSpeed * Math.sin(Math.toRadians(bullet.rotation-180));
	}
	
	private static void collideWithScenery(EntityBullet bullet) {
		
		for(int i = 0; i < bullet.level.tiles.length; i++) {
			for(int j = 0; j < bullet.level.tiles[i].length; j++) {
				int x = i;
				int y = bullet.level.tiles[i].length - j - 1;
				
				if(bullet.level.tiles[x][y] == null) {
					continue;
				}
				
				if(!bullet.level.tiles[x][y].isSolid)
					continue;
				
				if(Geometry.contains(bullet.level.tiles[x][y].getRect(), bullet.x, bullet.y))
					bullet.destroy();
			}
		}
	}
	
	private static void damageEntities(EntityBullet bullet) {
		for(Entity ent : bullet.level.entities) {
			if(ent.isInvincible || ent == bullet.spawner)
				continue;
			
			if(!bullet.collides(ent))
				continue;
			
			Random rnd = new Random();
			
			ent.hp -= bullet.damage * (1 + (0.5f-rnd.nextFloat())*0.4);
			bullet.destroy();
			
			System.out.println(ent.hp);
		}
	}
}
