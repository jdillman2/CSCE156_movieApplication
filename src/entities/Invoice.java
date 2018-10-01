package entities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Invoice {
	
	private String invoiceID;
	private Customer customer;
	private Person salesPerson;
	private DateTime invoiceDate;
	private Product[] products;
	
	private DateTime convertDateString(String movieTime) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = formatter.parseDateTime(movieTime);
		return dt;
	}

	public Invoice(String invoiceID, Customer customer, Person salesPerson, String invoiceDate, Product[] products) {
		super();
		this.invoiceID = invoiceID;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.invoiceDate = convertDateString(invoiceDate);
		this.products = products;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(Person salesPerson) {
		this.salesPerson = salesPerson;
	}

	public DateTime getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = convertDateString(invoiceDate);
	}

	public Product[] getProducts() {
		return products;
	}

	public void setProducts(Product[] products) {
		this.products = products;
	}
	
	
}
