package readers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ceg.ext.DatabaseInfo;

import entities.Address;
import entities.Person;

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
}
