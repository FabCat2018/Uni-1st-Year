//A template for a premium Share.
public class PremiumShare extends Share
{
	private double dividend;
	private double royalty;
	
	//A constructor for PremiumShare using inherited instance methods.
	public PremiumShare (String sharename, double shareprice, double interestRate, double extra)
	{
		super(sharename, shareprice);
		setDividend(interestRate);
		setRoyalty(extra);
		
	}//END PremiumShare
	
	//A method to calculate the value of a PremiumShare.
	public double calculateValue (double interestRate)
	{
		setDividend(interestRate);
		double value = Math.round(100.00* (getPrice() + (1 + dividend) + royalty)) / 100.00;
		return value;
		
	}//END calculateValue
	
	//A method to set the dividend of a PremiumShare.
	public void setDividend (double interestRate)
	{
		dividend = interestRate;
		
	}//END setDividend
	
	//A method yo set the royalty of a PremiumShare.
	public void setRoyalty (double extra)
	{
		royalty = extra;
		
	}//END setRoyalty
	
	//A method to get the dividend of a PremiumShare.
	public double getDividend ()
	{
		return dividend;
		
	}//END getDividend
	
	//A method to get the royalty of a PremiumShare.
	public double getRoyalty ()
	{
		return royalty;
		
	}//END getRoyalty
	
}//END class PremiumShare