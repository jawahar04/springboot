package com.jawahar.springboot.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jawahar.springboot.service.GreetingService;

@Component
public class GreetingBatchBean {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GreetingService greetingService;
	
	@Scheduled(cron = "0,30 * * * * *")
	public void cronJob() {
		logger.info("> cronJob");
		
		int size = greetingService.findAll().size();
		logger.info("There are: " + size + " greetings.");
		
		logger.info("< cronJob");
	}
	
	//@Scheduled(initialDelay = 5000, fixedRate = 15000)
	public void fixedRateJobWithInitialDelay() {
		logger.info("> fixedRateJobWithInitialDelay");
		long pause = 5000;
		long start = System.currentTimeMillis();
		
		do {
			if ((start + pause) < System.currentTimeMillis())
				break;
		}while (true);
		logger.info("processing time took: " + pause/1000 + " secs");
		logger.info("< fixedRateJobWithInitialDelay");
		
	}
	
	// at most one job runs, because the next job is run 15 seconds after the first job is run
	//@Scheduled(initialDelay = 5000, fixedDelay = 15000)
	public void fixedDelayJobWithInitialDelay() {
		logger.info("> fixedDelayJobWithInitialDelay");
		long pause = 5000;
		long start = System.currentTimeMillis();
		
		do {
			if ((start + pause) < System.currentTimeMillis())
				break;
		}while (true);
		logger.info("processing time took: " + pause/1000 + " secs");
		logger.info("< fixedDelayJobWithInitialDelay");
		
	}
}
