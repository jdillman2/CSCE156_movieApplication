package unl.cse.movieApp.phase1;
//Comment here again
public class Customer {
	String customerCode = null;
	String primaryContact = null;
	String customerName = null;
	Address customerAddress = null;
	public Customer(String customerCode, String primaryContact, String customerName, Address customerAddress) {
		super();
		this.customerCode = customerCode;
		this.primaryContact = primaryContact;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getPrimaryContact() {
		return primaryContact;
	}
	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Address getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}
	
}
