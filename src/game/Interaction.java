package game;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class Interaction{
	
	private Text text = new Text();
	
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
	
	public void setWindow(Window window){
		text.setWindow(window);
	}
	
	public Player interact(Player player){
		
		boolean used = false;
		
		if(uses > 0 || uses == -1){
			text.printTextAddLine(eventText);
			waitSeconds(waitTime, false);
			
			switch(type){
				case 0:
					
					text.printTextAddLine("You found ");
					text.print(item.getDisplayName(), item.getColor());
					text.print(".");
					text.printText("Do you want to pick it up?");
					
					break;
					
				case 1:
					
					String healthRestored = Integer.toString(player.getHealth() - player.getHealth());					
					String magicRestored = Integer.toString(player.getMagic() - player.getMagic());
					
					player.setHealth(player.getHealth() + health);
					player.setMagic(player.getMagic() + magic);
					
					if(health > 0){
						if(magic > 0){
							text.printText("It healed ");
							text.print(healthRestored + " health ", Color.RED);
							text.print("and ");
							text.print(magicRestored + " magic", Color.BLUE);
							text.print(".");
							text.print("You now have ");
							text.print(Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()) + " health ", Color.RED);
							text.print("and ");
							text.print(Integer.toString(player.getMagic()) + "/" + Integer.toString(player.getMaxMagic()) + " magic", Color.BLUE);
							text.print(".");
						}
						else{
							text.printText("It healed ");
							text.print(healthRestored + " health", Color.RED);
							text.print(".");
							text.print("You now have ");
							text.print(Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()) + " health", Color.RED);
							text.print(".");
						}
					}
					else if(magic > 0){
						text.printText("It healed ");
						text.print(magicRestored + " magic", Color.BLUE);
						text.print(".");
						text.print("You now have ");
						text.print(Integer.toString(player.getMagic()) + "/" + Integer.toString(player.getMaxMagic()) + " magic", Color.BLUE);
						text.print(".");
					}
					
					if(health > 0 && player.getHealth() == player.getMaxHealth() || magic > 0 && player.getMagic() == player.getMaxMagic()){
						used = true;
					}
					
					break;
				
				case 2:
					
					player.setBaseMaxHealth(player.getBaseMaxHealth() + health);
					player.setBaseMaxMagic(player.getBaseMaxMagic() + magic);
					
					if(health > 0){
						if(magic > 0){
							text.printText(new String[]{"Your maximum health increased by " + Integer.toString(health) + " and your maximum magic increased by " + Integer.toString(magic) + "," +
							" leaving you with " + Integer.toString(player.getMaxHealth()) + " maximum health and " + Integer.toString(player.getMaxMagic()) + " maximum magic!"});
						}
						else{
							text.printText(new String[]{"Your maximum health increased by " + Integer.toString(health) + "," +
							" leaving you with " + Integer.toString(player.getMaxHealth()) + " maximum health!"});
						}
					}
					else if(magic > 0){
						text.printText(new String[]{"Your maximum magic increased by " + Integer.toString(magic) + "," +
						" leaving you with " + Integer.toString(player.getMaxMagic()) + " maximum magic!"});
					}
					
					used = true;
					
					break;
				
				case 3:
					player.setGold(player.getGold() + gold);
					text.printText("You obtained " + Integer.toString(gold) + " gold!");
					used = true;
					
					break;
			}
			
			if(uses > 0 && used){
				uses--;
			}
		}
		else{
			text.printTextAddLine(endText);
		}
		
		return player;
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
