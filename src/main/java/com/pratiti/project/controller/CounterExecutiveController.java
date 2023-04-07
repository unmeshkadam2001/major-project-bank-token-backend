package com.pratiti.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pratiti.project.exceptions.CounterServiceException;
import com.pratiti.project.model.CounterExecutiveLoginStatus;
import com.pratiti.project.model.LoginData;
import com.pratiti.project.model.StatusData;
import com.pratiti.project.service.CounterExecutiveService;

@RestController
@CrossOrigin
public class CounterExecutiveController {
	
	@Autowired
	private CounterExecutiveService  counterExecutiveService;
	
	@PostMapping("/executivelogin")
	public CounterExecutiveLoginStatus Login(@RequestBody LoginData loginData) {
		CounterExecutiveLoginStatus status = new CounterExecutiveLoginStatus();
		try {
			status=counterExecutiveService.login(loginData);
		} catch (CounterServiceException e) {
			status.setStatus(false);
			status.setMesssageIfAny("Incorrect credentials");
		}
		return status;
		
	}
}
