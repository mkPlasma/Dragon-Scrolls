package game;

import java.awt.Color;

public class Area{
	
	private final Shopkeeper[] shopkeepers;
	
	private final int encounterChance;
	private final Enemy[] enemyEncounters;
	
	private final String areaName, areaSurroundings;
	private final Interaction[] areaInteractions;
	
	private final Color color;
	
	public Area(String areaName, Shopkeeper[] shopkeepers, int encounterChance, Enemy[] enemyEncounters, String areaSurroundings, Interaction[] areaInteractions, Color color){
		this.areaName = areaName;
		this.shopkeepers = shopkeepers;
		this.encounterChance = encounterChance;
		this.enemyEncounters = enemyEncounters;
		this.areaSurroundings = areaSurroundings;
		this.areaInteractions = areaInteractions;
		this.color = color;
	}
	
	public void setWaitEnabled(boolean waitEnabled){
		for(int i = 0; i < areaInteractions.length; i++){
			areaInteractions[i].setWaitEnabled(waitEnabled);
		}
	}
	
	public Shopkeeper[] getShopkeepers(){
		return shopkeepers;
	}
	
	public int getEncounterChance(){
		return encounterChance;
	}
	
	public Enemy[] getEncounters(){
		return enemyEncounters;
	}
	
	public String getName(){
		return areaName;
	}
	
	public String getSurroundings(){
		return areaSurroundings;
	}
	
	public Interaction[] getInteractions(){
		return areaInteractions;
	}
	
	public Color getColor(){
		return color;
	}
}
