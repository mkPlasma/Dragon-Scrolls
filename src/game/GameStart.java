package game;

import java.awt.Color;

public class GameStart {
	
	Text text = new Text();
	Save save = new Save();
	Player player = new Player();
	
	private boolean classChosenSuccessful = false;
	private boolean debugMode = false;

	private boolean waitEnabled = true;
	private boolean soundEnabled = true;
	
	private Area area = null;
	private Area[] accessibleAreas = null;
	
	boolean saveLoaded = false;
	
	public void start(Window window){
		
		text.setWindow(window);
		save.setWindow(window);
		player.setWindow(window);
		
		text.print("Welcome to ");
		text.print("Dragon Scrolls", new Color(96, 96, 0));
		text.print("!");
		text.printText("Type \"start\"");
		
		int stringChosen = text.testInput(new String[] {"start", "debug"});
		
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
				
				player = save.readSave(save.getSaveLoaded());
				classChosenSuccessful = true;
				saveLoaded = true;
			}
		}
		else if(stringChosen == 1){
			waitEnabled = false;
			
			player.setClassType(0);
			player.setLevel(999);
			player.setGold(99999);
			
			player.setHealth(99999);
			player.setMagic(99999);
			
			player.updatePlayer();
			
			player.loadDefaultInventory();
			
			text.printText("Debug mode is active.");
			classChosenSuccessful = true;
			debugMode = true;
		}
		else{
			System.exit(0);
		}
	}
	
	private void waitSelect(){
		text.printTextAddLine("Enable waiting?");
		text.printYN();
		text.printText(new String[]{"Waiting will create short pauses in the game for added effect.", "If waiting is off, all text will appear immediately."});
		
		while(true){
			int stringChosen = text.testInput(new String[] {"yes", "y", "no", "n"});
			
			switch(stringChosen){
				case 0: case 1:
					text.printText("Waiting has been enabled.");
					waitEnabled = true;
					return;
				
				case 2: case 3:
					text.printText("Waiting has been disabled.");
					waitEnabled = false;
					return;
				
				default:
					text.printText("Input not recognized, please try again");
					break;
			}
		}
	}
	
	private void soundSelect(){
		text.printTextAddLine("Enable sounds?");
		text.printYN();
		
		while(true){
			int stringChosen = text.testInput(new String[] {"yes", "y", "no", "n"});
			
			switch(stringChosen){
				case 0: case 1:
					text.printText("Sounds have been enabled.");
					soundEnabled = true;
					return;
				
				case 2: case 3:
					text.printText("Sounds have been disabled.");
					soundEnabled = false;
					return;
				
				default:
					text.printText("Input not recognized, please try again");
					break;
			}
		}
	}
	
	private void classSelect(){
		
		int classType = -1;
		
		do{
			text.printTextAddLine(new String[]{"Select a class.", "Type its name for more info."});
			text.printText("Knight", Color.DARK_GRAY);
			text.printText("Archer", new Color(0, 128, 0));
			text.printText("Mage", Color.BLUE);
	
			int stringChosen = text.testInput(new String[] {"knight", "archer", "mage"});
			
			switch(stringChosen){
				case 0:
					text.printText("Knight", Color.DARK_GRAY);
					text.print(": The knight is a good fighter and a tank. He has plenty of health, but is lacking in magic. " +
					"Because of his reliance on high damage and health, he is easy to start out with, but may not do as well later on.");
					
					text.printTextAddLine("Stats:");
					text.printText("Starting HP: 200", Color.RED);
					text.printText("Starting MP: 100", Color.BLUE);
					text.printText("Starting DEF: 8", Color.DARK_GRAY);
					
					text.addLine();
					text.printText(new String[]{"Attacks:", "Main: The knight uses a sword, which can do a lot of damage, however, it is easily avoided.",
					"Special: The knight can use his shield to temporarily increase his defense."});
					
					classType = 0;
					break;
					
				case 1:
					text.printText("Archer", new Color(0, 128, 0));
					text.print(": The archer is a well-rounded class with even health and magic. He is evenly focused and will do well throughout the adventure.");
					
					text.printTextAddLine("Stats:");
					text.printText("Starting HP: 150", Color.RED);
					text.printText("Starting MP: 150", Color.BLUE);
					text.printText("Starting DEF: 5", Color.DARK_GRAY);
					
					text.addLine();
					text.printText(new String[]{"Attacks:", "Main: The archer uses a bow, which has low damage, but is difficult to avoid.",
					"Special: The archer can use a power snipe his enemies with a high-damage, unvavoidable arrow."});
	
					classType = 1;
					break;
					
				case 2:
					text.printText("Mage", Color.BLUE);
					text.print(": The mage is mainly focused on magic and spells. He has low health, but has plenty of buffs and debuffs at his fingertips. " +
					"Due to these stats, he is difficult to start with but gains tremendous power throughout the adventure.");
					
					text.printTextAddLine("Stats:");
					text.printText("Starting HP: 100", Color.RED);
					text.printText("Starting MP: 200", Color.BLUE);
					text.printText("Starting DEF: 3", Color.DARK_GRAY);
					
					text.addLine();
					text.printText(new String[]{"Attacks:", "Main: The mage uses a wand with medium damage and medium avoidability.",
					"Special: The mage can cast spells which cause different effects on the enemy based on the spell."});
	
					classType = 2;
					break;
					
				default:
					text.printText("Input not recognized, please try again");
					classType = -1;
					break;
			}
		}while(!classSelectQuestion(classType));
	}
	
	private boolean classSelectQuestion(int classType){
		
		if(classType == -1)
			return false;
		
		text.printTextAddLine("Choose this class?");
		text.printYN();
		
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
			
			int stringChosen = text.testInput(new String[] {"yes", "y", "no", "n"});
			
			switch(stringChosen){
				case 0: case 1:
					text.printTextAddLine("You chose the " + className + ".");
					
					player.setClassType(classType);
					player.setGold(100);
					
					player.setHealth(200);
					player.setMagic(200);
					
					player.updatePlayer();
					
					player.loadDefaultInventory();
					
					classChosenSuccessful = true;
					classChosen = true;
					
					return true;
				
				case 2: case 3:
					text.printText("Choose another class.");
					classChosen = true;
					return false;
				
				default:
					text.printText("Input not recognized, please try again");
					break;
			}
		}
		
		return false;
	}
	
	public boolean getClassChosenSuccessful(){
		return classChosenSuccessful;
	}
	
	public boolean getWaitEnabled(){
		return waitEnabled;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public boolean getDebugMode(){
		return debugMode;
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
	
	public boolean getSoundEnabled(){
		return soundEnabled;
	}
}
