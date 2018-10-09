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
	//The following GetInvoiceXXXX() methods utilize the individual product methods to get totals, taxes, fees and discounts
	public double getInvoiceSubTotal() {
		double subTotal = 0.0;
		for(Product p: products) {
			subTotal += p.getSubtotal();
		}
		return subTotal;
	}
	
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
		if(customer.getType().equals("S")) {
			return (subtotal * .08) + taxes;
		}else {
			return 0;
		}
	}
	
	public double getInvoiceAdditionalFee() {
		return customer.getCustomerFee();
	}
	//This returns the total of each unique product in the invoice to be used in the detail report
	public double getInvoiceTotalofTotals() {
		double total = 0.0;
		for(Product p: products) {
			total += p.getSubtotal() + p.getTAX();
		}
		return total;
	}
	//This provides the final grand total for all the products in the invoice
	public double getInvoiceGrandTotal() {
		return this.getInvoiceSubTotal() + this.getInvoiceTax() - this.getInvoiceDiscount() + this.getInvoiceAdditionalFee();
	}
	//This provides a single formatted line for the necessary data fields in the Summary Report
	public void printSummaryTotal() {
		String id = this.invoiceID;
		String custName = this.customer.getName() + "[" + this.customer.getFullType() + "]";
		String salesName = this.salesPerson.getName();
		double subTotal = this.getInvoiceSubTotal();
		double fees = this.customer.getCustomerFee();
		double taxes = this.getInvoiceTax();
		double discount = this.getInvoiceDiscount() * -1;
		double total = this.getInvoiceGrandTotal();
		System.out.printf("%-8s %-36s %-20s %2s "
				+ "%7.2f %2s %7.2f %2s %7.2f "
				+ "%2s %7.2f %2s %7.2f"
				+ "\n", id, custName, salesName, "$", subTotal, "$", fees, "$", taxes, "$", discount, "$", total);
	}
	
}
