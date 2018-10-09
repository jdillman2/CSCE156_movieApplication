package entities;

public class ParkingPass extends Service {
	
	private int numOfTickets;
	private String matchingMovie = "";
	
	public int getNumOfTickets() {
		return numOfTickets;
	}

	public void setNumOfTickets(int numOfTickets) {
		this.numOfTickets = numOfTickets;
	}
	
	public String getMatchingMovie() {
		return matchingMovie;
	}

	public void setMatchingMovie(String matchingMovie) {
		this.matchingMovie = matchingMovie;
	}

	public ParkingPass(String productCode, String productType, double price) {
		super(productCode, productType, price);
	}
	//Copy constructor that can be used to duplicate a parking pass product when copying to an invoice object
	//and preserve the numOftickets and matchingMovie when reading the invoice file.
	public ParkingPass(ParkingPass oldPass) {
		super(oldPass.getProductCode(), oldPass.getProductType(), oldPass.getPrice());
		this.numOfTickets = oldPass.getNumOfTickets();
		this.matchingMovie = oldPass.getMatchingMovie();
	}
	
	//Overridden methods inherited from the Product Abstract Class
	
	//No actual discounts are offered for this product so it returns 0
	@Override
	public double getDiscount() {
		return 0.0;
	}
	//getSubtotal() uses the numOfTickets attribute to determine how many free passes there should be for the invoice.
	@Override
	public double getSubtotal() {
		int quantity = super.getQuantity();
		int freePasses = this.numOfTickets;
		double price = super.getPrice();
		double totalPrice;
		if(freePasses >= quantity) {
			totalPrice = 0;
		}else {
			totalPrice = (quantity - freePasses) * price;
		}
		return totalPrice;	
	}
	@Override
	public double getTAX(){
		double subtotal = this.getSubtotal();
		double tax = super.getTAX();
		return subtotal * tax;
	}

	@Override
	public String getFullType() {
		return "ParkingPass";
	}
}
