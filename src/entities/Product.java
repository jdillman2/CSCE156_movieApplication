package entities;

public abstract class Product {
	
	private String productCode;
	private String productType;
	private double price;
	private int quantity;

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
	

	@Override
	public String toString() {
		return "Product [productCode=" + productCode + ", productType=" + productType + ", price=" + price + "]";
	}
	
	//Abstract classes that will get called from each individual product type
	public abstract double getTAX();
	public abstract double getSubtotal();
	public abstract double getDiscount();
	public abstract String getFullType();
}
