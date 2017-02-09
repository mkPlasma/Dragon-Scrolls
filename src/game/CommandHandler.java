package game;

import java.awt.Color;
import java.util.concurrent.TimeUnit;


public class CommandHandler{
	
	private final String[] acceptedInput = {
		"help",
		"hp",
		
		"inventory",
		"items",
		"i",
		
		"area",
		"a",
		
		"consume",
		"drink",
		"eat",
		"use",
		"u",
		
		"equip",
		"eq",
		
		"stats",
		"st",
		
		"buy",
		"shop",
		"s",
		
		"look",
		"l",
		
		"interact",
		"in",
		
		"map",
		"m",
		
		"money",
		"gold",
		"gp",
		
		"walk",
		"run",
		"goto",
		"g",
		
		"options",
		"option",
		"o",
		
		"command",
		"c",
		
		"xp",
		"x",
		
		"save",
		"sv",
		
		"hunt",
		"h",
		
		"rest",
		"r",
		
		"exit",
		"quit",
	};
	
	private final String[] tips = {"You can rest after a battle to recover your stats.",
		"When entering a new area, look around then try interacting with things you find.",
		"Use an item during a battle to recover your stats.",
		"Examine an enemy to see its status effects.",
		"If you find an item you don't need, sell it to a tradesman.",
		"You can view all the stats of every item you own in your inventory menu.",
		"In battle, you can change your equipment."};
	
	private Enemy enemy = ObjectList.getEnemyList()[0];
	private Enemy lastEnemyKilled;
	
	private Save save = new Save();
	
	private boolean incrementUpdateTick;
	private boolean inFight = false;
	
	private boolean randomizeEncounter = true;
	private boolean tutorialComplete = false;
	
	private int restCounter = 0;
	
	private final Item emptyItem = new Item();
	private final StatusEffect emptyEffect = new StatusEffect();
	
	private final Color COLOR_DARK_GREEN = new Color(0, 128, 0), COLOR_GOLD = new Color(96, 96, 0);
	
	public void commands(){
		
		Text.printLineExtra("Input a command:");
		
		int stringChosen = Text.testInput(acceptedInput);
		incrementUpdateTick = false;
		randomizeEncounter = true;
		updatePlayer(false);
		
		if(stringChosen == -1){
			stringChosen = Text.testInput(Text.trimToSpace(Text.getLastInput()), acceptedInput);
		}
		
		switch(stringChosen){
			case 0: case 1: // help
				helpMenu(Text.trimFromSpace(Text.getLastInput()));
				break;
			
			case 2: case 3: case 4: // inventory
				inventory(Text.trimFromSpace(Text.getLastInput()));
				break;
			
			case 5: case 6: // area
				Text.printLineExtra("You are currently in ");
				Text.print(AreaHandler.getArea().getName(), AreaHandler.getArea().getColor());
				Text.print(".");
				break;
			
			case 7: case 8: case 9: case 10: case 11: // use
				useItem(Text.trimFromSpace(Text.getLastInput()));
				break;
			
			case 12: case 13: // equip
				equipItem(Text.trimFromSpace(Text.getLastInput()));
				break;
			
			case 14: case 15: // stats
				printStats();
				break;
			
			case 16: case 17: case 18: // shop
				shop(Text.trimFromSpace(Text.getLastInput()));
				break;
			
			case 19: case 20: // look
				Text.printLineExtra("Looking around...");
				waitSeconds(1, false);
				Text.printLine(AreaHandler.getArea().getSurroundings());
				incrementUpdateTick = true;
				break;
			
			case 21: case 22: // interact
				interact(Text.trimFromSpace(Text.getLastInput()));
				break;
			
			case 23: case 24: // map
				Text.printLineExtra("Your map:");
				
				for(Area a : AreaHandler.getAccessibleAreas()){
					Text.printLine(a.getName(), a.getColor());
				}
				
				break;
			
			case 25: case 26: case 27: // gold
				Text.printLineExtra("You have ");
				Text.print(Integer.toString(Player.getGold()) + " gold", COLOR_GOLD);
				Text.print(".");
				break;
			
			case 28: case 29: case 30: case 31: // goto
				goTo(Text.trimFromSpace(Text.getLastInput()));
				break;
			
			case 32: case 33: case 34: // options
				options(Text.trimFromSpace(Text.getLastInput()));
				break;
				
			case 35: case 36: // command (debug)
				if(Settings.debugEnabled()){
					executeCommand(true);
				}
				else{
					Text.printLine("Input not recognized, please try again");
				}
				break;
			
			case 37: case 38: // xp
				Text.printLineExtra("You are ");
				Text.print("level " + Integer.toString(Player.getLevel()), COLOR_GOLD);
				Text.print(" with ");
				Text.print(Integer.toString(Player.getXP()) + "/" + Integer.toString(Player.getXPReq()) + " XP (" + Integer.toString(Player.getXPReq() - Player.getXP()) + ")", COLOR_DARK_GREEN);
				Text.print(" remaining.");
				break;
			
			case 39: case 40: // save
				Text.printLine("Saving...");
				if(!Settings.debugEnabled()){
					save.setTutorialComplete(tutorialComplete);
					save.save(save.getSaveLoaded(), save.getSaveName(), AreaHandler.getArea(), AreaHandler.getAccessibleAreas());
				}
				Text.printLine("Game saved.");
	
				displayTip();
				
				break;
			
			case 41: case 42: // hunt
				Text.printLine("Hunting...");
				waitSeconds(RandomGen.getInt(1, 2), false);
				randomizeEncounter = false;
				
				if(AreaHandler.getArea().getEncounters().length == 0){
					Text.printLine("You couldn't find any enemies.");
				}
				
				incrementUpdateTick = true;
				break;
			
			case 43: case 44: // rest
				
				if(restCounter <= 0){
					Text.printLine("Resting...");
					waitSeconds(2, false);
					
					int hp = Player.getHealth();
					int mp = Player.getMagic();
					
					Player.restoreStats();
					
					Text.printLine("You recovered ");
					Text.print(Integer.toString(Player.getHealth() - hp) + " health", Color.RED);
					Text.print(" and ");
					Text.print(Integer.toString(Player.getMagic() - mp) + " magic", Color.BLUE);
					Text.print(".");
					
					Text.printLine("You now have ");
					Text.print(Integer.toString(Player.getHealth()) + "/" + Integer.toString(Player.getMaxHealth()) + " health", Color.RED);
					Text.print(" and ");
					Text.print(Integer.toString(Player.getMagic()) + "/" + Integer.toString(Player.getMaxMagic()) + " magic", Color.BLUE);
					Text.print(".");
					
					restCounter = 5;
					incrementUpdateTick = true;
				}
				else{
					Text.printLine("You have just rested, you cannot rest again for now.");
				}
				
				break;
			case 45: case 46: // exit
				Text.printLineExtra("Save before exiting?");
				Text.printYN();
				
				boolean answered = false;
				
				while(!answered){
					stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n", "back", "b"});
					
					switch(stringChosen){
					case 0: case 1:
						Text.printLine("Saving...");
						if(!Settings.debugEnabled()){
							save.setTutorialComplete(tutorialComplete);
							save.save(save.getSaveLoaded(), save.getSaveName(), AreaHandler.getArea(), AreaHandler.getAccessibleAreas());
						}
						Text.printLine("Game saved.");
						
						System.exit(0);
					case 2: case 3:
						System.exit(0);
					case 4: case 5:
						break;
					default:
						Text.printLine("Input not recognized, please try again");
						break;
					}
				}
				
				break;
			default:
				Text.printLine("Input not recognized, please try again");
				break;
		}
		
		updatePlayer(false);
	}
	
	//Help main menu
	private void helpMenu(String input){
		
		if(input.equals("")){
			Text.printLineExtra(new String[]{"What do you need help with?", "Commands", "Status Effects", "Dragon Scrolls", "Gameplay", "Combat", "Back"});
		}
		
		int stringChosen = -1;
		
		if(input.equals("")){
			stringChosen = Text.testInput(new String[]{"commands", "command", "status effects", "status effect", "status", "effects", "effect", "dragon scrolls", "dragon", "dscrolls", "scrolls", "scroll", "gameplay", "game", "play", "combat", "back", "b"});
		}
		else{
			stringChosen = Text.testInput(input, new String[]{"commands", "command", "status effects", "status effect", "status", "effects", "effect", "dragon scrolls", "dragon", "dscrolls", "scrolls", "scroll", "gameplay", "game", "play", "combat", "back", "b"});
			
			if(stringChosen == -1){
				Text.printLine("Input not recognized, please try again");
				return;
			}
		}
		
		boolean displayText = true;
		
		while(true){
			
			switch(stringChosen){
			case 0: case 1:
				helpMenuCommands();
				return;
			
			case 2: case 3: case 4: case 5:
				Text.addLine();
				
				Text.printLine("Buffs", COLOR_DARK_GREEN);
				Text.print(":");
				
				Text.printLine("Regeneration", COLOR_DARK_GREEN);
				Text.print(": Increases the user's health over time.");
				Text.printLine("Magic Regeneration", COLOR_DARK_GREEN);
				Text.print(": Increases the user's magic over time.");
				Text.printLine("Defense Buff", COLOR_DARK_GREEN);
				Text.print(": Increases the user's defense.");
				Text.printLine("Strength", COLOR_DARK_GREEN);
				Text.print(": Increases the user's damage output.");
				Text.printLine("Health/Magic Max Increase", COLOR_DARK_GREEN);
				Text.print(": Temporarily increases your maximum health/magic.");
				Text.printLine("Barrier", COLOR_DARK_GREEN);
				Text.print(": The user has a chance to completly block incoming attacks.");
				Text.printLine("Absorption", COLOR_DARK_GREEN);
				Text.print(": Heals instead of taking damage.");
				
				Text.addLine();
				Text.printLine("Debuffs", Color.RED);
				Text.print(":");
				
				Text.printLine("Imprison", Color.RED);
				Text.print(": Disables special attacks.");
				Text.printLine("Lock", Color.RED);
				Text.print(": Disables item use.");
				Text.printLine("Paralysis", Color.RED);
				Text.print(": Cannot go to different locations or run from battles.");
				Text.printLine("Bleed and Burn", Color.RED);
				Text.print(": Decreases the target's health over time.");
				Text.printLine("Poison and Magic Steal");
				Text.print(": Decreases magic over time.");
				Text.printLine("Weakness", Color.RED);
				Text.print(": Decreases the target's damage output.");
				Text.printLine("Defense Break", Color.RED);
				Text.print(": Decreases the target's defense.");
				Text.printLine("Health/Magic Max Decrease");
				Text.print(": Temporarily decreases your maximum health/magic.");
				Text.printLine("Confusion", Color.RED);
				Text.print(": The target has a chance to fail an attack.");
				
				displayText = true;
				break;
			
			case 6: case 7: case 8: case 9: case 10: case 11:
				Text.printLineExtra("The ");
				Text.print("Dragon Scrolls", ObjectList.COLOR_DRAGON);
				Text.print(" are very rare, special spells with extreme power.");
				Text.printLine(new String[]{"Each is made for knights, archers, or mages, and are comprised of magic used by dragons.", "Thus, they are the guardians of these spells. " +
				"The spells were created by an ancient guild of mages, for adventurers.", "They realized their potential, and summoned six dragons, each in a respective mountain, to protect the scrolls.", "Those who defeat these dragons prove themselves worthy of " +
				"the scrolls and, therefore, obtain their power.", "", "The list of dragons is as follows:"});
				Text.printLine(new String[]{"Dragon: Dragonstone Mountain", "Inorexite Dragon: Monolith Mountain", "Earth Core Dragon: Earthkey Mountain", "Fire Hydra: Hydra Hell Castle",
				"Black Void Dragon: Void Mountain", "Nova Dragon: Azure Star Mountain"}, ObjectList.COLOR_DRAGON);
				Text.printLineExtra("The list of Dragon Scrolls is as follows:");
				Text.printLine(new String[]{"Dragon Scroll I: Magic Arrow Storm", "Dragon Scroll II: Cosmic Speed Quiver", "Dragon Scroll III: Arcane Summoning Sword",
				"Dragon Scroll IV: Colossal Buffer Shield", "Dragon Scroll V: Ancient Spells of Wizardry", "Dragon Scroll VI: Geological Destruction Spell", "Dragon Scroll VII: Solar Flame Spell", "Dragon Scroll VIII: Infinity Banishment Spell",
				"Dragon Scroll IX: Magic Singularity Spell"}, ObjectList.COLOR_DRAGON);
				
				displayText = true;
				break;
			
			case 12: case 13: case 14:
				Text.printLineExtra(new String[]{"The general goal of the game is simply to advance to new areas, level up, and find new items.", "There is also the task of defeating the "});
				Text.print("Dragon God", ObjectList.COLOR_GOD);
				Text.print(" which will reward you with something very special.");
				Text.printLine("If you wish to get to the ");
				Text.print("Dragon God", ObjectList.COLOR_GOD);
				Text.print(", you'll have to progress far enough until you can confront him and prove yourself in battle.");
				Text.printLineExtra(new String[]{"Syntax:", "Commands are not case sensitive. Some commands, such as ones that have attacks, items, or " +
				"interactions, require you to type the full name of the selection.", "In some cases, such as goto or equip, you can type the command and extra input in the same command (e.g. equip dragon scroll i)."});
				
				displayText = true;
				break;
			
			case 15:
				Text.printLineExtra(new String[]{"Combat in the game is turn-based.", "Some stronger enemies will attack before you have the chance to, but you will be able to attack weaker ones first.", "", "When fighting, you can choose from a list " +
				"of commands. These include:", "Attack: Use an attack from your normal or special weapon.", "Items: Use an item in your inventory.", "Equip: Equip an item in your inventory.", "Stats: View your HP, MP, and defense.", "Examine Enemy: View the enemy's HP " +
				", defense, and status effects.", "Run: Attempt to escape from the battle.", "Attacking, using an item, or attempting to run will use your turn and allow the enemy to attack.", "", "Upon killing an enemy, you will recieve some XP and gold, along with any items the enemy may " +
				"have dropped."});
				
				displayText = true;
				break;
			
			case 16: case 17:
				Text.printLineExtra("Exited the help menu.");
				return;
			
			default:
				Text.printLine("Input not recognized, please try again");
				displayText = false;
				break;
			}
			
			if(displayText){
				Text.printLineExtra(new String[]{"What do you need help with?", "Commands", "Status Effects", "Dragon Scrolls", "Gameplay", "Combat", "Back"});
			}
			
			stringChosen = Text.testInput(new String[]{"commands", "command", "status effects", "status effect", "status", "effects", "effect", "dragon scrolls", "dragon", "dscrolls", "scrolls", "scroll", "gameplay", "game", "play", "combat", "back", "b"});
		}
	}
	
