package game;

import java.awt.Color;
import java.util.Random;

public class GameHandler{
	
	Random random = new Random();
	
	GameStart gameStart = new GameStart();
	Text text = new Text();
	ObjectList list = new ObjectList();
	Save save = new Save();
	Window window;
	
	CommandHandler commandHandler = new CommandHandler();
	AreaHandler areaHandler = new AreaHandler();
	
	Player player = new Player();
	
	boolean tutorialComplete = false;
	
	int gameTicks = 0;
	
	int prevUpdateTicks = -1;
	int updateTicks = 0;
	
	public void start(Window window){
		
		this.window = window;
		text.setWindow(window);
		gameStart.start(window);
		
		if(gameStart.getClassChosenSuccessful()){
			startMainGame();
		}
	}
	
	public void startMainGame(){
		
		save = gameStart.getSave();
		
		if(!save.newSaveCreated()){
			save.setCurrentSaveName();
		}
		
		commandHandler.setSave(save);
		
		player = gameStart.getPlayer();
		
		if(gameStart.isSaveLoaded()){
			areaHandler.setArea(gameStart.getArea());
			areaHandler.setAccessibleAreas(gameStart.getAccessibleAreas());
			tutorialComplete = save.readTutorial(save.getSaveLoaded());
		}
		else{
			areaHandler.setArea(list.getAreaList()[1]);
			areaHandler.setAccessibleAreas(new Area[] {list.getAreaList()[1]});
			
			if(gameStart.getDebugMode()){
				areaHandler.setAccessibleAreas(list.getAreaList());
			}
		}
		
		commandHandler.setPlayer(player);
		commandHandler.setWindow(window);

		commandHandler.setWaitEnabled(gameStart.getWaitEnabled());
		commandHandler.setSoundEnabled(gameStart.getSoundEnabled());
		commandHandler.setDebugMode(gameStart.getDebugMode());
		
		commandHandler.setAreaHandler(areaHandler);
		
		if(!gameStart.getDebugMode()){
			text.printText("Saving...");
			save.setTutorialComplete(tutorialComplete);
			
			if(save.newSaveCreated()){
				save.save(save.getSaveLoaded(), save.getSaveName(), areaHandler.getArea(), areaHandler.getAccessibleAreas(), player);
			}
			else{
				save.save(save.getSaveLoaded(), save.getSaveName(save.getSaveLoaded()), areaHandler.getArea(), areaHandler.getAccessibleAreas(), player);
			}
			
			text.printText("Game saved.");
		}
		
		if(save.newSaveCreated()){
			text.addLine();
			text.printText("-TIP-: Use the \"help\" command. You will learn useful information from it.", new Color(0, 128, 0));
			text.addLine();
			text.printTextAddLine(new String[] {"Welcome to Forestry Town!", "You are now free to input commands!", "Type 'help' for details.", "You can also use 'back' or 'b' to exit menus."});
		}
		else{
			text.printTextAddLine("You are currently in ");
			text.print(areaHandler.getArea().getName(), areaHandler.getArea().getColor());
			text.print(".");
		}
		
		while(true){
			commandHandler.commands();
			update();
		}
	}
	
	public void update(){
		
		player = commandHandler.getPlayer();
		
		areaHandler = commandHandler.getAreaHandler();
		prevUpdateTicks = updateTicks;
		gameTicks++;
		
		player.updateStats();
		
		if(commandHandler.incrementUpdateTick()){
			updateTicks++;
		}
		
		if(updateTicks > prevUpdateTicks){
			player.updateStats();
			commandHandler.updateRestCounter();
			encounter(commandHandler.getRandomizeEncounter());
		}
		
		if(gameTicks >= 5 && !tutorialComplete && !gameStart.getDebugMode()){
			commandHandler.enemyEncounter(list.getEnemyList()[0]);
			tutorialComplete = true;
			
			commandHandler.setTutorialComplete(tutorialComplete);
			text.printTextAddLine(new String[]{"You have completed your first battle with an enemy! You can type 'i' then 'healer' to heal yourself.", "You can now access "});
			text.print(list.getAreaList()[2].getName(), list.getAreaList()[2].getColor());
			text.print("!");
			
			areaHandler.addAccessibleArea(list.getAreaList()[2]);
			commandHandler.setAreaHandler(areaHandler);
		}
		
		if(commandHandler.getLastEnemyKilled() != null){
			if(commandHandler.getLastEnemyKilled().getEnemyName().equals(list.getEnemyList()[7].getEnemyName())){
				text.printTextAddLine("You can now access ");
				text.print(list.getAreaList()[3].getName(), list.getAreaList()[3].getColor());
				text.print("!");
	
				areaHandler.addAccessibleArea(list.getAreaList()[3]);
				commandHandler.setAreaHandler(areaHandler);
			}
			else if(commandHandler.getLastEnemyKilled().getEnemyName().equals(list.getEnemyList()[20].getEnemyName())){
				text.printTextAddLine("You can now access ");
				text.print(list.getAreaList()[5].getName(), list.getAreaList()[5].getColor());
				text.print(", ");
				text.print(list.getAreaList()[6].getName(), list.getAreaList()[6].getColor());
				text.print(", and ");
				text.print(list.getAreaList()[7].getName(), list.getAreaList()[7].getColor());
				text.print("!");

				areaHandler.addAccessibleArea(list.getAreaList()[5]);
				areaHandler.addAccessibleArea(list.getAreaList()[6]);
				areaHandler.addAccessibleArea(list.getAreaList()[7]);
				commandHandler.setAreaHandler(areaHandler);
			}
		}
		
		
		commandHandler.setPlayer(player);
		
		commandHandler.setAreaHandler(areaHandler);
	}
	
	public void encounter(boolean randomEncounter){
		Enemy[] enemies = areaHandler.getArea().getEncounters();
		Enemy enemy = null;
		
		if(!commandHandler.getRandomizeEncounter() || (random.nextInt(99) + 1 <= areaHandler.getArea().getEncounterChance() && commandHandler.getRandomizeEncounter())){
			if(enemies.length > 0){
				
				boolean[] isEnemySelected = new boolean[enemies.length];
				
				int enemiesSelected = 0;
				
				while(enemiesSelected == 0){
					for(int i = 0; i < enemies.length; i++){
						isEnemySelected[i] = enemies[i].getRandomEncounter();
					}
					
					for(int i = 0; i < isEnemySelected.length; i++){
						if(isEnemySelected[i]){
							enemiesSelected++;
						}
					}
				}
				
				int chance = 101;
				
				for(int i = 0; i < isEnemySelected.length; i++){
					if(isEnemySelected[i]){
						if(enemies[i].getEncounterRate() < chance){
							enemy = enemies[i];
						}
						
						chance = enemies[i].getEncounterRate();
					}
				}
				
				if(enemy != null){
					commandHandler.enemyEncounter(enemy);
				}
			}
		}
	}
}
