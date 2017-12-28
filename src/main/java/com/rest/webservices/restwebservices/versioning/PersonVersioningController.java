package com.rest.webservices.restwebservices.versioning;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

	// URI Path Versioning
	@GetMapping(path = "/v1/person")
	public PersonV1 personV1() {
		PersonV1 personV1 = new PersonV1("Bob Charlie");
		return personV1;
	}

	@GetMapping(path = "/v2/person")
	public PersonV2 personV2() {
		PersonV2 personV2 = new PersonV2(new Name("Bob", "Charlie"));
		return personV2;
	}

	// Query parameter Versioning
	@GetMapping(path = "/person/param", params = "version=1")
	public PersonV1 paramV1() {
		PersonV1 personV1 = new PersonV1("Bob Charlie");
		return personV1;
	}

	@GetMapping(path = "/person/param", params = "version=2")
	public PersonV2 paramV2() {
		PersonV2 personV2 = new PersonV2(new Name("Bob", "Charlie"));
		return personV2;
	}

	// Header Versioning
	@GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
	public PersonV1 headerV1() {
		PersonV1 personV1 = new PersonV1("Bob Charlie");
		return personV1;
	}

	@GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
	public PersonV2 headerV2() {
		PersonV2 personV2 = new PersonV2(new Name("Bob", "Charlie"));
		return personV2;
	}

	// Content Negotiation (a.k.a "Accept Header") Versioning.  This works by adding, for example, "Accept=application/vnd.company.app-v1+json" to the request header
	@GetMapping(path = "/person/produces", produces = "application/vnd.company.app-v1+json")
	public PersonV1 producesV1() {
		PersonV1 personV1 = new PersonV1("Bob Charlie");
		return personV1;
	}

	@GetMapping(path = "/person/produces", produces = "application/vnd.company.app-v2+json")
	public PersonV2 producesV2() {
		PersonV2 personV2 = new PersonV2(new Name("Bob", "Charlie"));
		return personV2;
	}
}
