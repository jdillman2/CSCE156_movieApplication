package readers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
import lists.LinkedList;

public class DatabaseReader {
	
	static Connection conn = null;
    static PreparedStatement ps = null;
	
	public ArrayList<Person> readPersonsDB(){
		ArrayList<Person> personList = new ArrayList<Person>();
		String getPersons = "SELECT personCode, lName, fName, street, city, state, postalCode, country, GROUP_CONCAT(email) as emails\r\n" + 
				"FROM Persons p join Address a on p.id = a.personID\r\n" + 
				"left join Email e on p.id = e.accountID\r\n" + 
				"WHERE p.id > 0\r\n" + 
				"GROUP BY personCode, lName, fName, street, city, state, postalCode, country;";
		ResultSet rs = null;
		
		//read in the records from the Persons table and create instances of java Person objects
		try {
			conn = DatabaseInfo.getConnection();
	        ps = conn.prepareStatement(getPersons);
	        rs = ps.executeQuery();
	        while(rs.next()) {
	        	String code = rs.getString(1);
	        	String lName = rs.getString(2);
	        	String fName = rs.getString(3);
	        	String[] emails = null;
	    		Address personAddress = new Address(rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
	    		if(rs.getString(9) != null) {
	    			emails = rs.getString(9).split(",");
	    		}
	    		personList.add(new Person(code, lName, fName, personAddress, emails));
	    	}
	        
	        //Close resources
	        rs.close();
	        ps.close();
	        conn.close();
		} catch (SQLException e) {
	        e.printStackTrace();
	      } 
    	return personList;
	}
	
	public ArrayList<Customer> readCustomersDB(){
		ArrayList<Person> personList = this.readPersonsDB();
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		String getPersons = "SELECT customerCode, personCode, c.lName, \r\n" + 
				"CASE WHEN ISNULL(c.fName) THEN \"\" ELSE CONCAT(c.fName,' ') END as fName, \r\n" + 
				"street, city, state, postalCode, country, ct.category\r\n" + 
				"FROM Customers c join Address a on c.id = a.customerID\r\n" + 
				"join Persons p on c.primaryContact = p.id\r\n" + 
				"join R_Customer_Category ct on ct.id = c.category\r\n" + 
				"WHERE c.id > 0\r\n" + 
				"GROUP BY personCode, lName, fName, street, city, state, postalCode, country;";
		ResultSet rs = null;
		
		//read in the records from the Customers table and create instances of java Student and General Student objects
		try {
			conn = DatabaseInfo.getConnection();
	        ps = conn.prepareStatement(getPersons);
	        rs = ps.executeQuery();
	        while(rs.next()) {
	        	String code = rs.getString(1);
	        	String name = rs.getString(4) + rs.getString(3);
	        	String type = rs.getString(10);
	    		Address customerAddress = new Address(rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9));
	    		Person primaryContact = null;
	    		for(Person p: personList) {
	    			if(p.getPersonCode().equals(rs.getString(2))) {
	    				primaryContact = new Person(p);
	    			}
	    		}
	    		if(type.equals("Student")) {
	    			customerList.add(new Student(code,"S",rs.getString(2),primaryContact,name,customerAddress));
	    		}else {
	    			customerList.add(new General(code,"G",rs.getString(2),primaryContact,name,customerAddress));
	    		}
	    	}
	        
	        //Close resources
	        rs.close();
	        ps.close();
	        conn.close();
		} catch (SQLException e) {
	        e.printStackTrace();
	      } 
    	return customerList;
	}
	
	public ArrayList<Product> readProductsDB(){
	
		ArrayList<Product> productList = new ArrayList<Product>();
		String getRefreshments = "SELECT * FROM Refreshment WHERE id > 0;";
		String getSeasonPasses = "SELECT * FROM Season_Pass WHERE id > 0;";
		String getParkingPasses = "SELECT * FROM Parking_Pass WHERE id > 0;";
		String getMovies = "SELECT * FROM Movies LEFT JOIN Address ON Movies.id=Address.ProductID WHERE Movies.id > 0;";
		ResultSet rs = null;
		
		//read in the records from the Refreshment table and create instances of java Refreshment objects
	    try {
	    	conn = DatabaseInfo.getConnection();
	    	ps = conn.prepareStatement(getRefreshments);
	        rs = ps.executeQuery();
	        
	        while (rs.next()) {
	        	String productCode = rs.getString(2);
	 	        String description = rs.getString(3);
	 	        double price = rs.getDouble(5);
	 	       
	 	       //Add new Refreshment objects to array
		        productList.add(new Refreshment(productCode, "Refreshment", price, description));
	        }
	
	    }
	    catch (SQLException e) {
	    	System.out.print("Problem adding refreshments.");
	    	e.printStackTrace();
	    }
	    
		//read in the records from the Season_Pass table and create instances of java SeasonPass objects
		try {
			ps = conn.prepareStatement(getSeasonPasses);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String productCode = rs.getString(2);
				String description = rs.getString(3);
				double price = rs.getDouble(5);
				String startDate = rs.getString(6);
				String endDate = rs.getString(7);
				
				//Add new SeasonPass objects to array
				productList.add(new SeasonPass(productCode, "Season Pass", price, description, startDate,
						endDate));

			}
		}
		catch (SQLException e) {
	    	System.out.print("Problem adding season passes.");
	    	e.printStackTrace();
	    }
		 
