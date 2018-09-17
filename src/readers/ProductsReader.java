package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Address;
import entities.Movie;
import entities.ParkingPass;
import entities.Products;
import entities.Refreshment;
import entities.SeasonPass;

public class ProductsReader {
	
	public ArrayList<Products> readProducts() {
		Scanner s = null;
    	try {
			s = new Scanner(new File("data/Products.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	ArrayList<Products> p = new ArrayList<Products>();
    	s.nextLine();
    	while(s.hasNext()) {
    		String[] product = s.nextLine().split(";");
    		String type = product[1];
    		switch(type) {
    		case "M": 
    			String[] a = product[4].split(",");
    			Address theatreAddress = new Address(a[0],a[1],a[2],a[3],a[4]);
    			p.add(new Movie(product[0],product[1],Double.parseDouble(product[6]),product[2],product[3],theatreAddress, product[5]));
    			break;
    		case "S":
    			p.add(new SeasonPass(product[0],product[1],Double.parseDouble(product[5]),product[2],product[3],product[4]));
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
}
