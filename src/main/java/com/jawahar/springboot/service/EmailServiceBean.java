package com.jawahar.springboot.service;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jawahar.springboot.model.Greeting;
import com.jawahar.springboot.utils.AsyncResponse;

@Service
public class EmailServiceBean implements EmailService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Boolean send(Greeting greeting) {
		logger.info("> send");
		
		Boolean success = Boolean.FALSE;
		
		long pause = 5000;
		try {
			Thread.sleep(pause);
		}
		catch (InterruptedException ie) {
			
		}
		logger.info("Processing took: " + pause/1000 + " secs");
		success = Boolean.TRUE;
		logger.info("< send");
		return success;
	}

	// spring uses this annotation to  use task manager n a new thread
	// fire and forget, the initiating thread doesn't care about the response
	@Async  
	@Override
	public void sendAsync(Greeting greeting) {
		logger.info("> sendAsync");
		try {
			send(greeting);
		}
		catch (Exception ex) {
			logger.warn("exception caught while sending mail async", ex);
		}
		logger.info("< sendAsync");

	}

	@Async
	@Override
	public Future<Boolean> sendAsyncResult(Greeting greeting) {
		logger.info("> sendAsyncResult");
		AsyncResponse<Boolean> response = new AsyncResponse<>();
		try {
			Boolean success = send(greeting);
			response.complete(success);
			
		}
		catch (Exception e) {
			logger.warn("Exception in sendAsyncResult", e);
			response.completeExceptionally(e);
		}
		logger.info("< sendAsyncResult");
		return response;
	}

}
