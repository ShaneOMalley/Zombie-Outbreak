package menu;

import art.Animation;
import art.Sprite;

public class MenuBackGround2D extends MenuBackGround {
	
	//MenuBackGround2D Variables
	private Animation animation;
	
	public MenuBackGround2D(Sprite sprite) {
		animation = new Animation(1, sprite);
	}

	public MenuBackGround2D(Animation animation) {
		this.animation = animation;
	}
	
	public void tick(long timePassed) {
		animation.update(timePassed);
	}
	
	public void draw() {
		animation.draw(0, 0, MainMenuInit.WINDOW_WIDTH, MainMenuInit.WINDOW_HEIGHT);
	}

	public void draw(float x, float y, float w, float h) {
	}
	public void draw(float x, float y, float w, float h, float angle) {
	}
}
