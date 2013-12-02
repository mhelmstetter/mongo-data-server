package com.mongodb.opendata.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.opendata.domain.Response;
import com.mongodb.opendata.service.QueryService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ApiController {

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	
	@Autowired
	private QueryService queryService;
	
	@RequestMapping(value = "/api/{dataSet}/{collection}", 
			produces={"application/json", "application/xml"},
			method = RequestMethod.GET)
	public @ResponseBody Response xml(@PathVariable String dataSet,
			@PathVariable String collection, Writer output, HttpServletRequest request) throws IOException {
		
		PrintWriter writer = new PrintWriter(output);
		String uri = request.getRequestURI();
		if (uri.endsWith("json")) {
			queryService.queryJson(dataSet, collection, writer);
		} else if (uri.endsWith("xml")) {
			
		} else if (uri.endsWith("csv")) {
			
		}
		return null;
	}

}
