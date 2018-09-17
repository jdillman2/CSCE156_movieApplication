package entities;

public class SeasonPass extends Products {
	
	private String name;
	private String startDate;
	private String endDate;
	
	public SeasonPass(String productCode, String productType, double price, String name, String startDate,
			String endDate) {
		super(productCode, productType, price);
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
