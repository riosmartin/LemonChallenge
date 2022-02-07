package me.lemon.challenge.domain.services;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class FoaasMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter private String message;
	@Getter @Setter private String subtitle;

	public FoaasMessage(String message, String subtitle) {
		this.message = message;
		this.subtitle = subtitle;
	}
}
