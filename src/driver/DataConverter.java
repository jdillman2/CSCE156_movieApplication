package driver;

import java.util.ArrayList;

import entities.Customer;
import entities.Person;
import entities.Products;
import readers.FlatFileReader;
import writers.JSONWriter;
import writers.XMLWriter;

public class DataConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Instantiate arraylists to hold objects
		ArrayList<Person> person = null;
		ArrayList<Customer> customer = null;
		ArrayList<Products> products = null;
		
		//Instantiate reader object to populate arraylists
		FlatFileReader reader = new FlatFileReader();
		person = reader.readPersons();
		customer = reader.readCustomers();
		products = reader.readProducts();
		
		//Instantiate XMLwriter to write objects to XML
		XMLWriter writer = new XMLWriter();
		writer.personXMLConverter(person);
		writer.CustomerXMLConverter(customer);
		writer.ProductsXMLConverter(products);
		
		JSONWriter jsonWriter = new JSONWriter();
		jsonWriter.personJSONConverter(person);
		jsonWriter.CustomerJSONConverter(customer);
		jsonWriter.ProductsJSONConverter(products);
	}

}
