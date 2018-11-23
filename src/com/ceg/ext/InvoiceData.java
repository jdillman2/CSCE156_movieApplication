package com.ceg.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import readers.DatabaseInfo;

/*
*  This is a collection of utility methods that define a general API for
*  interacting with the database supporting this application.
*  15 methods in total, add more if required.
*  Do not change any method signatures or the package name.
*
*/
public class InvoiceData {
    static Connection conn = null;
    static PreparedStatement ps = null;
/**
 * 1. Method that removes every person record from the database
 */
public static void removeAllPersons() {
    
    String update1 = "UPDATE Invoice SET SalesPersonID = 0;";
    String update2 = "UPDATE Customers SET PrimaryContact = 0;"; 
    String delete1 = "DELETE FROM Email;"; 
    String delete2 = "DELETE FROM Address WHERE PersonID > 0;"; 
    String delete3 = "DELETE FROM Persons WHERE id > 0;";
    
    //Remove records from Email, Address, Persons tables and set SalesPersonID to default value
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(update1);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(update2);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(delete1);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(delete2);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(delete3);
        ps.executeUpdate();
    } 
    catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    //Close resources
    finally {
        try {
            ps.close();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
/**
 * 2. Method to add a person record to the database with the 
provided data.
 *
 * @param personCode
 * @param firstName
 * @param lastName
 * @param street
 * @param city
 * @param state
 * @param zip
 * @param country
 */
public static void addPerson(String personCode, String firstName, 
String lastName, String street, String city, String state, String zip, 
String country) {
    String insertP = "INSERT INTO Persons(personCode, fName, lName)\r\n"+
                   "VALUES(?,?,?);";
    String getID = "SELECT id FROM Persons WHERE PersonCode = ?;";
    String insertA = "INSERT INTO Address(PersonID, Street, City, State, PostalCode, Country)\r\n"+
                     "VALUES(?,?,?,?,?,?);";
    int newID = 0;
    
    //Add new record to Persons table and new record to Address table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(insertP);
        ps.setString(1, personCode);
        ps.setString(2, firstName);
        ps.setString(3, lastName);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(getID);
        ps.setString(1, personCode);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            newID = rs.getInt(1);
        }
        rs.close();
        ps.close();
        ps = conn.prepareStatement(insertA);
        ps.setInt(1, newID);
        ps.setString(2, street);
        ps.setString(3, city);
        ps.setString(4, state);
        ps.setString(5, zip);
        ps.setString(6, country);
        ps.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
      } 
    
    //Close resources
    finally {
          try {
            ps.close();
            conn.close();
          } 
          catch (SQLException e) {
            e.printStackTrace();
          }
          }
}
/**
 * 3. Adds an email record corresponding person record corresponding 
to the
 * provided <code>personCode</code>
 *
 * @param personCode
 * @param email
 */
public static void addEmail(String personCode , String email) {
    
    String testForEmailCopy = "SELECT * FROM Email WHERE accountID = ? and email = ?;";
    String getPersonID = "SELECT id FROM Persons WHERE personCode = ?;";
    String addEmail = "INSERT INTO Email(accountID, email) VALUES (?, ?);";
    boolean copy = false;
    int personID = 0;
    ResultSet rs = null;
    
    //Check for record with duplicate values in Email table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(getPersonID);
        ps.setString(1, personCode);
        rs = ps.executeQuery();
        if(rs.next()) {
        	personID = rs.getInt(1);
        }
        rs.close();
        ps.close();
        ps = conn.prepareStatement(testForEmailCopy);
        ps.setInt(1, personID);
        ps.setString(2, email);
        rs = ps.executeQuery();
        if(rs.next())
        {
            copy = true;
        }
    } catch (SQLException e) {
        System.out.print("Problem checking for copy of email.");
        e.printStackTrace();
      }
    
    //If the record does not have a duplicate, add it to the Email table
    if(copy == false)
    {
        try {
            ps = conn.prepareStatement(addEmail);
            ps.setInt(1, personID);
            ps.setString(2, email);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException f) {
                System.out.print("Problem adding email.");
                f.printStackTrace();
          }
    } 
    
    //Close resources
    try {
            conn.close();
            ps.close();
    }
    catch (SQLException f) {
            f.printStackTrace();
    }
}
/**
 * 4. Method that removes every customer record from the database
 */
