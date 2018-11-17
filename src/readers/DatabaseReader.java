package readers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ceg.ext.DatabaseInfo;

import entities.Address;
import entities.Customer;
import entities.General;
import entities.Person;
import entities.Student;

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
	    			customerList.add(new Student(code,type,rs.getString(2),primaryContact,name,customerAddress));
	    		}else {
	    			customerList.add(new General(code,type,rs.getString(2),primaryContact,name,customerAddress));
	    		}
	    	}
	        rs.close();
	        ps.close();
	        conn.close();
		} catch (SQLException e) {
	        e.printStackTrace();
	      } 
    	return customerList;
	}
}
