package driver;

import java.util.ArrayList;

import entities.Person;
import readers.PersonsReader;
import writers.PersonsXMLWriter;

public class DataConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Person> person = null;
		PersonsReader p = new PersonsReader();
		person = p.readPersons();
		PersonsXMLWriter writer = new PersonsXMLWriter();
		writer.xmlConverter(person);
	}

}
