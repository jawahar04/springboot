package com.jawahar.springboot.model;

import java.math.BigInteger;

public class Greeting {
	private BigInteger id;
	private String greetingText;
	
	public Greeting() {
		
	}
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getGreetingText() {
		return greetingText;
	}
	public void setGreetingText(String greetingText) {
		this.greetingText = greetingText;
	}
	

}
