package entities;

public abstract class Ticket extends Product{
	//Abstract class that can designate the .06 tax rate for Ticket type products
	public Ticket(String productCode, String productType, double price) {
		super(productCode, productType, price);
		// TODO Auto-generated constructor stub
	}

	private final double TAX = .06;

	public double getTAX() {
		return TAX;
	}
	
}
