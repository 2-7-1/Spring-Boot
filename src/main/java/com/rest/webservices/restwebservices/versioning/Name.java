package com.rest.webservices.restwebservices.versioning;

public class Name {
	
	private String firstName;
	private String LastName;
	
	public Name() {
		super();
	}	
	
	public Name(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		LastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return LastName;
	}
	
	public void setLastName(String lastName) {
		LastName = lastName;
	}

}
