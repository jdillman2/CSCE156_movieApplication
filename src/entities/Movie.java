package entities;

public class Movie extends Products {

	private String movieTime;
	private String title;
	private Address theatreAddress;
	private String theatreNo;
	
	public Movie(String productCode, String productType, double price, String movieTime, String title,
			Address theatreAddress, String theatreNo) {
		super(productCode, productType, price);
		this.movieTime = movieTime;
		this.title = title;
		this.theatreAddress = theatreAddress;
		this.theatreNo = theatreNo;
	}

	public String getMovieTime() {
		return movieTime;
	}

	public void setMovieTime(String movieTime) {
		this.movieTime = movieTime;
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
	
	
}
