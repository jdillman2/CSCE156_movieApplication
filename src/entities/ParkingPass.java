package entities;

public class ParkingPass extends Service {
	
	private int numOfTickets;
	private String matchingMovie = "";
	
	public double getDiscount() {
		return 0.0;
	}
	
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
	
	public ParkingPass(ParkingPass oldPass) {
		super(oldPass.getProductCode(), oldPass.getProductType(), oldPass.getPrice());
		this.numOfTickets = oldPass.getNumOfTickets();
		this.matchingMovie = oldPass.getMatchingMovie();
	}
	
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
