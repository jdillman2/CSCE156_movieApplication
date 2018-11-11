package com.ceg.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
*  This is a collection of utility methods that define a general API for
*  interacting with the database supporting this application.
*  15 methods in total, add more if required.
*  Donot change any method signatures or the package name.
*
*/
public class InvoiceData {
/**
 * 1. Method that removes every person record from the database
 */
public static void removeAllPersons() {
	
	Connection conn = DatabaseInfo.getConnection();
	PreparedStatement ps;
	String update1 = "UPDATE Invoice SET SalesPersonID = 0;";
	String update2 = "UPDATE Customers SET PrimaryContact = 0;"; 
	String delete1 = "DELETE FROM Email;"; 
	String delete2 = "DELETE FROM Address WHERE PersonID > 0;"; 
	String delete3 = "DELETE FROM Persons WHERE id > 0;";
	try {
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
		ps.close();
		conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
	Connection conn = DatabaseInfo.getConnection();
	PreparedStatement ps;
	String insertP = "INSERT INTO Persons(personCode, fName, lName)\r\n"+
				   "VALUES(?,?,?);";
	String getID = "SELECT id FROM Persons WHERE PersonCode = ?;";
	String insertA = "INSERT INTO Address(PersonID, Street, City, State, PostalCode, Country)\r\n"+
					 "VALUES(?,?,?,?,?,?);";
	int newID = 0;
	try {
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
		ps.close();
		conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
public static void addEmail(String personCode, String email) {}
/**
 * 4. Method that removes every customer record from the database
 */
public static void removeAllCustomers() {}
public static void addCustomer(String customerCode, String 
customerType, String primaryContactPersonCode,String name, String street, 
String city, String state, String zip, String country) {}
/**
 * 5. Removes all product records from the database
 */
public static void removeAllProducts() {}
/**
 * 6. Adds an movieTicket record to the database with the provided 
data.
 */
public static void addMovieTicket(String productCode, String 
dateTime, String movieName, String street, String city,String state, 
String zip, String country, String screenNo, double pricePerUnit) {}
/**
 * 7. Adds a seasonPass record to the database with the provided 
data.
 */
public static void addSeasonPass(String productCode, String name, 
String seasonStartDate, String seasonEndDate, double cost) {}
/**
 * 8. Adds a ParkingPass record to the database with the provided 
data.
 */
public static void addParkingPass(String productCode, double 
parkingFee) {}
/**
 * 9. Adds a refreshment record to the database with the provided 
data.
 */
public static void addRefreshment(String productCode, String name, 
double cost) {}
/**
 * 10. Removes all invoice records from the database
 */
public static void removeAllInvoices() {}
/**
 * 11. Adds an invoice record to the database with the given data.
 */
public static void addInvoice(String invoiceCode, String 
customerCode, String salesPersonCode, String invoiceDate) {}
/**
 * 12. Adds a particular movieticket (corresponding to 
<code>productCode</code>
 * to an invoice corresponding to the provided 
<code>invoiceCode</code> with
 * the given number of units
 */
public static void addMovieTicketToInvoice(String invoiceCode, 
String productCode, int quantity) {}
/*
 * 13. Adds a particular seasonpass (corresponding to 
<code>productCode</code>
 * to an invoice corresponding to the provided 
<code>invoiceCode</code> with
 * the given begin/end dates
 */
public static void addSeasonPassToInvoice(String invoiceCode, String 
productCode, int quantity) {}
/**
*  14. Adds a particular ParkingPass (corresponding to 
<code>productCode</code> to an
*  invoice corresponding to the provided <code>invoiceCode</code> 
with the given
*  number of quantity.
*  NOTE: ticketCode may be null
*/
public static void addParkingPassToInvoice(String invoiceCode, String 
productCode, int quantity, String ticketCode) {}
/**
*  15. Adds a particular refreshment (corresponding to 
<code>productCode</code> to an
*  invoice corresponding to the provided <code>invoiceCode</code> 
with the given
*  number of quantity.
*/
public static void addRefreshmentToInvoice(String invoiceCode, String 
productCode, int quantity) {}
}
