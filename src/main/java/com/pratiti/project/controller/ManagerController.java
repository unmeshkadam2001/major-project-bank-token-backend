package com.pratiti.project.controller;

import java.util.ArrayList;
import java.util.List;

import com.pratiti.project.model.ServiceAndTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.pratiti.project.entity.Counter;
import com.pratiti.project.entity.CounterExecutive;
import com.pratiti.project.entity.Manager;
import com.pratiti.project.entity.Service;
import com.pratiti.project.exceptions.CounterServiceException;
import com.pratiti.project.exceptions.ManagerServiceException;
import com.pratiti.project.model.CounterData;
import com.pratiti.project.model.CounterExecutivesData;
import com.pratiti.project.model.LoginData;
import com.pratiti.project.model.LoginStatus;
import com.pratiti.project.model.ServicesData;
import com.pratiti.project.model.Status;
import com.pratiti.project.model.StatusData;
import com.pratiti.project.service.ManagerService;

@RestController
@CrossOrigin
public class ManagerController {

	@Autowired
	private ManagerService managerService;


	// Manager : Adding Counter and assigning service and counter executive to it
	@PostMapping("/add/counter")
	public String addCounter(@RequestBody CounterData counterData) {
		try {
			String res = managerService.addCounter(counterData);
			return res;
		} catch (ManagerServiceException e) {
			return e.getMessage();
		}
	}


	@GetMapping("/get/services")
	public List<ServicesData> getService() {
		List<Service> services = managerService.getServices();
		List<ServicesData> servicesData = new ArrayList<>();
		for (Service service : services) {
			ServicesData serviceData = new ServicesData();
			serviceData.setId(service.getId());
			serviceData.setServiceName(service.getServiceName());
			servicesData.add(serviceData);
		}
		return servicesData;
	}


	@GetMapping("/get/counter/executive")
	public List<CounterExecutivesData> getCounterExecutive() {
		List<CounterExecutive> counterExecutives = managerService.getCounterExecutive();
		List<CounterExecutivesData> CounterExecutivesData = new ArrayList<>();
		for (CounterExecutive counterExecutive : counterExecutives) {
			CounterExecutivesData counterExecutiveData = new CounterExecutivesData();
			counterExecutiveData.setId(counterExecutive.getId());
			counterExecutiveData.setUsername(counterExecutive.getUsername());
			CounterExecutivesData.add(counterExecutiveData);
		}
		return CounterExecutivesData;
	}



	// Manager : Adding Service
	@PostMapping("/add/service")
	public String addServiceAndItsType(@RequestBody ServiceAndTypes serviceAndTypes) {
		try {
			String res = managerService.addServiceAndItsType(serviceAndTypes);
			return res;
		} catch (ManagerServiceException e) {
			return e.getMessage();
		}
	}
	
	
	@PostMapping("/adminlogin")
	public LoginStatus adminLogin(@RequestBody LoginData loginData) {
		LoginStatus status=new LoginStatus();
		try {
			Manager manager=managerService.login(loginData);
			status.setId(manager.getId());
			status.setMesssageIfAny("Login successfull!");
			status.setStatus(true);
			
		} 
		catch (CounterServiceException e) {
			status.setMesssageIfAny(e.getMessage());
			status.setStatus(false);
		}
		return status;
	}
	
	@PostMapping("/addcounterexecutive")
	public Status addCounterExecutive(@RequestBody CounterExecutive counterExecutive) {
		Status status=new Status();
		try {
			managerService.addCounterExecutive(counterExecutive);
			status.setMesssageIfAny("Successfully added the Counter Executive !");
			status.setStatus(true);
		}
		catch(ManagerServiceException e) {
			status.setMesssageIfAny(e.getMessage());
			status.setStatus(false);
		}
		return status;
	}

	// Manager : Adding Catch all Counter 
		@PostMapping("/add/catchallcounter")
		public Status addCatchAllCounter(@RequestBody CounterData counterData) {
			Status status=new Status();
			try {
				Counter counter = managerService.addCatchAllCounter(counterData);
				status.setMesssageIfAny("Successfully added the the Catchall counter!");
				status.setStatus(true);
			} catch (ManagerServiceException e) {
				status.setMesssageIfAny(e.getMessage());
				status.setStatus(false);
			}
			return status;
		}
}
