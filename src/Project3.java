import java.lang.Math;   
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.util.Scanner;
import java.util.Stack;
import java.io.*;
import javax.swing.border.BevelBorder;
import java.util.ArrayList;

//Eyiara Oladipo
//11/29/2022
//Eyiara Oladipo
public class Project3 extends JFrame implements KeyListener{
	JTextArea calculatorInputSection;
	String globalInputResult;
	String[] operands = new String[100];
	String[] operators =  new String[100];
	String baseOfText = "HISTORY";
	JTextArea txtrHistory;
	int numberOfOperators, numberOfOperands;
	
	//Key listneres for getting the input of the user, once the user
	//has put in their input, add it to the input section
	public void keyTyped(KeyEvent event) {}	
	public void keyPressed(KeyEvent event) {
		char keyClicked = event.getKeyChar();
		int asciiCode = keyClicked;
		if((asciiCode >= 42 && asciiCode <=59 )) {
			String newString = ""+(char)asciiCode;
			calculatorInputSection.append(newString);
			validateUserInput(calculatorInputSection.getText());
		}
				
		//Checking for backspace
		if(asciiCode == 8) {
			String currentText = calculatorInputSection.getText();
			if(currentText.length() > 0) {
				currentText = currentText.substring(0, currentText.length() - 1);
				calculatorInputSection.setText("");
				calculatorInputSection.setText(currentText);
				validateUserInput(calculatorInputSection.getText());
			}
		}
		
		//Checking for an enter click
		if(asciiCode == 10) {
			if(numberOfOperators + 1 == numberOfOperands && numberOfOperands != 1) {
				baseOfText = baseOfText + "\n" + calculatorInputSection.getText() + " = " + globalInputResult; 
				calculatorInputSection.setText(globalInputResult);
			}
		}
		
		//Checking for whether the user has clicked a C to clear the user input section
		if(asciiCode == 99) {
			calculatorInputSection.setText("");
		}
	}
	
	//Necessary event listener
	public void keyReleased(KeyEvent event) {

	}
	
	//When the user enters a number, validate the user input
	public void validateUserInput(String userInput) {
		numberOfOperators = 0;
		numberOfOperands = 0;
		Scanner userInputScanner = new Scanner(userInput);
		userInputScanner.useDelimiter("[-/*+=]+");	
		
		//Using a scanner as a delimeter to get the number of operands 
		while(userInputScanner.hasNext()) {
			String inputNumber = userInputScanner.next();
			operands[numberOfOperands] = inputNumber;
			numberOfOperands++;
		}
		
		//initializing the number of operators variables
		numberOfOperators = 0;
		
		//Through the user input, look for the number of operators and store in an array
		for(int i = 0; i < userInput.length(); i++) {
			switch(userInput.charAt(i)) {
			case '+':
			case '-':
			case '*':
			case '/':
				operators[numberOfOperators] = ""+userInput.charAt(i);
				numberOfOperators++;
				break;
			}
		}		
		calculateUserInput(userInput);
		userInputScanner.close();
	};
	
	//Solve the user's input
	public void calculateUserInput(String userInput) {
		int currentOperator = 0, currentOperand  = 0;
		
		//Create a new stack
		Stack userInputCalculations = new Stack();
		
		//If the number of operators is at least the same as the number of operands
		if(((numberOfOperators  == numberOfOperands) || numberOfOperands == 1) && numberOfOperators -1 != numberOfOperands) {
			txtrHistory.setText(baseOfText + "\n" + userInput);
			//If there is one more operator than operands
		}else if (numberOfOperators + 1 == numberOfOperands) {
			double inputResult = 0;
			for(int i = 0; i < numberOfOperands + numberOfOperators; i++) {
			if(userInputCalculations.size() == 2) {
				String secondInput = (String)userInputCalculations.pop();	
				String firstInput = (String)userInputCalculations.pop();
				switch(operators[currentOperator]) {
				case "+":
					System.out.println(secondInput + " + " + firstInput);
					inputResult = Double.parseDouble(secondInput) +  Double.parseDouble(firstInput);
					break;
				case "-":
					System.out.println(secondInput + " - " + firstInput);
					inputResult = Double.parseDouble(firstInput) - Double.parseDouble(secondInput) ;
					break;
				case "*":
					System.out.println(secondInput + " * " + firstInput);
					inputResult = Double.parseDouble(secondInput) *  Double.parseDouble(firstInput);
					break;
				case "/":
					System.out.println(secondInput + " / " + firstInput);
					inputResult =  Double.parseDouble(firstInput) / Double.parseDouble(secondInput);
					break;
				}
			globalInputResult = ""+inputResult;
			userInputCalculations.push(""+inputResult);
			currentOperator++;

			} else {
				
				//Pushing to the stack 
				userInputCalculations.push(operands[currentOperand]);
				currentOperand++;
			}
		}
		txtrHistory.setText(baseOfText + "\n" + userInput + " = " + inputResult);
		}else {
			txtrHistory.setText("Please enter a valid input");
		}
	}
	

	class CalculatorButtonsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			JButton buttonClicked = (JButton)e.getSource();
		//	"Clear", "CE", "C", "←", "="
			
