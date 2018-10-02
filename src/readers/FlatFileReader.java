package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Address;
import entities.Customer;
import entities.General;
import entities.Invoice;
import entities.Movie;
import entities.ParkingPass;
import entities.Person;
import entities.Product;
import entities.Refreshment;
import entities.SeasonPass;
import entities.Student;

public class FlatFileReader {
	public ArrayList<Person> readPersons(){
		Scanner s = null;
    	try {
			s = new Scanner(new File("data/Persons.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	ArrayList<Person> personList = new ArrayList<Person>();
    	s.nextLine();
    	while(s.hasNext()) {
    		String[] person = s.nextLine().split(";");
    		String[] name = person[1].split(",");
    		String[] a = person[2].split(",");
    		Address address = new Address(a[0].trim(),a[1].trim(),a[2].trim(),a[3].trim(),a[4].trim());
    		String[] emails = null;
    		if(person.length == 4) {
    			emails = person[3].split(",");
    		}
    		personList.add(new Person(person[0].trim(), name[0].trim(), name[1].trim(), address, emails));
    	}
    	s.close();
    	return personList;
	}
	
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
    		ArrayList<Person> personList = this.readPersons();
    		//Loop through personList to see if there is a matching code to the customer
    		Person primaryContactMatch = null;
    		for(Person p : personList) {
    			if (primaryContactCode.equals(p.getPersonCode())) {
    				primaryContactMatch = p;
    			}
    		}
    		
    		if(type.equals("G")) {
    			Customer c = new General(customerCode, type, primaryContactCode, primaryContactMatch, name, address);
    			customerList.add(c);
    		}
    		else if (type.equals("S")) {
    			Customer c = new Student(customerCode, type, primaryContactCode, primaryContactMatch, name, address);
    			customerList.add(c);
    		}
    		
    	}
	s.close();
	return customerList;
	}
	
	public ArrayList<Product> readProduct() {
		Scanner s = null;
    	try {
			s = new Scanner(new File("data/Product.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	ArrayList<Product> p = new ArrayList<Product>();
    	s.nextLine();
    	while(s.hasNext()) {
    		String[] product = s.nextLine().split(";");
    		String type = product[1];
    		switch(type) {
    		case "M": 
    			String[] a = product[4].split(",");
    			Address theatreAddress = new Address(a[0],a[1],a[2],a[3],a[4]);
    			p.add(new Movie(product[0],product[1],Double.parseDouble(product[6]),product[2].trim(),product[3],theatreAddress, product[5]));
    			break;
    		case "S":
    			p.add(new SeasonPass(product[0],product[1],Double.parseDouble(product[5]),product[2],product[3].trim(),product[4].trim()));
    			break;
    		case "P":
    			p.add(new ParkingPass(product[0],product[1],Double.parseDouble(product[2])));
    			break;
    		case "R":
    			p.add(new Refreshment(product[0],product[1],Double.parseDouble(product[3]),product[2]));
    			break;
    		}
    	}
    	s.close();
    	return p;
	}
	
	public ArrayList<Invoice> readInvoices(){
		Scanner s = null;
    	try {
			s = new Scanner(new File("data/Invoices.dat"));
		} 
    	catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
    	s.nextLine();
    	while(s.hasNext()) {
    		String[] invoice = s.nextLine().split(";");
    		String invoiceCode = invoice[0];
    		String customerCode = invoice[1];
    		String personCode = invoice[2];
    		String invoiceDate = invoice[3];
    		String[] Product = invoice[4].split(",");
    		
    		//Find associated Person using primaryContact
    		//Re-read personList using PersonsReader
    		ArrayList<Customer> customerList = this.readCustomers();
    		ArrayList<Person> personList = this.readPersons();
    		ArrayList<Product> productList = this.readProduct();
    		//Loop through personList to see if there is a matching code to the customer
    		Customer customerMatch = null;
    		Person personMatch = null;
    		Product[] productMatch = new Product[Product.length];
    		for(Customer c : customerList) {
    			if (customerCode.equals(c.getCustomerCode())) {
    				customerMatch = c;
    			}
    		}
    		for(Person p : personList) {
    			if (personCode.equals(p.getPersonCode())) {
    				personMatch = p;
    			}
    		}
    		for(Product pr : productList) {
    			for(int i = 0; i < Product.length; i++) {
    				String[] productData = Product[i].split(":");
    				String productCode = productData[0];
    				if(productCode.equals(pr.getProductCode())) {
    					productMatch[i] = pr;
    				}
    			}
    		}
    		
    		//Add customer to customerList
    		invoiceList.add(new Invoice(invoiceCode, customerMatch, personMatch, invoiceDate, productMatch));
    	}
	s.close();
	return invoiceList;
	}
}
