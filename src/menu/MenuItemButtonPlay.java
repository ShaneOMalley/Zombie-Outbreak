package menu;

import static art.SpriteSheetData.buttonSpriteSheet;
import art.Animation;

public class MenuItemButtonPlay extends MenuItemButton {
	
	public MenuItemButtonPlay(float x, float y) {
		super(x, y, MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		
		textures = new Animation[]{
				new Animation(1, buttonSpriteSheet.sprites[0][0]),
				new Animation(1, buttonSpriteSheet.sprites[0][1]),
				new Animation(1, buttonSpriteSheet.sprites[0][2])
		};
	}
	
	public void onPressed() {
		MainMenuInit.menuState = MainMenuInit.GAME_SCREEN;
	}
}