public static void removeAllCustomers() {
    
    String updateInvoice = "UPDATE Invoice SET CustomerID = 0;";
    String updateAddress = "DELETE FROM Address WHERE CustomerID > 0;";
    String updateCustomers = "DELETE FROM Customers WHERE id > 0;";
    
    //Update CustomerID column to default value in Invoice table
    //Delete Address records with a non-default CustomerID value
    //Delete all records from Customers table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(updateInvoice);
        ps.executeUpdate();
        ps = conn.prepareStatement(updateAddress);
        ps.executeUpdate();
        ps = conn.prepareStatement(updateCustomers);
        ps.executeUpdate();
        ps.close();
    }
    catch (SQLException e) {
        System.out.print("Problem deleting customers from database.");
        e.printStackTrace();
    } 
    
    //Close resources
    finally {
          try {
            ps.close();
            conn.close();
          } 
          catch (SQLException e) {
            e.printStackTrace();
          }
          }
}


public static void addCustomer(String customerCode, String 
customerType, String primaryContactPersonCode,String name, String street, 
String city, String state, String zip, String country) {
	
    //Assign value for customer type
    int cType = 1;
    int pContactID = 0;
    if(customerType.equals("Student")) {
        cType = 2;
    }
    
    ResultSet rs = null;
    
    String getPContactID = "SELECT id FROM Persons WHERE personCode = ?";
    String getNewCustomerID = "SELECT id FROM Customers WHERE customerCode = ?";
    String testForCustomerCopy = "SELECT * FROM Customers WHERE customerCode = ?";
    String addAddress = "INSERT INTO Address(CustomerID, Street, City, State, PostalCode, Country) VALUES (?, ?, ?, ?, ?, ?);";
    String addCustomer = "INSERT INTO Customers(customerCode, lname, primaryContact, category) VALUES (?, ?, ?, ?);";
    
    //Get the id value using the primaryContactPersonCode
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(getPContactID);
        ps.setString(1, primaryContactPersonCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            pContactID = rs.getInt("id");
        }
    }
    catch (SQLException e) {
        System.out.print("Problem getting primary contact id.");
        e.printStackTrace();
    }

    rs = null;
    boolean copy = false;
    
    //Check for duplicate record in Customers table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(testForCustomerCopy);
        ps.setString(1, customerCode);
        rs = ps.executeQuery();
        if(rs.next())
        {
            copy = true;
        }
    } catch (SQLException e) {
        System.out.print("Problem checking for copy of customer.");
        e.printStackTrace();
      }
    
    //If there is no duplicate record, add to Customers table
    if(copy == false)
    {
        int cID = 0;
        try {
            ps = conn.prepareStatement(addCustomer);
            ps.setString(1, customerCode);
            ps.setString(2, name);
            ps.setInt(3, pContactID);
            ps.setInt(4, cType);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding customer.");
            e.printStackTrace();
          }
        
        //Obtain the id value for the new customer
        try {
            ps = conn.prepareStatement(getNewCustomerID);
            ps.setString(1, customerCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                cID = rs.getInt("id");
            }
        }
        catch (SQLException e) {
            System.out.print("Problem getting customerID.");
            e.printStackTrace();
          }
        
        //Add new record to Address table for the new customer
        try {
            ps = conn.prepareStatement(addAddress);
            ps.setInt(1, cID);
            ps.setString(2, street);
            ps.setString(3, city);
            ps.setString(4, state);
            ps.setString(5, zip);
            ps.setString(6, country);
            ps.executeUpdate();
        } catch (SQLException e) {
                System.out.print("Problem adding address.");
                e.printStackTrace();
          }
    } 
    
    //Close resources
    try {
            conn.close();
            ps.close();
    }
    catch (SQLException f) {
            f.printStackTrace();
    }
}

/**
 * 5. Removes all product records from the database
 */
