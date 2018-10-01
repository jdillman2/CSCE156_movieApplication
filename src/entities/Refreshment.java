package entities;

public class Refreshment extends Service{
	
	private String name;
	private double discount;
	
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Refreshment(String productCode, String productType, double price, String name) {
		super(productCode, productType, price);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getTAX() {
		double subtotal = this.getSubtotal();
		double tax = super.getTAX();
		return subtotal * tax;
	}

	public double getSubtotal() {
		int quantity = super.getQuantity();
		double price = super.getPrice();
		double discount = 1 - this.discount;
		return quantity * price * discount;
	}
	
}
