package game;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class CommandHandler{
	
	private final String[] acceptedInput = {
		"help",
		"hp",
		
		"inventory",
		"items",
		"inv",
		
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
		"i",
		
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
		"When entering a new area, look then try interacting with things you find.",
		"Use an item during a battle to recover your stats.",
		"Examine an enemy to see its status effects."};
	
	private Text text = new Text();
	private ObjectList list = new ObjectList();
	
	private Player player = new Player();
	private Enemy enemy = list.getEnemyList()[0];
	private Enemy lastEnemyKilled;
	
	private AreaHandler areaHandler;
	
	private Save save = new Save();
	private Random random = new Random();
	private Sound sound = new Sound();
	
	private Window window;
	
	private boolean incrementUpdateTick;
	private boolean inFight = false;
	
	private boolean waitEnabled;
	private boolean soundEnabled;
	private boolean debugMode = false;
	
	private boolean randomizeEncounter = true;
	private boolean tutorialComplete = false;
	
	private int restCounter = 0;

	private final Item emptyItem = new Item();
	private final StatusEffect emptyEffect = new StatusEffect();
	
	private final Color DARK_GREEN = new Color(0, 128, 0), GOLD = new Color(96, 96, 0);
	
	public void commands(){
		
		text.printTextAddLine("Input a command:");
		
		int stringChosen = text.testInput(acceptedInput);
		incrementUpdateTick = false;
		randomizeEncounter = true;
		updatePlayer(false);
		
		if(stringChosen == -1){
			stringChosen = text.testInput(text.trimToSpace(text.getLastInput()), acceptedInput);
		}
		
		switch(stringChosen){
		case 0: case 1:
			helpMenu(text.trimFromSpace(text.getLastInput()));
			break;
		
		case 2: case 3: case 4:
			inventory(text.trimFromSpace(text.getLastInput()));
			break;
		
		case 5: case 6:
			text.printTextAddLine("You are currently in ");
			text.print(areaHandler.getArea().getName(), areaHandler.getArea().getColor());
			text.print(".");
			break;
		
		case 7: case 8: case 9: case 10: case 11:
			useItem(text.trimFromSpace(text.getLastInput()));
			break;
		
		case 12: case 13:
			equipItem(text.trimFromSpace(text.getLastInput()));
			break;
		
		case 14: case 15:
			printStats();
			break;
		
		case 16: case 17: case 18:
			shop(text.trimFromSpace(text.getLastInput()));
			break;
		
		case 19: case 20:
			text.printTextAddLine("Looking around...");
			waitSeconds(1, false);
			text.printText(areaHandler.getArea().getSurroundings());
			incrementUpdateTick = true;
			break;
		
		case 21: case 22:
			interact(text.trimFromSpace(text.getLastInput()));
			break;
		
		case 23: case 24:
			text.printTextAddLine("Your map:");
			
			Area[] accessibleAreas = areaHandler.getAccessibleAreas();
			
			for(int i = 0; i < accessibleAreas.length; i++){
				text.printText(accessibleAreas[i].getName(), accessibleAreas[i].getColor());
			}
			
			break;
		
		case 25: case 26: case 27:
			text.printTextAddLine("You have ");
			text.print(Integer.toString(player.getGold()) + " gold", GOLD);
			text.print(".");
			break;
		
		case 28: case 29: case 30: case 31:
			goTo(text.trimFromSpace(text.getLastInput()));
			break;
		
		case 32: case 33: case 34:
			options(text.trimFromSpace(text.getLastInput()));
			break;
			
		case 35: case 36:
			if(debugMode){
				executeCommand(true);
			}
			else{
				text.printText("Input not recognized, please try again");
			}
			break;
		
		case 37: case 38:
			text.printTextAddLine("You are ");
			text.print("level " + Integer.toString(player.getLevel()), GOLD);
			text.print(" with ");
			text.print(Integer.toString(player.getXP()) + "/" + Integer.toString(player.getXPReq()) + " XP (" + Integer.toString(player.getXPReq() - player.getXP()) + ")", DARK_GREEN);
			text.print(" remaining.");
			break;
		
		case 39: case 40:
			text.printText("Saving...");
			if(!debugMode){
				save.setTutorialComplete(tutorialComplete);
				save.save(save.getSaveLoaded(), save.getSaveName(), areaHandler.getArea(), areaHandler.getAccessibleAreas(), player);
			}
			text.printText("Game saved.");

			displayTip();
			
			break;
		
		case 41: case 42:
			text.printText("Hunting...");
			waitSeconds(random.nextInt(1) + 1, false);
			randomizeEncounter = false;
			
			if(areaHandler.getArea().getEncounters().length == 0){
				text.printText("You couldn't find any enemies.");
			}
			
			incrementUpdateTick = true;
			break;
		
		case 43: case 44:
			
			if(restCounter <= 0){
				text.printText("Resting...");
				waitSeconds(2, false);
				
				for(int i = 0; i < 3; i++){
					player.updateStats();
				}
				
				restCounter = 5;
				incrementUpdateTick = true;
			}
			else{
				text.printText("You have just rested, you cannot rest again.");
			}
			
			break;
		case 45: case 46:
			text.printTextAddLine("Save before exiting?");
			text.printYN();
			
			boolean answered = false;
			
			while(!answered){
				stringChosen = text.testInput(new String[]{"yes", "y", "no", "n", "back", "b"});
				
				switch(stringChosen){
				case 0: case 1:
					text.printText("Saving...");
					if(!debugMode){
						save.setTutorialComplete(tutorialComplete);
						save.save(save.getSaveLoaded(), save.getSaveName(), areaHandler.getArea(), areaHandler.getAccessibleAreas(), player);
					}
					text.printText("Game saved.");
					
					System.exit(0);
				case 2: case 3:
					System.exit(0);
				case 4: case 5:
					break;
				default:
					text.printText("Input not recognized, please try again");
					break;
				}
			}
			
			break;
		default:
			text.printText("Input not recognized, please try again");
			break;
		}
		
		updatePlayer(false);
	}
	
	//Help main menu
	private void helpMenu(String input){
		
		if(input.equals("")){
			text.printTextAddLine(new String[]{"What do you need help with?", "Commands", "Status Effects", "Dragon Scrolls", "Gameplay", "Combat", "back"});
		}
		
		int stringChosen = -1;
		
		if(input.equals("")){
			stringChosen = text.testInput(new String[]{"commands", "command", "status effects", "status effect", "status", "effects", "effect", "dragon scrolls", "dragon", "dscrolls", "scrolls", "scroll", "gameplay", "game", "play", "combat", "back", "b"});
		}
		else{
			stringChosen = text.testInput(input, new String[]{"commands", "command", "status effects", "status effect", "status", "effects", "effect", "dragon scrolls", "dragon", "dscrolls", "scrolls", "scroll", "gameplay", "game", "play", "combat", "back", "b"});
			
			if(stringChosen == -1){
				text.printText("Input not recognized, please try again");
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
				text.addLine();
				text.printText("Buffs", DARK_GREEN);
				text.print(":");
				text.printText(new String[]{"Regeneration: Increases the user's health over time.", "Magic Regeneration: Increases the user's magic over time.", "Defense Buff: Increases the user's defense.", "Strength: Increases the user's damage output.",
				"Health/Magic Max Increase: Temporarily increases your maximum health/magic.", "Barrier: The user has a chance to completly block incoming attacks.", "Absorption: Heals instead of taking damage."});
				text.addLine();
				text.printText("Debuffs", Color.RED);
				text.print(":");
				text.printText(new String[]{"Imprison: Disables special attacks.", "Lock: Disables item use.", "Paralysis: Cannot go to different locations or run from battles.", "Bleed and Burn: Decreases the target's health over time.",
				"Poison and Magic Steal: Decreases magic over time.", "Weakness: Decreases the target's damage output.", "Defense Break: Decreases the target's defense.", "Health/Magic Max Decrease: Temporarily decreases your maximum health/magic.",
				"Confusion: The target has a chance to fail an attack."});
				displayText = true;
				break;
			
			case 6: case 7: case 8: case 9: case 10: case 11:
				text.addLine();
				text.printText("The");
				text.print("Dragon Scrolls", list.DRAGON);
				text.print(" are very rare, special spells with extreme power.");
				text.printText(new String[]{"Each is made for knights, archers, or mages, and are comprised of magic used by dragons.", "Thus, they are the guardians of these spells. " +
				"They were created by an ancient guild of mages, for adventurers.", "They realized their potential, and summoned six dragons, each in a respective mountain, to protect the scrolls.", "Those who defeat these dragons prove themselves worthy of " +
				"the scrolls and, therefore, obtain their power.", "", "The list of dragons is as follows:"});
				text.printText(new String[]{"Dragon: Dragonstone Mountain", "Inorexite Dragon: Monolith Mountain", "Earth Core Dragon: Earthkey Mountain", "Fire Hydra: Hydra Hell Castle",
				"Black Void Dragon: Void Mountain", "Nova Dragon: Azure Star Mountain"}, list.DRAGON);
				text.printTextAddLine("The list of Dragon Scrolls is as follows:");
				text.printText(new String[]{"Dragon Scroll I: Magic Arrow Storm", "Dragon Scroll II: Cosmic Speed Quiver", "Dragon Scroll III: Arcane Summoning Sword",
				"Dragon Scroll IV: Colossal Buffer Shield", "Dragon Scroll V: Ancient Spells of Wizardry", "Dragon Scroll VI: Geological Destruction Spell", "Dragon Scroll VII: Solar Flame Spell", "Dragon Scroll VIII: Infinity Banishment Spell",
				"Dragon Scroll IX: Magic Singularity Spell"}, list.DRAGON);
				displayText = true;
				break;
			
			case 12: case 13: case 14:
				text.addLine();
				text.printText(new String[]{"The general goal of the game is simply to advance to new areas, level up, and find new items.", "There is also the task of defeating the "});
				text.print("Dragon God", list.GOD);
				text.print(" which will reward you with something very special.");
				text.printText("If you wish to get to the ");
				text.print("Dragon God", list.GOD);
				text.print(", you'll have to progress far enough until you can confront him and prove yourself in battle.");
				text.printTextAddLine(new String[]{"Syntax:", "Commands are not case sensitive. Some commands, such as ones that have attacks, items, or " +
				"interactions, require you to type the full name of the selection.", "In some cases, such as goto or equip, you can type the command and extra input in the same command (e.g. equip dragon scroll i)."});
				displayText = true;
				break;
			
			case 15:
				text.addLine();
				text.printText(new String[]{"Combat in the game is turn-based.", "Some stronger enemies will attack before you have the chance to, but you will be able to attack weaker ones first.", "", "When fighting, you can choose from a list " +
				"of commands. These include:", "Attack: Use an attack from your normal or special weapon.", "Items: Use an item in your inventory.", "Equip: Equip an item in your inventory.", "Stats: View your HP, MP, and defense.", "Examine Enemy: View the enemy's HP " +
				", defense, and status effects.", "Run: Attempt to escape from the battle.", "Attacking, using an item, or attempting to run will use your turn and allow the enemy to attack.", "", "Upon killing an enemy, you will recieve some XP and gold, along with any items the enemy may " +
				"have dropped."});
				displayText = true;
				break;
			
			case 16: case 17:
				text.printText("Exited the help menu.");
				return;
			
			default:
				text.printText("Input not recognized, please try again");
				displayText = false;
				break;
			}
			
			if(displayText){
				text.printTextAddLine(new String[]{"What do you need help with?", "Commands", "Status Effects", "Dragon Scrolls", "Gameplay", "Combat", "back"});
			}
			
			stringChosen = text.testInput(new String[]{"commands", "command", "status effects", "status effect", "status", "effects", "effect", "dragon scrolls", "dragon", "dscrolls", "scrolls", "scroll", "gameplay", "game", "play", "combat", "back", "b"});
		}
	}
	
	//Help Commands menu
	private void helpMenuCommands(){
		text.printTextAddLine("Would you like a list of commands or a detailed description?");
		
		int stringChosen = text.testInput(new String[]{"list of commands", "list of command", "commands list", "command list", "list", "commands", "command", "detailed description", "detail description", "detailed desc", "detail desc", "detailed", "details", "detail", "description", "desc", "back", "b"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
				text.printText(new String[]{"", " Help/hp", " Inventory/inv/i", " Area/a", " Use/u", " Equip/e", " Stats/st", " Shop/s", " Look/l", " Interact/i", " Map/m", " Gold/gp", " Goto/g", " Options/o", " XP/x", " Save/sv", " Hunt/h", " Rest/r", "Exit/Quit", "Exited the help menu."});
				return;
			
			case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16:
				helpMenuCommandsSub();
				return;
			
			case 17: case 18:
				return;
			
			default:
				text.printText("Input not recognized, please try again");
				break;
			}
			
			stringChosen = text.testInput(new String[]{"list of commands", "list of command", "commands list", "command list", "list", "commands", "command", "detailed description", "detail description", "detailed desc", "detail desc", "detailed", "details", "detail", "description", "desc", "back", "b"});
		}
	}
	
	//Help Commands (specific) menu
	private void helpMenuCommandsSub(){
		
		text.printTextAddLine("Type a command and a description will be shown.");
		
		String[] input = new String[acceptedInput.length + 2];
		
		for(int i = 0; i < acceptedInput.length; i++){
			input[i] = acceptedInput[i];
		}
		
		input[input.length - 1] = "b";
		input[input.length - 2] = "back";
		
		int stringChosen = text.testInput(input);
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				text.printText("Help/hp: Displays the help menu.");
				break;
				
			case 2: case 3: case 4:
				text.printText("Inventory/inv/in: Displays your inventory.");
				break;
				
			case 5: case 6:
				text.printText("Area/a: Displays your current location.");
				break;
				
			case 7: case 8: case 9: case 10: case 11:
				text.printText("Use/u: Use an item in your inventory.");
				break;
				
			case 12: case 13:
				text.printText("Equip/eq: Shows your equipped items, or equip different items.");
				break;
				
			case 14: case 15:
				text.printText("Stats/st: Displays your health, magic, and defense.");
				break;
				
			case 16: case 17: case 18:
				text.printText("Shop/s: Buy items at a shop when available.");
				break;
				
			case 19: case 20:
				text.printText("Look/l: Observes your surroundings.");
				break;
				
			case 21: case 22:
				text.printText("Interact/i: Interact with your surroundings.");
				break;
				
			case 23: case 24:
				text.printText("Map/m: View accessible areas on the region map.");
				break;
				
			case 25: case 26: case 27:
				text.printText("Gold/gp: View your current gold.");
				break;
				
			case 28: case 29: case 30: case 31:
				text.printText("Goto/g: Go to a specified location.");
				break;
				
			case 32: case 33: case 34:
				text.printText("Options/o: Menu for enabling sound, waiting, and other options.");
				break;
				
			case 37: case 38:
				text.printText("XP/x: Shows your current level and experience.");
				break;
				
			case 39: case 40:
				text.printText("Save/sv: Saves your game if possible.");
				break;
				
			case 41: case 42:
				text.printText("Hunt/h: Hunts for an enemy.");
				break;
				
			case 43: case 44:
				text.printText("Rest/r: Rests to regain your stats.");
				break;
				
			case 45: case 46:
				text.printText("Exit/Quit: Prompts to save then exits the game.");
				break;
				
			case 47: case 48:
				text.printTextAddLine("Exited the help menu.");
				return;
				
			default:
				text.printText("Input not recognized, please try again");
				break;
			}
			
			stringChosen = text.testInput(input);
		}
	}
	
	//Inventory main menu
	private void inventory(String input){
		
		Item[] inv = player.getInventory();
		
		if(input.equals("")){
			text.printTextAddLine("Your Inventory (" + Integer.toString(player.getOpenInventorySlots()) + "/" + Integer.toString(player.getInventory().length - 3) + ") Slots full:");
			
			for(int i = 3; i < inv.length; i++){
				if(!inv[i].getName().equals(emptyItem.getName())){
					text.printText(" ");
					text.print(inv[i].getName(), inv[i].getColor());
					text.print(" [" + Integer.toString(player.getItemCountInInventorySlot(i)) + "]");
				}
			}
			text.printText("Type an item name for more options.");
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
			itemNum = text.testInput(itemNames);
		}
		else{
			itemNum = text.testInput(input, itemNames);
			
			if(itemNum == -1){
					text.printText("Input not recognized, please try again");
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
				text.printText("Input not recognized, please try again");
				displayText = false;
			}
			
			if(displayText){
				text.printTextAddLine("Your Inventory (" + Integer.toString(player.getOpenInventorySlots()) + "/" + Integer.toString(player.getInventory().length - 3) + ") Slots full:");
				
				for(int i = 3; i < inv.length; i++){
					if(!inv[i].getName().equals(emptyItem.getName())){
						text.printText(" ");
						text.print(inv[i].getName(), inv[i].getColor());
						text.print(" [" + Integer.toString(player.getItemCountInInventorySlot(i)) + "]");
					}
				}
				text.printText("Type an item name for more options.");
			}
			
			itemNum = text.testInput(itemNames);
		}
	}
	
	//Inventory selected item menu
	private void inventorySub(Item item){
		
		text.printTextAddLine("Do what with ");
		text.print(item.getDisplayName() + "?", item.getColor());
		text.printText(new String[]{"Use", "Equip", "Stats", "Drop"});
		
		int stringChosen = text.testInput(new String[]{"use", "u", "equip", "eq", "e", "stats", "st", "s", "drop", "d", "back", "b"});
		
		boolean displayText = true;
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				
				if(displayText){
					text.printTextAddLine("Do what with ");
					text.print(item.getDisplayName() + "?", item.getColor());
					text.printText(new String[]{"Use", "Equip", "Stats", "Drop"});
				}
				
				if(item.canUse()){
					if(player.hasStatusEffect(6)){
						text.printText("You are under the influence of a lock effect.");
						displayText = true;
						break;
					}
					
					useItem(item);
					return;
				}
				else{
					text.printText("That item cannot be used.");
					displayText = true;
					break;
				}
			
			case 2: case 3: case 4:
				
				if(item.getType() >= 0 && item.getType() <= 2){
					if(item.getClassType() == player.getClassType() || item.getClassType() == 3){
						
						player.switchItemsInInventory(player.getInventory()[item.getType()], item);
						sound.playSound(item.getSound());
						
						text.printText("You equipped your ");
						text.print(item.getName(), item.getColor());
						text.print(".");
						updatePlayer(true);
						
						incrementUpdateTick = true;
						return;
					}
					else{
						text.printText("That item is not for your class.");
						displayText = true;
						break;
					}
				}
				else{
					text.printText("That item cannot be equipped.");
					displayText = true;
					break;
				}
			
			case 5: case 6: case 7:
				
				if(item.getType() >= 0 && item.getType() <= 3){
					itemInfo(item);
					return;
				}
				else{
					text.printText("That item has no stats.");
					displayText = true;
					break;
				}
			
			case 8: case 9:
				removeItem(item, 0);
				return;
			
			case 10: case 11:
				return;
			
			default:
				text.printText("Input not recognized, please try again");
				displayText = false;
				break;
			}
			
			if(displayText){
				text.printTextAddLine("Do what with ");
				text.print(item.getDisplayName() + "?", item.getColor());
				text.printText(new String[]{"Use", "Equip", "Stats", "Drop"});
			}
			
			stringChosen = text.testInput(new String[]{"use", "u", "equip", "eq", "e", "stats", "st", "s", "drop", "d", "back", "b"});
		}
	}
	
	//Drops selected item
	private void removeItemChoice(){
		
		text.printTextAddLine("Drop which item?");
		
		
		Item[] inv = player.getInventory();
		String[] itemNames = new String[inv.length + 2];
		
		for(int i = 0; i < inv.length; i++){
			
			for(int j = 3; j < inv.length; j++){
				if(!inv[j].getName().equals(emptyItem.getName())){
					text.printText(" ");
					text.print(inv[j].getName(), inv[j].getColor());
					text.print(" [" + Integer.toString(player.getItemCountInInventorySlot(j)) + "]");
				}
			}
			
			itemNames[i] = inv[i].getName();
		}

		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		
		int itemNum = text.testInput(itemNames);
		
		while(true){
			if(itemNum >= itemNames.length - 2){
				text.printText("Item drop cancelled.");
				return;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 2){
				removeItem(inv[itemNum], 1);
				sound.playSound(inv[itemNum].getSound().replace("items/", "items/drop/"));
				return;
			}
			else{
				text.printText("Input not recognized, please try again");
			}
			
			itemNum = text.testInput(itemNames);
		}
	}
	
	//Drops item(s) in parameters
	private void removeItem(Item item, int count){
		if(player.getInventoryItemCount(item, false) > 1){
			
			boolean displayText = true;
			
			while(true){
				if(displayText){
					text.printTextAddLine("How many do you want to drop?");
					displayText = false;
				}
				
				String stringChosen = text.testInput();
				
				try{
					count = Integer.parseInt(stringChosen);
				}
				catch(Exception e){
					text.printText("Input is not a number or is in text form. Please try again.");
					displayText = false;
				}
				
				if(count > player.getInventoryItemCount(item, false) || count < 1){
					text.printText("You cannot drop that many.");
					displayText = true;
				}
				else{
					text.printTextAddLine("Are you sure you want to drop " + Integer.toString(count) + " ");
					text.print(item.getName(), item.getColor());
					text.print("s?");
					text.printYN();
					text.printText("Once dropped, you cannot retrieve them.");
					break;
				}
			}
		}
		else{
			text.printTextAddLine("Are you sure you want to drop your ");
			text.print(item.getName(), item.getColor());
			text.print("?");
			text.printYN();
			text.printText("Once dropped, you cannot retrieve it.");
			count = 1;
		}
		
		int stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				if(count == 1){
					text.printText("Item dropped.");
				}
				else{
					text.printText("Items dropped.");
				}
				
				for(int i = 0; i < count; i++){
					player.removeItemFromInventory(item);
				}
				
				sound.playSound(item.getSound().replace("items/", "items/drop/"));
				return;
			
			case 2: case 3:
				if(count == 1){
					text.printText("Item not dropped.");
				}
				else{
					text.printText("Items not dropped.");
				}
				return;
			
			default:
				text.printText("Input not recognized, please try again");
				break;
			}
			
			stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Use item menu
	private boolean useItem(String input){
		
		if(player.hasStatusEffect(6)){
			text.printText("You are under the influence of a lock effect.");
			return false;
		}
		
		if(input.equals("")){
			text.printTextAddLine("Use which item?");
		}
		
		Item[] items = player.getUsableItemsInInventory();
		String[] itemNames = new String[items.length + 2];
		
		for(int i = 0; i < items.length; i++){
			
			if(input.equals("")){
				text.printText(" ");
				text.print(items[i].getName(), items[i].getColor());
				text.print(" [" + Integer.toString(player.getUsableItemCountInInventory()[i]) + "]");
			}
			
			itemNames[i] = items[i].getName();
		}

		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		int itemNum = -1;
		
		if(input.equals("")){
			itemNum = text.testInput(itemNames);
		}
		else{
			itemNum = text.testInput(input, itemNames);
			
			if(itemNum == -1){
					text.printText("Input not recognized, please try again");
					return false;
			}
		}
		
		while(true){
			if(itemNum >= itemNames.length - 2){
				text.printText("Item use cancelled.");
				return false;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 2){
				boolean tmp = useItem(items[itemNum]);
				incrementUpdateTick = tmp;
				
				return tmp;
			}
			else{
				text.printText("Input not recognized, please try again");
			}
			
			itemNum = text.testInput(itemNames);
		}
	}
	
	//Use item function
	private boolean useItem(Item item){
		
		if(!item.canUse()){
			text.printText("That item cannot be used.");
			return false;
		}
		
		if(item.getType() == 4){
			int itemNum = text.testInput(item.getName(), new String[]{list.getMiscItemList()[0].getName()});
			
			switch(itemNum){
			case 0:
				if(areaHandler.isAreaAccessible(list.getAreaList()[4])){
					text.printTextAddLine("You have already unlocked ");
					text.print(list.getAreaList()[4].getName(), list.getAreaList()[4].getColor());
					text.print(".");
					return false;
				}
				else{
					
					int count = player.getItemCountInInventory(list.getMiscItemList()[0]);
					
					if(count < 5){
						text.printTextAddLine(new String[]{"You don't have enough keys to unlock the castle.", "You need " + Integer.toString(5 - count) + " more."});
						return false;
					}
					else{
						for(int i = 0; i < 5; i++){
							player.removeItemFromInventory(list.getMiscItemList()[0]);
						}
						
						areaHandler.addAccessibleArea(list.getAreaList()[4]);
						
						text.printTextAddLine("You can now access ");
						text.print(list.getAreaList()[4].getName(), list.getAreaList()[4].getColor());
						text.print("!");
						
						return true;
					}
				}
			}
			
			return false;
		}
		
		if(!item.hasStatusEffects()){
			if(item.getConsumableStats()[0] > 0 && player.getHealth() == player.getMaxHealth()){
				if(item.getConsumableStats()[1] > 0 && player.getMagic() == player.getMaxMagic()){
					text.printTextAddLine("You did not use ");
					text.print(item.getDisplayName(), item.getColor());
					text.print(" because you already have full ");
					text.print("health", Color.RED);
					text.print(" and ");
					text.print("magic", Color.BLUE);
					text.print(".");
					
					return false;
				}
				text.printTextAddLine("You did not use ");
				text.print(item.getDisplayName(), item.getColor());
				text.print(" because you already have full ");
				text.print("health", Color.RED);
				text.print(".");
				
				return false;
			}
			else if(item.getConsumableStats()[1] > 0 && player.getMagic() == player.getMaxMagic()){
				text.printTextAddLine("You did not use ");
				text.print(item.getDisplayName(), item.getColor());
				text.print(" because you already have full ");
				text.print("magic", Color.BLUE);
				text.print(".");
				
				return false;
			}
		}
		
		if(item.getClassType() == player.getClassType() || item.getClassType() == 3){

			player.removeItemFromInventory(item);
			sound.playSound(item.getUseSound());
			
			text.printTextAddLine("You used ");
			text.print(item.getDisplayName(), item.getColor());
			text.print(".");
			incrementUpdateTick = true;
			
			String healthRestored = Integer.toString(Math.min(item.getConsumableStats()[0], player.getMaxHealth() - player.getHealth()));
			String magicRestored = Integer.toString(Math.min(item.getConsumableStats()[1], player.getMaxMagic() - player.getMagic()));
			
			if(item.getConsumableStats()[0] > 0){
				if(item.getConsumableStats()[1] > 0){
					
					player.setHealth(player.getHealth() + item.getConsumableStats()[0]);
					player.setMagic(player.getMagic() + item.getConsumableStats()[1]);
					
					text.printText("It healed ");
					text.print(healthRestored + " health ", Color.RED);
					text.print("and ");
					text.print(magicRestored + " magic", Color.BLUE);
					text.print(".");
					text.print("You now have ");
					text.print(Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()) + " health ", Color.RED);
					text.print("and ");
					text.print(Integer.toString(player.getMagic()) + "/" + Integer.toString(player.getMaxMagic()) + " magic", Color.BLUE);
					text.print(".");
				}
				else{
					player.setHealth(player.getHealth() + item.getConsumableStats()[0]);
					
					text.printText("It healed ");
					text.print(healthRestored + " health", Color.RED);
					text.print(".");
					text.print("You now have ");
					text.print(Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()) + " health", Color.RED);
					text.print(".");
				}
			}
			else if(item.getConsumableStats()[1] > 0){
				
				player.setMagic(player.getMagic() + item.getConsumableStats()[1]);

				text.printText("It healed ");
				text.print(magicRestored + " magic", Color.BLUE);
				text.print(".");
				text.print("You now have ");
				text.print(Integer.toString(player.getMagic()) + "/" + Integer.toString(player.getMaxMagic()) + " magic", Color.BLUE);
				text.print(".");
			}
			
			
			if(item.hasStatusEffects()){
				
				StatusEffect[] effects = item.getStatusEffects();
				
				for(int i = 0; i < effects.length; i++){
					if(effects[i].getType() != emptyEffect.getType()){
						player.addStatusEffect(effects[i]);
						
						text.printText("The ");
						text.print(item.getName(), item.getColor());
						text.print(" gave you a ");
						text.print(effects[i].getName(), effects[i].isDebuff() ? Color.RED : DARK_GREEN);
						text.print(" effect!");
					}
				}
			}
			
			updatePlayer(true);
			return true;
		}
		else{
			text.printText("That item is not for your class.");
			return false;
		}
	}
	
	//Give player item
	private void giveItem(Item item){
		
		int stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				
				if(player.getOpenInventorySlots(item) == 0){
					text.printText(new String[]{"You do not have enough room.", "Do you want to drop an item?"});
					
					while(true){
						
						int stringChosen2 = text.testInput(new String[]{"yes", "y", "no", "n"});
						
						switch(stringChosen2){
						case 0: case 1:
							
							if(player.getOpenInventorySlots(item) == 0){
								removeItemChoice();
							}
							return;
						
						case 2: case 3:
							text.printText("Item not picked up.");
							return;
						
						default:
							text.printText("Input not recognized, please try again");
							return;
						}
					}
				}
				
				if(player.getOpenInventorySlots(item) > 0){
					text.printText("You picked up the ");
					text.print(item.getName(), item.getColor());
					text.print(".");
					sound.playSound(item.getSound());
					player.addItemToInventory(item);
					return;
				}
				
				return;
			
			case 2: case 3:
				text.printText("Item not picked up.");
				return;
			
			default:
				text.printText("Input not recognized, please try again");
				break;
			}
			
			stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Equip main menu
	private void equipItem(String input){
		
		if(input.equals("")){
			text.printTextAddLine(new String[]{"View your currently equipped items or equip new ones?"});
		}
		
		int stringChosen = -1;
		
		if(input.equals("")){
			stringChosen = text.testInput(new String[]{"view currently equipped", "currently equipped", "view current equips", "current equips", "view current", "view equipped", "view equips", "current", "c", "view", "v", "equip new ones", "equip new", "equip", "new", "e", "n", "back", "b"});
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
				text.printText("Equip cancelled.");
				return;
			
			default:
				text.printText("Input not recognized, please try again");
				break;
			}
			
			stringChosen = text.testInput(new String[]{"view currently equipped", "currently equipped", "view current equips", "current equips", "view current", "view equipped", "view equips", "current", "c", "view", "v", "equip new ones", "equip new", "equip", "new", "e", "n", "back", "b"});
		}
	}
	
	//Equip Examine menu
	private void equipItemExamine(){
		
		Item[] inv = player.getInventory();
		
		boolean displayText = true;
		
		text.printTextAddLine("Your currently equipped items are:");
		text.printText(inv[0].getName(), inv[0].getColor());
		text.printText(inv[1].getName(), inv[1].getColor());
		text.printText(inv[2].getName(), inv[2].getColor());
		text.printText("Type the item name for details.");
		
		String[] input = new String[]{inv[0].getName(), inv[1].getName(), inv[2].getName(), "back", "b"};
		
		int stringChosen = text.testInput(input);
		
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
				text.printText("Input not recognized, please try again");
				displayText = false;
				break;
			}

			if(displayText){
				text.printTextAddLine("Your currently equipped items are:");
				text.printText(inv[0].getName(), inv[0].getColor());
				text.printText(inv[1].getName(), inv[1].getColor());
				text.printText(inv[2].getName(), inv[2].getColor());
				text.printText("Type the item name for details.");
			}
			
			stringChosen = text.testInput(input);
		}
	}
	
	//Equip item prompt
	private void equipItemSub(String input){
		
		if(input.equals("")){
			text.printTextAddLine("Equip which item?");
		}
		
		Item[] items = player.getEquipableItemsInInventory();
		String[] itemNames = new String[items.length + 2];
		
		for(int i = 0; i < items.length; i++){
			
			if(input.equals("")){
				text.addLine();
				text.print(items[i].getName(), items[i].getColor());
			}
			
			itemNames[i] = items[i].getName();
		}

		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		
		int itemNum = -1;
		
		if(input.equals("")){
			itemNum = text.testInput(itemNames);
		}
		else{
			itemNum = text.testInput(input, itemNames);
			
			if(itemNum == -1){
					text.printText("Input not recognized, please try again");
					return;
			}
		}
		
		while(true){
			if(itemNum >= itemNames.length - 2){
				text.printText("Equip cancelled.");
				return;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 2){
				if(items[itemNum].getClassType() == player.getClassType() || items[itemNum].getClassType() == 3){
					incrementUpdateTick = true;
					Item item = items[itemNum];
					
					player.switchItemsInInventory(player.getInventory()[item.getType()], item);
					sound.playSound(item.getSound());
					
					text.printText("You equipped your ");
					text.print(item.getName(), item.getColor());
					text.print(".");
					updatePlayer(true);
					
					if(!input.equals("")){
						return;
					}
				}
				else{
					text.printText("That item is not for your class.");
					return;
				}
			}
			else{
				text.printText("Input not recognized, please try again");
			}
			
			itemNum = text.testInput(itemNames);
		}
	}
	
	//Display item info
	private void itemInfo(Item item){
		
		if(item.getType() == 0 || item.getType() == 1){

			text.printTextAddLine("Your ");
			text.print(item.getName(), item.getColor());
			text.print(" has the attack");
			
			if(item.getWeaponAttacks().length > 1){
				text.print("s");
			}
			
			text.print(":");
			
			for(int i = 0; i < item.getWeaponAttacks().length; i++){
				
				Attack attack = item.getWeaponAttacks()[i];
				
				String end = ".";
				
				if(attack.hasStatusEffects()){
					end = ",";
				}
				
				if(item.getType() == 0){
					text.addLine();
					
					text.printText(attack.getAttackName(), attack.getColor());
					text.print(", which does ");
					text.print((attack.getMinimumDamage() == attack.getMaximumDamage() ? Integer.toString(attack.getMinimumDamage()) :
					Integer.toString(attack.getMinimumDamage()) + "-" +
					Integer.toString(attack.getMaximumDamage())) + " damage ", Color.RED);
					text.print("with a hit chance of ");
					text.print(Integer.toString(attack.getHitRate()) + "%", GOLD);
					text.print(end);
				}
				else{
					text.addLine();

					text.printText(attack.getAttackName(), attack.getColor());
					text.print(", which costs ");
					text.print(Integer.toString(attack.getUserBasedInt()) + " magic", Color.BLUE);
					text.print(", and does ");
					text.print((attack.getMinimumDamage() == attack.getMaximumDamage() ? Integer.toString(attack.getMinimumDamage()) :
					Integer.toString(attack.getMinimumDamage()) + "-" +
					Integer.toString(attack.getMaximumDamage())) + " damage ", Color.RED);
					text.print("with a hit chance of ");
					text.print(Integer.toString(attack.getHitRate()) + "%", GOLD);
					text.print(end);
				}
				
				if(attack.hasStatusEffects()){
					
					text.printText("and has the effect");
					
					if(attack.getStatusEffects().length > 1){
						text.print("s");
					}
					
					text.print(":");
					
					for(int j = 0; j < attack.getStatusEffects().length; j++){
						
						StatusEffect effect = attack.getStatusEffects()[j];
						String effectName = effect.getName();
						
						text.printText("Level " + Integer.toString(effect.getLevel()) + " ", GOLD);
						text.print(effectName, effect.isDebuff() ? Color.RED : DARK_GREEN);
						text.print(" on ");
						text.print((effect.isTargetingPlayer() ?  " yourself" : " the enemy"),
						(effect.isDebuff()^effect.isTargetingPlayer()) ? DARK_GREEN : Color.RED);
						text.print(", lasting " + Integer.toString(effect.getLength()) + " action" + 
						(effect.getLength() == 1 ? "" : "s") +" at a ");
						text.print(Integer.toString(effect.getChance()) + "% chance", GOLD);
						text.print(".");
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
						text.printTextAddLine("Your ");
						text.print(name, color);
						text.print(" gives you ");
						text.print(def + " defense", Color.DARK_GRAY);
						text.print(", ");
						text.print(hp + " health", Color.RED);
						text.print(", and ");
						text.print(mp + " magic", Color.BLUE);
						text.print(".");
					}
					else{
						text.printTextAddLine("Your ");
						text.print(name, color);
						text.print(" gives you ");
						text.print(def + " defense", Color.DARK_GRAY);
						text.print(" and ");
						text.print(hp + " health", Color.RED);
						text.print(".");
					}
				}
				else if(item.getArmorStats()[2] > 0){
					text.printTextAddLine("Your ");
					text.print(name, color);
					text.print(" gives you ");
					text.print(def + " defense", Color.DARK_GRAY);
					text.print(" and ");
					text.print(mp + " magic", Color.BLUE);
					text.print(".");
				}
				else{
					text.printTextAddLine("Your ");
					text.print(name, color);
					text.print(" gives you ");
					text.print(def + " defense", Color.DARK_GRAY);
					text.print(".");
				}
			}
			else if(item.getArmorStats()[1] > 0){
				
				if(item.getArmorStats()[2] > 0){
					text.printTextAddLine("Your ");
					text.print(name, color);
					text.print(" gives you ");
					text.print(hp + " health", Color.RED);
					text.print(" and ");
					text.print(mp + " magic", Color.BLUE);
					text.print(".");
				}
				else{
					text.printTextAddLine("Your ");
					text.print(name, color);
					text.print(" gives you ");
					text.print(hp + " health", Color.RED);
					text.print(".");
				}
			}
			else{
				text.printTextAddLine("Your ");
				text.print(name, color);
				text.print(" gives you ");
				text.print(mp + " magic", Color.BLUE);
				text.print(".");
			}
		}
		
		if(item.getType() == 3){
			
			int hp = item.getConsumableStats()[0];
			int mp = item.getConsumableStats()[1];
			
			if(hp > 0){
				if(mp > 0){
					text.printTextAddLine("Your ");
					text.print(item.getName(), item.getColor());
					text.print(" restores ");
					text.print(Integer.toString(hp) + " health", Color.RED);
					text.print(" and ");
					text.print(Integer.toString(mp) + " magic", Color.BLUE);
					text.print(".");
				}
				else{
					text.printTextAddLine("Your ");
					text.print(item.getName(), item.getColor());
					text.print(" heals ");
					text.print(Integer.toString(hp) + " health", Color.RED);
					text.print(".");
				}
			}
			else{
				text.printTextAddLine("Your ");
				text.print(item.getName(), item.getColor());
				text.print(" restores ");
				text.print(Integer.toString(mp) + " magic", Color.BLUE);
				text.print(".");
			}
			
			if(item.hasStatusEffects()){
				
				if(hp > 0 || mp > 0){
					text.printText("It has the status effects:");
				}
				else{
					text.printTextAddLine("Your ");
					text.print(item.getName(), item.getColor());
					text.print(" has the status effects:");
				}
				
				for(int i = 0; i < item.getStatusEffectCount(); i++){
					
					StatusEffect effect = item.getStatusEffects()[i];
					
					String effectName = effect.getName();
					
					text.printText("Level " + Integer.toString(effect.getLevel()) + " ");
					text.print(effectName, (effect.isDebuff()^effect.isTargetingPlayer()) ? Color.RED : DARK_GREEN);
					text.print(" on ");
					text.print((effect.isTargetingPlayer() ?  " yourself " : " the enemy "),
					(effect.isDebuff()^effect.isTargetingPlayer()) ? DARK_GREEN : Color.RED);
					text.print("with a duration of " + Integer.toString(effect.getLength()) + " actions.");
				}
			}
		}
	}
	
	//Shop main menu
	private void shop(String input){
		if(areaHandler.getArea().getShopkeepers().length < 1){
			text.printText("There are no available shops here.");
			return;
		}
		else{

			Shopkeeper[] shops = areaHandler.getArea().getShopkeepers();
			
			if(input.equals("")){
				text.printTextAddLine("Choose a shop:");
				
				for(int i = 0; i < shops.length; i++){
					text.printText(shops[i].getName());
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
				shopNum = text.testInput(shopNames);
			}
			else{
				shopNum = text.testInput(input, shopNames);
				
				if(shopNum == -1){
						text.printText("Input not recognized, please try again");
						return;
				}
			}
			
			while(true){
				if(shopNum >= shopNames.length - 2){
					text.printText(new String[]{"Shopping cancelled."});
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
					text.printText("Input not recognized, please try again");
				}
				
				shopNum = text.testInput(shopNames);
			}
		}
	}
	
	//Shop menu for specific shopkeeper
	private void shopSub(Shopkeeper shopkeeper){
		
		Item[] items = shopkeeper.getItems();
		int[] itemPrices = shopkeeper.getItemPrices();
		int[] itemCounts = shopkeeper.getItemCounts();
		
		text.addLine();
		text.printText(shopkeeper.getGreetMessage());

		int[] counts = shopkeeper.getItemCounts();
		int[] prices = shopkeeper.getItemPrices();
		
		for(int i = 0; i < items.length; i++){
			text.printText(items[i].getName(), items[i].getColor());
			text.print(" " + "[" + Integer.toString(counts[i]) + "] ");
			text.print(Integer.toString(prices[i]) + " gp", GOLD);
		}
		
		int foundItems = 0;

		for(int i = 0; i < items.length; i++){
			if(!items[i].getName().equals(emptyItem.getName())){
				foundItems++;
			}
		}
		
		String[] itemNames = new String[foundItems + 4];
		int itemNum = 0;
		
		for(int i = 0; i < items.length; i++){
			if(!items[i].getName().equals(emptyItem.getName())){
				itemNames[i] = items[i].getName();
				itemNum++;
			}
		}
		
		itemNames[itemNames.length - 1] = "b";
		itemNames[itemNames.length - 2] = "back";
		itemNames[itemNames.length - 3] = "n";
		itemNames[itemNames.length - 4] = "no";
		
		itemNum = text.testInput(itemNames);
		
		while(true){
			if(itemNum >= itemNames.length - 4){
				text.printText(shopkeeper.getEndMessage());
				return;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 4){
				if(itemCounts[itemNum] >= 1){
					if(itemCounts[itemNum] == 1){
						shopItemPurchase(items[itemNum], itemPrices[itemNum], 1, shopkeeper);
					}
					else{
						shopMultipleItems(items[itemNum], itemPrices[itemNum], itemCounts[itemNum], shopkeeper);
					}
				}
				else{
					text.printText("Sorry, we're out of stock on that one.");
				}
			}
			else{
				text.printText("What did you say? I might have misunderstood you.");
			}
			
			itemNum = text.testInput(itemNames);
		}
	}
	
	//Shop menu for buying an item
	private void shopMultipleItems(Item item, int gold, int count, Shopkeeper shopkeeper){
		
		text.printTextAddLine("How many do you want?");
		
		String itemString = text.testInput();
		int itemCount = -1;
		
		if(itemString.equalsIgnoreCase("back") || !itemString.equalsIgnoreCase("b")){
			text.printText("Anything else you want?");
			return;
		}
		
		while(itemCount < 1){
			try{
				itemCount = Integer.parseInt(itemString);
			}
			catch(Exception e){
				text.printText(new String[]{"That's not a number, or maybe you typed the word instead of the number."});
			}
			
			if(itemCount < 1){
				text.printText("Sorry, I can't give you that much.");
			}
		}
		
		if(itemCount > count){
			shopMultipleItemsSub(item, gold, count, shopkeeper);
		}
		else if(itemCount <= count){
			shopItemPurchase(item, gold, itemCount, shopkeeper);
		}
		
		text.printText("Anything else you want?");
		return;
	}
	
	//Shop menu if player orders more than are available
	private void shopMultipleItemsSub(Item item, int gold, int count, Shopkeeper shopkeeper){
		
		text.printText("Sorry, we only have " + Integer.toString(count) + " of those. Do you want all of them?");
		
		int stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
				case 0: case 1:
					shopItemPurchase(item, gold, count, shopkeeper);
					return;
				
				case 2: case 3:
					return;
				
				default:
					text.printText("What did you say? I may have misunderstood you.");
					break;
			}
			
			stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Shop menu for confirming purchase
	private void shopItemPurchase(Item item, int gold, int count, Shopkeeper shopkeeper){
		
		if(player.getOpenInventorySlots(item) < count){
			text.printText("You don't have enough inventory space.");
			return;
		}
		
		if(player.getGold() < (gold*count)){
			text.printText("You don't have enough gold.");
			return;
		}
		
		String plural = "";
		
		if(count > 1){
			plural = "s";
		}
		
		text.printTextAddLine("Do you want to buy " + Integer.toString(count) + " ");
		text.print(item.getName() + plural, item.getColor());
		text.printText(" for ");
		text.print(Integer.toString(gold*count) + " gold", GOLD);
		text.print("?");
		text.printYN();
		
		int stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				
				player.setGold(player.getGold() - (gold*count));
				
				for(int i = 0; i < count; i++){
					player.addItemToInventory(item);
					shopkeeper.removeItem(item);
				}
				
				text.printText("You bought the ");
				text.print(item.getName() + plural, item.getColor());
				text.print(".");
				sound.playSound("coins01.wav");
				
				return;
				
			case 2: case 3:
				return;
			
			default:
				text.printText("Input not recognized, please try again");
				break;
			}
			
			stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Shop menu for Tradesman (player sells items)
	private void shopTrade(Shopkeeper shopkeeper){
		
		Item[] inv = player.getInventory();
		
		boolean displayText;
		
		text.printTextAddLine(shopkeeper.getGreetMessage());
		
		for(int i = 3; i < inv.length; i++){
			if(!inv[i].getName().equals(emptyItem.getName())){
				text.printText(" ");
				text.print(inv[i].getName(), inv[i].getColor());
				text.print(" [" + Integer.toString(player.getItemCountInInventorySlot(i)) + "]");
			}
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
		itemNum = text.testInput(itemNames);
		
		while(true){
			if(itemNum >= itemNames.length - 2){
				return;
			}
			else if(itemNum > -1 && itemNum < itemNames.length - 2){
				shopTradeSub(inv[itemNum + 3], 0);
				text.printText("Anything else you want to sell?");
				displayText = true;
			}
			else{
				text.printText("I'm sorry, what did you say?");
				displayText = false;
			}
			
			if(displayText){
				for(int i = 3; i < inv.length; i++){
					if(!inv[i].getName().equals(emptyItem.getName())){
						text.printText(" ");
						text.print(inv[i].getName(), inv[i].getColor());
						text.print(" [" + Integer.toString(player.getItemCountInInventorySlot(i)) + "]");
					}
				}
			}
			
			itemNum = text.testInput(itemNames);
		}
	}
	
	//Shop menu for Tradesman for specific item
	private void shopTradeSub(Item item, int count){
		
		int gold = 20;
		
		double classes = 1;
		
		if(item.getClassType() == 3){
			classes = 1.5;
		}
		
		switch(item.getType()){
			case 0: case 1:
				gold = 0;
				
				for(int i = 0; i < item.getWeaponAttacks().length; i++){
					double temp1 = item.getWeaponAttacks()[i].getMinimumDamage();
					double temp2 = item.getWeaponAttacks()[i].getHitRate();
					double temp3 = temp2/70;
					double temp4 = temp1*temp3;
					double temp5 = temp4*classes;
					
					gold += (int) temp5;
					
					if(item.getWeaponAttacks()[i].hasStatusEffects()){
						for(int j = 0; j < item.getWeaponAttacks()[i].getStatusEffectCount(); j++){
							double temp6 = item.getWeaponAttacks()[i].getStatusEffects()[j].getLength();
							double temp7 = item.getWeaponAttacks()[i].getStatusEffects()[j].getLevel();
							double temp8 = item.getWeaponAttacks()[i].getStatusEffects()[j].getChance();
							double temp9 = temp6*temp7;
							double temp10 = temp8/100;
							double temp11 = temp9*temp10;
							double temp12 = temp11/4;
							
							if(item.getClassType() == 0 && item.getType() == 1){
								temp12 *= 5;
							}
							
							gold += (int)temp12;
						}
					}
				}
				
				if(item.getName().toLowerCase().contains("dragon scroll")){
					gold *= 100;
				}
				
				break;
			
			case 2:
				gold = (int) ((int) (item.getArmorStats()[0] + item.getArmorStats()[1] + item.getArmorStats()[2])*classes*3);
				break;
			
			case 3:
				gold = (int) ((int) (item.getConsumableStats()[0] + item.getConsumableStats()[1])*classes);
				
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
		
		if(player.getInventoryItemCount(item, false) > 1){
			
			text.printText("How many do you want to sell?");
			
			String stringChosen = text.testInput();
			count = -1;
			
			while(count < 0){
				try{
					count = Integer.parseInt(stringChosen);
				}
				catch(Exception e){
					text.printText("That isn't a number, or did you type the word instead of the number?");
				}
				
				if(count < 0){
					text.printText("You can't sell that many.");
				}
				else if(count > player.getInventoryItemCount(item, false)){
					text.printText("You don't have that many.");
					count = 0;
				}
			}
			
			if(count == 1){
				text.printTextAddLine("Are you sure you want to sell 1 ");
				text.print(item.getName(), item.getColor());
				text.print(" for ");
				text.print(Integer.toString(gold) + " gold", GOLD);
				text.print("?");
				text.printYN();
				text.printText("Once sold, you cannot retrieve it.");
			}
			else{
				text.printTextAddLine("Are you sure you want to sell " + Integer.toString(count) + " ");
				text.print(item.getName() + "s", item.getColor());
				text.print(" for ");
				text.print(Integer.toString(gold) + " gold", GOLD);
				text.print("?");
				text.printYN();
				text.printText("Once sold, you cannot retrieve them.");
			}
		}
		else{
			text.printTextAddLine("Are you sure you want to sell your ");
			text.print(item.getName(), item.getColor());
			text.print(" for ");
			text.print(Integer.toString(gold) + " gold", GOLD);
			text.print("?");
			text.printYN();
			text.printText("Once sold, you cannot retrieve it.");
			count = 1;
		}
		
		int stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		
		while(true){
			switch(stringChosen){
			case 0: case 1:
				if(count == 1){
					text.printText("Item sold.");
				}
				else{
					text.printText("Items sold.");
				}
				
				for(int i = 0; i < count; i++){
					player.removeItemFromInventory(item);
					player.setGold(player.getGold() + gold);
				}
				
				sound.playSound("coins01.wav");
				incrementUpdateTick = true;
				return;
			
			case 2: case 3:
				if(count == 1){
					text.printText("Item not sold.");
				}
				else{
					text.printText("Items not sold.");
				}
				return;
			
			default:
				text.printText("Input not recognized, please try again");
				break;
			}
			
			stringChosen = text.testInput(new String[]{"yes", "y", "no", "n"});
		}
	}
	
	//Interact menu
	private void interact(String input){
		
		if(input.equals("")){
			text.printTextAddLine("Interact with what?");
		}
		
		//All area interactions
		Interaction[] intrs = areaHandler.getArea().getInteractions();
		
		//No. of total intr. names for an intr. in array above plus no. of names for all prior intrs.
		//Used to determine which interaction the player selects
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
			intrNum = text.testInput(intNames);
		}
		else{
			intrNum = text.testInput(input, intNames);
			
			if(intrNum == -1){
					text.printText("Input not recognized, please try again");
					return;
			}
		}
		
		while(true){
			if(intrNum >= intNames.length - 2){//Player types cancel or cn
				text.printText("Interaction cancelled.");
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
				
				//Set window in interaction
				intrs[intrNum].setWindow(window);
				
				//Do interaction stuff
				player = intrs[intrNum].interact(player);
				sound.playSound(intrs[intrNum].getSound());
				
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
				text.printText("Input not recognized or interaction unavailable, please try again");
			}
			
			intrNum = text.testInput(intNames);
		}
	}
	
	//Goto menu
	private void goTo(String input){
		if(player.hasStatusEffect(7)){
			text.printText("You are paralyzed!");
			return;
		}
		
		if(input.equals("")){
			text.printTextAddLine("Where do you want to go?");
		}
		
		Area[] accessibleAreas = areaHandler.getAccessibleAreas();
		String[] areaNames = new String[accessibleAreas.length + 2];
		
		for(int i = 0; i < accessibleAreas.length; i++){
			areaNames[i] = accessibleAreas[i].getName();
		}
		
		areaNames[areaNames.length - 2] = "b";
		areaNames[areaNames.length - 1] = "back";
		
		int areaNum = -1;
		
		if(input.equals("")){
			areaNum = text.testInput(areaNames);
		}
		else{
			areaNum = text.testInput(input, areaNames);
			
			if(areaNum == -1){
					text.printText("Input not recognized, please try again");
					return;
			}
		}
		
		Area area = areaHandler.getArea();
		
		while(true){
			if(areaNum >= areaNames.length - 2){
				text.printText("Goto cancelled.");
				return;
			}
			else if(areaNum > -1 && areaNum < areaNames.length - 2){
				if(!accessibleAreas[areaNum].equals(area)){
					text.printText("Leaving ");
					text.print(area.getName(), area.getColor());
					text.print("...");
					waitSeconds(2, false);
					
					area = accessibleAreas[areaNum];
					areaHandler.setArea(area);
					
					text.printText("You have arrived at ");
					text.print(area.getName(), area.getColor());
					text.print(".");
					updatePlayer(true);
					incrementUpdateTick = true;
					
					displayTip();
				}
				else{
					text.printText("You are already in ");
					text.print(area.getName(), area.getColor());
					text.print(".");
				}
				return;
			}
			else{
				if(input.equals("")){
					text.printText("Input not recognized or area currently inaccessable, please try again");
				}
			}
			
			areaNum = text.testInput(areaNames);
		}
	}
	
	//Options menu
	private void options(String input){
		if(input.equals("")){
			text.printTextAddLine(new String[]{"Type the option name to change it:", "Wait: "});
			text.print(waitEnabled ? "ON" : "OFF", waitEnabled ? DARK_GREEN : Color.RED);
			text.printText("Sound: ");
			text.print(soundEnabled ? "ON" : "OFF", soundEnabled ? DARK_GREEN : Color.RED);
		}

		int stringChosen = -1;
		
		if(input.equals("")){
			stringChosen = text.testInput(new String[]{"waiting", "wait", "w", "sounds", "sound", "s", "back", "b"});
		}
		else{
			stringChosen = text.testInput(input, new String[]{"waiting", "wait", "w", "sounds", "sound", "s", "back", "b"});
			
			if(stringChosen == -1){
				text.printText("Input not recognized, please try again");
				return;
			}
		}
		
		while(true){
			switch(stringChosen){
			case 0: case 1: case 2:
				waitEnabled = !waitEnabled;
				
				text.printTextAddLine("Wait is now ");
				text.print(waitEnabled ? "ON" : "OFF", waitEnabled ? DARK_GREEN : Color.RED);
				text.print(".");
				break;
				
			case 3: case 4: case 5:
				soundEnabled = !soundEnabled;
				
				text.printTextAddLine("Sound is now ");
				text.print(soundEnabled ? "ON" : "OFF", soundEnabled ? DARK_GREEN : Color.RED);
				text.print(".");
				
				sound.setSoundEnabled(soundEnabled);
				break;
				
			case 6: case 7:
				return;
				
			default:
				text.printText("Input not recognized, please try again");
				break;
			}
			
			stringChosen = text.testInput(new String[]{"waiting", "wait", "w", "sounds", "sound", "s", "back", "b"});
		}
	}
	
	//Debug commands menu
	private void executeCommand(boolean displayText){
		
		if(displayText)
			text.printTextAddLine("Input a command to execute (give, set, spawn):");
		
		int stringChosen = text.testInput(new String[]{"give", "set", "spawn"});
		
		switch(stringChosen){
		case 0:
			if(!player.isInventoryFull()){
				
				text.printTextAddLine("Enter item name:");
				
				String itemName = text.testInput();
				boolean found = false;
				
				for(int i = 0; i < list.getNormalWeaponItemList().length; i++){
					if(list.getNormalWeaponItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(list.getNormalWeaponItemList()[i]);
						found = true;
					}
				}
				
				for(int i = 0; i < list.getSpecialWeaponItemList().length; i++){
					if(list.getSpecialWeaponItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(list.getSpecialWeaponItemList()[i]);
						found = true;
					}
				}
				
				for(int i = 0; i < list.getArmorItemList().length; i++){
					if(list.getArmorItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(list.getArmorItemList()[i]);
						found = true;
					}
				}
				
				for(int i = 0; i < list.getConsumableItemList().length; i++){
					if(list.getConsumableItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(list.getConsumableItemList()[i]);
						found = true;
					}
				}
				
				for(int i = 0; i < list.getMiscItemList().length; i++){
					if(list.getMiscItemList()[i].getName().equalsIgnoreCase(itemName)){
						giveItem(list.getMiscItemList()[i]);
						found = true;
					}
				}
				if(!found){
					text.printText("Item not found!");
				}
			}
			else{
				text.printText("Inventory full.");
			}
			return;
		
		case 1:
			text.printTextAddLine("Enter value to set (hp, mp, gold, inventory, xp, level):");
			
			int stringChosen2 = text.testInput(new String[]{"hp", "mp", "gold", "inventory", "xp", "level"});

			text.printText("Enter amount:");
			
			String stringChosen3 = text.testInput();
			
			int amount = 1;
			
			try{
				amount = Integer.parseInt(stringChosen3);
			}
			catch(Exception e){}
			
			switch(stringChosen2){
				case 0:
					player.setMaxHealth(amount);
					player.setHealth(amount);
					return;
				
				case 1:
					player.setMaxMagic(amount);
					player.setMagic(amount);
					return;
				
				case 2:
					player.setGold(amount);
					return;
				
				case 4:
					player.setInventorySpace(amount);
					return;
				
				case 5:
					player.addXP(amount);
					return;
				
				case 6:
					player.setLevel(amount);
					return;
			}
			return;
		
		case 2:
			
			text.printTextAddLine("Enter enemy name or ID:");
			String stringChosen4 = text.testInput();
			
			try{
				enemyEncounter(list.getEnemyList()[Integer.parseInt(stringChosen4)]);
			}
			catch(NumberFormatException e){
				for(int i = 0; i < list.getEnemyList().length; i++){
					if(list.getEnemyList()[i].getEnemyName().toLowerCase().equals(stringChosen4)){
						enemyEncounter(list.getEnemyList()[i]);
						return;
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException e){
				text.printText("Invalid ID!");
				return;
			}
			
			text.printText("Enemy not found!");
			return;
			
		default:
			text.printText("Input not recognized, please try again");
			executeCommand(false);
			return;
		}
	}
	
	//Enemy fight
	public void enemyEncounter(Enemy enemyToEncounter){
		
		enemy = enemyToEncounter;
		enemy.setHealth(enemy.getMaxHealth());
		
		String enemyName = enemy.getEnemyName();
		
		text.printTextAddLine("You've encountered a");
		
		if(enemyName.startsWith("A") || enemyName.startsWith("E") || enemyName.startsWith("I") || enemyName.startsWith("O") || enemyName.startsWith("U")){
			text.print("n");
		}
		
		text.print(" " + enemyName, enemy.getColor());
		text.print("!");
		
		sound.playSound(enemy.getSound());
		
		inFight = true;
		
		int turn = 0;
		
		if(enemy.attacksFirst()){
			turn = 1;
		}
		
		while(inFight){
			
			if(turn == 0){
				updatePlayer(true);
				enemyEncounterPlayerCommands();
			}
			
			if(player.getHealth() <= 0 || enemy.getHealth() <= 0){
				inFight = false;
				return;
			}
			
			if(turn == 1){
				
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
				text.printTextAddLine("The ");
				text.print(enemy.getEnemyName(), enemy.getColor());
				text.print(" used ");
				text.print(attacks[attackNum].getAttackName(), attacks[attackNum].getColor());
				text.print("!");

				if(waitEnabled){
					sound.playSound(attacks[attackNum].getSound());
				}
				
				waitSeconds(1, false);
				
				if(!attacks[attackNum].getRandomHit()){
					text.printText("The attack missed!", DARK_GREEN);
				}
				else{
					if(enemy.getRandomHitFail()){
						text.printText("The attack failed due to confusion!", DARK_GREEN);
					}
					else{
						
						boolean blocked = false;
						
						if(player.hasStatusEffect(19)){
							int level = player.getStatusEffectLevel(19);
							
							if(level > 0 && random.nextInt(99) + 1 <= level*5){
								blocked = true;
							}
							
						}
						if(blocked){
							text.printText("You blocked the attack!", DARK_GREEN);
						}
						else{
							int damage = attacks[attackNum].getRandomDamage() - player.getDefense();
							
							if(damage < 0){
								damage = 0;
							}
							
							if(player.hasStatusEffect(20)){
								damage *= -(player.getStatusEffectLevel(20)/20);
							}

							int newLife = player.getHealth() - damage;
							
							
							if(newLife < 0){
								newLife = 0;
							}
							
							player.setHealth(newLife);
	
							if(damage == 0 && attacks[attackNum].getMaximumDamage() != 0){
								text.printText("The attack was inaffective!", DARK_GREEN);
								text.printText("You still have ");
								text.print(Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()) + " health", Color.RED);
								text.print(" left!");
							}
							else if(damage < 0){
								text.printText("It healed ");
								text.print(Integer.toString(damage) + " damage", DARK_GREEN);
								text.print("!");

								text.printText("You now have ");
								text.print(Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()) + " health", Color.RED);
								text.print(" left!");
							}
							else if(attacks[attackNum].getMaximumDamage() != 0){
								text.printText("It dealt ");
								text.print(Integer.toString(damage) + " damage", Color.RED);
								text.print("!");
								
								if(newLife > 0){
									text.printText("You now have ");
									text.print(Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()) + " health", Color.RED);
									text.print(" left!");
									
									sound.playSound("hurt01.wav");
								}
								else{
									if(player.getHealth() + damage >= player.getMaxHealth() && damage >= player.getMaxHealth()){
										text.printText("It's a one-hit kill!", Color.RED);
									}
									
									playerDeath();
								}
							}
							
							if(newLife > 0 && attacks[attackNum].hasStatusEffects()){
								StatusEffect effect = attacks[attackNum].getRandomEffect();
								
								if(effect.getType() != emptyEffect.getType()){

									String effectName = effect.getName();
									
									if(effect.isTargetingPlayer()){
										if(!player.isImmuneToStatusEffect(effect)){
											player.addStatusEffect(effect);
											
											text.printText("You got a ");
											text.print(effectName, effect.isDebuff() ? Color.RED : DARK_GREEN);
											text.print(" effect!");
										}
										else{
											text.printText("You got a ");
											text.print(effectName, effect.isDebuff() ? Color.RED : DARK_GREEN);
											text.print(" effect, but you are ");
											text.print("immune", effect.isDebuff() ? DARK_GREEN : Color.RED);
											text.print("!");
										}
									}
									else{
										if(!enemy.isImmuneToStatusEffect(effect)){
											enemy.addStatusEffect(effect);
											
											text.printText("The enemy got a ");
											text.print(effectName, effect.isDebuff() ? DARK_GREEN : Color.RED);
											text.print(" effect!");
										}
										else{
											text.printText("The enemy got a ");
											text.print(effectName, effect.isDebuff() ? DARK_GREEN : Color.RED);
											text.print(" effect, but it is ");
											text.print("immune", effect.isDebuff() ? Color.RED : DARK_GREEN);
											text.print("!");
										}
									}
								}
							}
						}
					}
				}
				
				waitSeconds(1, false);
			}
			
			turn++;
			
			if(turn >= 2){
				turn = 0;
			}
			
			if(player.getHealth() <= 0 || enemy.getHealth() <= 0){
				inFight = false;
			}
		}
	}
	
	//Enemy fight player's turn
	private void enemyEncounterPlayerCommands(){
		
		text.printTextAddLine(new String[]{"What will you do?", "Attack", "Items", "Equip", "Stats", "Examine Enemy", "Run"});
		
		int stringChosen = text.testInput(new String[]{"attacks", "attack", "a", "items", "item", "i", "equips", "equip", "eq", "stats", "st", "s", "run", "r", "examine enemy", "examine", "enemy", "e"});
		
		if(stringChosen == -1){
			stringChosen = text.testInput(text.trimToSpace(text.getLastInput()), acceptedInput);
		}
		
		boolean displayText = true;
		
		while(true){
			switch(stringChosen){
			case 0: case 1: case 2:
				if(!enemyEncounterPlayerAttack(text.trimFromSpace(text.getLastInput()))){
					displayText = true;
					break;
				}
				return;
				
			case 3: case 4: case 5:
				if(!useItem(text.trimFromSpace(text.getLastInput()))){
					displayText = true;
					break;
				}
				return;
				
			case 6: case 7: case 8:
				equipItem(text.trimFromSpace(text.getLastInput()));
				displayText = true;
				break;
			
			case 9: case 10: case 11:
				printStats();
				displayText = true;
				break;
			
			case 12: case 13:
				if(player.hasStatusEffect(7)){
					text.addLine();
					text.printText("You cannot run, you are paralyzed!", Color.RED);
					break;
				}
				
				text.printTextAddLine("Attempting to escape...");
				waitSeconds(random.nextInt(1) + 1, false);
				
				int escape = random.nextInt(99) + 1;
				
				if(escape <= enemy.getEscapeRate()){
					text.printText("You escaped successfully!", DARK_GREEN);
					inFight = false;
				}
				else{
					text.printText("You could not escape!", Color.RED);
				}
				
				return;
			
			case 14: case 15: case 16: case 17:
				text.printTextAddLine("The enemy has ");
				text.print(Integer.toString(enemy.getHealth()) + "/" + Integer.toString(enemy.getMaxHealth()) + " health ", Color.RED);
				text.print("and ");
				text.print(Integer.toString(enemy.getDefense()) + " defense", Color.DARK_GRAY);
				text.print(".");
				
				if(enemy.hasStatusEffects()){
					text.printText("The enemy also has these status effects:");
					
					StatusEffect[] effects = enemy.getStatusEffects();
					
					for(int i = 0; i < effects.length; i++){
						if(effects[i].getType() != (emptyEffect.getType())){
							
							String plural = "s";
							
							if(effects[i].getLength() == 1){
								plural = "";
							}
	
							text.printText("Level " + effects[i].getLevel() + " ");
							text.print(effects[i].getName(), effects[i].isDebuff() ? Color.RED : DARK_GREEN);
							text.print(" with " + effects[i].getLength() + " turn" + plural + " remaining");
						}
					}
				}
				
				displayText = true;
				break;
			
			default:
				text.printText("Input not recognized or command currently unavailable, please try again");
				displayText = false;
				break;
			}
			
			if(displayText){
				text.printTextAddLine(new String[]{"What will you do?", "Attack", "Items", "Equip", "Stats", "Examine Enemy", "Run"});
			}
			
			stringChosen = text.testInput(new String[]{"attacks", "attack", "a", "items", "item", "i", "equips", "equip", "eq", "stats", "st", "s", "run", "r", "examine enemy", "examine", "enemy", "e"});
		}
	}
	
	//Enemy fight player attack menu
	private boolean enemyEncounterPlayerAttack(String input){
		
		if(input.equals("")){
			text.printText("Use a normal attack or special attack?");
		}
		
		int stringChosen = -1;
		
		if(input.equals("")){
			stringChosen = text.testInput(new String[]{"normal attack", "normal", "n", "special attack", "special", "s", "back", "b"});
		}
		else{
			stringChosen = text.testInput(input, new String[]{"normal attack", "normal", "n", "special attack", "special", "s", "back", "b"});
			
			if(stringChosen == -1){
				text.printText("Input not recognized, please try again");
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
				text.printText("Input not recognized, please try again");
				break;
			}
			
			stringChosen = text.testInput(new String[]{"normal attack", "normal", "n", "special attack", "special", "s", "back", "b"});
		}
	}
	
	//Enemy fight player attack submenu (choosing specific attack)
	private boolean enemyEncounterPlayerAttackSub(boolean isSpecialAttack){
		
		int slot = 0;
		
		if(isSpecialAttack){
			slot = 1;
		}
		
		Attack[] attacks = player.getInventory()[slot].getWeaponAttacks();
		String[] attackNames = new String[attacks.length + 2];
		
		int attackNum = -1;
		
		if(attacks.length > 1){
			
			text.printTextAddLine("Which attack do you want to use?");
			
			for(int i = 0; i < attacks.length; i++){
				text.printText(" " + attacks[i].getAttackName(), attacks[i].getColor());
			}
			
			for(int i = 0; i < attacks.length; i++){
				attackNames[i] = attacks[i].getAttackName();
			}

			attackNames[attackNames.length - 1] = "b";
			attackNames[attackNames.length - 2] = "back";
			
			while(attackNum == -1){
				attackNum = text.testInput(attackNames);
				
				if(attackNum >= attackNames.length - 2){
					return false;
				}
				else if(attackNum == -1){
					text.printText("Input not recognized, please try again");
				}
			}
		}
		else if(attacks.length == 1){
			attackNum = 0;
		}
		
		if(!isSpecialAttack || (isSpecialAttack && player.getMagic() >= attacks[attackNum].getUserBasedInt()) && !player.hasStatusEffect(5)){
			text.printTextAddLine("You used ");
			text.print(attacks[attackNum].getAttackName(), attacks[attackNum].getColor());
			text.print("!");
			
			if(waitEnabled){
				sound.playSound(attacks[attackNum].getSound());
			}
			
			if(isSpecialAttack){
				player.setMagic(player.getMagic() - attacks[attackNum].getUserBasedInt());
			}
			
			waitSeconds(1, false);
			
			if(!attacks[attackNum].getRandomHit()){
				text.printText("Your attack missed!", Color.RED);
				return true;
			}
			else{
				if(player.getRandomHitFail()){
					text.printText("Your attack failed due to confusion!", Color.RED);
					return true;
				}
				else{
					boolean blocked = false;
					
					if(enemy.hasStatusEffect(19)){
						int level = enemy.getStatusEffectLevel(19);
						
						if(level > 0 && random.nextInt(99) + 1 <= level*5){
							blocked = true;
						}
						
					}
					if(blocked){
						text.printText("The enemy blocked the attack!", Color.RED);
						return true;
					}
					else{
						int temp = (int)(attacks[attackNum].getRandomDamage()*player.getDamageMultiplier());
						
						int damage = temp - enemy.getDefense();
						
						if(damage < 0){
							damage = 0;
						}
						
						if(enemy.hasStatusEffect(20)){
							double dmgTemp = damage*-((double)(enemy.getStatusEffectLevel(20))/20);
							damage = (int)Math.round(dmgTemp);
						}
						
						int newLife = enemy.getHealth() - damage;
						
						if(newLife < 0){
							newLife = 0;
						}
						
						enemy.setHealth(newLife);
						
						if(damage == 0 && attacks[attackNum].getMaximumDamage() != 0){
							text.printText("The attack was inaffective!", Color.RED);
						}
						else if(damage < 0){
							text.printText("It healed ");
							text.print(Integer.toString(damage) + " damage", Color.RED);
							text.print("!");
						}
						else if(attacks[attackNum].getMaximumDamage() != 0){
							text.printText("It dealt ");
							text.print(Integer.toString(damage) + " damage", Color.RED);
							text.print("!");
							
							if(newLife > 0){
								if(waitEnabled){
									sound.playSound(enemy.getHitSound());
								}
							}
							else{
								if(enemy.getHealth() + damage >= enemy.getMaxHealth() && damage >= enemy.getMaxHealth()){
									text.printText("It's a one-hit kill!", DARK_GREEN);
								}
								
								text.printTextAddLine("The " + enemy.getEnemyName() + " was killed!");
								sound.playSound(enemy.getSound());
								
								int xp = enemy.getRandomXP();
								
								text.printText("You got ");
								text.print(Integer.toString(xp) + " experience", DARK_GREEN);
								text.print("!");
								
								int level = player.getLevel();
								player.addXP(xp);
								
								if(level < player.getLevel()){
									text.printText("You grew to ");
									text.print("level " + Integer.toString(player.getLevel()), GOLD);
									text.print("!");
								}
	
								int gold = enemy.getRandomGold();
								
								text.printText("You got ");
								text.print(Integer.toString(gold) + " gold", GOLD);
								text.print("!");
								player.setGold(player.getGold() + gold);
								
								Item item = enemy.getRandomDrop();
								
								if(!item.getName().equals(emptyItem.getName())){
									text.printTextAddLine("The enemy dropped ");
									text.print(item.getDisplayName(), item.getColor());
									text.print(".");
									text.printText("Do you want to pick it up?");
									text.printYN();
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
								if(!player.isImmuneToStatusEffect(effect)){
									player.addStatusEffect(effect);
									
									text.printText("You got a ");
									text.print(effectName, effect.isDebuff() ? Color.RED : DARK_GREEN);
									text.print(" effect!");
								}
								else{
									text.printText("You got a ");
									text.print(effectName, effect.isDebuff() ? Color.RED : DARK_GREEN);
									text.print(" effect, but you are ");
									text.print("immune", effect.isDebuff() ? DARK_GREEN : Color.RED);
									text.print("!");
								}
							}
							else{
								if(!enemy.isImmuneToStatusEffect(effect)){
									enemy.addStatusEffect(effect);
									
									text.printText("The enemy got a ");
									text.print(effectName, effect.isDebuff() ? DARK_GREEN : Color.RED);
									text.print(" effect!");
								}
								else{
									text.printText("The enemy got a ");
									text.print(effectName, effect.isDebuff() ? DARK_GREEN : Color.RED);
									text.print(" effect, but it is ");
									text.print("immune", effect.isDebuff() ? Color.RED : DARK_GREEN);
									text.print("!");
								}
							}
						}
						
						return true;
					}
				}
			}
		}
		else if(isSpecialAttack && player.hasStatusEffect(5)){
			text.printText("You are imprisoned!", Color.RED);
			return false;
		}
		else if(isSpecialAttack && player.getMagic() < attacks[attackNum].getUserBasedInt()){
			text.printText("Not enough magic!", Color.RED);
			return false;
		}
		
		return false;
	}
	
	
	//Extra functions
	public void updatePlayer(boolean updateStatusEffects){
		
		player.updatePlayer();
		
		if(!player.isPlayerAlive()){
			playerDeath();
		}
		
		if(updateStatusEffects){
			updatePlayerStatusEffects();
		}
	}
	
	public void updatePlayerStatusEffects(){
		
		StatusEffect[] endedEffects = player.updateStatusEffects(true);
		
		for(int i = 0; i < player.getStatusEffects().length; i++){
			if(endedEffects[i].getType() != emptyEffect.getType()){
				
				String effectName = endedEffects[i].getName();
				
				text.printTextAddLine("Your " + effectName + " effect wore off!");
			}
		}
	}
	
	public void playerDeath(){
		text.printTextAddLine(new String[]{"You have died!", "Exiting game. You can re-open and load from a previous save."});
		sound.playSound("death01.wav");
		waitSeconds(6, true);
		System.exit(0);
	}
	
	public void printStats(){
		text.printTextAddLine("You have ");
		text.print(Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()) + " health", Color.RED);
		text.print(", ");
		text.print(Integer.toString(player.getMagic()) + "/" + Integer.toString(player.getMaxMagic()) + " magic", Color.BLUE);
		text.print(", and ");
		text.print(Integer.toString(player.getDefense()) + " defense", Color.DARK_GRAY);
		text.print(".");
		
		if(player.hasStatusEffects()){
			text.printText("You also have these status effects:");
			
			StatusEffect[] effects = player.getStatusEffects();
			
			for(int i = 0; i < effects.length; i++){
				if(effects[i].getType() != (emptyEffect.getType())){
					String plural = "s";
					
					if(effects[i].getLength() == 1){
						plural = "";
					}
					
					text.printText("Level " + effects[i].getLevel() + " ");
					text.print(effects[i].getName(), effects[i].isDebuff() ? Color.RED : DARK_GREEN);
					text.print(" with " + effects[i].getLength() + " turn" + plural + " remaining");
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
			else if(waitEnabled && !ignoreWaitEnabled){
				TimeUnit.MILLISECONDS.sleep(milliseconds);
			}
			
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void setWaitEnabled(boolean waitEnabled){
		this.waitEnabled = waitEnabled;
	}
	
	private void displayTip(){
		text.addLine();
		text.printText("-TIP-: " + tips[random.nextInt(tips.length)], new Color(0, 128, 0));
	}
	
	//Getters and setters used by GameHandler
	public boolean getWaitEnabled(){
		return waitEnabled;
	}
	
	public AreaHandler getAreaHandler(){
		return areaHandler;
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
	
	public void setAreaHandler(AreaHandler areaHandler){
		this.areaHandler = areaHandler;
	}
	
	public void setDebugMode(boolean debugMode){
		this.debugMode = debugMode;
	}
	
	public Player getPlayer(){
		return player;
	}
	
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
	
	public void setSoundEnabled(boolean soundEnabled){
		this.soundEnabled = soundEnabled;
		sound.setSoundEnabled(soundEnabled);
	}
	
	public boolean getSoundEnabled(){
		return soundEnabled;
	}
	
	public void setWindow(Window window){
		this.window = window;
		text.setWindow(window);
		list.setWindow(window);
	}
	
	public Enemy getLastEnemyKilled(){
		return lastEnemyKilled;
	}
}
