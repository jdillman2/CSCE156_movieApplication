package writers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

import entities.Customer;
import entities.Person;
import entities.Products;

public class JSONWriter {
	
	public void personJSONConverter(ArrayList<Person> arrayList) {
		//Instantiate XStream to convert objects to XML
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			PrintWriter pw = new PrintWriter("data/Persons.json");
			//Aliases assigned for cleaner XML appearance
			
			pw.println("{\"persons\":");
			//Loop through array to convert objects to XML
			String jsonPerson = gson.toJson(arrayList);
			pw.println(jsonPerson);
			pw.println("}");
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.print("Cannot create file.");
		}
	}
	
	public void CustomerJSONConverter(ArrayList<Customer> customerList) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			PrintWriter pw = new PrintWriter("data/Customers.json");
			
			pw.println("{\"customers\":");
			String jsonCustomer = gson.toJson(customerList);
			pw.println(jsonCustomer);
			pw.println("}");
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.print("Cannot create file.");
		}
	}
	
	public void ProductsJSONConverter(ArrayList<Products> arrayList) {
		JsonSerializer<DateTime> movieSerializer = new JsonSerializer<DateTime>() {
			  @Override
			  public JsonElement serialize(DateTime dateTime, Type typeOfSrc, JsonSerializationContext 
				             context) {
		     
				  String dtString = dateTime.toString("dd-MM-yyyy HH:mm");
			      return new JsonPrimitive(dtString);
				  
			  }
		};
		JsonSerializer<DateTime> seasonPassSerializer = new JsonSerializer<DateTime>() {
			  @Override
			  public JsonElement serialize(DateTime dateTime, Type typeOfSrc, JsonSerializationContext 
				             context) {
		     
				  String dtString = dateTime.toString("MM-dd-yyyy");
			      return new JsonPrimitive(dtString);
				  
			  }
		};
		
		try {
			PrintWriter pw = new PrintWriter("data/Products.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Gson movieGson = new GsonBuilder().registerTypeAdapter(DateTime.class, movieSerializer).setPrettyPrinting().create();
			Gson seasonPassGson = new GsonBuilder().registerTypeAdapter(DateTime.class, seasonPassSerializer).setPrettyPrinting().create();
			pw.println("{\"products\":[");
			String jsonProduct = "";
			for(Products p : arrayList) {
				if (p.getProductType().equals("M")) {
					jsonProduct += movieGson.toJson(p) + ",";
				}else if (p.getProductType().equals("S")) {
					jsonProduct += seasonPassGson.toJson(p) + ",";
				}else {
					jsonProduct += gson.toJson(p) + ",";
				}
			}
			jsonProduct = jsonProduct.replaceAll(",$", "");
			pw.println(jsonProduct);
			pw.println("]}");
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.print("Cannot create file.");
		}	
	}
}
