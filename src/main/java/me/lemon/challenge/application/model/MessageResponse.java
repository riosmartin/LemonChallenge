package me.lemon.challenge.application.model;

public class MessageResponse {
	
	private String message;
	private String subtitle;
	
	public MessageResponse(String message, String subtitle) {
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
