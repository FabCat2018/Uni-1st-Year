import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/* ****************************************************************************************
AUTHOR: Fabio Cataleta 01/03/18
A class to act as the StockMarket itself, which makes use of Share and its subclasses.
The StockMarket is now implemented as a GUI.
The StockMarket now asks questions through JOptionPanes.
The StockMarket now utilises file i/o to output the daily interest rate, which is then used
to calculate a moving average which refreshes every day after day 7.
****************************************************************************************** */

public class StockMarket extends Frame implements ActionListener
{
	private ArrayList<Share> portfolio = createPortfolio();
	private ArrayList<String> weeklyInterest = createWeeklyInterest();
	private BufferedReader inputStream = createInputStream();
	private PrintWriter outputStream = createOutputStream();
	private double interestRate = changeDay();
	private int dayCounter = 1;

	private Button changeDayButton;
	private Button buyShareButton;
	private Button sellShareButton;
	private Button checkSharesButton;
	private Button weeklyInterestButton;
	private Button quitButton;

	private TextArea tArea = new TextArea();

	//A method to create a Portfolio to store all Shares.
	public ArrayList<Share> createPortfolio ()
	{
		ArrayList<Share> portfolio = new ArrayList<Share>();
		
		Share share1 = new StandardShare("Microsoft", 500.35, getInterestRate());
		portfolio.add(share1);

		Share share2 = new SafeShare("GAME", 100.43, 40.32);
		portfolio.add(share2);
		
		return portfolio;
		
	}//END createPortfolio

	public double getInterestRate ()
	{
		return interestRate;
	}

	//A method to create and return an ArrayList of Strings
	public ArrayList<String> createWeeklyInterest ()
	{
		try
		{
			ArrayList<String> weeklyInterest = new ArrayList<String>();
			return weeklyInterest;
		}

		catch (NullPointerException e1)
		{
			print("A value returned null");
			System.exit(0);
		}

		catch (ArrayIndexOutOfBoundsException e2)
		{
			print("Array index out of bounds");
			System.exit(0);
		}

		return null;

	}//END createWeeklyInterest

	/* A method to create and return inputStream  to allow
	the whole class to access it as a private instance variable */
	public BufferedReader createInputStream ()
	{
		try
		{
			BufferedReader inputStream = new BufferedReader(new FileReader("record.txt"));
			return inputStream;
		}

		catch (IOException e)
		{
			print("Input Exception error occurred");
			System.exit(0);
		}

		return null;

	}//END createInputStream

	/* A method to create and return outputStream to allow
	the whole class to access it as a private instance variable */
	public PrintWriter createOutputStream ()
	{
		try
		{
			PrintWriter outputStream = new PrintWriter(new FileWriter("record.txt"), true);
			return outputStream;
		}

		catch (IOException e)
		{
			print("Ouput Exception error occurred");
			System.exit(0);
		}

		return null;

	}//END createOutputStream

	//A method to advance the day, changes interesRate.
	public double changeDay ()
	{
		interestRate = Math.round(100.00 * rollDice()) / 100.00;
		outputStream.println(interestRate);
		dayCounter++;
		return interestRate;

	}//END changeDay

	//The constructor for the GUI.
	public StockMarket ()
	{
		//Constructor for the layout.
        super("Stock Market GUI");
        setSize(1000, 1000);
		setLayout(new GridLayout(4, 2));
		
        tArea.setText("Today's interest rate is " + getInterestRate());

        //Constructor for the Change Day button.
        changeDayButton = new Button("New Day");
        add(changeDayButton);
        changeDayButton.addActionListener(this);
        add(tArea);
		setVisible(true);
		
		//Constructor for the Buy Share button
		buyShareButton = new Button("Buy Share");
		add(buyShareButton);
		buyShareButton.addActionListener(this);
		add(tArea);
		setVisible(true);

		//Constructor for the Sell Share button
		sellShareButton = new Button("Sell Share");
		add(sellShareButton);
		sellShareButton.addActionListener(this);
		add(tArea);
		setVisible(true);

		//Constructor for the Check Shares button
		checkSharesButton = new Button("Check Share Prices");
		add(checkSharesButton);
		checkSharesButton.addActionListener(this);
		add(tArea);
		setVisible(true);

		//Constructor for the Weekly Interest Button
		weeklyInterestButton = new Button("Show Weekly Interest Rate");
		add(weeklyInterestButton);
		weeklyInterestButton.addActionListener(this);
		add(tArea);
		setVisible(true);

		//Constructor for the Quit Button
		quitButton = new Button("Quit");
		add(quitButton);
		quitButton.addActionListener(this);
		add(tArea);
		setVisible(true);

	}//END StockMarketGUI

	//A method to respond to the buttons being pressed.
    public void actionPerformed (ActionEvent evt)
    {
        if (evt.getSource() == changeDayButton)
            tArea.setText("Today's interest rate is: " + changeDay() + "%");
        else if (evt.getSource() == buyShareButton)
            buyShares(portfolio);
		else if (evt.getSource() == sellShareButton)
			sellShares(portfolio);
        else if (evt.getSource() == checkSharesButton)
			tArea.setText(checkValue(portfolio));
		else if (evt.getSource() == weeklyInterestButton)
			tArea.setText(getWeeklyInterest());
        else if (evt.getSource() == quitButton)
			System.exit(0);


    }//END actionPerformed
	
