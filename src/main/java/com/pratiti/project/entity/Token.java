package com.pratiti.project.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Time;
import java.util.List;

@Entity
public class Token  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="expected_time")
	private Time expectedTime;

	@Column(name="frequency_of_calling")
	private int frequencyOfCalling;

	@Column(name="generation_time")
	private Time generationTime;

	//bi-directional many-to-one association to Service
	@ManyToOne
	private Service service;
	
	private int servicetypeId;
	
	
	public int getServicetypeId() {
		return servicetypeId;
	}

	public void setServicetypeId(int servicetypeId) {
		this.servicetypeId = servicetypeId;
	}
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public static enum Status{
		ACTIVE,SERVICED,PENDING,NOSHOW,ABANDONED;
	}
	
	public Token() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(Time expectedTime) {
		this.expectedTime = expectedTime;
	}

	public int getFrequencyOfCalling() {
		return frequencyOfCalling;
	}

	public void setFrequencyOfCalling(int frequencyOfCalling) {
		this.frequencyOfCalling = frequencyOfCalling;
	}

	public Time getGenerationTime() {
		return generationTime;
	}

	public void setGenerationTime(Time generationTime) {
		this.generationTime = generationTime;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	public String toString() {
		List<Servicetype> temp = this.service.getServicetypes();
		for(Servicetype s:temp) {
			System.out.println(s.getServiceName());
		}
		return "token id"+this.id+" token status "+this.status+"token subservices"+this.service.getServiceName();
	}

	

}