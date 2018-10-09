package driver;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entities.Address;
import entities.Customer;
import entities.Invoice;
import entities.Movie;
import entities.ParkingPass;
import entities.Person;
import entities.Product;
import entities.Refreshment;
import entities.SeasonPass;
import readers.FlatFileReader;

public class InvoiceReport {

    public static void main(String[] args) {
        //Read invoices to invoice ArrayList
        FlatFileReader ffr = new FlatFileReader();
        ArrayList<Invoice> invoices = ffr.readInvoices();
        
        //Generate summary report
        generateSummaryReport(invoices);
        
        //Print invoices header
        System.out.println("Invididual Invoice Detail Reports\n"
                + "=========================================================");
        //For each invoice, print a detailed report
        for(Invoice i : invoices) {
            generateDetailReport(i);
            System.out.printf("%102s", "=================================\n");
            System.out.printf("%-67s $%10.2f $%9.2f $%9.2f", "SUB-TOTALS", i.getInvoiceSubTotal(), i.getInvoiceTax(), i.getInvoiceTotalofTotals());
            if(i.getInvoiceDiscount() > 0)
            {
            	  System.out.print("\nDISCOUNT ( 8% STUDENT AND NO TAX )");
            	  double discount = 0 - i.getInvoiceDiscount();
            	  System.out.printf("%57s$%9.2f", "", discount);
            }
            if(i.getInvoiceAdditionalFee() > 0)
            {
            	 System.out.print("\nADDITIONAL FEE (Student)");
           	     System.out.printf("%67s$%9.2f", "", i.getInvoiceAdditionalFee());
            }
            System.out.printf("\n%-89s  $%9.2f", "TOTAL", i.getInvoiceGrandTotal());
            System.out.println();
            System.out.println();
            System.out.print("\t\t\tThank you for your purchase!");
            System.out.println();
            System.out.println();
        }
    
    }
    
    private static void generateSummaryReport(ArrayList<Invoice> invoices) {
    	double iSubtotals = 0.0;
    	double iFees = 0.0;
    	double iTaxes = 0.0;
    	double iDiscounts = 0.0;
    	double iTotals = 0.0;
    	System.out.print("=========================\nEXECUTIVE SUMMARY REPORT\n");
    	System.out.printf("%-66s %10s "
    			+ "%10s %10s %10s %10s\n", "=========================", "Subtotal", "Fees", "Taxes", "Discounts","Total");
    	for(Invoice i: invoices) {
        	i.printSummaryTotal();
        	iSubtotals += i.getInvoiceSubTotal();
        	iFees += i.getCustomer().getCustomerFee();
        	iTaxes += i.getInvoiceTax();
        	iDiscounts += i.getInvoiceDiscount() * -1;
        	iTotals += i.getInvoiceGrandTotal();
        }
    	System.out.println("======================================================================================"
    			+ "===================================");
    	System.out.printf("%-66s %2s %7.2f %2s %7.2f "
    			+ "%2s %7.2f %2s %7.2f %2s %7.2f"
    			+ "\n\n\n\n\n\n", "TOTALS", "$", iSubtotals, "$", iFees, "$", iTaxes, "$", iDiscounts, "$", iTotals);
    }
    
    public static void generateDetailReport(Invoice invoice) {
        //Invoice header
        System.out.printf("\nInvoice %s\n===========================", invoice.getInvoiceID());
        
        //Salesperson/Customer information
        Person sPerson = invoice.getSalesPerson();
        System.out.printf("\nSalesperson: %s, %s", sPerson.getlName(), sPerson.getfName());
        Customer c = invoice.getCustomer();
        Person p = c.getPrimaryContact();
        Address a = c.getAddress();
        System.out.printf("\nCustomer Info:\n\t"
                + "%s (%s)\n\t"
                + "[%s]\n\t"
                + "%s, %s\n\t"
                + "%s\n\t%s %s %s %s\n--------------------------------------" 
                , c.getName(), c.getCustomerCode(), c.getFullType(), p.getlName(), p.getfName(), a.getStreet(), a.getCity(), a.getState(), a.getZip(), a.getCountry());
        
        //Product receipt header
        System.out.print("\nCode\t");
        System.out.printf("%-60s %10s %10s %10s\n", "Item", "SubTotal", "Tax", "Total");
        //Product receipt
        
        for(Product product : invoice.getProducts()) {
            printProductDetails(product);
          
        }
    }
        
