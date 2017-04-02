package com.jawahar.springboot.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.jawahar.springboot.AbstractControllerTest;
import com.jawahar.springboot.model.Greeting;
import com.jawahar.springboot.service.EmailService;
import com.jawahar.springboot.service.GreetingService;

/*
 * Notes
 * GreetingService tests are done via GreetingServiceTest --> good
 * GreetingControllerTest vs GreetingControllerMocksTest --> Mocks is preferred since the service is already tested above
 * 
 * Mocks/Mockito have this pattern 
 * 	Get - 
 * 		when(service.get(...)).thenReturn(One or List of Greeting object) ==> get is typically findAll() or findOne(id)
 * 		verify(service.get(...), times(1)).findOne()/findAll()
 * 	
 * Create -
 * 		when(service.create(any(Greeting.class))).thenReturn(mockGreetng)
 * 		verify(service, times(1)).create(any(Greeting.class)) --> calls the above mock method
 * 
 * Update -
 * 		when(service.update(any(Greeting.class))).thenReturn(mockGreetng)
 * 		verify(service, times(1)).update(any(Greeting.class)) --> calls the above mock method
 * 
 * Delete -
 * 		no when() --> delete returns void
 * 		verify(service.delete(any(Long.class))), times(1).delete() 
 * 
 */

@Transactional
@SpringBootTest
public class GreetingControllerMocksTest extends AbstractControllerTest {
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private GreetingService greetingService;
	
	@InjectMocks
	private GreetingController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		setUp(controller);
	}
	
	@Test
	public void testGetGreetings() throws Exception {
		Collection<Greeting> list = getEntityListStubData();
		
		when(greetingService.findAll()).thenReturn(list);
		
		String uri = "/api/greetings";
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		System.out.println("status: " + status + " response: " + content);
		
		verify(greetingService, times(1)).findAll();
		
		Assert.assertEquals("failure - expected 200", 200, status);
		Assert.assertTrue("failure expected return string len > 0", content.trim().length() > 0);	
		
	}
	
	@Test
	public void testGetOneGreeting() throws Exception {
		Greeting greeting = getEntityStubData();
		Long id = greeting.getId();
		
		when(greetingService.findOne(id)).thenReturn(greeting);
		
		String uri = "/api/greetings/{id}";
		
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(uri,id).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		System.out.println("status: " + status + " response: " + content);
		
		verify(greetingService, times(1)).findOne(id);
		
		Assert.assertEquals("failure - expected 200", 200, status);
		Assert.assertTrue("failure expected return string len > 0", content.trim().length() > 0);	
		
	}
	
	@Test
	public void testGetOneGreetingNotFound() throws Exception {
		String uri = "/api/greetings/{id}";
		Long id = Long.MAX_VALUE;
		when(greetingService.findOne(id)).thenReturn(null);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(greetingService, times(1)).findOne(id);
		
		System.out.println("status: " + status + " response: " + content);
		
		Assert.assertEquals("failure - expected 404", 404, status);
		Assert.assertTrue("failure expected return string len should be 0", content.trim().length() == 0);	
	}
	
	@Test
	public void testCreateGreeting() throws Exception {
		String uri = "/api/greetings";
		Greeting greeting = getEntityStubData();
		
		when(greetingService.create(any(Greeting.class))).thenReturn(greeting);
		
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
		
		verify(greetingService, times(1)).create(any(Greeting.class));
		
		Assert.assertEquals("failure - expected 201", 201, status);
		Assert.assertTrue("failure expected return string len should be 0", content.trim().length() > 0);	
		Assert.assertEquals("failure - expected greeting id of 3", new Long(1), createdGreeting.getId());
	}
	
	@Test
	public void testUpdateGreeting() throws Exception {
		String uri = "/api/greetings/{id}";
		Greeting greeting = getEntityStubData();
		Long id = greeting.getId();
		
		when(greetingService.update(any(Greeting.class))).thenReturn(greeting);
		
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
		
		verify(greetingService, times(1)).update(any(Greeting.class));
		
		
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
		
		when(greetingService.update(any(Greeting.class)))
			.thenReturn(null);
		
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
		
		verify(greetingService, times(1)).update(any(Greeting.class));
		
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
		
		// Mockito doesn't account for methods like delete which returns void
		// No need for when()
		
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
		
		verify(greetingService, times(1)).delete(any(Long.class));
		
		Assert.assertEquals("failure - expected 200", 200, status);
		Assert.assertTrue("failure expected return string len should be 0", content.trim().length() == 0);	
	}

	private Collection<Greeting> getEntityListStubData() {
		Collection<Greeting> list = new ArrayList<>();
		list.add(getEntityStubData());
		
		return list;
	}

	private Greeting getEntityStubData() {
		Greeting entity = new Greeting();
		entity.setGreetingText("hello");
		entity.setId(1L);
		return entity;
	}

}
