package com.pratiti.project.queuemanager;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.pratiti.project.entity.Token;
import com.pratiti.project.entity.Token.Status;

public class TokenQueueManager {
	private static TokenQueueManager instance = null;
    private Map<Integer, Deque<Token>> counterQueues = new HashMap<>();
    private Map<Integer, Deque<Token>> pendingQueues = new HashMap<>();

    private TokenQueueManager() {
        // Private constructor to prevent instantiation from outside
    }

    public static synchronized TokenQueueManager getInstance() {
        if (instance == null) {
            instance = new TokenQueueManager();
        }
        return instance;
    }

    public synchronized void enqueue(Token token, int counter,String... status) {
    	 Deque<Token> counterQueue;
    	if(status.length>0 && status[0].equals("noshow")) {
    		counterQueue=pendingQueues.get(counter);
    		if (counterQueue == null) {
                counterQueue = new LinkedList<>();
                pendingQueues.put(counter, counterQueue);
            }
    	}
    	else {
    		counterQueue= counterQueues.get(counter);
    		if (counterQueue == null) {
                counterQueue = new LinkedList<>();
                counterQueues.put(counter, counterQueue);
            }
    	}
        counterQueue.add(token);
    }

    public synchronized Token dequeue(int counter,String... status) {
    	Deque<Token> counterQueue;
    	if(status.length>0 && status[0].equals("pendingqueue")) {
    		counterQueue=pendingQueues.get(counter);
    		if (counterQueue == null || counterQueue.isEmpty()) {
                return null;
            }
    	}
    	else {
    		counterQueue=counterQueues.get(counter);
            if (counterQueue == null || counterQueue.isEmpty()) {
                return null;
            }
    	}
        return counterQueue.poll();
    }
    public synchronized Token top(int counter) {
    	 Queue<Token> counterQueue = counterQueues.get(counter);
         if (counterQueue == null || counterQueue.isEmpty()) {
             return null;
         }
         return counterQueue.peek();
    }
    
 
    public synchronized boolean isEmpty(int counter) {
        Queue<Token> counterQueue = counterQueues.get(counter);
        return (counterQueue == null || counterQueue.isEmpty());
    }
    
    public Map<Integer, Deque<Token>> getMap(){
    	return counterQueues;
    }
    
    public Map<Integer, Deque<Token>> getPendingMap(){
    	return pendingQueues;
    }
    
    public void addFirst(int counter,Token token) {
    	Deque<Token> counterQueue=counterQueues.get(counter);
    	counterQueue.addFirst(token);
    	
    }

}
