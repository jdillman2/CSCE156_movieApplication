package entities;

public class General extends Customer {
	
	private final double FEES = 0.0;
	private final double DISCOUNT_RATE = 0.0;
	
	public General(String customerCode, String type, String primaryContactCode, Person primaryContact, String name,
			Address address) {
		super(customerCode, type, primaryContactCode, primaryContact, name, address);
	}

	@Override
	public double getCustomerFee() {
		return this.FEES;
	}

	@Override
	public double getDiscountRate() {
		return this.DISCOUNT_RATE;
	}

	@Override
	public String getFullType() {
		return "General";
	}

}
