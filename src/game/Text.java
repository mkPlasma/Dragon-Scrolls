package game;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Text{
	
	private static String lastInput = "";
	
	public static String getInput(){
		
		do{
			try{
				TimeUnit.NANOSECONDS.sleep(1);
			}
			catch(InterruptedException e){}
		}
		while(!Window.getButtonPressed());
		
		try{
			TimeUnit.NANOSECONDS.sleep(1);
		}
		catch(InterruptedException e){}
		
		String input = Window.getInput();
		
		Window.resetButton();
		printLine(input);
		
		return input;
	}
	
	public static void print(String output, Color color){
		SimpleAttributeSet attrib = new SimpleAttributeSet();
		StyleConstants.setForeground(attrib, color);

		Document document = Window.getTextOutput().getStyledDocument();
		
		try{
			document.insertString(document.getLength(), output, attrib);
		}
		catch(BadLocationException e){}
	}
	
	public static void print(String output){
		SimpleAttributeSet attrib = new SimpleAttributeSet();
		StyleConstants.setForeground(attrib, Color.BLACK);
		
		Document document = Window.getTextOutput().getStyledDocument();
		
		try{
			document.insertString(document.getLength(), output, attrib);
		}
		catch(BadLocationException e){}
	}
	
	public static void printLine(String output){
		print(System.lineSeparator() + output);
	}
	
	public static void printLine(String output, Color color){
		print(System.lineSeparator() + output, color);
	}
	
	public static void printLine(String[] output){
		for(int i = 0; i < output.length; i++){
			print(System.lineSeparator() + output[i]);
		}
	}
	
	public static void printLine(String[] output, Color color){
		for(int i = 0; i < output.length; i++){
			print(System.lineSeparator() + output[i], color);
		}
	}
	
	public static void printLineExtra(String output){
		print(System.lineSeparator() + System.lineSeparator() + output);
	}
	
	public static void printLineExtra(String[] output){
		for(int i = 0; i < output.length; i++){
			if(i == 0){
				print(System.lineSeparator());
			}

			print(System.lineSeparator() + output[i]);
		}
	}
	
	public static void addLine(){
		print(System.lineSeparator());
	}
	
	public static void printYN(){
		print(" (");
		print("Y", new Color(0, 92, 0));
		print("/");
		print("N", Color.RED);
		print(")");
	}
	
	public static String testInput(){
		String input = getInput().toLowerCase();
		lastInput = input;
		return input;
	}
	
	public static int testInput(String[] acceptedInput){
		
		String input = getInput().trim();
		lastInput = input;
		
		for(int i = 0; i < acceptedInput.length; i++){
			if(input.equalsIgnoreCase(acceptedInput[i])){
				return i;
			}
		}
		
		return -1;
	}
	
	public static int testInput(String input, String[] acceptedInput){
		
		for(int i = 0; i < acceptedInput.length; i++){
			if(input.trim().equalsIgnoreCase(acceptedInput[i])){
				return i;
			}
		}
		
		return -1;
	}
	
	public static boolean canTrim(String str){
		
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
	
	public static String trimFromSpace(String str){
		
		str = str.trim();
		String newString = "";
		
		try{
			newString = str.substring(str.indexOf(" ")).trim();
		}
		catch(Exception e){}
		
		return newString;
	}
	
	public static String trimToSpace(String str){

		str = str.trim();
		String newString = "";
		
		try{
			newString = str.substring(0, str.indexOf(" ")).trim();
		}
		catch(Exception e){}
		
		return newString;
	}
	
	public static String getLastInput(){
		return lastInput;
	}
}
