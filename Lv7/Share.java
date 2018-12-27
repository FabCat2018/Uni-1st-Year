//A template for what a Share is.
public class Share
{
	//Characteristics of a Share.
	private String name;
	private double price;
	
	//A method to create a Share from user inputs.
	public Share (String shareName, double sharePrice)
	{
		setName(shareName);
		setPrice(sharePrice);
		
	}//END Share
	
	//A method to create a default Share.
	public Share ()
	{
		name = "Share";
		price = 0;
		
	}//END Share
	
	//A method to calculate the value of a Share.
	public double calculateValue (double interestRate)
	{
		double value = price;
		return value;
		
	}//END calculateValue

	//A method to set the name of a Share.
	public void setName (String sharename)
	{
		name = sharename;
		
	}//END setName
	
	//A method to set the price of a share.
	public void setPrice (double shareprice)
	{
		price = shareprice;
		
	}//END setPrice
	
	//A method to get the price of a stock.
	public double getPrice ()
	{
		return price;
		
	}//END getPrice
	
	//A method to get the name of a stock.
	public String getName ()
	{
		return name;
		
	}//END getName
	
}//END class Share