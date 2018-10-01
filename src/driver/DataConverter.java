package driver;

import java.util.ArrayList;

import entities.Customer;
import entities.Person;
import entities.Product;
import readers.FlatFileReader;
import writers.JSONWriter;

public class DataConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Instantiate arraylists to hold objects
		ArrayList<Person> person = null;
		ArrayList<Customer> customer = null;
		ArrayList<Product> products = null;
		
		//Instantiate reader object to populate arraylists
		FlatFileReader reader = new FlatFileReader();
		person = reader.readPersons();
		customer = reader.readCustomers();
		products = reader.readProduct();
		
		//Instantiate JSONwriter to write objects to JSON
		
		JSONWriter jsonWriter = new JSONWriter();
		jsonWriter.personJSONConverter(person);
		jsonWriter.CustomerJSONConverter(customer);
		jsonWriter.ProductJSONConverter(products);
	}

}
