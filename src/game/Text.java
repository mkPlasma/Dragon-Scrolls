package game;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Text{
	
	private String lastInput = "";
	
	private Window window;
	
	public void setWindow(Window window){
		this.window = window;
	}
	
	public String getInput(){
		
		do{
			try{
				TimeUnit.NANOSECONDS.sleep(1);
			}
			catch(InterruptedException e){}
		}
		while(!window.getButtonPressed());
		
		try{
			TimeUnit.NANOSECONDS.sleep(1);
		}
		catch(InterruptedException e){}
		
		String input = window.getInput();
		
		window.resetButton();
		printText(input);
		
		return input;
	}
	
	public void print(String output, Color color){
		SimpleAttributeSet attrib = new SimpleAttributeSet();
		StyleConstants.setForeground(attrib, color);

		Document document = window.getTextOutput().getStyledDocument();
		
		try{
			document.insertString(document.getLength(), output, attrib);
		}
		catch(BadLocationException e){}
	}
	
	public void print(String output){
		SimpleAttributeSet attrib = new SimpleAttributeSet();
		StyleConstants.setForeground(attrib, Color.BLACK);
		
		Document document = window.getTextOutput().getStyledDocument();
		
		try{
			document.insertString(document.getLength(), output, attrib);
		}
		catch(BadLocationException e){}
	}
	
	public void printText(String output){
		print(System.lineSeparator() + output);
	}
	
	public void printText(String output, Color color){
		print(System.lineSeparator() + output, color);
	}
	
	public void printText(String[] output){
		for(int i = 0; i < output.length; i++){
			print(System.lineSeparator() + output[i]);
		}
	}
	
	public void printText(String[] output, Color color){
		for(int i = 0; i < output.length; i++){
			print(System.lineSeparator() + output[i], color);
		}
	}
	
	public void printTextAddLine(String output){
		print(System.lineSeparator() + System.lineSeparator() + output);
	}
	
	public void printTextAddLine(String[] output){
		for(int i = 0; i < output.length; i++){
			if(i == 0){
				print(System.lineSeparator());
			}

			print(System.lineSeparator() + output[i]);
		}
	}
	
	public void addLine(){
		print(System.lineSeparator());
	}
	
	public void printYN(){
		print(" (");
		print("Y", new Color(0, 92, 0));
		print("/");
		print("N", Color.RED);
		print(")");
	}
	
	public String testInput(){
		String input = getInput().toLowerCase();
		lastInput = input;
		return input;
	}
	
	public int testInput(String[] acceptedInput){
		
		String input = getInput().trim();
		lastInput = input;
		
		for(int i = 0; i < acceptedInput.length; i++){
			if(input.equalsIgnoreCase(acceptedInput[i])){
				return i;
			}
		}
		
		return -1;
	}
	
	public int testInput(String input, String[] acceptedInput){
		
		for(int i = 0; i < acceptedInput.length; i++){
			if(input.trim().equalsIgnoreCase(acceptedInput[i])){
				return i;
			}
		}
		
		return -1;
	}
	
	public boolean canTrim(String str){
		
		str = str.trim();
		String newString = str;
		
		try{
			newString = str.substring(str.indexOf(" ")).trim();
		}
		catch(Exception e){
			newString = str;
		}
		
		boolean canTrim = false;
		
		if(!newString.equals(str)){
			canTrim = true;
		}
		
		return canTrim;
	}
	
	public String trimFromSpace(String str){
		
		str = str.trim();
		String newString = "";
		
		try{
			newString = str.substring(str.indexOf(" ")).trim();
		}
		catch(Exception e){}
		
		return newString;
	}
	
	public String trimToSpace(String str){

		str = str.trim();
		String newString = "";
		
		try{
			newString = str.substring(0, str.indexOf(" ")).trim();
		}
		catch(Exception e){}
		
		return newString;
	}
	
	public String getLastInput(){
		return lastInput;
	}
}
