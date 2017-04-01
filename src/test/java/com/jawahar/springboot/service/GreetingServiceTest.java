package com.jawahar.springboot.service;

import static org.junit.Assert.assertEquals;
//import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

//import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jawahar.springboot.AbstractTest;
import com.jawahar.springboot.model.Greeting;

import ch.qos.logback.core.net.SyslogOutputStream;

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
		Long id = Long.MAX_VALUE;
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
	@Test
	public void testUpdateGreetingFailure2() {
		Long id = Long.MAX_VALUE;
		String greetingText = " UPDATED";
			
		
		Greeting greeting = new Greeting();
		greeting.setGreetingText(greetingText);
		greeting.setId(id); 
		
		Greeting g = greetingService.update(greeting);
		
		Assert.assertNull("failure, greeting expected to be null", g);
	}
	@Test
	public void testDelete() {
		Long id = 2L;
		greetingService.delete(id);
				
		Greeting g = greetingService.findOne(id);
		
		Assert.assertNull("failure, greeting deleted expected to be null", g);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testDeleteFailure() {
		thrown.expect(org.springframework.dao.EmptyResultDataAccessException.class);
		Long id = Long.MAX_VALUE;
		greetingService.delete(id);
				
	}
	
	// java 8 completeable future --> move to a different test class
	/*
	 * Computations can be a Future, Consumer or Runnable --> apply, accept or run
	 * Computations can run in the current thread, as someAsync or someAsync with an Executor passed in
	 *
	 * Creating/completing tasks
	 * Error handling and exceptions
	 * canceling and forcing completion
	 */
	
	@Test
	public void testCompletedFuture() throws Exception {
		System.out.println(" --- testCompletedFuture ");
		String expectedVal = "Expected Value";
		CompletableFuture<String> compFutureString = CompletableFuture.completedFuture(expectedVal);
		//assertThat(expectedVal, compFutureString.get());
		
		assertEquals(expectedVal, compFutureString.get());
	
	}
	private static ExecutorService executor = Executors.newCachedThreadPool();
	
	@Test
	public void testRunAsync() {
		System.out.println(Thread.currentThread().getName() + " calling testRunAsync");
		CompletableFuture<Void> compFutureVoid = 
				CompletableFuture.runAsync(() -> System.out.println(
						Thread.currentThread().getName() + " In testRunAsync"), executor);
		pauseSeconds(1);
		assertEquals(compFutureVoid.isDone(), true);
	}
	
	private void pauseSeconds (int numSecs) {
		try {
			Thread.sleep(numSecs * 1000);
		}
		catch (InterruptedException e) {
			System.out.println("Exception in pauseSeconds " + e);
		}
	}
	
	
	
}
