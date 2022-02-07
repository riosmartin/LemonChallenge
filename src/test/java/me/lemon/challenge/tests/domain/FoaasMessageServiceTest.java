package me.lemon.challenge.tests.domain;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import me.lemon.challenge.application.Application;
import me.lemon.challenge.domain.services.FoaasMessage;
import me.lemon.challenge.domain.services.FoaasMessageService;
import me.lemon.challenge.domain.services.IFoaasMessageProvider;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FoaasMessageServiceTest {

	@MockBean
	IFoaasMessageProvider mockFoaasMessageProvider;
	
	@Autowired
	private FoaasMessageService foaasMessageService; 
	
	@Test
	public void shouldReturnCorrectMessageOnGetMessage() throws Exception {

		String expectedResultMessage = "test message";
		String subtitle = "test subtitle"; 
		String expectedResultSubtitle = String.format("- %s", subtitle);
		
		FoaasMessage mockFoaasApiMessage = new FoaasMessage(expectedResultMessage, expectedResultSubtitle);
		
		Mockito.when(mockFoaasMessageProvider.requestBagOfDicksMessage(subtitle)).thenReturn(mockFoaasApiMessage);
		
		FoaasMessage serviceResult = foaasMessageService.requestBagOfDicksMessage(subtitle);
		
		assert serviceResult.getMessage().equals(expectedResultMessage);
		assert serviceResult.getSubtitle().equals(expectedResultSubtitle);
	}
	
}
