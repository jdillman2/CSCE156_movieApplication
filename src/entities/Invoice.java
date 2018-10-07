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
	
	public double getInvoiceSubTotal() {
		double subTotal = 0.0;
		for(Product p: products) {
			subTotal += p.getSubtotal();
		}
		return subTotal;
	}
	
	/*public double getInvoiceFees() {
		if(customer instanceof Student) {
			return 6.75;
		}else {
			return 0.0;
		}
	}*/
	
	public double getInvoiceTax() {
		double taxes = 0.0;
		for(Product p: products) {
			taxes += p.getTAX();
		}
		return taxes;
	}
	
	public double getInvoiceDiscount() {
		double taxes = this.getInvoiceTax();
		double subtotal = this.getInvoiceSubTotal();
		if(customer instanceof Student) {
			return (subtotal * .08) + taxes;
		}else {
			return 0;
		}
	}
	
	public double getInvoiceTotal() {
		return this.getInvoiceSubTotal() + this.getInvoiceTax() - this.getInvoiceDiscount();
	}
	
	public void printSummaryTotal() {
		String id = this.invoiceID;
		String custName = this.customer.getName();
		String salesName = this.salesPerson.getName();
		double subTotal = this.getInvoiceSubTotal();
		double fees = this.customer.getCustomerFee();
		System.out.printf("%-8s %-36s %-20s %-2s %7.2f %-2s %6.2f\n", id, custName, salesName, "$", subTotal, "$", fees);
	}
	
}
