package me.lemon.challenge.infrastructure.foaas;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import me.lemon.challenge.domain.services.FoaasMessage;
import me.lemon.challenge.domain.services.IFoaasMessageProvider;

@Service
public class FoaasApi implements IFoaasMessageProvider {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	public FoaasMessage requestBagOfDicksMessage(String from) {
		String url = String.format("https://www.foaas.com/bag/%s", from);
		ResponseEntity<FoaasApiMessage> response = restTemplate.getForEntity(url, FoaasApiMessage.class);
		FoaasApiMessage apiMessage = response.getBody();
		return new FoaasMessage(apiMessage.getMessage(), apiMessage.getSubtitle());
	}
}
