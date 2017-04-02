package menu;

import static art.SpriteSheetData.buttonSpriteSheet;
import art.Animation;

public class MenuItemButtonBackToMain extends MenuItemButton {

	public MenuItemButtonBackToMain(float x, float y) {
		super(x, y, MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		
		textures = new Animation[]{
				new Animation(1, buttonSpriteSheet.sprites[5][0]),
				new Animation(1, buttonSpriteSheet.sprites[5][1]),
				new Animation(1, buttonSpriteSheet.sprites[5][2])
		};
	}
	
	public void onPressed() {
		MainMenu.currentMenuScreenIndex = 0;
	}

}
