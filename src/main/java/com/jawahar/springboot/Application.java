package com.jawahar.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class Application {
    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }
    
    public CacheManager cacheManager() {
    	//return new ConcurrentMapCacheManager("greetings");
    	return new GuavaCacheManager("greetings");
    	
    }
}
