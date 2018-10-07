package entities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Movie extends Ticket {

	private DateTime movieTime;
	private String title;
	private Address theatreAddress;
	private String theatreNo;
	
	private DateTime convertDateString(String movieTime) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		DateTime dt = formatter.parseDateTime(movieTime);
		return dt;
	}
	
	public Movie(String productCode, String productType, double price, String movieTime, String title,
			Address theatreAddress, String theatreNo) {
		super(productCode, productType, price);
		this.movieTime = this.convertDateString(movieTime);
		this.title = title;
		this.theatreAddress = theatreAddress;
		this.theatreNo = theatreNo;
	}
	
	public Movie(Movie oldMovie) {
		super(oldMovie.getProductCode(), oldMovie.getProductType(), oldMovie.getPrice());
		this.movieTime = new DateTime(oldMovie.getMovieTime());
		this.title = oldMovie.getTitle();
		this.theatreAddress = new Address(oldMovie.getTheatreAddress());
		this.theatreNo = oldMovie.getTheatreNo();
	}

	public DateTime getMovieTime() {
		return movieTime;
	}

	public void setMovieTime(String movieTime) {
		this.movieTime = this.convertDateString(movieTime);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Address getTheatreAddress() {
		return theatreAddress;
	}

	public void setTheatreAddress(Address theatreAddress) {
		this.theatreAddress = theatreAddress;
	}

	public String getTheatreNo() {
		return theatreNo;
	}

	public void setTheatreNo(String theatreNo) {
		this.theatreNo = theatreNo;
	}

	public double getDiscount() {
		if(this.movieTime.getDayOfWeek() == 2 || this.movieTime.getDayOfWeek() == 4) {
			return .07;
		}else {
			return 0.0;
		}
	}

	public double getSubtotal() {
		int quantity = super.getQuantity();
		double price = super.getPrice();
		double discount = 1 - this.getDiscount();
		return quantity * price * discount;
	}
	@Override 
	public double getTAX() {
		double subtotal = this.getSubtotal();
		double tax = super.getTAX();
		return subtotal * tax;
	}
}
