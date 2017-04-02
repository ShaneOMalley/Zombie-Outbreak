package menu;

import static art.SpriteSheetData.buttonSpriteSheet;
import art.Animation;

public class MenuItemButtonControls extends MenuItemButton {

	public MenuItemButtonControls(float x, float y) {
		super(x, y, MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		
		textures = new Animation[]{
				new Animation(1, buttonSpriteSheet.sprites[2][0]),
				new Animation(1, buttonSpriteSheet.sprites[2][1]),
				new Animation(1, buttonSpriteSheet.sprites[2][2])
		};
	}
	
	public void onPressed() {
		MainMenu.currentMenuScreenIndex = 1;
	}
}
