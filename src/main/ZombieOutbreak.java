package main;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.util.ArrayList;

import menu.MainMenuInit;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import art.Animation;
import art.SpriteSheetData;
import program.Program;

public class ZombieOutbreak {
	
	// Window Varibles
	public static final int WINDOW_WIDTH = 640, WINDOW_HEIGHT = 480;
	public static final String WINDOW_TITLE = "ZombieOutbreak";
	
	// Program Variables
	public static String path;
	public static boolean running = true;
	public static final String VERSION = "1.0"; 
	
	// Time Variables
	private static long nowTime;
	private static long timePassed;
	public static float delta;
	
	// Level Variables
	public static EntityPlayer activePlayer = null;
	public static ArrayList<Level> levels;
	public static int currentLevelIndex;
	
	// HUD
	public static HUD hud;
	
	public static void main(String[] args) {
		Program.setupDisplay();
		Program.setupOpenGL();
		Program.setupPath();
		Program.setupSpriteSheets();
		Program.setupHUD();
		setupLevels();
		setupCamera();
		Program.resetGameVariables();
		
		timePassed = 0;
		delta = 0;
		for(int ticks = 0; running; ticks++) {
			startLoop();
			
			tick(ticks, timePassed);
			
			updateDisplay(60);
			
			endLoop(ticks);
		}
		MainMenuInit.main(args);
	}
	
	/*
	 * Start Methods
	 */	
	private static void setupLevels() {
		levels = new ArrayList<Level>();
		try {
			levels.add(0, new Level(path + "/levels/level_1.png", 32, new Animation(1, SpriteSheetData.levelNameScreen1.sprites[0][0])));
			levels.add(1, new Level(path + "/levels/level_2.png", 32, new Animation(1, SpriteSheetData.levelNameScreen2.sprites[0][0])));
			levels.add(2, new Level(path + "/levels/level_3.png", 32, new Animation(1, SpriteSheetData.levelNameScreen3.sprites[0][0])));
			levels.add(3, new Level(path + "/levels/level_4.png", 32, new Animation(1, SpriteSheetData.levelNameScreen4.sprites[0][0])));
			levels.add(4, new Level(path + "/levels/level_5.png", 32, new Animation(1, SpriteSheetData.levelNameScreen5.sprites[0][0])));
			levels.add(5, new Level(path + "/levels/level_6.png", 32, new Animation(1, SpriteSheetData.levelNameScreen6.sprites[0][0])));
			levels.add(6, new Level(path + "/levels/level_7.png", 32, new Animation(1, SpriteSheetData.levelNameScreen7.sprites[0][0])));
			levels.add(7, new Level(path + "/levels/level_complete.png", 32, new Animation(1, SpriteSheetData.levelNameScreenComplete.sprites[0][0])));			
		} catch (IOException ex) {
			Program.kill(1, "Error loading level");
		}
		EventManager.changeLevel(0);
	}
	
	private static void setupCamera() {
		CameraController.init();
	}
	
	/*
	 * Main Loop Methods
	 */
	private static void startLoop() {
		glClear(GL_COLOR_BUFFER_BIT);
		nowTime = getTime();
	}
	
	private static void tick(int ticks, long timePassed) {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			running = false;
		
		if(Display.isCloseRequested()) {
			Program.kill(0);
		}
		
		levels.get(currentLevelIndex).tick(timePassed);
		CameraController.tick(timePassed);
	}
	
	public static void render() {
		levels.get(currentLevelIndex).render();
	}
	
	private static void updateDisplay(int fps) {
		Display.update();
		Display.sync(fps);
	}
	
	private static void endLoop(int ticks) {
		timePassed = getTime() - nowTime;
		delta = timePassed / 16.0f;
		int fps = (int)(16.0f / timePassed* 60);
	}
	/*
	 * Misc. Methods
	 */
	
	public static long getTime() {
		return System.nanoTime() / 1000000;
	}
}
