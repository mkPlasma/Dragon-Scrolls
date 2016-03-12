package game;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class Enemy{
	
	private final Random random = new Random();
	

	private final String enemyName;
	
	private final int encounterRate;
	private final int escapeRate;
	

	private final int baseMaxHealth;
	private int maxHealth;
	private int health;
	
	private final int baseDefense;
	private int defense;
	
	private double damageMultiplier = 1.0;
	
	
	private boolean attacksFirst;
	
	private final Attack[] attacks;
	
	
	private final Item emptyItem = new Item();
	
	private final Item[] drops;
	private final int[] dropRates;
	private final int dropChance;
	
	private final int minGold, maxGold;
	private final int minXP, maxXP;
	
	
	private final StatusEffect emptyEffect = new StatusEffect();
	
	private StatusEffect[] statusEffects = new StatusEffect[20];
	private final int[] statusEffectImmunities;
	
	
	private final String sound;
	private final String hitSound;
	
	private final Color color;
	
	
	public Enemy(int encounterRate, String enemyName, int health, int maxHealth, int baseDefense, int escapeRate, boolean attacksFirst, Attack[] attacks,
	Item[] drops, int[] dropRates, int dropChance, int minGold, int maxGold, int minXP, int maxXP, int[] statusEffectImmunities, String sound, String hitSound, Color color){
		
		Arrays.fill(statusEffects, emptyEffect);
		
		this.encounterRate = encounterRate;
		this.enemyName = enemyName;
		
		this.health = health;
		this.maxHealth = maxHealth;
		baseMaxHealth = this.maxHealth;
		
		this.baseDefense = baseDefense;
		
		this.escapeRate = escapeRate;
		
		this.attacksFirst = attacksFirst;
		this.attacks = attacks;
		
		this.drops = drops;
		this.dropRates = dropRates;
		this.dropChance = dropChance;
		
		this.minGold = minGold;
		this.maxGold = maxGold;
		this.minXP = minXP;
		this.maxXP = maxXP;
		
		this.statusEffectImmunities = statusEffectImmunities;
		
		this.sound = sound;
		this.hitSound = hitSound;
		
		this.color = color;
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public void setDefense(int defense){
		this.defense = defense;
	}
	
	
	public int getEncounterRate(){
		return encounterRate;
	}
	
	public String getEnemyName(){
		return enemyName;
	}
	
	public int getHealth(){
		updateEnemy();
		return health;
	}
	
	public int getMaxHealth(){
		updateEnemy();
		return maxHealth;
	}
	
	public int getDefense(){
		updateEnemy();
		return defense;
	}
	
	public Attack[] getAttacks(){
		return attacks;
	}
	
	public int getEscapeRate(){
		return escapeRate;
	}
	
	public Item[] getDrops(){
		return drops;
	}
	
	public int[] getDropRates(){
		return dropRates;
	}
	
	public int getMinGold(){
		return minGold;
	}
	
	public int getMaxGold(){
		return maxGold;
	}
	
	public String getSound(){
		return sound;
	}
	
	public String getHitSound(){
		return hitSound;
	}
	
	public Color getColor(){
		return color;
	}
	
	public boolean getRandomEncounter(){
		if(random.nextInt(99) + 1 <= encounterRate)
			return true;
		
		return false;
	}
	
	public Item getRandomDrop(){
		
		if(random.nextInt(99) + 1 <= dropChance){
			Item[] items = new Item[drops.length];
			Arrays.fill(items, emptyItem);
			
			if(items.length > 0){
				
				for(int i = 0; i < drops.length; i++){
					if(random.nextInt(99) + 1 <= dropRates[i]){
						items[i] = drops[i];
					}
				}
				
				int itemNum = 0;
				int chance = 101;
				
				for(int i = 0; i < drops.length; i++){
					if(items[i] != emptyItem){
						if(dropRates[i] < chance){
							itemNum = i;
						}
						
						chance = dropRates[i];
					}
				}
				
				return items[itemNum];
			}
		}
		
		return emptyItem;
	}
	
	public int getRandomGold(){
		int temp = random.nextInt((maxGold - minGold) + 1) + minGold;
		return temp;
	}

	public int getRandomXP(){
		int temp = random.nextInt((maxXP - minXP) + 1) + minXP;
		return temp;
	}
	
	public void updateEnemy(){
		updateStatusEffects(false);
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
	
	public StatusEffect[] updateStatusEffects(boolean updateLength){
		
		StatusEffect[] endedEffects = new StatusEffect[20];
		Arrays.fill(endedEffects, emptyEffect);
		
		damageMultiplier = 1.0;
		
		for(int i = 0; i < statusEffects.length; i++){

			if(updateLength){
				statusEffects[i].updateLength();
			}
			
			if(statusEffects[i].getType() != emptyEffect.getType() && statusEffects[i].getLength() <= 0){
				endedEffects[i] = statusEffects[i];
			}
			
			switch(statusEffects[i].getType()){
			case 1:
				health += statusEffects[i].getLevel();
				break;
			
			case 3:
				defense = baseDefense + statusEffects[i].getLevel();
				break;
			
			case 4:
				damageMultiplier += (0.05*statusEffects[i].getLevel());
				break;

			case 8: case 9:
				health -= statusEffects[i].getLevel();
				break;
			
			case 11:
				damageMultiplier -= (0.05*statusEffects[i].getLevel());
				break;
			
			case 13:
				defense = baseDefense - statusEffects[i].getLevel();
				break;
			
			case 14:
				maxHealth = baseMaxHealth + statusEffects[i].getLevel();
				break;
			
			case 16:
				maxHealth = baseMaxHealth - statusEffects[i].getLevel();
				break;
			}
		}
		
		return endedEffects;
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
	
	public int[] getStatusEffectImmunities(){
		return statusEffectImmunities;
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

	
	public boolean attacksFirst(){
		return attacksFirst;
	}
}
