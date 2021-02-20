/*
Programmer: Fathi AIYYOUB
File: Player.java
Purpose: Final project The Numbers Game windowed application
*/

public class Player
{	//class fields/instance variables
	private String  playerName; //customer name
	private int 	totalAmount;

	public Player() //default constructor
	{
		this(" ", 0);
	}

	public Player(String playerName, int totalAmount) //parameterised constructor
	{
		this.playerName = playerName;
		this.totalAmount = totalAmount;
	}
	// player name
	public void setPlayerName(String playerName) //sets player name
	{
		this.playerName = playerName;
	}

	public String getPlayerName() //gets player name (to display data)
	{
		return playerName;
	}

	// total amount
	public void setTotalAmount(int totalAmount) // sets player total amount (credit)
	{
		this.totalAmount = totalAmount;
	}

	public int getTotalAmount() // gets player total amount (credit)
	{
		return totalAmount;
	}

} //end Player class
