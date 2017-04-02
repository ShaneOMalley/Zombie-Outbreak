package main;

public class Controller {
	
	public static void tick(Entity entity) {
		
		if(entity instanceof EntityPlayer) { ControllerPlayer.tick((EntityPlayer)entity); }
		if(entity instanceof EntityZombie) { ControllerZombie.tick((EntityZombie)entity); }
		if(entity instanceof EntityBullet) { ControllerBullet.tick((EntityBullet)entity); }		
	}
}
