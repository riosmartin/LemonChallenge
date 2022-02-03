package me.lemon.challenge.infrastructure.foaas;

import java.io.Serializable;

public class FoaasApiMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	private String subtitle;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
}
