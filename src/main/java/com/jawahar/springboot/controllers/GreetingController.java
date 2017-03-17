package com.jawahar.springboot.controllers;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jawahar.springboot.model.Greeting;

@RestController
public class GreetingController {
	private static BigInteger nextId;
	private static Map<BigInteger, Greeting> greetingMap;
	
	private static Greeting save(Greeting greeting) {
		if (greetingMap == null) {
			greetingMap = new HashMap<>();
			nextId = BigInteger.ONE;
			
		}
		greeting.setId(nextId);
		nextId = nextId.add(BigInteger.ONE);
		greetingMap.put(greeting.getId(), greeting);
		return greeting;
	}
	static {
		Greeting g1 = new Greeting();
		g1.setGreetingText("Hello World");
		save(g1);
		
		Greeting g2 = new Greeting();
		g2.setGreetingText("Hola! World");
		save(g2);		
		
	}
	
	@RequestMapping(value = "/greetings", method = RequestMethod.GET)
	public ResponseEntity<Collection<Greeting>> getGreetings() {
		
		return new ResponseEntity<Collection<Greeting>>(greetingMap.values(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/greetings/{id}")
	public ResponseEntity<Greeting> getGreeting(@PathVariable BigInteger id) {
		Greeting g = greetingMap.get(id);
		if (g == null)
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Greeting>(g, HttpStatus.OK);
		
	}

}