    private static void printProductDetails(Product p)
    {

        if(p instanceof Movie) {
        //Print movie details
            System.out.printf("%s\t", p.getProductCode());
            String movieDetails = String.format("%s '%s' @ %s", p.getFullType(), ((Movie) p).getTitle(), ((Movie) p).getTheatreAddress().getStreet());
            System.out.printf("%-60s", movieDetails);
            
        //Print movie cost
            System.out.printf("$%10.2f $%9.2f $%9.2f", p.getSubtotal(), p.getTAX(), p.getSubtotal() + p.getTAX());
            
  
            System.out.println();
            
        //Format movie DateTime
            DateTime dt = ((Movie) p).getMovieTime();
            DateTimeFormatter dtf = DateTimeFormat.forPattern("MMM d,YYYY HH:m");
            String formattedDateTime = dtf.print(dt);
        //Check if Tue/Thurs discount
            String movieDetails_2 = String.format("    \t%s (%d units @ %.2f/unit)", formattedDateTime, p.getQuantity(), p.getPrice());
            if(p.getDiscount() > 0)
            {
            	movieDetails_2 = String.format("    \t%s (%d units @ %.2f/unit - Tue/Thu 7%% off)", formattedDateTime, p.getQuantity(), p.getPrice());
            }
        //Print rest of movie details
            System.out.print(movieDetails_2);
            
            System.out.println();
        }
        else if (p instanceof ParkingPass) {
            //Get connected movie code
            System.out.printf("%s\t", p.getProductCode());
            
            //Test if free parking passes alloted, and if so, include in details
            String ppDetails = String.format("%s %s (%d units @ %.2f/unit)", p.getFullType(), ((ParkingPass) p).getMatchingMovie(), p.getQuantity(), p.getPrice());
            if(((ParkingPass) p).getNumOfTickets() > 0)
            {
            	int numOfTickets = ((ParkingPass) p).getNumOfTickets();
            	if(((ParkingPass) p).getNumOfTickets() > p.getQuantity())
            	{
            		numOfTickets = p.getQuantity();
            	}
            	ppDetails = String.format("%s %s (%d units @ %.2f/unit with %d free)", p.getFullType(), 
            			((ParkingPass) p).getMatchingMovie(), p.getQuantity(), p.getPrice(), numOfTickets);
            }
            //Print parking pass details
            System.out.printf("%-60s", ppDetails);

            //Print parking pass cost
            System.out.printf("$%10.2f $%9.2f $%9.2f", p.getSubtotal(), p.getTAX(), p.getSubtotal() + p.getTAX());
            
            System.out.println();
        }
        else if (p instanceof Refreshment) {
        	
            System.out.printf("%s\t", p.getProductCode());
            String rDetails = String.format("%s (%d units @ $%.2f/unit)", ((Refreshment)p).getName(), p.getQuantity(), p.getPrice());
          //Check if associated movie discount
            if(((Refreshment) p).isMovieExists()) {
        	   rDetails = String.format("%s (%d units @ $%.2f/unit with 5%% off)", ((Refreshment)p).getName(), p.getQuantity(), p.getPrice());
           }
          //Print refreshment details
            System.out.printf("%-60s", rDetails);
            
          //Print Refreshment cost
            System.out.printf("$%10.2f $%9.2f $%9.2f", p.getSubtotal(), p.getTAX(), p.getSubtotal() + p.getTAX());

            System.out.println();
        }
        else if (p instanceof SeasonPass) {
          //Print season pass details
            System.out.printf("%s\t", p.getProductCode());
            String sDetails = String.format("%s - %s", p.getFullType(), ((SeasonPass) p).getName());
            System.out.printf("%-60s", sDetails);
            
          //Print SeasonPass cost
            System.out.printf("$%10.2f $%9.2f $%9.2f", p.getSubtotal(), p.getTAX(), p.getSubtotal() + p.getTAX());
          
          //Check if prorated for any days
            String spDetails_2 = String.format("\n    \t(%d units @ $%.2f/unit + $8 fee/unit)", p.getQuantity(), p.getPrice());
            if(((SeasonPass) p).getInvoiceDate().isAfter(((SeasonPass) p).getStartDate())) {
            	int daysBetween = Days.daysBetween(((SeasonPass) p).getStartDate(), ((SeasonPass) p).getEndDate()).getDays();
            	spDetails_2 = String.format("\n    \t(%d units @ $%.2f/unit prorated %d/%d days + $8 fee/unit)", p.getQuantity(), p.getPrice(), ((SeasonPass) p).getProratedDays(), daysBetween);
            }
          //Print rest of season pass details
            System.out.print(spDetails_2);
            System.out.println();
        }
    }
        
}



