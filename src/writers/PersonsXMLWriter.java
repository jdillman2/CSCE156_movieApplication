package writers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import entities.Person;

public class PersonsXMLWriter {
	
	public void xmlConverter(ArrayList<Person> arrayList) {
		XStream xstream = new XStream();
		try {
			PrintWriter pw = new PrintWriter("data/Persons.xml");
			
			xstream.alias("person",  Person.class);
			pw.println("<persons>");
			for(Person p : arrayList) {
				String xmlPerson = xstream.toXML(p);
				pw.write(xmlPerson);
			}
			pw.println("</persons>");
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.print("Cannot create file.");
		}
		
	}

}
