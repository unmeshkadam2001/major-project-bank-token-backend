package com.pratiti.project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.pratiti.project.entity.Counter;

public interface CounterRepository extends JpaRepository<Counter, Integer>{

	@Query("select c from Counter c where upper(c.counterName) like upper(?1)")
	Optional<Counter> findByName(String name);
	
}
