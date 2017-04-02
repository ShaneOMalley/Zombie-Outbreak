package main;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import art.Animation;
import art.SpriteSheetData;
import program.Program;
import utility.Geometry;

public class ControllerPlayer {
	
	private static Random random = new Random();
	// Collision Variables
	private static final int MAX_ITERATIONS = 3;	
	
	// Variables for Collision Detection
	private static float xProjectedMove = 0, yProjectedMove = 0;
	private static float[][] ps;
	
	public static void tick(EntityPlayer player) {
		updatePPS(player);
		if(!EventManager.playerDead && !player.level.levelNameScreenVisible) {
			processInput(player);
			rotatePlayerTowardCursor(player);
		}
		slowDownPlayerThroughZombies(player);
		processCollisions(player);
		interact(player);
		shoot(player);
		updatePlayer(player);
	}
	
	private static void rotatePlayerTowardCursor(EntityPlayer player) {
		player.rotation = Geometry.getAngle(player.X_POS_ON_SCREEN, player.Y_POS_ON_SCREEN, Mouse.getX(), Mouse.getY());
	}
	
	private static void playerStepToward(EntityPlayer player, float angle, float speed) {
		float dx, dy;
		
		dx = speed * (float)Math.cos(Math.toRadians(angle));
		dy = speed * (float)Math.sin(Math.toRadians(angle));
		
		player.xSpeed = dx;
		player.ySpeed = dy;
	}
	
	private static void processInput(EntityPlayer player) {
		if(player.level.levelNameScreenVisible)
			return;
		
		// Mouse Movement (Click-and-drag)
		if(Program.controlMethod == Program.MOUSE_MOVEMENT) {
			if(Mouse.isButtonDown(0))
				playerStepToward(player, player.rotation -180, player.maxSpeed);
		}
		
		// WASD Movement
		if(Program.controlMethod == Program.WASD_MOVEMENT) {
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				player.ySpeed += player.maxSpeed;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				player.ySpeed -= player.maxSpeed;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				player.xSpeed -= player.maxSpeed;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				player.xSpeed += player.maxSpeed;
			}
		}
		
		// Debug levelNameScreen display
		if(Keyboard.isKeyDown(Keyboard.KEY_7)) {
			player.level.activateLevelNameScreen();
		}
		
		// Debug changeLevel
		while(Keyboard.next()) {
			if(Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_L)
				EventManager.changeLevel(1);
			}
		}
	
	private static void slowDownPlayerThroughZombies(EntityPlayer player) {
		LinkedList<Entity> entities = player.level.entities;
		
		int collidingZombies = 0;
		int maxCollidingZombies = 6;
		float slowFactor = 0.0f;
		
		for(Entity entity : entities) {
			//Skip if the entity is not a zombie
			if(!(entity instanceof EntityZombie))
				continue;
			
			//Skip if collidingZombies has reached the limit
			if(collidingZombies >= 6)
				continue;
			
			float dist = Geometry.getDistance(player, entity);
			if(dist <= player.r + entity.r + 4.0f)
				collidingZombies++;
		}
		
		slowFactor = (float)collidingZombies / (float)maxCollidingZombies;
		
		if(slowFactor > 0.8f) slowFactor = 0.8f;
		
		player.xSpeed *= (1 - slowFactor);
		player.ySpeed *= (1 - slowFactor);		
	}
	
	private static void processCollisions(EntityPlayer player) {
		boolean xContact = true;
		boolean yContact = true;		
		
		for(int iterations = 0; iterations < MAX_ITERATIONS && (xContact || yContact); iterations++) {
			
			xContact = false;
			yContact = false;
			for(int i = 0; i < player.level.tiles.length; i++) {
				for(int j = 0; j < player.level.tiles[i].length; j++) {
					
					if(player.level.tiles[i][j] == null)
						continue;
					
					if(!player.level.tiles[i][j].isSolid)
						continue;
					
					if(xContact && yContact)
						continue;
					
					Tile tile = player.level.tiles[i][j];
		
					//dir: 0 = Bottom, 1 = Right, 2 = Top, 3 = Left
					for(int dir = 0; dir < 4 && !(xContact && yContact); dir++) {
						
						if(dir == 0 || dir == 2) yProjectedMove = player.ySpeed;
						else yProjectedMove = 0;
						
						if(dir == 1 || dir == 3) xProjectedMove = player.xSpeed;
						else xProjectedMove = 0;
						
						while(containsPPS(tile, xProjectedMove, yProjectedMove, dir*2, dir*2+1)) {
							
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
							
							updatePPS(player);
						}
						
						if((dir == 1 || dir == 3) && xContact) {
							player.xSpeed = xProjectedMove;
						}
						if((dir == 0 || dir == 2) && yContact) {
							player.ySpeed = yProjectedMove;
						}
						
						if(yContact) {
							player.y += player.ySpeed;
							player.ySpeed = 0.0f;
						}
						
						if(xContact) {
							player.x += player.xSpeed;
							player.xSpeed = 0.0f;
						}
					}
				}
			}
		}
	}
	
	private static void interact(EntityPlayer player) {
		for(Entity ent : player.level.entities) {
			if(ent instanceof EntityWeapon) {
				if(player.collides(ent)) {
					player.currentWeapon = ((EntityWeapon) ent).weaponType;
					ent.level.entityRemoveBuffer.add(ent);
				}
			}
		}
	}
	
	private static void shoot(EntityPlayer player) {
		if(player.currentWeapon == null) return;
		
		boolean shooting = false;
		
		if(Program.controlMethod == Program.WASD_MOVEMENT && Mouse.isButtonDown(0)) shooting = true;
		if(Program.controlMethod == Program.MOUSE_MOVEMENT && Mouse.isButtonDown(1)) shooting = true;
		
		if(shooting && player.shootCoolDown < 1) {
			for(int i = 0; i < player.currentWeapon.shots; i++) {
				float direction = player.rotation + (random.nextFloat() * player.currentWeapon.spread - player.currentWeapon.spread/2);
				
				player.level.entityCreateBuffer.add(
						new EntityBullet(player.x, player.y, direction, 0xFFFF00, player, player.currentWeapon.damage));
				
				player.shootCoolDown = player.currentWeapon.rof;
			}
		}
	}
	
	private static boolean containsPPS(Tile tile, float xOffset, float yOffset, int...points) {
		for(int p : points) {
			if(tile.getRect().contains(ps[p][0] + xOffset, ps[p][1] + yOffset))
				return true;
		}
		return false;
	}
	
	private static void updatePPS(EntityPlayer player) {
		float x = player.x;
		float y = player.y;
		float r = player.r;
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
	
	private static void updatePlayer(EntityPlayer player) {
		//Update the animation
		player.updateAnim = (player.xSpeed != 0 || player.ySpeed != 0);
		if(!player.updateAnim) player.animation.currentSpriteIndex = 0;
				
		// Move the Player
		player.move(player.xSpeed, player.ySpeed);
		
		player.xSpeed = 0;
		player.ySpeed = 0;
	}
}
