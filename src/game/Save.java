package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Save{
	
	ObjectList list = new ObjectList();
	Text text = new Text();
	
	int saveLoaded = 1;
	String saveName;
	
	boolean newSaveCreated = false;
	
	boolean tutorialComplete = false;
	
	Window window;
	
	//Save format
	//
	//Save Name
	//
	//Area
	//
	//Player class
	//
	//Player level
	//player xp
	//
	//Player health
	//Player magic
	//Player gold
	//
	//inv_start
	//Inventory contents
	//inv_end
	//
	//status_start
	//Status Effects
	//status_end
	//
	//areas_start
	//Unlocked Areas
	//areas_end
	
	public void saveSelect(boolean displayText){
		
		String fileDir = System.getProperty("user.dir");
		
		File folder = new File("saves");
		
		if(!folder.exists()){
			try{
				folder.mkdir();
			}
			catch(Exception e){}
		}
		
		File[] saves = new File(fileDir +  "\\saves\\").listFiles();
		int[] savesAvailable = new int[1];
		
		if(saves.length > 0){
			if(displayText){
				text.printTextAddLine(new String[] {"Load from which save? (Enter a number)", "Type 'new' to create a new save.", "Type 'delete' to delete a save file.", ""});
				
				for(int i = 0; i < saves.length; i++){
					if(saves[i].getName().startsWith("save_") && saves[i].getName().endsWith(".txt")){
						
						String fileName = saves[i].getName();
						fileName = fileName.replace("save_", "");
						fileName = fileName.replace(".txt", "");
						
						int fileNum = 0;
						
						try{
							fileNum = Integer.parseInt(fileName);
						}
						catch(Exception e){}
						
						savesAvailable[i] = fileNum;
						int[] temp = new int[savesAvailable.length + 1];
						
						for(int j = 0; j < savesAvailable.length; j++){
							temp[j] = savesAvailable[j];
						}
						
						savesAvailable = temp.clone();
						
						text.printText(getSaveInfo(fileNum));
					}
				}
			}
			
			boolean answered = false;
			
			while(!answered){
				
				int saveNum = -1;
				
				String stringChosen = text.testInput();
				
				if(stringChosen.equals("new")){
					createNewSave(false);
					answered = true;
				}
				else if(stringChosen.equals("delete")){
					text.printTextAddLine("Delete which save? (Enter a number)");
					boolean answered2 = false;
					
					while(!answered2){
						try{
							String stringChosen2 = text.testInput();
							saveNum = Integer.parseInt(stringChosen2);
							answered2 = true;
							
							text.printText("Are you sure you want to permanently delete " + getSaveInfo(saveNum) + "?");
							text.printYN();
							boolean answered3 = false;
							
							while(!answered3){
								
								int stringChosen3 = text.testInput(new String[] {"yes", "y", "no", "n"});
								
								switch(stringChosen3){
								case 0: case 1:
									
									try{
										String fileNum = Integer.toString(saveNum);
										String fileName = "\\saves\\" + "save_" + fileNum + ".txt";
										Path path = Paths.get(fileDir + fileName);
										Files.deleteIfExists(path);
										
										text.printText("File deleted.");
									}
									catch(Exception e){
										e.printStackTrace();
									}
									
									answered3 = true;
									answered = true;
									saveSelect(true);
									break;
								
								case 2: case 3:
									text.printText("File not deleted.");
									answered3 = true;
									answered = true;
									saveSelect(true);
									break;
							
								default:
									text.printText("Input not recognized, please try again");
									break;
								}
							}
						}
						catch(Exception e){
							text.printText(new String[] {"Input is not a number or is in text form.", "Please try again."});
						}
					}
				}
				else{
					try{
						saveNum = Integer.parseInt(stringChosen);
						
						boolean found = false;
						
						for(int i = 0; i < savesAvailable.length; i++){
							if(saveNum == savesAvailable[i]){
								found = true;
							}
						}
						
						if(found){
							saveLoaded = saveNum;
							answered = true;
						}
						else{
							text.printText("Save file does not exist. Please try again.");
							saveSelect(false);
							return;
						}
					}
					catch(Exception e){
						text.printText(new String[] {"Input is not a number or is in text form.", "Please try again."});
					}
				}
			}
		}
		else{
			createNewSave(true);
		}
	}
	
	public void createNewSave(boolean overrideSaveNum){
		
		newSaveCreated = true;
		
		String fileDir = System.getProperty("user.dir");
		
		File[] saves = new File(fileDir +  "\\saves\\").listFiles();
		
		text.printTextAddLine("Enter a save name:");
		saveName = text.testInput();
		
		boolean saveNumFound = false;
		int saveNum = 0;
		
		if(!overrideSaveNum){
			for(int i = 0; i < saves.length; i++){
				if(saves[i].getName().startsWith("save_") && saves[i].getName().endsWith(".txt") && !saveNumFound){
					
					String fileName = saves[i].getName();
					fileName = fileName.replace("save_", "");
					fileName = fileName.replace(".txt", "");
					
					int fileNum = 0;
					
					try{
						fileNum = Integer.parseInt(fileName);
						
						String fileName2 = saves[i + 1].getName();
						fileName2 = fileName2.replace("save_", "");
						fileName2 = fileName2.replace(".txt", "");
						int fileNum2 = Integer.parseInt(fileName2);
						
						if(fileNum2 != 0){
							if(fileNum + 1 != fileNum2){
								saveNumFound = true;
								saveNum = fileNum + 1;
							}
						}
					}
					catch(ArrayIndexOutOfBoundsException e){
						saveNum = fileNum + 1;
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			
			if(saveNum != 0){
				text.printText("Save file created sucessfully.");
			}
			else{
				text.printTextAddLine(new String[] {"Save file not correctly created.", "Try ordering your save files so the" +
				"numbers are not out of order, i.e:", "Change \"save_1.txt\" \"save_2.txt\" \"save_5.txt\" \"save_7.txt\"",
				"to \"save_1.txt\" \"save_2.txt\" \"save_3.txt\" \"save_4.txt\"."});
			}
		}
		else{
			saveNum = 0;
			text.printText("Save file created sucessfully.");
		}
		
		saveLoaded = saveNum;
	}
	
	public Area[] readSaveAreas(int saveNum){
		
		if(saveLoaded == 0){
			saveLoaded = 1;
		}
		if(saveNum == 0){
			saveNum = 1;
		}
		
		Area[] areas = new Area[1];
		
		String fileNum = Integer.toString(saveNum);
		String fileName = "\\saves\\" + "save_" + fileNum + ".txt";
		String fileDir = System.getProperty("user.dir");
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDir + fileName));
			
			for(int i = 0; i < 2; i++){
				bufferedReader.readLine();
			}
			
			areas[0] = list.getAreaList()[Integer.parseInt(bufferedReader.readLine())];
			Area[] areasTemp = new Area[1];
			
			int areaNum = 1;
			
			String lastLine = "default";
			
			while(!lastLine.equals("areas_start")){
				lastLine = bufferedReader.readLine();
			}
			
			while(lastLine != "areas_end"){
				
				areasTemp = new Area[areas.length + 1];
				
				for(int i = 0; i < areas.length; i++){
					areasTemp[i] = areas[i];
				}
				
				lastLine = bufferedReader.readLine();
				
				if(lastLine.equals("areas_end")){
					break;
				}
				
				areasTemp[areaNum] = list.getAreaList()[Integer.parseInt(lastLine)];
				
				areas = areasTemp.clone();
				
				areaNum++;
			}
			bufferedReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return areas;
	}
	
	public boolean readTutorial(int saveNum){
		
		if(saveLoaded == 0){
			saveLoaded = 1;
		}
		if(saveNum == 0){
			saveNum = 1;
		}
		
		boolean tutorialComplete = false;
		
		String fileNum = Integer.toString(saveNum);
		String fileName = "\\saves\\" + "save_" + fileNum + ".txt";
		String fileDir = System.getProperty("user.dir");
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDir + fileName));
			
			String lastLine = "";
			
			while(!lastLine.equals("tutorial")){
				lastLine = bufferedReader.readLine();
			}
			
			if(Integer.parseInt(bufferedReader.readLine()) == 1){
				tutorialComplete = true;
			}
			
			
			bufferedReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return tutorialComplete;
	}
	
	public Player readSave(int saveNum){
		
		if(saveLoaded == 0){
			saveLoaded = 1;
		}
		if(saveNum == 0){
			saveNum = 1;
		}
		
		Player player = new Player();
		
		String fileNum = Integer.toString(saveNum);
		String fileName = "\\saves\\" + "save_" + fileNum + ".txt";
		String fileDir = System.getProperty("user.dir");
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDir + fileName));
			
			for(int i = 0; i < 3; i++){
				bufferedReader.readLine();
			}
			
			player.setClassType(Integer.parseInt(bufferedReader.readLine()));
			
			player.setLevel(Integer.parseInt(bufferedReader.readLine()));
			player.setXP(Integer.parseInt(bufferedReader.readLine()));
			
			player.updatePlayer();
			
			player.setHealth(Integer.parseInt(bufferedReader.readLine()));
			player.setMagic(Integer.parseInt(bufferedReader.readLine()));
			player.setGold(Integer.parseInt(bufferedReader.readLine()));
			
			player.updatePlayer();
			
			final Item emptyItem = new Item();
			
			Item[] inventory = {emptyItem};
			int[] inventoryItemCount = new int[1];
			Item[] invTemp = new Item[inventory.length + 1];
			int[] invCountTemp = new int[inventoryItemCount.length + 1];
			
			int itemNum = 0;
			
			bufferedReader.readLine();
			
			String lastLine = "";
			
			while(lastLine != "inv_end"){
				
				if(itemNum > 0){
					invTemp = new Item[inventory.length + 1];
					invCountTemp = new int[inventoryItemCount.length + 1];
					
					for(int i = 0; i < inventory.length; i++){
						invTemp[i] = inventory[i];
						invCountTemp[i] = inventoryItemCount[i];
					}
				}
				
				Item[] itemList = list.getNormalWeaponItemList();
				
				lastLine = bufferedReader.readLine();
				
				if(lastLine.equals("inv_end")){
					break;
				}
				
				int listNum = Integer.parseInt(lastLine);
				
				switch(listNum){
					case 1:
						itemList = list.getSpecialWeaponItemList();
						break;
	
					case 2:
						itemList = list.getArmorItemList();
						break;
	
					case 3:
						itemList = list.getConsumableItemList();
						break;
	
					case 4:
						itemList = list.getMiscItemList();
						break;
				}
				
				int itemNum2 = Integer.parseInt(bufferedReader.readLine());
				Item item;
				
				if(itemNum2 == -1){
					item = new Item();
				}
				else{
					item = itemList[itemNum2];
				}
				
				invTemp[itemNum] = item;
				
				invCountTemp[itemNum] = Integer.parseInt(bufferedReader.readLine());
				
				inventory = invTemp.clone();
				inventoryItemCount = invCountTemp.clone();
				
				if(inventory[inventory.length - 1] == null){
					invTemp = new Item[inventory.length - 1];
					invCountTemp = new int[inventoryItemCount.length - 1];
					
					for(int i = 0; i < invTemp.length; i++){
						invTemp[i] = inventory[i];
						invCountTemp[i] = inventoryItemCount[i];
					}
					
					inventory = invTemp.clone();
					inventoryItemCount = invCountTemp.clone();
				}
				
				itemNum++;
			}
			
			player.setInventory(inventory);
			player.setInventoryItemCount(inventoryItemCount);
			player.updatePlayer();
			
			final StatusEffect emptyEffect = new StatusEffect();
			
			StatusEffect[] statusEffects = new StatusEffect[20];
			Arrays.fill(statusEffects, emptyEffect);
			
			int effectNum = 0;
			
			bufferedReader.readLine();
			
			while(lastLine != "stats_end"){
				
				lastLine = bufferedReader.readLine();
				
				if(lastLine.equals("stats_end")){
					break;
				}
				
				statusEffects[effectNum].setType(Integer.parseInt(lastLine));
				statusEffects[effectNum].setLength(Integer.parseInt(bufferedReader.readLine()));
				statusEffects[effectNum].setLevel(Integer.parseInt(bufferedReader.readLine()));
				
				effectNum++;
			}
			
			player.setStatusEffects(statusEffects);
			player.updatePlayer();
			
			bufferedReader.close();
		}
		catch(Exception e){
			text.printTextAddLine("Error while reading from save file!");
			e.printStackTrace();
		}
		
		return player;
	}
	
	public void save(int saveNum, String saveName, Area area, Area[] accessibleAreas, Player player){
		
		if(saveLoaded == 0){
			saveLoaded = 1;
		}
		if(saveNum == 0){
			saveNum = 1;
		}
		
		String fileNum = Integer.toString(saveNum);
		String fileName = "\\saves\\" + "save_" + fileNum + ".txt";
		String fileDir = System.getProperty("user.dir");
		
		File folder = new File("saves");
		
		if(!folder.exists()){
			try{
				folder.mkdir();
			}
			catch(Exception e){}
		}
		
		try{
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileDir + fileName));
			
			bufferedWriter.write("DO NOT MODIFY THIS FILE. IT MAY CAUSE ERRORS WHILE LOADING THE SAVE.");
			bufferedWriter.newLine();
			
			bufferedWriter.write(saveName);
			bufferedWriter.newLine();
			
			bufferedWriter.write(Integer.toString(list.getAreaInfo(area)));
			bufferedWriter.newLine();
			
			bufferedWriter.write(Integer.toString(player.getClassType()));
			bufferedWriter.newLine();
			
			bufferedWriter.write(Integer.toString(player.getLevel()));
			bufferedWriter.newLine();
			
			bufferedWriter.write(Integer.toString(player.getXP()));
			bufferedWriter.newLine();
			
			bufferedWriter.write(Integer.toString(player.getHealth()));
			bufferedWriter.newLine();
			
			bufferedWriter.write(Integer.toString(player.getMagic()));
			bufferedWriter.newLine();
			
			bufferedWriter.write(Integer.toString(player.getGold()));
			bufferedWriter.newLine();
			
			bufferedWriter.write("inv_start");
			bufferedWriter.newLine();
			
			for(int i = 0; i < player.getInventory().length; i++){
				int[] itemData = list.getItemInfo(player.getInventory()[i]);
				
				bufferedWriter.write(Integer.toString(itemData[0]));
				bufferedWriter.newLine();
				
				bufferedWriter.write(Integer.toString(itemData[1]));
				bufferedWriter.newLine();
				
				bufferedWriter.write(Integer.toString(player.getInventoryItemCount()[i]));
				bufferedWriter.newLine();
			}
			bufferedWriter.write("inv_end");
			bufferedWriter.newLine();
			
			bufferedWriter.write("stats_start");
			bufferedWriter.newLine();
			
			for(int i = 0; i < player.getStatusEffects().length; i++){
				int[] effectData = {player.getStatusEffects()[i].getType(), player.getStatusEffects()[i].getLength(), player.getStatusEffects()[i].getLevel()};
				
				bufferedWriter.write(Integer.toString(effectData[0]));
				bufferedWriter.newLine();
				
				bufferedWriter.write(Integer.toString(effectData[1]));
				bufferedWriter.newLine();
				
				bufferedWriter.write(Integer.toString(effectData[2]));
				bufferedWriter.newLine();
			}
			bufferedWriter.write("stats_end");
			bufferedWriter.newLine();
			
			bufferedWriter.write("areas_start");
			bufferedWriter.newLine();
			
			for(int i = 0; i < accessibleAreas.length; i++){
				int areaData = list.getAreaInfo(accessibleAreas[i]);
				
				bufferedWriter.write(Integer.toString(areaData));
				bufferedWriter.newLine();
			}
			bufferedWriter.write("areas_end");
			bufferedWriter.newLine();
			
			bufferedWriter.write("tutorial");
			bufferedWriter.newLine();
			
			String tutorial = "0";
			
			if(tutorialComplete){
				tutorial = "1";
			}
			
			bufferedWriter.write(tutorial);
			
			bufferedWriter.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getSaveName(int saveNum){
		
		if(saveLoaded == 0){
			saveLoaded = 1;
		}
		if(saveNum == 0){
			saveNum = 1;
		}
		
		String saveName = "default";
		
		String fileNum = Integer.toString(saveNum);
		String fileName = "\\saves\\" + "save_" + fileNum + ".txt";
		String fileDir = System.getProperty("user.dir");
		
		try{
			FileReader fileReader = new FileReader(fileDir + fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			bufferedReader.readLine();
			saveName = bufferedReader.readLine();
			
			bufferedReader.close();
		}
		catch(Exception e){}
		
		return saveName;
	}
	
	public String getSaveInfo(int saveNum){
		
		if(saveLoaded == 0){
			saveLoaded = 1;
		}
		if(saveNum == 0){
			saveNum = 1;
		}
		
		String saveInfo = "default";
		
		String saveName = "default";
		String areaName = "default";
		String className = "default";
		String level = "default";
		
		String fileNum = Integer.toString(saveNum);
		String fileName = "\\saves\\" + "save_" + fileNum + ".txt";
		String fileDir = System.getProperty("user.dir");
		
		try{
			FileReader fileReader = new FileReader(fileDir + fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			bufferedReader.readLine();
			saveName = bufferedReader.readLine();
			areaName = list.getAreaList()[Integer.parseInt(bufferedReader.readLine())].getName();
			className = "Knight";
			
			switch(Integer.parseInt(bufferedReader.readLine())){
				case 1:
					className = "Archer";
					break;
				
				case 2:
					className = "Mage";
					break;
			}
			
			level = bufferedReader.readLine();
			saveInfo = saveName + ": Level " + level + " " + className + ": " + areaName;
			
			bufferedReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return saveInfo;
	}
	
	public void setTutorialComplete(boolean tutorialComplete){
		this.tutorialComplete = tutorialComplete;
	}
	
	public boolean isTutorialComplete(){
		return tutorialComplete;
	}
	
	public int getSaveLoaded(){
		return saveLoaded;
	}
	
	public String getSaveName(){
		return saveName;
	}
	
	public boolean newSaveCreated(){
		return newSaveCreated;
	}
	
	public void setSaveName(String saveName){
		this.saveName = saveName;
	}
	
	public void setCurrentSaveName(){
		saveName = getSaveName(saveLoaded);
	}
	
	public void setWindow(Window window){
		this.window = window;
		list.setWindow(window);
		text.setWindow(window);
	}
}
