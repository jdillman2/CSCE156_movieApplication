package entities;

public class Student extends Customer {

	private final double FEES = 6.75;
	private final double DISCOUNT_RATE = .08;
	
	public Student(String customerCode, String type, String primaryContactCode, Person primaryContact, String name,
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
		return "Student";
	}
}
