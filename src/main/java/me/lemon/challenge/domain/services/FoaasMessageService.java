package me.lemon.challenge.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoaasMessageService {
	
	@Autowired
	private IFoaasMessageProvider foaasMessageProvider;
	
	public FoaasMessage requestBagOfDicksMessage(String subtitle) {
		return foaasMessageProvider.requestBagOfDicksMessage(subtitle);
	}
}
