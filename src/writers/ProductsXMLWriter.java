package writers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import entities.Movie;
import entities.ParkingPass;
import entities.Products;
import entities.Refreshment;
import entities.SeasonPass;

public class ProductsXMLWriter {

	public void xmlConverter(ArrayList<Products> arrayList) {
		XStream xstream = new XStream();
		try {
			PrintWriter pw = new PrintWriter("data/Products.xml");
			
			xstream.alias("movie",  Movie.class);
			xstream.alias("seasonPass", SeasonPass.class);
			xstream.alias("parkingPass", ParkingPass.class);
			xstream.alias("Refreshment", Refreshment.class);
			pw.println("<products>");
			for(Products p : arrayList) {
				String xmlPerson = xstream.toXML(p);
				pw.println(xmlPerson);
			}
			pw.println("</products>");
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.print("Cannot create file.");
		}
		
	}
}
