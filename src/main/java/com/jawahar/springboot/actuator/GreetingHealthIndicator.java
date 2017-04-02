package com.jawahar.springboot.actuator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.jawahar.springboot.model.Greeting;
import com.jawahar.springboot.service.GreetingService;

@Component
public class GreetingHealthIndicator implements HealthIndicator {

	@Autowired
	private GreetingService greetingService;
	
	@Override
	public Health health() {
		Collection<Greeting> collGreeting = greetingService.findAll();
		if (collGreeting == null || collGreeting.size() == 0)
			return Health.down().withDetail("count", 0).build();
		
		return Health.up().withDetail("count", collGreeting.size()).build();
	}

}
