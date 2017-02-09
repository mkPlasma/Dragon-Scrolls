package game;

import java.util.Arrays;
import java.util.Random;

public class Player{
	
	private static final Random random = new Random();
	
	private static int classType = -1;
	
	
	//Base max variables are "real" maximums
	//Max variables include status effects
	private static int baseMaxHealth;
	private static int health;
	private static int maxHealth;
	
	private static int baseMaxMagic;
	private static int magic;
	private static int maxMagic;
	
	private static int baseDefense;
	private static int defense;
	
	private static int gold;
	
	private static double damageMultiplier = 1.0;
	
	private static int level = 1;
	private static int xp = 0;
	private static int xpReq = 50;
	
	private static boolean playerAlive = true;
	
	private static final Item emptyItem = new Item();
	private static final StatusEffect emptyEffect = new StatusEffect();
	
	//0 - Normal Weapon
	//1 - Special Weapon
	//2 - Armor
	//Others - Inventory
	private static Item[] inventory = new Item[23];
	private static int[] inventoryItemCount = new int[23];
	
	private static StatusEffect[] statusEffects = new StatusEffect[20];
	
	public Player(){
		Arrays.fill(inventory, emptyItem);
		Arrays.fill(statusEffects, emptyEffect);
	}
	
	// Setters
	
	public static void setBaseMaxHealth(int baseMaxHealth){
		Player.baseMaxHealth = baseMaxHealth;
	}
	
	public static void setHealth(int health){
		Player.health = health;
		updatePlayer();
	}
	
	public static void setMaxHealth(int maxHealth){
		Player.maxHealth = maxHealth;
	}
	
	public static void setBaseMaxMagic(int baseMaxMagic){
		Player.baseMaxMagic = baseMaxMagic;
	}
	
	public static void setMagic(int magic){
		Player.magic = magic;
		updatePlayer();
	}
	
	public static void setMaxMagic(int maxMagic){
		Player.maxMagic = maxMagic;
	}
	
	public static void setDefense(int defense){
		Player.defense = defense;
	}
	
	public static void setGold(int gold){
		Player.gold = gold;
	}

	public static void setClassType(int classType){
		Player.classType = classType;
	}

	public static void setXP(int xp){
		Player.xp = xp;
		updatePlayer();
	}
	
	public static void setXPReq(int xpReq){
		Player.xpReq = xpReq;
		updatePlayer();
	}
	
	public static void setLevel(int level){
		Player.level = level;
		updatePlayer();
	}
	
	
	// Inventory
	
	public static void setInventory(Item[] inventory){
		Player.inventory = inventory;
	}
	
	public static void setInventoryItemCount(int[] inventoryItemCount){
		Player.inventoryItemCount = inventoryItemCount;
	}

	public static void setInventorySpace(int space){
		
		Item[] tempInventory = inventory.clone();
		int[] tempInventoryItemCount = inventoryItemCount.clone();
		
		inventory = new Item[3];
		inventoryItemCount = new int[3];
		
		for(int i = 0; i < space + 3; i++){
			
			Item[] invTemp = new Item[inventory.length + 1];
			int[] countTemp = new int[inventoryItemCount.length + 1];
			
			for(int i2 = 0; i2 < inventory.length; i2++){
				invTemp[i2] = inventory[i2];
				countTemp[i2] = inventoryItemCount[i2];
			}
			
			invTemp[invTemp.length - 1] = emptyItem;
			countTemp[countTemp.length - 1] = 0;
			
			inventory = invTemp.clone();
			inventoryItemCount = countTemp.clone();
			
			if(i < tempInventory.length){
				inventory[i] = tempInventory[i];
				inventoryItemCount[i] = tempInventoryItemCount[i];
			}
		}
	}
	
	
	
	
	
	// Getters
	
	public static int getBaseMaxHealth(){
		return baseMaxHealth;
	}
	
	public static int getMaxHealth(){
		return maxHealth;
	}
	
	public static int getHealth(){
		return health;
	}
	
