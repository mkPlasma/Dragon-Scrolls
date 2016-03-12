package game;

import java.io.BufferedInputStream;
import java.net.URLClassLoader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound{
	
	boolean soundEnabled = true;
	
	public void playSound(String path){
		if(!soundEnabled){
			return;
		}
		
		try{
			path = "res/sound/" + path;
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(URLClassLoader.getSystemResourceAsStream(path)));
			clip.open(inputStream);
			clip.start();
		}
		catch(Exception e){}
	}
	
	public void setSoundEnabled(boolean b){
		soundEnabled = b;
	}
}
