package entities;

public class Refreshment extends Service{
	
	private String name;
	private boolean movieExists;
	
	public boolean isMovieExists() {
		return movieExists;
	}

	public void setMovieExists(boolean movieExists) {
		this.movieExists = movieExists;
	}

	public double getDiscount() {
		if(movieExists) {
			return .05;
		}else {
			return 0.0;
		}
	}

	public Refreshment(String productCode, String productType, double price, String name) {
		super(productCode, productType, price);
		this.name = name;
	}
	
	public Refreshment(Refreshment oldRefreshment) {
		super(oldRefreshment.getProductCode(), oldRefreshment.getProductType(), oldRefreshment.getPrice());
		this.name = oldRefreshment.getName();
		this.movieExists = oldRefreshment.isMovieExists();
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
		double discount = 1 - this.getDiscount();
		return quantity * price * discount;
	}
	
}
