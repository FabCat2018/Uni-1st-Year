//A template for a SafeShare.
public class SafeShare extends Share
{
	private double fixedCash;
	
	//A constructor for SafeShare with instance methods inherited from Share.
	public SafeShare (String sharename, double shareprice, double cashFloor)
	{
		super(sharename, shareprice);
		setFixedCash(cashFloor);
		
	}//END SafeShare
	
	//A method to calculate the value of a SafeShare.
	public double calculateValue (double interestRate)
	{
		double value = Math.round(100.00 * (getPrice() + fixedCash)) / 100.00;
		return value;
		
	}//END calculateValue
	
	//A method to set the fixedCash of a SafeShare.
	public void setFixedCash (double cashFloor)
	{
		fixedCash = cashFloor;
	
	}//END setFixedCash
	
	//A method to get the fixedCash of a SafeShare.
	public double getFixedCash ()
	{
		return fixedCash;
		
	}//END getFixedCash

}//END class SafeShare