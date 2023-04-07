package com.pratiti.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pratiti.project.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer>{
	
	@Query("select s from Service s where s.serviceName=?1")
	Optional<Service> findByServiceName(String serviceName);
	
	@Query("select s from Service s where s.counter.id=?1")
	Optional<Service> findByCounterId(int id);

//	@Query("select s.service.name from Service s where s.id=?1")
//	String findservicename(int id);

	@Query("select s from Service s where s.serviceName = ?1")
	Service fetchByName(String serviceName);

	@Query("select s from Service s where s.counter.id = null")
	List<Service> fetchAll();

}
