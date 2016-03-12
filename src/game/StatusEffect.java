package game;

public class StatusEffect{
	
	// 0 - None
	
	// Buffs
	// 1 - Health Regeneration - 1 HP per level per turn
	// 2 - Magic Regeneration - 1 MP per level per turn
	// 3 - Defense Buff - 1 DEF per level
	// 4 - Attack Buff - 5% per level (100% at lvl 20)
	// 14 - Health Max Increase - 1 HP per level
	// 15 - Magic Max Increase - 1 MP per level
	// 19 - Barrier (Chance to fully block attack) - 5% per level (100% at lvl 20)
	// 20 - Absorption (Heals damage taken) - Heals 5% of damage per level (Heals 100% of damage at lvl 20)
	
	// Debuffs
	// 5 - Imprison (Cannot use special)
	// 6 - Lock (Cannot use item)
	// 7 - Paralysis (Cannot run or goto)
	// 8 - Bleeding (HP decrease) - 1 HP per level
	// 9 - Burn (HP decrease) - 1 HP per level
	// 10 - Poison (MP decrease) - 1 MP per level
	// 11 - Weakness (Attack debuff) - 5% per level (100% at lvl 20)
	// 12 - Magic Steal (MP decrease) - 1 MP per level
	// 13 - Defense Break (Lowers DEF) - 1 DEF per level
	// 16 - Health Max Decrease - 1 HP per level
	// 17 - Magic Max Decrease - 1 MP per level
	// 18 - Confuse (Chance to fail attack) - 5% per level (100% at lvl 20)
	
	private int type, length, level, chance;
	private boolean targetPlayer;
	
	public StatusEffect(){
		this(0, 0, 0);
	}
	
	public StatusEffect(int type, int length, int level){
		this.type = type;
		this.length = length;
		this.level = level;
	}
	
	public StatusEffect(int type, int length, int level, int chance, boolean targetPlayer){
		this.type = type;
		this.length = length;
		this.level = level;
		this.chance = chance;
		this.targetPlayer = targetPlayer;
	}
	
	public void updateLength(){
		if(type != 0){
			length -= 1;
		}
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public void setLength(int length){
		this.length = length;
	}

	public void setLevel(int level){
		this.level = level;
	}
	
	public void setChance(int chance){
		this.chance = chance;
	}
	
	public String getName(){
		switch(type){
		case 1:
			return "regeneration";
		
		case 2:
			return "magic regeneration";
		
		case 3:
			return "defense buff";
	
		case 4:
			return "strength";
		
		case 5:
			return "imprison";
		
		case 6:
			return "lock";
		
		case 7:
			return "paralysis";

		case 8:
			return "bleed";
		
		case 9:
			return "burn";
		
		case 10:
			return "poison";
		
		case 11:
			return "weakness";
		
		case 12:
			return "magic steal";
		
		case 13:
			return "defense break";
		
		case 14:
			return "health increase";
		
		case 15:
			return "magic increase";
		
		case 16:
			return "health decrease";
		
		case 17:
			return "magic decrease";
		
		case 18:
			return "confusion";
			
		case 19:
			return "barrier";
			
		case 20:
			return "absorption";
		}
		
		return "";
	}
	
	public boolean isDebuff(){
		
		if(type >= 1 && type <= 4){
			return false;
		}
		
		if(type == 14 || type == 15 || type >= 19){
			return false;
		}
		
		return true;
	}
	
	public boolean isTargetingPlayer(){
		return targetPlayer;
	}
	
	public int getType(){
		return type;
	}
	
	public int getLength(){
		return length;
	}

	public int getLevel(){
		return level;
	}
	
	public int getChance(){
		return chance;
	}
}
