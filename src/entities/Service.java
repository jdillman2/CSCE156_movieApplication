package entities;

public abstract class Service extends Product{

	public Service(String productCode, String productType, double price) {
		super(productCode, productType, price);
		// TODO Auto-generated constructor stub
	}

	private final double TAX = .04;

	public double getTAX() {
		return TAX;
	}
}
