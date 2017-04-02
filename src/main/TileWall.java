package main;

import art.Animation;
import art.Sprite;
import art.SpriteSheetData;

public class TileWall extends Tile {
	
	public TileWall(float x, float y, float size) {
		this.x = x;
		this.y = y;
		this.w = size;
		this.h = size;
		this.isSolid = true;
		
		layer = 120;
		
		animation = new Animation(1, 
				new Sprite(SpriteSheetData.tileSpriteSheet.sprites[4][1]));
	}
}
