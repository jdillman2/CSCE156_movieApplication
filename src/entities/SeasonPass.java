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
	
	public double getDiscount() {
		return 0.0;
	}
	
	public SeasonPass(String productCode, String productType, double price, String name, String startDate,
			String endDate) {
		super(productCode, productType, price);
		this.name = name;
		this.startDate = convertDateString(startDate);
		this.endDate = convertDateString(endDate);
	}
	
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
	
	private double getPricePerDay() {
		int daysInSeason = Days.daysBetween(startDate, endDate).getDays();
		double price = super.getPrice();
		return price/daysInSeason;
	}
	
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
	
	public int getProratedDays() {
		//int quantity = super.getQuantity();
		int daysLeftInSeason;
		if(this.invoiceDate.isAfter(this.startDate)) {
			daysLeftInSeason = Days.daysBetween(invoiceDate, endDate).getDays();
		}else {
			daysLeftInSeason = Days.daysBetween(startDate, endDate).getDays();
		}
		return daysLeftInSeason;
	}
	@Override
	public double getTAX() {
		double subtotal = this.getSubtotal();
		double tax = super.getTAX();
		return subtotal * tax;
	}
	
	//ADDED
	@Override
	public String getFullType() {
		return "SeasonPass";
	}
	
	
}
