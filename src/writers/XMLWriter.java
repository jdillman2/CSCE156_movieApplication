package writers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import entities.Customer;
import entities.Movie;
import entities.ParkingPass;
import entities.Person;
import entities.Product;
import entities.Refreshment;
import entities.SeasonPass;

public class XMLWriter {
	
	public void personXMLConverter(ArrayList<Person> arrayList) {
		//Instantiate XStream to convert objects to XML
		XStream xstream = new XStream();
		try {
			PrintWriter pw = new PrintWriter("data/Persons.xml");
			//Aliases assigned for cleaner XML appearance
			xstream.alias("person",  Person.class);
			xstream.alias("email", String.class);
			pw.println("<persons>");
			//Loop through array to convert objects to XML
			for(Person p : arrayList) {
				String xmlPerson = xstream.toXML(p);
				pw.println(xmlPerson);
			}
			pw.println("</persons>");
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.print("Cannot create file.");
		}
	}
	
	public void CustomerXMLConverter(ArrayList<Customer> customerList) {
		XStream xstream = new XStream();
		try {
			PrintWriter pw = new PrintWriter("data/Customers.xml");
			
			xstream.alias("customer",  Customer.class);
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
	
	public void ProductXMLConverter(ArrayList<Product> arrayList) {
		XStream xstream = new XStream();
		try {
			PrintWriter pw = new PrintWriter("data/Product.xml");
			
			xstream.alias("movie",  Movie.class);
			xstream.alias("seasonPass", SeasonPass.class);
			xstream.alias("parkingPass", ParkingPass.class);
			xstream.alias("Refreshment", Refreshment.class);
			
			pw.println("<Product>");
			for(Product p : arrayList) {
				String xmlPerson = xstream.toXML(p);
				pw.println(xmlPerson);
			}
			pw.println("</Product>");
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.print("Cannot create file.");
		}	
	}

}
