package game;

import java.util.Arrays;
import java.util.Random;

public class Player{
	
	private final Random random = new Random();
	
	private final ObjectList list = new ObjectList();
	private final Text text = new Text();
	
	private int classType = -1;
	
	
	//Base max variables are "real" maximums
	//Max variables include status effects
	private int baseMaxHealth;
	private int health;
	private int maxHealth;
	
	private int baseMaxMagic;
	private int magic;
	private int maxMagic;
	
	private int baseDefense;
	private int defense;
	
	private int gold;
	
	private double damageMultiplier = 1.0;
	
	private int level = 1;
	private int xp = 0;
	private int xpReq = 50;
	
	private boolean playerAlive = true;
	
	private final Item emptyItem = new Item();
	private final StatusEffect emptyEffect = new StatusEffect();
	
	//0 - Normal Weapon
	//1 - Special Weapon
	//2 - Armor
	//Others - Inventory
	private Item[] inventory = new Item[23];
	private int[] inventoryItemCount = new int[23];
	
	private StatusEffect[] statusEffects = new StatusEffect[20];
	
	public Player(){
		Arrays.fill(inventory, emptyItem);
		Arrays.fill(statusEffects, emptyEffect);
	}
	
	public void setBaseMaxHealth(int baseMaxHealth){
		this.baseMaxHealth = baseMaxHealth;
	}
	
	public void setHealth(int health){
		this.health = health;
		updatePlayer();
	}
	
	public void setMaxHealth(int maxHealth){
		this.maxHealth = maxHealth;
	}
	
	public void setBaseMaxMagic(int baseMaxMagic){
		this.baseMaxMagic = baseMaxMagic;
	}
	
	public void setMagic(int magic){
		this.magic = magic;
		updatePlayer();
	}
	
	public void setMaxMagic(int maxMagic){
		this.maxMagic = maxMagic;
	}
	
	public void setDefense(int defense){
		this.defense = defense;
	}
	
	public void setGold(int gold){
		this.gold = gold;
	}
	
	public void setWindow(Window window){
		text.setWindow(window);
	}
	
	
	public int getBaseMaxHealth(){
		return baseMaxHealth;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getBaseMaxMagic(){
		return baseMaxMagic;
	}
	
	public int getMaxMagic(){
		return maxMagic;
	}
	
	public int getMagic(){
		return magic;
	}
	
	public int getDefense(){
		updatePlayer();
		return defense;
	}
	
	public int getGold(){
		return gold;
	}
	
	
	public void setClassType(int classType){
		this.classType = classType;
	}
	
	public int getClassType(){
		return classType;
	}
	
	public double getDamageMultiplier(){
		return damageMultiplier;
	}
	
	public String getClassTypeName(){
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
	
	public void loadDefaultInventory(){
		switch(classType){
		case 0:
			inventory[0] = list.getNormalWeaponItemList()[0];
			inventory[1] = list.getSpecialWeaponItemList()[0];
			inventory[2] = list.getArmorItemList()[0];
			break;
		
		case 1:
			inventory[0] = list.getNormalWeaponItemList()[16];
			inventory[1] = list.getSpecialWeaponItemList()[16];
			inventory[2] = list.getArmorItemList()[15];
			break;
		
		case 2:
			inventory[0] = list.getNormalWeaponItemList()[32];
			inventory[1] = list.getSpecialWeaponItemList()[32];
			inventory[2] = list.getArmorItemList()[30];
			break;
		}
		
		inventoryItemCount[0] = 1;
		inventoryItemCount[1] = 1;
		inventoryItemCount[2] = 1;
		
		baseDefense = inventory[2].getArmorStats()[0];
		
		inventory[3] = list.getConsumableItemList()[0];
		inventoryItemCount[3] = 3;
		
		updatePlayer();
	}
	
	public Item[] getInventory(){
		return inventory;
	}
	
	public int[] getInventoryItemCount(){
		return inventoryItemCount;
	}
	
	public void setInventory(Item[] inventory){
		this.inventory = inventory;
	}
	
	public void setInventoryItemCount(int[] inventoryItemCount){
		this.inventoryItemCount = inventoryItemCount;
	}
	
	public Item getItemInInventorySlot(int slot){
		return inventory[slot];
	}
	
	public int getItemCountInInventorySlot(int slot){
		return inventoryItemCount[slot];
	}
	
	public int getItemCountInInventory(Item item){
		
		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].getName().equals(item.getName())){
				return inventoryItemCount[i];
			}
		}
		
