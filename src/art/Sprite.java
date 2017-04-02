package art;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBTextureRectangle.*;
import main.ZombieOutbreak;

public class Sprite implements Drawable{
	
	// Sprite Variables
	public SpriteSheet spriteSheet;
	public float x, y;
	public float w, h;
	private int displayList;
	
	public Sprite(SpriteSheet spriteSheet, float x, float y, float w, float h) {
		this.spriteSheet = spriteSheet;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		setupDisplayList();
	}
	
	public Sprite(Sprite sprite) {
		this.spriteSheet = sprite.spriteSheet;
		
		this.x = sprite.x;
		this.y = sprite.y;
		this.w = sprite.w;
		this.h = sprite.h;
		
		setupDisplayList();
	}
	
	private void setupDisplayList() {
		displayList = glGenLists(1);
		
		glNewList(displayList, GL_COMPILE);
			
			glEnable(GL_TEXTURE_RECTANGLE_ARB);
			glBindTexture(GL_TEXTURE_RECTANGLE_ARB, spriteSheet.texture);
			
			glBegin(GL_QUADS);
				
				glTexCoord2f(x, y);
				glVertex2f(0, 0);
				glTexCoord2f(x + w, y);
				glVertex2f(1, 0);
				glTexCoord2f(x + w, y + h);
				glVertex2f(1, 1);
				glTexCoord2f(x, y + h);
				glVertex2f(0, 1);
				
			glEnd();
			
			glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
			glDisable(GL_TEXTURE_RECTANGLE_ARB);
			
		glEndList();
	}
	
	public void draw(float x, float y, float w, float h) {
		glPushMatrix();
			
			glTranslatef(x, y, 0);
			glScalef(w, h, 0);
			
			draw();
			
		glPopMatrix();
	}
	
	public void draw(float x, float y, float w, float h, float angle) {
		glPushMatrix();
			
			glTranslatef(x, y, 0);
			glRotatef(angle, 0, 0, 1);
			glTranslatef(-w/2, -h/2, 0);
			glScalef(w, h, 0);
			
			draw();
			
		glPopMatrix();
	}

	public void draw() {
		glCallList(displayList);
	}
}
