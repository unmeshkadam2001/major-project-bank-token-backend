package com.pratiti.project.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@NamedQuery(name="Counter.findAll", query="SELECT c FROM Counter c")
public class Counter  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String counterName;



	@OneToOne(mappedBy="counter",cascade = CascadeType.ALL)
	@JsonIgnore
	private CounterExecutive counterExecutive;
	
	//bidirectional one-to-one association to Service
	@OneToOne(mappedBy="counter", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private Service service;

	public Counter() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.counterName;
	}

	public void setName(String counterName) {
		this.counterName = counterName;
	}

	public CounterExecutive getCounterExecutive() {
		return this.counterExecutive;
	}

	public void setCounterExecutive(CounterExecutive counterExecutive) {
		this.counterExecutive = counterExecutive;
	}

	public Service getService() {
		return this.service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}