package entities;

public class Refreshment extends Products{
	
	private String name;

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
	
}