public static void removeAllProducts() {
    String updatePAddress = "DELETE FROM Address WHERE ProductID > 0;";
    String updateInvoiceProducts = "DELETE FROM Invoice_Products WHERE MovieID > 0 OR SeasonPassID > 0 OR ParkingPassID > 0 OR RefreshmentID > 0;";
    String updateMovies = "DELETE FROM Movies WHERE id > 0;";
    String updateSeasonPass = "DELETE FROM Season_Pass WHERE id > 0;";
    String updateRefreshment = "DELETE FROM Refreshment WHERE id > 0;";
    String updateParkingPass = "DELETE FROM Parking_Pass WHERE id > 0;";
    
    //Remove Address records corresponding with a productID
    //Remove all records from InvoiceProducts, Movies, Season_Pass, Refreshment, Parking_Pass
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(updatePAddress);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(updateInvoiceProducts);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(updateMovies);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(updateSeasonPass);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(updateRefreshment);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(updateParkingPass);
        ps.executeUpdate();
    }
    catch (SQLException e) {
        System.out.print("Problem adding customer.");
        e.printStackTrace();
      }
    
    //Close resources
        try {
                conn.close();
                ps.close();
        }
        catch (SQLException f) {
                f.printStackTrace();
        }
}
/**
 * 6. Adds an movieTicket record to the database with the provided 
data.
 */
public static void addMovieTicket(String productCode, String 
dateTime, String movieName, String street, String city, String state, 
String zip, String country, String screenNo, double pricePerUnit) {
    
    String updateMovies = "INSERT INTO Movies (productCode, description, price, movieTime, theatreNo) VALUES (?, ?, ?, ?, ?);";
    String getProductID = "SELECT id FROM Movies WHERE productCode = ?;";
    String updateAddress = "INSERT INTO Address (ProductID, Street, City, State, PostalCode, Country) VALUES (?, ?, ?, ?, ?, ?);";
    String checkForMovieCopy = "SELECT * FROM Movies WHERE productCode = ?";
    ResultSet rs = null;
    int movieID = 0;
    
    boolean copy = false;
    
    //Check for duplicate values in Movies table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(checkForMovieCopy);
        ps.setString(1, productCode);
        rs = ps.executeQuery();
        
        if(rs.next()) {
            copy = true;
        }
    } catch (SQLException e) {
        System.out.print("Problem checking for movie copy.");
        e.printStackTrace();
    }
    
    //If no duplicate record, add new record to Movies
    if(copy == false) {
        
        try {
            
            ps = conn.prepareStatement(updateMovies);
            ps.setString(1, productCode);
            ps.setString(2, movieName);
            ps.setDouble(3, pricePerUnit);
            ps.setString(4, dateTime);
            ps.setString(5, screenNo);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding movie.");
            e.printStackTrace();
        }
        
        //Obtain new Movies record's id value
        try {
            ps = conn.prepareStatement(getProductID);
            ps.setString(1, productCode);
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                movieID = rs.getInt("id");
            }
        }
        catch (SQLException e) {
            System.out.print("Problem getting movieID.");
            e.printStackTrace();
        }
        
        //Update Address with new record corresponding with movieID
        try {
            ps = conn.prepareStatement(updateAddress);
            ps.setInt(1, movieID);
            ps.setString(2, street);
            ps.setString(3, city);
            ps.setString(4, state);
            ps.setString(5, zip);
            ps.setString(6, country);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding address.");
            e.printStackTrace();
        }
    }
    
    //Close resources
    try {
            conn.close();
            ps.close();
    }
    catch (SQLException f) {
            f.printStackTrace();
    }
}
/**
 * 7. Adds a seasonPass record to the database with the provided 
data.
 */
public static void addSeasonPass(String productCode, String name, 
String seasonStartDate, String seasonEndDate, double cost) {
    
    String updateSP= "INSERT INTO Season_Pass (productCode, description, price, startDate, endDate) VALUES (?, ?, ?, ?, ?);";
    String checkForSPCopy = "SELECT * FROM Season_Pass WHERE productCode = ?";
    ResultSet rs = null;
    
    boolean copy = false;
    
    //Check for duplicate record in Season_Pass table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(checkForSPCopy);
        ps.setString(1, productCode);
        rs = ps.executeQuery();
        
        if(rs.next()) {
            copy = true;
        }
    } catch (SQLException e) {
        System.out.print("Problem checking for season pass copy.");
        e.printStackTrace();
    }
    
    //If no duplicate record, add new record to Season_Pass
    if(copy == false) {
        
        try {
            
            ps = conn.prepareStatement(updateSP);
            ps.setString(1, productCode);
            ps.setString(2, name);
            ps.setDouble(3, cost);
            ps.setString(4, seasonStartDate);
            ps.setString(5, seasonEndDate);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding season pass.");
            e.printStackTrace();
        }
    }
    
    //Close resources
    try {
            conn.close();
            ps.close();
    }
    catch (SQLException f) {
            f.printStackTrace();
    }
  
}
/**
 * 8. Adds a ParkingPass record to the database with the provided 
data.
 */
