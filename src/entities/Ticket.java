package entities;

public abstract class Ticket extends Product{
	
	public Ticket(String productCode, String productType, double price) {
		super(productCode, productType, price);
		// TODO Auto-generated constructor stub
	}

	private final double TAX = .06;

	public double getTAX() {
		return TAX;
	}
	
}
