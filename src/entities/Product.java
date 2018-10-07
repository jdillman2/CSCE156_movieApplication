package entities;

public abstract class Product {
	private String productCode;
	private String productType;
	private double price;
	private int quantity;
	private double discount;
	
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(Customer c) {
		if(c.getType().equals("S")) {
			this.discount = .08;
		}
	}
	public Product(String productCode, String productType, double price) {
		super();
		this.productCode = productCode;
		this.productType = productType;
		this.price = price;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	abstract double getTAX();
	abstract double getSubtotal();
}