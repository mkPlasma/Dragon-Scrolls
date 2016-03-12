package game;

public class Main{
	
	static GameHandler gameHandler = new GameHandler();
	
	public static void main(String[] args){
		
		Window window = new Window();
		window.setSize(600, 450);
		window.setVisible(true);
		
		gameHandler.start(window);
	}
}