	//Help Commands menu
	private void helpMenuCommands(){
		Text.printLineExtra("Would you like a list of commands or a detailed description?");
		
		int stringChosen = Text.testInput(new String[]{"list of commands", "list of command", "commands list", "command list", "list", "commands", "command", "detailed description", "detail description", "detailed desc", "detail desc", "detailed", "details", "detail", "description", "desc", "back", "b"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
				Text.printLine(new String[]{"", " Help/hp", " Inventory/inv/i", " Area/a", " Use/u", " Equip/e", " Stats/st", " Shop/s", " Look/l", " Interact/i", " Map/m", " Gold/gp", " Goto/g", " Options/o", " XP/x", " Save/sv", " Hunt/h", " Rest/r", "Exit/Quit", "Exited the help menu."});
				return;
			
			case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16:
				helpMenuCommandsSub();
				return;
			
			case 17: case 18:
				return;
			
			default:
				Text.printLine("Input not recognized, please try again");
				break;
			}
			
			stringChosen = Text.testInput(new String[]{"list of commands", "list of command", "commands list", "command list", "list", "commands", "command", "detailed description", "detail description", "detailed desc", "detail desc", "detailed", "details", "detail", "description", "desc", "back", "b"});
		}
	}
	
	//Help Commands (specific) menu
	private void helpMenuCommandsSub(){
		
		Text.printLineExtra("Type a command and a description will be shown.");
		
		String[] input = new String[acceptedInput.length + 2];
		
		for(int i = 0; i < acceptedInput.length; i++){
			input[i] = acceptedInput[i];
		}
		
		input[input.length - 1] = "b";
		input[input.length - 2] = "back";
		
		int stringChosen = Text.testInput(input);
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				Text.printLine("Help/hp: Displays the help menu.");
				break;
				
			case 2: case 3: case 4:
				Text.printLine("Inventory/i: Displays your inventory.");
				break;
				
			case 5: case 6:
				Text.printLine("Area/a: Displays your current location.");
				break;
				
			case 7: case 8: case 9: case 10: case 11:
				Text.printLine("Use/u: Use an item in your inventory.");
				break;
				
			case 12: case 13:
				Text.printLine("Equip/eq: Shows your equipped items, or equip different items.");
				break;
				
			case 14: case 15:
				Text.printLine("Stats/st: Displays your health, magic, and defense.");
				break;
				
			case 16: case 17: case 18:
				Text.printLine("Shop/s: Buy items at a shop when available.");
				break;
				
			case 19: case 20:
				Text.printLine("Look/l: Observes your surroundings.");
				break;
				
			case 21: case 22:
				Text.printLine("Interact/in: Interact with your surroundings.");
				break;
				
			case 23: case 24:
				Text.printLine("Map/m: View accessible areas on the region map.");
				break;
				
			case 25: case 26: case 27:
				Text.printLine("Gold/gp: View your current gold.");
				break;
				
			case 28: case 29: case 30: case 31:
				Text.printLine("Goto/g: Go to a specified location.");
				break;
				
			case 32: case 33: case 34:
				Text.printLine("Options/o: Menu for enabling sound, waiting, and other options.");
				break;
				
			case 37: case 38:
				Text.printLine("XP/x: Shows your current level and experience.");
				break;
				
			case 39: case 40:
				Text.printLine("Save/sv: Saves your game if possible.");
				break;
				
			case 41: case 42:
				Text.printLine("Hunt/h: Hunts for an enemy.");
				break;
				
			case 43: case 44:
				Text.printLine("Rest/r: Rests to regain your stats.");
				break;
				
			case 45: case 46:
				Text.printLine("Exit/Quit: Prompts to save then exits the game.");
				break;
				
			case 47: case 48:
				Text.printLineExtra("Exited the help menu.");
				return;
				
			default:
				Text.printLine("Input not recognized, please try again");
				break;
			}
			
