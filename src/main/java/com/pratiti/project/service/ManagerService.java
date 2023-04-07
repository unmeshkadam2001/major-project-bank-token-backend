package com.pratiti.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pratiti.project.model.ServiceAndTypes;
import com.pratiti.project.model.StatusData;

import org.springframework.beans.factory.annotation.Autowired;
import com.pratiti.project.entity.Counter;
import com.pratiti.project.entity.CounterExecutive;
import com.pratiti.project.entity.Manager;
import com.pratiti.project.entity.Service;
import com.pratiti.project.entity.Servicetype;
import com.pratiti.project.exceptions.CounterServiceException;
import com.pratiti.project.exceptions.ManagerServiceException;
import com.pratiti.project.model.CounterData;
import com.pratiti.project.model.LoginData;
import com.pratiti.project.repository.CounterExecutiveRepository;
import com.pratiti.project.repository.CounterRepository;
import com.pratiti.project.repository.ManagerRepository;
import com.pratiti.project.repository.ServiceRepository;
import com.pratiti.project.repository.ServicetypeRepository;

@org.springframework.stereotype.Service
public class ManagerService {

	@Autowired
	private CounterRepository counterRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private ServicetypeRepository serviceTypeRepository;

	@Autowired
	private CounterExecutiveRepository counterExecutiveRepository;
	
	@Autowired
	private ManagerRepository managerRepository;


	// Manager : Adding Counter and assigning service
	public String addCounter(CounterData counterData) {
		Optional<Counter> counter1 = counterRepository.findByName(counterData.getCounterName());
		Service service = serviceRepository.fetchByName(counterData.getServiceName());
		CounterExecutive counterExecutive = counterExecutiveRepository.fetchByUsername(counterData.getCounterExecutiveName());
		if (!counter1.isPresent() && counterData.getCounterName() != null) {
			Counter counter = new Counter();
			counter.setName(counterData.getCounterName());
			// Assigning counter to service
			service.setCounter(counter);
			// Assigning counter executive to counter
			counterExecutive.setCounter(counter);

			counterRepository.save(counter);
			return "Counter Added Successfully";
		} else {
			throw new ManagerServiceException("Counter Is already Available");
		}
	}

	// Getting All Services which are not assigned to a counter
	public List<Service> getServices() {
		List<Service> services = serviceRepository.fetchAll();
		return services;
	}

	// Getting All Counter Executives which are not assigned to a counter
	public List<CounterExecutive> getCounterExecutive() {
		List<CounterExecutive> counterExecutives = counterExecutiveRepository.fetchAll();
		return counterExecutives;
	}



	// Manager : Adding Services and adding it's types
	public String addServiceAndItsType(ServiceAndTypes serviceAndTypes){
		Optional<Service> serviceCheck = serviceRepository.findByServiceName(serviceAndTypes.getServiceName());

		if (!serviceCheck.isPresent() && serviceAndTypes.getServiceName() != null) {
			Service service1 = new Service();
			List<Servicetype> servicetype = new ArrayList<>();

			service1.setServiceName(serviceAndTypes.getServiceName());

			for (Servicetype st : serviceAndTypes.getServicetypes()) {
				Servicetype st1 = new Servicetype();
				st1.setServiceName(st.getServiceName());
				st1.setParentService(service1);
				servicetype.add(st1);
			}
			serviceTypeRepository.saveAll(servicetype);
			return "Service and it's types added successfully!";
		} else {
			throw new ManagerServiceException("The bank is already providing this service");
		}
	}
	
	//admin login
	public Manager login(LoginData data) {
		if (managerRepository.existsByName(data.getUsername())) {
			Optional<Manager> manager=managerRepository.findByName(data.getUsername());
			Manager managerData=manager.get();
			if (managerData.getPassword().equals(data.getPassword())) {
				System.out.println("login successfully");
				return managerData;
			} 
			else {
				throw new CounterServiceException("Enter Correct Password");
			}

		} 
		else {
			throw new CounterServiceException("Enter Correct Username");
		}
	}
	
	//Manager : Adding the Counter Executive
	public void addCounterExecutive(CounterExecutive data) {
		 CounterExecutive counterExecutive=counterExecutiveRepository.findByUsername(data.getUsername());
		 if(counterExecutive==null) {
			 counterExecutiveRepository.save(data);
		 }
		 else {
			 throw new ManagerServiceException("Counter executive already exists !");
		 }
	}
	
	
	// Manager : Adding CatchAllCounter and assigning counter Executive
	public Counter addCatchAllCounter(CounterData counterData) {
		Optional<Counter> counter1 = counterRepository.findByName(counterData.getCounterName());
		CounterExecutive counterExecutive = counterExecutiveRepository.fetchByUsername(counterData.getCounterExecutiveName());
		if (!counter1.isPresent() && counterData.getCounterName() != null) {
			Counter counter = new Counter();
			counter.setName(counterData.getCounterName());
			// Assigning counter executive to counter
			counterExecutive.setCounter(counter);

			counterRepository.save(counter);
			return counter;
		} else {
			throw new ManagerServiceException("Counter Is already Available");
		}
	}


}
