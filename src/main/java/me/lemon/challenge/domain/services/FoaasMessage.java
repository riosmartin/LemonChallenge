package me.lemon.challenge.domain.services;

import java.io.Serializable;

public class FoaasMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	private String subtitle;
	
	public FoaasMessage() {
		
	}

	public FoaasMessage(String message, String subtitle) {
		this.message = message;
		this.subtitle = subtitle;
	}
	
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
