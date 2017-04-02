package art;

import main.ZombieOutbreak;

public class SpriteSheetData {
	
	private static String path;
	public static SpriteSheet playerSpriteSheet;
	public static SpriteSheet zombieSpriteSheet;	
	public static SpriteSheet tileSpriteSheet;
	public static SpriteSheet weaponSpriteSheet;
	public static SpriteSheet buttonSpriteSheet;
	public static SpriteSheet menuBackGround;
	public static SpriteSheet healthBarSheet;
	public static SpriteSheet torchSheet;
	public static SpriteSheet deathScreen;
	public static SpriteSheet levelNameScreen1;
	public static SpriteSheet levelNameScreen2;
	public static SpriteSheet levelNameScreen3;
	public static SpriteSheet levelNameScreen4;
	public static SpriteSheet levelNameScreen5;
	public static SpriteSheet levelNameScreen6;
	public static SpriteSheet levelNameScreen7;
	public static SpriteSheet levelNameScreenComplete;
	
	
	public static void setupSpriteSheets() {
		path = ZombieOutbreak.path;
		playerSpriteSheet = new SpriteSheet(path + "/res/player_spritesheet.png", 16, 16);
		zombieSpriteSheet = new SpriteSheet(path + "/res/zombie_spritesheet.png", 16, 16);
		tileSpriteSheet = new SpriteSheet(path + "/res/tiles.png", 16, 16);
		weaponSpriteSheet = new SpriteSheet(path + "/res/weapons_dropped.png", 56, 20);
		buttonSpriteSheet = new SpriteSheet(path + "/res/button_sheet.png", 96, 32);
		menuBackGround = new SpriteSheet(path + "/res/MenuBackGround.png", 1024, 768);
		healthBarSheet = new SpriteSheet(path + "/res/health_bar_8.png", 32, 8);
		torchSheet = new SpriteSheet(path + "/res/torch.png", 800, 800);
		deathScreen = new SpriteSheet(path + "/res/death_screen.png", 640, 480);
		levelNameScreen1 = new SpriteSheet(path + "/levels/title_1.png", 640, 480);
		levelNameScreen2 = new SpriteSheet(path + "/levels/title_2.png", 640, 480);
		levelNameScreen3 = new SpriteSheet(path + "/levels/title_3.png", 640, 480);
		levelNameScreen4 = new SpriteSheet(path + "/levels/title_4.png", 640, 480);
		levelNameScreen5 = new SpriteSheet(path + "/levels/title_5.png", 640, 480);
		levelNameScreen6 = new SpriteSheet(path + "/levels/title_6.png", 640, 480);
		levelNameScreen7 = new SpriteSheet(path + "/levels/title_7.png", 640, 480);
		levelNameScreenComplete = new SpriteSheet(path + "/levels/title_complete.png", 640, 480);
		
	}
}
