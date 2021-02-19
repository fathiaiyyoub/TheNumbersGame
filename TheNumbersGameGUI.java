/*
Programmer: Fathi AIYYOUB
Course: Programming Fundamentals COIT 11222 T320
File: TheNumbersGameGUI.java
Purpose: Final project -- The Numbers Game windowed application
Date: 13 February 2021
*/

import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.util.*;
import java.util.Random;


public class TheNumbersGameGUI extends JFrame implements ActionListener
{
	Player player = new Player();

	private JLabel playerNameLabel  = new JLabel("Player name: ");// for prompt
	private JTextField playerNameField = new JTextField(28);      // for name entry
	private JLabel programmerNameLabel = new JLabel("Programmer: Fathi AIYYOUB. SID: 12151675");

	private JTextArea displayTextArea = new JTextArea("  ", 14, 60); // declare text area
	private JScrollPane scrollPane; // scroll pane for the text area

	// declare all of the buttons
	private JButton enterButton = new JButton("Enter"); // buttons
	private JButton generateNumbers = new JButton("Generate Numbers");
	private JButton addFunds = new JButton("Add funds");
	private JButton checkFunds = new JButton("Check funds");
	private JButton exitButton = new JButton("Exit");

	public TheNumbersGameGUI()
	{ // constructor create the Gui
		this.setLayout(new FlowLayout());			// set JFrame to FlowLayout

		add(playerNameLabel);
		add(playerNameField);

		// set text area to a monospaced font so the columns can be aligned using a format string
		displayTextArea.setFont(new Font("Monospaced", Font.BOLD, 15));
		displayTextArea.setEditable(false); 			// make text area read only
		scrollPane = new JScrollPane(displayTextArea); 	// add text area to the scroll pane
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // just need vertical scrolling

		add(scrollPane);
		add(enterButton);
		add(generateNumbers);
		add(addFunds);
		add(checkFunds);
		add(exitButton);
		add(programmerNameLabel);

		enterButton.addActionListener(this);		// add the action listener to the buttons
		generateNumbers.addActionListener(this);
		addFunds.addActionListener(this);
		checkFunds.addActionListener(this);
		exitButton.addActionListener(this);

		// when the user pushes the system close (X top right corner)
		addWindowListener( // override window closing method
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					exit();				// Attempt to exit application
				}
			}
		);
	} //end constructor TheNumbersGame()

	public void actionPerformed(ActionEvent e)
	{ // process the clicks on all of the buttons
		String command = e.getActionCommand();

		if (command.compareTo("Enter") == 0)
			enter();
		else if (command.compareTo("Generate Numbers") == 0)
			generateNumbers();
		else if (command.compareTo("Add funds") == 0)
			addFunds();
		else if (command.compareTo("Check funds") == 0)
			checkFunds();
		else if (command.compareTo("Exit") == 0)
			exit();
	} //end actionPerformed() method

	private void enter()
	{//adds player details i.e player name and credit (funds)

		if(playerNameField.getText().compareTo("") == 0)
		{// checks if player name filed is empty
			JOptionPane.showMessageDialog(null,"You must enter player name",
					"The Numbers Game", JOptionPane.ERROR_MESSAGE);

			playerNameField.requestFocus();
			return; //exits the method early
		}
		String playerName = playerNameField.getText(); //player name
		player.setPlayerName(playerName);

		displayGameRules(); //displays game rules

		String totalAmountString;
		int totalAmount = 0;

		while(true)
		{
			totalAmountString = JOptionPane.showInputDialog(null, "How much funds would you like to add?", "Initial funds"
	 				,JOptionPane.QUESTION_MESSAGE);

	 		if(totalAmountString == null)
			{
				return; // handles if cancel is clicked
			}

			else if(totalAmountString.isEmpty())
			{
				JOptionPane.showMessageDialog(null,"You must enter amount",
					"The Numbers Game", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			else
			{
				try
				{
					totalAmount = Integer.parseInt(totalAmountString);
					player.setTotalAmount(totalAmount);
				}

				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(null,"You must enter a number!",
					"The Numbers Game", JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}
			break;
		}

		//creates new instance of the object using data entered by the user
		Player player = new Player(playerName, totalAmount);
	} //end enter() method


	private void displayHeading()
	{// this method is used from assessment 2 code provided by Bruce.
		displayTextArea.setText(String.format("                *****Your lucky numbers are*****\n"));
		appendLine();
	} //end displayHeading() method

	private void addFunds()
	{// adds funds so that the player can continue playing if they want.

		while(playerNameField.getText().compareTo("") == 0)
		{// so that the game does not start without player name
			JOptionPane.showMessageDialog(null,"You must enter player name first!");
					playerNameField.requestFocus();
					return; // so that the programme does not crash
		}

		while(true)
		{
			String addedAmountString = JOptionPane.showInputDialog(null, "How much more funds would you like to add?", "Extra Funds"
			 		,JOptionPane.QUESTION_MESSAGE);

			if(addedAmountString == null)
			{
				return;
			}

			else if(addedAmountString.isEmpty())
			{
				JOptionPane.showMessageDialog(null,"You must enter some value",
					"The Numbers Game", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			else
			{
				try
				{
					int addedAmount = Integer.parseInt(addedAmountString);
					int newAmount = addedAmount + player.getTotalAmount();
					player.setTotalAmount(newAmount);
				}
				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(null,"You must enter a number!",
					"The Numbers Game", JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}
			break;
		}

	}// end addFunds() method

	private void appendLine()
	{// this method is used from assessment 2 code provided by Bruce.
		displayTextArea.append("---------------------------------------------------------------\n");
	} //end appendLine() method


	private void generateNumbers()
	{ // generates 25 random integers

		boolean caseOne 	= false;
		boolean caseTwo 	= false;
		boolean caseThree 	= false;
		boolean caseFour 	= false;
		int 	totalAmount;

		while(playerNameField.getText().compareTo("") == 0)
		{// so that the game does not start without player name
			JOptionPane.showMessageDialog(null,"Player name cannot be empty!");
					playerNameField.requestFocus();
					return; // so that the programme does not crash
		}

		// bid handling section
		String bidString;
		int bid;

		while(true)
		{
			bidString = JOptionPane.showInputDialog(null, "Place your bid ", "Bid",
					JOptionPane.PLAIN_MESSAGE);

			if(bidString == null)
			{
				return;
			}

			else if(bidString.isEmpty())
			{
				JOptionPane.showMessageDialog(null,"You must enter some value",
					"The Numbers Game", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			else
			{
				try
				{
					bid = Integer.parseInt(bidString);
				}

				catch(NumberFormatException exception)
				{
					JOptionPane.showMessageDialog(null,"You must enter a number!",
						"The Numbers Game", JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}
			break;
		}

		//checks for insufficient funds
		while(bid > player.getTotalAmount())
		{
			int optionSelection = JOptionPane.showConfirmDialog(null,"You do not have suffecient funds!\nWoud you like to add funds?"
				,"Insufficient Funds", JOptionPane.YES_NO_OPTION);

			if(optionSelection == JOptionPane.YES_OPTION)
			{
				addFunds();
				continue;
			}

			else
			{
				return;
			}
		}

		// numbers generator section
		Random integerGenerator = new Random(); // random object declaration & instantiation
		int[] arr = new int[25]; // array of numbers declaration & instantiation

		for (int counter = 0; counter < arr.length; counter++)
		{
			arr[counter] = integerGenerator.nextInt(20); // numbers between 0-20.
		}
		// rows section
		int rowOne = arr[0] + arr[1] + arr[2] + arr[3] + arr[4];
		int rowTwo = arr[5] + arr[6] + arr[7] + arr[8] + arr[9];
		int rowThree = arr[10] + arr[11] + arr[12] + arr[13] + arr[14];
		int rowFour = arr[15] + arr[16] + arr[17] + arr[18] + arr[19];
		int rowFive = arr[20] + arr[21] + arr[22] + arr[23] + arr[24];

		// display section
		displayHeading();
		displayTextArea.append(String.format("    %-12d%-12d%-12d%-12d%-12d\n",arr[0], arr[1], arr[2], arr[3], arr[4]));
		displayTextArea.append(String.format("    %-12d%-12d%-12d%-12d%-12d\n",arr[5], arr[6], arr[7], arr[8], arr[9]));
		displayTextArea.append(String.format("    %-12d%-12d%-12d%-12d%-12d\n",arr[10], arr[11], arr[12], arr[13], arr[14]));
		displayTextArea.append(String.format("    %-12d%-12d%-12d%-12d%-12d\n",arr[15], arr[16], arr[17], arr[18], arr[19]));
		displayTextArea.append(String.format("    %-12d%-12d%-12d%-12d%-12d\n",arr[20], arr[21], arr[22], arr[23], arr[24]));

		appendLine();
		displayTextArea.append(" Row one total:    " + rowOne + "\n");
		displayTextArea.append(" Row two total:    " + rowTwo + "\n");
		displayTextArea.append(" Row three total:  " + rowThree + "\n");
		displayTextArea.append(" Row four total:   " + rowFour + "\n");
		displayTextArea.append(" Row five total:   " + rowFive + "\n");

		// winning possibilities section
		//row one
		if(rowOne == rowTwo || rowOne == rowThree || rowOne == rowFour || rowOne == rowFive)
		{
			caseOne = true;
		}
		//row two
		if(rowTwo == rowThree || rowTwo == rowFour || rowTwo == rowFive)
		{
			caseTwo = true;
		}
		//row three
		if(rowThree == rowFour || rowThree == rowFive)
		{
			caseThree = true;
		}
		//row four
		if(rowFour == rowFive)
		{
			caseFour = true;
		}
/*
I have done it this way so that less JOptionPane code is needed so it makes the code more readable and reusable.
I have also considerd the cyclomatic compexity of the method since it is growing. And of course, to make the code
look better.
*/		// winning rewards section
		if(caseOne || caseTwo || caseThree || caseFour)
		{// checks for 2 rows are equal
			bid = bid * 2;
			totalAmount = player.getTotalAmount();
			totalAmount += bid;
			player.setTotalAmount(totalAmount);

			JOptionPane.showMessageDialog(null, "Congratulations!!!\nYou are lucky!\nYour current balance is: " + player.getTotalAmount(), "Winning!",
					JOptionPane.PLAIN_MESSAGE);
		}

		else if(caseOne && caseTwo && caseThree && caseFour)
		{// checks for 5 rows are equal
			bid = bid * 5;
			totalAmount = player.getTotalAmount();
			totalAmount += bid;
			player.setTotalAmount(totalAmount);

			JOptionPane.showMessageDialog(null, "Congratulations!!!\nYou are lucky!\nYour current balance is: " + player.getTotalAmount() , "BIG WIN!!!",
					JOptionPane.PLAIN_MESSAGE);
		}

		else
		{// no rows are equal to each other
			totalAmount = player.getTotalAmount();
			totalAmount = totalAmount - bid;
			player.setTotalAmount(totalAmount);

			JOptionPane.showMessageDialog(null, "Hard Luck!!!\n: - (", "No Win!",
					JOptionPane.ERROR_MESSAGE);
		}
	} //end generateNumbers() method

	private void checkFunds()
	{// the player can click the button to check the remaining balance
		JOptionPane.showMessageDialog(null, "Your current available funds are:\n $" + player.getTotalAmount(),
				"Available Funds", JOptionPane.INFORMATION_MESSAGE);
	}

	private void displayGameRules()
	{// to make the player aware of the gamer rules

		JOptionPane.showMessageDialog(null, "Welcome to The Numbers Game " + player.getPlayerName()+ "!" +
		",\n\n" + "The game is programmed to generate 25 random numbers which are then arranged in 5 rows.\nThe total of each row will be calaulated. " +
		"If 2 rows have the same total, your bet amount doubles.\nIf all rows have the same total, BIG WIN! your bid amount gets multiplied by 5!\nIf neither, you loose your bet amount"+
		"\n\nDisclaimer:\nThis game does not promote nor resemble gambling in any form. It was created for \nacademic purposes, and to prove the programmer skills in using JAVA programming language.\n"+
		"\nGood Luck!", "Game Information", JOptionPane.INFORMATION_MESSAGE);

	}// displayGameRules()

	private void exit()
	{// displays exit message

		JOptionPane.showMessageDialog(null,"Hope you enjoyed the game.\n\nAll GAME DATA WILL BE ERASED!",
				"The Numbers Game", JOptionPane.PLAIN_MESSAGE);
		System.exit(0);
	} //end exit() method

	//Main method create instance of class
	public static void main(String[] args)
	{

		TheNumbersGameGUI f = new TheNumbersGameGUI();					// Create instance of class
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	// allow the code to close the program
		f.setBounds(250, 200, 600, 425);						// Define position and size of app
		f.setTitle("The Numbers Game");			// Set the title of the app
		f.setVisible(true);										// Make the application visible
		f.setResizable(false);									// Make the window not resizable
		f.setDefaultLookAndFeelDecorated(true);
	} //end main() method
} //end TheNumbersGame() class