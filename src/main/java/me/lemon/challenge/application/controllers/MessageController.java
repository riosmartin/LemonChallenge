package me.lemon.challenge.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import me.lemon.challenge.application.model.MessageResponse;
import me.lemon.challenge.application.services.RequestFrequencySentinelService;
import me.lemon.challenge.domain.services.FoaasMessage;
import me.lemon.challenge.domain.services.FoaasMessageService;


@RestController
public class MessageController {
	
	@Autowired
	private RequestFrequencySentinelService requestFrequencySentinel;
	
	@Autowired
	private FoaasMessageService foaasMessageService;
	
	@GetMapping("/message")
	public MessageResponse message(@RequestHeader("UserId") String userId) {
		requestFrequencySentinel.LogRequest(userId);
		FoaasMessage message = foaasMessageService.requestBagOfDicksMessage(userId); 
		return new MessageResponse(message.getMessage(), message.getSubtitle());
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleException(Exception ex) {
	  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}
	
}
