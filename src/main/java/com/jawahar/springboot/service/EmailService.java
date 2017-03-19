package com.jawahar.springboot.service;

import java.util.concurrent.Future;

import com.jawahar.springboot.model.Greeting;

public interface EmailService {
	Boolean send(Greeting greeting);
	void sendAsync(Greeting greeting);
	
	Future<Boolean> sendAsyncResult(Greeting greeting);

}
