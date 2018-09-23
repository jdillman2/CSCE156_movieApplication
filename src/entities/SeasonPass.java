package entities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SeasonPass extends Products {
	
	private String name;
	private DateTime startDate;
	private DateTime endDate;
	
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
	
}
