package main;

import java.io.IOException;

import program.Program;
import art.Animation;
import art.SpriteSheetData;

public class EventManager {
	
	public static boolean playerDead = false; 
	
	public static void playerDead() {
		playerDead = true;
	}
	
	public static void playerPickUpWeapon(EnumWeapon weapon) {
		// TODO Finish Method		
	}
	
	public static void changeLevel(int levelIndex) {		
		ZombieOutbreak.currentLevelIndex = levelIndex;
		ZombieOutbreak.levels.get(ZombieOutbreak.currentLevelIndex).levelStart();
	}
}
