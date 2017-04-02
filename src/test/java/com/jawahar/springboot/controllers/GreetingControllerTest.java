package com.jawahar.springboot.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.jawahar.springboot.AbstractControllerTest;
import com.jawahar.springboot.model.Greeting;
import com.jawahar.springboot.service.GreetingService;

@Transactional
@SpringBootTest
public class GreetingControllerTest extends AbstractControllerTest {
	
	@Autowired
	private GreetingService greetingService;
	
	@Before
	public void setUp() {
		super.setUp();
		greetingService.evictCache();
	}
	
	@Test
	public void testGetGreetings() throws Exception {
		String uri = "/api/greetings";
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		System.out.println("status: " + status + " response: " + content);
		
		Assert.assertEquals("failure - expected 200", 200, status);
		Assert.assertTrue("failure expected return string len > 0", content.trim().length() > 0);	
	}
	
	@Test
	public void testGetOneGreeting() throws Exception {
		String uri = "/api/greetings/{id}";
		Long id = 1L;
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		System.out.println("status: " + status + " response: " + content);
		
		Assert.assertEquals("failure - expected 200", 200, status);
		Assert.assertTrue("failure expected return string len > 0", content.trim().length() > 0);	
	}
	
	@Test
	public void testGetOneGreetingNotFound() throws Exception {
		String uri = "/api/greetings/{id}";
		Long id = Long.MAX_VALUE;
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		System.out.println("status: " + status + " response: " + content);
		
		Assert.assertEquals("failure - expected 404", 404, status);
		Assert.assertTrue("failure expected return string len should be 0", content.trim().length() == 0);	
	}
	
	
	@Test
	public void testCreateGreeting() throws Exception {
		String uri = "/api/greetings";
		Greeting greeting = new Greeting();
		greeting.setGreetingText("Vanakkam");
		
		String jsonStr = mapToJson(greeting);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
							.post(uri)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(jsonStr))
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Greeting createdGreeting = super.mapFromJson(content, Greeting.class);
							
		System.out.println("status: " + status + " response: " + content);
		
		Assert.assertEquals("failure - expected 201", 201, status);
		Assert.assertTrue("failure expected return string len should be 0", content.trim().length() > 0);	
		Assert.assertEquals("failure - expected greeting id of 3", new Long(3), createdGreeting.getId());
	}
	
	@Test
	public void testUpdateGreeting() throws Exception {
		String uri = "/api/greetings/{id}";
		Long id = 2L;
		Greeting greeting = new Greeting();
		greeting.setGreetingText("Vanakkam");
		greeting.setId(id);
		
		String jsonStr = mapToJson(greeting);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
							.put(uri, id)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(jsonStr))
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Greeting updatedGreeting = super.mapFromJson(content, Greeting.class);
							
		System.out.println("status: " + status + " response: " + content);
		
		Assert.assertEquals("failure - expected 200", 200, status);
		Assert.assertTrue("failure expected return string len should be 0", content.trim().length() > 0);
		Assert.assertNotNull("failure Greeting updated and not returned", updatedGreeting);
	}
	
	@Test
	public void testUpdateGreetingNotFound() throws Exception {
		String uri = "/api/greetings/{id}";
		Long id = Long.MAX_VALUE;
		Greeting greeting = new Greeting();
		greeting.setGreetingText("Vanakkam");
		greeting.setId(id);
		
		String jsonStr = mapToJson(greeting);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
							.put(uri, id)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(jsonStr))
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		
		System.out.println("status: " + status + " response: " + content);
		
		Assert.assertEquals("failure - expected 200", 500, status);
		Assert.assertTrue("failure expected return string len should be 0", content.trim().length() == 0);	
	}
	
	@Test
	public void testDeleteGreeting() throws Exception {
		String uri = "/api/greetings/{id}";
		Long id = 2L;
		Greeting greeting = new Greeting();
		greeting.setGreetingText("Vanakkam");
		greeting.setId(id);
		
		String jsonStr = mapToJson(greeting);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
							.delete(uri, id)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							)
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		
		System.out.println("status: " + status + " response: " + content);
		
		Assert.assertEquals("failure - expected 200", 200, status);
		Assert.assertTrue("failure expected return string len should be 0", content.trim().length() == 0);	
	}
	
	
	
	
	
	

}
