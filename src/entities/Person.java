package entities;

public class Person {
	
	String personCode;
	String lName;
	String fName;
	Address personAddress;
	String emails[];
	public Person(String personCode, String lName, String fName, Address personAddress, String[] emails) {
		super();
		this.personCode = personCode;
		this.lName = lName;
		this.fName = fName;
		this.personAddress = personAddress;
		this.emails = emails;
	}
	public Person(Person oldPerson) {
		this.personCode = oldPerson.personCode;
		this.lName = oldPerson.lName;
		this.fName = oldPerson.fName;
		this.personAddress = new Address(oldPerson.getPersonAddress());
		this.emails = oldPerson.emails;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public Address getPersonAddress() {
		return personAddress;
	}
	public void setPersonAddress(Address personAddress) {
		this.personAddress = personAddress;
	}
	public String[] getEmails() {
		return emails;
	}
	public void setEmails(String[] emails) {
		this.emails = emails;
	}
	
	public String getName() {
		return this.lName + ", " + this.fName;
	}
	
}
