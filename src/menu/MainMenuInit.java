package menu;

import org.lwjgl.opengl.Display;

import program.Program;

import static org.lwjgl.opengl.GL11.*;

public class MainMenuInit {
	
	//Menu States
	public static final int MAIN_MENU = 100;
	public static final int GAME_SCREEN = 101;
	public static final int EXITING = 102;
	
	//Window Variables
	public static final int WINDOW_WIDTH = 640, WINDOW_HEIGHT = 480;
	public static final String WINDOW_TITLE = "Zombie Outbreak";
	
	//Menu Variables
	public static int menuState = MAIN_MENU;
	public static MainMenu mainMenu;
	
	//Time Variables
	private static long nowTime;
	private static long timePassed;
	
	public static void main(String[] args) {		
		menuState = MAIN_MENU;
		
		Program.setupPath();
		Program.setupDisplay();
		Program.setupOpenGL();
		Program.setupSpriteSheets();
		setupMainMenu();
		
		timePassed = 0;
		
		// Start the main Menu Loop
		while(menuState == MAIN_MENU) {
			startLoop();
			
			mainMenu.tick(timePassed);
			
			endLoop();
		}
		
		// Check which state the program is in
		if(menuState == GAME_SCREEN) {
			main.ZombieOutbreak.main(args);
		}
		
		if(menuState == EXITING) {
			Program.kill(0);
		}
	}
	
	/*
	 * Pre-Loop Methods
	 */
	private static void setupMainMenu() {
		mainMenu = new MainMenu();
	}	
	
	/*
	 * Loop Methods
	 */
	private static void startLoop() {
		glClear(GL_COLOR_BUFFER_BIT);
		nowTime = main.ZombieOutbreak.getTime();
	}
	
	private static void endLoop() {
		timePassed = nowTime - main.ZombieOutbreak.getTime();
		
		Program.updateDisplay(60);
	}
}
