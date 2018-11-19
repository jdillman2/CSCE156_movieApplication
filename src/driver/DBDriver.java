package driver;

import java.util.ArrayList;

import entities.Customer;
import entities.Person;
import entities.Product;
import readers.DatabaseReader;

public class DBDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 //FlatFileReader ffr = new FlatFileReader();
	     //LinkedList<Invoice> invoices = ffr.readInvoicesToList();
	     
	     //for(int i = 1; i <= invoices.getSize(); i++) {
	    	 //System.out.println(invoices.getObject(i).getInvoiceID() + " " + Double.toString(invoices.getObject(i).getInvoiceGrandTotal()));
	     //}
		DatabaseReader db = new DatabaseReader();
		ArrayList<Person> personList = db.readPersonsDB();
		ArrayList<Customer> customerList = db.readCustomersDB();
		for(Customer c: customerList) {
			System.out.println(c.toString());
		}
		ArrayList<Product> productList1 = db.readProductsDB();
		for(Product p: productList1) {
			System.out.println(p.toString());
		}
	}

}
