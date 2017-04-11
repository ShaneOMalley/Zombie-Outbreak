--------------------------------------------------------------------
--------------------------------------------------------------------
 ________                      __                    
/\_____  \                    /\ \      __           
\/____//'/'    ___     ___ ___\ \ \____/\_\     __    
     //'/'    / __`\ /' __` __`\ \ '__`\/\ \  /'__`\     
    //'/'___ /\ \L\ \/\ \/\ \/\ \ \ \L\ \ \ \/\  __/ 
    /\_______\ \____/\ \_\ \_\ \_\ \_,__/\ \_\ \____\    
    \/_______/\/___/  \/_/\/_/\/_/\/___/  \/_/\/____/     
  _____            __    __                            __         
 /\  __`\         /\ \__/\ \                          /\ \        
 \ \ \/\ \  __  __\ \ ,_\ \ \____  _ __    __     __  \ \ \/'\    
  \ \ \ \ \/\ \/\ \\ \ \/\ \ '__`\/\`'__\/'__`\ /'__`\ \ \ , <  
   \ \ \_\ \ \ \_\ \\ \ \_\ \ \L\ \ \ \//\  __//\ \L\.\_\ \ \\`\  
    \ \_____\ \____/ \ \__\\ \_,__/\ \_\\ \____\ \__/.\_\\ \_\ \_\
     \/_____/\/___/   \/__/ \/___/  \/_/ \/____/\/__/\/_/ \/_/\/_/
--------------------------------------------------------------------
--------------------------------------------------------------------
Zombie Outbreak Version 1.0
A game by Shane O'Malley

----------------------

1:ChangeLog
2:Zombie Outbreak game
	2.1:Intro
	2.2:Controls
3:Map Making / Editing
4:Future Updates

----------------------
1:ChangeLog
----------------------

~~~~~~~~~~~~~~
FULL RELEASE:
~~~~~~~~~~~~~~
1.0 (Current Version):
	-Completed all levels (7 in total)
	-Go to next level when stepping on the yellow blocks
	-2 new weapons (SMG and ASSAULT RIFLE)
	-Added text at the beginning of a level
	-New and improved health bars for zombies
	-Three different types of zombies spawn
	-Made new Zombie Sprites

~~~~~~~~~~~~~~
ALPHA:
~~~~~~~~~~~~~~
0.6a (Current Version):
	-Made a new player sprite (more sprites to come)
	-Added death/game over mechanic
	-Changed bullet size
	-Zombies are now faster (it is not possible to outrun them anymore)
	-The level is now a little 'brighter'

0.5a:
	-Added New Torch psuedo-lighting
	-Changed shoot controls for WASD movement
0.4a:
	-Default Level is improved and more ellaborate
	-changed default control method back to WASD
	-added a new Menu Screen which allows the selection of control method (either WASD or Mouse)
	-Added Weapons and bullet projectiles
	-You can now kill Zombies (Finally!)

0.3a:
	-Fixed buggy Main Menu Buttons
	-Added a new mechanic where zombies "bounce off" each other and do not all "bunch up" into one
	-Player now gets slowed down when he is encumbered by zombies, the amount he slows is relative
 	 to the number of zombies on him
	-Fixed zombie texture (does not display flashing white line anymore)

0.2a:
	-Added Main Menu System(Still a bit buggy, will fix later)
	-Changed movement controls to Click-and-drag instead of WASD
	-Camera now follows player
	-Got rid of maximum zombie engagement distance(if you are in their line
	 of Sight, zombies will start running from any distance)
	-Changed background colour
	
0.1a:
	-Started Development
	-Added (very)basic WASD movement
	-Added (very)basic Zombie AI
	-Added a collision system for the player (will add for zombies later on)
                                                
----------------------
2:Zombie Outbreak Game
----------------------
  ----------------------
  2.1:Intro
  ----------------------
  'Zombie Outbreak' is a top down zombie survival game in VERY early development, as
  of now there is not much in the way of gameplay, but that will soon change, I
  intend to finish this project in its entirity, and for this project to give me a
  better understanding of game mechanics
  ----------------------
  2.2:Controls
  ---------------------- 
  WASD (default)
   (or)
  Mouse Click (Click-and-Drag)


----------------------
3:Map Making/Editing
----------------------
It is now possible to make maps for ZombieOutbreak
to make a map all you need is basic image editing software (I use Paint.NET, its free)

You can basically draw the map and different colors represent different tiles/entities(You can edit the existing levels also)

Here is a list to all the current tiles and entities:
  Wall         : HEX = 000000 R = 000 G = 000 B = 000
  Floor        : HEX = FFFFFF R = 255 G = 255 B = 255

  Player Spawn : HEX = 00FF00 R = 000 G = 255 B = 000 (*Make sure there is only one of these*)
  Zombie       : HEX = FF0000 R = 255 G = 000 B = 000
  SHOTGUN      : HEX = FF6A00 R = 255 G = 106 B = 000
  SMG	       : HEX = 7F3300 R = 127 G = 51  B = 000
  ASSAULT RIFLE: HEX = 7F6A00 R = 127 G = 106 B = 000

