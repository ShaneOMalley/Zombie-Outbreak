package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import utility.Geometry;

import static org.lwjgl.opengl.GL11.*;

public class ControllerZombie {
	
	private static final int MAX_ITERATIONS = 3;	
	private static float xProjectedMove = 0, yProjectedMove = 0;
	private static float[][] ps;
	
	public static void tick(EntityZombie zombie) {
		rotateTowardsPlayer(zombie);
		updatePS(zombie);
		stepForward(zombie);
		bounceOffEntity(zombie);
		processCollisions(zombie);
		processEntityCollision(zombie);
		updateZombie(zombie);
	}
	
	private static void rotateTowardsPlayer(EntityZombie zombie) {
		EntityPlayer player = ZombieOutbreak.activePlayer;
		if(zombie.alert)
			zombie.rotation = Geometry.getAngle(zombie.x, zombie.y, player.x, player.y);
	}
	
	private static void stepForward(EntityZombie zombie) {
		if(zombie.alert) {
			zombie.xSpeed = zombie.maxSpeed * -(float)Math.cos(Math.toRadians(zombie.rotation));
			zombie.ySpeed = zombie.maxSpeed * -(float)Math.sin(Math.toRadians(zombie.rotation));
		}
	}
	
	private static void bounceOffEntity(EntityZombie zombie) {
		List<Entity> ents = zombie.level.entities;
		for(Entity entity : ents) {
			
			// Do not bounce if entity is off the screen
			if(!Geometry.onScreen(zombie, zombie.level.tileSize))
				continue;
			
			//Do not bounce if this has a lower bounce priority
			if(zombie.bouncePriority < entity.bouncePriority)
				continue;
			
			//Do not bounce off itself
			if(entity == zombie)
				continue;
			
			//Only Bounce of specified Entities
			if(!(entity instanceof EntityPlayer)
					&& !(entity instanceof EntityZombie))
				continue;
			
			//Checks if the entities are intersecting
			if(Geometry.getDistance(zombie, entity) > zombie.r + entity.r)
				continue;			
			
			float distance = Geometry.getDistance(zombie, entity);			
			float intersectAmount = (zombie.r + entity.r) - distance;			
			float angle = Geometry.getAngle(zombie, entity);
			
			float dx = intersectAmount * (float)Math.cos(angle);
			float dy = intersectAmount * (float)Math.sin(angle);
			
			zombie.xSpeed = dx*0.25f;
			zombie.ySpeed = dy*0.25f;
		}
	}
	
	private static void processCollisions(EntityZombie zombie) {

		boolean xContact = true;
		boolean yContact = true;		
		
		for(int iterations = 0; iterations < MAX_ITERATIONS && (xContact || yContact); iterations++) {
			
			xContact = false;
			yContact = false;
			for(int i = 0; i < zombie.level.tiles.length; i++) {
				for(int j = 0; j < zombie.level.tiles[i].length; j++) {
					
					if(zombie.level.tiles[i][j] == null)
						continue;
					
					if(!zombie.level.tiles[i][j].isSolid)
						continue;
					
					if(!Geometry.onScreen(zombie, zombie.level.tileSize))
						continue;
					
					if(xContact && yContact)
						continue;
					
					Tile tile = zombie.level.tiles[i][j];
		
					//dir: 0 = Bottom, 1 = Right, 2 = Top, 3 = Left
					for(int dir = 0; dir < 4 && !(xContact && yContact); dir++) {
						
						if(dir == 0 || dir == 2) yProjectedMove = zombie.ySpeed;
						else yProjectedMove = 0;
						
						if(dir == 1 || dir == 3) xProjectedMove = zombie.xSpeed;
						else xProjectedMove = 0;
						
						while(containsPS(tile, xProjectedMove, yProjectedMove, dir*2, dir*2+1)) {
							
							if(dir == 0) {
								yContact = true;
								yProjectedMove += 0.5f;
							}
								
							if(dir == 1) {
								xContact = true;
								xProjectedMove -= 0.5f;
							}
							
							if(dir == 2) {
								yContact = true;
								yProjectedMove -= 0.5f;
							}
							
							if(dir == 3) {
								xContact = true;
								xProjectedMove += 0.5f;
							}
							
							updatePS(zombie);
						}
						
						if((dir == 1 || dir == 3) && xContact) {
							zombie.xSpeed = xProjectedMove;
						}
						if((dir == 0 || dir == 2) && yContact) {
							zombie.ySpeed = yProjectedMove;
						}
						
						if(yContact) {
							zombie.y += zombie.ySpeed;
							zombie.ySpeed = 0.0f;
						}
						
						if(xContact) {
							zombie.x += zombie.xSpeed;
							zombie.xSpeed = 0.0f;
						}
					}
				}
			}
		}
	
	}
	
	private static boolean containsPS(Tile tile, float xOffset, float yOffset, int...points) {
		for(int p : points) {
			if(tile.getRect().contains(ps[p][0] + xOffset, ps[p][1] + yOffset))
				return true;
		}
		return false;
	}
	
	private static void updatePS(EntityZombie zombie) {
		float x = zombie.x;
		float y = zombie.y;
		float r = zombie.r;
		ps = new float[][] {
				// Bottom
				{x-r+1, y-r}, // 1
				{x+r-1, y-r},// 2
				
				// Right
				{x+r, y-r+1},// 3
				{x+r, y+r-1},// 4
				
				// Top
				{x+r-1, y+r},// 5
				{x-r+1, y+r},// 6
				
				// Left
				{x-r, y+r-1},// 7
				{x-r, y-r+1},// 8				
		};
		
//		for(float[] point : ps) {
//			point[0] += player.xSpeed;
//			point[1] += player.ySpeed;
//		}
	}
	
	private static void processEntityCollision(EntityZombie zombie) {
		
		// If Zombie collides with player
		if(!Geometry.onScreen(zombie, zombie.level.tileSize))
			return;
		
		if(zombie.collides(ZombieOutbreak.activePlayer, 2.0f)) {
			if(zombie.damageCoolDown <= 0) {
				ZombieOutbreak.activePlayer.damageBuffer += zombie.damage;
				zombie.damageCoolDown = zombie.maxDamageCoolDown;
			}
		}
	}
	
	private static void updateZombie(EntityZombie zombie) {
		// Check if the zombie is on the screen
		if(!Geometry.onScreen(zombie, zombie.level.tileSize))
			return;
		
		// Check if the Zombie is 'alert'
		EntityPlayer player = ZombieOutbreak.activePlayer;
		if(zombie.isInLineOfSight(player)) {
			zombie.alert = true;			
		}
		
		zombie.updateAnim = !(zombie.xSpeed == 0 && zombie.ySpeed == 0);
		
		// Move the zombie according to its X and Y Speed
		zombie.move(zombie.xSpeed, zombie.ySpeed);
		
		zombie.xSpeed = 0;
		zombie.ySpeed = 0;
		
		//Update and draw the zombies health bar
		if(zombie.hp >= zombie.maxHp - 0.1)
			zombie.hpVisible = false;
		
		else
			zombie.hpVisible = true;
	}
}
