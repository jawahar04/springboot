package com.jawahar.springboot;

import org.slf4j.Logger;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.boot.test.j

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class AbstractTest {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
