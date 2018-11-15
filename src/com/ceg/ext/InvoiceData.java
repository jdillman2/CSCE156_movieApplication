package com.ceg.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    //Close resources.
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
public static void addEmail(int personCode , String email) {
    
    String testForEmailCopy = "SELECT * FROM Email WHERE accountID = ? and email = ?;";
    String addEmail = "INSERT INTO Email(accountID, email) VALUES (?, ?);";
    boolean copy = false;
    ResultSet rs = null;
    try {
        conn = DatabaseInfo.getConnection();
        ps = conn.prepareStatement(testForEmailCopy);
        ps.setInt(1, personCode);
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
    if(copy == false)
    {
        try {
            ps = conn.prepareStatement(addEmail);
            ps.setInt(1, personCode);
            ps.setString(2, email);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException f) {
                System.out.print("Problem adding email.");
                f.printStackTrace();
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
 * 4. Method that removes every customer record from the database
 */
public static void removeAllCustomers() {
    
    String updateInvoice = "UPDATE Invoice SET CustomerID = 0;";
    String updateAddress = "DELETE FROM Address WHERE CustomerID > 0;";
    String updateCustomers = "DELETE FROM Customers WHERE id > 0;";
    
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
    //Close resources.
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
        try {
            ps = conn.prepareStatement(getNewCustomerID);
            ps.setString(1, customerCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                cID = rs.getInt("id");
                System.out.print("Got something.");
            }
        }
        catch (SQLException e) {
            System.out.print("Problem getting customerID.");
            e.printStackTrace();
          }
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
 * 5. Removes all product records from the database
 */
public static void removeAllProducts() {
    String updatePAddress = "DELETE FROM Address WHERE ProductID > 0;";
    String updateInvoiceProducts = "DELETE FROM Invoice_Products WHERE MovieID > 0 OR SeasonPassID > 0 OR ParkingPassID > 0 OR RefreshmentID > 0;";
    String updateMovies = "DELETE FROM Movies WHERE id > 0;";
    String updateSeasonPass = "DELETE FROM Season_Pass WHERE id > 0;";
    String updateRefreshment = "DELETE FROM Refreshment WHERE id > 0;";
    String updateParkingPass = "DELETE FROM Parking_Pass WHERE id > 0;";
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
    //Check for copy
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
    //If no copy, create new row in Movies and Address
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
 * 7. Adds a seasonPass record to the database with the provided 
data.
 */
public static void addSeasonPass(String productCode, String name, 
String seasonStartDate, String seasonEndDate, double cost) {
    
    String updateSP= "INSERT INTO Season_Pass (productCode, description, price, startDate, endDate) VALUES (?, ?, ?, ?, ?);";
    String checkForSPCopy = "SELECT * FROM Season_Pass WHERE productCode = ?";
    ResultSet rs = null;
    
    boolean copy = false;
    //Check for copy
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
    //If no copy, create new row in Season_Pass
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
        
    //Close resources.
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
 * 8. Adds a ParkingPass record to the database with the provided 
data.
 */
public static void addParkingPass(String productCode, double 
parkingFee) {
    
    String updatePP= "INSERT INTO Parking_Pass (productCode, price) VALUES (?, ?);";
    String checkForPPCopy = "SELECT * FROM Parking_Pass WHERE productCode = ?";
    ResultSet rs = null;
    
    boolean copy = false;
    //Check for copy
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
    //If no copy, create new row in Parking_Pass
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
        
    //Close resources.
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
 * 9. Adds a refreshment record to the database with the provided 
data.
 */
public static void addRefreshment(String productCode, String name, 
double cost) {

    String updateR= "INSERT INTO Refreshment (productCode, description, price) VALUES (?, ?, ?);";
    String checkForRCopy = "SELECT * FROM Refreshment WHERE productCode = ?";
    ResultSet rs = null;
    
    boolean copy = false;
    //Check for copy
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
    //If no copy, create new row in Refreshment
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
        
    //Close resources.
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

