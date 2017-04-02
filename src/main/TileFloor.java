package main;

import art.Animation;
import art.Sprite;
import art.SpriteSheetData;

public class TileFloor extends Tile {
	
	public TileFloor(float x, float y, float size) {
		this.x = x;
		this.y = y;
		this.w = size;
		this.h = size;
		this.isSolid = false;
		
		layer = 0;
		
		animation = new Animation(1,
				new Sprite(SpriteSheetData.tileSpriteSheet.sprites[0][1]));
	}
}