			stringChosen = Text.testInput(input);
		}
	}
	
	//Inventory main menu
	private void inventory(String input){
		
		Item[] inv = Player.getInventory();
		
		if(input.equals("")){
			Text.printLineExtra("Your Inventory (" + Integer.toString(Player.getOpenInventorySlots()) + "/" + Integer.toString(Player.getInventory().length - 3) + ") Slots full:");
			
			for(int i = 3; i < inv.length; i++){
				if(!inv[i].getName().equals(emptyItem.getName())){
					Text.printLine(" ");
					Text.print(inv[i].getName(), inv[i].getColor());
					Text.print(" [" + Integer.toString(Player.getItemCountInInventorySlot(i)) + "]");
				}
			}
			Text.printLine("Type an item name for more options.");
		}
		
		int itemNum = 0;
		
		for(int i = 3; i < inv.length; i++){
			if(!inv[i].getName().equals(emptyItem.getName())){
				itemNum++;
			}
		}
		
		String[] itemNames = new String[itemNum + 2];
		
		for(int i = 3; i < inv.length; i++){
			if(!inv[i].getName().equals(emptyItem.getName())){
				itemNames[i - 3] = inv[i].getName();
			}
		}

		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		
		if(input.equals("")){
			itemNum = Text.testInput(itemNames);
		}
		else{
			itemNum = Text.testInput(input, itemNames);
			
			if(itemNum == -1){
					Text.printLine("Input not recognized, please try again");
					return;
			}
		}
		
		boolean displayText = true;
		
		while(true){
			
			if(itemNum >= itemNames.length - 2){
				return;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 2){
				inventorySub(inv[itemNum + 3]);
				displayText = true;
			}
			else{
				Text.printLine("Input not recognized, please try again");
				displayText = false;
			}
			
			if(displayText){
				Text.printLineExtra("Your Inventory (" + Integer.toString(Player.getOpenInventorySlots()) + "/" + Integer.toString(Player.getInventory().length - 3) + ") Slots full:");
				
				for(int i = 3; i < inv.length; i++){
					if(!inv[i].getName().equals(emptyItem.getName())){
						Text.printLine(" ");
						Text.print(inv[i].getName(), inv[i].getColor());
						Text.print(" [" + Integer.toString(Player.getItemCountInInventorySlot(i)) + "]");
					}
				}
				Text.printLine("Type an item name for more options.");
			}
			
			itemNum = Text.testInput(itemNames);
		}
	}
	
	//Inventory selected item menu
	private void inventorySub(Item item){
		
		Text.printLineExtra("Do what with ");
		Text.print(item.getDisplayName() + "?", item.getColor());
		Text.printLine(new String[]{"Use", "Equip", "Stats", "Drop"});
		
		int stringChosen = Text.testInput(new String[]{"use", "u", "equip", "eq", "e", "stats", "st", "s", "drop", "d", "back", "b"});
		
		boolean displayText = true;
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				
				if(displayText){
					Text.printLineExtra("Do what with ");
					Text.print(item.getDisplayName() + "?", item.getColor());
					Text.printLine(new String[]{"Use", "Equip", "Stats", "Drop"});
				}
				
				if(item.canUse()){
					if(Player.hasStatusEffect(6)){
						Text.printLine("You are under the influence of a lock effect.");
						displayText = true;
						break;
					}
					
					useItem(item);
					return;
				}
				else{
					Text.printLine("That item cannot be used.");
					displayText = true;
					break;
				}
			
			case 2: case 3: case 4:
				
				if(item.getType() >= 0 && item.getType() <= 2){
					if(item.getClassType() == Player.getClassType() || item.getClassType() == 3){
						
						Player.switchItemsInInventory(Player.getInventory()[item.getType()], item);
						Sound.playSound(item.getSound());
						
						Text.printLine("You equipped your ");
						Text.print(item.getName(), item.getColor());
						Text.print(".");
						updatePlayer(true);
						
						incrementUpdateTick = true;
						return;
					}
					else{
						Text.printLine("That item is not for your class.");
						displayText = true;
						break;
					}
				}
				else{
					Text.printLine("That item cannot be equipped.");
					displayText = true;
					break;
				}
			
			case 5: case 6: case 7:
				
				if(item.getType() >= 0 && item.getType() <= 3){
					itemInfo(item);
					return;
				}
				else{
					Text.printLine("That item has no stats.");
					displayText = true;
					break;
				}
			
			case 8: case 9:
				removeItem(item, 0);
				return;
			
			case 10: case 11:
				return;
			
			default:
				Text.printLine("Input not recognized, please try again");
				displayText = false;
				break;
			}
			
			if(displayText){
				Text.printLineExtra("Do what with ");
				Text.print(item.getDisplayName() + "?", item.getColor());
				Text.printLine(new String[]{"Use", "Equip", "Stats", "Drop"});
			}
			
			stringChosen = Text.testInput(new String[]{"use", "u", "equip", "eq", "e", "stats", "st", "s", "drop", "d", "back", "b"});
		}
	}
	
	//Drops selected item
	private void removeItemChoice(){
		
		Text.printLineExtra("Drop which item?");
		
		
		Item[] inv = Player.getInventory();
		String[] itemNames = new String[inv.length + 2];
		
		for(int i = 0; i < inv.length; i++){
			
			for(int j = 3; j < inv.length; j++){
				if(!inv[j].getName().equals(emptyItem.getName())){
					Text.printLine(" ");
					Text.print(inv[j].getName(), inv[j].getColor());
					Text.print(" [" + Integer.toString(Player.getItemCountInInventorySlot(j)) + "]");
				}
			}
			
			itemNames[i] = inv[i].getName();
		}

		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		
		int itemNum = Text.testInput(itemNames);
		
		while(true){
			if(itemNum >= itemNames.length - 2){
				Text.printLine("Item drop cancelled.");
				return;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 2){
				removeItem(inv[itemNum], 1);
				Sound.playSound(inv[itemNum].getSound().replace("items/", "items/drop/"));
				return;
			}
			else{
				Text.printLine("Input not recognized, please try again");
			}
			
			itemNum = Text.testInput(itemNames);
		}
	}
	
	//Drops item(s) in parameters
	private void removeItem(Item item, int count){
		if(Player.getInventoryItemCount(item, false) > 1){
			
			boolean displayText = true;
			
			while(true){
				if(displayText){
					Text.printLineExtra("How many do you want to drop?");
					displayText = false;
				}
				
				String stringChosen = Text.testInput();
				
				try{
					count = Integer.parseInt(stringChosen);
				}
				catch(Exception e){
					Text.printLine("Input is not a number or is in Text form. Please try again.");
					displayText = false;
				}
				
				if(count > Player.getInventoryItemCount(item, false) || count < 1){
					Text.printLine("You cannot drop that many.");
					displayText = true;
				}
				else{
					Text.printLineExtra("Are you sure you want to drop " + Integer.toString(count) + " ");
					Text.print(item.getName(), item.getColor());
					Text.print("s?");
					Text.printYN();
					Text.printLine("Once dropped, you cannot retrieve them.");
					break;
				}
			}
		}
		else{
			Text.printLineExtra("Are you sure you want to drop your ");
			Text.print(item.getName(), item.getColor());
			Text.print("?");
			Text.printYN();
			Text.printLine("Once dropped, you cannot retrieve it.");
			count = 1;
		}
		
		int stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				if(count == 1){
					Text.printLine("Item dropped.");
				}
				else{
					Text.printLine("Items dropped.");
				}
				
				for(int i = 0; i < count; i++){
					Player.removeItemFromInventory(item);
				}
				
				Sound.playSound(item.getSound().replace("items/", "items/drop/"));
				return;
			
			case 2: case 3:
				if(count == 1){
					Text.printLine("Item not dropped.");
				}
				else{
					Text.printLine("Items not dropped.");
				}
				return;
			
			default:
				Text.printLine("Input not recognized, please try again");
				break;
			}
			
			stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Use item menu
	private boolean useItem(String input){
		
		if(Player.hasStatusEffect(6)){
			Text.printLine("You are under the influence of a lock effect.");
			return false;
		}
		
		if(input.equals("")){
			Text.printLineExtra("Use which item?");
		}
		
		Item[] items = Player.getUsableItemsInInventory();
		String[] itemNames = new String[items.length + 2];
		
		for(int i = 0; i < items.length; i++){
			
			if(input.equals("")){
				Text.printLine(" ");
				Text.print(items[i].getName(), items[i].getColor());
				Text.print(" [" + Integer.toString(Player.getUsableItemCountInInventory()[i]) + "]");
			}
			
			itemNames[i] = items[i].getName();
		}

		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		int itemNum = -1;
		
		if(input.equals("")){
			itemNum = Text.testInput(itemNames);
		}
		else{
			itemNum = Text.testInput(input, itemNames);
			
			if(itemNum == -1){
					Text.printLine("Input not recognized, please try again");
					return false;
			}
		}
		
		while(true){
			if(itemNum >= itemNames.length - 2){
				Text.printLine("Item use cancelled.");
				return false;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 2){
				boolean tmp = useItem(items[itemNum]);
				incrementUpdateTick = tmp;
				
				return tmp;
			}
			else{
				Text.printLine("Input not recognized, please try again");
			}
			
			itemNum = Text.testInput(itemNames);
		}
	}
	
	//Use item function
	private boolean useItem(Item item){
		
		if(!item.canUse()){
			Text.printLine("That item cannot be used.");
			return false;
		}
		
		if(item.getType() == 4){	// Misc Items
			int itemNum = Text.testInput(item.getName(), new String[]{ObjectList.getMiscItemList()[0].getName()});
			
			switch(itemNum){
			case 0: // Nightmare Castle Key
				if(AreaHandler.isAreaAccessible(ObjectList.getAreaList()[4])){
					Text.printLineExtra("You have already unlocked ");
					Text.print(ObjectList.getAreaList()[4].getName(), ObjectList.getAreaList()[4].getColor());
					Text.print(".");
					return false;
				}
				else{
					int count = Player.getItemCountInInventory(ObjectList.getMiscItemList()[0]);
					
					if(count < 5){
						Text.printLineExtra(new String[]{"You don't have enough keys to unlock the castle.", "You need " + Integer.toString(5 - count) + " more."});
						return false;
					}
					else if(AreaHandler.getArea() != ObjectList.getAreaList()[3]){
						Text.printLine("You must be in ");
						Text.print(ObjectList.getAreaList()[3].getName(), ObjectList.getAreaList()[3].getColor());
						Text.print(" to use the keys.");
					}
					else{
						for(int i = 0; i < 5; i++){
							Player.removeItemFromInventory(ObjectList.getMiscItemList()[0]);
						}
						
						AreaHandler.addAccessibleArea(ObjectList.getAreaList()[4]);
						
						Text.printLineExtra("You can now access ");
						Text.print(ObjectList.getAreaList()[4].getName(), ObjectList.getAreaList()[4].getColor());
						Text.print("!");
						
						return true;
					}
				}
			}
			
			return false;
		}
		
		if(!item.hasStatusEffects()){
			if(item.getConsumableStats()[0] > 0 && Player.getHealth() == Player.getMaxHealth()){
				if(item.getConsumableStats()[1] > 0 && Player.getMagic() == Player.getMaxMagic()){
					Text.printLineExtra("You did not use ");
					Text.print(item.getDisplayName(), item.getColor());
					Text.print(" because you already have full ");
					Text.print("health", Color.RED);
					Text.print(" and ");
					Text.print("magic", Color.BLUE);
					Text.print(".");
					
					return false;
				}
				Text.printLineExtra("You did not use ");
				Text.print(item.getDisplayName(), item.getColor());
				Text.print(" because you already have full ");
				Text.print("health", Color.RED);
				Text.print(".");
				
				return false;
			}
			else if(item.getConsumableStats()[1] > 0 && Player.getMagic() == Player.getMaxMagic()){
				Text.printLineExtra("You did not use ");
				Text.print(item.getDisplayName(), item.getColor());
				Text.print(" because you already have full ");
				Text.print("magic", Color.BLUE);
				Text.print(".");
				
				return false;
			}
		}
		
		if(item.getClassType() == Player.getClassType() || item.getClassType() == 3){
			
			Player.removeItemFromInventory(item);
			Sound.playSound(item.getUseSound());
			
			Text.printLineExtra("You used ");
			Text.print(item.getDisplayName(), item.getColor());
			Text.print(".");
			incrementUpdateTick = true;
			
			String healthRestored = Integer.toString(Math.min(item.getConsumableStats()[0], Player.getMaxHealth() - Player.getHealth()));
			String magicRestored = Integer.toString(Math.min(item.getConsumableStats()[1], Player.getMaxMagic() - Player.getMagic()));
			
			if(item.getConsumableStats()[0] > 0){
				if(item.getConsumableStats()[1] > 0){
					
					Player.setHealth(Player.getHealth() + item.getConsumableStats()[0]);
					Player.setMagic(Player.getMagic() + item.getConsumableStats()[1]);
					
					Text.printLine("It healed ");
					Text.print(healthRestored + " health ", Color.RED);
					Text.print("and ");
					Text.print(magicRestored + " magic", Color.BLUE);
					Text.print(".");
					Text.print("You now have ");
					Text.print(Integer.toString(Player.getHealth()) + "/" + Integer.toString(Player.getMaxHealth()) + " health ", Color.RED);
					Text.print("and ");
					Text.print(Integer.toString(Player.getMagic()) + "/" + Integer.toString(Player.getMaxMagic()) + " magic", Color.BLUE);
					Text.print(".");
				}
				else{
					Player.setHealth(Player.getHealth() + item.getConsumableStats()[0]);
					
					Text.printLine("It healed ");
					Text.print(healthRestored + " health", Color.RED);
					Text.print(".");
					Text.print("You now have ");
					Text.print(Integer.toString(Player.getHealth()) + "/" + Integer.toString(Player.getMaxHealth()) + " health", Color.RED);
					Text.print(".");
				}
			}
			else if(item.getConsumableStats()[1] > 0){
				
				Player.setMagic(Player.getMagic() + item.getConsumableStats()[1]);

				Text.printLine("It healed ");
				Text.print(magicRestored + " magic", Color.BLUE);
				Text.print(".");
				Text.print("You now have ");
				Text.print(Integer.toString(Player.getMagic()) + "/" + Integer.toString(Player.getMaxMagic()) + " magic", Color.BLUE);
				Text.print(".");
			}
			
			
			if(item.hasStatusEffects()){
				
				StatusEffect[] effects = item.getStatusEffects();
				
				for(int i = 0; i < effects.length; i++){
					if(effects[i].getType() != emptyEffect.getType()){
						Player.addStatusEffect(effects[i]);
						
						Text.printLine("The ");
						Text.print(item.getName(), item.getColor());
						Text.print(" gave you a ");
						Text.print(effects[i].getName(), effects[i].isDebuff() ? Color.RED : COLOR_DARK_GREEN);
						Text.print(" effect!");
					}
				}
			}
			
			updatePlayer(true);
			return true;
		}
		else{
			Text.printLine("That item is not for your class.");
			return false;
		}
	}
	
	//Give Player item
	private void giveItem(Item item){
		
		int stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				
				if(Player.getOpenInventorySlots(item) == 0){
					Text.printLine(new String[]{"You do not have enough room.", "Do you want to drop an item?"});
					
					while(true){
						
						int stringChosen2 = Text.testInput(new String[]{"yes", "y", "no", "n"});
						
						switch(stringChosen2){
						case 0: case 1:
							
							if(Player.getOpenInventorySlots(item) == 0){
								removeItemChoice();
							}
							return;
						
						case 2: case 3:
							Text.printLine("Item not picked up.");
							return;
						
						default:
							Text.printLine("Input not recognized, please try again");
							return;
						}
					}
				}
				
				if(Player.getOpenInventorySlots(item) > 0){
					Text.printLine("You picked up the ");
					Text.print(item.getName(), item.getColor());
					Text.print(".");
					Sound.playSound(item.getSound());
					Player.addItemToInventory(item);
					return;
				}
				
				return;
			
			case 2: case 3:
				Text.printLine("Item not picked up.");
				return;
			
			default:
				Text.printLine("Input not recognized, please try again");
				break;
			}
			
			stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Equip main menu
	private void equipItem(String input){
		
		if(input.equals("")){
			Text.printLineExtra(new String[]{"View your currently equipped items or equip new ones?"});
		}
		
		String[] localAcceptedInput = {"view currently equipped", "currently equipped", "view current equips", "current equips", "view current", "view equipped", "view equips", "current", "c",
			"view", "v", "equip new ones", "equip new", "equip", "new", "e", "n", "back", "b"};
		
		int stringChosen = -1;
		
		if(input.equals("")){
			stringChosen = Text.testInput(localAcceptedInput);
		}
		else if(input.equals("view")){
			stringChosen = 0;
		}
		else{
			stringChosen = 11;
		}
		
		while(true){
			switch(stringChosen){
			case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10:
				equipItemExamine();
				return;
			
			case 11: case 12: case 13: case 14: case 15: case 16:
				equipItemSub(input);
				return;
			
			case 17: case 18:
				Text.printLine("Equip cancelled.");
				return;
			
			default:
				Text.printLine("Input not recognized, please try again");
				break;
			}
			
			stringChosen = Text.testInput(localAcceptedInput);
		}
	}
	
	//Equip Examine menu
	private void equipItemExamine(){
		
		Item[] inv = Player.getInventory();
		
		boolean displayText = true;
		
		Text.printLineExtra("Your currently equipped items are:");
		Text.printLine(inv[0].getName(), inv[0].getColor());
		Text.printLine(inv[1].getName(), inv[1].getColor());
		Text.printLine(inv[2].getName(), inv[2].getColor());
		Text.printLine("Type the item name for details.");
		
		String[] input = new String[]{inv[0].getName(), inv[1].getName(), inv[2].getName(), "back", "b"};
		
		int stringChosen = Text.testInput(input);
		
		while(true){
			switch(stringChosen){
			case 0:
				itemInfo(inv[0]);
				displayText = true;
				break;
			
			case 1:
				itemInfo(inv[1]);
				displayText = true;
				break;
			
			case 2:
				itemInfo(inv[2]);
				displayText = true;
				break;
			
			case 3: case 4:
				return;
			
			default:
				Text.printLine("Input not recognized, please try again");
				displayText = false;
				break;
			}

			if(displayText){
				Text.printLineExtra("Your currently equipped items are:");
				Text.printLine(inv[0].getName(), inv[0].getColor());
				Text.printLine(inv[1].getName(), inv[1].getColor());
				Text.printLine(inv[2].getName(), inv[2].getColor());
				Text.printLine("Type the item name for details.");
			}
			
			stringChosen = Text.testInput(input);
		}
	}
	
	//Equip item prompt
	private void equipItemSub(String input){
		
		if(input.equals("")){
			Text.printLineExtra("Equip which item?");
		}
		
		Item[] items = Player.getEquipableItemsInInventory();
		String[] itemNames = new String[items.length + 2];
		
		for(int i = 0; i < items.length; i++){
			
			if(input.equals("")){
				Text.addLine();
				Text.print(items[i].getName(), items[i].getColor());
			}
			
			itemNames[i] = items[i].getName();
		}

		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		
		int itemNum = -1;
		
		if(input.equals("")){
			itemNum = Text.testInput(itemNames);
		}
		else{
			itemNum = Text.testInput(input, itemNames);
			
			if(itemNum == -1){
					Text.printLine("Input not recognized, please try again");
					return;
			}
		}
		
		while(true){
			if(itemNum >= itemNames.length - 2){
				Text.printLine("Equip cancelled.");
				return;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 2){
				if(items[itemNum].getClassType() == Player.getClassType() || items[itemNum].getClassType() == 3){
					incrementUpdateTick = true;
					Item item = items[itemNum];
					
					Player.switchItemsInInventory(Player.getInventory()[item.getType()], item);
					Sound.playSound(item.getSound());
					
					Text.printLine("You equipped your ");
					Text.print(item.getName(), item.getColor());
					Text.print(".");
					updatePlayer(true);
					
					return;
				}
				else{
					Text.printLine("That item is not for your class.");
					return;
				}
			}
			else{
				Text.printLine("Input not recognized, please try again");
			}
			
			itemNum = Text.testInput(itemNames);
		}
	}
	
	//Display item info
	private void itemInfo(Item item){
		
		if(item.getType() == 0 || item.getType() == 1){

			Text.printLineExtra("Your ");
			Text.print(item.getName(), item.getColor());
			Text.print(" has the attack");
			
			if(item.getWeaponAttacks().length > 1){
				Text.print("s");
			}
			
			Text.print(":");
			
			for(int i = 0; i < item.getWeaponAttacks().length; i++){
				
				Attack attack = item.getWeaponAttacks()[i];
				
				String end = ".";
				
				if(attack.hasStatusEffects()){
					end = ",";
				}
				
				if(item.getType() == 0){
					Text.addLine();
					
					Text.printLine(attack.getAttackName(), attack.getColor());
					Text.print(", which does ");
					Text.print((attack.getMinimumDamage() == attack.getMaximumDamage() ? Integer.toString(attack.getMinimumDamage()) :
					Integer.toString(attack.getMinimumDamage()) + "-" +
					Integer.toString(attack.getMaximumDamage())) + " damage ", Color.RED);
					Text.print("with a hit chance of ");
					Text.print(Integer.toString(attack.getHitRate()) + "%", COLOR_GOLD);
					Text.print(end);
				}
				else{
					Text.addLine();

					Text.printLine(attack.getAttackName(), attack.getColor());
					Text.print(", which costs ");
					Text.print(Integer.toString(attack.getUserBasedInt()) + " magic", Color.BLUE);
					Text.print(", and does ");
					Text.print((attack.getMinimumDamage() == attack.getMaximumDamage() ? Integer.toString(attack.getMinimumDamage()) :
					Integer.toString(attack.getMinimumDamage()) + "-" +
					Integer.toString(attack.getMaximumDamage())) + " damage ", Color.RED);
					Text.print("with a hit chance of ");
					Text.print(Integer.toString(attack.getHitRate()) + "%", COLOR_GOLD);
					Text.print(end);
				}
				
				if(attack.hasStatusEffects()){
					
					Text.printLine("and has the effect");
					
					if(attack.getStatusEffects().length > 1){
						Text.print("s");
					}
					
					Text.print(":");
					
					for(int j = 0; j < attack.getStatusEffects().length; j++){
						
						StatusEffect effect = attack.getStatusEffects()[j];
						String effectName = effect.getName();
						
						Text.printLine("Level " + Integer.toString(effect.getLevel()) + " ", COLOR_GOLD);
						Text.print(effectName, effect.isDebuff() ? Color.RED : COLOR_DARK_GREEN);
						Text.print(" on ");
						Text.print((effect.isTargetingPlayer() ?  " yourself" : " the enemy"),
						(effect.isDebuff()^effect.isTargetingPlayer()) ? COLOR_DARK_GREEN : Color.RED);
						Text.print(", lasting " + Integer.toString(effect.getLength()) + " action" + 
						(effect.getLength() == 1 ? "" : "s") +" at a ");
						Text.print(Integer.toString(effect.getChance()) + "% chance", COLOR_GOLD);
						Text.print(".");
					}
				}
			}
		}
		
		if(item.getType() == 2){

			String name = item.getName();
			Color color = item.getColor();
			
			String def = Integer.toString(item.getArmorStats()[0]);
			String hp = Integer.toString(item.getArmorStats()[1]);
			String mp = Integer.toString(item.getArmorStats()[2]);
			
			if(item.getArmorStats()[0] > 0){
				
				if(item.getArmorStats()[1] > 0){
					if(item.getArmorStats()[2] > 0){
						Text.printLineExtra("Your ");
						Text.print(name, color);
						Text.print(" gives you ");
						Text.print(def + " defense", Color.DARK_GRAY);
						Text.print(", ");
						Text.print(hp + " health", Color.RED);
						Text.print(", and ");
						Text.print(mp + " magic", Color.BLUE);
						Text.print(".");
					}
					else{
						Text.printLineExtra("Your ");
						Text.print(name, color);
						Text.print(" gives you ");
						Text.print(def + " defense", Color.DARK_GRAY);
						Text.print(" and ");
						Text.print(hp + " health", Color.RED);
						Text.print(".");
					}
				}
				else if(item.getArmorStats()[2] > 0){
					Text.printLineExtra("Your ");
					Text.print(name, color);
					Text.print(" gives you ");
					Text.print(def + " defense", Color.DARK_GRAY);
					Text.print(" and ");
					Text.print(mp + " magic", Color.BLUE);
					Text.print(".");
				}
				else{
					Text.printLineExtra("Your ");
					Text.print(name, color);
					Text.print(" gives you ");
					Text.print(def + " defense", Color.DARK_GRAY);
					Text.print(".");
				}
			}
			else if(item.getArmorStats()[1] > 0){
				
				if(item.getArmorStats()[2] > 0){
					Text.printLineExtra("Your ");
					Text.print(name, color);
					Text.print(" gives you ");
					Text.print(hp + " health", Color.RED);
					Text.print(" and ");
					Text.print(mp + " magic", Color.BLUE);
					Text.print(".");
				}
				else{
					Text.printLineExtra("Your ");
					Text.print(name, color);
					Text.print(" gives you ");
					Text.print(hp + " health", Color.RED);
					Text.print(".");
				}
			}
			else{
				Text.printLineExtra("Your ");
				Text.print(name, color);
				Text.print(" gives you ");
				Text.print(mp + " magic", Color.BLUE);
				Text.print(".");
			}
		}
		
		if(item.getType() == 3){
			
			int hp = item.getConsumableStats()[0];
			int mp = item.getConsumableStats()[1];
			
			if(hp > 0){
				if(mp > 0){
					Text.printLineExtra("Your ");
					Text.print(item.getName(), item.getColor());
					Text.print(" restores ");
					Text.print(Integer.toString(hp) + " health", Color.RED);
					Text.print(" and ");
					Text.print(Integer.toString(mp) + " magic", Color.BLUE);
					Text.print(".");
				}
				else{
					Text.printLineExtra("Your ");
					Text.print(item.getName(), item.getColor());
					Text.print(" heals ");
					Text.print(Integer.toString(hp) + " health", Color.RED);
					Text.print(".");
				}
			}
			else{
				Text.printLineExtra("Your ");
				Text.print(item.getName(), item.getColor());
				Text.print(" restores ");
				Text.print(Integer.toString(mp) + " magic", Color.BLUE);
				Text.print(".");
			}
			
			if(item.hasStatusEffects()){
				
				if(hp > 0 || mp > 0){
					Text.printLine("It has the status effects:");
				}
				else{
					Text.printLineExtra("Your ");
					Text.print(item.getName(), item.getColor());
					Text.print(" has the status effects:");
				}
				
				for(int i = 0; i < item.getStatusEffectCount(); i++){
					
					StatusEffect effect = item.getStatusEffects()[i];
					
					String effectName = effect.getName();
					
					Text.printLine("Level " + Integer.toString(effect.getLevel()) + " ");
					Text.print(effectName, (effect.isDebuff()^effect.isTargetingPlayer()) ? Color.RED : COLOR_DARK_GREEN);
					Text.print(" on ");
					Text.print((effect.isTargetingPlayer() ?  " yourself " : " the enemy "),
					(effect.isDebuff()^effect.isTargetingPlayer()) ? COLOR_DARK_GREEN : Color.RED);
					Text.print("with a duration of " + Integer.toString(effect.getLength()) + " actions.");
				}
			}
		}
	}
	
	//Shop main menu
	private void shop(String input){
		if(AreaHandler.getArea().getShopkeepers().length < 1){
			Text.printLine("There are no available shops here.");
			return;
		}
		else{

			Shopkeeper[] shops = AreaHandler.getArea().getShopkeepers();
			
			if(input.equals("")){
				Text.printLineExtra("Choose a shop:");
				
				for(int i = 0; i < shops.length; i++){
					Text.printLine(shops[i].getName());
				}
			}
			
			String[] shopNames = new String[shops.length + 2];

			for(int i = 0; i < shops.length; i++){
				shopNames[i] = shops[i].getName();
			}

			shopNames[shopNames.length - 1] = "b";
			shopNames[shopNames.length - 2] = "back";
			
			int shopNum = -1;
			
			if(input.equals("")){
				shopNum = Text.testInput(shopNames);
			}
			else{
				shopNum = Text.testInput(input, shopNames);
				
				if(shopNum == -1){
						Text.printLine("Input not recognized, please try again");
						return;
				}
			}
			
			while(true){
				if(shopNum >= shopNames.length - 2){
					Text.printLine(new String[]{"Shopping cancelled."});
					return;
				}
				else if(shopNum > -1 && shopNum < shopNames.length - 2){
					if(!shops[shopNum].getName().equals("Tradesman")){
						shopSub(shops[shopNum]);
					}
					else{
						shopTrade(shops[shopNum]);
					}
					return;
				}
				else{
					Text.printLine("Input not recognized, please try again");
				}
				
				shopNum = Text.testInput(shopNames);
			}
		}
	}
	
	//Shop menu for specific shopkeeper
	private void shopSub(Shopkeeper shopkeeper){
		
		Item[] items = shopkeeper.getItems();
		int[] itemPrices = shopkeeper.getItemPrices();
		int[] itemCounts = shopkeeper.getItemCounts();
		
		Text.addLine();
		Text.printLine(shopkeeper.getGreetMessage());

		int[] counts = shopkeeper.getItemCounts();
		int[] prices = shopkeeper.getItemPrices();
		
		String[] itemNames = new String[items.length + 4];
		int itemNum = 0;
		
		for(int i = 0; i < items.length; i++){
			if(itemCounts[i] > 0){
				Text.printLine(items[i].getName(), items[i].getColor());
				Text.print(" " + "[" + Integer.toString(counts[i]) + "] ");
				Text.print(Integer.toString(prices[i]) + " gp", COLOR_GOLD);
				
				itemNames[i] = items[i].getName();
				itemNum++;
			}
		}
		
		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		itemNames[itemNames.length - 3] = "n";
		itemNames[itemNames.length - 4] = "no";
		
		itemNum = Text.testInput(itemNames);
		
		while(true){
			if(itemNum >= itemNames.length - 4){
				Text.printLine(shopkeeper.getEndMessage());
				return;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 4){
				if(itemCounts[itemNum] >= 1){
					if(itemCounts[itemNum] == 1)
						shopItemPurchase(items[itemNum], itemPrices[itemNum], 1, shopkeeper);
					else
						shopMultipleItems(items[itemNum], itemPrices[itemNum], itemCounts[itemNum], shopkeeper);
					
					Text.printLine("Anything else you want?");
					
					for(int i = 0; i < items.length; i++){
						if(itemCounts[i] > 0){
							Text.printLine(items[i].getName(), items[i].getColor());
							Text.print(" " + "[" + Integer.toString(counts[i]) + "] ");
							Text.print(Integer.toString(prices[i]) + " gp", COLOR_GOLD);
						}
					}
					
				}
				else{
					Text.printLine("Sorry, we're out of stock on that one.");
				}
			}
			else{
				Text.printLine("What did you say? I might have misunderstood you.");
			}
			
			itemNum = Text.testInput(itemNames);
		}
	}
	
	//Shop menu for buying an item
	private void shopMultipleItems(Item item, int gold, int count, Shopkeeper shopkeeper){
		
		Text.printLineExtra("How many do you want?");
		
		String itemString = Text.testInput();
		int itemCount = -1;
		
		if(itemString.equalsIgnoreCase("back") || itemString.equalsIgnoreCase("b")){
			Text.printLine("Anything else you want?");
			return;
		}
		
		while(itemCount < 1){
			try{
				itemCount = Integer.parseInt(itemString);
			}
			catch(Exception e){
				Text.printLine(new String[]{"That's not a number, or maybe you typed the word instead of the number."});
			}
			
			if(itemCount < 1){
				Text.printLine("Sorry, I can't give you that much.");
			}
		}
		
		if(itemCount > count){
			shopMultipleItemsSub(item, gold, count, shopkeeper);
		}
		else if(itemCount <= count){
			shopItemPurchase(item, gold, itemCount, shopkeeper);
			return;
		}
		
		Text.printLine("Anything else you want?");
		return;
	}
	
	//Shop menu if Player orders more than are available
	private void shopMultipleItemsSub(Item item, int gold, int count, Shopkeeper shopkeeper){
		
		Text.printLine("Sorry, we only have " + Integer.toString(count) + " of those. Do you want all of them?");
		
		int stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
				case 0: case 1:
					shopItemPurchase(item, gold, count, shopkeeper);
					return;
				
				case 2: case 3:
					return;
				
				default:
					Text.printLine("What did you say? I may have misunderstood you.");
					break;
			}
			
			stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Shop menu for confirming purchase
	private void shopItemPurchase(Item item, int gold, int count, Shopkeeper shopkeeper){
		
		if(Player.getOpenInventorySlots(item) < count){
			Text.printLine("You don't have enough inventory space.");
			return;
		}
		
		if(Player.getGold() < (gold*count)){
			Text.printLine("You don't have enough gold.");
			return;
		}
		
		String plural = "";
		
		if(count > 1){
			plural = "s";
		}
		
		Text.printLineExtra("Do you want to buy " + Integer.toString(count) + " ");
		Text.print(item.getName() + plural, item.getColor());
		Text.print(" for ");
		Text.print(Integer.toString(gold*count) + " gold", COLOR_GOLD);
		Text.print("?");
		Text.printYN();
		
		int stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				
				Player.setGold(Player.getGold() - (gold*count));
				
				for(int i = 0; i < count; i++){
					Player.addItemToInventory(item);
					shopkeeper.removeItem(item);
				}
				
				Text.printLine("You bought the ");
				Text.print(item.getName() + plural, item.getColor());
				Text.print(".");
				Sound.playSound("coins01.wav");
				
				return;
				
			case 2: case 3:
				return;
			
			default:
				Text.printLine("Input not recognized, please try again");
				break;
			}
			
			stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Shop menu for Tradesman (Player sells items)
	private void shopTrade(Shopkeeper shopkeeper){
		
		Item[] inv = Player.getInventory();
		
		boolean displayText;
		
		Text.printLineExtra(shopkeeper.getGreetMessage());

		int itemNum = 0;
		
		for(int i = 3; i < inv.length; i++){
			if(!inv[i].getName().equals(emptyItem.getName())){
				Text.printLine(" ");
				Text.print(inv[i].getName(), inv[i].getColor());
				Text.print(" [" + Integer.toString(Player.getItemCountInInventorySlot(i)) + "]");
				itemNum++;
			}
		}
		
		String[] itemNames = new String[itemNum + 4];
		
		for(int i = 3; i < inv.length; i++){
			if(!inv[i].getName().equals(emptyItem.getName())){
				itemNames[i - 3] = inv[i].getName();
			}
		}

		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		itemNames[itemNames.length - 3] = "n";
		itemNames[itemNames.length - 4] = "no";
		
		itemNum = Text.testInput(itemNames);
		
		while(true){
			if(itemNum >= itemNames.length - 4){
				Text.printLine("Have a good day.");
				return;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 2){
				shopTradeSub(inv[itemNum + 3], 0);
				Text.printLine("Anything else you want to sell?");
				displayText = true;
			}
			else{
				Text.printLine("I'm sorry, what did you say?");
				displayText = false;
			}
			
			if(displayText){
				for(int i = 3; i < inv.length; i++){
					if(!inv[i].getName().equals(emptyItem.getName())){
						Text.printLine(" ");
						Text.print(inv[i].getName(), inv[i].getColor());
						Text.print(" [" + Integer.toString(Player.getItemCountInInventorySlot(i)) + "]");
					}
				}
			}
			
			itemNum = Text.testInput(itemNames);
		}
	}
	
	//Shop menu for Tradesman for specific item
	private void shopTradeSub(Item item, int count){
		
		int gold = 0;
		
		double classes = 1;
		
		if(item.getClassType() == 3){
			classes = 1.5;
		}
		
		switch(item.getType()){
			case 0: case 1: // Weapon
				
				for(Attack a:item.getWeaponAttacks()){
					double temp1 = (a.getMinimumDamage() + a.getMaximumDamage())/2;
					double temp2 = a.getHitRate()/85;
					double temp3 = (temp1/2)*temp2;
					
					gold += (int)temp3;
					
					if(a.hasStatusEffects()){
						for(StatusEffect e:a.getStatusEffects()){
							double temp4 = e.getLength();
							double temp5 = e.getLevel();
							double temp6 = e.getChance()/100;
							double temp7 = (temp4*temp5*temp6)/10;
							
							if(item.getClassType() == 0 && item.getType() == 1){// If item is a shield
								temp7 *= 5;
							}
							
							gold += (int)temp7;
						}
					}
				}
				
				gold *= classes;
				
				if(item.getName().toLowerCase().contains("dragon scroll")){
					gold *= 100;
				}
				
				break;
			
			case 2: // Armor
				gold = (int)((item.getArmorStats()[0] + (item.getArmorStats()[1] + item.getArmorStats()[2])/5)*classes*3);
				break;
			
			case 3: // Consumable
				gold = (int)((item.getConsumableStats()[0] + item.getConsumableStats()[1])*(classes/1.5)/4);
				
				if(item.hasStatusEffects()){
					for(int i = 0; i < item.getStatusEffectCount(); i++){
						
						int multiplier = 1;
						
						if(item.getStatusEffects()[i].getType() < 14 || item.getStatusEffects()[i].getType() > 15){
							if(item.getStatusEffects()[i].getType() > 4){
								multiplier = -1;
							}
						}
						
						gold += (item.getStatusEffects()[i].getLevel()*item.getStatusEffects()[i].getLength())*multiplier;
					}
				}
				
				break;
			
			case 4:
				
				break;
		}
		
		if(Player.getInventoryItemCount(item, false) > 1){
			
			Text.printLine("How many do you want to sell?");
			
			String stringChosen = Text.testInput();
			count = -1;
			
			while(count < 0){
				try{
					count = Integer.parseInt(stringChosen);
				}
				catch(Exception e){
					Text.printLine("That isn't a number, or did you type the word instead of the number?");
				}
				
				if(count < 0){
					Text.printLine("You can't sell that many.");
				}
				else if(count > Player.getInventoryItemCount(item, false)){
					Text.printLine("You don't have that many.");
					count = 0;
				}
			}
			
			if(count == 1){
				Text.printLineExtra("Are you sure you want to sell 1 ");
				Text.print(item.getName(), item.getColor());
			}
			else{
				Text.printLineExtra("Are you sure you want to sell " + Integer.toString(count) + " ");
				Text.print(item.getName() + "s", item.getColor());
			}
			
			Text.print(" for ");
			Text.print(Integer.toString(gold) + " gold", COLOR_GOLD);
			Text.print("?");
			Text.printYN();
			Text.printLine("Once sold, you cannot retrieve them.");
		}
		else{
			Text.printLineExtra("Are you sure you want to sell your ");
			Text.print(item.getName(), item.getColor());
			Text.print(" for ");
			Text.print(Integer.toString(gold) + " gold", COLOR_GOLD);
			Text.print("?");
			Text.printYN();
			Text.printLine("Once sold, you cannot retrieve it.");
			count = 1;
		}
		
		int stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				Text.printLine("Item" + (count == 1 ? "" : "s") + " sold.");
				
				for(int i = 0; i < count; i++){
					Player.removeItemFromInventory(item);
					Player.setGold(Player.getGold() + gold);
				}
				
				Sound.playSound("coins01.wav");
				incrementUpdateTick = true;
				return;
			
			case 2: case 3:
				Text.printLine("Item" + (count == 1 ? "" : "s") + " not sold.");
				return;
			
			default:
				Text.printLine("Input not recognized, please try again");
				break;
			}
			
			stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Interact menu
	private void interact(String input){
		
		if(input.equals("")){
			Text.printLineExtra("Interact with what?");
		}
		
		//All area interactions
		Interaction[] intrs = AreaHandler.getArea().getInteractions();
		
		//No. of total intr. names for an intr. in array above plus no. of names for all prior intrs.
		//Used to determine which interaction the Player selects
		int[] intrNameLengthData = new int[intrs.length];
		
		//Total area names
		int count = 0;
		
		for(int i = 0; i < intrs.length; i++){//Set values for name length data array
			count += intrs[i].getNames().length;
			intrNameLengthData[i] = count;
		}
		
		//Array for accepted interaction name strings
		String[] intNames = new String[count + 2];
		
		for(int i = 0; i < intrs.length; i++){//Sets values for that array
			for(int j = 0; j < intrs[i].getNames().length; j++){
				intNames[i + j] = intrs[i].getNames()[j];
			}
		}
		
		//Append cancel strings into arrav above
		intNames[intNames.length - 2] = "b";
		intNames[intNames.length - 1] = "back";
		
		//Test input with array above
		
		//Chosen interaction index
		int intrNum = -1;
		
		if(input.equals("")){
			intrNum = Text.testInput(intNames);
		}
		else{
			intrNum = Text.testInput(input, intNames);
			
			if(intrNum == -1){
					Text.printLine("Input not recognized, please try again");
					return;
			}
		}
		
		while(true){
			if(intrNum >= intNames.length - 2){//Player types cancel or cn
				Text.printLine("Interaction cancelled.");
				return;
			}
			else if(intrNum > -1 && intrNum < intNames.length - 2){//Check if input refers to an interaction
				incrementUpdateTick = true;
				
				for(int i = 0; i < intrNameLengthData.length; i++){
					if(intrNum < intrNameLengthData[i]){
						intrNum = i;
						break;
					}
				}
				
				//Do interaction stuff
				intrs[intrNum].interact();
				Sound.playSound(intrs[intrNum].getSound());
				
				//Give items/Start encounters
				if(intrs[intrNum].getType() == 0){
					giveItem(intrs[intrNum].getItem());
				}
				else if(intrs[intrNum].getType() == 4){
					enemyEncounter(intrs[intrNum].getEnemy());
				}
				
				return;
			}
			else{
				Text.printLine("Input not recognized or interaction unavailable, please try again");
			}
			
			intrNum = Text.testInput(intNames);
		}
	}
	
	//Goto menu
	private void goTo(String input){
		if(Player.hasStatusEffect(7)){
			Text.printLine("You are paralyzed!");
			return;
		}
		
		if(input.equals(""))
			Text.printLineExtra("Where do you want to go?");
		
		Area[] accessibleAreas = AreaHandler.getAccessibleAreas();
		String[] areaNames = new String[accessibleAreas.length + 2];
		
		for(int i = 0; i < accessibleAreas.length; i++){
			if(input.equals(""))
				Text.printLine(accessibleAreas[i].getName(), accessibleAreas[i].getColor());
			areaNames[i] = accessibleAreas[i].getName();
		}
		
		areaNames[areaNames.length - 2] = "b";
		areaNames[areaNames.length - 1] = "back";
		
		int areaNum = -1;
		
		if(input.equals("")){
			areaNum = Text.testInput(areaNames);
		}
		else{
			areaNum = Text.testInput(input, areaNames);
			
			if(areaNum == -1){
					Text.printLine("Input not recognized, please try again");
					return;
			}
		}
		
		Area area = AreaHandler.getArea();
		
		while(true){
			if(areaNum >= areaNames.length - 2){
				Text.printLine("Goto cancelled.");
				return;
			}
			else if(areaNum > -1 && areaNum < areaNames.length - 2){
				if(!accessibleAreas[areaNum].equals(area)){
					Text.printLine("Leaving ");
					Text.print(area.getName(), area.getColor());
					Text.print("...");
					waitSeconds(2, false);
					
					area = accessibleAreas[areaNum];
					AreaHandler.setArea(area);
					
					Text.printLine("You have arrived at ");
					Text.print(area.getName(), area.getColor());
					Text.print(".");
					updatePlayer(true);
					incrementUpdateTick = true;
					
					displayTip();
				}
				else{
					Text.printLine("You are already in ");
					Text.print(area.getName(), area.getColor());
					Text.print(".");
				}
				return;
			}
			else{
				if(input.equals("")){
					Text.printLine("Input not recognized or area currently inaccessable, please try again");
				}
			}
			
			areaNum = Text.testInput(areaNames);
		}
	}
	
	//Options menu
	private void options(String input){
		if(input.equals("")){
			Text.printLineExtra(new String[]{"Type the option name to change it:", "Wait: "});
			Text.print(Settings.waitEnabled() ? "ON" : "OFF", Settings.waitEnabled() ? COLOR_DARK_GREEN : Color.RED);
			Text.printLine("Sound: ");
			Text.print(Settings.soundEnabled() ? "ON" : "OFF", Settings.soundEnabled() ? COLOR_DARK_GREEN : Color.RED);
		}

		int stringChosen = -1;
		
		if(input.equals("")){
			stringChosen = Text.testInput(new String[]{"waiting", "wait", "w", "sounds", "sound", "s", "back", "b"});
		}
		else{
			stringChosen = Text.testInput(input, new String[]{"waiting", "wait", "w", "sounds", "sound", "s", "back", "b"});
			
			if(stringChosen == -1){
				Text.printLine("Input not recognized, please try again");
				return;
			}
		}
		
		while(true){
			switch(stringChosen){
			case 0: case 1: case 2:
				Settings.toggleWait();
				
				Text.printLineExtra("Wait is now ");
				Text.print(Settings.waitEnabled() ? "ON" : "OFF", Settings.waitEnabled() ? COLOR_DARK_GREEN : Color.RED);
				Text.print(".");
				
				Text.printLineExtra(new String[]{"Type the option name to change it:", "Wait: "});
				Text.print(Settings.waitEnabled() ? "ON" : "OFF", Settings.waitEnabled() ? COLOR_DARK_GREEN : Color.RED);
				Text.printLine("Sound: ");
				Text.print(Settings.soundEnabled() ? "ON" : "OFF", Settings.soundEnabled() ? COLOR_DARK_GREEN : Color.RED);
				break;
				
			case 3: case 4: case 5:
				Settings.toggleSound();
				
				Text.printLineExtra("Sound is now ");
				Text.print(Settings.soundEnabled() ? "ON" : "OFF", Settings.soundEnabled() ? COLOR_DARK_GREEN : Color.RED);
				Text.print(".");

				Text.printLineExtra(new String[]{"Type the option name to change it:", "Wait: "});
				Text.print(Settings.waitEnabled() ? "ON" : "OFF", Settings.waitEnabled() ? COLOR_DARK_GREEN : Color.RED);
				Text.printLine("Sound: ");
				Text.print(Settings.soundEnabled() ? "ON" : "OFF", Settings.soundEnabled() ? COLOR_DARK_GREEN : Color.RED);
				break;
				
			case 6: case 7:
				return;
				
			default:
				Text.printLine("Input not recognized, please try again");
				break;
			}
			
			stringChosen = Text.testInput(new String[]{"waiting", "wait", "w", "sounds", "sound", "s", "back", "b"});
		}
	}
	
	//Debug commands menu
	private void executeCommand(boolean displayText){
		
		if(displayText)
			Text.printLineExtra("Input a command to execute (give, set, spawn):");
		
		int stringChosen = Text.testInput(new String[]{"give", "set", "spawn"});
		
		switch(stringChosen){
		case 0:
			if(!Player.isInventoryFull()){
				
				Text.printLineExtra("Enter item name:");
				
				String itemName = Text.testInput();
				boolean found = false;
				
				for(int i = 0; i < ObjectList.getNormalWeaponItemList().length; i++){
					if(ObjectList.getNormalWeaponItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(ObjectList.getNormalWeaponItemList()[i]);
						found = true;
					}
				}
				
				for(int i = 0; i < ObjectList.getSpecialWeaponItemList().length; i++){
					if(ObjectList.getSpecialWeaponItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(ObjectList.getSpecialWeaponItemList()[i]);
						found = true;
					}
				}
				
				for(int i = 0; i < ObjectList.getArmorItemList().length; i++){
					if(ObjectList.getArmorItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(ObjectList.getArmorItemList()[i]);
						found = true;
					}
				}
				
				for(int i = 0; i < ObjectList.getConsumableItemList().length; i++){
					if(ObjectList.getConsumableItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(ObjectList.getConsumableItemList()[i]);
						found = true;
					}
				}
				
				for(int i = 0; i < ObjectList.getMiscItemList().length; i++){
					if(ObjectList.getMiscItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(ObjectList.getMiscItemList()[i]);
						found = true;
					}
				}
				if(!found){
					Text.printLine("Item not found!");
				}
			}
			else{
				Text.printLine("Inventory full.");
			}
			return;
		
		case 1:
			Text.printLineExtra("Enter value to set (hp, mp, gold, inventory, xp, level):");
			
			int stringChosen2 = Text.testInput(new String[]{"hp", "mp", "gold", "inventory", "xp", "level"});

			Text.printLine("Enter amount:");
			
			String stringChosen3 = Text.testInput();
			
			int amount = 1;
			
			try{
				amount = Integer.parseInt(stringChosen3);
			}
			catch(Exception e){}
			
			switch(stringChosen2){
				case 0:
					Player.setMaxHealth(amount);
					Player.setHealth(amount);
					return;
				
				case 1:
					Player.setMaxMagic(amount);
					Player.setMagic(amount);
					return;
				
				case 2:
					Player.setGold(amount);
					return;
				
				case 4:
					Player.setInventorySpace(amount);
					return;
				
				case 5:
					Player.addXP(amount);
					return;
				
				case 6:
					Player.setLevel(amount);
					return;
			}
			return;
		
		case 2:
			
			Text.printLineExtra("Enter enemy name or ID:");
			String stringChosen4 = Text.testInput();
			
			try{
				enemyEncounter(ObjectList.getEnemyList()[Integer.parseInt(stringChosen4)]);
				return;
			}
			catch(NumberFormatException e){
				for(int i = 0; i < ObjectList.getEnemyList().length; i++){
					if(ObjectList.getEnemyList()[i].getEnemyName().toLowerCase().equals(stringChosen4)){
						enemyEncounter(ObjectList.getEnemyList()[i]);
						return;
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException e){
				Text.printLine("Invalid ID!");
				return;
			}
			
			Text.printLine("Enemy not found!");
			return;
			
		default:
			Text.printLine("Input not recognized, please try again");
			executeCommand(false);
			return;
		}
	}
	
	//Enemy fight
	public void enemyEncounter(Enemy enemyToEncounter){
		
		enemy = enemyToEncounter;
		enemy.setHealth(enemy.getMaxHealth());
		
		String enemyName = enemy.getEnemyName();
		
		Text.printLineExtra("You've encountered a");
		
		if(enemyName.startsWith("A") || enemyName.startsWith("E") || enemyName.startsWith("I") || enemyName.startsWith("O") || enemyName.startsWith("U")){
			Text.print("n");
		}
		
		Text.print(" " + enemyName, enemy.getColor());
		Text.print("!");
		
		Sound.playSound(enemy.getSound());
		
		inFight = true;
		
		boolean enemyTurn = false;
		
		if(enemy.attacksFirst()){
			enemyTurn = true;
		}
		
		while(inFight){
			
			if(!enemyTurn){
				updatePlayer(true);
				enemyEncounterPlayerCommands();
			}
			
			if(Player.getHealth() <= 0 || enemy.getHealth() <= 0){
				inFight = false;
				return;
			}
			
			if(enemyTurn){
				
				Attack[] attacks = enemy.getAttacks();
				int attackNum = 0;
				
				if(attacks.length > 1){
					
					boolean[] isAttackSelected = new boolean[attacks.length];
					
					int attacksSelected = 0;
					
					while(attacksSelected == 0){
						for(int i = 0; i < attacks.length; i++){
							isAttackSelected[i] = attacks[i].getRandomAttackChance();
						}
						
						for(int i = 0; i < isAttackSelected.length; i++){
							if(isAttackSelected[i]){
								attacksSelected++;
							}
						}
					}
					
					int chance = 101;
					
					for(int i = 0; i < isAttackSelected.length; i++){
						if(isAttackSelected[i]){
							if(attacks[i].getUserBasedInt() < chance){
								attackNum = i;
							}
							
							chance = attacks[i].getUserBasedInt();
						}
					}
				}
				
				waitSeconds(1, false);
				Text.printLineExtra("The ");
				Text.print(enemy.getEnemyName(), enemy.getColor());
				Text.print(" used ");
				Text.print(attacks[attackNum].getAttackName(), attacks[attackNum].getColor());
				Text.print("!");

				if(Settings.waitEnabled()){
					Sound.playSound(attacks[attackNum].getSound());
				}
				
				waitSeconds(1, false);
				
				if(!attacks[attackNum].getRandomHit()){
					Text.printLine("The attack missed!", COLOR_DARK_GREEN);
				}
				else{
					if(enemy.getRandomHitFail()){
						Text.printLine("The attack failed due to confusion!", COLOR_DARK_GREEN);
					}
					else{
						
						boolean blocked = false;
						
						if(Player.hasStatusEffect(19)){
							int level = Player.getStatusEffectLevel(19);
							
							if(level > 0 && RandomGen.randomChance(level*5)){
								blocked = true;
							}
							
						}
						if(blocked){
							Text.printLine("You blocked the attack!", COLOR_DARK_GREEN);
						}
						else{
							int damage = (int)((attacks[attackNum].getRandomDamage()*enemy.getDamageMultiplier()) - Player.getDefense());
							
							if(damage < 0){
								damage = 0;
							}
							
							if(Player.hasStatusEffect(20)){
								damage *= -(Player.getStatusEffectLevel(20)/20);
							}

							int newLife = Player.getHealth() - damage;
							
							if(newLife < 0){
								newLife = 0;
							}
							
							Player.setHealth(newLife);
	
							if(damage == 0 && attacks[attackNum].getMaximumDamage() != 0){
								Text.printLine("The attack was inaffective!", COLOR_DARK_GREEN);
								Text.printLine("You still have ");
								Text.print(Integer.toString(Player.getHealth()) + "/" + Integer.toString(Player.getMaxHealth()) + " health", Color.RED);
								Text.print(" left!");
							}
							else if(damage < 0){
								Text.printLine("It healed ");
								Text.print(Integer.toString(-damage) + " damage", COLOR_DARK_GREEN);
								Text.print("!");

								Text.printLine("You now have ");
								Text.print(Integer.toString(Player.getHealth()) + "/" + Integer.toString(Player.getMaxHealth()) + " health", Color.RED);
								Text.print(" left!");
							}
							else if(attacks[attackNum].getMaximumDamage() != 0){
								Text.printLine("It dealt ");
								Text.print(Integer.toString(damage) + " damage", Color.RED);
								Text.print("!");
								
								if(newLife > 0){
									Text.printLine("You now have ");
									Text.print(Integer.toString(Player.getHealth()) + "/" + Integer.toString(Player.getMaxHealth()) + " health", Color.RED);
									Text.print(" left!");
									
									Sound.playSound("hurt01.wav");
								}
								else{
									playerDeath();
								}
							}
							
							if(newLife > 0 && attacks[attackNum].hasStatusEffects()){
								StatusEffect effect = attacks[attackNum].getRandomEffect();
								
								if(effect.getType() != emptyEffect.getType()){

									String effectName = effect.getName();
									
									if(effect.isTargetingPlayer()){
										if(!Player.isImmuneToStatusEffect(effect)){
											Player.addStatusEffect(effect);
											
											Text.printLine("You got a ");
											Text.print(effectName, effect.isDebuff() ? Color.RED : COLOR_DARK_GREEN);
											Text.print(" effect!");
										}
										else{
											Text.printLine("You got a ");
											Text.print(effectName, effect.isDebuff() ? Color.RED : COLOR_DARK_GREEN);
											Text.print(" effect, but you are ");
											Text.print("immune", effect.isDebuff() ? COLOR_DARK_GREEN : Color.RED);
											Text.print("!");
										}
									}
									else{
										if(!enemy.isImmuneToStatusEffect(effect)){
											enemy.addStatusEffect(effect);
											
											Text.printLine("The enemy got a ");
											Text.print(effectName, effect.isDebuff() ? COLOR_DARK_GREEN : Color.RED);
											Text.print(" effect!");
										}
										else{
											Text.printLine("The enemy got a ");
											Text.print(effectName, effect.isDebuff() ? COLOR_DARK_GREEN : Color.RED);
											Text.print(" effect, but it is ");
											Text.print("immune", effect.isDebuff() ? Color.RED : COLOR_DARK_GREEN);
											Text.print("!");
										}
										
										if(enemy.getHealth() <= 0){
											Text.printLine("The enemy died due to the effect!", COLOR_DARK_GREEN);
										}
									}
								}
							}
						}
					}
				}
				
				waitSeconds(1, false);
			}
			
			enemyTurn = !enemyTurn;
			
			if(Player.getHealth() <= 0 || enemy.getHealth() <= 0){
				inFight = false;
			}
		}
	}
	
	//Enemy fight Player's turn
	private void enemyEncounterPlayerCommands(){
		
		Text.printLineExtra(new String[]{"What will you do?", "Attack", "Items", "Equip", "Stats", "Examine Enemy", "Run"});
		
		int stringChosen = Text.testInput(new String[]{"attacks", "attack", "a", "items", "item", "i", "equips", "equip", "eq", "stats", "st", "s", "run", "r", "examine enemy", "examine", "enemy", "e"});
		
		if(stringChosen == -1){
			stringChosen = Text.testInput(Text.trimToSpace(Text.getLastInput()), acceptedInput);
		}
		
		boolean displayText = true;
		
		while(true){
			switch(stringChosen){
			case 0: case 1: case 2:
				if(!enemyEncounterPlayerAttack(Text.trimFromSpace(Text.getLastInput()))){
					displayText = true;
					break;
				}
				return;
				
			case 3: case 4: case 5:
				if(!useItem(Text.trimFromSpace(Text.getLastInput()))){
					displayText = true;
					break;
				}
				return;
				
			case 6: case 7: case 8:
				equipItem(Text.trimFromSpace(Text.getLastInput()));
				displayText = true;
				break;
			
			case 9: case 10: case 11:
				printStats();
				displayText = true;
				break;
			
			case 12: case 13:
				if(Player.hasStatusEffect(7)){
					Text.addLine();
					Text.printLine("You cannot run, you are paralyzed!", Color.RED);
					break;
				}
				
				Text.printLineExtra("Attempting to escape...");
				waitSeconds(RandomGen.getInt(1, 2), false);
				
				if(RandomGen.randomChance(enemy.getEscapeRate())){
					Text.printLine("You escaped successfully!", COLOR_DARK_GREEN);
					inFight = false;
				}
				else{
					Text.printLine("You could not escape!", Color.RED);
				}
				
				return;
			
			case 14: case 15: case 16: case 17:
				Text.printLineExtra("The enemy has ");
				Text.print(Integer.toString(enemy.getHealth()) + "/" + Integer.toString(enemy.getMaxHealth()) + " health ", Color.RED);
				Text.print("and ");
				Text.print(Integer.toString(enemy.getDefense()) + " defense", Color.DARK_GRAY);
				Text.print(".");
				
				if(enemy.hasStatusEffects()){
					Text.printLine("The enemy also has these status effects:");
					
					StatusEffect[] effects = enemy.getStatusEffects();
					
					for(int i = 0; i < effects.length; i++){
						if(effects[i].getType() != (emptyEffect.getType())){
							
							String plural = "s";
							
							if(effects[i].getLength() == 1){
								plural = "";
							}
	
							Text.printLine("Level " + effects[i].getLevel() + " ");
							Text.print(effects[i].getName(), effects[i].isDebuff() ? Color.RED : COLOR_DARK_GREEN);
							Text.print(" with " + effects[i].getLength() + " turn" + plural + " remaining");
						}
					}
				}
				
				displayText = true;
				break;
			
			default:
				Text.printLine("Input not recognized or command currently unavailable, please try again");
				displayText = false;
				break;
			}
			
			if(displayText){
				Text.printLineExtra(new String[]{"What will you do?", "Attack", "Items", "Equip", "Stats", "Examine Enemy", "Run"});
			}
			
			stringChosen = Text.testInput(new String[]{"attacks", "attack", "a", "items", "item", "i", "equips", "equip", "eq", "stats", "st", "s", "run", "r", "examine enemy", "examine", "enemy", "e"});
		}
	}
	
	//Enemy fight Player attack menu
	private boolean enemyEncounterPlayerAttack(String input){
		
		if(input.equals("")){
			Text.printLine("Use a normal attack or special attack?");
		}
		
		int stringChosen = -1;
		
		if(input.equals("")){
			stringChosen = Text.testInput(new String[]{"normal attack", "normal", "n", "special attack", "special", "s", "back", "b"});
		}
		else{
			stringChosen = Text.testInput(input, new String[]{"normal attack", "normal", "n", "special attack", "special", "s", "back", "b"});
			
			if(stringChosen == -1){
				Text.printLine("Input not recognized, please try again");
				return false;
			}
		}
		
		while(true){
			switch(stringChosen){
			case 0: case 1: case 2:
				return enemyEncounterPlayerAttackSub(false);
			
			case 3: case 4: case 5:
				return enemyEncounterPlayerAttackSub(true);
			
			case 6: case 7:
				return false;
			
			default:
				Text.printLine("Input not recognized, please try again");
				break;
			}
			
			stringChosen = Text.testInput(new String[]{"normal attack", "normal", "n", "special attack", "special", "s", "back", "b"});
		}
	}
	
	//Enemy fight Player attack submenu (choosing specific attack)
	private boolean enemyEncounterPlayerAttackSub(boolean specialAttack){
		
		int slot = 0;
		
		if(specialAttack)
			slot = 1;
		
		Attack[] attacks = Player.getInventory()[slot].getWeaponAttacks();
		String[] attackNames = new String[attacks.length + 2];
		
		if(specialAttack){
			boolean able = false;
			
			for(Attack a:attacks){
				if(Player.getMagic() >= a.getUserBasedInt())
					able = true;
			}
			
			if(!able){
				Text.printLine("Not enough magic!", Color.RED);
				return false;
			}
		}
		
		int attackNum = -1;
		
		if(attacks.length > 1){
			
			Text.printLineExtra("Which attack do you want to use?");
			
			for(int i = 0; i < attacks.length; i++){
				Text.printLine(" " + attacks[i].getAttackName(), attacks[i].getColor());
			}
			
			for(int i = 0; i < attacks.length; i++){
				attackNames[i] = attacks[i].getAttackName();
			}

			attackNames[attackNames.length - 1] = "b";
			attackNames[attackNames.length - 2] = "back";
			
			while(attackNum == -1){
				attackNum = Text.testInput(attackNames);
				
				if(attackNum >= attackNames.length - 2){
					return false;
				}
				else if(attackNum == -1){
					Text.printLine("Input not recognized, please try again");
				}
			}
		}
		else if(attacks.length == 1){
			attackNum = 0;
		}
		
		if(!specialAttack || (specialAttack && Player.getMagic() >= attacks[attackNum].getUserBasedInt()) && !Player.hasStatusEffect(5)){
			Text.printLineExtra("You used ");
			Text.print(attacks[attackNum].getAttackName(), attacks[attackNum].getColor());
			Text.print("!");
			
			if(Settings.waitEnabled()){
				Sound.playSound(attacks[attackNum].getSound());
			}
			
			if(specialAttack){
				Player.setMagic(Player.getMagic() - attacks[attackNum].getUserBasedInt());
			}
			
			waitSeconds(1, false);
			
			if(!attacks[attackNum].getRandomHit()){
				Text.printLine("Your attack missed!", Color.RED);
				return true;
			}
			else{
				if(Player.getRandomHitFail()){
					Text.printLine("Your attack failed due to confusion!", Color.RED);
					return true;
				}
				else{
					boolean blocked = false;
					
					if(enemy.hasStatusEffect(19)){
						int level = enemy.getStatusEffectLevel(19);
						
						if(level > 0 && RandomGen.randomChance(level*5)){
							blocked = true;
						}
						
					}
					if(blocked){
						Text.printLine("The enemy blocked the attack!", Color.RED);
						return true;
					}
					else{
						int damage = (int)(attacks[attackNum].getRandomDamage()*Player.getDamageMultiplier()) - enemy.getDefense();
						
						if(damage < 0){
							damage = 0;
						}
						
						if(enemy.hasStatusEffect(20)){
							damage *= -((double)(enemy.getStatusEffectLevel(20))/20);
						}
						
						int newLife = enemy.getHealth() - damage;
						
						if(newLife < 0){
							newLife = 0;
						}
						
						enemy.setHealth(newLife);
						
						if(damage == 0 && attacks[attackNum].getMaximumDamage() != 0){
							Text.printLine("The attack was inaffective!", Color.RED);
						}
						else if(damage < 0){
							Text.printLine("It healed ");
							Text.print(Integer.toString(-damage) + " damage", Color.RED);
							Text.print("!");
						}
						else if(attacks[attackNum].getMaximumDamage() != 0){
							Text.printLine("It dealt ");
							Text.print(Integer.toString(damage) + " damage", Color.RED);
							Text.print("!");
							
							if(newLife > 0){
								Text.printLine("The enemy now has ");
								Text.print(Integer.toString(enemy.getHealth()) + "/" + Integer.toString(enemy.getMaxHealth()) + " health", Color.RED);
								Text.print(" left!");
								
								Sound.playSound(enemy.getHitSound());
							}
							else{
								Text.printLineExtra("The " + enemy.getEnemyName() + " was killed!");
								Sound.playSound(enemy.getSound());
								
								int xp = enemy.getRandomXP();
								
								Text.printLine("You got ");
								Text.print(Integer.toString(xp) + " experience", COLOR_DARK_GREEN);
								Text.print("!");
								
								int level = Player.getLevel();
								Player.addXP(xp);
								
								if(level < Player.getLevel()){
									Text.printLine("You grew to ");
									Text.print("level " + Integer.toString(Player.getLevel()), COLOR_GOLD);
									Text.print("!");
								}
	
								int gold = enemy.getRandomGold();
								
								Text.printLine("You got ");
								Text.print(Integer.toString(gold) + " gold", COLOR_GOLD);
								Text.print("!");
								Player.setGold(Player.getGold() + gold);
								
								Item item = enemy.getRandomDrop();
								
								if(!item.getName().equals(emptyItem.getName())){
									Text.printLineExtra("The enemy dropped ");
									Text.print(item.getDisplayName(), item.getColor());
									Text.print(".");
									Text.printLine("Do you want to pick it up?");
									Text.printYN();
									giveItem(item);
								}
								
								lastEnemyKilled = enemy;
								
								return true;
							}
						}
						
						if(newLife > 0 && attacks[attackNum].hasStatusEffects()){
							StatusEffect effect = attacks[attackNum].getRandomEffect();

							String effectName = effect.getName();
							
							if(effect.isTargetingPlayer()){
								if(!Player.isImmuneToStatusEffect(effect)){
									Player.addStatusEffect(effect);
									
									Text.printLine("You got a ");
									Text.print(effectName, effect.isDebuff() ? Color.RED : COLOR_DARK_GREEN);
									Text.print(" effect!");
								}
								else{
									Text.printLine("You got a ");
									Text.print(effectName, effect.isDebuff() ? Color.RED : COLOR_DARK_GREEN);
									Text.print(" effect, but you are ");
									Text.print("immune", effect.isDebuff() ? COLOR_DARK_GREEN : Color.RED);
									Text.print("!");
								}
							}
							else{
								if(!enemy.isImmuneToStatusEffect(effect)){
									enemy.addStatusEffect(effect);
									
									Text.printLine("The enemy got a ");
									Text.print(effectName, effect.isDebuff() ? COLOR_DARK_GREEN : Color.RED);
									Text.print(" effect!");
								}
								else{
									Text.printLine("The enemy got a ");
									Text.print(effectName, effect.isDebuff() ? COLOR_DARK_GREEN : Color.RED);
									Text.print(" effect, but it is ");
									Text.print("immune", effect.isDebuff() ? Color.RED : COLOR_DARK_GREEN);
									Text.print("!");
								}
								
								if(enemy.getHealth() <= 0){
									Text.printLine("The enemy died due to the effect!", COLOR_DARK_GREEN);
								}
							}
						}
						
						return true;
					}
				}
			}
		}
		else if(specialAttack && Player.hasStatusEffect(5)){
			Text.printLine("You are imprisoned!", Color.RED);
			return false;
		}
		else if(specialAttack && Player.getMagic() < attacks[attackNum].getUserBasedInt()){
			Text.printLine("Not enough magic!", Color.RED);
			return false;
		}
		
		return false;
	}
	
	
	//Extra functions
	public void updatePlayer(boolean updateStatusEffects){
		
		Player.updatePlayer();
		
		if(!Player.isPlayerAlive()){
			playerDeath();
		}
		
		if(updateStatusEffects){
			updatePlayerStatusEffects();
		}
	}
	
	public void updatePlayerStatusEffects(){
		
		StatusEffect[] endedEffects = Player.updateStatusEffects(true);
		
		for(int i = 0; i < Player.getStatusEffects().length; i++){
			if(endedEffects[i].getType() != emptyEffect.getType()){
				
				String effectName = endedEffects[i].getName();
				
				Text.printLineExtra("Your " + effectName + " effect wore off!");
			}
		}
	}
	
	public void playerDeath(){
		Text.printLineExtra(new String[]{"You have died!", "Exiting game. You can re-open and load from a previous save."});
		Sound.playSound("death01.wav");
		waitSeconds(6, true);
		System.exit(0);
	}
	
	public void printStats(){
		Text.printLineExtra("You have ");
		Text.print(Integer.toString(Player.getHealth()) + "/" + Integer.toString(Player.getMaxHealth()) + " health", Color.RED);
		Text.print(", ");
		Text.print(Integer.toString(Player.getMagic()) + "/" + Integer.toString(Player.getMaxMagic()) + " magic", Color.BLUE);
		Text.print(", and ");
		Text.print(Integer.toString(Player.getDefense()) + " defense", Color.DARK_GRAY);
		Text.print(".");
		
		if(Player.hasStatusEffects()){
			Text.printLine("You also have these status effects:");
			
			StatusEffect[] effects = Player.getStatusEffects();
			
			for(int i = 0; i < effects.length; i++){
				if(effects[i].getType() != (emptyEffect.getType())){
					String plural = "s";
					
					if(effects[i].getLength() == 1){
						plural = "";
					}
					
					Text.printLine("Level " + effects[i].getLevel() + " ");
					Text.print(effects[i].getName(), effects[i].isDebuff() ? Color.RED : COLOR_DARK_GREEN);
					Text.print(" with " + effects[i].getLength() + " turn" + plural + " remaining");
				}
			}
		}
	}
	
	private void waitSeconds(float seconds, boolean ignoreWaitEnabled){
		
		int milliseconds = (int)(seconds*1000);
		
		try{
			if(ignoreWaitEnabled){
				TimeUnit.MILLISECONDS.sleep(milliseconds);
			}
			else if(Settings.waitEnabled() && !ignoreWaitEnabled){
				TimeUnit.MILLISECONDS.sleep(milliseconds);
			}
			
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	
	private void displayTip(){
		Text.addLine();
		Text.printLine("-TIP-: " + tips[RandomGen.getInt(tips.length)], new Color(0, 128, 0));
	}
	
	//Getters and setters used by GameHandler
	
	public boolean incrementUpdateTick(){
		return incrementUpdateTick;
	}
	
	public Save getSave(){
		return save;
	}
	
	public void setSave(Save save){
		this.save = save;
	}
	
	public boolean getRandomizeEncounter(){
		return randomizeEncounter;
	}
	
	public void setRandomizeEncounter(boolean randomizeEncounter){
		this.randomizeEncounter = randomizeEncounter;
	}
	
	public void setTutorialComplete(boolean tutorialComplete){
		this.tutorialComplete = tutorialComplete;
	}
	
	public void updateRestCounter(){
		
		restCounter--;
		
		if(restCounter < 0){
			restCounter = 0;
		}
	}
	
	public Enemy getLastEnemyKilled(){
		return lastEnemyKilled;
	}
}
