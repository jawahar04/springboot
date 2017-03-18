package com.jawahar.springboot.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Greeting {
	@Id
	@GeneratedValue
	private Long id;
	
	//@Column(name = "greeting_text")
	private String greetingText;
	
	public Greeting() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGreetingText() {
		return greetingText;
	}
	public void setGreetingText(String greetingText) {
		this.greetingText = greetingText;
	}
	

}