    /* A method to take the data entered from the JOptionPane,
       and add it as a Share to the portfolio */
	public void buyShares (ArrayList<Share> portfolio)
	{
		String name = "";
		double price = 0;
        String type = "Standard";

		try
		{     
			name = input("Please enter the name of the stock you would like to purchase: ");
			price = inputDouble("Please enter the price of the stock you would like to purchase (in £s): ");
			type = input("Please enter the type of Stock you would like to purchase (Safe, Standard, Premium): ");
        }

		catch (ArrayIndexOutOfBoundsException e)
		{
			print("User did not enter recognised response");
			System.exit(0);
		}

		Share share3 = pickShareType(name, price, type);

		portfolio.add(share3);

	}//END buyShares

	//A method to select the type of Share the user wants to buy
	public Share pickShareType (String name, double price, String type)
	{
		Share temp = new Share();

		if (type.equalsIgnoreCase("Safe"))
			temp = new SafeShare(name, price, (price * 0.1));
		else if (type.equalsIgnoreCase("Standard"))
			temp = new StandardShare(name, price, getInterestRate());
		else if (type.equalsIgnoreCase("Premium"))
			temp = new PremiumShare(name, price, getInterestRate(), (price * 0.25));
		
		return temp;

	}//END pickShareType
	
	//A method to allow the user to sell shares using a JOptionPane
	public void sellShares (ArrayList<Share> portfolio)
	{
		String name = "";

		try
		{
			name = input("What is the name of the share you would like to sell?");

			for (int i = 0; i < portfolio.size(); i++)
			{
				if (name.equalsIgnoreCase(portfolio.get(i).getName()))
					portfolio.remove(i);
			}
		}
		
		catch (NullPointerException e1)
		{
			print("A value returned null");
			System.exit(0);
		}
		
	}//END sellShares
	
	//A method to check the price of all owned shares.
	public String checkValue (ArrayList<Share> portfolio)
	{
		String temp = "";

		try
		{
			for (int i = 0; i < portfolio.size(); i++)
			{
				temp += (portfolio.get(i).getName() + ": £" + portfolio.get(i).calculateValue(getInterestRate()) + "\n");
			}
		}

		catch (NullPointerException e1)
		{
			print("A value returned null");
			System.exit(0);
		}

		catch (NumberFormatException e2)
		{
			print("Not a number entry");
			System.exit(0);
		}

		catch (ArrayIndexOutOfBoundsException e3)
		{
			print("Out of bounds of the array");
			System.exit(0);
		}

		return temp;

	}//END checkValue

	//A method to display the weekly interest rates, averaging them.
	public String getWeeklyInterest ()
	{
		String temp = "";
		double interest = 0;
		String line = "";
		int counter = 0;

		try
		{
			inputStream.mark(100000);

			if (dayCounter < 7)
				temp = "A week must pass before the weekly average can be calculated.\nToday's interest rate is " + getInterestRate() + "%\nTry again in " + (7 - dayCounter) + " days";

			else if (dayCounter >= 7)
			{
				for (int i = 0; i < 7; i++)
					weeklyInterest.add(i, inputStream.readLine());

				if (dayCounter > 7)
				{
					line = inputStream.readLine();

					while (line != null)
					{
						weeklyInterest.add(line);
						line = inputStream.readLine();
						counter++;
					}

					for (int j = 0; j < counter; j++)
						weeklyInterest.remove(0);
				}

				for (int k = 0; k < 7; k++)
					interest += Double.parseDouble(weeklyInterest.get(k));
			
				interest /= 7;

				temp = "The average interest rate from the last 7 days is: " + (Math.round(100.00 * interest) / 100.00) + "%\n";
			}

			inputStream.reset();

		}

		catch (FileNotFoundException e1)
		{
			print("The file specified was not found");
			System.exit(0);
		}

		catch (IOException e2)
		{
			print("I/O Exception occurred");
			System.exit(0);
		}

		catch (ArrayIndexOutOfBoundsException e4)
		{
			print("Array index out of bounds");
			System.exit(0);
		}

		return temp;

	}//END getWeeklyInterest

	public static void main (String[] param)
	{
		new StockMarket();
		
	}//END main
	
	//A method to input Strings.
	public static String input (String message)
	{
		String input = "";
		input = JOptionPane.showInputDialog(null, message);
		return input;
		
	}//END input
	
	//A method to input doubles.
	public static double inputDouble (String message)
	{
		return Double.parseDouble(input(message));
		
	}//END inputDouble

	//Random Number Generator
	public static double rollDice()
	{
		Random dice = new Random();

        double rate = 0.05 + (0.18 - 0.05) * dice.nextDouble();

		return rate;

	}//END rollDice
	
	//A method to print Strings
	public static void print (String message)
	{
		JOptionPane.showMessageDialog(null, message);
		
	}//END print
	
}//END class StockMarket