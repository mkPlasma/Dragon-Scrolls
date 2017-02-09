package game;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class Interaction{
	
	private final int type;
	//0 - Give items
	//1 - Give health/magic
	//2 - Give max health/max magic
	//3 - Give gold
	//4 - Enemy encounter
	
	private int uses;
	
	private final String[] names;
	private final String eventText, endText;
	
	private Item item;
	
	private int health, magic, gold;
	
	private boolean waitEnabled;
	private final int waitTime;
	
	private Enemy enemy;
	
	private String sound;
	
	public Interaction(int uses, String[] names, String eventText, String endText, int waitTime, Item item, String sound){
		type = 0;
		
		this.uses = uses;
		this.names = names;
		this.eventText = eventText;
		this.endText = endText;
		this.waitTime = waitTime;
		this.sound = sound;
		
		this.item = item;
	}
	
	public Interaction(int type, int uses, String[] names, String eventText, String endText, int waitTime, int health, int magic, String sound){
		this.type = type;
		
		this.uses = uses;
		this.names = names;
		this.eventText = eventText;
		this.endText = endText;
		this.waitTime = waitTime;
		this.sound = sound;
		
		this.health = health;
		this.magic = magic;
	}
	
	public Interaction(int uses, String[] names, String eventText, String endText, int waitTime, int gold, String sound){
		type = 3;
		
		this.uses = uses;
		this.names = names;
		this.eventText = eventText;
		this.endText = endText;
		this.waitTime = waitTime;
		this.sound = sound;
		
		this.gold = gold;
	}
	
	public Interaction(int uses, String[] names, String eventText, String endText, int waitTime, Enemy enemy, String sound){
		type = 4;

		this.uses = uses;
		this.names = names;
		this.eventText = eventText;
		this.endText = endText;
		this.waitTime = waitTime;
		this.sound = sound;
		
		this.enemy = enemy;
	}
	
	public void setWaitEnabled(boolean waitEnabled){
		this.waitEnabled = waitEnabled;
	}
	
	public String[] getNames(){
		return names;
	}
	
	public void interact(){
		
		boolean used = false;
		
		if(uses > 0 || uses == -1){
			Text.printLineExtra(eventText);
			waitSeconds(waitTime, false);
			
			switch(type){
				case 0:
					
					Text.printLineExtra("You found ");
					Text.print(item.getDisplayName(), item.getColor());
					Text.print(".");
					Text.printLine("Do you want to pick it up?");
					
					break;
					
				case 1:
					
					String healthRestored = Integer.toString(Math.min(health, Player.getMaxHealth() - Player.getHealth()));					
					String magicRestored = Integer.toString(Math.min(magic, Player.getMaxMagic() - Player.getMagic()));
					
					Player.setHealth(Player.getHealth() + health);
					Player.setMagic(Player.getMagic() + magic);
					
					if(health > 0){
						if(magic > 0){
							Text.printLine("It healed ");
							Text.print(healthRestored + " health ", Color.RED);
							Text.print("and ");
							Text.print(magicRestored + " magic", Color.BLUE);
							Text.print(".");
							Text.print("You now have ");
							Text.print(Integer.toString(Player.getHealth()) + "/" + Integer.toString(Player.getMaxHealth()) + " health ", Color.RED);
							Text.print("and ");
							Text.print(Integer.toString(Player.getMagic()) + "/" + Integer.toString(Player.getMaxMagic()) + " magic", Color.BLUE);
							Text.print(".");
						}
						else{
							Text.printLine("It healed ");
							Text.print(healthRestored + " health", Color.RED);
							Text.print(".");
							Text.print("You now have ");
							Text.print(Integer.toString(Player.getHealth()) + "/" + Integer.toString(Player.getMaxHealth()) + " health", Color.RED);
							Text.print(".");
						}
					}
					else if(magic > 0){
						Text.printLine("It healed ");
						Text.print(magicRestored + " magic", Color.BLUE);
						Text.print(".");
						Text.print("You now have ");
						Text.print(Integer.toString(Player.getMagic()) + "/" + Integer.toString(Player.getMaxMagic()) + " magic", Color.BLUE);
						Text.print(".");
					}
					
					if(health > 0 && Player.getHealth() == Player.getMaxHealth() || magic > 0 && Player.getMagic() == Player.getMaxMagic()){
						used = true;
					}
					
					break;
				
				case 2:
					
					Player.setBaseMaxHealth(Player.getBaseMaxHealth() + health);
					Player.setBaseMaxMagic(Player.getBaseMaxMagic() + magic);
					
					if(health > 0){
						if(magic > 0){
							Text.printLine(new String[]{"Your maximum health increased by " + Integer.toString(health) + " and your maximum magic increased by " + Integer.toString(magic) + "," +
							" leaving you with " + Integer.toString(Player.getMaxHealth()) + " maximum health and " + Integer.toString(Player.getMaxMagic()) + " maximum magic!"});
						}
						else{
							Text.printLine(new String[]{"Your maximum health increased by " + Integer.toString(health) + "," +
							" leaving you with " + Integer.toString(Player.getMaxHealth()) + " maximum health!"});
						}
					}
					else if(magic > 0){
						Text.printLine(new String[]{"Your maximum magic increased by " + Integer.toString(magic) + "," +
						" leaving you with " + Integer.toString(Player.getMaxMagic()) + " maximum magic!"});
					}
					
					used = true;
					
					break;
				
				case 3:
					Player.setGold(Player.getGold() + gold);
					Text.printLine("You obtained " + Integer.toString(gold) + " gold!");
					used = true;
					
					break;
			}
			
			if(uses > 0 && used){
				uses--;
			}
		}
		else{
			Text.printLineExtra(endText);
		}
	}
	
	private void waitSeconds(int seconds, boolean ignoreWaitEnabled){
		try{
			if(waitEnabled && !ignoreWaitEnabled){
				TimeUnit.SECONDS.sleep(seconds);
			}
			
			if(ignoreWaitEnabled){
				TimeUnit.SECONDS.sleep(seconds);
			}
			
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public int getType(){
		return type;
	}
	
	public Item getItem(){
		return item;
	}
	
	public Enemy getEnemy(){
		return enemy;
	}
	
	public String getSound(){
		return sound;
	}
}
