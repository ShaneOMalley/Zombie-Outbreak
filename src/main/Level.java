package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import utility.Geometry;
import art.Animation;
import art.Sprite;
import art.SpriteSheetData;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBTextureRectangle.*;

public class Level {
	
	// Level Variables
	public Tile[][] tiles;
	private int levelw, levelh;
	public float tileSize;
	public LinkedList<Entity> entities;
	public LinkedList<Entity> entityCreateBuffer;
	public LinkedList<Entity> entityRemoveBuffer;	
	private String levelDataLocation;
	private EntityPlayer player;
	
	private BufferedImage levelData;
	
	// Rendering Variables
	private int tilesDisplayList;
	
	// LevelNameScreenVariables
	public Animation levelNameScreen;
	public boolean levelNameScreenVisible;
	public boolean levelNameScreenActivated = false;
	public static final long levelNameScreenTime = 5000;
	public long levelNameScreenTimeLeft;
	
	public Level(String levelDataLocation, float tileSize, Animation levelNameScreen) throws IOException {
		// Creates the buffered images that hold the Tile and Entity data
		levelData = ImageIO.read(new File(levelDataLocation));
				
		//Initializes tile data location and tileSize
		this.levelDataLocation = levelDataLocation;
		this.tileSize = tileSize;
		
		// Initialize the levelNameScreen
		this.levelNameScreen = levelNameScreen;
	}
	
