package com.jawahar.springboot.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jawahar.springboot.model.Greeting;
import com.jawahar.springboot.service.GreetingsService;

@RestController
public class GreetingController {
	@Autowired
	GreetingsService greetingsService;
	
//	private static BigInteger nextId;
//	private static Map<BigInteger, Greeting> greetingMap;
//	
//	private static Greeting save(Greeting greeting) {
//		if (greetingMap == null) {
//			greetingMap = new HashMap<>();
//			nextId = BigInteger.ONE;
//			
//		}
//		if (greeting.getId() != null) {
//			Greeting g = greetingMap.get(greeting.getId());
//			if (g == null) {
//				return null;
//			}
//			g.setGreetingText(greeting.getGreetingText());
//			greetingMap.put(g.getId(), g);
//			return g;
//		}
//		else {
//			greeting.setId(nextId);
//			nextId = nextId.add(BigInteger.ONE);
//			greetingMap.put(greeting.getId(), greeting);
//			return greeting;
//		}
//	}
//	static {
//		Greeting g1 = new Greeting();
//		g1.setGreetingText("Hello World");
//		save(g1);
//		
//		Greeting g2 = new Greeting();
//		g2.setGreetingText("Hola! World");
//		save(g2);		
//		
//	}
//	
	@RequestMapping(value = "/api/greetings", method = RequestMethod.GET)
	public ResponseEntity<Collection<Greeting>> getGreetings() {
		
		return new ResponseEntity<Collection<Greeting>>(greetingsService.findAll(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/api/greetings/{id}")
	public ResponseEntity<Greeting> getGreeting(@PathVariable Long id) {
		Greeting g = greetingsService.findOne(id);
		if (g == null)
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Greeting>(g, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/api/greetings", method = RequestMethod.POST)
	public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
		System.out.println("createGreeting called: " + greeting.getGreetingText());
		Greeting g = greetingsService.create(greeting);
		
		return new ResponseEntity<Greeting>(g, HttpStatus.CREATED);
		
	}
	
	// Note 1 - Since the request comes in as http://localhost/api/greetings/2, if {id} is not specified in RequestMapping
	// this updateGreeting method isn't called.
	// Note 2 - Now that we have established the need for {id} it should be a PathVariable and has to be first i.e. before
	// RequestBody
	@RequestMapping(value="/api/greetings/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Greeting> updateGreeting(@PathVariable Long id, @RequestBody Greeting greeting) {
		System.out.println("updateGreeting called: " + greeting.getGreetingText());
		greeting.setId(id);
		Greeting g = greetingsService.update(greeting);
		System.out.println("In updateGreeting greeting is: " + g);
		if (g == null) {
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Greeting>(g, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/api/greetings/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Greeting> deleteGreeting(@PathVariable Long id) {
		
		greetingsService.delete(id);
//		if (g == null)
//			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Greeting>(HttpStatus.OK);
		
	}
	
	

}
