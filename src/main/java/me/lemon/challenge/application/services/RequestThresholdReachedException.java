package me.lemon.challenge.application.services;

@SuppressWarnings("serial")
public class RequestThresholdReachedException extends RuntimeException {

	public RequestThresholdReachedException() {
        super("Usted realizó mas de 5 solicitudes en un lapso de 10 segundos. Por favor intente mas tarde.");
    }
}
