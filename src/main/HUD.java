package main;

import static org.lwjgl.opengl.GL11.*;

import java.awt.List;
import java.util.ArrayList;

import utility.Geometry;
import art.Animation;
import art.Drawable;
import art.SpriteSheetData;

public class HUD implements Drawable {
	
	// HUD Variables
	private float hudItemGreyScale = 1f;
	private int WeaponBackDisplayList;
	private int playerHPDisplaylist1, playerHPDisplaylist2;
	
	public void initHUD() {
		// Grey area behind weapon in HUD Displaylist init
		WeaponBackDisplayList = glGenLists(1);
		
		glNewList(WeaponBackDisplayList, GL_COMPILE);
			glColor4f(hudItemGreyScale, hudItemGreyScale, hudItemGreyScale, 0.5f);
			glBegin(GL_QUADS);
				glVertex2f(8, 8);
				glVertex2f(180, 8);
				glVertex2f(180, 72);
				glVertex2f(8, 72);	
			glEnd();
			glColor4f(1, 1, 1, 1);
		glEndList();
		
		// Grey area behind HP bar
		playerHPDisplaylist1 = glGenLists(1);
		
		glNewList(playerHPDisplaylist1, GL_COMPILE);
			glColor4f(hudItemGreyScale, hudItemGreyScale, hudItemGreyScale, 0.5f);
			glBegin(GL_QUADS);
				glVertex2f(8, 78);
				glVertex2f(180, 78);
				glVertex2f(180, 108);
				glVertex2f(8, 108);	
			glEnd();
			glColor4f(1, 1, 1, 1);
		glEndList();
		
		// HP bar
		playerHPDisplaylist2 = glGenLists(1);
		
		glNewList(playerHPDisplaylist2, GL_COMPILE);
			glColor4f(0, 1, 0, 1);
			glBegin(GL_QUADS);
				glVertex2f(0, 80);
				glVertex2f(168, 80);
				glVertex2f(168, 106);
				glVertex2f(0, 106);	
			glEnd();
			glColor4f(1, 1, 1, 1);
		glEndList();
	
	}
	
	public void update() {
		
	}
	
	public void draw() {
		// Draws the player's active Weapon
		if(ZombieOutbreak.activePlayer.currentWeapon != null) {
			glCallList(WeaponBackDisplayList);
			ZombieOutbreak.activePlayer.currentWeapon.animation.draw(10, 10, 168, 60);
		}
		
		// Draws the player's health bar
		glCallList(playerHPDisplaylist1);
		
		float scaleFactor = ZombieOutbreak.activePlayer.hp / ZombieOutbreak.activePlayer.maxHp;
		glPushMatrix();
		glTranslatef(10, 0, 0);
		glScalef(scaleFactor, 1, 1);
		glCallList(playerHPDisplaylist2);
		glPopMatrix();
		
	}
	
	public void draw(float x, float y, float w, float h) {
		draw();
	}
	
	public void draw(float x, float y, float w, float h, float angle) {
		draw();
	}

}
