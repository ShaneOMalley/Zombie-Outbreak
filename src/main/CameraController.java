package main;

import static org.lwjgl.opengl.GL11.*;

import java.awt.geom.Line2D;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import program.Program;

import art.Animation;
import art.SpriteSheetData;

public class CameraController {
	
	// Camera Variables
	private static float camX = 0, camY = 0;
	private static float camDX = 0, camDY = 0;
	
	// Death Screen Variables	
	private static boolean deathScreen;
	private static long deathScreenStartTime = -1;
	
	private static float deathScreenAlphaValue = 0.0f;
	private static final float deathScreenMaxAlphaValue = 1.0f;
	
	// Lighting Variables
	private static final float playerLightOriginDist = 5.0f;
	
	
	// Window Related Variables
	private static final int WIN_W = ZombieOutbreak.WINDOW_WIDTH, WIN_H = ZombieOutbreak.WINDOW_HEIGHT;
	
	public static void init() {
//		glTranslatef(WIN_W/2, WIN_H/2, 0);
	}
	
	public static void tick(long timePassed) {
		// 'Follow' the player
		camX = ZombieOutbreak.activePlayer.x - WIN_W/2;
		camY = ZombieOutbreak.activePlayer.y - WIN_H/2;
		
		glPushMatrix();
			glTranslatef(-camX, -camY, 0);
			ZombieOutbreak.render();
			if(Program.lighting)
				renderLight();				
		glPopMatrix();
		
		// Skip HUD Drawing if player is dead
		if(!EventManager.playerDead) {
			ZombieOutbreak.hud.update();
			ZombieOutbreak.hud.draw();
		}
		
		renderLevelNameScreen();
		renderDeathScreen(timePassed);
	}
	
	private static void renderLight() {
		if(!EventManager.playerDead) {
			glColor4f(1, 1, 1, 1);
			SpriteSheetData.torchSheet.sprites[0][0].draw(ZombieOutbreak.activePlayer.x - (float)Math.cos(Math.toRadians(ZombieOutbreak.activePlayer.rotation)) * playerLightOriginDist,
					ZombieOutbreak.activePlayer.y - (float)Math.sin(Math.toRadians(ZombieOutbreak.activePlayer.rotation)) * playerLightOriginDist,
					800, 800, ZombieOutbreak.activePlayer.rotation - 180);
		}
		else {
			glColor4f(0, 0, 0, 0.96078f);
			glBegin(GL_QUADS);
				glVertex2f(ZombieOutbreak.activePlayer.x - 640/2, ZombieOutbreak.activePlayer.y - 480/2);
				glVertex2f(ZombieOutbreak.activePlayer.x + 640/2, ZombieOutbreak.activePlayer.y - 480/2);
				glVertex2f(ZombieOutbreak.activePlayer.x + 640/2, ZombieOutbreak.activePlayer.y + 480/2);
				glVertex2f(ZombieOutbreak.activePlayer.x - 640/2, ZombieOutbreak.activePlayer.y + 480/2);				
			glEnd();
		}
	}
	
	private static void renderDeathScreen(long timePassed) {
		if(EventManager.playerDead)
			deathScreen = true;
		
		if(deathScreen & deathScreenStartTime == -1) {
			deathScreenStartTime = ZombieOutbreak.getTime();
		}
		
		if(deathScreen) {
			// Set the death screens alpha value
			deathScreenAlphaValue += (float)timePassed / 30000.0f;
			if(deathScreenAlphaValue > deathScreenMaxAlphaValue) deathScreenAlphaValue = deathScreenMaxAlphaValue;
			glColor4f(1, 1, 1, deathScreenAlphaValue);
			
			// Draw the deathScreen
			SpriteSheetData.deathScreen.sprites[0][0].draw(0, 0, 640, 480);
			
			//  Reset the alpha value
			glColor4f(1, 1, 1, 1);
		}
	}
	
	private static void renderLevelNameScreen() {
		if(ZombieOutbreak.activePlayer.level.levelNameScreenVisible)
			ZombieOutbreak.activePlayer.level.renderLevelNameScreen();
	}
	
	
	public static void reset() {
//		glTranslatef(-camX, -camY, 0);
	}
	
	public static void resetDeathScreen() {
		deathScreenStartTime = -1;
		deathScreenAlphaValue = 0.0f;
		deathScreen = false;
	}
}