	/*
	 * When the level starts
	 */
	public void levelStart() {		
		tiles = new Tile[levelData.getWidth()][levelData.getHeight()]; //initializes the array of tiles
		
		//initializes the ArrayList of Entities
		entities = new LinkedList<Entity>();
		
		//Initializes the Arrays that handle the creation and destruction of entities
		entityCreateBuffer = new LinkedList<Entity>();
		entityRemoveBuffer = new LinkedList<Entity>();
		
		// Parses the tileData image into Tile array
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				float x = i;
				float y = tiles[i].length - j - 1;
				
				int color = 0xFFFFFF + levelData.getRGB(i, j) +1;
				
				//Wall
				if(color == 0x000000) {
					tiles[i][j] = new TileWall(x * tileSize, y * tileSize, tileSize);
				}
				
				else if(color == 0x1E1E1E) {
					tiles[i][j] = null;
				}
				
				else {
					tiles[i][j] = new TileFloor(x * tileSize, y * tileSize, tileSize);
				}
				
				//Player spawn
				if(color == 0x00FF00) {
					player = new EntityPlayer(x * tileSize + tileSize/2, y * tileSize + tileSize/2, this);
					ZombieOutbreak.activePlayer = player;
					entities.add(player);
				}
				
				//Zombie spawn				
				if(color == 0xFF0000) {
					entities.add(new EntityZombie(x * tileSize +tileSize/2, y * tileSize +tileSize/2, this));
				}
				
				//Weapon Spawn
				//Shotgun
				if(color == 0xFF6A00)
					entities.add(new EntityWeapon(x * tileSize +tileSize/2, y * tileSize +tileSize/2, EnumWeapon.SHOTGUN, this));
				//SMG
				if(color == 0x7F3300)
					entities.add(new EntityWeapon(x * tileSize +tileSize/2, y * tileSize +tileSize/2, EnumWeapon.SMG, this));
				//Assault
				if(color == 0x7F6A00)
					entities.add(new EntityWeapon(x * tileSize +tileSize/2, y * tileSize +tileSize/2, EnumWeapon.ASSAULT, this));
				
				//Level Change				
				if(color == 0xFFFF00) {
					entities.add(new EntityLevelChange(x * tileSize + tileSize/2, y * tileSize + tileSize/2, tileSize));
				}
			}
		}
		
		this.levelw = tiles.length;
		this.levelh = tiles[0].length;
		
		// Initialize the displayList for drawing the level
		tilesDisplayList = glGenLists(1);
		
		glNewList(tilesDisplayList, GL_COMPILE);
			
			glBegin(GL_QUADS);
			for(int i = 0; i < tiles.length; i++) {
				for(int j = 0; j < tiles[0].length; j++) {
					
					if(tiles[i][j] == null)
						continue;
					Sprite spr = tiles[i][j].animation.sprites[tiles[i][j].animation.currentSpriteIndex];
					glBindTexture(GL_TEXTURE_RECTANGLE_ARB, spr.spriteSheet.texture);
					
					glTexCoord2f(spr.x, spr.y);
					glVertex2f(tiles[i][j].x, tiles[i][j].y);
					glTexCoord2f(spr.x + spr.w, spr.y);
					glVertex2f(tiles[i][j].x + tiles[i][j].w, tiles[i][j].y);
					glTexCoord2f(spr.x + spr.w, spr.y + spr.h);
					glVertex2f(tiles[i][j].x + tiles[i][j].w, tiles[i][j].y + tiles[i][j].h);
					glTexCoord2f(spr.x, spr.y + spr.h);
					glVertex2f(tiles[i][j].x, tiles[i][j].y + tiles[i][j].h);
				}
			}
			glEnd();
			
		glEndList();
		
	}
	
	public void updateAndRender(long timePassed) {		
		tick(timePassed);
		render();
	}
	
	public void render() {
		if(!isCurrentLevel())
			return;
		
		//Render Floor Tiles
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				if(tiles[i][j] instanceof TileFloor && 
						Geometry.onScreen(tiles[i][j], tileSize+1)) tiles[i][j].draw();
			}
		}
		
		//Render LevelChange
		for(Entity entity : entities) {
			if(entity instanceof EntityLevelChange)
			if(Geometry.onScreen(entity, tileSize))
			entity.draw();
		}
		
		// Render Blood/Floor decals
		for(Entity entity : entities) {
			if(entity instanceof EntityStatic)
			if(Geometry.onScreen(entity, tileSize))
			entity.draw();
		}
		
		// Render Weapons (on ground)
		for(Entity entity : entities) {
			if(entity instanceof EntityWeapon)
			if(Geometry.onScreen(entity, tileSize))
			entity.draw();
		}
		// Render Bullets
		for(Entity entity : entities) {
			if(entity instanceof EntityBullet)
			if(Geometry.onScreen(entity, tileSize))
			entity.draw();
		}
		// Render Zombie
		for(Entity entity : entities) {
			if(entity instanceof EntityZombie)
			if(Geometry.onScreen(entity, tileSize))
			entity.draw();
		}
		// Render Player
		for(Entity entity : entities) {
			if(entity instanceof EntityPlayer)
			if(Geometry.onScreen(entity, tileSize))
			entity.draw();
		}
		
		//Render Wall Tiles
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				if(tiles[i][j] instanceof TileWall && 
					Geometry.onScreen(tiles[i][j], tileSize+1)) tiles[i][j].draw();
			}
		}
	}
	
	public void tick(long timePassed) {
		if(!isCurrentLevel())
			return;
		
		// 'Tick' tiles
		for(int i = 0; i < tiles.length; i++)
			for(int j = 0; j < tiles[i].length; j++) {
				
				if(tiles[i][j] == null)
					continue;
				
				if(Geometry.onScreen(tiles[i][j], tileSize) && tiles[i][j] != null);
				tiles[i][j].tick(timePassed);
			}
		
		// 'Tick' entities
		for(Entity entity : entities) {
			if(Geometry.onScreen(entity, tileSize));
			entity.tick(timePassed);
		}
		
		// Add new entities;
		entities.addAll(entityCreateBuffer);
		entityCreateBuffer.clear();
		
		entities.removeAll(entityRemoveBuffer);
		entityRemoveBuffer.clear();
		
		
		// Checks if levelNameScreen needs to be activated (if this level has just been chosen)
		if(isCurrentLevel())
			if(!this.levelNameScreenActivated) this.activateLevelNameScreen();
		
		// CountDown levelNameScreen if necessary
		if(levelNameScreenVisible) {
			levelNameScreenTimeLeft -= timePassed;
			
			if(levelNameScreenTimeLeft <= 0)
				levelNameScreenVisible = false;
		}
	}
	
	public void renderLevelNameScreen() {
		// Render LevelNameScreen
		if(levelNameScreenVisible) {
			
			// Determines the alpha value of the levelNameScreen to be drawn
			float alpha = 0.0f;
			
			if(levelNameScreenTimeLeft >= levelNameScreenTime * 0.24f)
				alpha = 1;
			
			else
				alpha = (float)levelNameScreenTimeLeft / ((float)levelNameScreenTime * 0.24f);
			
			// Renders levelScreen
			glColor4f(1.0f, 1.0f, 1.0f, alpha);
			levelNameScreen.draw(0, 0, 640, 480);
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}
	
	
	public void activateLevelNameScreen() {
		levelNameScreenVisible = true;
		levelNameScreenActivated = true;
		levelNameScreenTimeLeft = levelNameScreenTime;
	}
	
	/*
	 * Returns true if this level is the current active level
	 */
	public boolean isCurrentLevel() {
		return ZombieOutbreak.levels.get(ZombieOutbreak.currentLevelIndex) == this;
	}
}
