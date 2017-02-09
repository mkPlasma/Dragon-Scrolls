package game;

import java.awt.Color;

public class GameHandler{
	
	private GameStart gameStart;
	
	private CommandHandler commandHandler = new CommandHandler();
	
	private Save save;
	
	private boolean tutorialComplete = false;
	
	private int gameTicks = 0;
	
	private int prevUpdateTicks = -1;
	private int updateTicks = 0;
	
	public void start(){
		gameStart = new GameStart();
		gameStart.start();
		
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
		
		if(gameStart.isSaveLoaded()){
			AreaHandler.setArea(gameStart.getArea());
			AreaHandler.setAccessibleAreas(gameStart.getAccessibleAreas());
			tutorialComplete = save.readTutorial(save.getSaveLoaded());
		}
		else{
			AreaHandler.setArea(ObjectList.getAreaList()[1]);
			AreaHandler.setAccessibleAreas(new Area[] {ObjectList.getAreaList()[1]});
			
			if(Settings.debugEnabled()){
				AreaHandler.setAccessibleAreas(ObjectList.getAreaList());
			}
		}
		
		if(!Settings.debugEnabled()){
			Text.printLine("Saving...");
			save.setTutorialComplete(tutorialComplete);
			
			if(save.newSaveCreated()){
				save.save(save.getSaveLoaded(), save.getSaveName(), AreaHandler.getArea(), AreaHandler.getAccessibleAreas());
			}
			else{
				save.save(save.getSaveLoaded(), save.getSaveName(save.getSaveLoaded()), AreaHandler.getArea(), AreaHandler.getAccessibleAreas());
			}
			
			Text.printLine("Game saved.");
		}
		
		if(save.newSaveCreated()){
			Text.addLine();
			Text.printLine("-TIP-: Use the \"help\" command. You will learn useful information from it.", new Color(0, 128, 0));
			Text.addLine();
			Text.printLineExtra(new String[] {"Welcome to Forestry Town!", "You are now free to input commands!", "Type 'help' for details.", "You can also use 'back' or 'b' to exit menus."});
		}
		else{
			Text.printLineExtra("You are currently in ");
			Text.print(AreaHandler.getArea().getName(), AreaHandler.getArea().getColor());
			Text.print(".");
		}
		
		while(true){
			commandHandler.commands();
			update();
		}
	}
	
	public void update(){
		
		prevUpdateTicks = updateTicks;
		gameTicks++;
		
		if(commandHandler.incrementUpdateTick()){
			updateTicks++;
		}
		
		if(updateTicks > prevUpdateTicks){
			commandHandler.updateRestCounter();
			encounter(commandHandler.getRandomizeEncounter());
		}
		
		if(gameTicks >= 5 && !tutorialComplete && !Settings.debugEnabled()){
			// Tutorial
			
			commandHandler.enemyEncounter(ObjectList.getEnemyList()[0]);
			tutorialComplete = true;
			
			commandHandler.setTutorialComplete(tutorialComplete);
			Text.printLineExtra(new String[]{"You have completed your first battle with an enemy! You can type 'in healer' to heal yourself.", "You can now access "});
			Text.print(ObjectList.getAreaList()[2].getName(), ObjectList.getAreaList()[2].getColor());
			Text.print("!");
			
			AreaHandler.addAccessibleArea(ObjectList.getAreaList()[2]);
		}
		
		if(commandHandler.getLastEnemyKilled() != null){
			if(!AreaHandler.isAreaAccessible(ObjectList.getAreaList()[3]) && commandHandler.getLastEnemyKilled().getEnemyName().equals(ObjectList.getEnemyList()[7].getEnemyName())){
				// Killed Goblin Leader, unlocks Spectral Forest
				
				Text.printLineExtra("You can now access ");
				Text.print(ObjectList.getAreaList()[3].getName(), ObjectList.getAreaList()[3].getColor());
				Text.print("!");
	
				AreaHandler.addAccessibleArea(ObjectList.getAreaList()[3]);
			}
			else if(!AreaHandler.isAreaAccessible(ObjectList.getAreaList()[5]) && commandHandler.getLastEnemyKilled().getEnemyName().equals(ObjectList.getEnemyList()[20].getEnemyName())){
				// Killed Ghost King, unlocks Vibrancy Town, Diamond River, and Bluesteel Mineshaft
				
				Text.printLineExtra("You can now access ");
				Text.print(ObjectList.getAreaList()[5].getName(), ObjectList.getAreaList()[5].getColor());
				Text.print(", ");
				Text.print(ObjectList.getAreaList()[6].getName(), ObjectList.getAreaList()[6].getColor());
				Text.print(", and ");
				Text.print(ObjectList.getAreaList()[7].getName(), ObjectList.getAreaList()[7].getColor());
				Text.print("!");

				AreaHandler.addAccessibleArea(ObjectList.getAreaList()[5]);
				AreaHandler.addAccessibleArea(ObjectList.getAreaList()[6]);
				AreaHandler.addAccessibleArea(ObjectList.getAreaList()[7]);
			}
		}
	}
	
	public void encounter(boolean randomEncounter){
		Enemy[] enemies = AreaHandler.getArea().getEncounters();
		Enemy enemy = null;
		
		if(!commandHandler.getRandomizeEncounter() || (RandomGen.randomChance(AreaHandler.getArea().getEncounterChance()) && commandHandler.getRandomizeEncounter())){
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
