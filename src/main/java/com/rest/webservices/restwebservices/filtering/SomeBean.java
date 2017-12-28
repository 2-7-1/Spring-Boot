package com.rest.webservices.restwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

// Part of Dynamic filtering.  This annotation allows com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter 
// apply it's dynamic filtering in the FilteringController.java
@JsonFilter("SomeBeanFilter")
public class SomeBean {
	
	private String field1;
	private String field2;
	
	// Secured field
	// One way to do this is add annotation to class:
	// @JsonIgnoreProperties(value = "field3");
	// Another way is to annotate the field itself with @JsonIgnore
	// Both of these ways are "static" filtering of fields.
	private String field3;
	
	public SomeBean(String field1, String field2, String field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}
}
