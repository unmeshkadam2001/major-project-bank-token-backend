package com.pratiti.project.service;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratiti.project.entity.Counter;
import com.pratiti.project.entity.Service;
import com.pratiti.project.entity.Servicetype;
import com.pratiti.project.entity.Token;
import com.pratiti.project.entity.Token.Status;
import com.pratiti.project.exceptions.TokenServiceException;
import com.pratiti.project.queuemanager.TokenQueueManager;
import com.pratiti.project.repository.CounterRepository;
import com.pratiti.project.repository.ServiceRepository;
import com.pratiti.project.repository.ServicetypeRepository;
import com.pratiti.project.repository.TokenRepository;

@org.springframework.stereotype.Service
public class CounterService {
	@Autowired
	private TokenRepository tokenrepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private CounterRepository counterRepository;

	@Autowired
	private ServicetypeRepository servicetypeRepository;

	TokenQueueManager tokenqueue = TokenQueueManager.getInstance();

	public Queue<Token> gettoken(int cid) {

		Queue<Token> q = null;
		Map<Integer, Deque<Token>> map = tokenqueue.getMap();
		for (Map.Entry<Integer, Deque<Token>> x : map.entrySet()) {
			System.out.println(x.getKey());
			if (x.getKey() == cid) {
				q = x.getValue();
			}
		}
		return q;
	}

	public Token gettopservice(int cid) {
		Token token = tokenqueue.top(cid);
		if (token != null) {
			token.setStatus(Status.ACTIVE);
		}
		return token;
	}
	
	public boolean makeTokenActive(int tokenId,int cId) {
		Map<Integer, Deque<Token>> map = tokenqueue.getMap();
		Deque<Token> queue=map.get(cId);
		int requiredCounterId=cId;
		if(queue==null || queue.size()==0) {
			requiredCounterId=tokenrepository.findById(tokenId).get().getService().getCounter().getId();
		}
		for (Map.Entry<Integer, Deque<Token>> x : map.entrySet()) {
			Queue<Token> q=x.getValue();
			if(x.getKey()==requiredCounterId) {
				Token token = tokenrepository.findById(tokenId).get();
				if(token.getStatus() == Status.NOSHOW) {
					token.setStatus(Status.ACTIVE);
					token.setFrequencyOfCalling(token.getFrequencyOfCalling()+1);
					tokenrepository.save(token);
					tokenqueue.dequeue(cId, "pendingqueue");
					return true;
				}else if(token.getStatus()==Status.ACTIVE) {
					return false;
				}else if(token.getStatus()==Status.ABANDONED) {
					return false;
				}
				token.setStatus(Status.ACTIVE);
				token.setFrequencyOfCalling(token.getFrequencyOfCalling()+1);
				tokenrepository.save(token);
				tokenqueue.dequeue(requiredCounterId);
			}
		}
		return true;
	}
	
	public boolean addTokenToPending(int tokenId,int cId) {
		Token token = tokenrepository.findById(tokenId).get();
//		int cId = tokenrepository.findById(tokenId).get().getService().getCounter().getId();
		if(token.getStatus()!=Status.ACTIVE) {
			return false;
		}
		if(token.getFrequencyOfCalling()>=3) {			
			token.setStatus(Status.ABANDONED);
			tokenrepository.save(token);
			return false;
		}
		token.setStatus(Status.NOSHOW);
		tokenrepository.save(token);
		tokenqueue.enqueue(token, cId, "noshow");
		return true;
	}
	
	public boolean serveToken(int tokenId) {
		Token token = tokenrepository.findById(tokenId).get();
		int cId = tokenrepository.findById(tokenId).get().getService().getCounter().getId();
		if(token.getStatus()==Status.ACTIVE) {
			token.setStatus(Status.SERVICED);
			tokenrepository.save(token);
			return true;
		}
		return false;
	}

//	public void changestatus(int cid, String st) {
//		Queue<Token> q = new LinkedList<>();
//		Token token = new Token();
//		token = tokenqueue.top(cid);
//		if (st.equals("done")) {
//			token.setStatus(Status.SERVICED);
//			tokenqueue.dequeue(cid);
//			tokenrepository.save(token);
//			System.out.println("done");
//		} else if (st.equals("noshow")) {
//			if (token.getFrequencyOfCalling() >= 3) {
//				token.setStatus(Status.ABANDONED);
//				tokenrepository.save(token);
//				tokenqueue.dequeue(cid);
//				throw new TokenServiceException("excess of token call so it is abandoned");
//			}
//			token.setStatus(Status.NOSHOW);
//			token.setFrequencyOfCalling(token.getFrequencyOfCalling() + 1);
//			tokenqueue.enqueue(token, cid, st);
//			tokenqueue.dequeue(cid);
//		}
//	}

	public List<Counter> getcounter() {
		return counterRepository.findAll();
	}
	
	public Counter getCounter(int counterId) {
		Optional<Counter> counterData=counterRepository.findById(counterId);
		if(counterData.isPresent()) {
			return counterData.get();
		}else {
			throw new RuntimeException("Counter not found!");
		}
	}

	public List<Service> getservices() {
		return serviceRepository.findAll();
	}

	public List<Servicetype> getsubservicename(int sid) {
		return servicetypeRepository.findByParentServiceId(sid);
	}

	public List<Servicetype> getallsubservicename() {
		return servicetypeRepository.findAll();
	}

}
