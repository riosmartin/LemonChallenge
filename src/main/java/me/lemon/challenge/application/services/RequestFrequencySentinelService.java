package me.lemon.challenge.application.services;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class RequestFrequencySentinelService {
	
	private Map<String,Queue<GregorianCalendar>> userLastRequests = new HashMap<String, Queue<GregorianCalendar>>(); 

	public void LogRequest(String userId) {
		GregorianCalendar currentDate = new GregorianCalendar();

		if (!userLastRequests.containsKey(userId)) {
			userLastRequests.put(userId, new LinkedList<GregorianCalendar>());
			userLastRequests.get(userId).add(currentDate);
			return;
		}
		
		if (userLastRequests.get(userId).size() < 5) {
			userLastRequests.get(userId).add(currentDate);
			return;
		}
		
		GregorianCalendar targetDate = (GregorianCalendar) userLastRequests.get(userId).peek().clone();
		targetDate.add(GregorianCalendar.SECOND, 10);
		
		if (currentDate.before(targetDate)) {
			throw new RequestThresholdReachedException();
		}
		
		userLastRequests.get(userId).remove();
		userLastRequests.get(userId).add(currentDate);
	}
}
