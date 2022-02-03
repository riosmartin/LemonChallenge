package me.lemon.challenge.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.lemon.challenge.application.controllers.MessageController;

@SpringBootTest
class InitializationTests {

	@Autowired
	private MessageController controller;
	
	@Test
	public void contextLoads() {
		assert controller != null;
	}

}
