package writers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import entities.Customer;

public class CustomersXMLWriter {

	public void xmlConverter(ArrayList<Customer> customerList) {
		XStream xstream = new XStream();
		try {
			PrintWriter pw = new PrintWriter("data/Customers.xml");
			
			xstream.alias("customer",  Customer.class);
			
			/*Do we need to try to print out primaryContact Person
			Or is just the Person code adequate?
			Note: Why does it print out details of Address but not Person?*/
			pw.println("<customers>");
			for(Customer c : customerList) {
				String xmlCustomer = xstream.toXML(c);
				pw.println(xmlCustomer);
			}
			pw.println("</customers>");
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.print("Cannot create file.");
		}
		
	}
}
