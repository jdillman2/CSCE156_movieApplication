package driver;

import java.util.ArrayList;

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
        //generateSummaryReport();
        
        //Print invoices header
        System.out.println("Invididual Invoice Detail Reports\n"
                + "=========================================================");
        //For each invoice, print a detailed report
        for(Invoice i : invoices) {
            generateDetailReport(i);
        }
    
    }
    
    public static void generateSummaryReport() {
        
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
                + "%s\n--------------------------------------" 
                , c.getName(), c.getCustomerCode(), c.getType(), p.getlName(), p.getfName(), a.toString());
        
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
        //TO-DO: FIX PRODUCT TYPE FULL PRINT OUT & ADDRESS PRINT OUT
        if(p instanceof Movie) {
            //Print movie details
            System.out.printf("%s\t", p.getProductCode());
            String movieDetails = String.format("%s '%s' @ %s", p.getProductType(), ((Movie) p).getTitle(), ((Movie) p).getTheatreAddress().getStreet());
            System.out.printf("%-60s", movieDetails);
            System.out.println();
            
            //TO-DO: Print movie cost
        }
        else if (p instanceof ParkingPass) {
            //TO-DO: How to get connected movie code?
            System.out.printf("%s\t", p.getProductCode());
            String ppDetails = String.format("%s", p.getProductType());
            System.out.printf("%-60s", ppDetails);
            System.out.println();
        }
        else if (p instanceof Refreshment) {
            System.out.printf("%s\t", p.getProductCode());
            String rDetails = String.format("%s ( %d units @ $%.2f/unit with x off)", ((Refreshment)p).getName(), p.getQuantity(), p.getPrice());
            System.out.printf("%-60s", rDetails);
            System.out.println();
        }
        else if (p instanceof SeasonPass) {
            System.out.printf("%s\t", p.getProductCode());
            String sDetails = String.format("%s", p.getProductType());
            System.out.printf("%-60s", sDetails);
            System.out.println();
        }
    }
        
}

