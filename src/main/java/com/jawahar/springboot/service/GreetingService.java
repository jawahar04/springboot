package com.jawahar.springboot.service;

import java.util.Collection;

import com.jawahar.springboot.model.Greeting;


public interface GreetingService {
	public Collection<Greeting> findAll();
	public Greeting findOne(Long id);
	public Greeting create(Greeting greeting);
	public Greeting update(Greeting greeting);
	public void delete(Long id);
	public void evictCache();
}
