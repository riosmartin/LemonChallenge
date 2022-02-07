package me.lemon.challenge.infrastructure.foaas;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class FoaasApiMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter private String message;
	@Getter @Setter private String subtitle;
}
