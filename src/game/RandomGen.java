package game;

import java.util.Random;

public class RandomGen{
	private final static Random r = new Random();
	
	public static int getInt(int min, int max){
		return r.nextInt(max - min) + min;
	}
	
	public static int getInt(int max){
		return r.nextInt(max);
	}
	
	// Returns true chance% of time
	// Used for random true/false events
	public static boolean randomChance(int chance){
		return r.nextInt(99) + 1 <= chance;
	}
}
