package unl.cse.movieApp.phase1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePersons = "data/Persons.dat";
		String fileCustomers = "data/Customers.dat";
		String fileProducts = "data/Products.dat";
		Scanner personScanner = null;
		Scanner customerScanner = null;
		Scanner productScanner = null;
		try {
			personScanner = new Scanner(new File(filePersons));
			customerScanner = new Scanner(new File(fileCustomers));
			productScanner = new Scanner(new File(fileProducts));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Person Persons[] = new Person[personScanner.nextInt()];
		//Customer Customers[] = new Customer[customerScanner.nextInt()];
		//Product Products[] = new Product[productScanner.nextInt()];
		
		personScanner.nextLine();
		
		JSONObject personsJSON = new JSONObject();
		JSONArray array = new JSONArray();
		for(int i = 0; i < personScanner.nextInt(); i++) {
			JSONObject currentObject = new JSONObject();
			JSONObject currentAddressObj = new JSONObject();
			String personData[] = personScanner.nextLine().split(";");
			String code = personData[0];
			String name[] = personData[1].split(",");
			String address[] = personData[2].split(",");
			Address currentAddress = new Address(address[0].trim(), address[1].trim(), address[2].trim(), address[3].trim(), address[4].trim());
			currentAddressObj.put("street", currentAddress.getStreet());
			currentAddressObj.put("city", currentAddress.getCity());
			currentAddressObj.put("state", currentAddress.getState());
			currentAddressObj.put("zip", currentAddress.getZip());
			currentAddressObj.put("country", currentAddress.getCountry());
			if(personData.length > 3) {
				String email[] = personData[3].split(",");
			}else {
				String email[] = null;
			}
			currentObject.put("personCode", personData[0]);
		}
		
		personScanner.close();
		customerScanner.close();
		productScanner.close();
		
		
	}

}
