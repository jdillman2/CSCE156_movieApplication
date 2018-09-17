package entities;

public abstract class Products {
	private String productCode;
	private String productType;
	private double price;
	public Products(String productCode, String productType, double price) {
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
	
	
}
