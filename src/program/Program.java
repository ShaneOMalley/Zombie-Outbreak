package program;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

import main.CameraController;
import main.EventManager;
import main.HUD;
import main.ZombieOutbreak;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import art.SpriteSheetData;

public class Program {
	
	// Window Variables
	public static final int WINDOW_WIDTH = 640, WINDOW_HEIGHT = 480;
	public static final String WINDOW_TITLE = "Zombie Outbreak";
	public static final String VERSION = "1.0";
	
	// Setup Variables
	public static boolean openglSetup = false;
	public static boolean displaySetup = false;
	public static boolean pathSetup = false;
	public static boolean spriteSheetSetup = false;
	public static boolean hudSetup = false;
	
	// Controls Variables
	public static final int WASD_MOVEMENT = 100;
	public static final int MOUSE_MOVEMENT = 101;
	public static int controlMethod = WASD_MOVEMENT;
	
	//Lighting Variables
	public static boolean lighting = true;
	
	/*
	 * Initialization Methods 
	 */
	public static void resetGameVariables() {
		ZombieOutbreak.running = true;
		EventManager.playerDead = false;
		ZombieOutbreak.activePlayer.hp = ZombieOutbreak.activePlayer.maxHp;
		CameraController.resetDeathScreen();
	}
	
	public static void setupOpenGL() {
		if(!openglSetup) {
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT, 1, -1);
			glMatrixMode(GL_MODELVIEW);

			glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			
			glEnable(GL_CULL_FACE);
			glCullFace(GL_BACK);
			
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			glEnable(GL_TEXTURE_RECTANGLE_ARB);
			
			openglSetup = true;
		}
	}
	
	public static void setupDisplay() {
		if(!displaySetup) {
			try {
				Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
				Display.setTitle(WINDOW_TITLE);
				Display.create();
			} catch(LWJGLException ex) {
				System.err.println("Error creating display");
				System.exit(1);
			}
			displaySetup = true;
		}
	}
	
	public static void setupPath() {
		if(!pathSetup) {
			CodeSource codeSource = ZombieOutbreak.class.getProtectionDomain().getCodeSource();
			File jarFile = null;
			
			try {
				jarFile = new File(codeSource.getLocation().toURI().getPath());
			} catch (URISyntaxException ex) {
			}
			
			String jarDir = jarFile.getParentFile().getPath();
			ZombieOutbreak.path = jarDir;
			
			pathSetup = true;
		}
	}
	
	public static void setupSpriteSheets() {
		if(!spriteSheetSetup) {
			SpriteSheetData.setupSpriteSheets();
			
			spriteSheetSetup = true;
		}
	}
	
	public static void setupHUD() {
		if(!hudSetup) {
			ZombieOutbreak.hud = new HUD();
			ZombieOutbreak.hud.initHUD();
		}
	}
	
	/*
	 * Misc. Methods
	 */
	public static void updateDisplay(int fps) {
		Display.update();
		Display.sync(fps);
	}
	
	public static void kill(int status) {
		Display.destroy();
		System.exit(status);
	}
	
	public static void kill(int status, String errorMessage) {
		System.err.println(errorMessage);
		Display.destroy();
		System.exit(status);
	}	
}
