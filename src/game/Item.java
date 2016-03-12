package game;

import java.awt.Color;
import java.util.Arrays;

public class Item{
	
	private final int itemType;
	//0 - Weapon
	//1 - Special
	//2 - Armor
	//3 - Consumable
	//4 - Other
	
	private final String itemName, displayName;
	
	private final int maxStack;
	boolean canUse = false;
	
	int classType;
	//0 - Knight
	//1 - Archer
	//2 - Mage
	//3 - All
	
	Attack[] weaponAttacks;
	
	int[] armorStats = {0, 0, 0};
	//0 - Defense buff
	//1 - Health buff
	//2 - Magic buff
	
	int[] statusEffectImmunities;
	
	int[] consumableStats = {0, 0};
	//0 - Health heal
	//1 - Magic heal
	
	private final StatusEffect emptyEffect = new StatusEffect();
	private StatusEffect[] statusEffects = new StatusEffect[1];
	
	private final String sound;
	private String useSound;
	
	private final Color color;
	
	private int sellPrice;
	
	public Item(){
		this(0, "EMPTY", "EMPTY", 0, false, "", "", Color.BLACK, 0);
	}
	
	public Item(int classType, String itemName, String displayName, int maxStack, boolean canUse, String sound, String useSound, Color color, int sellPrice){
		itemType = 4;
		
		Arrays.fill(statusEffects, emptyEffect);
		
		this.classType = classType;
		this.itemName = itemName;
		this.displayName = displayName;
		this.maxStack = maxStack;
		this.canUse = canUse;
		this.sound = sound;
		this.useSound = useSound;
		this.color = color;
		this.sellPrice = sellPrice;
	}
	
	public Item(int itemType, int classType, String itemName, String displayName, int maxStack, Attack[] weaponAttacks, String sound, Color color){
		Arrays.fill(statusEffects, emptyEffect);
		
		this.itemType = itemType;
		this.classType = classType;
		this.itemName = itemName;
		this.displayName = displayName;
		this.maxStack = maxStack;
		this.sound = sound;
		this.color = color;
		
		this.weaponAttacks = weaponAttacks;
	}
	
	public Item(int classType, String itemName, String displayName, int maxStack, int armorDefense, int armorHealth, int armorMagic, int[] statusEffectImmunities, String sound, Color color){
		itemType = 2;
		
		Arrays.fill(statusEffects, emptyEffect);
		
		this.classType = classType;
		this.itemName = itemName;
		this.displayName = displayName;
		this.maxStack = maxStack;
		this.sound = sound;
		this.color = color;

		armorStats[0] = armorDefense;
		armorStats[1] = armorHealth;
		armorStats[2] = armorMagic;
		
		this.statusEffectImmunities = statusEffectImmunities;
	}
	
	public Item(int classType, String itemName, String displayName, int maxStack, int consumableHealth, int consumableMagic, String sound, String useSound, Color color){
		itemType = 3;
		
		Arrays.fill(statusEffects, emptyEffect);
		
		this.classType = classType;
		this.itemName = itemName;
		this.displayName = displayName;
		this.maxStack = maxStack;
		this.sound = sound;
		this.useSound = useSound;
		this.color = color;
		
		consumableStats[0] = consumableHealth;
		consumableStats[1] = consumableMagic;
		
		canUse = true;
	}
	
	public Item(int classType, String itemName, String displayName, int maxStack, int consumableHealth, int consumableMagic, StatusEffect statusEffect,  String sound, String useSound, Color color){
		itemType = 3;
		
		this.classType = classType;
		this.itemName = itemName;
		this.displayName = displayName;
		this.maxStack = maxStack;
		this.sound = sound;
		this.useSound = useSound;
		this.color = color;
		
		consumableStats[0] = consumableHealth;
		consumableStats[1] = consumableMagic;
		
		this.statusEffects[0] = statusEffect;
		
		canUse = true;
	}
	
	public Item(int classType, String itemName, String displayName, int maxStack, int consumableHealth, int consumableMagic, StatusEffect[] statusEffects,  String sound, String useSound, Color color){
		itemType = 3;
		
		this.classType = classType;
		this.itemName = itemName;
		this.displayName = displayName;
		this.maxStack = maxStack;
		this.sound = sound;
		this.useSound = useSound;
		this.color = color;
		
		consumableStats[0] = consumableHealth;
		consumableStats[1] = consumableMagic;
		
		this.statusEffects = statusEffects;
		
		canUse = true;
	}
	
	public void setArmorStats(int defense, int health, int magic){
		armorStats[0] = defense;
		armorStats[1] = health;
		armorStats[2] = magic;
	}
	
	public void setConsumableStats(int health, int magic){
		consumableStats[0] = health;
		consumableStats[1] = magic;
	}
	
	public int getType(){
		return itemType;
	}
	
	public String getName(){
		return itemName;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public int getMaxStack(){
		return maxStack;
	}
	
	public boolean canUse(){
		return canUse;
	}
	
	public int getClassType(){
		return classType;
	}
	
	public Attack[] getWeaponAttacks(){
		return weaponAttacks;
	}
	
	public int[] getArmorStats(){
		return armorStats;
	}
	
	public int[] getConsumableStats(){
		return consumableStats;
	}
	
	public StatusEffect[] getStatusEffects(){
		return statusEffects;
	}
	
	public String getSound(){
		return sound;
	}
	
	public String getUseSound(){
		return useSound;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getSellPrice(){
		return sellPrice;
	}
	
	public boolean hasStatusEffects(){
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() != 0){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isImmuneToStatusEffect(StatusEffect effect){

		if(statusEffectImmunities.length <= 0){
			return false;
		}
		
		for(int i = 0; i < statusEffectImmunities.length; i++){
			if(effect.getType() == statusEffectImmunities[i]){
				return true;
			}
		}
		
		return false;
	}
	
	
	public int getStatusEffectCount(){
		
		int statusEffectsFound = 0;
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() != 0){
				statusEffectsFound++;
			}
		}
		
		return statusEffectsFound;
	}
}