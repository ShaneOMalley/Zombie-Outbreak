package art;

public class Animation implements Drawable{

	// A public 'blank' Animation
	public static Animation blankAnim = new Animation(1, SpriteSheetData.zombieSpriteSheet.sprites[0][0]);
	
	//Animation Variables
	public Sprite[] sprites;
	public int currentSpriteIndex;
	
	private long animTime;
	private long timeLeft;
	
	public Animation(long animTime, Sprite...sprites) {
		this.sprites = sprites;
		this.animTime = animTime;
		
		timeLeft = animTime;
		currentSpriteIndex = 0;
	}
	
	public void update(long timePassed) {
		timeLeft -= timePassed;
		
		if(timeLeft <= 0) {
			timeLeft = animTime + timeLeft;
			if(currentSpriteIndex < sprites.length - 1)
				currentSpriteIndex ++;
			else
				currentSpriteIndex = 0;
		}
	}
	
	public void draw() {
		sprites[currentSpriteIndex].draw();
	}
	
	public void draw(float x, float y, float w, float h) {
		sprites[currentSpriteIndex].draw(x, y, w, h);
	}

	public void draw(float x, float y, float w, float h, float angle) {
		sprites[currentSpriteIndex].draw(x, y, w, h, angle);
	}
}
