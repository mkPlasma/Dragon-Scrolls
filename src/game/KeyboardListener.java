package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener{

	private boolean sendText = false;
	
	private boolean upHeld = false;
	private boolean downHeld = false;
	
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
	
	public boolean sendText(){
		return sendText;
	}
	
	public void resetEnter(){
		sendText = false;
	}
	
	public void resetArrowKeys(){
		upHeld = false;
		downHeld = false;
	}
	
	public boolean isUpHeld(){
		return upHeld;
	}
	
	public boolean isDownHeld(){
		return downHeld;
	}
}