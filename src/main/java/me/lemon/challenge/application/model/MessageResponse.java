package me.lemon.challenge.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MessageResponse {
	
	@Getter @Setter private String message;
	@Getter @Setter private String subtitle;
	
	public MessageResponse(String message, String subtitle) {
		this.message = message;
		this.subtitle = subtitle;
	}
}
