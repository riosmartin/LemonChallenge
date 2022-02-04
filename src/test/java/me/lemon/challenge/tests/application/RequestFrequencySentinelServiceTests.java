package me.lemon.challenge.tests.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import me.lemon.challenge.application.services.RequestFrequencySentinelService;
import me.lemon.challenge.application.services.RequestThresholdReachedException;

@ContextConfiguration(classes=RequestFrequencySentinelService.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class RequestFrequencySentinelServiceTests {
	
	@Autowired
	private RequestFrequencySentinelService requestFrequencySentinelService;
	
	@BeforeEach
	public void initializeServices() {
		requestFrequencySentinelService = new RequestFrequencySentinelService();
	}
	
	@Test
	public void serviceShouldLogRequestSuccessfully() {
		requestFrequencySentinelService.LogRequest("Test");
	}

	@Test
	public void serviceShouldLogFirstFiveRequestsSuccessfully() {
		for (int i = 0; i < 5; i++) {
			requestFrequencySentinelService.LogRequest("Test");
		}
	}
	
	@Test
	public void serviceShouldFailLoggingTooManyRequests() {
		
		for (int i = 0; i < 5; i++) {
			requestFrequencySentinelService.LogRequest("Test");	
		}
		
		Exception exception = assertThrows(RequestThresholdReachedException.class, () -> {
			requestFrequencySentinelService.LogRequest("Test");
	    });
		
		assertTrue(exception.getMessage() == "Usted realizó mas de 5 solicitudes en un lapso de 10 segundos. Por favor intente mas tarde.");		
	}
	
	
	
	
	
}
