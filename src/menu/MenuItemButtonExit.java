package menu;

import static art.SpriteSheetData.buttonSpriteSheet;
import art.Animation;

public class MenuItemButtonExit extends MenuItemButton {

	public MenuItemButtonExit(float x, float y) {
		super(x, y, MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		
		textures = new Animation[]{
				new Animation(1, buttonSpriteSheet.sprites[1][0]),
				new Animation(1, buttonSpriteSheet.sprites[1][1]),
				new Animation(1, buttonSpriteSheet.sprites[1][2])
		};
	}

	public void onPressed() {
		MainMenuInit.menuState = MainMenuInit.EXITING;
	}
}
