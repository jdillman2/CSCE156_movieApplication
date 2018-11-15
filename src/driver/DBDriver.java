package driver;

import entities.Invoice;
import lists.LinkedList;
import readers.FlatFileReader;

public class DBDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 FlatFileReader ffr = new FlatFileReader();
	     LinkedList<Invoice> invoices = ffr.readInvoicesToList();
	     
	     for(int i = 1; i <= invoices.getSize(); i++) {
	    	 System.out.println(invoices.getObject(i).getInvoiceID() + " " + Double.toString(invoices.getObject(i).getInvoiceGrandTotal()));
	     }
	}

}