			//When the button is clicked, check for the operator buttons
			switch(buttonClicked.getText()) {
			case "Clear": 
				calculatorInputSection.setText("");
				break;				
			case "CE":
				String calculatorCurrentText = calculatorInputSection.getText();
				Scanner separatorScanner = new Scanner(calculatorCurrentText);
				separatorScanner.useDelimiter("[-/*+=]+");	
				int lastInputLength = 0;
				while(separatorScanner.hasNext()) {
					String inputNumber = separatorScanner.next();
					lastInputLength = inputNumber.length();
				}
				if(lastInputLength > 0) {
					calculatorInputSection.setText(calculatorCurrentText.substring(0, calculatorCurrentText.length() - lastInputLength));
				}
				System.out.println("Heres the length: " + lastInputLength);
				break;
			case "←":
				String currentText = calculatorInputSection.getText();
				if(currentText.length() > 0) {
					currentText = currentText.substring(0, currentText.length() - 1);
					calculatorInputSection.setText("");
					calculatorInputSection.setText(currentText);
				}
				break;
			case "=":
				if(numberOfOperators + 1 == numberOfOperands && numberOfOperands != 1) {
				System.out.println("add to history");
				baseOfText = baseOfText + "\n" + calculatorInputSection.getText() + " = " + globalInputResult; 
				calculatorInputSection.setText(globalInputResult);
				}
				break;
			default:
				calculatorInputSection.append(buttonClicked.getText());
			}
			
			if(buttonClicked.getText() != "=")
				validateUserInput(calculatorInputSection.getText());
		}		
	}
	
	//Constructor to create the frame
	Project3(){
		getContentPane().setBackground(Color.DARK_GRAY);
		setBackground(Color.DARK_GRAY);
		setSize(558, 308);
		getContentPane().setLayout(new GridLayout(1, 2, 0, 0));
		setTitle("Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		JPanel calculatorPanel = new JPanel();
		JPanel calculatorButtonsPanel = new JPanel();
		calculatorButtonsPanel.setBackground(Color.LIGHT_GRAY);
		calculatorButtonsPanel.setLayout(new GridLayout(5,3));
		SpringLayout sl_calculatorPanel = new SpringLayout();
		sl_calculatorPanel.putConstraint(SpringLayout.WEST, calculatorButtonsPanel, 0, SpringLayout.WEST, calculatorPanel);
		sl_calculatorPanel.putConstraint(SpringLayout.SOUTH, calculatorButtonsPanel, 272, SpringLayout.NORTH, calculatorPanel);
		sl_calculatorPanel.putConstraint(SpringLayout.EAST, calculatorButtonsPanel, 273, SpringLayout.WEST, calculatorPanel);
		calculatorPanel.setLayout(sl_calculatorPanel);
		calculatorInputSection = new JTextArea();
		calculatorInputSection.setEditable(false);
		calculatorInputSection.setLineWrap(true);
		calculatorInputSection.addKeyListener(this);
		calculatorInputSection.setFont(new Font("Bahnschrift", Font.BOLD, 23));
		calculatorInputSection.setWrapStyleWord(true);
		calculatorInputSection.setBackground(new Color(47, 79, 79));
		calculatorInputSection.setForeground(Color.white);
		sl_calculatorPanel.putConstraint(SpringLayout.NORTH, calculatorButtonsPanel, 0, SpringLayout.SOUTH, calculatorInputSection);
		sl_calculatorPanel.putConstraint(SpringLayout.NORTH, calculatorInputSection, 0, SpringLayout.NORTH, calculatorPanel);
		sl_calculatorPanel.putConstraint(SpringLayout.WEST, calculatorInputSection, 0, SpringLayout.WEST, calculatorPanel);
		sl_calculatorPanel.putConstraint(SpringLayout.SOUTH, calculatorInputSection, 66, SpringLayout.NORTH, calculatorPanel);
		sl_calculatorPanel.putConstraint(SpringLayout.EAST, calculatorInputSection, 273, SpringLayout.WEST, calculatorPanel);
		calculatorInputSection.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		calculatorPanel.add(calculatorInputSection);
		
		//Buttons
		JButton numbersButtonsArray[] = new JButton[20];
		String[] calculatorButtons = {"+", "-", "*", "/", ".", "Clear", "CE", "←", "="};
		int currentPosition = 0;
		for(int i = 0; i <= 18; i++) {
			numbersButtonsArray[i] = new JButton();
			numbersButtonsArray[i].setBackground(Color.DARK_GRAY);  
			numbersButtonsArray[i].setForeground(Color.white);
			numbersButtonsArray[i].setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			numbersButtonsArray[i].setText(""+i);
			if(i >= 10) {
					numbersButtonsArray[i].setText(calculatorButtons[currentPosition]);
					currentPosition++;
					if(i == 15)				
						numbersButtonsArray[i].setBackground(new Color(101,28,50));
			}
			numbersButtonsArray[i].addActionListener(new CalculatorButtonsListener());
			calculatorButtonsPanel.add(numbersButtonsArray[i]);
		}
		operands[0] = "";
		operators[0] = "";
		txtrHistory = new JTextArea();
		txtrHistory.setLineWrap(true);
		txtrHistory.setWrapStyleWord(true);
		txtrHistory.setForeground(Color.WHITE);
		txtrHistory.setBackground(Color.DARK_GRAY);
		txtrHistory.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 13));
		txtrHistory.setText("HISTORY");
		txtrHistory.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		txtrHistory.setEditable(false);
		getContentPane().add(txtrHistory);
		calculatorPanel.add(calculatorButtonsPanel);
		getContentPane().add(calculatorPanel);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Project3();
	}
}
