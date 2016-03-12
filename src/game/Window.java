package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

public class Window extends JFrame implements ActionListener, Runnable{
	
	Thread thread;
	
	private boolean running = false;
	
	private final KeyboardListener keyboardListener;
	
	private final Color borderColor = new Color(122, 138, 153);
	
	private JTextPane textOutput;
	private JTextArea textInput;
	private JScrollPane textScroll;
	private JButton bSend;
	
	private boolean buttonPressed = false;
	private String input = "";

	private String[] inputHistory = new String[32];
	private int historyPosition = -1;
	
	public Window(){
		super("Dragon Scrolls");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setResizable(false);
		
		keyboardListener = new KeyboardListener();
		
		textOutput = new JTextPane();
		textOutput.setEditable(false);
		DefaultCaret caret = (DefaultCaret)textOutput.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		textScroll = new JScrollPane(textOutput);
		textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textScroll.setPreferredSize(new Dimension(575, 350));
		textScroll.setMinimumSize(new Dimension(5, 33));
		add(textScroll);
		
		textInput = new JTextArea();
		textInput.setBorder(new LineBorder(borderColor, 1));
		textInput.setPreferredSize(new Dimension(500, 50));
		textInput.setMaximumSize(new Dimension(500, 50));
		textInput.addKeyListener(keyboardListener);
		add(textInput);
		
		bSend = new JButton("Send");
		bSend.setToolTipText("Send command");
		bSend.setActionCommand("send");
		bSend.addActionListener(this);
		bSend.setPreferredSize(new Dimension(70, 50));
		bSend.setMinimumSize(new Dimension(35, 15));
		add(bSend);
		
		Arrays.fill(inputHistory, "");
		
		startThread();
	}
	
	public void startThread(){
		thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		
		running = true;
		
		while(running){
			
			if(textInput.getText().length() > 64){
				textInput.setText(textInput.getText().substring(0, 64));
			}
			
			if(keyboardListener.sendText()){
				keyboardListener.resetEnter();
				sendText();
			}
			
			if(keyboardListener.isUpHeld() && historyPosition < 31
			&& (historyPosition == -1 || historyPosition == 31 || inputHistory[historyPosition + 1] != "")){
				historyPosition += 1;
				textInput.setText(inputHistory[historyPosition]);
				keyboardListener.resetArrowKeys();
			}
			
			if(keyboardListener.isDownHeld() && historyPosition > -1){
				historyPosition -= 1;
				
				if(historyPosition >= 0){
					textInput.setText(inputHistory[historyPosition]);
				}
				else{
					textInput.setText("");
				}

				keyboardListener.resetArrowKeys();
			}
			
			try{
				thread.sleep(1);
			}
			catch(Exception e){}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("send")){
			sendText();
		}
	}
	
	private void sendText(){
		
		if(textInput.getText() == ""){
			return;
		}
		
		buttonPressed = true;
		input = textInput.getText();
		
		historyPosition = -1;
		
		for(int i = inputHistory.length - 1; i > 0; i--){
			inputHistory[i] = inputHistory[i - 1];
		}
		inputHistory[0] = input;
		
		textInput.setText("");
	}
	
	public void setText(String text){
		textOutput.setText(text);
	}
	
	public String getText(){
		return textOutput.getText();
	}
	
	public JTextPane getTextOutput(){
		return textOutput;
	}
	
	public boolean getButtonPressed(){
		return buttonPressed;
	}
	
	public String getInput(){
		return input;
	}
	
	public void resetButton(){
		buttonPressed = false;
	}
}
