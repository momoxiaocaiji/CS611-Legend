# CS611-Legends

## Name

---

Zheng Jiang

U88611204

Yunqi Ni

U25997530

coding my project on Windows


## Files

---
1. Armor - class as the Armor object
2. BushTile - the bush tile in the map, which will trigger the buff after stepping in.
3. Buyable - the interface indicates an item can be brought.
4. Castable - the interface indicates a spell can be cast.
5. CaveTile - the cave tile in the map, which will trigger the buff after stepping in.
6. Character - the abstract class for Character in the game, such as hero and monster
7. CirculateList - give the next Character who supposed to move during the fight.
8. Constant - maintain some final factor of this game
9. Equipment - keep the information of the hero's equipments
10. FightSystem - the interface which indicates the fight action between heroes and monsters.
11. FireSpell - class as the fire_spell object
12. Hero - class as the Hero object, which has subclass like Warrior.
13. Hurtable - indicates the Character can be hurt
14. IceSpell - class as the ice_spell object
15. InaccessibleTile - the inaccessible tile in the map
16. Inventory - the inventory of the hero
17. Item - the abstract class for all item in the game, such as weapon, spell ...
18. KoulouTile - the Koulou tile in the map, which will trigger the buff after stepping in.
19. Learnable - the interface indicates a spell can be learned
20. LightningSpell - class as the lightning_spell object
21. MAHGame - the main class to create a MAHGame and start it.
22. MAHGamePlayer - the player of the MAHGame
23. Main - the class for running
24. MainMarket - the only market of the game, every NexusTile will visit this market
25. MainScanner - the only scanner of the game, every input will invoke this scanner
26. Market - the interface Market for extension
27. Monster - class as the Monster object
28. MonsterCreator - interface of the Creator of Factory Pattern .
29. NexusTile - the market tile in the map, which will trigger the market
30. Paladin - class as the Paladin object
31. Parser - class to parse the information in the config file
32. ParticipateBasedFightSystem - the fight system for one turn fight, which is suitable for valor.
33. PlainTile - the plain tile in the map
34. PlayMap - the game map of the MAH game
35. Position - record the position of the player, rewrite equals method for comparing the position easily.
36. Potion - class as the Potion object.
37. RandomMonsterCreator - the Creator of Factory Pattern , to create a monster from different type of monster randomly.
38. RPGame - the abstract class for extension, in case of another RPGame.
39. Saleable - interface that indicates an item can be sold.
40. Sorcerer - class as the Sorcerer object
41. Spell - class as the Spell object, which has subclass like FireSpell
42. Teleport - interface which indicates the character can teleport.
43. Usable - interface which indicates the item can be used
44. ValorTile - the adaptor class for the sub-tile class to have the hero and monster as their member invariant.
45. Tile - the interface of the tile
46. TileCreator - interface of the Creator of Factory Pattern .
47. Warrior - class as the Warrior object
48. Weapon - class as the Weapon object


## Note

---
1. Files to be parsed should be stored in ConfigFiles, for parser class to read class
2. might bonus
   1. the color output in the terminal
      1. the player's position in this map will be red
      2. the damage and defence of hero will be displayed in two part. The damage and defence of their own and that of the
      equipments in the parentheses.
      3. the damage from formal attack will be white. red for fire_spell, blue for ice_spell, yellow for lighting_spell
      4. the inaccessibleTile is black background
   2. some extension
      1. this game can play with multiple players
      2. you can and more skills to the hero
   3. have a Constant class to maintain all final factor, which makes it easier to change the factor.
   4. Design Pattern
      1. I use Singleton Pattern for class MainMarket and MainScanner, because for the whole game, we only need one market and 
      scanner, thus Singleton Pattern is suitable.
      2. I use Factor Pattern for Tile and Monster. Because when we create a game map, the tile will be randomly generated,
      the same idea as the monster team when the heroes go into a commonTile, then the monster in this tile should be randomly 
      created.
      3. I use Iterator Pattern for choosing who should move during the fight. The class CirculateList is designed for this 
      idea. You put your team in the CirculateList and it will give the next Character to move circularly.
      4. hero can choose the monster he'd like to fight
3. Things instructions to note
   1. defence can only decrease (20% * defence) damage
   2. every hero will get a Sword and a Platinum_Shield in their equipment as the init equipment
   3. spell cannot avoid and defence.
   4. the monster will attack the hero moved last (maybe they hate the hero who caused the damage)
   5. you can equip the armor on the body, and two single-handy weapons for two hands
   6. NexusTile has the letter 'M' in the Tile
   7. When the player check the inventory or enter the market, the hero will do the action one by one.
   8. You will enter the market automatically when you step in the Market Tile, and you can't enter it again if you exit 
   the market.

## How to run

---
1. Navigate to the directory after downloading the project
2. Run the following instructions on command line:
   In the src directory, run
   javac *.java
   to get the compiled class files
3. Back to the previous directory, and run
   java -cp src Main.java