public static void addParkingPass(String productCode, double 
parkingFee) {
    
    String updatePP= "INSERT INTO Parking_Pass (productCode, price) VALUES (?, ?);";
    String checkForPPCopy = "SELECT * FROM Parking_Pass WHERE productCode = ?";
    ResultSet rs = null;
    
    boolean copy = false;
    
    //Check for duplicate record in Parking_Pass table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(checkForPPCopy);
        ps.setString(1, productCode);
        rs = ps.executeQuery();
        
        if(rs.next()) {
            copy = true;
        }
    } catch (SQLException e) {
        System.out.print("Problem checking for parking pass copy.");
        e.printStackTrace();
    }
    
    //If no duplicate record, add new record to Parking_Pass
    if(copy == false) {
        
        try {
            
            ps = conn.prepareStatement(updatePP);
            ps.setString(1, productCode);
            ps.setDouble(2, parkingFee);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding parking pass.");
            e.printStackTrace();
        }
       
    }
    
    //Close resources
    try {
            conn.close();
            ps.close();
    }
    catch (SQLException f) {
            f.printStackTrace();
    }

}
/**
 * 9. Adds a refreshment record to the database with the provided 
data.
 */
public static void addRefreshment(String productCode, String name, 
double cost) {

    String updateR= "INSERT INTO Refreshment (productCode, description, price) VALUES (?, ?, ?);";
    String checkForRCopy = "SELECT * FROM Refreshment WHERE productCode = ?";
    ResultSet rs = null;
    
    boolean copy = false;
    
    //Check for duplicate record in Refreshment table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(checkForRCopy);
        ps.setString(1, productCode);
        rs = ps.executeQuery();
        
        if(rs.next()) {
            copy = true;
        }
    } catch (SQLException e) {
        System.out.print("Problem checking for refreshment copy.");
        e.printStackTrace();
    }
    
    //If no duplicate record, add new record to Refreshment table
    if(copy == false) {
        
        try {
            ps = conn.prepareStatement(updateR);
            ps.setString(1, productCode);
            ps.setString(2, name);
            ps.setDouble(3, cost);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding refreshment.");
            e.printStackTrace();
        }
    }
    
    //Close resources
    try {
            conn.close();
            ps.close();
    }
    catch (SQLException f) {
            f.printStackTrace();
    }

}
/**
 * 10. Removes all invoice records from the database
 */
public static void removeAllInvoices() {
	String deleteInvoiceProducts = "DELETE FROM Invoice_Products;";
    String deleteInvoice = "DELETE FROM Invoice;";
    
    //Delete all records from Invoice_Products and Invoice table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(deleteInvoiceProducts);
        ps.executeUpdate();
        ps = conn.prepareStatement(deleteInvoice);
        ps.executeUpdate();
        ps.close();
    }
    catch (SQLException e) {
        System.out.print("Problem deleting invoices from database.");
        e.printStackTrace();
    } 
    
    //Close resources
    finally {
          try {
            ps.close();
            conn.close();
          } 
          catch (SQLException e) {
            e.printStackTrace();
          }
          }
}
/**
 * 11. Adds an invoice record to the database with the given data.
 */
public static void addInvoice(String invoiceCode, String 
customerCode, String salesPersonCode, String invoiceDate) {
	
	int cID = 0;
	int pID = 0;
	ResultSet rs = null;
	
	String getPersonID = "SELECT id FROM Persons WHERE personCode = ?";
    String getCustomerID = "SELECT id FROM Customers WHERE customerCode = ?";
    String testForInvoiceCopy = "SELECT * FROM Invoice WHERE InvoiceCode = ?";
    String addInvoice = "INSERT INTO Invoice(InvoiceCode, CustomerID, SalesPersonID, InvoiceDate) VALUES (?, ?, ?, ?);";
    
    //Get id values from Persons and Customers table
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(getPersonID);
        ps.setString(1, salesPersonCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            pID = rs.getInt("id");
        }
        rs = null;
        ps = conn.prepareStatement(getCustomerID);
        ps.setString(1, customerCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            cID = rs.getInt("id");
        }
    }
    catch (SQLException e) {
        System.out.print("Problem getting ids.");
        e.printStackTrace();
    }
    rs = null;
    boolean copy = false;
    
    //Check for duplicate record in Invoice table
    try {
        ps = conn.prepareStatement(testForInvoiceCopy);
        ps.setString(1, invoiceCode);
        rs = ps.executeQuery();
        
        if(rs.next()) {
            copy = true;
        }
    } catch (SQLException e) {
        System.out.print("Problem checking for invoice copy.");
        e.printStackTrace();
    }
    
    //If no duplicate record, add new record to Invoice table
    if(copy == false) {
        
        try {
            ps = conn.prepareStatement(addInvoice);
            ps.setString(1, invoiceCode);
            ps.setInt(2, cID);
            ps.setInt(3, pID);
            ps.setString(4, invoiceDate);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding invoice.");
            e.printStackTrace();
        }  
        
        //Close resources
	    try {
	    		conn.close();
	    		ps.close();
	    }
	    catch (SQLException f) {
	        	f.printStackTrace();
	    }
    }
}
/**
 * 12. Adds a particular movieticket (corresponding to 
<code>productCode</code>
 * to an invoice corresponding to the provided 
<code>invoiceCode</code> with
 * the given number of units
 */
