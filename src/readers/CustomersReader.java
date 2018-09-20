package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Address;
import entities.Customer;
import entities.Person;

public class CustomersReader {
	public ArrayList<Customer> readCustomers(){
		Scanner s = null;
    	try {
			s = new Scanner(new File("data/Customers.dat"));
		} 
    	catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	ArrayList<Customer> customerList = new ArrayList<Customer>();
    	s.nextLine();
    	while(s.hasNext()) {
    		String[] customer = s.nextLine().split(";");
    		String customerCode = customer[0];
    		String type = customer[1];
    		String primaryContactCode = customer[2];
    		String name = customer[3];
    		String[] a = customer[4].split(",");
    		Address address = new Address(a[0].trim(),a[1].trim(),a[2].trim(),a[3].trim(),a[4].trim());
    		
    		
    		//Find associated Person using primaryContact
    		//Re-read personList using PersonsReader
    		ArrayList<Person> personList = new PersonsReader().readPersons();
    		
    		//Loop through personList to see if there is a matching code to the customer
    		Person primaryContactMatch = null;
    		for(Person p : personList) {
    			if (primaryContactCode == p.getPersonCode()) {
    				primaryContactMatch = p;
    			}
    		}
    		
    		//Add customer to customerList
    		customerList.add(new Customer(customerCode, type, primaryContactCode, primaryContactMatch, name, address));
    	}
	s.close();
	return customerList;
	}
}
