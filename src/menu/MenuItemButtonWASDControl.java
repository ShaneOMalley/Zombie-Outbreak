package menu;

import static art.SpriteSheetData.buttonSpriteSheet;
import program.Program;
import art.Animation;

public class MenuItemButtonWASDControl extends MenuItemButton {
	
	public MenuItemButtonWASDControl(float x, float y) {
		super(x, y, MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		
		textures = new Animation[]{
				new Animation(1, buttonSpriteSheet.sprites[3][0]),
				new Animation(1, buttonSpriteSheet.sprites[3][1]),
				new Animation(1, buttonSpriteSheet.sprites[3][2])
		};
	}
	
	public void onPressed() {
		Program.controlMethod = Program.WASD_MOVEMENT;
	}
	
	//Update the button and check if it is pressed
		public void tick(long timePassed) {		
			if(isEntered())
				currentTextureIndex = 1;
			else
				currentTextureIndex = 0;
			
			// if Mouse movement is selected, keep the button highlighted
			if(Program.controlMethod == Program.WASD_MOVEMENT)
				currentTextureIndex = 2;
						
						
			textures[currentTextureIndex].update(timePassed);
		}
}
