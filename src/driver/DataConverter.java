package driver;

import java.util.ArrayList;

import entities.Customer;
import entities.Person;
import entities.Products;
import readers.CustomersReader;
import readers.PersonsReader;
import readers.ProductsReader;
import writers.CustomersXMLWriter;
import writers.PersonsXMLWriter;
import writers.ProductsXMLWriter;

public class DataConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Person> person = null;
		PersonsReader pReader = new PersonsReader();
		person = pReader.readPersons();
		PersonsXMLWriter pWriter = new PersonsXMLWriter();
		pWriter.xmlConverter(person);
		
		ArrayList<Products> products = null;
		ProductsReader prReader = new ProductsReader();
		products = prReader.readProducts();
		ProductsXMLWriter prWriter = new ProductsXMLWriter();
		prWriter.xmlConverter(products);
		
		ArrayList<Customer> customers = null;
		CustomersReader cReader = new CustomersReader();
		customers = cReader.readCustomers();
		CustomersXMLWriter cWriter = new CustomersXMLWriter();
		cWriter.xmlConverter(customers);
	}

}
