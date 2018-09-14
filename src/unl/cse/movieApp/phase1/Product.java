package unl.cse.movieApp.phase1;

public class Product {
	String productCode = null;
	char type;
	double price = 0;
	String name = null;
	String showTime = null;
	String startDate = null;
	String endDate = null;
	Address theatreAddress = new Address();
	
	public Product(String productCode, char type, double price, String name, String showTime, String startDate,
			String endDate, Address theatreAddress) {
		super();
		this.productCode = productCode;
		this.type = type;
		this.price = price;
		this.name = name;
		this.showTime = showTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.theatreAddress = theatreAddress;
	}
}
