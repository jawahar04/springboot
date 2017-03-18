package com.jawahar.springboot.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jawahar.springboot.model.Greeting;

@Service
public class GreetingServiceBean implements GreetingsService {
	
	private static BigInteger nextId;
	private static Map<BigInteger, Greeting> greetingMap;
	
	private static Greeting save(Greeting greeting) {
		if (greetingMap == null) {
			greetingMap = new HashMap<>();
			nextId = BigInteger.ONE;
			
		}
		if (greeting.getId() != null) {
			Greeting g = greetingMap.get(greeting.getId());
			if (g == null) {
				return null;
			}
			g.setGreetingText(greeting.getGreetingText());
			greetingMap.put(g.getId(), g);
			return g;
		}
		else {
			greeting.setId(nextId);
			nextId = nextId.add(BigInteger.ONE);
			greetingMap.put(greeting.getId(), greeting);
			return greeting;
		}
	}
	static {
		Greeting g1 = new Greeting();
		g1.setGreetingText("Hello World");
		save(g1);
		
		Greeting g2 = new Greeting();
		g2.setGreetingText("Hola! World");
		save(g2);		
		
	}

	@Override
	public Collection<Greeting> getAllGreetings() {
		return greetingMap.values();
	}

	@Override
	public Greeting getGreeting(BigInteger id) {
		return greetingMap.get(id);
	}

	@Override
	public Greeting createGreeting(Greeting greeting) {
		save(greeting);
		return greeting;
	}

	@Override
	public Greeting updateGreeting(Greeting greeting) {
		save(greeting);
		return greeting;
	}

	@Override
	public Greeting deleteGreeting(BigInteger id) {
		Greeting greeting = greetingMap.get(id);
		boolean b =  greetingMap.remove(id);
		return greeting;
	}

}
