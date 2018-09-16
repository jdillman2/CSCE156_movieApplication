package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Address;
import entities.Person;

public class PersonsReader {
	public ArrayList<Person> readPersons(){
		Scanner s = null;
    	try {
			s = new Scanner(new File("data/Persons.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	ArrayList<Person> personList = new ArrayList<Person>();
    	s.nextLine();
    	while(s.hasNext()) {
    		String[] person = s.nextLine().split(";");
    		String[] name = person[1].split(",");
    		String[] a = person[2].split(",");
    		Address address = new Address(a[0],a[1],a[2],a[3],a[4]);
    		String[] email = person[3].split(",");
    		personList.add(new Person(person[0], name[0], name[1], address, email));
    	}
    	s.close();
    	return personList;
	}
}
