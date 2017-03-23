package com.jawahar.springboot.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.hibernate.type.TrueFalseType;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jawahar.springboot.model.Greeting;
import com.jawahar.springboot.repository.GreetingRepository;

@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
public class GreetingServiceBean implements GreetingService {
	
//	private static Long nextId;
//	private static Map<Long, Greeting> greetingMap;
//	
//	private static Greeting save(Greeting greeting) {
//		if (greetingMap == null) {
//			greetingMap = new HashMap<>();
//			nextId = 1L;
//			
//		}
//		if (greeting.getId() != null) {
//			
//			Greeting g = greetingMap.get(greeting.getId());
//			if (g == null) {
//				System.out.println("In update greeting is null: ");
//				return null;
//			}
//			g.setGreetingText(greeting.getGreetingText());
//			greetingMap.put(g.getId(), g);
//			System.out.println("In update returning: " + g.getId() + " : " + g.getGreetingText());
//			return g;
//		}
//		else {
//			greeting.setId(nextId);
//			nextId = nextId + 1;
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

	@Autowired
	private GreetingRepository greetingRepository;
	
	
	@Override
	public Collection<Greeting> findAll() {
		return greetingRepository.findAll();
	}

	@Override
	@Cacheable(value = "greetings", key = "#id")
	public Greeting findOne(Long id) {
		return greetingRepository.findOne(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly = false)
	@CachePut(value = "greetings", key = "#result.id", condition = "#result != null")
	public Greeting create(Greeting greeting) {
		if (greeting.getId() != null) {
			System.out.println("Create has to be called with null id");
			return null;
		}
		
		Greeting g = greetingRepository.save(greeting);
		if (g.getId() == 4L) {
			throw new RuntimeException("Testing rollback");
		}
		return g;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly = false)
	@CachePut(value = "greetings", key = "#greeting.id", condition = "#greeting.id != null")
	public Greeting update(Greeting greeting) {
		if (greeting.getId() == null) {
			System.out.println("Update has to be called with non-null id");
			return null;
		}
		
		Greeting greetingPersisted = greetingRepository.findOne(greeting.getId());
		if (greetingPersisted == null) {
			System.out.println("Update called with a non-existent greeting: " + greeting.getId());
			return null;
		}
		
		return greetingRepository.save(greeting);
	}

	@Override
	@CacheEvict(value = "greetings", key = "#id")
	public void delete(Long id) {
		greetingRepository.delete(id);
	}
	
	@Override
	@CacheEvict(value = "greetings", allEntries = true)
	public void evictCache() {
	}

}
