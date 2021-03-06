package entities;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SeasonPass extends Ticket {
	
	private String name;
	private DateTime startDate;
	private DateTime endDate;
	private DateTime invoiceDate;
	
	private DateTime convertDateString(String movieTime) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = formatter.parseDateTime(movieTime);
		return dt;
	}
	
	public SeasonPass(String productCode, String productType, double price, String name, String startDate,
			String endDate) {
		super(productCode, productType, price);
		this.name = name;
		this.startDate = convertDateString(startDate);
		this.endDate = convertDateString(endDate);
	}
	//Copy constructor that can be used to duplicate a season pass product 
	//when it is set when building the Invoice objects
	public SeasonPass(SeasonPass oldPass) {
		super(oldPass.getProductCode(), oldPass.getProductType(), oldPass.getPrice());
		this.name = oldPass.getName();
		this.startDate = oldPass.getStartDate();
		this.endDate = oldPass.getEndDate();
		this.invoiceDate = oldPass.getInvoiceDate();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = convertDateString(startDate);
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = convertDateString(endDate);
	}

	public DateTime getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = this.convertDateString(invoiceDate);
	}
	//getPricePerDay() and getProratedDays() allows the SeasonPass to calculate the price if 
	//the season pass is purchased after the start of the passes start date.
	private double getPricePerDay() {
		int daysInSeason = Days.daysBetween(startDate, endDate).getDays();
		double price = super.getPrice();
		return price/daysInSeason;
	}
	//This method is used in invoice detailed reports to indicate to the customer how many days 
	//their prorated discount took into account
	public int getProratedDays() {
		int daysLeftInSeason;
		if(this.invoiceDate.isAfter(this.startDate)) {
			daysLeftInSeason = Days.daysBetween(invoiceDate, endDate).getDays();
		}else {
			daysLeftInSeason = Days.daysBetween(startDate, endDate).getDays();
		}
		return daysLeftInSeason;
	}
	
	//Overridden methods inherited from the Product Abstract Class
	@Override
	public double getSubtotal() {
		int quantity = super.getQuantity();
		int daysLeftInSeason;
		if(this.invoiceDate.isAfter(this.startDate)) {
			daysLeftInSeason = Days.daysBetween(invoiceDate, endDate).getDays();
		}else {
			daysLeftInSeason = Days.daysBetween(startDate, endDate).getDays();
		}
		double pricePerDay = this.getPricePerDay();
		return (pricePerDay * daysLeftInSeason + 8) * quantity;
	}
	
	//No discount is ever offered by method must be implemented for the inherited superclass
	@Override
	public double getDiscount() {
		return 0.0;
	}
	
	@Override
	public double getTAX() {
		double subtotal = this.getSubtotal();
		double tax = super.getTAX();
		return subtotal * tax;
	}
	
	@Override
	public String getFullType() {
		return "SeasonPass";
	}
	
	
}
