package menu;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import art.Drawable;
import art.SpriteSheetData;

public class MainMenu {
	
	public static final float BUTTON_WIDTH = 92, BUTTON_HEIGHT = 32;
	
	//Main menu Variables	
	public static ArrayList<MenuScreen> menuScreens;
	public static int currentMenuScreenIndex;
	
	public MainMenu() {
		menuScreens = new ArrayList<MenuScreen>();
		menuScreens.add(0, new MenuScreen(new MenuBackGround2D(SpriteSheetData.menuBackGround.sprites[0][0]),
				new MenuItemButtonPlay(10, 30 + BUTTON_HEIGHT*2),
				new MenuItemButtonControls(10, 20 + BUTTON_HEIGHT),
				new MenuItemButtonExit(10, 10)								
				));
		
		menuScreens.add(1, new MenuScreen(new MenuBackGround2D(SpriteSheetData.menuBackGround.sprites[0][0]),
				new MenuItemButtonBackToMain(10, 20 + BUTTON_HEIGHT),
				new MenuItemButtonWASDControl(10, 10),
				new MenuItemButtonMouseControl(20 + BUTTON_WIDTH, 10)
				));
		
		currentMenuScreenIndex = 0;
	}
	
	public void tick(long timePassed) {
		menuScreens.get(currentMenuScreenIndex).tick(timePassed);
		menuScreens.get(currentMenuScreenIndex).draw();
		
		if(Display.isCloseRequested()) {
			MainMenuInit.menuState = MainMenuInit.EXITING;
		}
	}
}