		return -1;
	}
	
	public boolean searchForItemInInventory(Item item, boolean searchEntireInventory){
		
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
	
	public int getInventoryItemCount(Item item, boolean searchEntireInventory){
		
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
	
	public Item[] getTypeInInventory(int type, boolean searchEntireInventory){
		
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
	
	public Item[] getUsableItemsInInventory(){
		
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
	
	public int[] getUsableItemCountInInventory(){
		
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
	
	public Item[] getEquipableItemsInInventory(){
		
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
	
	public String[] getInventoryContentsInString(){
		
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
	
	public void setInventorySpace(int space){
		
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
	
	public void addItemToInventory(Item item){
		
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
	
	public void removeItemFromInventory(Item item){
		for(int i = 3; i < inventory.length; i++){
			if(inventory[i].getName().equals(item.getName()) && inventoryItemCount[i] > 0){
				inventoryItemCount[i] -= 1;
				
				if(inventoryItemCount[i] == 0){
					inventory[i] = emptyItem;
				}
			}
		}
	}
	
	public void switchItemsInInventory(Item item, int slot){
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
	
	public void switchItemsInInventory(Item item, Item itemToSwitch){
		
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
	
	public boolean isInventoryFull(){
		
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
	
	public int getOpenInventorySlots(){
		
		int itemNum = 0;
		
		for(int i = 3; i < inventory.length; i++){
			if(!inventory[i].getName().equals(emptyItem.getName())){
				itemNum++;
			}
		}
		
		return itemNum;
	}
	
	public int getOpenInventorySlots(Item item){
		
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
	
	public boolean isPlayerAlive(){
		return playerAlive;
	}
	
	public void addStatusEffect(StatusEffect statusEffect){
		
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
	
	public void updatePlayer(){
		
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
		
		if(health > maxHealth){
			health = maxHealth;
		}
		
		if(magic > maxMagic){
			magic = maxMagic;
		}
		
		if(health <= 0){
			playerAlive = false;
		}
		else{
			playerAlive = true;
		}
		
		updateStatusEffects(false);
	}
	
	public void addXP(int xp){
		
		this.xp += xp;
		updatePlayer();
	}
	
	public void setXP(int xp){
		this.xp = xp;
		updatePlayer();
	}
	
	public void setXPReq(int xpReq){
		this.xpReq = xpReq;
		updatePlayer();
	}
	
	public void setLevel(int level){
		this.level = level;
		updatePlayer();
	}
	
	public int getXP(){
		return xp;
	}
	
	public int getXPReq(){
		return xpReq;
	}
	
	public int getLevel(){
		return level;
	}
	
	public StatusEffect[] updateStatusEffects(boolean fullUpdate){
		
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
					text.printText("You were healed by " + Integer.toString(statusEffects[i].getLevel()) + " from your regeneration effect!");
				}
				break;
			
			case 2:
				if(fullUpdate){
					magic += statusEffects[i].getLevel();
					text.printText("Your magic was restored by " + Integer.toString(statusEffects[i].getLevel()) + " from your regeneration effect!");
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
						text.printText("You took " + Integer.toString(statusEffects[i].getLevel()) + " damage from bleeding!");
					}
					else{
						text.printText("You took " + Integer.toString(statusEffects[i].getLevel()) + " damage from burning!");
					}
				}
				
				break;
			
			case 10: case 12:
				if(fullUpdate){
					magic -= statusEffects[i].getLevel();

					if(statusEffects[i].getType() == 10){
						text.printText("Your magic was drained by " + Integer.toString(statusEffects[i].getLevel()) + " from being poisoned!");
					}
					else{
						text.printText(Integer.toString(statusEffects[i].getLevel()) + " of your magic was stolen!");
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
	
	public void updateStats(){
		
		updatePlayer();
		
		health += baseMaxHealth/(20 + (classType*5));
		magic += baseMaxMagic/(30 - (classType*5));
	}
	
	public void setStatusEffects(StatusEffect[] statusEffects){
		this.statusEffects = statusEffects;
	}
	
	public StatusEffect[] getStatusEffects(){
		return statusEffects;
	}
	
	public boolean hasStatusEffect(int effect){
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() == effect){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasStatusEffects(){
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() != 0){
				return true;
			}
		}
		
		return false;
	}
	
	public int getStatusEffectLevel(int effect){
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() == effect){
				return statusEffects[i].getLevel();
			}
		}
		
		return -1;
	}
	
	public boolean getRandomHitFail(){

		int level = getStatusEffectLevel(18);
		
		if(level > 0 && random.nextInt(99) + 1 <= level*5){
			return true;
		}
		
		return false;
	}
	
	public boolean isImmuneToStatusEffect(StatusEffect statusEffect){
		return inventory[2].isImmuneToStatusEffect(statusEffect);
	}
}
