package art;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utility.TextureLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBTextureRectangle.*;

public class SpriteSheet {
	
	//SpriteSheet Variables
	private BufferedImage image;
	private int cellWidth, cellHeight;
	private String textureLocation;
	public int texture;
	public Sprite[][] sprites;
	
	public SpriteSheet(String location, int cellWidth, int cellHeight) {
		textureLocation = location;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		setupBufferedImage();
		setupTexture();
		setupSprites();
	}
	
	private void setupTexture() {
		try {
			texture = TextureLoader.loadTexture(textureLocation, GL_TEXTURE_RECTANGLE_ARB, GL_NEAREST);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void setupBufferedImage() {
		image = null;
		try {
			image = ImageIO.read(new File(textureLocation));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void setupSprites() {
		if(image.getWidth() % cellWidth == 0 && image.getHeight() % cellHeight == 0)
			sprites = new Sprite[image.getWidth() / cellWidth][image.getHeight() / cellHeight];
		else {
			System.err.println("Invalid cell dimensions");
		}
		
		// Populate sprites[][] with Sprites
		for(int i = 0; i < sprites.length; i++)
			for(int j = 0; j < sprites[0].length; j++) {
				sprites[i][sprites[0].length - 1 - j] = new Sprite(this, i * cellWidth, j * cellHeight, cellWidth, cellHeight);
			}
	}
}
