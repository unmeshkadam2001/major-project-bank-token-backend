package com.pratiti.project.controller;

import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratiti.project.entity.Counter;
import com.pratiti.project.entity.Service;
import com.pratiti.project.entity.Servicetype;
import com.pratiti.project.entity.Token;
import com.pratiti.project.exceptions.TokenServiceException;
import com.pratiti.project.model.Status;
import com.pratiti.project.model.StatusData;
import com.pratiti.project.service.CounterService;


@RestController
@CrossOrigin
public class CounterController {
	
	
	@Autowired
	private CounterService counterService;
	
	
	@GetMapping("/gettokencounters")
	public Queue<Token> getToken(@RequestParam int cid){
		Queue<Token> tokens=counterService.gettoken(cid);
		return tokens;
	}
	
	@GetMapping("/gettopservice")
	public Token getTopService(@RequestParam int cid) {
//		System.out.println("get top service called");
		Token token=counterService.gettopservice(cid);
		return token;	
	}
	
	@GetMapping("/make-token-active")
	public boolean makeTokenActive(@RequestParam int tokenId,@RequestParam int cId) {
		return counterService.makeTokenActive(tokenId,cId);
		
	}
	
	@GetMapping("/addtoken-to-pending")
	public boolean addTokenToPending(@RequestParam int tokenId,@RequestParam int cId) {
		return counterService.addTokenToPending(tokenId,cId);
	}
	
	@GetMapping("serve-token")
	public boolean serveToken(@RequestParam int tokenId) {
		return counterService.serveToken(tokenId);
	}
	
//	@PostMapping("/changestatus")
//	public Status changeStatus(@RequestBody StatusData statusdata){
//		Status status=new Status();
//		try {
//			counterService.changestatus(statusdata.getCid(),statusdata.getSt());
//			status.setMesssageIfAny("Successfully change status!");
//			status.setStatus(true);
//			}
//		catch(TokenServiceException e) {
//			status.setMesssageIfAny(e.getMessage());
//			status.setStatus(false);
//		}
//		return status;
//	}
	
	@GetMapping("/get-counter")
	public List<Counter> getCounters()
	{
		 return counterService.getcounter();
	}
	
	@GetMapping("/get-services")
	public List<Service> getServices()
	{
		return counterService.getservices();
		
	}
	@GetMapping("/get-sub-service")
	public List<Servicetype> getSubServices(@RequestParam int sid)
	{
		return counterService.getsubservicename(sid);
		
	}
	
	@GetMapping("/get-all-sub-service")
	public List<Servicetype> getAllSubservices()
	{
		return counterService.getallsubservicename();
		
	}
	
	@GetMapping("/get-counter-name")
	public String getCounterName(@RequestParam("counterid") int counterId) {
		try {
			Counter counter=counterService.getCounter(counterId);
			return counter.getName();
		}catch(RuntimeException e) {
			return e.getMessage();
		}
		
	}
			
}
