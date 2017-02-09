package game;

public class Settings{

	private static boolean waitEnabled;
	private static boolean soundEnabled;
	private static boolean debugEnabled;
	
	public static void toggleWait(){
		waitEnabled = !waitEnabled;
	}
	
	public static void toggleSound(){
		soundEnabled = !soundEnabled;
	}
	
	public static void setWaitEnabled(boolean waitEnabled){
		Settings.waitEnabled = waitEnabled;
	}

	public static void setSoundEnabled(boolean soundEnabled){
		Settings.soundEnabled = soundEnabled;
	}
	
	public static void setDebugEnabled(boolean debugEnabled){
		Settings.debugEnabled = debugEnabled;
	}
	
	public static boolean waitEnabled(){
		return waitEnabled;
	}
	
	public static boolean soundEnabled(){
		return soundEnabled;
	}
	
	public static boolean debugEnabled(){
		return debugEnabled;
	}
}
