package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener{

	private static boolean sendText = false;
	
	private static boolean upHeld = false;
	private static boolean downHeld = false;
	
	public void keyPressed(KeyEvent event){
		
		if(event.getKeyCode() == KeyEvent.VK_ENTER){
			event.consume();
			sendText = true;
		}
		
		if(event.getKeyCode() == KeyEvent.VK_UP){
			upHeld = true;
		}
		
		if(event.getKeyCode() == KeyEvent.VK_DOWN){
			downHeld = true;
		}
	}

	public void keyReleased(KeyEvent event){
		
	}

	public void keyTyped(KeyEvent event){
		
	}
	
	public static boolean sendText(){
		return sendText;
	}
	
	public static void resetEnter(){
		sendText = false;
	}
	
	public static void resetArrowKeys(){
		upHeld = false;
		downHeld = false;
	}
	
	public static boolean isUpHeld(){
		return upHeld;
	}
	
	public static boolean isDownHeld(){
		return downHeld;
	}
}