public static void addMovieTicketToInvoice(String invoiceCode, 
String productCode, int quantity) {
	
	int invoiceID = 0;
	int movieID = 0;
	boolean movieExists = false;
	ResultSet rs = null;
	
	String getInvoice = "SELECT id FROM Invoice WHERE InvoiceCode = ?";
	String getMovie = "SELECT id FROM Movies WHERE ProductCode = ?";
	String checkDuplicate = "SELECT * FROM Invoice_Products WHERE InvoiceID = ? AND MovieID = ?";
	String addDuplicate = "UPDATE Invoice_Products SET Quantity = Quantity + ? WHERE InvoiceID = ? AND MovieID = ?";
	String insertMovie = "INSERT INTO Invoice_Products(InvoiceID, MovieID, Quantity) VALUES(?,?,?)";
	
	//Get id values from Invoice and Movies tables
	try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(getInvoice);
        ps.setString(1, invoiceCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            invoiceID = rs.getInt("id");
        }
        rs = null;
        ps = conn.prepareStatement(getMovie);
        ps.setString(1, productCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            movieID = rs.getInt("id");
        }
    }
    catch (SQLException e) {
        System.out.print("Problem getting ids.");
        e.printStackTrace();
    }
	rs = null;
	
	//Check for duplicate record in Invoice_Products
	 try {
	        ps = conn.prepareStatement(checkDuplicate);
	        ps.setInt(1, invoiceID);
	        ps.setInt(2, movieID);
	        rs = ps.executeQuery();
	        if(rs.next()) {
	            movieExists = true;
	        }
	    } catch (SQLException e) {
	        System.out.print("Problem checking for invoice product");
	        e.printStackTrace();
	    }
	rs = null;
	
	//If a duplicate record exists, update the existing record's quantity value
	if(movieExists) {
		try {
            ps = conn.prepareStatement(addDuplicate);
            ps.setInt(1, quantity);
            ps.setInt(2, invoiceID);
            ps.setInt(3, movieID);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem updating invoice product quantity");
            e.printStackTrace();
        } 
	}
	
	//Else, if a duplicate record does not exist, add a new record to the Invoice_Products
	else {
		try {
            ps = conn.prepareStatement(insertMovie);
            ps.setInt(1, invoiceID);
            ps.setInt(2, movieID);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding invoice product.");
            e.printStackTrace();
        } 
	}
	
	//Close resources
    try {
    		conn.close();
    		ps.close();
    }
    catch (SQLException f) {
        	f.printStackTrace();
    }
}
/*
 * 13. Adds a particular seasonpass (corresponding to 
<code>productCode</code>
 * to an invoice corresponding to the provided 
<code>invoiceCode</code> with
 * the given begin/end dates
 */