	public static int getBaseMaxMagic(){
		return baseMaxMagic;
	}
	
	public static int getMaxMagic(){
		return maxMagic;
	}
	
	public static int getMagic(){
		return magic;
	}
	
	public static int getDefense(){
		updatePlayer();
		return defense;
	}
	
	public static int getGold(){
		return gold;
	}
	
	public static int getClassType(){
		return classType;
	}
	
	public static double getDamageMultiplier(){
		return damageMultiplier;
	}
	
	public static String getClassTypeName(){
		switch(classType){
			case -1:
				return "default";
				
			case 0:
				return "knight";
			
			case 1:
				return "archer";
			
			case 2:
				return "mage";
			
			default:
				return "invalid";
		}
	}

	public static int getXP(){
		return xp;
	}
	
	public static int getXPReq(){
		return xpReq;
	}
	
	public static int getLevel(){
		return level;
	}
	
	public static boolean getRandomHitFail(){

		int level = getStatusEffectLevel(18);
		
		if(level > 0 && random.nextInt(99) + 1 <= level*5){
			return true;
		}
		
		return false;
	}
	
	
	//Inventory

	public static Item[] getInventory(){
		return inventory;
	}
	
	public static int[] getInventoryItemCount(){
		return inventoryItemCount;
	}
	
	public static Item getItemInInventorySlot(int slot){
		return inventory[slot];
	}
	
	public static int getItemCountInInventorySlot(int slot){
		return inventoryItemCount[slot];
	}
	
