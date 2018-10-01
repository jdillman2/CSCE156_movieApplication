package entities;

public class ParkingPass extends Service {
	
	private int numOfTickets;

	public int getNumOfTickets() {
		return numOfTickets;
	}

	public void setNumOfTickets(int numOfTickets) {
		this.numOfTickets = numOfTickets;
	}

	public ParkingPass(String productCode, String productType, double price) {
		super(productCode, productType, price);
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
}
