package com.jawahar.springboot.service;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jawahar.springboot.AbstractTest;
import com.jawahar.springboot.model.Greeting;

@Transactional
public class GreetingServiceTest extends AbstractTest {
	
	@Autowired
	private GreetingService greetingService;
	
	@Before
	public void setup() {
		greetingService.evictCache();
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void findAll() {
		Collection<Greeting> list = greetingService.findAll();
		
		Assert.assertNotNull("failure, collection not expected to be null", list);
		Assert.assertEquals("failure expected size", 2, list.size());
		
	}
	
	@Test
	public void findOne() {
		Long id = new Long(1);
		Greeting greeting = greetingService.findOne(id);
		
		Assert.assertNotNull("failure, findOne greeting not expected to be null", greeting);
		Assert.assertEquals("failure expected Greeting id of 1", id, greeting.getId());
		
	}
	
	@Test
	public void findOneNotFound() {
		Long id = new Long(Long.MAX_VALUE);
		Greeting greeting = greetingService.findOne(id);
		
		Assert.assertNull("failure, findOneNotFound greeting expected to be null", greeting);		
	}
	
	@Test
	public void testCreateGreeting() {
		Long id = 3L;
		String greetingText = "Vanakkam";
		
		Greeting greeting = new Greeting();
		greeting.setGreetingText(greetingText);
		
		Greeting g = greetingService.create(greeting);
		
		Assert.assertNotNull("failure, greeting not expected to be null", g);
		Assert.assertEquals("failure expected Greeting id of 3", id, g.getId());	
		Assert.assertEquals("failure expected Greeting text of: " + greetingText, greetingText, g.getGreetingText());
	}
	@Test
	public void testCreateGreetingFailure() {
		Long id = 3L;
		String greetingText = "Vanakkam";
				
		Greeting greeting = new Greeting();
		greeting.setGreetingText(greetingText);
		greeting.setId(id); //ids are assigned by the service
		
		Greeting g = greetingService.create(greeting);
		
		Assert.assertNull("failure, greeting expected to be null", g);
		
	}
	
	@Test
	public void testUpdateGreeting() {
		Long id = 2L;
		String greetingText = " UPDATED";
			
		Greeting origGreeting = greetingService.findOne(id);
		String updatedText = origGreeting.getGreetingText() + greetingText;
		
		Greeting greeting = new Greeting();
		greeting.setGreetingText(updatedText);
		greeting.setId(id); 
		
		Greeting g = greetingService.update(greeting);
		
		System.out.println("testUpdateGreeting: " + g.getId() + " " + g.getGreetingText());
		
		Assert.assertNotNull("failure, greeting not expected to be null", g);
		Assert.assertEquals("failure expected Greeting id of 2", id, g.getId());	
		Assert.assertEquals("failure expected Greeting text of: " + updatedText, 
				updatedText, g.getGreetingText());
	
		
	}
	
	@Test
	public void testUpdateGreetingFailure1() {
		Long id = 2L;
		String greetingText = " UPDATED";
			
		
		Greeting greeting = new Greeting();
		greeting.setGreetingText(greetingText);
		// don't set the id
		// greeting.setId(id); 
		
		Greeting g = greetingService.update(greeting);
		
		Assert.assertNull("failure, greeting expected to be null", g);
	
		
	}
}