	public static int getItemCountInInventory(Item item){
		
		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].getName().equals(item.getName())){
				return inventoryItemCount[i];
			}
		}
		
		return -1;
	}

	public static int getInventoryItemCount(Item item, boolean searchEntireInventory){
		
		int iOffset = 3;
		
		if(searchEntireInventory){
			iOffset = 0;
		}
		
		for(int i = iOffset; i < inventory.length; i++){
			if(inventory[i].getName().equals(item.getName())){
				return inventoryItemCount[i];
			}
		}
		
		return 0;
	}
	
	public static Item[] getTypeInInventory(int type, boolean searchEntireInventory){
		
		int iOffset = 3;
		
		if(searchEntireInventory){
			iOffset = 0;
		}
		
		int itemCount = 0;
		
		for(int i = iOffset; i < inventory.length - iOffset; i++){
			if(inventory[i].getType() == type){
				itemCount++;
			}
		}
		
		Item[] foundItems = new Item[itemCount];
		int itemNum = 0;
		
		for(int i = iOffset; i < inventory.length - iOffset; i++){
			if(inventory[i].getType() == type){
				foundItems[itemNum] = inventory[i];
				itemNum++;
			}
		}
		
		return foundItems;
	}
	
	public static Item[] getUsableItemsInInventory(){
		
		int itemCount = 0;

		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].canUse()){
				itemCount++;
			}
		}
		
		Item[] foundItems = new Item[itemCount];
		int itemNum = 0;
		
		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].canUse()){
				foundItems[itemNum] = inventory[i];
				itemNum++;
			}
		}
		
		return foundItems;
	}
	
	public static int[] getUsableItemCountInInventory(){
		
		int itemCount = 0;

		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].canUse()){
				itemCount++;
			}
		}
		
		int[] foundItems = new int[itemCount];
		int itemNum = 0;
		
		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].canUse()){
				foundItems[itemNum] = inventoryItemCount[i];
				itemNum++;
			}
		}
		
		return foundItems;
	}
	
	public static Item[] getEquipableItemsInInventory(){
		
		int itemCount = 0;

		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].getType() == 0 || inventory[i].getType() == 1 || inventory[i].getType() == 2){
				itemCount++;
			}
		}
		
		Item[] foundItems = new Item[itemCount];
		int itemNum = 0;
		
		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].getType() == 0 || inventory[i].getType() == 1 || inventory[i].getType() == 2){
				foundItems[itemNum] = inventory[i];
				itemNum++;
			}
		}
		
		return foundItems;
	}
	
	public static String[] getInventoryContentsInString(){
		
		int itemNum = 0;
		
		for(int i = 3; i < inventory.length; i++){
			if(!inventory[i].getName().equals(emptyItem.getName())){
				itemNum++;
			}
		}

		String[] foundItems = new String[itemNum];
		
		itemNum = 0;
		
		for(int i = 3; i < inventory.length; i++){
			if(!inventory[i].getName().equals(emptyItem.getName())){
				foundItems[itemNum] = inventory[i].getName();
				itemNum++;
			}
		}
		
		return foundItems;
	}

	public static boolean isInventoryFull(){
		
		int itemNum = 0;
		
		for(int i = 3; i < inventory.length; i++){
			if(!inventory[i].getName().equals(emptyItem.getName())){
				itemNum++;
			}
		}
		
		if(itemNum == inventory.length){
			return true;
		}
		
		return false;
	}
	
	
	//Status Effects
	
	public static StatusEffect[] getStatusEffects(){
		return statusEffects;
	}
	
	public static boolean hasStatusEffect(int effect){
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() == effect){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean hasStatusEffects(){
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() != 0){
				return true;
			}
		}
		
		return false;
	}
	
	public static int getStatusEffectLevel(int effect){
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() == effect){
				return statusEffects[i].getLevel();
			}
		}
		
		return -1;
	}

	public static boolean isImmuneToStatusEffect(StatusEffect statusEffect){
		return inventory[2].isImmuneToStatusEffect(statusEffect);
	}
	
	
	
	
	// Other Inventory Functions
	
	public static void loadDefaultInventory(){
		switch(classType){
		case 0:
			inventory[0] = ObjectList.getNormalWeaponItemList()[0];
			inventory[1] = ObjectList.getSpecialWeaponItemList()[0];
			inventory[2] = ObjectList.getArmorItemList()[0];
			break;
		
		case 1:
			inventory[0] = ObjectList.getNormalWeaponItemList()[16];
			inventory[1] = ObjectList.getSpecialWeaponItemList()[16];
			inventory[2] = ObjectList.getArmorItemList()[15];
			break;
		
		case 2:
			inventory[0] = ObjectList.getNormalWeaponItemList()[32];
			inventory[1] = ObjectList.getSpecialWeaponItemList()[32];
			inventory[2] = ObjectList.getArmorItemList()[30];
			break;
		}
		
		inventoryItemCount[0] = 1;
		inventoryItemCount[1] = 1;
		inventoryItemCount[2] = 1;
		
		baseDefense = inventory[2].getArmorStats()[0];
		
		inventory[3] = ObjectList.getConsumableItemList()[0];
		inventoryItemCount[3] = 3;
		
		updatePlayer();
	}
	
	public static boolean searchForItemInInventory(Item item, boolean searchEntireInventory){
		
		int iOffset = 3;
		
		if(searchEntireInventory){
			iOffset = 0;
		}
		
		for(int i = iOffset; i < inventory.length; i++){
			if(inventory[i].getName().equals(item.getName()) && inventoryItemCount[i] > 0){
				return true;
			}
		}
		return false;
	}
	
	public static void addItemToInventory(Item item){
		
		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].getName().equals(item.getName()) && inventoryItemCount[i] < item.getMaxStack()){
				inventoryItemCount[i] += 1;
				return;
			}
		}
		
		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].getName().equals(emptyItem.getName())){
				inventory[i] = item;
				inventoryItemCount[i] = 1;
				return;
			}
		}
	}
	
	public static void removeItemFromInventory(Item item){
		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].getName().equals(item.getName()) && inventoryItemCount[i] > 0){
				inventoryItemCount[i] -= 1;
				
				if(inventoryItemCount[i] == 0){
					inventory[i] = emptyItem;
					
					for(int j = i; j < inventory.length - 1; j++){
						inventory[j] = inventory[j + 1];
						inventoryItemCount[j] = inventoryItemCount[j + 1];
					}
						
					inventory[inventory.length - 1] = emptyItem;
				}
			}
		}
	}
	
	public static void switchItemsInInventory(Item item, int slot){
		for(int i = 0; i < inventory.length; i++){
			if(inventory[i].getName().equals(item.getName())){
				Item tempItem = inventory[slot];
				inventory[slot] = inventory[i];
				inventory[i] = tempItem;
			}
		}
		
		baseDefense = inventory[2].getArmorStats()[0];
		maxHealth = baseMaxHealth + inventory[2].getArmorStats()[1];
		maxMagic = baseMaxMagic + inventory[2].getArmorStats()[2];
		
		updatePlayer();
	}
	
	public static void switchItemsInInventory(Item item, Item itemToSwitch){
		
		int slot = 0;
		boolean itemFound = false;
		
		for(int i = 0; i < inventory.length; i++){
			if(inventory[i].equals(itemToSwitch) && !itemFound){
				slot = i;
				itemFound = true;
			}
		}
		
		for(int i = 0; i < inventory.length; i++){
			if(inventory[i].getName().equals(item.getName())){
				Item tempItem = inventory[slot];
				inventory[slot] = inventory[i];
				inventory[i] = tempItem;
			}
		}
		
		baseDefense = inventory[2].getArmorStats()[0];
		maxHealth = baseMaxHealth + inventory[2].getArmorStats()[1];
		maxMagic = baseMaxMagic + inventory[2].getArmorStats()[2];
		
		updatePlayer();
	}
	
	public static int getOpenInventorySlots(){
		
		int itemNum = 0;
		
		for(int i = 3; i < inventory.length; i++){
			if(!inventory[i].getName().equals(emptyItem.getName())){
				itemNum++;
			}
		}
		
		return itemNum;
	}
	
	public static int getOpenInventorySlots(Item item){
		
		int itemNum = 0;
		
		for(int i = 3; i < inventory.length; i++){
			
			if(!inventory[i].getName().equals(emptyItem.getName())){
				itemNum += item.getMaxStack();
			}
			
			if(inventory[i].getName().equals(item.getName())){
				itemNum += (inventoryItemCount[i] - item.getMaxStack());
			}
		}
		
		return itemNum;
	}
	
	public static void addStatusEffect(StatusEffect statusEffect){
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() == emptyEffect.getType() || statusEffects[i].getType() == statusEffect.getType()){
				
				if(statusEffects[i].getType() == emptyEffect.getType()){
					statusEffects[i] = statusEffect;
				}
				else{
					if(statusEffect.getLevel() > statusEffects[i].getLevel()){
						statusEffects[i].setLevel(statusEffect.getLevel());
						statusEffects[i].setLength(statusEffect.getLength());
					}
				}
				
				return;
			}
		}
	}
	
	
	// Misc functions
	public static void updatePlayer(){
		
		baseDefense = inventory[2].getArmorStats()[0];
		defense = baseDefense;
		
		int hp = 200;
		int mp = 100;
		
		int hpm = 8;
		int mpm = 6;
		
		if(classType == 1){
			hp = 150;
			mp = 150;
			
			hpm = 7;
			mpm = 7;
		}
		
		if(classType == 2){
			hp = 100;
			mp = 200;
			
			hpm = 6;
			mpm = 8;
		}
		
		xpReq = (int)(50 + Math.pow(level - 1, 1.8));
		
		while(xp > xpReq){
			xp -= xpReq;
			level++;
			xpReq = (int)(50 + Math.pow(level - 1, 1.8));
		}
		
		baseMaxHealth = hp + (level - 1)*hpm;
		baseMaxMagic = mp + (level - 1)*mpm;
		
		maxHealth = baseMaxHealth + inventory[2].getArmorStats()[1];
		maxMagic = baseMaxMagic + inventory[2].getArmorStats()[2];
		
		if(health > maxHealth)
			health = maxHealth;
		
		if(magic > maxMagic)
			magic = maxMagic;
		else if(magic < 0)
			magic = 0;
		
		if(health <= 0){
			playerAlive = false;
		}
		else{
			playerAlive = true;
		}
		
		updateStatusEffects(false);
	}
	
	
	public static boolean isPlayerAlive(){
		return playerAlive;
	}
	
	
	public static void addXP(int xp){
		
		Player.xp += xp;
		updatePlayer();
	}

	
	public static void restoreStats(){
		
		updatePlayer();
		
		health += baseMaxHealth/(5 + (classType*2));
		magic += baseMaxMagic/(9 - (classType*3));
		
		if(health > maxHealth)
			health = maxHealth;
		
		if(magic > maxMagic)
			magic = maxMagic;
	}
	
	
	// Status Effect Functions
	
	public static StatusEffect[] updateStatusEffects(boolean fullUpdate){
		
		// Full Update will modify health, magic, and other values
		//
		// Otherwise, max health, max magic, and defense are updated without reducing effect time
		
		StatusEffect[] endedEffects = new StatusEffect[20];
		Arrays.fill(endedEffects, emptyEffect);
		
		damageMultiplier = 1.0;
		
		for(int i = 0; i < statusEffects.length; i++){
			
			if(fullUpdate){
				statusEffects[i].updateLength();
			}
			
			if(statusEffects[i].getType() != emptyEffect.getType() && statusEffects[i].getLength() <= 0){
				endedEffects[i] = statusEffects[i];
				statusEffects[i] = emptyEffect;
			}
			
			switch(statusEffects[i].getType()){
				case 1:
					if(fullUpdate){
						health += statusEffects[i].getLevel();
						Text.printLine("You were healed by " + Integer.toString(statusEffects[i].getLevel()) + " from your regeneration effect!");
					}
					break;
				
				case 2:
					if(fullUpdate){
						magic += statusEffects[i].getLevel();
						Text.printLine("Your magic was restored by " + Integer.toString(statusEffects[i].getLevel()) + " from your regeneration effect!");
					}
					break;
				
				case 3:
					defense = baseDefense + statusEffects[i].getLevel();
					break;
				
				case 4:
					damageMultiplier += (0.05*statusEffects[i].getLevel());
					break;
	
				case 8: case 9:
					if(fullUpdate){
						health -= statusEffects[i].getLevel();
	
						if(statusEffects[i].getType() == 8){
							Text.printLine("You took " + Integer.toString(statusEffects[i].getLevel()) + " damage from bleeding!");
						}
						else{
							Text.printLine("You took " + Integer.toString(statusEffects[i].getLevel()) + " damage from burning!");
						}
					}
					
					break;
				
				case 10: case 12:
					if(fullUpdate){
						magic -= statusEffects[i].getLevel();
	
						if(statusEffects[i].getType() == 10){
							Text.printLine("Your magic was drained by " + Integer.toString(statusEffects[i].getLevel()) + " from being poisoned!");
						}
						else{
							Text.printLine(Integer.toString(statusEffects[i].getLevel()) + " of your magic was stolen!");
						}
					}
					
					break;
				
				case 11:
					damageMultiplier -= (0.05*statusEffects[i].getLevel());
					break;
				
				case 13:
					defense = baseDefense - statusEffects[i].getLevel();
					break;
				
				case 14:
					maxHealth = baseMaxHealth + inventory[2].getArmorStats()[1] + statusEffects[i].getLevel();
					break;
				
				case 15:
					maxMagic = baseMaxMagic + inventory[2].getArmorStats()[2] + statusEffects[i].getLevel();
					break;
				
				case 16:
					maxHealth = baseMaxHealth + inventory[2].getArmorStats()[1] - statusEffects[i].getLevel();
					break;
				
				case 17:
					maxMagic = baseMaxMagic + inventory[2].getArmorStats()[2] - statusEffects[i].getLevel();
					break;
			}
		}
		
		return endedEffects;
	}
	
	
	public static void setStatusEffects(StatusEffect[] statusEffects){
		Player.statusEffects = statusEffects;
	}
	
}