public static void addSeasonPassToInvoice(String invoiceCode, String 
productCode, int quantity) {
	
	int invoiceID = 0;
	int seasonPassID = 0;
	boolean spExists = false;
	ResultSet rs = null;
	
	String getInvoice = "SELECT id FROM Invoice WHERE InvoiceCode = ?";
	String getSeasonPass = "SELECT id FROM Season_Pass WHERE ProductCode = ?";
	String checkDuplicate = "SELECT * FROM Invoice_Products WHERE InvoiceID = ? AND SeasonPassID = ?";
	String addDuplicate = "UPDATE Invoice_Products SET Quantity = Quantity + ? WHERE InvoiceID = ? AND SeasonPassID = ?";
	String insertSP = "INSERT INTO Invoice_Products(InvoiceID, SeasonPassID, Quantity) VALUES(?,?,?)";
	
	//Get id values from Invoice and SeasonPass tables
	try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(getInvoice);
        ps.setString(1, invoiceCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            invoiceID = rs.getInt("id");
        }
        rs = null;
        ps = conn.prepareStatement(getSeasonPass);
        ps.setString(1, productCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            seasonPassID = rs.getInt("id");
        }
    }
    catch (SQLException e) {
        System.out.print("Problem getting ids.");
        e.printStackTrace();
    }
	rs = null;
	
	//Check if a duplicate record exists in Invoice_Products
	 try {
	        ps = conn.prepareStatement(checkDuplicate);
	        ps.setInt(1, invoiceID);
	        ps.setInt(2, seasonPassID);
	        rs = ps.executeQuery();
	        if(rs.next()) {
	            spExists = true;
	        }
	    } catch (SQLException e) {
	        System.out.print("Problem checking for invoice product");
	        e.printStackTrace();
	    }
	rs = null;
	
	//If a duplicate record exists, update the existing record's quantity value
	if(spExists) {
		try {
            ps = conn.prepareStatement(addDuplicate);
            ps.setInt(1, quantity);
            ps.setInt(2, invoiceID);
            ps.setInt(3, seasonPassID);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem updating invoice product quantity");
            e.printStackTrace();
        } 
	}
	
	//Else, if a duplicate record does not exist, add a new record to the Invoice_Products
	else {
		try {
            ps = conn.prepareStatement(insertSP);
            ps.setInt(1, invoiceID);
            ps.setInt(2, seasonPassID);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding invoice product.");
            e.printStackTrace();
        } 
	}
	
	 //Close resources.
    try {
    		conn.close();
    		ps.close();
    }
    catch (SQLException f) {
        	f.printStackTrace();
    }
	
}
/**
*  14. Adds a particular ParkingPass (corresponding to 
<code>productCode</code> to an
*  invoice corresponding to the provided <code>invoiceCode</code> 
with the given
*  number of quantity.
*  NOTE: ticketCode may be null
*/
public static void addParkingPassToInvoice(String invoiceCode, String 
productCode, int quantity, String ticketCode) {

	int invoiceID = 0;
	int parkingPassID = 0;
	int matchingMovieID = 0;
	boolean ppExists = false;
	ResultSet rs = null;
	
	String getInvoice = "SELECT id FROM Invoice WHERE InvoiceCode = ?";
	String getParkingPass = "SELECT id FROM Parking_Pass WHERE ProductCode = ?";
	String getMatchingMovie = "SELECT id FROM Movies WHERE ProductCode = ?";
	String checkDuplicate = "SELECT * FROM Invoice_Products WHERE InvoiceID = ? AND ParkingPassID = ?";
	String addDuplicate = "UPDATE Invoice_Products SET Quantity = Quantity + ? WHERE InvoiceID = ? AND ParkingPassID = ?";
	String updateMovie = "UPDATE Invoice_Products SET ParkingMovieID = ? WHERE InvoiceID = ? AND ParkingPassID = ?";
	String insertPP = "INSERT INTO Invoice_Products(InvoiceID, ParkingPassID, Quantity) VALUES(?,?,?)";
	
	//Get id values from Invoice and Parking_Pass tables
	try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(getInvoice);
        ps.setString(1, invoiceCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            invoiceID = rs.getInt("id");
        }
        rs = null;
        ps = conn.prepareStatement(getParkingPass);
        ps.setString(1, productCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            parkingPassID = rs.getInt("id");
        }
    }
    catch (SQLException e) {
        System.out.print("Problem getting ids.");
        e.printStackTrace();
    }
	rs = null;
	
	//Check for duplicate record in Invoice_Products table
	 try {
	        ps = conn.prepareStatement(checkDuplicate);
	        ps.setInt(1, invoiceID);
	        ps.setInt(2, parkingPassID);
	        rs = ps.executeQuery();
	        if(rs.next()) {
	            ppExists = true;
	        }
	    } catch (SQLException e) {
	        System.out.print("Problem checking for invoice product");
	        e.printStackTrace();
	    }
	rs = null;
	
	//If a duplicate record exists, update the existing record's quantity value
	if(ppExists) {
		try {
            ps = conn.prepareStatement(addDuplicate);
            ps.setInt(1, quantity);
            ps.setInt(2, invoiceID);
            ps.setInt(3, parkingPassID);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem updating invoice product quantity");
            e.printStackTrace();
        } 
	}
	//Else, if a duplicate record does not exist, add a new record to the Invoice_Products
	else {
		try {
            ps = conn.prepareStatement(insertPP);
            ps.setInt(1, invoiceID);
            ps.setInt(2, parkingPassID);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding invoice product.");
            e.printStackTrace();
        } 
	}
	
	//If a valid ticketCode has been passed, get the corresponding id value from the Movies table
	if(ticketCode != null) {
		try {
			rs = null;
            ps = conn.prepareStatement(getMatchingMovie);
            ps.setString(1, ticketCode);
            rs = ps.executeQuery();
             
            if (rs.next()) {
            	matchingMovieID = rs.getInt("id");
            }
        }
        catch (SQLException e) {
            System.out.print("Problem getting corresponding movieID for parking pass.");
            e.printStackTrace();
        }
		
		//Update record in Invoice_Products with the id corresponding to the movie
		try {
            ps = conn.prepareStatement(updateMovie);
            ps.setInt(1, matchingMovieID);
            ps.setInt(2, invoiceID);
            ps.setInt(3, parkingPassID);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem setting corresponding movieID for parking pass.");
            e.printStackTrace();
        }
	}
	
	//Close resources
    try {
    		conn.close();
    		ps.close();
    }
    catch (SQLException f) {
        	f.printStackTrace();
    }
	
}
/**
*  15. Adds a particular refreshment (corresponding to 
<code>productCode</code> to an
*  invoice corresponding to the provided <code>invoiceCode</code> 
with the given
*  number of quantity.
*/
public static void addRefreshmentToInvoice(String invoiceCode, String 
productCode, int quantity) {

	int invoiceID = 0;
	int refreshmentID = 0;
	boolean rExists = false;
	ResultSet rs = null;
	
	String getInvoice = "SELECT id FROM Invoice WHERE InvoiceCode = ?";
	String getRefreshment = "SELECT id FROM Refreshment WHERE ProductCode = ?";
	String checkDuplicate = "SELECT * FROM Invoice_Products WHERE InvoiceID = ? AND RefreshmentID = ?";
	String addDuplicate = "UPDATE Invoice_Products SET Quantity = Quantity + ? WHERE InvoiceID = ? AND RefreshmentID = ?";
	String insertR = "INSERT INTO Invoice_Products(InvoiceID, RefreshmentID, Quantity) VALUES(?,?,?)";
	
	//Get id values from Invoice and Refreshment tables
	try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(getInvoice);
        ps.setString(1, invoiceCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            invoiceID = rs.getInt("id");
        }
        rs = null;
        ps = conn.prepareStatement(getRefreshment);
        ps.setString(1, productCode);
        rs = ps.executeQuery();
        if(rs.next()) {
            refreshmentID = rs.getInt("id");
        }
    }
    catch (SQLException e) {
        System.out.print("Problem getting ids.");
        e.printStackTrace();
    }
	rs = null;
	
	//Check for duplicate record in Invoice_Products table
	 try {
	        ps = conn.prepareStatement(checkDuplicate);
	        ps.setInt(1, invoiceID);
	        ps.setInt(2, refreshmentID);
	        rs = ps.executeQuery();
	        if(rs.next()) {
	            rExists = true;
	        }
	    } catch (SQLException e) {
	        System.out.print("Problem checking for invoice product");
	        e.printStackTrace();
	    }
	rs = null;
	
	//If a duplicate record exists, update the existing record's quantity value
	if(rExists) {
		try {
            ps = conn.prepareStatement(addDuplicate);
            ps.setInt(1, quantity);
            ps.setInt(2, invoiceID);
            ps.setInt(3, refreshmentID);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem updating invoice product quantity");
            e.printStackTrace();
        } 
	}
	
	//Else, if a duplicate record does not exist, add a new record to the Invoice_Products
	else {
		try {
            ps = conn.prepareStatement(insertR);
            ps.setInt(1, invoiceID);
            ps.setInt(2, refreshmentID);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.print("Problem adding invoice product.");
            e.printStackTrace();
        } 
	}
	
    //Close resources
    try {
    		conn.close();
    		ps.close();
    }
    catch (SQLException f) {
        	f.printStackTrace();
    }
	
}
}