package me.lemon.challenge.tests.application;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import me.lemon.challenge.application.Application;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MessageControllerTests {

	private static final String MESSAGE_ACTION_PATH = "/message";
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void endpointShouldReturnFailDueToMissingUserId() throws Exception {
		String expectedMessage = 
				"Required request header 'UserId' for "
			  + "method parameter type String is not present";
		ResultActions result = mockMvc.perform(get(MESSAGE_ACTION_PATH));
		result.andExpect(status().is(500))
		      .andExpect(content().string(expectedMessage));
	}
	
	/**
	 * Use Case : Se consume una vez la API con un userId determinado 
	 *            y devuelve el mensaje del servicio
	 */
	@Test
	public void endpointShouldSucceedReturningMessage() throws Exception {
		String userId = "TestUserId";
		String expectedMessage = "Eat a bag of fucking dicks.";
		String expectedSubtitle = String.format("- %s", userId);
		
		ResultActions result = 
				mockMvc.perform(get(MESSAGE_ACTION_PATH).header("UserId", userId));
												
		result.andExpect(status().is(200))
		      .andExpect(content()
		    		  	    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		      .andExpect(jsonPath("$.message", is(expectedMessage))) 
		      .andExpect(jsonPath("$.subtitle", is(expectedSubtitle))); 
	}
	
	/**
	 * Use Case : Se consume la API 5 veces dentro de un periodo de 10 segundos 
	 *            y esta devuelve los 5 mensajes del servicio
	 */
	@Test
	public void endpointShouldSucceedOnFirstFiveRequests() throws Exception {
		String userId = "TestUserId";
		String expectedMessage = "Eat a bag of fucking dicks.";
		String expectedSubtitle = String.format("- %s", userId);
		
		for (int i = 0; i < 5; i++) {
			ResultActions result = mockMvc.perform(get(MESSAGE_ACTION_PATH).header("UserId", userId));
			
			result.andExpect(status().is(200))
		      .andExpect(content()
		    		  	    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		      .andExpect(jsonPath("$.message", is(expectedMessage))) 
		      .andExpect(jsonPath("$.subtitle", is(expectedSubtitle)));
		}
		
	}
	
	/**
	 * Use Case : Se consume la API 6 veces dentro de un periodo de 10 segundos 
	 * y el sexto llamado devuelve un error.
	 */
	@Test
	public void endpointShouldFailDueToTooManyRequests() throws Exception {
		String userId = "TestUserId";
		String expectedMessage = "Usted realizó mas de 5 solicitudes en un lapso de 10 segundos. Por favor intente mas tarde.";

		mockMvc.perform(get(MESSAGE_ACTION_PATH).header("UserId", userId));
		long intervalUpperBound = new GregorianCalendar().getTimeInMillis() + 10000;
		
		for (int i = 0; i < 4; i++) {
			mockMvc.perform(get(MESSAGE_ACTION_PATH).header("UserId", userId));
		}
		
		assert(new GregorianCalendar().getTimeInMillis() < intervalUpperBound);
		
		mockMvc.perform(get(MESSAGE_ACTION_PATH).header("UserId", userId))
			   .andExpect(status().is(500))
			   .andExpect(content().string(expectedMessage));;
	}
	
	/**
	 * Use Case : Se consume la API 6 veces dentro de un periodo de 10 segundos, 
	 * 			  se hace un septimo llamado 10 segundos despues del primer llamado 
	 * 			  y este devuelve un mensaje del servicio
	 */
	@Test
	public void endpointShouldSucceedAfterMessageCooldownExpired() throws Exception {
		String userId = "TestUserId";
		String expectedMessage = "Eat a bag of fucking dicks.";
		String expectedSubtitle = String.format("- %s", userId);
		
		mockMvc.perform(get(MESSAGE_ACTION_PATH).header("UserId", userId));
		
		long firstRequestMillis = new GregorianCalendar().getTimeInMillis();
		long seventhRequestTargetMillis = firstRequestMillis + 10000;
		
		for (int i = 0; i < 5; i++) {
			mockMvc.perform(get(MESSAGE_ACTION_PATH).header("UserId", userId));
		}
		
		long millisToWait = seventhRequestTargetMillis - new GregorianCalendar().getTimeInMillis();
		if (millisToWait > 0) {
			TimeUnit.MILLISECONDS.sleep(millisToWait);	
		}
		
		ResultActions result = 
				mockMvc.perform(get(MESSAGE_ACTION_PATH).header("UserId", userId));
								
		result.andExpect(status().is(200))
	      .andExpect(content()
	    		  	    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$.message", is(expectedMessage))) 
	      .andExpect(jsonPath("$.subtitle", is(expectedSubtitle)));
	}
}
