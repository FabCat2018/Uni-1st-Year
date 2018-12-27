//A template for a standard Share.
public class StandardShare extends Share
{
	private double dividend;
	
	//A constructor to create a Standard Share using inherited instance methods.
	public StandardShare (String sharename, double shareprice, double interestRate)
	{
		super(sharename, shareprice);
		setDividend(interestRate);
		
	}//END StandardShare
	
	//A method to calculate the value of a StandardShare.
	public double calculateValue (double interestRate)
	{
		setDividend(interestRate);
		double value = Math.round(100.00 * (getPrice() * (1 + dividend))) / 100.00;
		return value;
		
	}//END calculateValue
	
	//A method to set the dividend from a StandardShare.
	public void setDividend (double interestRate)
	{
		dividend = interestRate;
		
	}//END setDividend
	
	//A method to get the dividend of a StandardShare.
	public double getDividend ()
	{
		return dividend;
		
	}//END getDividend
	
}//END class StandardShare