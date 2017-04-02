package utility;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import org.lwjgl.util.vector.Vector2f;

import main.Entity;
import main.EntityPlayer;
import main.Tile;
import main.ZombieOutbreak;

public class Geometry {
	
	//Returns true if the point is inside the rectangular area
	public static boolean contains(float px, float py, float x1, float y1, float w, float h) {
		return(px >= x1 && px <= x1 + w)
				&&(py >= y1 && py <= y1 + h);
	}
	
	public static boolean contains(Rectangle rect, float x, float y) {
		
		return contains(x, y, rect.x, rect.y, rect.width, rect.height);
	}
	
	//Returns true if line1 intersects line2
	public static boolean intersects(Line2D line1, Line2D line2) {
		return line1.intersectsLine(line2);
	}
	
	//Returns true if line1 intersects line2
	public static boolean intersects(Line2D line1, float x1, float y1, float x2, float y2) {
		return line1.intersectsLine(new Line2D.Float(x1, y1, x2, y2));
	}
	
	//Returns true if two rectangular shapes intersect
	public static boolean intersects(Rectangle rect1, Rectangle rect2) {
		return rect1.intersects(rect2);
	}
	
	//Returns the distance between two points
	public static float getDistance(float x1, float y1, float x2, float y2) {
		
		return (float)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));		
	}
	
	public static float getDistance(Entity e1, Entity e2) {
		float x1 = e1.x;
		float y1 = e1.y;
		float x2 = e2.x;
		float y2 = e2.y;
		return getDistance(x1, y1, x2, y2);	
	}
	
	// Returns true if the Tile is visible on the screen
	public static boolean onScreen(Tile tile, float Margin) {
		EntityPlayer player = ZombieOutbreak.activePlayer;
		
		return tile.x > player.x - ZombieOutbreak.WINDOW_WIDTH/2 - Margin && tile.x < player.x + ZombieOutbreak.WINDOW_WIDTH/2 + Margin
				&& tile.y > player.y - ZombieOutbreak.WINDOW_HEIGHT/2 - Margin && tile.y < player.y + ZombieOutbreak.WINDOW_HEIGHT/2 + Margin;
	}
	
	// Returns true if the Entity is visible on the screen
	public static boolean onScreen(Entity entity, float Margin) {
		EntityPlayer player = ZombieOutbreak.activePlayer;
		
		return entity.x > player.x - ZombieOutbreak.WINDOW_WIDTH/2 - Margin && entity.x < player.x + ZombieOutbreak.WINDOW_WIDTH/2 + Margin
				&& entity.y > player.y - ZombieOutbreak.WINDOW_HEIGHT/2 - Margin && entity.y < player.y + ZombieOutbreak.WINDOW_HEIGHT/2 + Margin;
	}
	
	//Returns the angle between two points
	public static float getAngle(Point p1, Point p2) {
		int x = p1.x - p2.x;
		int y = p1.y - p2.y;
		
		return (float)Math.toDegrees(Math.atan2(y, x));
	}
	
	//Returns the angle between two points
	public static float getAngle(Entity e1, Vector2f v2) {
		float x = e1.x - v2.x;
		float  y = e1.y - v2.y;
		
		return (float)Math.toDegrees(Math.atan2(y, x));
	}
	
	//Returns the angle between two points
	public static float getAngle(float x1, float y1, float x2, float y2) {
		int x = (int)(x1 - x2);
		int y = (int)(y1 - y2);
		
		return (float)Math.toDegrees(Math.atan2(y, x));
	}
	
	//Returns the angle between two points
		public static float getAngle(Entity e1, Entity e2) {
			int x = (int)(e1.x - e2.x);
			int y = (int)(e1.y - e2.y);
			
			return (float)Math.toDegrees(Math.atan2(y, x));
		}
}
