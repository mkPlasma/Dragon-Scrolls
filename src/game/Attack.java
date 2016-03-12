package game;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class Attack{
	
	private final int minimumDamage, maximumDamage, hitRate;
	
	private final String attackName;
	
	private final int userBasedInt;

	private StatusEffect[] statusEffects = new StatusEffect[1];
	
	private final Random random = new Random();
	
	private final String sound;
	
	private final Color color;

	public Attack(int minimumDamage, int maximumDamage, int hitRate, String attackName, String sound, Color color){
		this(minimumDamage, maximumDamage, hitRate, attackName, 0, sound, color);
	}
	
	public Attack(int minimumDamage, int maximumDamage, int hitRate, String attackName, StatusEffect statusEffect, String sound, Color color){
		this(minimumDamage, maximumDamage, hitRate, attackName, 0, statusEffect, sound, color);
	}
	
	public Attack(int minimumDamage, int maximumDamage, int hitRate, String attackName, StatusEffect[] statusEffects, String sound, Color color){
		this(minimumDamage, maximumDamage, hitRate, attackName, 0, statusEffects, sound, color);
	}
	
	public Attack(int minimumDamage, int maximumDamage, int hitRate, String attackName, int userBasedInt, String sound, Color color){
		
		Arrays.fill(statusEffects, new StatusEffect());
		
		this.minimumDamage = minimumDamage;
		this.maximumDamage = maximumDamage;
		this.hitRate = hitRate;
		this.attackName = attackName;
		this.userBasedInt = userBasedInt;
		this.sound = sound;
		this.color = color;
	}
	
	public Attack(int minimumDamage, int maximumDamage, int hitRate, String attackName, int userBasedInt, StatusEffect statusEffect, String sound, Color color){

		Arrays.fill(statusEffects, new StatusEffect());
		
		this.minimumDamage = minimumDamage;
		this.maximumDamage = maximumDamage;
		this.hitRate = hitRate;
		this.attackName = attackName;
		this.userBasedInt = userBasedInt;
		this.statusEffects[0] = statusEffect;
		this.sound = sound;
		this.color = color;
	}
	
	public Attack(int minimumDamage, int maximumDamage, int hitRate, String attackName, int userBasedInt, StatusEffect[] statusEffects, String sound, Color color){
		this.minimumDamage = minimumDamage;
		this.maximumDamage = maximumDamage;
		this.hitRate = hitRate;
		this.attackName = attackName;
		this.userBasedInt = userBasedInt;
		this.statusEffects = statusEffects;
		this.sound = sound;
		this.color = color;
	}
	
	public int getMinimumDamage(){
		return minimumDamage;
	}
	
	public int getMaximumDamage(){
		return maximumDamage;
	}
	
	public int getHitRate(){
		return hitRate;
	}
	
	public String getAttackName(){
		return attackName;
	}
	
	public int getUserBasedInt(){
		return userBasedInt;
	}
	
	public StatusEffect[] getStatusEffects(){
		return statusEffects;
	}
	
	public String getSound(){
		return sound;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getRandomDamage(){
		int temp = random.nextInt((maximumDamage - minimumDamage) + 1) + minimumDamage;
		return temp;
	}
	
	public boolean getRandomHit(){
		
		boolean hasHit = false;
		int randomHit = random.nextInt(99) + 1;
		
		if(randomHit <= hitRate){
			hasHit = true;
		}
		
		return hasHit;
	}
	
	public boolean getRandomAttackChance(){
		
		boolean hasAttacked = false;
		int randomAttack = random.nextInt(99) + 1;
		
		if(randomAttack <= userBasedInt){
			hasAttacked = true;
		}
		
		return hasAttacked;
	}
	
	public boolean hasStatusEffects(){
		
		for(int i = 0; i < statusEffects.length; i++){
			if(statusEffects[i].getType() != 0){
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
	
	public StatusEffect getRandomEffect(){
		
		StatusEffect[] effects = new StatusEffect[statusEffects.length];
		Arrays.fill(effects, new StatusEffect());
		
		for(int i = 0; i < statusEffects.length; i++){
			if(random.nextInt(99) + 1 <= statusEffects[i].getChance()){
				effects[i] = statusEffects[i];
			}
		}
		
		int effectNum = 0;
		int chance = 101;
		
		for(int i = 0; i < statusEffects.length; i++){
			if(effects[i].getType() != 0){
				if(statusEffects[i].getChance() < chance){
					effectNum = i;
				}
				
				chance = statusEffects[i].getChance();
			}
		}
		
		return statusEffects[effectNum];
	}
}