		//read in the records from the Parking_Pass table and create instances of java ParkingPass objects
		try {
			ps = conn.prepareStatement(getParkingPasses);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String productCode = rs.getString(2);
				double price = rs.getDouble(4);
				
				//Add new ParkingPass objects to array
				productList.add(new ParkingPass(productCode, "Parking Pass", price));
			}
		}
		catch (SQLException e) {
	    	System.out.print("Problem adding parking passes.");
	    	e.printStackTrace();
	    }
		 
		
		//read in the records from the Movies table and create instances of java Movie objects
				try {
					ps = conn.prepareStatement(getMovies);
					rs = ps.executeQuery();
					
					while (rs.next()) {
						String productCode = rs.getString(2);
						String description = rs.getString(3);
						double price = rs.getDouble(5);
						String movieTime = rs.getString(6);
						String theatreNo = rs.getString(7);
						String street = rs.getString(12);
						String city = rs.getString(13);
						String state = rs.getString(14);
						String zip = rs.getString(15);
						String country = rs.getString(16);
						
						//Add new Movie objects to array
						productList.add(new Movie(productCode, "Movie", price, movieTime, description,
								new Address(street, city, state, zip, country), theatreNo));
					}
				}
				catch (SQLException e) {
			    	System.out.print("Problem adding movies.");
			    	e.printStackTrace();
			    }
				
		//Close resources.
		try {
			rs.close();
			ps.close();
			conn.close();	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
    	return productList;
	}
	
	public LinkedList<Invoice> readInvoicesToList(){
		//Empty linkedList of Invoices
		LinkedList<Invoice> invoicesLinkedList = new LinkedList<Invoice>();
		

		
		
		ResultSet rs = null;
		
		//Query String that will concatenate values from the database in an identical format to the flat files for Invoice.
		//This is useful to reuse much of our code written to readInvoices from a flat file format.
		String getInvoicesFlatFormat = "SELECT CONCAT(inv_data,product) FROM\r\n" + 
				"                (SELECT x.id, inv_data,\r\n" + 
				"                GROUP_CONCAT(CONCAT(y.productCode,y.quantity,y.movieCode)) as product\r\n" + 
				"                FROM (SELECT i.id, CONCAT(InvoiceCode,';',CustomerCode,';',PersonCode,';',InvoiceDate,';') as inv_data \r\n" + 
				"                FROM Invoice i join Customers c on i.CustomerID = c.id \r\n" + 
				"                join Persons p on i.SalesPersonID = p.id) as x join \r\n" + 
				"                (SELECT a.id, a.InvoiceID, a.productCode,\r\n" + 
				"                CASE WHEN m.productCode is not null THEN CONCAT(':',m.productCode) ELSE '' END as movieCode, quantity FROM\r\n" + 
				"                (SELECT ip.id, InvoiceID, GROUP_CONCAT(CASE WHEN m.productCode <> '00000' THEN CONCAT(m.productCode,':') ELSE '' END, \r\n" + 
				"                CASE WHEN s.productCode <> '00000' THEN CONCAT(s.productCode,':') ELSE '' END, \r\n" + 
				"                CASE WHEN p.productCode <> '00000' THEN CONCAT(p.productCode,':') ELSE '' END,\r\n" + 
				"                CASE WHEN r.productCode <> '00000' THEN CONCAT(r.productCode,':') ELSE '' END) as productCode, ParkingMovieID, quantity \r\n" + 
				"                FROM Invoice_Products ip left join Movies m on ip.MovieID = m.id \r\n" + 
				"                left join Season_Pass s on ip.SeasonPassID = s.id \r\n" + 
				"                left join Parking_Pass p on ip.ParkingPassID = p.id \r\n" + 
				"                left join Refreshment r on ip.RefreshmentID = r.id\r\n" + 
				"                GROUP BY id, InvoiceID, ParkingMovieID, quantity) as a left join Movies m on a.ParkingMovieID = m.id) as y on y.InvoiceID = x.id \r\n" + 
				"                GROUP BY id) as z;";
		
    	try {
    		conn = DatabaseInfo.getConnection();
	        ps = conn.prepareStatement(getInvoicesFlatFormat);
	        rs = ps.executeQuery();
	        while(rs.next()) {
	        	String[] invoice = rs.getString(1).split(";");
	    		String invoiceCode = invoice[0];
	    		String customerCode = invoice[1];
	    		String personCode = invoice[2];
	    		String invoiceDate = invoice[3];
	    		String[] product = invoice[4].split(",");
	    		
	    		//Find associated Person using primaryContact
	    		//Re-read personList using PersonsReader
	    		ArrayList<Person> allPersons = this.readPersonsDB();
	    		ArrayList<Customer> allCustomers = this.readCustomersDB();
	    		ArrayList<Product> allProducts = this.readProductsDB();
	    		
	    		//Loop through personList to see if there is a matching code to the customer
	    		Customer customerMatch = null;
	    		Person personMatch = null;
	    		Product[] productMatch = new Product[product.length];
	    		
	    		for(Customer c : allCustomers) {
	    			if (customerCode.equals(c.getCustomerCode())) {
	    				customerMatch = c;
	    			}
	    		}
	    		for(Person p : allPersons) {
	    			if (personCode.equals(p.getPersonCode())) {
	    				personMatch = p;
	    			}
	    		}
	    		for(Product pr : allProducts) {
	    			for(int i = 0; i < product.length; i++) {
	    				String[] productData = product[i].split(":");
	    				String productCode = productData[0];
	    				int productQuantity = Integer.parseInt(productData[1]);
	    				String codeMatch = pr.getProductCode();
	    				if(productCode.equals(codeMatch)) {
	    					if(pr instanceof Movie) {
	    						Movie m = (Movie)pr;
	    						productMatch[i] = new Movie(m);
	    						productMatch[i].setQuantity(productQuantity);
	    					}else if(pr instanceof ParkingPass){
	    						String matchingMovie = null;
	    	    				if(productData.length == 3) {
	    	    					matchingMovie = productData[2];
	    	    				}
	    						ParkingPass p = (ParkingPass)pr;
	    						for(int j = 0; j < product.length; j++) {
	    							String[] findMovie = product[j].split(":");
	    							String code = findMovie[0];
	    							if(code.equals(matchingMovie)) {
	    								p.setNumOfTickets(Integer.parseInt(findMovie[1]));
	    								p.setMatchingMovie(matchingMovie);
	    							}
	    						}
	    						productMatch[i] = new ParkingPass(p);
	    						productMatch[i].setQuantity(productQuantity);
	    					}else if(pr instanceof Refreshment) {
	    						Refreshment r = (Refreshment)pr;
	    						for(Product m: allProducts) {
	    							for(int k = 0; k < product.length; k++) {
	    								String[] findMovie = product[k].split(":");
	    			    				String code = findMovie[0];
	    								if(m.getProductCode().equals(code) && m instanceof Movie) {
	    									r.setMovieExists(true);
	    									break;
	    								}
	    							}
	    						}
	    						productMatch[i] = new Refreshment(r);
	    						productMatch[i].setQuantity(productQuantity);
	    					}else if(pr instanceof SeasonPass) {
	    						SeasonPass sp = (SeasonPass)pr;
	    						sp.setInvoiceDate(invoiceDate);
	    						productMatch[i] = new SeasonPass(sp);
	    						productMatch[i].setQuantity(productQuantity);
	    					}
	    				}
	    			}
	    		}
	    		//Add invoice to invoiceList
	    		invoicesLinkedList.addInvoiceOrdered((new Invoice(invoiceCode, customerMatch, personMatch, invoiceDate, productMatch)));
	    	}
    	}
    	catch (SQLException e) {
    		System.out.print("Problem reading invoices and storing to list.");
    		e.printStackTrace();
    	}
    	
    	//Close resources.
    			try {
    				rs.close();
    				ps.close();
    				conn.close();	
    			}
    			catch (SQLException e) {
    				e.printStackTrace();
    			}
    			
		return invoicesLinkedList;   
}
	
}
