package com.rest.webservices.restwebservices.filtering;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import io.swagger.annotations.ApiOperation;

@RestController
public class FilteringController {
	
	@GetMapping(path = "/filtering")
	@ApiOperation(value = "Get SomeBean", response = SomeBean.class)
	public MappingJacksonValue retrieveSomeBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		
		// Dynamic filtering of resource fields happens here in the @RestController
		// This way configures a field filter for this method only 
		
		MappingJacksonValue mappingJacksonValue = this.getFilters(someBean, "field1", "field2");
		return mappingJacksonValue;
		
//		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
//		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
//		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
//		mappingJacksonValue.setFilters(filters);
//		return mappingJacksonValue;
	}
	
	@GetMapping(path = "/filtering-list")
	@ApiOperation(value = "Get list of all SomeBean's", response = SomeBean.class)
	public MappingJacksonValue retrieveListOfSomeBeans() {
		ArrayList<SomeBean> someBeans = new ArrayList<SomeBean>();
		someBeans.add(new SomeBean("value11", "value12", "value13"));
		someBeans.add(new SomeBean("value21", "value22", "value23"));
		someBeans.add(new SomeBean("value31", "value32", "value33"));
		MappingJacksonValue mappingJacksonValue = this.getFilters(someBeans, "field1", "field2");
		return mappingJacksonValue;
	}
	
	private MappingJacksonValue getFilters(Object obj, String ...args) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(obj);
		HashSet<String> set = new HashSet<String>();
		for (int i = 0; i < args.length; i++) {
			set.add(args[i]);
		}
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(set.stream().collect(Collectors.toSet()));
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;	
	}
}
