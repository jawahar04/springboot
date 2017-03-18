package com.jawahar.springboot.service;

import java.math.BigInteger;
import java.util.Collection;

import com.jawahar.springboot.model.Greeting;


public interface GreetingsService {
	public Collection<Greeting> getAllGreetings();
	public Greeting getGreeting(BigInteger id);
	public Greeting createGreeting(Greeting greeting);
	public Greeting updateGreeting(Greeting greeting);
	public Greeting deleteGreeting(BigInteger id);
}
