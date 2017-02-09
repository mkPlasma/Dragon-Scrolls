package game;

import java.awt.Color;

public class GameStart{
	
	Save save = new Save();
	
	private boolean classChosenSuccessful = false;
	
	private Area area = null;
	private Area[] accessibleAreas = null;
	
	boolean saveLoaded = false;
	
	public void start(){
		
		Text.print("Welcome to ");
		Text.print("Dragon Scrolls", new Color(96, 96, 0));
		Text.print("!");
		Text.printLine("Type \"start\"");
		
		int stringChosen = Text.testInput(new String[] {"start", "debug"});
		
		if(stringChosen == 0){
			
			save.saveSelect(true);
			
			if(save.getSaveLoaded() == 0 || save.newSaveCreated()){
				waitSelect();
				soundSelect();
				classSelect();
			}
			else{
				area = save.readSaveAreas(save.getSaveLoaded())[0];
				
				accessibleAreas = save.readSaveAreas(save.getSaveLoaded()).clone();
				Area[] areaTemp = new Area[accessibleAreas.length - 1];
				
				for(int i = 0; i < areaTemp.length; i++){
					areaTemp[i] = accessibleAreas[i + 1];
				}
				
				accessibleAreas = areaTemp.clone();
				
				save.readSave(save.getSaveLoaded());
				classChosenSuccessful = true;
				saveLoaded = true;
			}
		}
		else if(stringChosen == 1){
			Settings.setWaitEnabled(false);

			new Player();
			
			Player.setClassType(0);
			Player.setLevel(999);
			Player.setGold(99999);
			
			Player.setHealth(99999);
			Player.setMagic(99999);
			
			Player.updatePlayer();
			
			Player.loadDefaultInventory();
			
			Text.printLine("Debug mode is active.");
			classChosenSuccessful = true;
			Settings.setDebugEnabled(true);
		}
		else{
			System.exit(0);
		}
	}
	
	private void waitSelect(){
		Text.printLineExtra("Enable waiting?");
		Text.printYN();
		Text.printLine(new String[]{"Waiting will create short pauses in the game for added effect.", "If waiting is off, all text will appear immediately."});
		
		while(true){
			int stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
			
			switch(stringChosen){
				case 0: case 1:
					Text.printLine("Waiting has been enabled.");
					Settings.setWaitEnabled(true);
					return;
				
				case 2: case 3:
					Text.printLine("Waiting has been disabled.");
					Settings.setWaitEnabled(false);
					return;
				
				default:
					Text.printLine("Input not recognized, please try again");
					break;
			}
		}
	}
	
	private void soundSelect(){
		Text.printLineExtra("Enable sounds?");
		Text.printYN();
		
		while(true){
			int stringChosen = Text.testInput(new String[]{"yes", "y", "no", "n"});
			
			switch(stringChosen){
				case 0: case 1:
					Text.printLine("Sounds have been enabled.");
					Settings.setSoundEnabled(true);
					return;
				
				case 2: case 3:
					Text.printLine("Sounds have been disabled.");
					Settings.setSoundEnabled(false);
					return;
				
				default:
					Text.printLine("Input not recognized, please try again");
					break;
			}
		}
	}
	
	private void classSelect(){
		
		int classType = -1;
		
		do{
			Text.printLineExtra(new String[]{"Select a class.", "Type its name for more info."});
			Text.printLine("Knight", Color.DARK_GRAY);
			Text.printLine("Archer", new Color(0, 128, 0));
			Text.printLine("Mage", Color.BLUE);
	
			int stringChosen = Text.testInput(new String[] {"knight", "archer", "mage"});
			
			switch(stringChosen){
				case 0:
					Text.printLine("Knight", Color.DARK_GRAY);
					Text.print(": The knight is a good fighter and a tank. He has plenty of health, but is lacking in magic. " +
					"Because of his reliance on high damage and health, he is easy to start out with, but may not do as well later on.");
					
					Text.printLineExtra("Stats:");
					Text.printLine("Starting HP: 200", Color.RED);
					Text.printLine("Starting MP: 100", Color.BLUE);
					Text.printLine("Starting DEF: 8", Color.DARK_GRAY);
					
					Text.addLine();
					Text.printLine(new String[]{"Attacks:", "Main: The knight uses a sword, which can do a lot of damage, however, it is easily avoided.",
					"Special: The knight can use his shield to temporarily increase his defense."});
					
					classType = 0;
					break;
					
				case 1:
					Text.printLine("Archer", new Color(0, 128, 0));
					Text.print(": The archer is a well-rounded class with even health and magic. He is evenly focused and will do well throughout the adventure.");
					
					Text.printLineExtra("Stats:");
					Text.printLine("Starting HP: 150", Color.RED);
					Text.printLine("Starting MP: 150", Color.BLUE);
					Text.printLine("Starting DEF: 5", Color.DARK_GRAY);
					
					Text.addLine();
					Text.printLine(new String[]{"Attacks:", "Main: The archer uses a bow, which has low damage, but is difficult to avoid.",
					"Special: The archer can use a power snipe his enemies with a high-damage, unvavoidable arrow."});
	
					classType = 1;
					break;
					
				case 2:
					Text.printLine("Mage", Color.BLUE);
					Text.print(": The mage is mainly focused on magic and spells. He has low health, but has plenty of buffs and debuffs at his fingertips. " +
					"Due to these stats, he is difficult to start with but gains tremendous power throughout the adventure.");
					
					Text.printLineExtra("Stats:");
					Text.printLine("Starting HP: 100", Color.RED);
					Text.printLine("Starting MP: 200", Color.BLUE);
					Text.printLine("Starting DEF: 3", Color.DARK_GRAY);
					
					Text.addLine();
					Text.printLine(new String[]{"Attacks:", "Main: The mage uses a wand with medium damage and medium avoidability.",
					"Special: The mage can cast spells which cause different effects on the enemy based on the spell."});
	
					classType = 2;
					break;
					
				default:
					Text.printLine("Input not recognized, please try again");
					classType = -1;
					break;
			}
		}while(!classSelectQuestion(classType));
	}
	
	private boolean classSelectQuestion(int classType){
		
		if(classType == -1)
			return false;
		
		Text.printLineExtra("Choose this class?");
		Text.printYN();
		
		boolean classChosen = false;
		String className = "default";
		
		switch(classType){
			case 0:
				className = "knight";
				break;
			
			case 1:
				className = "archer";
				break;
			
			case 2:
				className = "mage";
				break;
		}
		
		while(!classChosen){
			
			int stringChosen = Text.testInput(new String[] {"yes", "y", "no", "n"});
			
			switch(stringChosen){
				case 0: case 1:
					Text.printLineExtra("You chose the " + className + ".");
					
					new Player();
					
					Player.setClassType(classType);
					Player.setGold(100);
					
					Player.setHealth(200);
					Player.setMagic(200);
					
					Player.updatePlayer();
					
					Player.loadDefaultInventory();
					
					classChosenSuccessful = true;
					classChosen = true;
					
					return true;
				
				case 2: case 3:
					Text.printLine("Choose another class.");
					classChosen = true;
					return false;
				
				default:
					Text.printLine("Input not recognized, please try again");
					break;
			}
		}
		
		return false;
	}
	
	public boolean getClassChosenSuccessful(){
		return classChosenSuccessful;
	}
	
	public Save getSave(){
		return save;
	}
	
	public Area getArea(){
		return area;
	}
	
	public Area[] getAccessibleAreas(){
		return accessibleAreas;
	}
	
	public boolean isSaveLoaded(){
		return saveLoaded;
	}
}
