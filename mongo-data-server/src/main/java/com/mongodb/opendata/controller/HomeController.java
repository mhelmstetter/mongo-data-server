package com.mongodb.opendata.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.dspl.model.Dataset;
import com.mongodb.opendata.domain.Collection;
import com.mongodb.opendata.domain.DatasetMapping;
import com.mongodb.opendata.repository.DatasetMappingRepository;
import com.mongodb.opendata.repository.DatasetRepository;
import com.mongodb.opendata.service.DataService;
import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private RepresentationFactory representationFactory = new DefaultRepresentationFactory();
	
	@Autowired
	DatasetMappingRepository datasetMappingRepository;
	
	@Autowired
    DatasetRepository datasetRepository;
	
	@Autowired DataService dataService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String datasetListPage(Locale locale, Model model) {
		
	    Dataset d = datasetRepository.findOne("1");
        System.out.println(d);
		
		model.addAttribute("datasets", datasetRepository.findAll() );
		
		return "datasets";
	}
	
	@RequestMapping(value = "/data/{dataSet}", method = RequestMethod.GET)
	public String dataset(@PathVariable String dataSet, Model model) {
		model.addAttribute("dataset", datasetMappingRepository.findOne(dataSet) );
		return "dataset";
	}
	
	@RequestMapping(value = "/data/{dataSet}/{collectionName}", method = RequestMethod.GET)
	public String collection(@PathVariable String dataSet, @PathVariable String collectionName, Model model) {
		Collection collection = dataService.findCollection(dataSet, collectionName);
		model.addAttribute("collection",  collection);
		return "collection";
	}
	
	// produces={"application/hal+json", "application/hal+xml"
	 @ResponseBody
	    @RequestMapping(value="/blah", 
	        method=RequestMethod.GET, 
	        produces={"application/json", "application/xml"
	    })
	    public ReadableRepresentation representation() {
	        Representation person = representationFactory.newRepresentation("/person")
	            .withProperty("name", "Mike")
	            .withProperty("age", "36") ;
	        return person;
	    }
	
	
	@RequestMapping(value = "/loadHmda", method = RequestMethod.GET)
	public String loadHmda(Locale locale, Model model) {
		
		
		DatasetMapping hmda = new DatasetMapping();
		hmda.setName("hmda");
		hmda.setDisplayName("2009-2011 HMDA LAR");
		hmda.setDescription("Home Mortgage Disclosure Act Data for the years 2007-2011.");
		
		Collection hmdaLar = new Collection();
		hmdaLar.setName("hmda_lar");
		hmdaLar.setDisplayName("HMDA LAR with code sheets");
		hmdaLar.setDescription("The HMDA LAR data, with code sheet lookups added.");
		
		
		hmdaLar.addField("action_taken");
		hmdaLar.addField("action_taken_name");
		hmdaLar.addField("agency_abbr");
		hmdaLar.addField("agency_code");
		
		hmda.addCollection(hmdaLar);
		
		datasetMappingRepository.save(hmda);
		
		return "home";
	}
	
	@RequestMapping(value = "/loadCensus", method = RequestMethod.GET)
	public String loadCensus(Locale locale, Model model) {
		
		
		DatasetMapping census = new DatasetMapping();
		census.setName("census");
		census.setDisplayName("2011 State Populations");
		census.setDescription("State population by age, sex, race, and Hispanic origin.");
		
		Collection populationRaw = new Collection();
		populationRaw.setName("population_raw");
		populationRaw.setDisplayName("Population");
		populationRaw.setDescription("Raw data for 2010 Census population counts by state.");
		
		populationRaw.addField("region");
		populationRaw.addField("division");
		populationRaw.addField("state");
		populationRaw.addField("sex");
		populationRaw.addField("origin");
		populationRaw.addField("race");
		populationRaw.addField("age");
		
		census.addCollection(populationRaw);
		
		datasetMappingRepository.save(census);
		
		return "home";
	}
	
}
